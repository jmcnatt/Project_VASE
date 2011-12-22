/**
 * Project_VASE client Thread package
 */
package vase.client.thread;

import java.rmi.RemoteException;

import vase.client.LogWriter;

import com.vmware.vim25.DuplicateName;
import com.vmware.vim25.FileFault;
import com.vmware.vim25.InsufficientResourcesFault;
import com.vmware.vim25.InvalidFolder;
import com.vmware.vim25.InvalidName;
import com.vmware.vim25.InvalidState;
import com.vmware.vim25.RuntimeFault;
import com.vmware.vim25.TaskInProgress;
import com.vmware.vim25.ToolsUnavailable;
import com.vmware.vim25.VimFault;
import com.vmware.vim25.VmConfigFault;
import com.vmware.vim25.mo.Folder;
import com.vmware.vim25.mo.ManagedEntity;
import com.vmware.vim25.mo.Task;
import com.vmware.vim25.mo.VirtualMachine;

/**
 * Superclass for performing Virtual Machine operations
 * Inherit this class for more specific power operation threads
 * @author James McNatt & Brenton Kapral
 * @version Project_VASE
 */
public class EngineOperationsThread extends ThreadExt
{
	/**
	 * The Virtual Machine in which operations will be performed
	 */
	public VirtualMachine vm;
	
	/**
	 * The Integer representation of the command
	 * @see Engine
	 */
	public int command;
	
	/**
	 * LogWriter instance
	 */
	public LogWriter log;
	
	/**
	 * String of the new name for a Virtual Machine if the command is "rename"
	 */
	public String name;
	
	/**
	 * Main constructor used for all commands except renaming a Virtual Machine
	 * @param vm the Virtual Machine object
	 * @param command integer representation of the command to use
	 * @log the LogWriter instance
	 * @see Engine
	 */
	public EngineOperationsThread(VirtualMachine vm, int command, LogWriter log)
	{
		this.vm = vm;
		this.command = command;
		this.log = log;
	}
	
	/**
	 * Overloaded Constructor used to rename a Virtual Machine
	 * @param vm the Virtual Machine object
	 * @param command integer respresentation of the command to use
	 * @param log the LogWriter instance
	 * @param name the name to replace the current Virtual Machine's name
	*/
	public EngineOperationsThread(VirtualMachine vm, int command, LogWriter log, String name)
	{
		this.vm = vm;
		this.command = command;
		this.log = log;
		this.name = name;
	}
	
	/**
	 * Powers on a Virtual Machine
	 * @throws VmConfigFault
	 * @throws TaskInProgress
	 * @throws FileFault
	 * @throws InvalidState
	 * @throws InsufficientResourcesFault
	 * @throws RuntimeFault
	 * @throws RemoteException
	 * @throws InterruptedException
	 */
	public void powerOn() throws VmConfigFault, TaskInProgress, FileFault, InvalidState, InsufficientResourcesFault, 
		RuntimeFault, RemoteException, InterruptedException
	{
		Task powerOn = vm.powerOnVM_Task(null);
		
		if (powerOn.waitForTask() == Task.SUCCESS)
		{
			log.write(vm.getName() + " changed state to PoweredOn");
		}
		
		else
		{
			log.write("Error: Could not power on " + vm.getName());
		}
	}
	
	/**
	 * Powers off a Virtual Machine
	 * @throws TaskInProgress
	 * @throws InvalidState
	 * @throws RuntimeFault
	 * @throws RemoteException
	 * @throws InterruptedException
	 */
	public void powerOff() throws TaskInProgress, InvalidState, RuntimeFault, RemoteException, InterruptedException
	{
		Task powerOff = vm.powerOffVM_Task();
		
		if (powerOff.waitForTask() == Task.SUCCESS)
		{
			log.write(vm.getName() + " changed state to PoweredOff");
		}
		
		else
		{
			log.write("Error: Could not power off " + vm.getName());
		}
	}
	
	/**
	 * Suspends a Virtual Machine
	 * @throws TaskInProgress
	 * @throws InvalidState
	 * @throws RuntimeFault
	 * @throws RemoteException
	 * @throws InterruptedException
	 */
	public void suspendVM() throws TaskInProgress, InvalidState, RuntimeFault, RemoteException, InterruptedException
	{
		Task suspend = vm.suspendVM_Task();
		
		if (suspend.waitForTask() == Task.SUCCESS)
		{
			log.write(vm.getName() + " changed state to Suspended");
		}
		
		else
		{
			log.write("Error: Could not suspend " + vm.getName());
		}
	}
	
	/**
	 * Resets a Virtual Machine
	 * @throws TaskInProgress
	 * @throws InvalidState
	 * @throws RuntimeFault
	 * @throws RemoteException
	 * @throws InterruptedException
	 */
	public void reset() throws TaskInProgress, InvalidState, RuntimeFault, RemoteException, InterruptedException
	{
		Task reset = vm.resetVM_Task();
		
		if (reset.waitForTask() == Task.SUCCESS)
		{
			log.write(vm.getName() + " is Reseting");
		}
		
		else
		{
			log.write("Error: Could not reset " + vm.getName());
		}
	}
	
	/**
	 * Shuts down a virtual machine
	 * @throws TaskInProgress
	 * @throws InvalidState
	 * @throws ToolsUnavailable
	 * @throws RuntimeFault
	 * @throws RemoteException
	 */
	public void shutdown() throws TaskInProgress, InvalidState, ToolsUnavailable, RuntimeFault, RemoteException
	{
		vm.shutdownGuest();
		log.write(vm.getName() + " is Shutting Down");
	}
	
	/**
	 * Reboots a virtual machine
	 * @throws TaskInProgress
	 * @throws InvalidState
	 * @throws ToolsUnavailable
	 * @throws RuntimeFault
	 * @throws RemoteException
	 */
	public void reboot() throws TaskInProgress, InvalidState, ToolsUnavailable, RuntimeFault, RemoteException
	{
		vm.rebootGuest();
		log.write(vm.getName() + " is rebooting");
	}
	
	/**
	 * Renames a virtual machine
	 * @throws InvalidName
	 * @throws DuplicateName
	 * @throws RuntimeFault
	 * @throws RemoteException
	 * @throws InterruptedException
	 */
	public void rename() throws InvalidName, DuplicateName, RuntimeFault, RemoteException, InterruptedException
	{
		Task rename = vm.rename_Task(name);
		
		if (rename.waitForTask() == Task.SUCCESS)
		{
			log.write(vm.getName() + " rename successful");
		}
		
		else
		{
			log.write("Error: Could not rename " + vm.getName());
		}
	}
	
	/**
	 * Deletes a virtual machine
	 * @throws VimFault
	 * @throws RuntimeFault
	 * @throws RemoteException
	 * @throws InterruptedException
	 */
	public void delete() throws VimFault, RuntimeFault, RemoteException, InterruptedException
	{
		String name = vm.getName();
		Task delete = vm.destroy_Task();
	
		if (delete.waitForTask() == Task.SUCCESS)
		{
			log.write(name + " deleted");
		}
		
		else
		{
			log.write("Error: Could not delete " + name);
		}
	}
	
	/**
	 * Moved a virtual machine to a Folder object
	 * @param folder the folder in which to move the virtual machine
	 * @throws DuplicateName
	 * @throws InvalidState
	 * @throws InvalidFolder
	 * @throws RuntimeFault
	 * @throws RemoteException
	 * @throws InterruptedException
	 */
	public void setTeam(Folder folder) throws DuplicateName, InvalidState, InvalidFolder, RuntimeFault, RemoteException, InterruptedException
	{
		ManagedEntity[] thisVM = {vm};
		Task task = folder.moveIntoFolder_Task(thisVM);
		
		if (task.waitForTask() == Task.SUCCESS)
		{
			log.write(vm.getName() + " changed to " + name);
		}
		
		else
		{
			log.write("Error: Could not change " + vm.getName() + " to " + name);
		}
	}
}
