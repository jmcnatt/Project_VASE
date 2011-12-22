/**
 * Project_VASE Deploy package
 */
package vase.client.deploy;

import vase.client.Engine;
import vase.client.thread.EngineOperationsThread;

import com.vmware.vim25.DuplicateName;
import com.vmware.vim25.InvalidFolder;
import com.vmware.vim25.InvalidName;
import com.vmware.vim25.InvalidState;
import com.vmware.vim25.ToolsUnavailable;
import com.vmware.vim25.VimFault;
import com.vmware.vim25.mo.Folder;
import com.vmware.vim25.mo.VirtualMachine;

/**
 * Thread to handle power operations for VirtualMachines
 * Commands called by the CommandEngine and then executed in this Thread
 * <br />
 * Logic handled by superclass EngineOperationsThread
 * @author James McNatt & Brenton Kapral
 * @version Project_VASE Deploy
 * @see EngineOperationsThread
 */
public class CommandOperationsThread extends EngineOperationsThread
{
	private CommandEngine engine;
	
	/**
	 * Main Constructor
	 * Calls superclass's constructor
	 * @param vm the Virtual Machine object
	 * @param command integer representation of the command
	 * @param engine the CommandEngine instance
	 */
	public CommandOperationsThread(VirtualMachine vm, int command, CommandEngine engine)
	{
		super(vm, command, ProjectConstraints.LOG);
		this.engine = engine;
	}
	
	/**
	 * Overloaded constructor for renaming a virtual machine or moving a virtual machine
	 * @param vm the Virtual Machine object
	 * @param command integer representation of the command
	 * @param name the new name of the virtual machine
	 */
	public CommandOperationsThread(VirtualMachine vm, int command, CommandEngine engine, String name)
	{
		super(vm, command, ProjectConstraints.LOG, name);
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
			case Engine.POWER_ON:
			{
				powerOn();
				break;
			}
			
			case Engine.POWER_OFF:
			{
				powerOff();
				break;
			}
			
			case Engine.SUSPEND:
			{
				suspendVM();
				break;
			}
			
			case Engine.RESET:
			{
				reset();
				break;
			}
			
			case Engine.SHUTDOWN:
			{
				shutdown();
				break;
			}
			
			case Engine.RESTART:
			{
				restart();
				break;
			}
			
			case Engine.DELETE:
			{
				delete();
				break;
			}
			
			case Engine.RENAME:
			{
				rename();
				break;
			}
			
			case Engine.MOVE:
			{
				setTeam();
				break;
			}
		}
	}
	
	/**
	 * Powers on a Virtual Machine
	 */
	public void powerOn()
	{
		try
		{
			super.powerOn();
		}
		
		catch (InvalidState e)
		{
			log.write("Error: " + vm.getName() + " not in proper state to be Powered On");
		}
		
		catch (Exception e)
		{
			log.write("Error: Could not power off " + vm.getName());
			log.printStackTrace(e);
		}
		
		finally
		{		
			engine.main.startRefreshThread();
		}		
	}
	
	/**
	 * Powers off a virtual machine
	 */
	public void powerOff()
	{
		try
		{
			super.powerOff();
		}
		
		catch (InvalidState e)
		{
			log.write("Error: " + vm.getName() + " not in proper state to be Powered Off");
		}
		
		catch (Exception e)
		{
			log.write("Error: Could not power off " + vm.getName());
			log.printStackTrace(e);
		}
		
		finally
		{
			engine.main.startRefreshThread();
		}
	}
	
	/**
	 * Suspends a virtual machine
	 */
	public void suspendVM()
	{
		try
		{
			super.suspendVM();
		}
		
		catch (InvalidState e)
		{
			log.write("Error: " + vm.getName() + " not in proper state to be Suspended");
		}
		
		catch (Exception e)
		{
			log.write("Error: Could not suspend " + vm.getName());
			log.printStackTrace(e);
		}
		
		finally
		{
			engine.main.startRefreshThread();
		}
	}
	
	/**
	 * Resets a virtual machine
	 */
	public void reset()
	{
		try
		{
			super.reset();
		}
		
		catch (InvalidState e)
		{
			log.write("Error: " + vm.getName() + " not in proper state to be Reset");
		}
		
		catch (Exception e)
		{
			log.write("Error: Could not reset " + vm.getName());
			log.printStackTrace(e);
		}
		
		finally
		{
			engine.main.startRefreshThread();
		}
	}
	
	/**
	 * Shuts down a virtual machine
	 */
	public void shutdown()
	{
		try
		{
			super.shutdown();
		}
		
		catch (InvalidState e)
		{
			log.write("Error: " + vm.getName() + " not in proper state to be Shut Down");
		}
		
		catch (ToolsUnavailable e)
		{
			log.write("Error in shutting down " + vm.getName() + ". Could not communicate with VMware Tools");
		}
		
		catch (Exception e)
		{
			log.write("Error: Could not shutdown " + vm.getName());
			log.printStackTrace(e);
		}
	}
	
	/**
	 * Restarts a virtual machine
	 */
	public void restart()
	{
		try
		{
			super.reboot();
		}
		
		catch (InvalidState e)
		{
			log.write("Error: " + vm.getName() + " not in proper state to be Rebooted");
		}
		
		catch (ToolsUnavailable e)
		{
			log.write("Error in rebooting " + vm.getName() + ". Could not communicate with VMware Tools");
		}
		
		catch (Exception e)
		{
			log.write("Error: Could not reboot " + vm.getName());
			log.printStackTrace(e);
		}
	}
	
	/**
	 * Renames a virtual machine
	 */
	public void rename()
	{
		try
		{
			super.rename();
		}
		
		catch (InvalidName e)
		{
			log.write("Error: " + vm.getName() + " not a valid name");
		}
		
		catch (DuplicateName e)
		{
			log.write("Error: " + vm.getName() + " is a duplicate name in the inventory");
		}
	
		catch (Exception e)
		{
			log.write("Error: Could not rename " + vm.getName());
			log.printStackTrace(e);
		}
		
		finally
		{
			engine.main.startRefreshThread();
		}
	}
	
	/**
	 * Deletes a virtual machine
	 */
	public void delete()
	{
		try
		{
			super.delete();
		}
		
		catch (VimFault e)
		{
			log.write("Error deleting " + vm.getName() + ". Resource in use");
		}
	
		catch (Exception e)
		{
			log.write("Error: Could not delete " + name);
			log.printStackTrace(e);
		}
		
		finally
		{
			engine.main.startRefreshThread();
		}
	}
	
	/**
	 * Moves a specified VM to a new folder in the <PROJECT_DIR>, representing the VM's Team
	 */
	public void setTeam()
	{
		Folder folder = null;
		
		try
		{
			for (Object each : engine.rootDir.getChildEntity())
			{
				if (each instanceof Folder)
				{
					Folder thisFolder = (Folder) each;
					if (thisFolder.getName().equals(name))
					{
						folder = thisFolder;
					}
				}
			}
			
			if (folder == null)
			{
				engine.rootDir.createFolder(name);
				ProjectConstraints.LOG.write("Folder created for " + name, true);
				for (Object each : engine.rootDir.getChildEntity())
				{
					if (each instanceof Folder)
					{
						Folder thisFolder = (Folder) each;
						if (thisFolder.getName().equals(name))
						{
							folder = thisFolder;
						}
					}
				}
			}
			
			super.setTeam(folder);
		}
		
		catch (InvalidState e)
		{
			log.write("Error: " + vm.getName() + " not in proper state to be moved to " + name);
		}
		
		catch (DuplicateName e)
		{
			log.write("Error in moving " + vm.getName() + ". Duplicate name in destination directory");
		}
		
		catch (InvalidFolder e)
		{
			log.write("Error: Invalid folder specification");
			log.printStackTrace(e);
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
