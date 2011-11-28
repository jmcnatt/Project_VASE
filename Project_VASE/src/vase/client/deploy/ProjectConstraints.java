/**
 * Project_VASE Deploy
 */
package vase.client.deploy;

import java.util.Map;

import vase.client.LogWriter;

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
	
	/**
	 * Author of this project
	 */
	String AUTHOR = "James McNatt & Brenton Kapral";
	
	/**
	 * Version of this project
	 */
	String VERSION = "1.0.0";
	
	/**
	 * The build of this project
	 */
	String BUILD = "602";
	
	/**
	 * The stage of this project
	 */
	String STAGE = "Alpha";
	
	/**
	 * Sourceforge web URL
	 */
	String SOURCEFORGE_WEB = "http://sourceforge.net/projects/projectvase/";
	
	/**
	 * Main of the configuration file
	 */
	String CONFIG_FILE_LOCATION = "deploy.conf";
	
	/**
	 * Location of the settings file
	 */
	String SETTINGS_FILE_LOCATION = System.getenv("APPDATA") + "\\Project_VASE\\vase-deploy-settings.bin";
	
	/**
	 * Location of the log file
	 */
	String LOG_FILE_LOCATION = System.getenv("APPDATA") + "\\Project_VASE\\vase-deploy-events.log";
	
	/**
	 * Global configuration reader instance, used to get .conf information
	 * @see ConfigReader
	 */
	ConfigReader CONFIG = new ConfigReader();
	
	/**
	 * Global log file writer instance, used to write LOG entries
	 * @see LogWriter
	 */
	LogWriter LOG = new LogWriter(LOG_FILE_LOCATION);
	
	//Settings Reader
	
	/**
	 * Global settings reader instance, used to read in the settings file
	 * @see SettingsReader
	 */
	SettingsReader SETTINGS_READER = new SettingsReader();
	
	/**
	 * Team-to-VM map
	 * @see SettingsReader#getTeamMap()
	 */
	Map<String, String> TEAMS = SETTINGS_READER.getTeamMap();
	
	//Global Variables via the ConfigReader (vase.conf)
	
	/**
	 * Datacenter name defined in deploy.conf
	 * @see ConfigReader#getDatacenterName()
	 */
	String DATACENTER = CONFIG.getDatacenterName();
	
	/**
	 * Template folder containing all templates used for deployment, defined in deploy.conf
	 * @see ConfigReader#getTemplateDir()
	 */
	String TEMPLATE_FOLDER = CONFIG.getTemplateDir();
	
	/**
	 * Root folder of the project where new deployments and teams are sent
	 * @see ConfigReader#getProjectDir()
	 */
	String ROOT_FOLDER = CONFIG.getProjectDir();
	
	/**
	 * Target datastore for new deployments, defined in deploy.conf
	 * @see ConfigReader#getTargetDatastore()
	 */
	String TARGET_DATASTORE = CONFIG.getTargetDatastore();
	
	/**
	 * Maximum number of VMs in a single deployment, defined in deploy.conf
	 * @see ConfigReader#getMaximumDeployment()
	 */
	int MAX_VM_DEPLOY = CONFIG.getMaximumDeployment();
	
	/**
	 * Number of Account CSV files on the server share for user account adds, defined in deploy.conf
	 * @see ConfigReader#getNumAccountFiles()
	 */
	int ACCOUNT_CSV_COUNT = CONFIG.getNumAccountFiles();
	
	/**
	 * Gets the share name on the VCenter server for the remote scritps
	 * @see ConfigReader#getRemoteScriptPath()
	 */
	String REMOTE_SCRIPT_PATH = CONFIG.getRemoteScriptPath();
	
	/**
	 * Amount of seconds to wait between GUI refreshes, defined in deploy.conf
	 * <br />
	 * Increase this number for slower systems
	 * @see ConfigReader#getRefreshInterval()
	 */
	int REFRESH_INTERVAL = CONFIG.getRefreshInterval();
	
	/**
	 * Team names, defined by the number of teams in deploy.conf
	 * @see ConfigReader#getTeams()
	 */
	String[] TEAM_NAMES = CONFIG.getTeams();
	
	/**
	 * Gets the default windows guest vm password, defined in deploy.conf
	 * @see ConfigReader#getDefaultWindowsPassword()
	 */
	String DEFAULT_WINDOWS_PASSWORD = CONFIG.getDefaultWindowsPassword();
	
	/**
	 * Gets the default linux guest vm password, defined in deploy.conf
	 * @see ConfigReader#getDefaultLinuxPassword()
	 */
	String DEFAULT_LINUX_PASSWORD = CONFIG.getDefaultLinuxPassword();
	
	/**
	 * Target path for VMRC program, excluding C:\Program Files\
	 * @see ConfigReader#getPathToVMRC()
	 */
	String VMRC = CONFIG.getPathToVMRC();
	
	/**
	 * Host username when activating the VM console
	 * @see ConfigReader#getHostUsername()
	 */
	String HOST_USERNAME = CONFIG.getHostUsername();
	
	/**
	 * Host password when activating the VM console
	 * @see ConfigReader#getHostPassword()
	 */
	String HOST_PASSWORD = CONFIG.getHostPassword();

	//Constants for Deploy Wizard
	
	/**
	 * Designates a client operating system
	 */
	String CLIENT = "Client";
	
	/**
	 * Designates a server operating system
	 */
	String SERVER = "Server";
	
	/**
	 * Microsoft Windows operating system family
	 */
	String WINDOWS = "Microsoft Windows";
	
	/**
	 * RPM-based linux family, including CentOS, RHEL, Fedora
	 */
	String LINUX_RPM = "Linux, RPM-based";
	
	/**
	 * Debian-based linux family, including Debian, Ubuntu, Ubuntu Server
	 */
	String LINUX_DEBIAN = "Linux, Debian-based";
	
	/**
	 * BSD and FreeBSD family
	 */
	String BSD = "BSD-based";
	
	/**
	 * Collection of the operating system types
	 */
	String[] OPERATING_SYSTEM_TYPES = {"Select Category", WINDOWS, LINUX_RPM, LINUX_DEBIAN, BSD};
	
	/**
	 * Supported Windows Clients, defined in deploy.conf
	 * @see ConfigReader#getWindowsClients()
	 */
	String[] WINDOWS_CLIENT = CONFIG.getWindowsClients();
	
	/**
	 * Supported Windows Servers, defined in deploy.conf
	 * @see ConfigReader#getWindowsServers()
	 */
	String[] WINDOWS_SERVER = CONFIG.getWindowsServers();
	
	/**
	 * Supported RPM-based Linux servers, defined in deploy.conf
	 * @see ConfigReader#getRPMServers()
	 */
	String[] RPM_SERVER = CONFIG.getRPMServers();
	
	/**
	 * Supported RPM-based Linux clients, defined in deploy.conf
	 * @see ConfigReader#getRPMClients()
	 */
	String[] RPM_CLIENT = CONFIG.getRPMClients();
	
	/**
	 * Supported Debian-based clients, defined in deploy.conf
	 * @see ConfigReader#getDebianClients()
	 */
	String[] DEBIAN_CLIENT = CONFIG.getDebianClients();
	
	/**
	 * Supported Debian-based servers, defined in deploy.conf
	 * @see ConfigReader#getDebianServers()
	 */
	String[] DEBIAN_SERVER = CONFIG.getDebianServers();
	
	/**
	 * Supported BSD servers, defined in deploy.conf
	 * @see ConfigReader#getBSD()
	 */
	String[] BSD_SERVER = CONFIG.getBSD();
}