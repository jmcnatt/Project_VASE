/**
 * Project_VASE Client package
 */
package vase.client.thread;

/**
 * ThreadExt superclass
 * @author James McNatt & Brenton Kapral
 * @version Project_VASE
 */
public class ThreadExt extends Thread
{
	/**
	 * Stop representation of this thread for haulting purposes
	 */
	public volatile Thread thread;
	
	/**
	 * Main Constructor
	 */
	public ThreadExt()
	{
		super();
	}
	
	/**
	 * Stops this thread by setting the instance to null
	 */
	public void halt()
	{
		thread = null;
	}
	
	
}
