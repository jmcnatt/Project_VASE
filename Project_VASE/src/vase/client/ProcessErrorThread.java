/**
 * Project_VASE Client package
 */
package vase.client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import vase.client.LogWriter;
import vase.client.ThreadExt;

/**
 * Displays StandardError from a Process on the System Log
 * @author James McNatt & Brenton Kapral
 * @version Project_VASE Deploy
 * @see Process
 */
public class ProcessErrorThread extends ThreadExt
{
	private InputStream stream;
	private LogWriter log;
	
	/**
	 * Main Constructor
	 * @param stream the main errorstream from the process
	 * @param log the log writer
	 */
	public ProcessErrorThread(InputStream stream, LogWriter log)
	{
		this.stream = stream;
		this.log = log;
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
				log.write(line, true);
			}
		}
		
		catch (IOException e)
		{
			log.printStackTrace(e);
			
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
				log.printStackTrace(e);
				
				e.printStackTrace();
			}
		}
	}
}
