/**
 * Project_VASE Deploy package
 */
package vase.client.deploy;

import java.awt.Container;
import java.io.File;
import java.io.IOException;
import java.rmi.RemoteException;
import java.util.ArrayList;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileSystemView;

import com.vmware.vim25.ManagedObjectReference;
import com.vmware.vim25.mo.Datacenter;
import com.vmware.vim25.mo.Datastore;
import com.vmware.vim25.mo.Folder;
import com.vmware.vim25.mo.HostSystem;
import com.vmware.vim25.mo.InventoryNavigator;
import com.vmware.vim25.mo.ManagedEntity;
import com.vmware.vim25.mo.Network;
import com.vmware.vim25.mo.ServiceInstance;
import com.vmware.vim25.mo.VirtualMachine;
import com.vmware.vim25.mo.util.MorUtil;

/**
 * Engine that interfaces with ESX vSphere Server
 * Contains methods that allow deployment customizing of VMs
 * <strong>Note: </strong>This class is the core of VASE Deploy.  Almost every
 * class has some ties to this class.  Use caution when editing this class.
 * @author James McNatt & Brenton Kapral
 * @version Project_VASE Deploy
 */
public class CommandEngine implements ProjectConstraints, GuiConstraints
{	
	private String currentServer;
	private String currentUsername;
	private String currentPassword;
	private ServiceInstance si;
	private GuiDeploymentTab deployTab;
	
	/**
	 * GuiMain instance
	 * @see GuiMain
	 */
	public GuiMain main;
	
	/**
	 * The project Datacenter
	 * @see Datacenter
	 * @see ConfigReader#getDatacenterName()
	 */
	public Datacenter dc;
	
	/**
	 * Template directory Folder object, containing all project templates
	 * @see ConfigReader#getTemplateDir()
	 */
	public Folder templateDir;
	
	/**
	 * Main project directory
	 * @see ConfigReader#getProjectDir()
	 */
	public Folder rootDir;
	
	/**
	 * Target datastore to send all VM deployments
	 * @see ConfigReader#getTargetDatastore()
	 */
	public Datastore targetDatastore;
	
	/**
	 * All networks in the Datacenter's network root
	 * @see Datacenter#getNetworkFolder()
	 * @see Network
	 */
	public ArrayList<Network> networks;
	
	/**
	 * List of all folders in the datacenter using the FolderExt class
	 * @see FolderExt 
	 */
	public ArrayList<FolderExt> vmFolders;
	
	/**
	 * List of all templates in the template folder using the Template class
	 * @see Template
	 */
	public ArrayList<Template> vmTemplates;
	
	/**
	 * List of all VMs in the datacenter using the VirtualMachineExt class
	 * @see VirtualMachineExt
	 */
	public ArrayList<VirtualMachineExt> vmRootEntities;
	
	//Identifiers for PowerOperationsThread
	
	/**
	 * Power on the virtual machine
	 */
	public static final int POWER_ON = 1;
	
	/**
	 * Power off the virtual machine
	 */
	public static final int POWER_OFF = 2;
	
	/**
	 * Suspend the virtual machine
	 */
	public static final int SUSPEND = 3;
	
	/**
	 * Reset the virtual machine
	 */
	public static final int RESET = 4;
	
	/**
	 * Shutdown the virtual machine
	 */
	public static final int SHUTDOWN = 5;
	
	/**
	 * Restart the virtual machine
	 */
	public static final int RESTART = 6;
	
	/**
	 * Delete the virtual machine from the disk
	 */
	public static final int DELETE = 7;
	
	/**
	 * Rename the virtual machine
	 */
	public static final int RENAME = 8;
	
	/**
	 * Move the virtual machine from one datastore to another
	 */
	public static final int MOVE = 9;
	
	/**
	 * Main Constructor
	 * Takes a ServiceInstance object created by the login splash
	 * @param ServiceInstance si
	 * @see LoginSplash
	 */
	public CommandEngine(ServiceInstance si, GuiMain main)
	{
		this.si = si;
		this.main = main;
		vmTemplates = new ArrayList<Template>();
		vmRootEntities = new ArrayList<VirtualMachineExt>();
		vmFolders = new ArrayList<FolderExt>();
		networks = new ArrayList<Network>();
		
		try
		{
			Folder rootFolder = si.getRootFolder();
			ManagedEntity[] datacenters = rootFolder.getChildEntity();
			for (int i = 0; i < datacenters.length; i++)
			{
				if (datacenters[i] instanceof Datacenter && datacenters[i].getName().equalsIgnoreCase(DATACENTER))
				{
					dc = (Datacenter) datacenters[i];
				}
			}
			
			//Set the template directory and the project directory
			for (ManagedEntity each : dc.getVmFolder().getChildEntity())
			{
				if (each instanceof Folder && each.getName().equalsIgnoreCase(TEMPLATE_FOLDER))
				{
					templateDir = (Folder) each;
				}
				
				else if (each instanceof Folder && each.getName().equalsIgnoreCase(ROOT_FOLDER))
				{
					rootDir = (Folder) each;
				}
			}
			
			//Set the target datastore for new VM deployments
			for (ManagedEntity each : dc.getDatastoreFolder().getChildEntity())
			{
				if (each instanceof Datastore && each.getName().equalsIgnoreCase(TARGET_DATASTORE))
				{
					targetDatastore = (Datastore) each;
				}
			}
			
			
			//Get Networks - only happens once per load
			for (ManagedEntity each : dc.getNetworkFolder().getChildEntity())
			{
				if (each instanceof Network)
				{
					networks.add(((Network) each));
				}
			}
			
			refresh();
		}
		
		catch (RemoteException e)
		{
			disconnect();
		}
		
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}
	
	/**
	 * Launches the VM's console
	 * <br />
	 * If the VM has been removed or is no longer reachable, Exception will be thrown and a dialog box
	 * will appear to inform the user.  Output of the process is appended to the log file.
	 * @param the virtual machine to target
	 */
	public void launchConsole(VirtualMachine vm)
	{
		try
		{
			ManagedObjectReference hostReference = vm.getSummary().runtime.host;
			ManagedEntity host = MorUtil.createExactManagedEntity(si.getServerConnection(), hostReference);
			String ipAddress = (((((HostSystem) host).getConfig()).network.consoleVnic)[0].getSpec().getIp().ipAddress);			
			String vmxPath = vm.getConfig().getFiles().getVmPathName();
			String pathToVMRC = System.getenv("ProgramFiles") + "\\" + VMRC;
			String args = " -h " + ipAddress + " -u " + HOST_USERNAME + " -p " + HOST_PASSWORD;
			
			//Full Screen
			if (SETTINGS_READER.fullScreen)
			{
				args += " -X ";
			}
			
			else
			{
				args += " ";
			}
			
			//VM Datastore and VMX file location
			args += "-m \"" + vmxPath + "\"";
			
			LOG.write("Launching console for " + vm.getName() + ".  Please wait...");
			Process p = Runtime.getRuntime().exec(pathToVMRC + args);
			
			Thread inputThread = new ProcessInputThread(p.getInputStream());
			Thread errorThread = new ProcessErrorThread(p.getErrorStream());
			
			inputThread.start();
			errorThread.start();
			p.getOutputStream().close();			
		}
		
		catch (NullPointerException e)
		{
			JOptionPane.showMessageDialog(main, "Error: Could not access information related to the Virtual Machine Host", 
					"Error", JOptionPane.ERROR_MESSAGE);
		}
		
		catch (IOException e)
		{
			JOptionPane.showMessageDialog(main, "Error: Could not execute VMRC. Please check the path in deploy.conf",
					"Error", JOptionPane.ERROR_MESSAGE);
		}
		
		catch (Exception e)
		{
			JOptionPane.showMessageDialog(main, "Error: Could not locate virtual machine in the datacenter", "Error", JOptionPane.ERROR_MESSAGE);
			e.printStackTrace();
		}
	}
	
	/**
	 * Gets the IP address of the vCenter server
	 * @return the currentServer
	 */
	public String getCurrentServer()
	{
		return currentServer;
	}

	/**
	 * Sets the IP address representation of the vCenter server
	 * @param currentServer the currentServer to set
	 */
	public void setCurrentServer(String currentServer)
	{
		this.currentServer = currentServer;
	}

	/**
	 * Gets the username of the user currently logged on
	 * @return the currentUsername
	 */
	public String getCurrentUsername()
	{
		return currentUsername;
	}

	/**
	 * Sets the username of the user currently logged on
	 * @param currentUsername the name of the current user logged on
	 */
	public void setCurrentUsername(String currentUsername)
	{
		this.currentUsername = currentUsername;
	}

	/**
	 * Gets the password of the user logged in
	 * <br />
	 * <strong>IMPORTANT</strong>: This information is stored in plain text and should
	 * only be used to call scripts in the deployment operation
	 * @return the currentPassword
	 */
	public String getCurrentPassword()
	{
		return currentPassword;
	}

	/**
	 * Sets the password for the user currently logged on
	 * @param currentPassword the password for the current user
	 */
	public void setCurrentPassword(String currentPassword)
	{
		this.currentPassword = currentPassword;
	}

	/**
	 * Deploys the collected VirtualMachines from the GuiDeploymentWizard
	 * <br />
	 * <strong>Note: </strong>Starts a new thread
	 * @param deployed the gathered DeployedVirtualMachine objects to be deployed
	 */
	public void deploy(ArrayList<DeployedVirtualMachine> deployed)
	{
		deployTab.updatePanel(deployed);
		LOG.write("Beginning Deployment", true);
		Thread thread = new DeployThread(deployed, this);
		thread.start();
	}
	
	/**
	 * Powers on a virtual machine
	 * @param vm the virtual machine
	 */
	public void powerOn(VirtualMachine vm)
	{
		LOG.write("Powering on " + vm.getName());
		Thread thread = new CommandOperationsThread(vm, POWER_ON, this);
		thread.start();
	}
	
	/**
	 * Powers off a virtual machine
	 * @param vm the virtual machine
	 */
	public void powerOff(VirtualMachine vm)
	{
		LOG.write("Powering off " + vm.getName());
		Thread thread = new CommandOperationsThread(vm, POWER_OFF, this);
		thread.start();
	}
	
	/**
	 * Suspends a virtual machine
	 * @param vm the virtual machine
	 */
	public void suspend(VirtualMachine vm)
	{
		LOG.write("Suspending " + vm.getName());
		Thread thread = new CommandOperationsThread(vm, SUSPEND, this);
		thread.start();
	}
	
	/**
	 * Resets a virtual machine
	 * @param vm the virtual machine
	 */
	public void reset(VirtualMachine vm)
	{
		LOG.write("Reseting " + vm.getName());
		Thread thread = new CommandOperationsThread(vm, RESET, this);
		thread.start();
	}
	
	/**
	 * Shuts down a virtual machine
	 * @param vm the virtual machine
	 */
	public void shutdown(VirtualMachine vm)
	{
		LOG.write("Shutting down " + vm.getName());
		Thread thread = new CommandOperationsThread(vm, SHUTDOWN, this);
		thread.start();
	}
	
	/**
	 * Restarts a virtual machine
	 * @param vm the virtual machine
	 */
	public void restart(VirtualMachine vm)
	{
		LOG.write("Restarting " + vm.getName());
		Thread thread = new CommandOperationsThread(vm, RESTART, this);
		thread.start();
	}
	
	/**
	 * Deletes a virtual machine
	 * @param vm the virtual machine
	 */
	public void delete(VirtualMachine vm)
	{
		int confirm = JOptionPane.showConfirmDialog(null, "Are you sure you want to delete this" +
				" virtual machine from the datastore?", "Confirm Delete", JOptionPane.YES_NO_OPTION,
				JOptionPane.QUESTION_MESSAGE);
		if (confirm == JOptionPane.YES_OPTION)
		{
			LOG.write("Deleting " + vm.getName() + " from datastore");
			Thread thread = new CommandOperationsThread(vm, DELETE, this);
			thread.start();
		}
	}	
	
	/**
	 * Renames a virtual machine
	 * @param vm the virtual machine
	 */
	public void rename(VirtualMachine vm)
	{
		String name = JOptionPane.showInputDialog(null, "Enter new guest name: ");
		if (name != null)
		{
			LOG.write("Renaming " + vm.getName() + " to " + name);
			Thread thread = new CommandOperationsThread(vm, RENAME, this, name);
			thread.start();
		}
	}
	
	public void setTeam(VirtualMachine vm)
	{
		String choice =
			(String) JOptionPane.showInputDialog(main, "Select Team", "Team: ", JOptionPane.INFORMATION_MESSAGE, null, TEAM_NAMES, "Team 1");	
		if (choice != null)
		{
			LOG.write("Changing " + vm.getName() + " to " + choice);
			TEAMS.put(vm.getName(), choice);
			Thread thread = new CommandOperationsThread(vm, MOVE, this, choice);
			thread.start();
		}
	}
	
	/**
	 * Checks to see if a specified template exists
	 * <br />
	 * Used during the GuiDeployWizard to allow or deny the next step in the wizard
	 * @param name the template name to check
	 * @return whether or not the template exists
	 */
	public boolean isTemplate(String name)
	{
		boolean exists = false;
	
		for (Template each : vmTemplates)
		{
			if (each.getName().equalsIgnoreCase(name))
			{
				exists = true;
			}
		}
		
		return exists;		
	}
	
	/**
	 * Gathers the data from each VirtualMachine object
	 * Creates an Object[][] which is filled with data in a primary for/each
	 * loop.
	 * <br />
	 * <strong>Important: </strong>refresh() should be called to ensure this information
	 * is valid
	 * @return the table of information from each virtual machine
	 */
	public Object[][] gatherData()
	{
		Object[][] data = null;
		
		try
		{
			data = new Object[vmRootEntities.size()][COLUMN_HEADINGS.length];
			int row = 0;
			for (VirtualMachineExt each : vmRootEntities)
			{			
				data[row][0] = each.getIcon();
				data[row][1] = each.getName();
				data[row][2] = each.getGuestOS();
				data[row][3] = each.getTeam();
				data[row][4] = each.getPowerStatus();
				data[row][5] = each.getCpuUsage();
				data[row][6] = each.getMemUsage();
				data[row][7] = each.getHost();
				data[row][8] = each.getIpAddr();
				data[row][9] = each.getHostName();
				data[row][10] = each.getDescription();
			
				row++;
			}
		}
		
		catch (Exception e)
		{
			LOG.write("Error getting data to update VM table", true);
			e.printStackTrace();
		}
		
		return data;
	}
	
	/**
	 * Saves the settings for team mapping, last deployment, and current username/server to 
	 * the settings file
	 * Called by the GuiMenuBar and WindowListeners
	 */
	public void saveSettings()
	{
		for (VirtualMachineExt each : vmRootEntities)
		{
			TEAMS.put(each.getName(), each.getTeam());
		}
		
		SETTINGS_READER.lastServer = getCurrentServer();
		SETTINGS_READER.lastUser = getCurrentUsername();
		SETTINGS_READER.save();
		LOG.write("Settings Saved Successfully");
	}
	
	/**
	 * Exports the Last Deployment to an html file
	 * Called by the GuiMain in an action event
	 */
	public void exportLastDeployment(ArrayList<DeployedVirtualMachine> deployed, Container parent)
	{
		if (deployed.size() != 0)
		{
			FileSystemView fsv = FileSystemView.getFileSystemView();
			JFileChooser chooser = new JFileChooser(fsv.getRoots()[0]);
			chooser.addChoosableFileFilter(new ReportGeneratorFileFilter());
			chooser.setSelectedFile(new File("report.html"));
			int choice = chooser.showSaveDialog(parent);
			if (choice == JFileChooser.APPROVE_OPTION)
			{
				try
				{
					LOG.write("Creating report...");
					new ReportGenerator().makeReport(chooser.getSelectedFile(), deployed, this);
					LOG.write("Report saved at " + chooser.getSelectedFile().getAbsolutePath());
				}
				
				catch (Exception e)
				{
					LOG.write("Error in generating the report");
					e.printStackTrace();
				}
			}
		}
		
		else
		{
			JOptionPane.showMessageDialog(main, "No deployment data exists.  \nUse the Deployment Wizard to start a " +
					"new deployment before creating a deployment report.", "Could not create report", JOptionPane.ERROR_MESSAGE);
		}
	}
	
	/**
	 * Logs off from the service instance
	 * <br />
	 * Should be called when the program exists to make a clean exit from vSphere
	 */
	public void logoff()
	{
		try
		{
			int choice = JOptionPane.showConfirmDialog(main, "Logoff and return to the login screen?", "Logoff VASE Deploy?", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
			if (choice == JOptionPane.YES_OPTION)
			{
				saveSettings();
				si.getServerConnection().logout();
				main.dispose();
				new LoginSplash();
			}
		}
		
		catch (Exception e)
		{
			e.printStackTrace();
		}
		
		finally
		{
			main.dispose();
		}
	}
	
	/**
	 * Quits the Program
	 * <br />
	 * Called by the GuiMenuBar or the GuiMainWindowListener
	 */
	public void quit()
	{
			saveSettings();
			si.getServerConnection().logout();
			main.dispose();
			System.exit(0);
	}
	
	/**
	 * Shows a disconnected message when a RemoteException is caught
	 * Brings up a new LoginSplash
	 */
	public void disconnect()
	{
		LOG.write("Error: Connection lost from vCenter server");
		JOptionPane.showMessageDialog(main, "Connection lost from vCenter server", "Error: Connection Lost", JOptionPane.ERROR_MESSAGE);
		main.dispose();
		new LoginSplash();
	}
	
	/**
	 * Sets the instance of the GuiDeploymentTab so it can be refreshed
	 * with data from the last deployment
	 * @param deployTab the deployment tab from the GuiMain
	 */
	public void setDeploymentTab(Tab deployTab)
	{
		this.deployTab = (GuiDeploymentTab) deployTab;
	}
	
	/**
	 * Retrieves a ManagedEntity[] from the vmFolder of the Datacenter
	 * <br />
	 * Retrieves the name of the dir and adds it to the Vector<String>
	 * @return the names of all dirs in the vmFolder
	 */
	public synchronized ArrayList<FolderExt> getListFolders()
	{		
		return vmFolders;
	}
	
	/**
	 * Retrieves a Template[] from the templateDir
	 * <br />
	 * @return VMs in the templateDir
	 */
	public ArrayList<Template> getListTemplates()
	{
		return vmTemplates;
	}
	
	/**
	 * Gets a Template based on a name
	 * @param name the name of the Template to find
	 * @return the Template, or null if not found
	 */
	public Template getTemplate(String name)
	{
		Template foundTemplate = null;
		
		for (Template thisTemplate : vmTemplates)
		{
			if (thisTemplate.getName().equals(name))
			{
				foundTemplate = thisTemplate;
			}
		}
		
		return foundTemplate;
	}

	/**
	 * Retrieves a list of VirtualMachineExt objects from the rootDir
	 * <br />
	 * @return a Collection of VirtualMachinesExt objects
	 */
	public ArrayList<VirtualMachineExt> getListVMs()
	{
		return vmRootEntities;
	}
	
	/**
	 * Gets a VirtualMachineExt based on a name
	 * @param name the name of the VirtualMachineExt to find
	 * @return the VirtualMachineExt, or null if not found
	 */
	public VirtualMachineExt getVM(String name)
	{
		VirtualMachineExt foundVM = null;
		
		for (VirtualMachineExt thisVM : vmRootEntities)
		{
			if (thisVM.getName().equals(name))
			{
				foundVM = thisVM;
			}
		}
		
		return foundVM;
	}
	
	/**
	 * Gets a list of the names of the Network objects in the Datacenter
	 * <br />
	 * Used in the GuiDeployWizard to fill the combo box
	 * @return a collection of network names in the datacenter
	 * @see GuiDeployWizard
	 * @see CommandEngine#networks
	 */
	public String[] getNetworksAsStrings()
	{
		String[] nets = new String[networks.size()];
		
		for (int i = 0; i < nets.length; i++)
		{
			nets[i] = networks.get(i).getName();
		}
		
		return nets;
	}
	
	/**
	 * Gets a Network object from the collection of Networks based on a String, 
	 * which is the name of the network.  Returns null if not found
	 * <br />
	 * Used in the DeployThread to convert the name of the network back to the Network object
	 * @param name the name of the network to find
	 * @return the Network object, or null if not found
	 * @see GuiDeployWizard
	 * @see CommandEngine#networks
	 */
	public Network getNetworkFromString(String name)
	{
		Network network = null;
		
		for (Network each : networks)
		{
			if (each.getName().equals(name))
			{
				network = each;
			}
		}
		
		return network;
	}
	
	/**
	 * Refreshes the following attributes:
	 * <ul>
	 * <li>VirtualMachine[] vmRootEntities - containing all virtual machines in the rootDir</li>
	 * <li>VirtualMachine[] vmTemplates - containing all templates in the templateDir</li>
	 * <li>ManagedEntity[] dcFolders - all folders in the vmFolder in the datacenter</li>
	 * <br />
	 * <strong>Note: </strong>This method should be called first in the refresh thread
	 */
	public synchronized void refresh()
	{
		try
		{
			//Refresh VirtualMachines
			if (rootDir == null)
			{
				boolean found = false;
				ManagedEntity[] folders = dc.getVmFolder().getChildEntity();
				for (ManagedEntity each : folders)
				{
					if (each.getName().equals(ROOT_FOLDER) && each instanceof Folder)
					{
						rootDir = (Folder) each;
						found = true;
					}
				}
				
				if (!found)
				{
					LOG.write("Error: Could not find root directory.  Check the deploy.conf file and ensure " +
							"the root project directory exists");
				}
			}
			
			else
			{
				ManagedEntity[] theseVMs = new InventoryNavigator(rootDir).searchManagedEntities("VirtualMachine");
				vmRootEntities.clear();
				for (int i = 0; i < theseVMs.length; i++)
				{
					vmRootEntities.add(new VirtualMachineExt((VirtualMachine) theseVMs[i]));
				}
			}
			
			//Refresh Templates
			if (templateDir == null)
			{
				boolean found = false;
				ManagedEntity[] folders = dc.getVmFolder().getChildEntity();
				for (ManagedEntity each : folders)
				{
					if (each.getName().equals(TEMPLATE_FOLDER))
					{
						templateDir = (Folder) each;
						found = true;
					}
				}
					
				if (!found)
				{
					LOG.write("Error: Could not find template directory. Check the deploy.conf file and ensure " +
							"the template directory exists");
				}
			}
			
			else
			{
				ManagedEntity[] theseVMs = new InventoryNavigator(templateDir).searchManagedEntities("VirtualMachine");
				vmTemplates.clear();
				for (int i = 0; i < theseVMs.length; i++)
				{
					vmTemplates.add(new Template((VirtualMachine) theseVMs[i]));
				}
			}
			
			//Refresh Folder Listing
			ManagedEntity[] dcFolders = dc.getVmFolder().getChildEntity();
			vmFolders.clear();
			for (int i = 0; i < dcFolders.length; i++)
			{
				if (dcFolders[i] instanceof Folder) vmFolders.add(new FolderExt((Folder) dcFolders[i]));
			}
		}
		
		catch (NullPointerException e)
		{
			disconnect();
		}
		
		catch (RemoteException e)
		{
			disconnect();
		}
		
		catch (Exception e)
		{
			LOG.write("Exception in Refreshing data: " + e.getMessage(), true);
			e.printStackTrace();
		}
	}
}