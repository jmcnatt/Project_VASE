/**
 * Project_VASE Client package
 */
package vase.client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import vase.client.ThreadExt;

/**
 * Displays StandardInput from a Process on the system log
 * <br />
 * Used when launching the console to write Process STDIN
 * @author James McNatt & Brenton Kapral
 * @version Project_VASE Deploy
 */
public class ProcessInputThread extends ThreadExt
{
	private InputStream stream;
	private LogWriter log;
	
	/**
	 * Main Constructor
	 * @param stream the main inputstream from the process
	 * @param log the log writer
	 */
	public ProcessInputThread(InputStream stream, LogWriter log)
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