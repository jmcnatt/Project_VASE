/**
 * Project_VASE Connect package
 */
package vase.client.connect;

import vase.client.LogWriter;

/**
 * Interface for all Swing constraints, project constants, and other variables needed by multiple classes
 * that must be static final
 * <br />
 * Contains:
 * <ul>
 * <li>Config file values</li>
 * <li>LogReader</li>
 * </ul>
 * @author James McNatt & Brenton Kapral
 * @version Project_VASE Connect
 */
public interface ProjectConstraints
{
	//Project Information
	
	/**
	 * Project_VASE Connect Author information for AboutDialog
	 * @see AboutDialog
	 */
	String AUTHOR = "James McNatt & Brenton Kapral";
	
	/**
	 * Project_VASE Connect Version information for AboutDialog
	 * @see AboutDialog
	 */
	String VERSION = "1.0.0";
	
	/**
	 * Project_VASE Build information for AboutDialog
	 * @see AboutDialog
	 */
	String BUILD = "302";
	
	/**
	 * Project_VASE Staging information for AboutDialog
	 * @see AboutDialog
	 */
	String STAGE = "Alpha";
	
	/**
	 * Project_VASE sourceforge URL
	 */
	String SOURCEFORGE_WEB = "http://sourceforge.net/projects/projectvase/";
	
	//Configuration Reader, Log File Writer, Settings Reader
	
	/**
	 * Name and location of the conf file
	 */
	String CONFIG_FILE_LOCATION = "connect.conf";
	
	/**
	 * Location of the Settings file using the APPDATA variable
	 * @see System#getenv(String)
	 */
	String SETTINGS_FILE_LOCATION = System.getenv("APPDATA") + "\\Project_VASE\\vase-connect-settings.bin";
	
	/**
	 * Location of the Log file using the APPDATA variable
	 * @see System#getenv(String)
	 */
	String LOG_FILE_LOCATION = System.getenv("APPDATA") + "\\Project_VASE\\vase-connect-events.log";
	
	/**
	 * Log writer - writes entries to the log file
	 * @see LogWriter
	 */
	LogWriter LOG = new LogWriter(LOG_FILE_LOCATION);
	
	/**
	 * Reads in the settings from the settings bin file
	 * @see SettingsReader
	 */
	SettingsReader SETTINGS_READER = new SettingsReader();
	
	/**
	 * Reads in values from the config file
	 */
	ConfigReader CONFIG = new ConfigReader();
	
	/**
	 * Datacenter to use in the project
	 */
	String DATACENTER = CONFIG.getDatacenterName();
	
	/**
	 * Directory to Ignore (contains templates)
	 */
	String TEMPLATE_DIR = CONFIG.getTemplateDir();
	
	/**
	 * Root folder of the project where new deployments and teams are sent
	 * @see ConfigReader#getProjectDir()
	 */
	String ROOT_FOLDER = CONFIG.getProjectDir();
	
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
	
	/**
	 * Refresh Interval
	 */
	int REFRESH_INTERVAL = CONFIG.getRefreshInterval();
}
