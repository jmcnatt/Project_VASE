/**
 * Project_VASE Connect package
 */
package vase.client.connect;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

/**
 * Responsible for reading the HashMap of VMname to the flag from
 * settings.bin and loading it into ProjectConstraits, and then
 * saving it when the program exits.
 * @author James McNatt & Brenton Kapral
 * @version Project_VASE Connect
 */
public class SettingsReader
{
	private  File settingsFile = new File(ProjectConstraints.SETTINGS_FILE_LOCATION);
	
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
	public SettingsReader()
	{
		ObjectInputStream ois = null;
		
		try
		{
			ois = new ObjectInputStream(new BufferedInputStream(new FileInputStream(settingsFile)));
			
			try
			{
				lastServer = (String) ois.readObject();							//Server
				lastUser = (String) ois.readObject();							//Username
				fullScreen = ois.readBoolean();
			}
			
			catch (Exception e)
			{
				//Settings Not Loaded
			}		
		}
		
		catch (IOException e)
		{
			//Settings Not Loaded
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
					e.printStackTrace();
				}
			}
		}
	}
	
	/**
	 * Sets the last successful logon server
	 * @param lastServer the server to the was last connected successfully
	 */
	public void setLastServer(String lastServer)
	{
		this.lastServer = lastServer;
	}

	/**
	 * Sets the last successful user to logon
	 * @param lastUser the last successful user that logged on
	 */
	public void setLastUser(String lastUser)
	{
		this.lastUser = lastUser;
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
			oos.writeBoolean(fullScreen);
			oos.flush();
		}
		
		catch (IOException e)
		{
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
					e.printStackTrace();
				}
			}
		}
	}
}
