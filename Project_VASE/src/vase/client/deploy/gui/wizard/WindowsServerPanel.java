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
 * Windows Server panel used to customize a Windows server
 * @author James McNatt & Brenton Kapral
 * @version Project_VASE Deploy
 */
public class WindowsServerPanel extends Panel
{
	private static final long serialVersionUID = -1770188604416655174L;
	
	/**
	 * Label - title of this panel
	 */
	public JLabel jlWindowsServer;
	
	/**
	 * Label - DHCP
	 */
	public JLabel jlDHCP;
	
	/**
	 * Label - DNS
	 */
	public JLabel jlDNS;
	
	/**
	 * Label - Active Directory Domain Controller
	 */
	public JLabel jlActiveDirectory;
	
	/**
	 * Label - File Server
	 */
	public JLabel jlFileServer;
	
	/**
	 * Label - IIS
	 */
	public JLabel jlIIS;
	
	/**
	 * Label - Load User Accounts
	 */
	public JLabel jlAccounts;
	
	/**
	 * Label - Load Exploits
	 */
	public JLabel jlExploits;
	
	/**
	 * Label - Directory Name
	 */
	public JLabel jlDirectoryName;
	
	/**
	 * Check box - DNS
	 */
	public JCheckBox jcbDNS;
	
	/**
	 * Check box - DHCP
	 */
	public JCheckBox jcbDHCP;
	
	/**
	 * Check box - Active Directory Domain Constroller
	 */
	public JCheckBox jcbActiveDirectory;
	
	/**
	 * Check box - File Server
	 */
	public JCheckBox jcbFileServer;
	
	/**
	 * Check box - IIS
	 */
	public JCheckBox jcbIIS;
	
	/**
	 * Check box - Load user accounts
	 */
	public JCheckBox jcbAccounts;
	
	/**
	 * Check box - Load Exploits
	 */
	public JCheckBox jcbExploits;
	
	/**
	 * Text field - directory name
	 */
	public JTextField jtfDirectoryName;

	/**
	 * Main Constructor
	 */
	public WindowsServerPanel()
	{
		super(new BorderLayout(), false, DIM_DEPLOY_CENTER);
		
		makeItems();
		makePanels();
	}
	
	/**
	 * Makes the attributes of this class
	 */
	private void makeItems()
	{
		jlWindowsServer = Utilities.createDeployLabel(DEPLOY_WINDOWS_SERVER);
		jlDNS = Utilities.createDeployComponentLabel("DNS Server: ");
		jlDHCP = Utilities.createDeployComponentLabel("DHCP Server: ");
		jlActiveDirectory = Utilities.createDeployComponentLabel("Active Directory Domain Controller: ");
		jlFileServer = Utilities.createDeployComponentLabel("File Server: ");
		jlIIS = Utilities.createDeployComponentLabel("IIS Service: ");
		jlAccounts = Utilities.createDeployComponentLabel("Load User Accounts: ");
		jlExploits = Utilities.createDeployComponentLabel("Load Exploits: ");
		jlDirectoryName = Utilities.createDeployComponentLabel("Directory Name: ");
		jcbDNS = Utilities.createDeployCheckBox(false);
		jcbDHCP = Utilities.createDeployCheckBox(false);
		jcbActiveDirectory = Utilities.createDeployCheckBox(false);
		jcbFileServer = Utilities.createDeployCheckBox(false);
		jcbIIS = Utilities.createDeployCheckBox(false);
		jcbAccounts = Utilities.createDeployCheckBox(false);
		jcbExploits = Utilities.createDeployCheckBox(false);
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
		Panel west = new Panel(layoutWest, false, DIM_DEPLOY_CENTER_SOUTH_WEST);
		Panel east = new Panel(layoutEast, false, DIM_DEPLOY_CENTER_SOUTH_EAST);
		Panel southSouth = new Panel(new FlowLayout(FlowLayout.CENTER), false, DIM_DEPLOY_CENTER_SOUTH_SOUTH);
				
		north.add(jlWindowsServer);
		
		west.add(jlDNS);
		west.add(jlDHCP);
		west.add(jlActiveDirectory);
		west.add(jlFileServer);
		west.add(jlIIS);
		west.add(jlAccounts);
		west.add(jlExploits);
		west.add(jlDirectoryName);
		layoutWest.putConstraint(SpringLayout.VERTICAL_CENTER, jlDNS, 15, SpringLayout.NORTH, west);
		layoutWest.putConstraint(SpringLayout.EAST, jlDNS, -15, SpringLayout.EAST, west);
		layoutWest.putConstraint(SpringLayout.VERTICAL_CENTER, jlDHCP, 35, SpringLayout.VERTICAL_CENTER, jlDNS);
		layoutWest.putConstraint(SpringLayout.EAST, jlDHCP, -15, SpringLayout.EAST, west);
		layoutWest.putConstraint(SpringLayout.VERTICAL_CENTER, jlActiveDirectory, 35, SpringLayout.VERTICAL_CENTER, jlDHCP);
		layoutWest.putConstraint(SpringLayout.EAST, jlActiveDirectory, -15, SpringLayout.EAST, west);
		layoutWest.putConstraint(SpringLayout.VERTICAL_CENTER, jlDirectoryName, 35, SpringLayout.VERTICAL_CENTER, jlActiveDirectory);
		layoutWest.putConstraint(SpringLayout.EAST, jlDirectoryName, -15, SpringLayout.EAST, west);
		layoutWest.putConstraint(SpringLayout.VERTICAL_CENTER, jlFileServer, 35, SpringLayout.VERTICAL_CENTER, jlDirectoryName);
		layoutWest.putConstraint(SpringLayout.EAST, jlFileServer, -15, SpringLayout.EAST, west);
		layoutWest.putConstraint(SpringLayout.VERTICAL_CENTER, jlIIS, 35, SpringLayout.VERTICAL_CENTER, jlFileServer);
		layoutWest.putConstraint(SpringLayout.EAST, jlIIS, -15, SpringLayout.EAST, west);
		layoutWest.putConstraint(SpringLayout.VERTICAL_CENTER, jlAccounts, 35, SpringLayout.VERTICAL_CENTER, jlIIS);
		layoutWest.putConstraint(SpringLayout.EAST, jlAccounts, -15, SpringLayout.EAST, west);
		layoutWest.putConstraint(SpringLayout.VERTICAL_CENTER, jlExploits, 35, SpringLayout.VERTICAL_CENTER, jlAccounts);
		layoutWest.putConstraint(SpringLayout.EAST, jlExploits, -15, SpringLayout.EAST, west);
		
		east.setOpaque(false);
		east.setPreferredSize(DIM_DEPLOY_CENTER_SOUTH_EAST);
		east.setMaximumSize(DIM_DEPLOY_CENTER_SOUTH_EAST);
		east.setMinimumSize(DIM_DEPLOY_CENTER_SOUTH_EAST);
		east.add(jcbDNS);
		east.add(jcbDHCP);
		east.add(jcbActiveDirectory);
		east.add(jtfDirectoryName);
		east.add(jcbFileServer);
		east.add(jcbAccounts);
		east.add(jcbExploits);
		east.add(jcbIIS);
		layoutEast.putConstraint(SpringLayout.VERTICAL_CENTER, jcbDNS, 15, SpringLayout.NORTH, east);
		layoutEast.putConstraint(SpringLayout.WEST, jcbDNS, 10, SpringLayout.WEST, east);
		layoutEast.putConstraint(SpringLayout.VERTICAL_CENTER, jcbDHCP, 35, SpringLayout.VERTICAL_CENTER, jcbDNS);
		layoutEast.putConstraint(SpringLayout.WEST, jcbDHCP, 10, SpringLayout.WEST, east);
		layoutEast.putConstraint(SpringLayout.VERTICAL_CENTER, jcbActiveDirectory, 35, SpringLayout.VERTICAL_CENTER, jcbDHCP);
		layoutEast.putConstraint(SpringLayout.WEST, jcbActiveDirectory, 10, SpringLayout.WEST, east);
		layoutEast.putConstraint(SpringLayout.VERTICAL_CENTER, jtfDirectoryName, 35, SpringLayout.VERTICAL_CENTER, jcbActiveDirectory);
		layoutEast.putConstraint(SpringLayout.WEST, jtfDirectoryName, 10, SpringLayout.WEST, east);
		layoutEast.putConstraint(SpringLayout.VERTICAL_CENTER, jcbFileServer, 35, SpringLayout.VERTICAL_CENTER, jtfDirectoryName);
		layoutEast.putConstraint(SpringLayout.WEST, jcbFileServer, 10, SpringLayout.WEST, east);
		layoutEast.putConstraint(SpringLayout.VERTICAL_CENTER, jcbIIS, 35, SpringLayout.VERTICAL_CENTER, jcbFileServer);
		layoutEast.putConstraint(SpringLayout.WEST, jcbIIS, 10, SpringLayout.WEST, east);
		layoutEast.putConstraint(SpringLayout.VERTICAL_CENTER, jcbAccounts, 35, SpringLayout.VERTICAL_CENTER, jcbIIS);
		layoutEast.putConstraint(SpringLayout.WEST, jcbAccounts, 10, SpringLayout.WEST, east);
		layoutEast.putConstraint(SpringLayout.VERTICAL_CENTER, jcbExploits, 35, SpringLayout.VERTICAL_CENTER, jcbAccounts);
		layoutEast.putConstraint(SpringLayout.WEST, jcbExploits, 10, SpringLayout.WEST, east);
		
		south.add(west, BorderLayout.WEST);
		south.add(east, BorderLayout.EAST);
		south.add(southSouth, BorderLayout.SOUTH);
		
		add(north, BorderLayout.NORTH);
		add(south, BorderLayout.SOUTH);
	}
}