/**
 * Project_VASE Connect package
 */
package vase.client.connect;

/**
 * Project_VASE Connect
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
 * @verson Project_VASE Connect
 * 
 * @revision Build 101 - 110 Main and testing builds
 * @revision Build 111 Fixed issue with Service Nic vs. Virtual Nic when launching consoles
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
