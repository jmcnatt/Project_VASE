/**
 * Project_VASE Deploy package
 */
package vase.client.deploy;

import java.rmi.RemoteException;
import java.util.ArrayList;

import vase.client.ThreadExt;

import com.vmware.vim25.NoPermission;
import com.vmware.vim25.VirtualDevice;
import com.vmware.vim25.VirtualDeviceConfigSpec;
import com.vmware.vim25.VirtualDeviceConfigSpecOperation;
import com.vmware.vim25.VirtualEthernetCard;
import com.vmware.vim25.VirtualEthernetCardNetworkBackingInfo;
import com.vmware.vim25.VirtualHardware;
import com.vmware.vim25.VirtualMachineCloneSpec;
import com.vmware.vim25.VirtualMachineConfigSpec;
import com.vmware.vim25.VirtualMachineRelocateSpec;
import com.vmware.vim25.mo.Folder;
import com.vmware.vim25.mo.InventoryNavigator;
import com.vmware.vim25.mo.ManagedEntity;
import com.vmware.vim25.mo.Network;
import com.vmware.vim25.mo.Task;
import com.vmware.vim25.mo.VirtualMachine;

/**
 * Deployment Thread in the CommandEngine, responsible for deploying VMs and installing
 * services based on the GuiDeploymentWizard results
 * @author James McNatt & Brenton Kapral
 * @version Project_VASE Deploy
 */
public class DeployThread extends ThreadExt
{
	private ArrayList<DeployedVirtualMachine> deployment;
	private CommandEngine engine;
	
	public DeployThread(ArrayList<DeployedVirtualMachine> deployment, CommandEngine engine)
	{
		this.deployment = deployment;
		this.engine = engine;
	}
	
	/**
	 * Checks to see if the team has a folder.
	 * <br />
	 * If not, creates a new Folder in the rootDir (Main Project Dir specified
	 * in vase.conf) and then returns that folder.  If the team is not set, returns
	 * the rootDir and the VM will be cloned accordingly.
	 * @param team the team name/folder
	 * @return the Folder to place the new VM
	 */
	public Folder getLocation(String team)
	{
		Folder folder = null;
		
		if (!team.equals("< Not Set >"))
		{
			try
			{
				for (Object each : engine.rootDir.getChildEntity())
				{
					if (each instanceof Folder)
					{
						Folder thisFolder = (Folder) each;
						if (thisFolder.getName().equals(team))
						{
							folder = thisFolder;
						}
					}
				}
				
				if (folder == null)
				{
					engine.rootDir.createFolder(team);
					ProjectConstraints.LOG.write("Folder created for " + team, true);
					for (Object each : engine.rootDir.getChildEntity())
					{
						if (each instanceof Folder)
						{
							Folder thisFolder = (Folder) each;
							if (thisFolder.getName().equals(team))
							{
								folder = thisFolder;
							}
						}
					}
				}
			}
			
			catch (RemoteException e)
			{
				engine.disconnect();
				ProjectConstraints.LOG.printStackTrace(e);
				
				e.printStackTrace();
			}
			
			catch (Exception e)
			{
				ProjectConstraints.LOG.write("Exception occured while accessing team folder", true);
				ProjectConstraints.LOG.printStackTrace(e);
				
				e.printStackTrace();
			}
		}
		
		else
		{
			folder = engine.rootDir;
		}
		
		return folder;
	}
	
	/**
	 * Sets a virtual machine's network interface card to a certain Network in the datacenter
	 * @param vm the virtual machine to set
	 * @param networkName the name of the Network to join
	 * @see VirtualMachine#getConfig()
	 * @see DeployVirtualMachine
	 * @see Network
	 * @see VirtualMachineConfigSpec
	 */
	private void setVirtualMachineNetwork(VirtualMachine vm, String networkName)
	{
		ArrayList<VirtualEthernetCard> nics = new ArrayList<VirtualEthernetCard>();
		
		try
		{
			//Get the virtual nics
			VirtualHardware hardware = vm.getConfig().hardware;
			VirtualDevice[] devices = hardware.getDevice();
			
			for (VirtualDevice each : devices)
			{
				if (each instanceof VirtualEthernetCard)
				{
					nics.add((VirtualEthernetCard) each);
				}
			}
			
			for (VirtualEthernetCard each : nics)
			{
				VirtualEthernetCardNetworkBackingInfo backing = (VirtualEthernetCardNetworkBackingInfo) each.getBacking();
				Network network = engine.getNetworkFromString(networkName);
				
				if (network != null)
				{
					backing.setNetwork(network.getMOR());
					backing.setDeviceName(network.getName());
					each.setBacking(backing);
					VirtualDeviceConfigSpec vdcs = new VirtualDeviceConfigSpec();
					vdcs.setOperation(VirtualDeviceConfigSpecOperation.edit);
					vdcs.setDevice(each);
					VirtualMachineConfigSpec vmcs = new VirtualMachineConfigSpec();
					vmcs.setDeviceChange(new VirtualDeviceConfigSpec[] {vdcs});
					
					Task changeNetwork = vm.reconfigVM_Task(vmcs);
					if (changeNetwork.waitForTask() == Task.SUCCESS)
					{
						ProjectConstraints.LOG.write(vm.getName() + " changed to network \"" + network.getName() + "\"", true);
					}
					
					else
					{
						ProjectConstraints.LOG.write(vm.getName() + " could not be changed to \"" + network.getName() + "\"", true);
					}
				}
				
				else
				{
					ProjectConstraints.LOG.write("Error in changing network label.  The network does not exist", true);
				}
			}
		}
		
		catch (Exception e)
		{
			ProjectConstraints.LOG.write("Error occured while setting the VM network");
			ProjectConstraints.LOG.printStackTrace(e);
			
			e.printStackTrace();
		}
	}		
	
	/**
	 * Run method from Thread
	 * <br />
	 * Deploys the collection of DeployedVirtualMachine objects
	 */
	public void run()
	{
		for (DeployedVirtualMachine newVM : deployment)
		{
			boolean cloneSuccessful = true;
			
			try
			{
				String templateName = newVM.getOsName();
				String guestName = newVM.getVmName();
				ManagedEntity selectedTemplate = new InventoryNavigator(engine.templateDir).searchManagedEntity("VirtualMachine", templateName);
				VirtualMachine vm = (VirtualMachine) selectedTemplate;
				VirtualMachineCloneSpec spec = new VirtualMachineCloneSpec();
				VirtualMachineRelocateSpec relocate = new VirtualMachineRelocateSpec();
				
				try
				{
					relocate.setDatastore(engine.targetDatastore.getMOR());
				}
				
				catch (NullPointerException e)
				{
					ProjectConstraints.LOG.write("Invalid datastore specified. Check deploy.conf and restart VASE Deploy");
				}
				
				spec.setLocation(relocate);
				spec.setPowerOn(true);
				spec.setTemplate(false);
				
				//Step 1 - Deploy
				ProjectConstraints.LOG.write("Creating " + guestName);
				Task task = vm.cloneVM_Task(getLocation(newVM.getTeam()), guestName, spec);
				
				if (task.waitForTask() == Task.SUCCESS)
				{
					ProjectConstraints.LOG.write(guestName + " created successfully");
				}
				
				else
				{
					ProjectConstraints.LOG.write("Error: Clone of Template \"" + templateName + "\" was not successful.\n" +
							"Deployment Wizard VM \"" + guestName + "\"", true);
					cloneSuccessful = false;
				}
				
				if (cloneSuccessful)
				{
					//Re-assign local variables to the updated VM that was just created
					selectedTemplate = new InventoryNavigator(engine.rootDir).searchManagedEntity("VirtualMachine", guestName);
					vm = (VirtualMachine) selectedTemplate;
					
					//Step 2 - assign the vm to the virtual network
					setVirtualMachineNetwork(vm, newVM.getNetwork());
					
					//Step 3 - Generate Scripts
					newVM.compileScripts(engine);
					
					//Step 4 - Run them
					int count = 0;
					sleep(5000);
					for (Script script : newVM.scripts)
					{
						//Wait until it powers up and VMware Tools is ready
						do
						{
							sleep(5000);
						} while (vm.getSummary().guest.toolsStatus.toString().equalsIgnoreCase("toolsNotRunning"));
						
						ProjectConstraints.LOG.write("Ready to copy script onto " + guestName, true);
						script.invoke();
						
						//First script requires a reboot, set sleep time accordingly
						if (count == 0) sleep(30000);							
						else sleep(5000);
						count++;
					}
					
					//save the team number in the map
					ProjectConstraints.TEAMS.put(newVM.getVmName(), newVM.getTeam());
				}
				
				ProjectConstraints.LOG.write(newVM.getVmName() + " deployed successfully");
			}
			
			catch (ArrayIndexOutOfBoundsException e)
			{
				ProjectConstraints.LOG.write("Error: Template Not Found after conclusion of Deployment Wizard");
				ProjectConstraints.LOG.printStackTrace(e);
				
				e.printStackTrace();
			}
			
			catch (NoPermission e)
			{
				ProjectConstraints.LOG.write("Error: Permission to deploy virtual machine denied");
				ProjectConstraints.LOG.printStackTrace(e);
				
				e.printStackTrace();
			}
			
			catch (RemoteException e)
			{
				engine.disconnect();
				ProjectConstraints.LOG.printStackTrace(e);
				
				e.printStackTrace();
			}
			
			catch (Exception e)
			{
				ProjectConstraints.LOG.write("Exception caught in deployment", true);
				ProjectConstraints.LOG.printStackTrace(e);
				
				e.printStackTrace();
			}
		}
		
		ProjectConstraints.LOG.write("Deployment complete");
		engine.main.startRefreshThread();
	}
}