/**
 * Project_VASE Deploy
 */
package vase.client.deploy;

import java.util.Map;

/**
 * Interface used for any variable that is required by multiple classes and read
 * through the ConfigReader or hard coded.
 * <p>GUI-related classes should be placed in the GuiConstraints interface
 * @author James McNatt & Brenton Kapral
 * @version Project_VASE Deploy
 */
public interface ProjectConstraints
{
	//Project Information
	String AUTHOR = "James McNatt & Brenton Kapral";
	String VERSION = "1.0.0";
	String BUILD = "400";
	String STAGE = "Alpha";
	
	//Main CONF file location
	String CONFIG_FILE_LOCATION = "deploy.conf";
	
	//Settings file location
	String SETTINGS_FILE_LOCATION = System.getenv("APPDATA") + "\\Project_VASE\\vase-deploy-settings.bin";
	
	//Log file location
	String LOG_FILE_LOCATION = System.getenv("APPDATA") + "\\Project_VASE\\vase-deploy-events.log";
	
	//Config Reader
	ConfigReader CONFIG = new ConfigReader();
	
	//Log Writer
	LogWriter LOG = new LogWriter();
	
	//Settings Reader
	SettingsReader SETTINGS_READER = new SettingsReader();
	Map<String, String> TEAMS = SETTINGS_READER.getTeamMap();
	
	//Global Variables via the ConfigReader (vase.conf)
	String DATACENTER = CONFIG.getDatacenterName();								//ESX Datacenter name containing Project_VASE
	String TEMPLATE_FOLDER = CONFIG.getTemplateDir();							//Folder containing the Templates
	String ROOT_FOLDER = CONFIG.getProjectDir();								//Folder containing the root for deployments
	String TARGET_DATASTORE = CONFIG.getTargetDatastore();						//Target Datastore for new Deployments
	int MAX_VM_DEPLOY = CONFIG.getMaximumDeployment();							//Maximum number of VMs in one deployment
	int ACCOUNT_CSV_COUNT = CONFIG.getNumAccountFiles();						//Number of Account CSV Files
	int REFRESH_INTERVAL = CONFIG.getRefreshInterval();							//Refresh interval
	String[] TEAM_NAMES = CONFIG.getTeams();									//Collection of team names
	String DEFAULT_WINDOWS_PASSWORD = CONFIG.getDefaultWindowsPassword();		//Default Windows Password for new VMs
	String DEFAULT_LINUX_PASSWORD = CONFIG.getDefaultLinuxPassword();			//Default Linux Password for new VMs 
	
	/**
	 * Target path for VMRC program, excluding C:\Program Files\
	 */
	String VMRC = CONFIG.getPathToVMRC();
	
	/**
	 * Host username when activating the VM console
	 */
	String HOST_USERNAME = CONFIG.getHostUsername();
	
	/**
	 * Host password when activating the VM console
	 */
	String HOST_PASSWORD = CONFIG.getHostPassword();

	//Constants for Deploy Wizard
	String CLIENT = "Client";
	String SERVER = "Server";
	String WINDOWS = "Microsoft Windows";
	String LINUX_RPM = "Linux, RPM-based";
	String LINUX_DEBIAN = "Linux, Debian-based";
	String BSD = "BSD-based";
	String[] OPERATING_SYSTEM_TYPES = {"Select Category", WINDOWS, LINUX_RPM, LINUX_DEBIAN, BSD};
	
	String[] WINDOWS_CLIENT = CONFIG.getWindowsClients();
	String[] WINDOWS_SERVER = CONFIG.getWindowsServers();
	String[] RPM_SERVER = CONFIG.getRPMServers();
	String[] RPM_CLIENT = CONFIG.getRPMClients();
	String[] DEBIAN_CLIENT = CONFIG.getDebianClients();
	String[] DEBIAN_SERVER = CONFIG.getDebianServers();
	String[] BSD_SERVER = CONFIG.getBSD();
	
	String SOURCEFORGE_WEB = "http://sourceforge.net/projects/projectvase/";
	
	//Text for Deploy Wizard
	String DEPLOY_WELCOME = String.format("<html><center>Welcome to the VASE Deployment Wizard.<br />" + 
					 "Please select the number of Virtual Machines to deploy</center></html>");
	String DEPLOY_SELECTOS = String.format("<html><center>Select the Operating System of the guest Virtual Machine</center></html>");
	String DEPLOY_GUESTINFO = String.format("<html><center>Enter the guest Virtual Machine information</center></html>");
	String DEPLOY_WINDOWS_SERVER = String.format("<html><center>Select serivces and settings to be loaded on Windows Server</center></html>");
	String DEPLOY_WINDOWS_CLIENT = String.format("<html><center>Select services and settings to be loaded on Windows</center></html");
	String DEPLOY_LINUX_SERVER = String.format("<html><center>Select services and settings to be loaded on the Linux/BSD Server</center></html");
	String DEPLOY_LINUX_CLIENT = String.format("<html><center>Select services and settings to be loaded on the Linux/BSD Client</center></html");
}