/**
 * Project_VASE Deploy package
 */
package vase.client.deploy;

import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.Scanner;

import javax.swing.JOptionPane;

/**
 * Responsible for loading all values into the ProjectConstraints interface for use
 * in the program.  Some values start with default values, but the vase.conf file should
 * be used for settings in the program to avoid unexpected results.
 * @author James McNatt & Brenton Kapral
 * @version Project_VASE Deploy
 * @see ProjectConstraints
 */
public class ConfigReader
{
	private File configFile = null;
	private int currentBlock = 1;
	private String datacenterName = "VASE";
	private String templateDir = "Templates";
	private String projectDir = "Project_VASE";
	private String targetDatastore = "Project_VASE";
	private String defaultWindowsPassword = "Windows-admin";
	private String defaultLinuxPassword = "Linux-admin";
	private String hostUsername;
	private String hostPassword;
	private String pathToVMRC;
	private String scriptPath;
	private int maximumDeployment = 7;
	private int accountFiles = 10;
	private ArrayList<String> teams;
	private ArrayList<String> windows_client;
	private ArrayList<String> windows_server;
	private ArrayList<String> rpm_client;
	private ArrayList<String> rpm_server;
	private ArrayList<String> debian_client;
	private ArrayList<String> debian_server;
	private ArrayList<String> bsd;
	private int refreshInterval = 120;
	
	private static final int GLOBALS = 1;
	private static final int WINDOWS_CLIENT = 2;
	private static final int WINDOWS_SERVER = 3;
	private static final int RPM_CLIENT = 4;
	private static final int RPM_SERVER = 5;
	private static final int DEBIAN_CLIENT = 6;
	private static final int DEBIAN_SERVER = 7;
	private static final int BSD = 8;
	private static final int TEAMS = 9;

	/**
	 * Main Constructor
	 * <br />
	 * Gathers all data, calls parseLine to categorize the data.  Realizes on
	 * the currentBlock int variable to tell where in the configuration file the
	 * this reader is.
	 */
	public ConfigReader()
	{
		windows_client = new ArrayList<String>();
		windows_server = new ArrayList<String>();
		rpm_client = new ArrayList<String>();
		rpm_server = new ArrayList<String>();
		debian_client = new ArrayList<String>();
		debian_server = new ArrayList<String>();
		bsd = new ArrayList<String>();
		teams = new ArrayList<String>();
		
		try
		{
			configFile = new File(ProjectConstraints.CONFIG_FILE_LOCATION);
			FileInputStream fis = new FileInputStream(configFile);
			Scanner in = new Scanner(fis);
			String line;
			int lineNumber = 1;
			while (in.hasNextLine())
			{
				line = in.nextLine();
				if (!line.startsWith("#") && !line.equals(""))
				{
					try
					{
						parseLine(line);
					}
					
					catch (Exception e)
					{
						JOptionPane.showMessageDialog(null, "Error in parsing line " + lineNumber + " of \"deploy.conf\"\n" + e.getMessage());
						e.printStackTrace();
					}
				}
				
				lineNumber++;
			}
		}
		
		catch (Exception e)
		{
			JOptionPane.showMessageDialog(null, "Could not open \"deploy.conf\"\nAll default values loaded");
		}
	}
	
	/**
	 * Gets the Datacenter name to be used
	 * @return the datacenterName
	 */
	public String getDatacenterName()
	{
		return datacenterName;
	}

	/**
	 * Gets the directory holding the templates
	 * @return the directory holding the templates
	 */
	public String getTemplateDir()
	{
		return templateDir;
	}

	/**
	 * Gets the directory holding the main project
	 * @return the the directory holding the project
	 */
	public String getProjectDir()
	{
		return projectDir;
	}

	/**
	 * Gets the maximum number of VMs in one deployment
	 * @return the maximumDeployment
	 */
	public int getMaximumDeployment()
	{
		return maximumDeployment;
	}

	/**
	 * Gets the time between refreshes for the GuiMain
	 * @return the refreshDelay
	 */
	public int getNumAccountFiles()
	{
		return accountFiles;
	}
	
	/**
	 * Gets the target datastore for VMs
	 * @return the target datastore for VMs
	 */
	public String getTargetDatastore()
	{
		return targetDatastore;
	}
	
	/**
	 * Gets the Refresh interval for refreshing the data on the GUI
	 * @return the number of seconds between refreshes
	 */
	public int getRefreshInterval()
	{
		return refreshInterval;
	}
	
	/**
	 * Gets the default Windows password for new Windows-based VMs
	 * @return the default Windows password for new Windows-based VMs
	 */
	public String getDefaultWindowsPassword()
	{
		return defaultWindowsPassword;
	}
	
	/**
	 * Gets the default Linux/BSD password for new Linux/BSD-based VMs
	 * @return the default Linux/BSD password for new Linux/BSD-based VMs
	 */
	public String getDefaultLinuxPassword()
	{
		return defaultLinuxPassword;
	}
	
	/**
	 * Takes in the maximum number of teams and generates a String[]
	 * representation for use in the GUIDeployWizard
	 * @return the string representation of each team's name
	 */
	public String[] getTeams()
	{
		String[] teamNames = new String[teams.size()];
		return teams.toArray(teamNames);	
	}
	
	/**
	 * Gets the collection of Windows Clients
	 * @return the supported Windows Clients
	 */
	public String[] getWindowsClients()
	{
		String[] operatingSystems = new String[windows_client.size()];
		return windows_client.toArray(operatingSystems);
	}
	
	/**
	 * Gets the collection of Windows Servers
	 * @return the supported Windows Servers
	 */
	public String[] getWindowsServers()
	{
		String[] operatingSystems = new String[windows_server.size()];
		return windows_server.toArray(operatingSystems);
	}
	
	/**
	 * Gets the collection of RPM-based linux clients
	 * @return the supported RPM-based linux clients
	 */
	public String[] getRPMClients()
	{
		String[] operatingSystems = new String[rpm_client.size()];
		return rpm_client.toArray(operatingSystems);
	}
	
	/**
	 * Gets the collection of RPM-based linux servers
	 * @return the supported RPM-based linux servers
	 */
	public String[] getRPMServers()
	{
		String[] operatingSystems = new String[rpm_server.size()];
		return rpm_server.toArray(operatingSystems);
	}
	
	/**
	 * Gets the collection of Debian-based linux clients
	 * @return the supported Debian-based linux clients
	 */
	public String[] getDebianClients()
	{
		String[] operatingSystems = new String[debian_client.size()];
		return debian_client.toArray(operatingSystems);
	}
	
	/**
	 * Gets the collection of Debian-based linux servers
	 * @return the supported Debian-based linux servers
	 */
	public String[] getDebianServers()
	{
		String[] operatingSystems = new String[debian_server.size()];
		return debian_server.toArray(operatingSystems);
	}
	
	/**
	 * Gets the collection of BSD clients/servers
	 * @return the supported BSD clients/servers
	 */
	public String[] getBSD()
	{
		String[] operatingSystems = new String[bsd.size()];
		return bsd.toArray(operatingSystems);
	}
	
	/**
	 * Gets the username needed for connecting to a host on the cluster/datacenter
	 * @return the username of the account used to connect to the vm's host
	 */
	public String getHostUsername()
	{
		return hostUsername;
	}
	
	/**
	 * Gets the password needed for connecting to a host on the cluster/datacenter
	 * @return the password for the account used to connect to the vm's host
	 */
	public String getHostPassword()
	{
		return hostPassword;
	}
	
	/**
	 * Gets the path to VMware VMRC, not including C:\Program Files
	 * @return the pathToVMRC
	 */
	public String getPathToVMRC()
	{
		return pathToVMRC;
	}

	/**
	 * Gets the path to remote scripts for customization
	 * @return the remoteScriptPath
	 */
	public String getScriptPath()
	{
		return scriptPath;
	}

	/**
	 * Parses the following variables from "vase.conf"
	 * <ul>
	 * <li>[global]</li>
	 * <li>[windows client]</li>
	 * <li>[windows server]</li>
	 * <li>[linux rpm client]</li>
	 * <li>[linux rpm server]</li>
	 * <li>[linux debian client]</li>
	 * <li>[linux debian server]</li>
	 * <li>[bsd]</li>
	 * <li>[teams]</li>
	 * @param line the line in the config file to parse
	 */
	private void parseLine(String line)
	{
		if (line.equalsIgnoreCase("[global]"))
		{
			currentBlock = GLOBALS;
		}
		
		else if (line.equalsIgnoreCase("[windows server]"))
		{
			currentBlock = WINDOWS_SERVER;
		}
		
		else if (line.equalsIgnoreCase("[windows client]"))
		{
			currentBlock = WINDOWS_CLIENT;
		}
		
		else if (line.equalsIgnoreCase("[linux rpm client]"))
		{
			currentBlock = RPM_CLIENT;
		}
		
		else if (line.equalsIgnoreCase("[linux rpm server]"))
		{
			currentBlock = RPM_SERVER;
		}
		
		else if (line.equalsIgnoreCase("[linux debian client]"))
		{
			currentBlock = DEBIAN_CLIENT;
		}
		
		else if (line.equalsIgnoreCase("[linux debian server]"))
		{
			currentBlock = DEBIAN_SERVER;
		}
		
		else if (line.equalsIgnoreCase("[bsd]"))
		{
			currentBlock = BSD;
		}
		
		else if (line.equalsIgnoreCase("[teams]"))
		{
			currentBlock = TEAMS;
		}
		
		else
		{
			switch (currentBlock)
			{
				case GLOBALS:
				{
					String[] lines = line.split("=");
					String variable = lines[0];
					String value = lines[1];
					
					if (variable.equalsIgnoreCase("DATACENTER"))
					{
						datacenterName = value;
					}
					
					else if (variable.equalsIgnoreCase("REFRESH"))
					{
						refreshInterval = Integer.parseInt(value);
					}
					
					else if (variable.equalsIgnoreCase("TEMPLATEDIR"))
					{
						templateDir = value;
					}
					
					else if (variable.equalsIgnoreCase("PROJECTDIR"))
					{
						projectDir = value;
					}
					
					else if (variable.equalsIgnoreCase("TARGET_DATASTORE"))
					{
						targetDatastore = value;
					}
					
					else if (variable.equalsIgnoreCase("ACCOUNT_CSV_COUNT"))
					{
						accountFiles = Integer.parseInt(value);
					}
					
					else if (variable.equalsIgnoreCase("DEFAULT_WINDOWS_PASSWORD"))
					{
						defaultWindowsPassword = value;
					}
					
					else if (variable.equalsIgnoreCase("DEFAULT_LINUX_PASSWORD"))
					{
						defaultLinuxPassword = value;
					}
					
					else if (variable.equalsIgnoreCase("HOST_USERNAME"))
					{
						hostUsername = value;
					}
					
					else if (variable.equalsIgnoreCase("HOST_PASSWORD"))
					{
						hostPassword = value;
					}
					
					else if (variable.equalsIgnoreCase("VMRC_PATH"))
					{
						pathToVMRC = value;
					}
					
					else if (variable.equalsIgnoreCase("SCRIPT_PATH"))
					{
						scriptPath = value;
					}
					
					break;
				}
				
				case WINDOWS_CLIENT:
				{
					windows_client.add(line);
					break;
				}
				
				case WINDOWS_SERVER:
				{
					windows_server.add(line);
					break;
				}
				
				case RPM_CLIENT:
				{
					rpm_client.add(line);
					break;
				}
				
				case RPM_SERVER:
				{
					rpm_server.add(line);
					break;
				}
				
				case DEBIAN_CLIENT:
				{
					debian_client.add(line);
					break;
				}
				
				case DEBIAN_SERVER:
				{
					debian_server.add(line);
					break;
				}
				
				case BSD:
				{
					bsd.add(line);
					break;
				}
				
				case TEAMS:
				{
					teams.add(line);
					break;
				}
			}				
		}
	}
}
