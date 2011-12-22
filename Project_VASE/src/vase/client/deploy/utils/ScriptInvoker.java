/**
 * Project_VASE Client Deploy Utility class
 */
package vase.client.deploy.utils;

import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URL;
import java.rmi.RemoteException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.net.ssl.HttpsURLConnection;

import com.vmware.vim25.FileFault;
import com.vmware.vim25.GuestFileAttributes;
import com.vmware.vim25.GuestOperationsFault;
import com.vmware.vim25.GuestProcessInfo;
import com.vmware.vim25.GuestProgramSpec;
import com.vmware.vim25.InvalidState;
import com.vmware.vim25.ManagedObjectReference;
import com.vmware.vim25.NamePasswordAuthentication;
import com.vmware.vim25.RuntimeFault;
import com.vmware.vim25.TaskInProgress;
import com.vmware.vim25.mo.GuestFileManager;
import com.vmware.vim25.mo.GuestOperationsManager;
import com.vmware.vim25.mo.GuestProcessManager;
import com.vmware.vim25.mo.VirtualMachine;

import vase.client.deploy.CommandEngine;
import vase.client.deploy.ProjectConstraints;
import vase.client.deploy.vmo.Script;

/**
 * Script Invoker class responsible for copying scripts to guest VMs, executing them, and cleaning up
 * Used during the deployment process
 * @author James McNatt & Brenton Kapral
 * @version Project_VASE
 */
public class ScriptInvoker
{
	/**
	 * Buffer array for data transfer
	 */
	public static final byte[] BUFFER = new byte[4096];
	
	/**
	 * Copies, invokes, and removes a script on a guest VM
	 * @param vm the Virtual Machine to target the script
	 * @param script the Script object
	 * @param engine the CommandEngine instance
	 * @throws IOException 
	 * @throws InterruptedException 
	 */
	public static void invoke(VirtualMachine vm, Script script, CommandEngine engine) throws IOException, InterruptedException
	{
		ManagedObjectReference guestOperationsManagerReference = engine.si.getServiceContent().getGuestOperationsManager();
		GuestOperationsManager manager = new GuestOperationsManager(engine.si.getServerConnection(), guestOperationsManagerReference);
		NamePasswordAuthentication auth = getAuthentication(script.username, script.password);
		
		//Make Directory
		makeDirectory(manager.getFileManager(vm), auth, script.scriptDestination);
		
		//Copy Script
		copyFileToGuest(vm, script, manager.getFileManager(vm), auth, engine);
		
		//Execute
		long pid = executeScript(vm, script, manager.getProcessManager(vm), auth);
		
		//Monitor
		monitorProcessUntilFinished(manager.getProcessManager(vm), auth, pid);
		
		//Read Input
	}
	
	/**
	 * Check to see if the process is still active.  If not, terminate loop.
	 * @param processManager
	 * @param auth
	 * @param pid
	 * @throws GuestOperationsFault
	 * @throws InvalidState
	 * @throws TaskInProgress
	 * @throws RuntimeFault
	 * @throws RemoteException
	 * @throws InterruptedException 
	 */
	public static void monitorProcessUntilFinished(GuestProcessManager processManager, NamePasswordAuthentication auth, long pid) throws GuestOperationsFault, 
		InvalidState, TaskInProgress, RuntimeFault, RemoteException, InterruptedException
	{
		GuestProcessInfo[] proc = processManager.listProcessesInGuest(auth, null);
		int count = 0;
		while (count < 50)
		{
			boolean exists = false;
			
			for (int i = 0; i < proc.length; i++)
			{
				if (proc[i].getPid() == pid) exists = true;
			}
			
			Thread.sleep(5000);
			if (exists) count++;
			else count = 51;
		}
	}
	
	/**
	 * Builds the Authentication token used to authenticate to the Guest VM
	 * @param username Guest VM username
	 * @param password Guest VM password
	 * @return the NamePasswordAuthentication instance
	 */
	public static NamePasswordAuthentication getAuthentication(String username, String password)
	{
		NamePasswordAuthentication auth = new NamePasswordAuthentication();
		auth.setUsername(username);
		auth.setPassword(password);
		
		return auth;
	}
	
	/**
	 * Creates a directory on the Guest VM
	 * @param fileManager
	 * @param auth
	 * @param dirName
	 * @throws GuestOperationsFault
	 * @throws InvalidState
	 * @throws TaskInProgress
	 * @throws FileFault
	 * @throws RuntimeFault
	 * @throws RemoteException
	 */
	public static void makeDirectory(GuestFileManager fileManager, NamePasswordAuthentication auth, String dirName) throws GuestOperationsFault, 
		InvalidState, TaskInProgress, FileFault, RuntimeFault, RemoteException
	{
		fileManager.makeDirectoryInGuest(auth, dirName, false);
	}
	
	/**
	 * Copies the script onto the guest VM
	 * @param script
	 * @param auth
	 * @throws IOException 
	 */
	public static void copyFileToGuest(VirtualMachine vm, Script script, GuestFileManager fileManager, NamePasswordAuthentication auth, CommandEngine engine) throws IOException
	{
		//Get Script Path + Make File
		String filePath = getOsSpecificDestinationPath(script.scriptDestination, script.script, script.scriptType);
		File src = new File(ProjectConstraints.SCRIPT_PATH + "\\" + script.script);
		
		//Get Full Path
		String url = fileManager.initiateFileTransferToGuest(auth, filePath, new GuestFileAttributes(), src.length(), true);
		String hostAddress = engine.getHostServiceConsoleIpAddress(vm);
		String fullUrl = getFileTransferPath(hostAddress, url);
		URL transferUrl = new URL(fullUrl);
		
		//Transfer via HTTPS
		HttpsURLConnection httpc = (HttpsURLConnection) transferUrl.openConnection();
		httpc.setDoInput(true);
		httpc.setDoOutput(true);
		httpc.setRequestMethod("PUT");
		ByteArrayOutputStream outputStream = (ByteArrayOutputStream) httpc.getOutputStream();
		DataInputStream inputStream = new DataInputStream(new FileInputStream(src));
		
		int length;
		while ((length = inputStream.read()) != -1)
		{
			outputStream.write(BUFFER, 0, length);
		}
		
		outputStream.flush();
		httpc.getInputStream().close();
		outputStream.close();
		inputStream.close();
	}
	
	public static long executeScript(VirtualMachine vm, Script script, GuestProcessManager processManager, NamePasswordAuthentication auth) throws GuestOperationsFault, 
		InvalidState, TaskInProgress, FileFault, RuntimeFault, RemoteException
	{
		GuestProgramSpec spec = new GuestProgramSpec();
		spec.setArguments(script.args);
		spec.setProgramPath(script.path);
		long[] pid = {processManager.startProgramInGuest(auth, spec)};
		
		return pid[0];
	}
	
	
	/**
	 * Gets the full transfer path to the Guest VM via HTTPS
	 * @param address
	 * @param url
	 * @return
	 */
	public static String getFileTransferPath(String address, String url)
	{
		Pattern pattern = Pattern.compile(Pattern.quote("*"));
		Matcher matcher = pattern.matcher(url);
		return matcher.replaceFirst(address);
	}
	
	/**
	 * Gets the full path based on the Script type, which is OS specific
	 * @param directory
	 * @param fileName
	 * @param type
	 * @return the full path of the script
	 */
	public static String getOsSpecificDestinationPath(String directory, String fileName, String type)
	{
		String path;
		
		if (type.equals(Script.BAT_SCRIPTTYPE) || type.equals(Script.POWERSHELL_SCRIPTTYPE))
		{
			path = directory + "\\" + fileName;
		}
		
		else
		{
			path = directory + "/" + fileName;
		}
		
		return path;
	}
}
