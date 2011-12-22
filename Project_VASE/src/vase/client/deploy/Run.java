/**
 * Project_VASE Deploy package
 */
package vase.client.deploy;

import vase.client.deploy.gui.LoginSplash;

/**
 * Project_VASE Deploy
 * 
 * See the Readme.txt file included with this program and the 
 * help file for more information.
 * 
 * Edit the deploy.conf file before launching this program to customize 
 * VASE Deploy for your environment
 * 
 * Enjoy!
 * - James McNatt & Brenton Kapral
 * - RIT Honors Program
 * - Rochester Institute of Technology
 *
 * @author James McNatt & Brenton Kapral
 * @verson Project_VASE Deploy
 */
public class Run
{
	/**
	 * Main method
	 * <br />
	 * Brings up the login splash
	 * @param args No arguments accepted
	 */
	public static void main(String[] args)
	{
		new LoginSplash();
	}
}
