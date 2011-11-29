/**
 * Project_VASE Deploy
 */
package vase.client.deploy;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.EOFException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import vase.client.deploy.vmo.DeployedVirtualMachine;

/**
 * Responsible for reading the HashMap of VMname to the flag from
 * settings.bin and loading it into ProjectConstraits, and then
 * saving it when the program exits.
 * @author James McNatt & Brenton Kapral
 * @version Project_VASE Deploy
 */
public class SettingsReader implements ProjectConstraints
{
	private  File settingsFile = new File(SETTINGS_FILE_LOCATION);
	private  Map<String, String> vmMap;
	private ArrayList<DeployedVirtualMachine> lastDeployment;
	
	/**
	 * Last successful server logon
	 */
	public String lastServer = "";
	
	/**
	 * Last successful user logon
	 */
	public String lastUser = "";
	
	/**
	 * Load console on full screen
	 */
	public boolean fullScreen = false;
	
	/**
	 * Gets the map from the settings file
	 * @return the HashMap containing the VMnames and their flagss
	 */
	@SuppressWarnings("unchecked")
	public SettingsReader()
	{
		vmMap = new HashMap<String, String>();
		ObjectInputStream ois = null;
		lastDeployment = new ArrayList<DeployedVirtualMachine>();
		
		try
		{
			ois = new ObjectInputStream(new BufferedInputStream(new FileInputStream(settingsFile)));
			
			try
			{
				lastServer = (String) ois.readObject();							//Server
				lastUser = (String) ois.readObject();							//Username
				vmMap = (HashMap<String, String>) ois.readObject();				//Team map
				lastDeployment = (ArrayList<DeployedVirtualMachine>) ois.readObject();	//Last Deployment
				fullScreen = ois.readBoolean();
			}
			
			catch (EOFException e)
			{
				//Info not saved
				LOG.printStackTrace(e);
				
				e.printStackTrace();
			}
			
			catch (Exception e)
			{
				LOG.write("Settings not loaded", true);
				LOG.printStackTrace(e);
			}		
		}
		
		catch (FileNotFoundException e)
		{
			LOG.write("\"settings.bin\" not found, loading new values", true);
		}
		
		catch (IOException e)
		{
			LOG.write("I/O Exception caught while loading settings.bin", true);
			LOG.printStackTrace(e);
		}
			
		finally
		{
			if (ois != null)
			{
				try
				{
					ois.close();
				}
				
				catch (Exception e)
				{
					LOG.write(e.getStackTrace().toString(), true);
					LOG.printStackTrace(e);
					
					e.printStackTrace();
				}
			}
		}
	}
	
	/**
	 * @param lastServer the lastServer to set
	 */
	public void setLastServer(String lastServer)
	{
		this.lastServer = lastServer;
	}

	/**
	 * @param lastUser the lastUser to set
	 */
	public void setLastUser(String lastUser)
	{
		this.lastUser = lastUser;
	}

	/**
	 * @return the vmMap
	 */
	public Map<String, String> getTeamMap()
	{
		return vmMap;
	}
	
	/**
	 * Gets the panel to display on the last deployment tab
	 * @return the panel to display on the last deployment tab
	 */
	public ArrayList<DeployedVirtualMachine> getLastDeployment()
	{
		return lastDeployment;
	}
	
	/**
	 * Sets the lastDeploymentPanel
	 * @param panel the lastDeploymentPanel
	 */
	public void setLastDeployment(ArrayList<DeployedVirtualMachine> deployedVMs)
	{
		lastDeployment = deployedVMs;
	}
	
	/**
	 * Sets whether or not to load console on full screen
	 * @param fullScreen whether or not to load console on full screen
	 */
	public void setFullScreen(boolean fullScreen)
	{
		this.fullScreen = fullScreen;
	}

	/**
	 * Writes the data to the file
	 */
	public void save()
	{
		ObjectOutputStream oos = null;
		try
		{
			oos = new ObjectOutputStream(new BufferedOutputStream(new FileOutputStream(settingsFile)));
			oos.writeObject(lastServer);
			oos.writeObject(lastUser);
			oos.writeObject(vmMap);
			oos.writeObject(lastDeployment);
			oos.writeBoolean(fullScreen);
			oos.flush();
		}
		
		catch (FileNotFoundException e)
		{
			LOG.write("Error: Settings file not found.");
			LOG.printStackTrace(e);
			
			e.printStackTrace();
		}
		
		catch (IOException e)
		{
			LOG.write("I/O error in writing to \"settings.bin\"\n", true);
			LOG.printStackTrace(e);
			
			e.printStackTrace();
		}
		
		finally
		{
			if (oos != null)
			{
				try
				{
					oos.close();
				}
				
				catch (Exception e)
				{
					LOG.printStackTrace(e);
					
					e.printStackTrace();
				}
			}
		}
	}
}
