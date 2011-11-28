/**
 * Project_VASE Connect package
 */
package vase.client.connect;

import vase.client.thread.ThreadExt;

import com.vmware.vim25.mo.Task;
import com.vmware.vim25.mo.VirtualMachine;

/**
 * Thread to handle power operations for VirtualMachines
 * <br />
 * Pass in a VirtualMachineExt object from which the VMware VirtualMachine
 * can be extracted
 * @author James McNatt
 * @version Project_VASE 0.1.0
 */
class CommandOperationsThread extends ThreadExt
{
	private VirtualMachine vm;
	private int command;
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
				engine.main.startRefreshThread();
			}
			
			else
			{
				ProjectConstraints.LOG.write("Error: Could not power on " + vm.getName());
			}
		}
		
		catch (Exception e)
		{
			ProjectConstraints.LOG.write("Error: Could not power on " + vm.getName());
			ProjectConstraints.LOG.write("Exception in PowerOn command: " + e.getMessage(), false);
			ProjectConstraints.LOG.printStackTrace(e);
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
				engine.main.startRefreshThread();
			}
			
			else
			{
				ProjectConstraints.LOG.write("Error: Could not power off " + vm.getName());
			}
		}
		
		catch (Exception e)
		{
			ProjectConstraints.LOG.write("Error: Could not power off " + vm.getName());
			ProjectConstraints.LOG.write("Exception in PowerOff command: " + e.getMessage(), false);
			ProjectConstraints.LOG.printStackTrace(e);
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
				engine.main.startRefreshThread();
			}
			
			else
			{
				ProjectConstraints.LOG.write("Error: Could not suspend " + vm.getName());
			}
		}
		
		catch (Exception e)
		{
			ProjectConstraints.LOG.write("Error: Could not suspend " + vm.getName());
			ProjectConstraints.LOG.write("Exception in Suspend command: " + e.getMessage(), false);
			ProjectConstraints.LOG.printStackTrace(e);
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
				engine.main.startRefreshThread();
			}
			
			else
			{
				ProjectConstraints.LOG.write("Error: Could not reset " + vm.getName());
			}
		}
		
		catch (Exception e)
		{
			ProjectConstraints.LOG.write("Error: Could not reset " + vm.getName());
			ProjectConstraints.LOG.write("Exception in Reset command: " + e.getMessage(), false);
			ProjectConstraints.LOG.printStackTrace(e);
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
			ProjectConstraints.LOG.write("Exception in Shutdown command: " + e.getMessage(), false);
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
			ProjectConstraints.LOG.write("Exception in Restart command: " + e.getMessage(), false);
			ProjectConstraints.LOG.printStackTrace(e);
		}
	}
}