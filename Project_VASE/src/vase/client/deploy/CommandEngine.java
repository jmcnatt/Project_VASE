/**
 * Project_VASE Deploy package
 */
package vase.client.deploy;

import java.awt.Component;
import java.io.File;
import java.rmi.RemoteException;
import java.util.ArrayList;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileSystemView;

import vase.client.Engine;
import vase.client.InterfaceConstraints;
import vase.client.deploy.gui.LoginSplash;
import vase.client.deploy.gui.Main;
import vase.client.deploy.gui.Tab;
import vase.client.deploy.gui.tab.DeploymentTab;
import vase.client.deploy.utils.ReportGenerator;
import vase.client.deploy.utils.report.ReportGeneratorFileFilter;
import vase.client.deploy.vmo.DeployedVirtualMachine;
import vase.client.deploy.vmo.FolderExt;
import vase.client.deploy.vmo.Template;
import vase.client.deploy.vmo.VirtualMachineExt;
import vase.client.thread.ThreadExt;

import com.vmware.vim25.ManagedObjectNotFound;
import com.vmware.vim25.mo.Datacenter;
import com.vmware.vim25.mo.Datastore;
import com.vmware.vim25.mo.Folder;
import com.vmware.vim25.mo.InventoryNavigator;
import com.vmware.vim25.mo.ManagedEntity;
import com.vmware.vim25.mo.Network;
import com.vmware.vim25.mo.ServiceInstance;
import com.vmware.vim25.mo.VirtualMachine;

/**
 * Engine that interfaces with ESX vSphere Server
 * Contains methods that allow deployment customizing of VMs
 * <strong>Note: </strong>This class is the core of VASE Deploy.  Almost every
 * class has some ties to this class.  Use caution when editing this class.
 * @author James McNatt & Brenton Kapral
 * @version Project_VASE Deploy
 */
public class CommandEngine extends Engine implements ProjectConstraints, InterfaceConstraints
{	
	
	private DeploymentTab deployTab;
	
	/**
	 * GuiMain instance
	 * @see GuiMain
	 */
	public Main main;
	
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
	
	/**
	 * Main Constructor
	 * Takes a ServiceInstance object created by the login splash
	 * @param ServiceInstance si
	 * @see LoginSplash
	 */
	public CommandEngine(ServiceInstance si, Main main)
	{
		super(si);
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
			LOG.printStackTrace(e);
		}
		
		catch (Exception e)
		{
			LOG.printStackTrace(e);
		}
	}
	
	/**
	 * Launches the VM's console, calls the superclass's method
	 * @param vm the Virtual Machine
	 */
	public void launchConsole(VirtualMachine vm)
	{
		super.launchConsole(vm, LOG, main, VMRC, HOST_USERNAME, HOST_PASSWORD, SETTINGS_READER.fullScreen);
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
		LOG.write("Beginning Deployment");
		Thread thread = new DeployThread(deployed, this);
		thread.start();
	}
	
	/**
	 * Powers on a virtual machine
	 * @param vm the virtual machine
	 */
	public void powerOn(VirtualMachine vm)
	{
		try
		{
			LOG.write("Powering on " + vm.getName());
			ThreadExt thread = new CommandOperationsThread(vm, POWER_ON, this);
			thread.start();
		}
		
		catch (RuntimeException e)
		{
			disconnect();
		}
	}
	
	/**
	 * Powers off a virtual machine
	 * @param vm the virtual machine
	 */
	public void powerOff(VirtualMachine vm)
	{
		try
		{
			LOG.write("Powering off " + vm.getName());
			ThreadExt thread = new CommandOperationsThread(vm, POWER_OFF, this);
			thread.start();
		}
		
		catch (RuntimeException e)
		{
			disconnect();
		}
	}
	
	/**
	 * Suspends a virtual machine
	 * @param vm the virtual machine
	 */
	public void suspend(VirtualMachine vm)
	{
		try
		{
			LOG.write("Suspending " + vm.getName());
			ThreadExt thread = new CommandOperationsThread(vm, SUSPEND, this);
			thread.start();
		}
		
		catch (RuntimeException e)
		{
			disconnect();
		}
	}
	
	/**
	 * Resets a virtual machine
	 * @param vm the virtual machine
	 */
	public void reset(VirtualMachine vm)
	{
		LOG.write("Reseting " + vm.getName());
		ThreadExt thread = new CommandOperationsThread(vm, RESET, this);
		thread.start();
	}
	
	/**
	 * Shuts down a virtual machine
	 * @param vm the virtual machine
	 */
	public void shutdown(VirtualMachine vm)
	{
		try
		{
			LOG.write("Shutting down " + vm.getName());
			ThreadExt thread = new CommandOperationsThread(vm, SHUTDOWN, this);
			thread.start();
		}
		
		catch (RuntimeException e)
		{
			disconnect();
		}
	}
	
	/**
	 * Restarts a virtual machine
	 * @param vm the virtual machine
	 */
	public void restart(VirtualMachine vm)
	{
		try
		{
			LOG.write("Restarting " + vm.getName());
			ThreadExt thread = new CommandOperationsThread(vm, RESTART, this);
			thread.start();
		}
		
		catch (RuntimeException e)
		{
			disconnect();
		}
	}
	
	/**
	 * Deletes a virtual machine
	 * @param vm the virtual machine
	 */
	public void delete(VirtualMachine vm)
	{
		try
		{
			int confirm = JOptionPane.showConfirmDialog(null, "Are you sure you want to delete this" +
					" virtual machine from the datastore?", "Confirm Delete", JOptionPane.YES_NO_OPTION,
					JOptionPane.QUESTION_MESSAGE);
			if (confirm == JOptionPane.YES_OPTION)
			{
				LOG.write("Deleting " + vm.getName() + " from datastore");
				ThreadExt thread = new CommandOperationsThread(vm, DELETE, this);
				thread.start();
			}
		}
		
		catch (RuntimeException e)
		{
			disconnect();
		}
	}	
	
	/**
	 * Renames a virtual machine
	 * @param vm the virtual machine
	 */
	public void rename(VirtualMachine vm)
	{
		try
		{
			String name = JOptionPane.showInputDialog(null, "Enter new guest name: ");
			if (name != null)
			{
				LOG.write("Renaming " + vm.getName() + " to " + name);
				ThreadExt thread = new CommandOperationsThread(vm, RENAME, this, name);
				thread.start();
			}
		}
		
		catch (RuntimeException e)
		{
			disconnect();
		}
	}
	
	public void setTeam(VirtualMachine vm)
	{
		try
		{
			String choice =
				(String) JOptionPane.showInputDialog(main, "Select Team", "Team: ", JOptionPane.INFORMATION_MESSAGE, null, TEAM_NAMES, null);	
			if (choice != null)
			{
				LOG.write("Changing " + vm.getName() + " to " + choice);
				TEAMS.put(vm.getName(), choice);
				ThreadExt thread = new CommandOperationsThread(vm, MOVE, this, choice);
				thread.start();
			}
		}
		
		catch (RuntimeException e)
		{
			disconnect();
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
	public Object[][] gatherTableData()
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
			LOG.printStackTrace(e);
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
	public void exportLastDeployment(ArrayList<DeployedVirtualMachine> deployed, Component parent)
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
					LOG.printStackTrace(e);
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
			}
		}
		
		catch (RuntimeException e)
		{
			disconnect();
		}
		
		catch (Exception e)
		{
			LOG.printStackTrace(e);
		}
		
		finally
		{
			main.worker.halt();
			main.dispose();
			new LoginSplash();
		}
	}
	
	/**
	 * Quits the Program
	 * <br />
	 * Called by the GuiMenuBar or the GuiMainWindowListener
	 */
	public void quit()
	{
		try
		{
			saveSettings();
			si.getServerConnection().logout();
		}
		
		catch (Exception e)
		{
			LOG.write("Error logging off. Could not close server connection successfully", true);
		}
		
		finally
		{
			main.dispose();
			System.exit(0);
		}
	}
	
	/**
	 * Shows a disconnected message when a RemoteException is caught
	 * Brings up a new LoginSplash
	 */
	public void disconnect()
	{
		LOG.write("Error: Connection lost from vCenter server");
		main.worker.halt();
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
		this.deployTab = (DeploymentTab) deployTab;
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
			LOG.write("Could not gather object data", true);
		}
		
		catch (ManagedObjectNotFound e)
		{
			LOG.write("Error: Could not locate VM in datacenter", true);
		}
		
		catch (RuntimeException e)
		{
			LOG.write("Error: Runtime error in refreshing datacenter list", true);
			disconnect();
		}
		
		catch (RemoteException e)
		{
			disconnect();
		}
		
		catch (Exception e)
		{
			LOG.write("Error in Refreshing data: " + e.getMessage(), true);
			LOG.printStackTrace(e);
		}
	}
}