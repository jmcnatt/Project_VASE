/**
 * Project_VASE Deploy package
 */
package vase.client.deploy;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * Displays StandardInput from a Process on the system log
 * <br />
 * Used when launching the console to write Process STDIN
 * @author James McNatt & Brenton Kapral
 * @version Project_VASE Deploy
 */
class ProcessInputThread extends ThreadExt
{
	private InputStream stream;
	
	/**
	 * Main Constructor
	 * @param stream the main inputstream from the process
	 */
	public ProcessInputThread(InputStream stream)
	{
		this.stream = stream;
	}
	
	/**
	 * Run method
	 */
	public void run()
	{
		BufferedReader reader = new BufferedReader(new InputStreamReader(stream));
		
		try
		{
			while (true)
			{
				String line = reader.readLine();
				if (line == null) break;
				LOG.write(line, true);
			}
		}
		
		catch (IOException e)
		{
			e.printStackTrace();
		}
		
		finally
		{
			try
			{
				reader.close();
			}
			catch (IOException e)
			{
				e.printStackTrace();
			}
		}
	}
}