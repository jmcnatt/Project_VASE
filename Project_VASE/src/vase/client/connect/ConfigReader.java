/**
 * Project_VASE Connect package
 */
package vase.client.connect;

import java.io.File;
import java.io.FileInputStream;
import java.util.Scanner;

import javax.swing.JOptionPane;

/**
 * Responsible for loading all values into the Constraints interface for use
 * in the program.  Some values start with default values, but the connect.conf file should
 * be used for settings in the program to avoid unexpected results.
 * @author James McNatt & Brenton Kapral
 * @version Project_VASE Connect
 * @see Constraints
 */
public class ConfigReader
{
	private File configFile = null;
	private int currentBlock = 1;
	private String datacenterName = "VASE";
	private String templateDir = "Templates";
	private String pathToVMRC;
	private String targetDatastore;
	private String hostUsername;
	private String hostPassword;
	private int refreshInterval = 60;
	
	private static final int GLOBALS = 1;	
	
	/**
	 * Main Constructor
	 * <br />
	 * Gathers all data, calls parseLine to categorize the data.  Realizes on
	 * the currentBlock int variable to tell where in the configuration file the
	 * this reader is.
	 */
	public ConfigReader()
	{		
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
						JOptionPane.showMessageDialog(null, "Error in parsing line " + lineNumber + " of \"connect.conf\"\n" + e.getMessage());
						e.printStackTrace();
					}
				}
				
				lineNumber++;
			}
		}
		
		catch (Exception e)
		{
			JOptionPane.showMessageDialog(null, "Could not open \"connect.conf\"\nAll default values loaded");
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
	 * Gets the path to VMware VMRC, not including C:\Program Files
	 * @return the pathToVMRC
	 */
	public String getPathToVMRC()
	{
		return pathToVMRC;
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
	 * Gets the refresh interval
	 * @return the time between refreshes
	 */
	public int getRefreshInterval()
	{
		return refreshInterval;
	}

	/**
	 * Parses the following variables from "connect.conf"
	 * <ul>
	 * <li>[global]</li>
	 * @param line the line in the config file to parse
	 */
	private void parseLine(String line)
	{
		if (line.equalsIgnoreCase("[global]"))
		{
			currentBlock = GLOBALS;
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
					
					else if (variable.equalsIgnoreCase("TEMPLATEDIR"))
					{
						templateDir = value;
					}
					
					else if (variable.equalsIgnoreCase("VMRC_PATH"))
					{
						pathToVMRC = value;
					}
					
					else if (variable.equalsIgnoreCase("TARGET_DATASTORE"))
					{
						targetDatastore = value;
					}
					
					else if (variable.equalsIgnoreCase("HOST_USERNAME"))
					{
						hostUsername = value;
					}
					
					else if (variable.equalsIgnoreCase("HOST_PASSWORD"))
					{
						hostPassword = value;
					}
					
					else if (variable.equalsIgnoreCase("REFRESH_INTERVAL"))
					{
						refreshInterval = Integer.parseInt(value);
					}
					
					break;
				}
			}				
		}
	}
}
