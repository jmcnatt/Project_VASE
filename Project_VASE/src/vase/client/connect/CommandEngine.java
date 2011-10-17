/**
 * Project_VASE Connect package
 */
package vase.client.connect;

import java.rmi.RemoteException;
import java.util.ArrayList;

import javax.swing.DefaultListModel;
import javax.swing.JOptionPane;

import vase.client.ProcessErrorThread;
import vase.client.ProcessInputThread;

import com.vmware.vim25.HostNetworkInfo;
import com.vmware.vim25.HostVirtualNic;
import com.vmware.vim25.InvalidProperty;
import com.vmware.vim25.ManagedObjectReference;
import com.vmware.vim25.RuntimeFault;
import com.vmware.vim25.VirtualMachineSummary;
import com.vmware.vim25.mo.Datacenter;
import com.vmware.vim25.mo.Folder;
import com.vmware.vim25.mo.HostSystem;
import com.vmware.vim25.mo.InventoryNavigator;
import com.vmware.vim25.mo.ManagedEntity;
import com.vmware.vim25.mo.ServiceInstance;
import com.vmware.vim25.mo.VirtualMachine;
import com.vmware.vim25.mo.util.MorUtil;

/**
 * Command Engine that interfaces with VMware vCenter.  Sends power operations to the 
 * Virtual Machines and is responsible for loading VMware remote console when called by
 * the user.  Gathers a list of all virtual machines except the ones in the TEMPLATE_DIR
 * @author James McNatt & Brenton Kapral
 * @version Project_VASE Connect
 */
public class CommandEngine implements ProjectConstraints
{
	private String currentUsername;
	private String currentServer;
	public GuiMain main;
	private ServiceInstance si;	
	private Datacenter dc = null;
	
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
	 * Main Constructor
	 * @param si the service instance created by the LoginSplash
	 * @param the main Gui window
	 */
	public CommandEngine(ServiceInstance si, GuiMain main)
	{
		this.main = main;
		this.si = si;
		
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
		}
		
		catch (RemoteException e)
		{
			disconnect();
		}
	}
	
	/**
	 * Gets the Virtual Machine objects from the datacenter in the environment specified
	 * in the conf file. Ignores the TEMPLATE_DIR specified in the conf file.
	 * @throws RemoteException 
	 * @throws RuntimeFault 
	 * @throws InvalidProperty 
	 */
	public synchronized ArrayList<VirtualMachine> getVirtualMachines()
	{
		ArrayList<VirtualMachine> vms = new ArrayList<VirtualMachine>();
		ManagedEntity[] virtualMachines = null;
		
		if (dc != null)
		{
			try
			{
				virtualMachines = new InventoryNavigator(dc.getVmFolder()).searchManagedEntities("VirtualMachine");
			}
			
			catch (InvalidProperty e)
			{
				LOG.printStackTrace(e);
			}
			
			catch (RuntimeFault e)
			{
				LOG.printStackTrace(e);
			}
			
			catch (RemoteException e)
			{
				LOG.printStackTrace(e);
			}
		}
		
		if (virtualMachines != null)
		{
			for (ManagedEntity thisVM : virtualMachines)
			{
				if (thisVM instanceof VirtualMachine)
				{
					if (!thisVM.getParent().getName().equalsIgnoreCase(TEMPLATE_DIR))
					{
						vms.add((VirtualMachine) thisVM);
					}
				}
			}
		}		
		
		return vms;
	}
	
	/**
	 * Adds the Virtual Machines to the List on the GuiMain
	 * @see GuiMain
	 * @see CommandEngine#getVirtualMachines()
	 */
	public void populateList()
	{
		try
		{
			ArrayList<VirtualMachine> vms = main.engine.getVirtualMachines();
			DefaultListModel model = (DefaultListModel) main.jListVMs.getModel();
			int selection = main.jListVMs.getSelectionModel().getMinSelectionIndex();
			
			model.removeAllElements();
			
			for (VirtualMachine vm : vms)
			{
				Object[] data = new Object[4];
				VirtualMachineSummary summary = vm.getSummary();
				try {data[0] = summary.config.name;} catch (Exception e) {data[0] = "Unknown";}
				try {data[1] = summary.guest.guestFullName;} catch (Exception e) {data[1] = "Unknown";}
				try {data[2] = summary.runtime.powerState.name();} catch (Exception e) {data[2] = "Unknown";}
				data[3] = vm;
				model.addElement(data);
			}
			
			if (selection <= model.getSize() - 1)
			{
				main.jListVMs.getSelectionModel().setSelectionInterval(selection, selection);
			}
		}
		
		catch (RuntimeException e)
		{
			ProjectConstraints.LOG.printStackTrace(e);
			main.engine.disconnect();
		}
		
		catch (Exception e)
		{
			ProjectConstraints.LOG.printStackTrace(e);
		}
	}
	
	/**
	 * Launches the VM's console
	 * <br />
	 * If the VM has been removed or is no longer reachable, Exception will be thrown and a dialog box
	 * will appear to inform the user.  Output of the process is appended to the log file.
	 * @param name the VM name
	 */
	public void launchConsole(VirtualMachine vm)
	{
		try
		{
			//Find host of VM
			ManagedObjectReference hostReference = vm.getSummary().runtime.host;
			ManagedEntity host = MorUtil.createExactManagedEntity(si.getServerConnection(), hostReference);
			
			//Get Network info
			HostNetworkInfo networkInfo = ((HostSystem) host).getConfig().network;
			HostVirtualNic[] virtualNics = null;
			HostVirtualNic[] serviceNics = null;
			String ipAddress = null;
			
			if (networkInfo.getConsoleVnic() != null)
			{
				serviceNics = networkInfo.getConsoleVnic();
			}
			
			if (networkInfo.getVnic() != null)
			{
				virtualNics = networkInfo.getVnic();
			}
			
			if (serviceNics != null)
			{
				ipAddress = serviceNics[0].getSpec().getIp().ipAddress;
			}
			
			else if (virtualNics != null)
			{
				ipAddress = virtualNics[0].getSpec().getIp().ipAddress;
			}

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
			
			LOG.write("Launching console for " + vm.getName());
			LOG.write(pathToVMRC + args, false);
			Process p = Runtime.getRuntime().exec(pathToVMRC + args);
			
			Thread inputThread = new ProcessInputThread(p.getInputStream(), LOG);
			Thread errorThread = new ProcessErrorThread(p.getErrorStream(), LOG);
			
			inputThread.start();
			errorThread.start();
			p.getOutputStream().close();			
		}
		
		catch (NullPointerException e)
		{
			JOptionPane.showMessageDialog(main, "Error: Could not access information related to the Virtual Machine Host", 
					"Error", JOptionPane.ERROR_MESSAGE);
			LOG.printStackTrace(e);
		}
		
		catch (Exception e)
		{
			JOptionPane.showMessageDialog(main, "Error: Could not locate virtual machine in the datacenter", "Error", JOptionPane.ERROR_MESSAGE);
			LOG.printStackTrace(e);
		}
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
	 * Logs off from the service instance
	 * <br />
	 * Should be called when the program exists to make a clean exit from vSphere
	 */
	public void logoff()
	{
		try
		{
			int choice = JOptionPane.showConfirmDialog(main, "Logoff and return to the login screen?", "Logoff VASE Connect?", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
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
			LOG.printStackTrace(e);
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
		try
		{
			saveSettings();
			si.getServerConnection().logout();
		}
		
		catch (Exception e)
		{
			LOG.printStackTrace(e);
		}
		
		finally
		{
			main.dispose();
			System.exit(0);
		}
	}
	
	/**
	 * Saves the last user to login and the last server
	 * Called by the GuiMenuBar and WindowListeners
	 */
	public void saveSettings()
	{		
		SETTINGS_READER.lastServer = getCurrentServer();
		SETTINGS_READER.lastUser = getCurrentUsername();
		SETTINGS_READER.save();
		LOG.write("Settings Saved Successfully");
	}

	/**
	 * Gets the current user logged in
	 * <br />
	 * Saved to the settings file when called by savedSettings()
	 * @return the currentUsername
	 * @see CommandEngine#saveSettings()
	 */
	public String getCurrentUsername()
	{
		return currentUsername;
	}

	/**
	 * Sets the current user logged in
	 * <br />
	 * Called during the login() method in the LoginSplash
	 * @param currentUsername the currentUsername to set
	 * @see LoginSplash
	 */
	public void setCurrentUsername(String currentUsername)
	{
		this.currentUsername = currentUsername;
	}

	/**
	 * Gets the current vCenter server
	 * <br />
	 * Saved to the settings file when called by savedSettings()
	 * @return the currentServer
	 * @see CommandEngine#saveSettings()
	 */
	public String getCurrentServer()
	{
		return currentServer;
	}

	/**
	 * Sets the current vCenter server
	 * <br />
	 * Called during the login() method in the LoginSplash
	 * @param currentServer the currentServer to set
	 * @see LoginSplash
	 */
	public void setCurrentServer(String currentServer)
	{
		this.currentServer = currentServer;
	}
	
	/**
	 * Powers on a virtual machine using an OperationsThread
	 * @param vm the virtual machine
	 * @see OperationsThread
	 */
	public void powerOn(VirtualMachine vm)
	{
		LOG.write("Powering on " + vm.getName());
		Thread thread = new CommandOperationsThread(vm, POWER_ON, this);
		thread.start();
	}
	
	/**
	 * Powers off a virtual machine using an OperationsThread
	 * @param vm the virtual machine
	 * @see OperationsThread
	 */
	public void powerOff(VirtualMachine vm)
	{
		LOG.write("Powering off " + vm.getName());
		Thread thread = new CommandOperationsThread(vm, POWER_OFF, this);
		thread.start();
	}
	
	/**
	 * Suspends a virtual machine using an OperationsThread
	 * @param vm the virtual machine
	 * @see OperationsThread
	 */
	public void suspend(VirtualMachine vm)
	{
		LOG.write("Suspending " + vm.getName());
		Thread thread = new CommandOperationsThread(vm, SUSPEND, this);
		thread.start();
	}
	
	/**
	 * Resets a virtual machine using an OperationsThread
	 * @param vm the virtual machine
	 * @see OperationsThread
	 */
	public void reset(VirtualMachine vm)
	{
		LOG.write("Reseting " + vm.getName());
		Thread thread = new CommandOperationsThread(vm, RESET, this);
		thread.start();
	}
	
	/**
	 * Shuts down a virtual machine using an OperationsThread
	 * @param vm the virtual machine
	 * @see OperationsThread
	 */
	public void shutdown(VirtualMachine vm)
	{
		LOG.write("Shutting down " + vm.getName());
		Thread thread = new CommandOperationsThread(vm, SHUTDOWN, this);
		thread.start();
	}
	
	/**
	 * Restarts a virtual machine using an OperationsThread
	 * @param vm the virtual machine
	 * @see OperationsThread
	 */
	public void restart(VirtualMachine vm)
	{
		LOG.write("Restarting " + vm.getName());
		Thread thread = new CommandOperationsThread(vm, RESTART, this);
		thread.start();
	}
}
