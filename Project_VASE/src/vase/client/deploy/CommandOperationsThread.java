/**
 * Project_VASE Deploy package
 */
package vase.client.deploy;

import vase.client.thread.ThreadExt;

import com.vmware.vim25.mo.Folder;
import com.vmware.vim25.mo.ManagedEntity;
import com.vmware.vim25.mo.Task;
import com.vmware.vim25.mo.VirtualMachine;

/**
 * Thread to handle power operations for VirtualMachines
 * Commands called by the CommandEngine and then executed in this Thread
 * @author James McNatt & Brenton Kapral
 * @version Project_VASE Deploy
 */
public class CommandOperationsThread extends ThreadExt
{
	private VirtualMachine vm;
	private int command;
	private String name;
	private CommandEngine engine;
	
	/**
	 * Main Constructor, takes in a VirtualMachineExt object
	 * @param vm
	 */
	public CommandOperationsThread(VirtualMachine vm, int command, CommandEngine engine)
	{
		this.vm = vm;
		this.command = command;
		this.engine = engine;
	}
	
	/**
	 * Overloaded constructor for renaming a virtual machine or moving a virtual machine
	 * @param vm the virtual machine
	 * @param command the command to execute
	 * @param name the new name of the virtual machine
	 */
	public CommandOperationsThread(VirtualMachine vm, int command, CommandEngine engine, String name)
	{
		this.vm = vm;
		this.command = command;
		this.name = name;
		this.engine = engine;
	}
	
	/**
	 * Runs the thread
	 * <br />
	 * Switches the command passed in from the CommandEngine
	 */
	public void run()
	{
		switch(command)
		{
			case 1:
			{
				powerOn(vm);
				break;
			}
			
			case 2:
			{
				powerOff(vm);
				break;
			}
			
			case 3:
			{
				suspend(vm);
				break;
			}
			
			case 4:
			{
				reset(vm);
				break;
			}
			
			case 5:
			{
				shutdown(vm);
				break;
			}
			
			case 6:
			{
				restart(vm);
				break;
			}
			
			case 7:
			{
				delete(vm);
				break;
			}
			
			case 8:
			{
				rename(vm);
				break;
			}
			
			case 9:
			{
				move(vm, name);
				break;
			}
		}
	}
	
	/**
	 * Powers on a Virtual Machine
	 * @param vm the virtual machine
	 */
	private void powerOn(VirtualMachine vm)
	{
		try
		{
			Task powerOn = vm.powerOnVM_Task(null);
			if (powerOn.waitForTask() == Task.SUCCESS)
			{
				ProjectConstraints.LOG.write(vm.getName() + " changed state to PoweredOn");
			}
			
			else
			{
				ProjectConstraints.LOG.write("Error: Could not power on " + vm.getName());
			}
		}
		
		catch (Exception e)
		{
			ProjectConstraints.LOG.write("Error: Could not power on " + vm.getName());
			ProjectConstraints.LOG.write("Exception in PowerOn command: " + e.getMessage(), true);
			ProjectConstraints.LOG.printStackTrace(e);
		}
		
		finally
		{
			engine.main.startRefreshThread();
		}
	}
	
	/**
	 * Powers off a virtual machine
	 * @param vm the virtual machine
	 */
	private void powerOff(VirtualMachine vm)
	{
		try
		{
			Task powerOff = vm.powerOffVM_Task();
			if (powerOff.waitForTask() == Task.SUCCESS)
			{
				ProjectConstraints.LOG.write(vm.getName() + " changed state to PoweredOff");
			}
			
			else
			{
				ProjectConstraints.LOG.write("Error: Could not power off " + vm.getName());
			}
		}
		
		catch (Exception e)
		{
			ProjectConstraints.LOG.write("Error: Could not power off " + vm.getName());
			ProjectConstraints.LOG.write("Exception in PowerOff command: " + e.getMessage(), true);
			ProjectConstraints.LOG.printStackTrace(e);
		}
		
		finally
		{
			engine.main.startRefreshThread();
		}
	}
	
	/**
	 * Suspends a virtual machine
	 * @param vm the virtual machine
	 */
	private void suspend(VirtualMachine vm)
	{
		try
		{
			Task suspend = vm.suspendVM_Task();
			if (suspend.waitForTask() == Task.SUCCESS)
			{
				ProjectConstraints.LOG.write(vm.getName() + " changed state to Suspended");
			}
			
			else
			{
				ProjectConstraints.LOG.write("Error: Could not suspend " + vm.getName());
			}
		}
		
		catch (Exception e)
		{
			ProjectConstraints.LOG.write("Error: Could not suspend " + vm.getName());
			ProjectConstraints.LOG.write("Exception in Suspend command: " + e.getMessage(), true);
			ProjectConstraints.LOG.printStackTrace(e);
		}
		
		finally
		{
			engine.main.startRefreshThread();
		}
	}
	
	/**
	 * Resets a virtual machine
	 * @param vm the virtual machine
	 */
	private void reset(VirtualMachine vm)
	{
		try
		{
			Task reset = vm.resetVM_Task();
			if (reset.waitForTask() == Task.SUCCESS)
			{
				ProjectConstraints.LOG.write(vm.getName() + " is Reseting");
			}
			
			else
			{
				ProjectConstraints.LOG.write("Error: Could not reset " + vm.getName());
			}
		}
		
		catch (Exception e)
		{
			ProjectConstraints.LOG.write("Error: Could not reset " + vm.getName());
			ProjectConstraints.LOG.write("Exception in Reset command: " + e.getMessage(), true);
			ProjectConstraints.LOG.printStackTrace(e);
		}
		
		finally
		{
			engine.main.startRefreshThread();
		}
	}
	
	/**
	 * Shuts down a virtual machine
	 * @param vm the virtual machine
	 */
	private void shutdown(VirtualMachine vm)
	{
		try
		{
			vm.shutdownGuest();
			ProjectConstraints.LOG.write(vm.getName() + " is shutting down");
		}
		
		catch (Exception e)
		{
			ProjectConstraints.LOG.write("Error: Could not shutdown " + vm.getName());
			ProjectConstraints.LOG.write("Exception in Shutdown command: " + e.getMessage(), true);
			ProjectConstraints.LOG.printStackTrace(e);
		}
	}
	
	/**
	 * Restarts a virtual machine
	 * @param vm the virtual machine
	 */
	private void restart(VirtualMachine vm)
	{
		try
		{
			vm.rebootGuest();
			ProjectConstraints.LOG.write(vm.getName() + " is restarting");
		}
		
		catch (Exception e)
		{
			ProjectConstraints.LOG.write("Error: Could not restart " + vm.getName());
			ProjectConstraints.LOG.write("Exception in Restart command: " + e.getMessage(), true);
			ProjectConstraints.LOG.printStackTrace(e);
		}
	}
	
	/**
	 * Renames a virtual machine
	 * @param vm the virtual machine
	 */
	private void rename(VirtualMachine vm)
	{
		try
		{
			Task rename = vm.rename_Task(name);
		
		
			if (rename.waitForTask() == Task.SUCCESS)
			{
				ProjectConstraints.LOG.write(vm.getName() + " rename successful");
			}
			
			else
			{
				ProjectConstraints.LOG.write("Error: Could not rename " + vm.getName());
			}
		}
	
		catch (Exception e)
		{
			ProjectConstraints.LOG.write("Error: Could not rename " + vm.getName());
			ProjectConstraints.LOG.write("Exception in Rename command: " + e.getMessage(), true);
			ProjectConstraints.LOG.printStackTrace(e);
		}
		
		finally
		{
			engine.main.startRefreshThread();
		}
	}
	
	/**
	 * Deletes a virtual machine
	 * @param vm the virtual machine
	 */
	private void delete(VirtualMachine vm)
	{
		try
		{
			String name = vm.getName();
			Task delete = vm.destroy_Task();
		
			if (delete.waitForTask() == Task.SUCCESS)
			{
				ProjectConstraints.LOG.write(name + " deleted");
			}
			
			else
			{
				ProjectConstraints.LOG.write("Error: Could not delete " + name);
			}
		}
	
		catch (Exception e)
		{
			ProjectConstraints.LOG.write("Error: Could not delete " + name);
			ProjectConstraints.LOG.write("Exception in Delete command: " + e.getMessage(), true);
			ProjectConstraints.LOG.printStackTrace(e);
		}
		
		finally
		{
			engine.main.startRefreshThread();
		}
	}
	
	/**
	 * Moves a specified VM to a new folder in the datacenter, referencing the VM's Team
	 * @param vm the Virtual Machine to move
	 * @param location the new folder for the Virtual Machine
	 */
	private void move(VirtualMachine vm, String location)
	{
		Folder folder = null;
		
		try
		{
			for (Object each : engine.rootDir.getChildEntity())
			{
				if (each instanceof Folder)
				{
					Folder thisFolder = (Folder) each;
					if (thisFolder.getName().equals(location))
					{
						folder = thisFolder;
					}
				}
			}
			
			if (folder == null)
			{
				engine.rootDir.createFolder(location);
				ProjectConstraints.LOG.write("Folder created for " + location, true);
				for (Object each : engine.rootDir.getChildEntity())
				{
					if (each instanceof Folder)
					{
						Folder thisFolder = (Folder) each;
						if (thisFolder.getName().equals(location))
						{
							folder = thisFolder;
						}
					}
				}
			}
			
			ManagedEntity[] thisVM = {vm};
			Task task = folder.moveIntoFolder_Task(thisVM);
			
			if (task.waitForTask() == Task.SUCCESS)
			{
				ProjectConstraints.LOG.write(vm.getName() + " changed to " + location);
			}
			
			else
			{
				ProjectConstraints.LOG.write("Error: Could not change " + vm.getName() + " to " + location);
			}
		}
		
		catch (Exception e)
		{
			ProjectConstraints.LOG.write("Exception occured while moving the virtual machine", true);
			ProjectConstraints.LOG.printStackTrace(e);
		}
		
		finally
		{
			engine.main.startRefreshThread();
		}
	}
}
