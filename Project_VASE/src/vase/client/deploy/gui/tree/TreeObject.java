/**
 * Project_VASE Deploy GUI tree package
 */
package vase.client.deploy.gui.tree;

import javax.swing.ImageIcon;

/**
 * TreeNode object representing an object in vCenter in the Folders Tree on the Main GUI
 * @author James McNatt & Brenton Kapral
 * @version Project_VASE Deploy
 */
public class TreeObject
{
	/**
	 * Datacenter icon
	 */
	public static final int DATACENTER = 1;
	
	/**
	 * Virtual Machine icon
	 */
	public static final int VIRTUAL_MACHINE = 2;
	
	/**
	 * Folder icon
	 */
	public static final int FOLDER = 3;
	
	/**
	 * Virtual App icon
	 */
	public static final int VAPP = 4;
	
	/**
	 * Template icon
	 */
	public static final int TEMPLATE = 5;
	
	private ImageIcon icon;
	private String name;
	private boolean expanded = true;
	private int iconType;	
	
	/**
	 * 
	 * @param name the name of this node
	 * @param icon the icon image to use
	 */
	public TreeObject(String name, int iconType)
	{
		setName(name);
		setIcon(retrieveIcon(iconType));
	}
	
	/**
	 * Gets the ImageIcon for this object
	 * @return the icon
	 */
	public ImageIcon getIcon()
	{
		return icon;
	}

	/**
	 * Sets the ImageIcon for this object
	 * @param icon the icon to set
	 */
	public void setIcon(ImageIcon icon)
	{
		this.icon = icon;
	}

	/**
	 * @return the name
	 */
	public String getName()
	{
		return name;
	}

	/**
	 * @param name the name to set
	 */
	public void setName(String name)
	{
		this.name = name;
	}

	/**
	 * Gets the icon for this node
	 * @param icon the icon to use
	 * @return the ImageIcon object
	 */
	private ImageIcon retrieveIcon(int iconType)
	{
		ImageIcon thisIcon = null;
		this.iconType = iconType;
		
		switch (iconType)
		{
			case DATACENTER:
			{
				thisIcon = new ImageIcon(getClass().getResource("/images/deploy/datacenter-icon.png"));
				break;
			}
			
			case VIRTUAL_MACHINE:
			{
				thisIcon = new ImageIcon(getClass().getResource("/images/deploy/vm-icon-small.png"));
				break;
			}
			
			case FOLDER:
			{
				if (expanded)
				{
					thisIcon = new ImageIcon(getClass().getResource("/images/deploy/folder-icon-small-open.png"));
				}
				
				else
				{
					thisIcon = new ImageIcon(getClass().getResource("/images/deploy/folder-icon-small-closed.png"));
				}
				
				break;
			}
			
			case VAPP:
			{
				thisIcon = new ImageIcon(getClass().getResource("/images/deploy/vapp-icon.png"));
				break;
			}
			
			case TEMPLATE:
			{
				thisIcon = new ImageIcon(getClass().getResource("/images/deploy/template-icon-small.png"));
				break;
			}
			
			default:
			{
				thisIcon = new ImageIcon(getClass().getResource("/images/deploy/unknown.png"));
			}
		}
		
		return thisIcon;
	}

	/**
	 * Sets whether or not this object is expanded
	 * Resets the icon
	 * @param expanded
	 */
	public void setExpanded(boolean expanded)
	{
		this.expanded = expanded;
		setIcon(retrieveIcon(iconType));
	}
}
