/**
 * Project_VASE Connect package
 */
package vase.client.connect;

import java.rmi.RemoteException;
import java.util.ArrayList;

import javax.swing.DefaultListModel;
import javax.swing.JOptionPane;

import vase.client.Engine;
import vase.client.connect.gui.LoginSplash;
import vase.client.connect.gui.Main;

import com.vmware.vim25.InvalidProperty;
import com.vmware.vim25.RuntimeFault;
import com.vmware.vim25.VirtualMachineSummary;
import com.vmware.vim25.mo.Datacenter;
import com.vmware.vim25.mo.Folder;
import com.vmware.vim25.mo.InventoryNavigator;
import com.vmware.vim25.mo.ManagedEntity;
import com.vmware.vim25.mo.ServiceInstance;
import com.vmware.vim25.mo.VirtualMachine;

/**
 * Command Engine that interfaces with VMware vCenter.  Sends power operations to the 
 * Virtual Machines and is responsible for loading VMware remote console when called by
 * the user.  Gathers a list of all virtual machines except the ones in the TEMPLATE_DIR
 * @author James McNatt & Brenton Kapral
 * @version Project_VASE Connect
 */
public class CommandEngine extends Engine implements ProjectConstraints
{
	/**
	 * Root VM Directory
	 */
	private Folder rootDir = null;
	
	/**
	 * Main GUI instance
	 */
	public Main main;
	
	/**
	 * Main Constructor
	 * @param si the service instance created by the LoginSplash
	 * @param the main Gui window
	 */
	public CommandEngine(ServiceInstance si, Main main)
	{
		super(si);
		this.main = main;
		
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
			
			for (ManagedEntity entity : dc.getVmFolder().getChildEntity())
			{
				if (entity instanceof Folder && entity.getName().equals(ROOT_FOLDER))
				{
					rootDir = (Folder) entity;
					break;
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
	 * Targets the Project_DIR in the conf file
	 * @throws RemoteException 
	 * @throws RuntimeFault 
	 * @throws InvalidProperty 
	 */
	public synchronized ArrayList<VirtualMachine> getVirtualMachines()
	{
		ArrayList<VirtualMachine> vms = new ArrayList<VirtualMachine>();
		ManagedEntity[] virtualMachines = null;
		
		if (dc != null && rootDir != null)
		{
			try
			{
				virtualMachines = new InventoryNavigator(rootDir).searchManagedEntities("VirtualMachine");
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
				disconnect();
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
	 * Launches the VM's console, calls the superclass's method
	 * @param vm the Virtual Machine
	 */
	public void launchConsole(VirtualMachine vm)
	{
		super.launchConsole(vm, LOG, main, VMRC, HOST_USERNAME, HOST_PASSWORD, SETTINGS_READER.fullScreen);
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
