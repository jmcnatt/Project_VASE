/**
 * Project_VASE Deploy virtual machine object package
 */
package vase.client.deploy.vmo;

import javax.swing.ImageIcon;

import com.vmware.vim25.mo.Folder;

/**
 * Folder class representing a Folder in the main Datacenter (VM Folder)
 * <br />
 * Contains attributes for the name and icon.  Used to represent Folders in the 
 * data tree on the GuiMain Folders list
 * @author James McNatt & Brenton Kapral
 * @version Project_VASE Deploy
 * @see Tree
 */
public class FolderExt
{
	private String name;
	private ImageIcon icon;
	private Folder folder;
	
	/**
	 * Main Constructor
	 * <br />
	 * Sets the closed-folder icon and sets the name of the folder
	 * @param folder the VMware folder object associated with this instance
	 */
	public FolderExt(Folder folder)
	{
		this.folder = folder;
		name = folder.getName();
		setIcon(new ImageIcon(getClass().getResource("/images/folder-icon.png")));
	}
	
	/**
	 * Gets the name of this folder
	 * @return the name of the folder
	 */
	public String getName()
	{
		return name;
	}
	
	/**
	 * Sets the name of this folder
	 * @param name the name of this folder
	 */
	public void setName(String name)
	{
		this.name = name;
	}

	/**
	 * Sets the icon for this folder
	 * @param icon the icon for this folder
	 */
	public void setIcon(ImageIcon icon)
	{
		this.icon = icon;
	}

	/**
	 * Gets the icon for this folder
	 * @return the icon for this folder
	 */
	public ImageIcon getIcon()
	{
		return icon;
	}
	
	/**
	 * Gets the Folder object
	 * @return the Folder object
	 */
	public Folder getFolder()
	{
		return folder;
	}
}
