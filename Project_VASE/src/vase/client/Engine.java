/**
 * Project_VASE Client package
 */
package vase.client;

import java.io.IOException;

import javax.swing.JOptionPane;

import vase.client.thread.ProcessErrorThread;
import vase.client.thread.ProcessInputThread;

import com.vmware.vim25.HostNetworkInfo;
import com.vmware.vim25.HostVirtualNic;
import com.vmware.vim25.ManagedObjectReference;
import com.vmware.vim25.mo.Datacenter;
import com.vmware.vim25.mo.HostSystem;
import com.vmware.vim25.mo.ManagedEntity;
import com.vmware.vim25.mo.ServiceInstance;
import com.vmware.vim25.mo.VirtualMachine;
import com.vmware.vim25.mo.util.MorUtil;

/**
 * Engine superclass providing methods and archicture to each application's
 * sub engine. This class handles calls to modify or control vCenter objects
 * such as VirtualMachine, Folder, Datacenter, vApp, and Template objects.
 * 
 * @author James McNatt & Brenton Kapral
 * @version Project_VASE
 */
public class Engine
{
	/**
	 * Server Instance handling the connection to VCENTER
	 * @see ServiceInstance
	 */
	public ServiceInstance si;
	
	/**
	 * Current server in which this engine interfaces
	 */
	private String currentServer;
	
	/**
	 * Current user logged on
	 */
	private String currentUsername;
	
	/**
	 * Stored password for the user logged on
	 */
	private String currentPassword;
	
	/**
	 * The engine's Datacenter
	 * @see Datacenter
	 */
	public Datacenter dc;
	
	
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
	 * @param si the ServiceInstance
	 */
	public Engine(ServiceInstance si)
	{
		this.si = si;
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
	 * Launches the VM's console
	 * <br />
	 * If the VM has been removed or is no longer reachable, Exception will be thrown and a dialog box
	 * will appear to inform the user.  Output of the process is appended to the log file.
	 * @param vm the virtual machine
	 * @param log the LogWriter instance
	 * @param parent the parent window
	 * @param vmrcPath path to the VMRC program
	 * @param hostUser ESX/ESXi host username authorized to launch the console
	 * @param hostPassword the host username's password
	 * @param fullScreen whether or not to launch in full screen
	 */
	public void launchConsole(VirtualMachine vm, LogWriter log, Window parent, String vmrcPath, String hostUser, 
			String hostPassword, boolean fullScreen)
	{
		try
		{
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
			String pathToVMRC = System.getenv("ProgramFiles") + "\\" + vmrcPath;
			String args = " -h " + ipAddress + " -u " + hostUser + " -p " + hostPassword;
			
			//Full Screen
			if (fullScreen)
			{
				args += " -X ";
			}
			
			else
			{
				args += " ";
			}
			
			//VM Datastore and VMX file location
			args += "-m \"" + vmxPath + "\"";
			
			log.write("Launching console for " + vm.getName() + ".  Please wait...");
			Process p = Runtime.getRuntime().exec(pathToVMRC + args);
			
			Thread inputThread = new ProcessInputThread(p.getInputStream(), log);
			Thread errorThread = new ProcessErrorThread(p.getErrorStream(), log);
			
			inputThread.start();
			errorThread.start();
			p.getOutputStream().close();			
		}
		
		catch (NullPointerException e)
		{
			JOptionPane.showMessageDialog(parent, "Error: Could not access information related to the Virtual Machine Host", 
					"Error", JOptionPane.ERROR_MESSAGE);
			log.printStackTrace(e);
		}
		
		catch (IOException e)
		{
			JOptionPane.showMessageDialog(parent, "Error: Could not execute VMRC. Please check the path in the configuration file",
					"Error", JOptionPane.ERROR_MESSAGE);
			log.printStackTrace(e);
		}
		
		catch (Exception e)
		{
			JOptionPane.showMessageDialog(parent, "Error: Could not locate virtual machine in the datacenter", "Error", JOptionPane.ERROR_MESSAGE);
			log.printStackTrace(e);
		}
	}
}
