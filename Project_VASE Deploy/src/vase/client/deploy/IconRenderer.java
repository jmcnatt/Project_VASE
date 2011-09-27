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
					icon = new ImageIcon(getClass().getResource("/images/windows-logo.png"));
				}
				
				else if (guestOS.indexOf(CENTOS_PATTERN) != -1)
				{
					icon = new ImageIcon(getClass().getResource("/images/centos-logo.png"));
				}
				
				else if (guestOS.indexOf(UBUNTU_PATTERN) != -1)
				{
					icon = new ImageIcon(getClass().getResource("/images/ubuntu-logo.png"));
				}
				
				else if (guestOS.indexOf(FREE_BSD_PATTERN) != -1)
				{
					icon = new ImageIcon(getClass().getResource("/images/freebsd-logo.png"));
				}
				
				else if (guestOS.indexOf("Other") != -1)
				{
					icon = new ImageIcon(getClass().getResource("/images/other-linux.png"));
				}
				
				else
				{
					icon = new ImageIcon(getClass().getResource("/images/unknown.png"));
				}
			}
			
			catch (Exception e)
			{
				icon = new ImageIcon(getClass().getResource("/images/unknown.png"));
			}
		}
		
		else
		{
			icon = new ImageIcon(getClass().getResource("/images/unknown.png"));
		}
		
		return icon;
	}	
}
