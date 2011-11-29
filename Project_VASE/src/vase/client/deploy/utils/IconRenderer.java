/**
 * Project_VASE Deploy utility package
 */
package vase.client.deploy.utils;

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
	private String WINDOWS_PATTERN = "Windows";
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
		
		if (guestOS != null)
		{
			try
			{
				if (guestOS.indexOf(WINDOWS_PATTERN) != -1)
				{
					icon = new ImageIcon(getClass().getResource("/images/deploy/windows-logo.png"));
				}
				
				else if (guestOS.indexOf(CENTOS_PATTERN) != -1)
				{
					icon = new ImageIcon(getClass().getResource("/images/deploy/centos-logo.png"));
				}
				
				else if (guestOS.indexOf(UBUNTU_PATTERN) != -1)
				{
					icon = new ImageIcon(getClass().getResource("/images/deploy/ubuntu-logo.png"));
				}
				
				else if (guestOS.indexOf(FREE_BSD_PATTERN) != -1)
				{
					icon = new ImageIcon(getClass().getResource("/images/deploy/freebsd-logo.png"));
				}
				
				else if (guestOS.indexOf("Other") != -1)
				{
					icon = new ImageIcon(getClass().getResource("/images/deploy/other-linux.png"));
				}
				
				else
				{
					icon = new ImageIcon(getClass().getResource("/images/deploy/unknown.png"));
				}
			}
			
			catch (Exception e)
			{
				icon = new ImageIcon(getClass().getResource("/images/deploy/unknown.png"));
			}
		}
		
		else
		{
			icon = new ImageIcon(getClass().getResource("/images/deploy/unknown.png"));
		}
		
		return icon;
	}	
}
