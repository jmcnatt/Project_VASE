/**
 * Project_VASE Deploy GUI wizard package
 */
package vase.client.deploy.gui.wizard;

import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.SpringLayout;

import vase.client.Panel;
import vase.client.Utilities;

/**
 * Windows Client panel used to customize a Windows client
 * @author James McNatt & Brenton Kapral
 * @version Project_VASE Deploy
 */
public class WindowsClientPanel extends Panel
{
	private static final long serialVersionUID = -3454771501288656227L;
	
	/**
	 * Label - title of this panel
	 */
	public JLabel jlWindowsClient;
	
	/**
	 * Label - Load user accounts
	 */
	public JLabel jlAccounts;
	
	/**
	 * Label - Load exploits
	 */
	public JLabel jlExploits;
	
	/**
	 * Label - Bind to Active Directory
	 */
	public JLabel jlBind;
	
	/**
	 * Label - Name of Directory
	 */
	public JLabel jlDirectoryName;
	
	/**
	 * CheckBox - Load user accounts
	 */
	public JCheckBox jcbAccounts;
	
	/**
	 * CheckBox - Load exploits
	 */
	public JCheckBox jcbExploits;
	
	/**
	 * CheckBox - Bind to Active Directory
	 */
	public JCheckBox jcbBind;
	
	/**
	 * TextField - Name of Directory
	 */
	public JTextField jtfDirectoryName;
	
	/**
	 * Main Constructor
	 */
	public WindowsClientPanel()
	{
		super(new BorderLayout(), false, DIM_DEPLOY_CENTER);
		
		makeItems();
		makePanels();
	}
	
	/**
	 * Makes the attributes for this class
	 */
	private void makeItems()
	{
		jlAccounts = Utilities.createDeployComponentLabel("Load User Accounts: ");
		jlExploits = Utilities.createDeployComponentLabel("Load Exploits: ");
		jlBind = Utilities.createDeployComponentLabel("Bind to Active Directory: ");
		jlDirectoryName = Utilities.createDeployComponentLabel("Directory Name: ");
		jlWindowsClient = Utilities.createDeployLabel(DEPLOY_WINDOWS_CLIENT);
		jcbAccounts = Utilities.createDeployCheckBox(false);
		jcbExploits = Utilities.createDeployCheckBox(false);
		jcbBind = Utilities.createDeployCheckBox(false);
		jtfDirectoryName = Utilities.createDeployTextField("");
		
		jtfDirectoryName.setVisible(false);
		jlDirectoryName.setVisible(false);
	}
	
	/**
	 * Makes the panels and adds the attributes
	 */
	private void makePanels()
	{
		SpringLayout layoutWest = new SpringLayout();
		SpringLayout layoutEast = new SpringLayout();
		Panel north = new Panel(new FlowLayout(FlowLayout.CENTER), false, DIM_DEPLOY_CENTER_NORTH);
		Panel south = new Panel(new BorderLayout(), false, DIM_DEPLOY_CENTER_SOUTH);
		Panel east = new Panel(layoutEast, false, DIM_DEPLOY_CENTER_SOUTH_EAST);
		Panel west = new Panel(layoutWest, false, DIM_DEPLOY_CENTER_SOUTH_WEST);
		Panel southSouth = new Panel(new FlowLayout(FlowLayout.CENTER), false, DIM_DEPLOY_CENTER_SOUTH_SOUTH);
		
		north.add(jlWindowsClient);
		
		west.add(jlBind);
		west.add(jlDirectoryName);
		west.add(jlAccounts);
		west.add(jlExploits);
		layoutWest.putConstraint(SpringLayout.VERTICAL_CENTER, jlBind, 15, SpringLayout.NORTH, west);
		layoutWest.putConstraint(SpringLayout.EAST, jlBind, -5, SpringLayout.EAST, west);
		layoutWest.putConstraint(SpringLayout.VERTICAL_CENTER, jlDirectoryName, 35, SpringLayout.VERTICAL_CENTER, jlBind);
		layoutWest.putConstraint(SpringLayout.EAST, jlDirectoryName, -5, SpringLayout.EAST, west);
		layoutWest.putConstraint(SpringLayout.VERTICAL_CENTER, jlAccounts, 75, SpringLayout.VERTICAL_CENTER, jlDirectoryName);
		layoutWest.putConstraint(SpringLayout.EAST, jlAccounts, -5, SpringLayout.EAST, west);
		layoutWest.putConstraint(SpringLayout.VERTICAL_CENTER, jlExploits, 35, SpringLayout.VERTICAL_CENTER, jlAccounts);
		layoutWest.putConstraint(SpringLayout.EAST, jlExploits, -5, SpringLayout.EAST, west);
		
		east.add(jcbBind);
		east.add(jtfDirectoryName);
		east.add(jcbAccounts);
		east.add(jcbExploits);
		layoutEast.putConstraint(SpringLayout.VERTICAL_CENTER, jcbBind, 15, SpringLayout.NORTH, east);
		layoutEast.putConstraint(SpringLayout.WEST, jcbBind, 5, SpringLayout.WEST, east);
		layoutEast.putConstraint(SpringLayout.VERTICAL_CENTER, jtfDirectoryName, 35, SpringLayout.VERTICAL_CENTER, jcbBind);
		layoutEast.putConstraint(SpringLayout.WEST, jtfDirectoryName, 5, SpringLayout.WEST, east);
		layoutEast.putConstraint(SpringLayout.VERTICAL_CENTER, jcbAccounts, 75, SpringLayout.VERTICAL_CENTER, jtfDirectoryName);
		layoutEast.putConstraint(SpringLayout.WEST, jcbAccounts, 5, SpringLayout.WEST, east);
		layoutEast.putConstraint(SpringLayout.VERTICAL_CENTER, jcbExploits, 35, SpringLayout.VERTICAL_CENTER, jcbAccounts);
		layoutEast.putConstraint(SpringLayout.WEST, jcbExploits, 5, SpringLayout.WEST, east);
		
		southSouth.add(Utilities.createDeployLabel("Note: Ensure a Domain Controller is active before continuing"));
		
		south.add(east, BorderLayout.EAST);
		south.add(west, BorderLayout.WEST);
		south.add(southSouth, BorderLayout.SOUTH);
		
		add(north, BorderLayout.NORTH);
		add(south, BorderLayout.SOUTH);
	}
}
