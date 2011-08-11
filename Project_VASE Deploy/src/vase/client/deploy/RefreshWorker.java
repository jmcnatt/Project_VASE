/**
 * Project_VASE Deploy package
 */
package vase.client.deploy;

/**
 * Refresher that waits the REFRESH_INTERVAL in seconds and then refreshes the data
 * @author James McNatt & Brenton Kapral
 * @version Project_VASE Deploy
 * @see Thread
 * @see GuiMain
 * @see ProjectConstraints#REFRESH_INTERVAL
 */
class RefresherWorker extends Thread implements ProjectConstraints
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
				sleep(REFRESH_INTERVAL * 1000);
				new RefreshThread(main).start();
			}
			
			catch (InterruptedException e)
			{
				e.printStackTrace();
			}
		}
	}
}
