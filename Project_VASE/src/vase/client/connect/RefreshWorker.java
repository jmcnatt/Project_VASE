/**
 * Project_VASE Connect package
 */
package vase.client.connect;

import vase.client.ThreadExt;

/**
 * Refresher that waits the REFRESH_INTERVAL in seconds and then refreshes the data
 * @author James McNatt & Brenton Kapral
 * @version Project_VASE Deploy
 * @see ThreadExt
 * @see GuiMain
 * @see ProjectConstraints#REFRESH_INTERVAL
 */
class RefresherWorker extends ThreadExt
{
	private GuiMain main;
	
	/**
	 * Main Constructor.  Starts the thread
	 */
	public RefresherWorker(GuiMain main)
	{
		this.main = main;
		start();
	}
	
	/**
	 * Runs the Thread
	 */
	public void run()
	{
		while (true)
		{
			try
			{
				sleep(ProjectConstraints.REFRESH_INTERVAL * 1000);
				new RefreshThread(main).start();
			}
			
			catch (InterruptedException e)
			{
				ProjectConstraints.LOG.printStackTrace(e);
			}
		}
	}
}
