/**
 * Project_VASE Deploy package
 */
package vase.client.deploy;

import javax.swing.ImageIcon;

import com.vmware.vim25.VirtualMachineSummary;
import com.vmware.vim25.mo.VirtualMachine;

/**
 * Template class containing information related to a VirtualMachine
 * found in the template directory
 * @author James McNatt & Brenton Kapral
 * @version Project_VASE Deploy
 */
public class Template implements ProjectConstraints
{
	private String name;					//Template name
	private String guestOS;					//Guest OS
	private String category;				//Guest OS Category (for GuiDeployWizard)
	private String type;					//Guest OS Type (CLIENT OR SERVER for GuiDeployWizard)
	private ImageIcon icon;					//Icon
	private VirtualMachineSummary summary;	//Main summary
	private VirtualMachine template;		//This Template
	
	/**
	 * Main constructor
	 * @param template the VirtualMachine found in the template directory
	 */
	public Template(VirtualMachine template)
	{
		this.template = template;
		update();
	}
	
	/**
	 * Updates this template's information from data collected in the VirtualMachine
	 */
	public void update()
	{
		summary = template.getSummary();
		try {name = summary.config.name;} catch (Exception e) {name = "Unknown";}
		try {guestOS = summary.guest.guestFullName;} catch (Exception e) {guestOS = "Unknown";}
		
		//Set the Icon
		icon = new ImageIcon(getClass().getResource("/images/template-icon-small.png"));
	}

	/**
	 * Gets the name of this template
	 * @return the name
	 */
	public String getName()
	{
		return name;
	}

	/**
	 * Sets the name of this template
	 * @param name the name to set
	 */
	public void setName(String name)
	{
		this.name = name;
	}

	/**
	 * Gets the guest operating system name
	 * @return the guestOS
	 */
	public String getGuestOS()
	{
		return guestOS;
	}

	/**
	 * Sets the guest operating system name
	 * @param guestOS the guestOS to set
	 */
	public void setGuestOS(String guestOS)
	{
		this.guestOS = guestOS;
	}

	/**
	 * Gets the icon for this template
	 * @return the icon
	 */
	public ImageIcon getIcon()
	{
		return icon;
	}

	/**
	 * Sets the icon for this template
	 * @param icon the icon to set
	 */
	public void setIcon(ImageIcon icon)
	{
		this.icon = icon;
	}
	
	/**
	 * Gets the OS Type and Category based on the name of the template
	 * <br />
	 * If name does not match the template list, category and type are null
	 */
	private void gatherDeployData()
	{
		for (int i = 0; i < WINDOWS_CLIENT.length; i++)
		{
			if (name.equals(WINDOWS_CLIENT[i]))
			{
				type = CLIENT;
				category = WINDOWS;
			}
		}
		
		for (int i = 0; i < WINDOWS_SERVER.length; i++)
		{
			if (name.equals(WINDOWS_SERVER[i]))
			{
				type = SERVER;
				category = WINDOWS;
			} 
		}
		
		for (int i = 0; i < RPM_CLIENT.length; i++)
		{
			if (name.equals(RPM_CLIENT[i]))
			{
				type = CLIENT;
				category = LINUX_RPM;
			} 
		}
		
		for (int i = 0; i < RPM_SERVER.length; i++)
		{
			if (name.equals(RPM_SERVER[i]))
			{
				type = SERVER;
				category = LINUX_RPM;
			} 
		}
		
		for (int i = 0; i < DEBIAN_CLIENT.length; i++)
		{
			if (name.equals(DEBIAN_CLIENT[i]))
			{
				type = CLIENT;
				category = LINUX_DEBIAN;
			} 
		}
		
		for (int i = 0; i < DEBIAN_SERVER.length; i++)
		{
			if (name.equals(DEBIAN_SERVER[i]))
			{
				type = SERVER;
				category = LINUX_DEBIAN;
			} 
		}
		
		for (int i = 0; i < BSD_SERVER.length; i++)
		{
			if (name.equals(BSD_SERVER[i]))
			{
				type = SERVER;
				category = BSD;
			} 
		}
	}
	
	/**
	 * Gets the OS Category
	 * @return the OS Category based on the guestOS value
	 */
	public String getOsCategory()
	{
		gatherDeployData();
		return category;
	}
	
	/**
	 * Gets the OS Type
	 * @return the OS type
	 */
	public String getOsType()
	{
		gatherDeployData();
		return type;
	}
}
