/**
 * Project_VASE Deploy package
 */
package vase.client.deploy;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * Displays StandardError from a Process on the System Log
 * @author James McNatt & Brenton Kapral
 * @version Project_VASE Deploy
 * @see Process
 */
public class ProcessErrorThread extends ThreadExt
{
	private InputStream stream;
	
	/**
	 * Main Constructor
	 * @param stream the main errorstream from the process
	 */
	public ProcessErrorThread(InputStream stream)
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
				System.out.println(line);
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
