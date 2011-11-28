/**
 * Project_VASE Connect package
 */
package vase.client.connect.gui.refresh;

import vase.client.thread.ThreadExt;
import vase.client.connect.ProjectConstraints;
import vase.client.connect.gui.Main;

/**
 * Refresher that waits the REFRESH_INTERVAL in seconds and then refreshes the data
 * @author James McNatt & Brenton Kapral
 * @version Project_VASE Deploy
 * @see ThreadExt
 * @see GuiMain
 * @see ProjectConstraints#REFRESH_INTERVAL
 */
public class RefreshWorker extends ThreadExt
{
	private Main main;
	
	/**
	 * Main Constructor.  Starts the thread
	 */
	public RefreshWorker(Main main)
	{
		this.main = main;
		thread = this;
		start();
	}
	
	/**
	 * Runs the Thread
	 */
	public void run()
	{
		Thread thisThread = Thread.currentThread();
		
		while (thread == thisThread)
		{
			try
			{
				sleep(ProjectConstraints.REFRESH_INTERVAL * 1000);
				if (!(thread == thisThread)) break;
				new RefreshThread(main).start();
			}
			
			catch (InterruptedException e)
			{
				ProjectConstraints.LOG.printStackTrace(e);
			}
		}
	}
}
