/**
 * Project_VASE Deploy package
 */
package vase.client.deploy;

import javax.swing.ImageIcon;

/**
 * Gets Icons for the VirtualMachineExt and Template classes to be rendered on
 * the Virtual Machine Table and the Lists on the Summary Tab.
 * @author James McNatt & Brenton Kapral
 * @version Project_VASE Deploy
 */
public class IconRenderer
{
	//Matching Patterns
	private String CENTOS_PATTERN = "CentOS";
	private String WINDOWS_PATTERN = "Microsoft";
	private String UBUNTU_PATTERN = "Ubuntu";
	private String FREE_BSD_PATTERN = "FreeBSD";
	
	/**
	 * Gets the ImageIcon based on the Guest Operating System
	 * @param guestOS the guest operating system
	 * @return the ImageIcon associated with the guest operating system
	 */
	public ImageIcon getIcon(String guestOS)
	{
		ImageIcon icon = null;
		try
		{
			String[] os = guestOS.split(" ");
			if (os[0].equalsIgnoreCase(WINDOWS_PATTERN) || os[0].equalsIgnoreCase("Windows"))
			{
				icon = new ImageIcon(getClass().getResource("/images/windows-logo.png"));
			}
			
			else if (os[0].equalsIgnoreCase(CENTOS_PATTERN))
			{
				icon = new ImageIcon(getClass().getResource("/images/centos-logo.png"));
			}
			
			else if (os[0].equalsIgnoreCase(UBUNTU_PATTERN))
			{
				icon = new ImageIcon(getClass().getResource("/images/ubuntu-logo.png"));
			}
			
			else if (os[0].equalsIgnoreCase(FREE_BSD_PATTERN))
			{
				icon = new ImageIcon(getClass().getResource("/images/freebsd-logo.png"));
			}
			
			else
			{
				icon = new ImageIcon(getClass().getResource("/images/blank.png"));
			}
		}
		
		catch (Exception e)
		{
			icon = new ImageIcon(getClass().getResource("/images/blank.png"));
		}
		
		return icon;
	}	
}
