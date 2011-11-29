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
public class LinuxServerPanel extends Panel
{
	private static final long serialVersionUID = -940180639162389619L;
	
	/**
	 * Label - title for this panel
	 */
	public JLabel jlLinuxServer;
	
	/**
	 * Label - DNS
	 */
	public JLabel jlDNS;
	
	/**
	 * Label - DHCP
	 */
	public JLabel jlDHCP;
	
	/**
	 * Label - NIS
	 */
	public JLabel jlNIS;
	
	/**
	 * Label - Directory Name
	 */
	public JLabel jlDirectoryName;
	
	/**
	 * Label - NFS
	 */
	public JLabel jlNFS;
	
	/**
	 * Label - Samba
	 */
	public JLabel jlSamba;
	
	/**
	 * Label - Mail
	 */
	public JLabel jlMail;
	
	/**
	 * Label - HTTP
	 */
	public JLabel jlHTTP;
	
	/**
	 * Label - FTP
	 */
	public JLabel jlFTP;
	
	/**
	 * Label - Load User Accounts
	 */
	public JLabel jlAccounts;
	
	/**
	 * Label - Load Exploits
	 */
	public JLabel jlExploits;
	
	/**
	 * Check box - DNS
	 */
	public JCheckBox jcbDNS;
	
	/**
	 * Check box - DHCP
	 */
	public JCheckBox jcbDHCP;
	
	/**
	 * Check box - NIS
	 */
	public JCheckBox jcbNIS;
	
	/**
	 * Check box - NFS
	 */
	public JCheckBox jcbNFS;
	
	/**
	 * Check box - Mail
	 */
	public JCheckBox jcbMail;
	
	/**
	 * Check box - HTTP
	 */
	public JCheckBox jcbHTTP;
	
	/**
	 * Check box - FTP
	 */
	public JCheckBox jcbFTP;
	
	/**
	 * Check box - Samba
	 */
	public JCheckBox jcbSamba;
	
	/**
	 * Check box - Load user accounts
	 */
	public JCheckBox jcbAccounts;
	
	/**
	 * Check box - Load Exploits
	 */
	public JCheckBox jcbExploits;
	
	/**
	 * Text field - Directory name
	 */
	public JTextField jtfDirectoryName;
	
	/**
	 * Main Constructor
	 */
	public LinuxServerPanel()
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
		jlLinuxServer = Utilities.createDeployLabel(DEPLOY_LINUX_SERVER);
		jlDNS = Utilities.createDeployComponentLabel("DNS Server: ");
		jlDHCP = Utilities.createDeployComponentLabel("DHCP Server: ");
		jlNIS = Utilities.createDeployComponentLabel("NIS Server: ");
		jlNFS = Utilities.createDeployComponentLabel("NFS Server: ");
		jlDirectoryName = Utilities.createDeployComponentLabel("NIS Directory Name: ");
		jlHTTP = Utilities.createDeployComponentLabel("HTTP Server: ");
		jlFTP = Utilities.createDeployComponentLabel("FTP Server: ");
		jlSamba = Utilities.createDeployComponentLabel("Samba Server: ");
		jlMail = Utilities.createDeployComponentLabel("Mail Server: ");
		jlAccounts = Utilities.createDeployComponentLabel("Load User Accounts: ");
		jlExploits = Utilities.createDeployComponentLabel("Load Exploits: ");
		jcbDNS = Utilities.createDeployCheckBox(false);
		jcbDHCP = Utilities.createDeployCheckBox(false);
		jcbNIS = Utilities.createDeployCheckBox(false);
		jcbNFS = Utilities.createDeployCheckBox(false);
		jcbHTTP = Utilities.createDeployCheckBox(false);
		jcbFTP = Utilities.createDeployCheckBox(false);
		jcbMail = Utilities.createDeployCheckBox(false);
		jcbSamba = Utilities.createDeployCheckBox(false);
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
		Panel east = new Panel(layoutEast, false, DIM_DEPLOY_CENTER_SOUTH_EAST);
		Panel west = new Panel(layoutWest, false, DIM_DEPLOY_CENTER_SOUTH_WEST);
		Panel southSouth = new Panel(new FlowLayout(FlowLayout.CENTER), false, DIM_DEPLOY_CENTER_SOUTH_SOUTH);
		
		north.add(jlLinuxServer);
		
		west.add(jlDNS);
		west.add(jcbDNS);
		west.add(jlHTTP);
		west.add(jcbHTTP);
		west.add(jlMail);
		west.add(jcbMail);
		west.add(jlSamba);
		west.add(jcbSamba);
		west.add(jlDirectoryName);
		west.add(jlAccounts);
		west.add(jlExploits);
		layoutWest.putConstraint(SpringLayout.VERTICAL_CENTER, jcbDNS, 15, SpringLayout.NORTH, west);
		layoutWest.putConstraint(SpringLayout.EAST, jcbDNS, -50, SpringLayout.EAST, west);
		layoutWest.putConstraint(SpringLayout.VERTICAL_CENTER, jlDNS, 15, SpringLayout.NORTH, west);
		layoutWest.putConstraint(SpringLayout.EAST, jlDNS, -15, SpringLayout.WEST, jcbDNS);
		layoutWest.putConstraint(SpringLayout.VERTICAL_CENTER, jcbHTTP, 35, SpringLayout.VERTICAL_CENTER, jcbDNS);
		layoutWest.putConstraint(SpringLayout.EAST, jcbHTTP, -50, SpringLayout.EAST, west);
		layoutWest.putConstraint(SpringLayout.VERTICAL_CENTER, jlHTTP, 35, SpringLayout.VERTICAL_CENTER, jlDNS);
		layoutWest.putConstraint(SpringLayout.EAST, jlHTTP, -15, SpringLayout.WEST, jcbHTTP);
		layoutWest.putConstraint(SpringLayout.VERTICAL_CENTER, jcbMail, 35, SpringLayout.VERTICAL_CENTER, jcbHTTP);
		layoutWest.putConstraint(SpringLayout.EAST, jcbMail, -50, SpringLayout.EAST, west);
		layoutWest.putConstraint(SpringLayout.VERTICAL_CENTER, jlMail, 35, SpringLayout.VERTICAL_CENTER, jlHTTP);
		layoutWest.putConstraint(SpringLayout.EAST, jlMail, -15, SpringLayout.WEST, jcbMail);
		layoutWest.putConstraint(SpringLayout.VERTICAL_CENTER, jcbSamba, 35, SpringLayout.VERTICAL_CENTER, jcbMail);
		layoutWest.putConstraint(SpringLayout.EAST, jcbSamba, -50, SpringLayout.EAST, west);
		layoutWest.putConstraint(SpringLayout.VERTICAL_CENTER, jlSamba, 35, SpringLayout.VERTICAL_CENTER, jlMail);
		layoutWest.putConstraint(SpringLayout.EAST, jlSamba, -15, SpringLayout.WEST, jcbSamba);
		layoutWest.putConstraint(SpringLayout.VERTICAL_CENTER, jlDirectoryName, 35, SpringLayout.VERTICAL_CENTER, jcbSamba);
		layoutWest.putConstraint(SpringLayout.EAST, jlDirectoryName, -5, SpringLayout.EAST, west);
		layoutWest.putConstraint(SpringLayout.VERTICAL_CENTER, jlAccounts, 75, SpringLayout.VERTICAL_CENTER, jlDirectoryName);
		layoutWest.putConstraint(SpringLayout.EAST, jlAccounts, -7, SpringLayout.EAST, west);
		layoutWest.putConstraint(SpringLayout.VERTICAL_CENTER, jlExploits, 35, SpringLayout.VERTICAL_CENTER, jlAccounts);
		layoutWest.putConstraint(SpringLayout.EAST, jlExploits, -7, SpringLayout.EAST, west);
		
		east.add(jlDHCP);
		east.add(jcbDHCP);
		east.add(jlFTP);
		east.add(jcbFTP);
		east.add(jlNFS);
		east.add(jcbNFS);
		east.add(jlNIS);
		east.add(jcbNIS);
		east.add(jtfDirectoryName);
		east.add(jcbAccounts);
		east.add(jcbExploits);		
		layoutEast.putConstraint(SpringLayout.VERTICAL_CENTER, jcbDHCP, 15, SpringLayout.NORTH, east);
		layoutEast.putConstraint(SpringLayout.EAST, jcbDHCP, -100, SpringLayout.EAST, east);
		layoutEast.putConstraint(SpringLayout.VERTICAL_CENTER, jlDHCP, 15, SpringLayout.NORTH, east);
		layoutEast.putConstraint(SpringLayout.EAST, jlDHCP, -15, SpringLayout.WEST, jcbDHCP);
		layoutEast.putConstraint(SpringLayout.VERTICAL_CENTER, jcbFTP, 35, SpringLayout.VERTICAL_CENTER, jcbDHCP);
		layoutEast.putConstraint(SpringLayout.EAST, jcbFTP, -100, SpringLayout.EAST, east);
		layoutEast.putConstraint(SpringLayout.VERTICAL_CENTER, jlFTP, 35, SpringLayout.VERTICAL_CENTER, jlDHCP);
		layoutEast.putConstraint(SpringLayout.EAST, jlFTP, -15, SpringLayout.WEST, jcbFTP);
		layoutEast.putConstraint(SpringLayout.VERTICAL_CENTER, jcbNFS, 35, SpringLayout.VERTICAL_CENTER, jcbFTP);
		layoutEast.putConstraint(SpringLayout.EAST, jcbNFS, -100, SpringLayout.EAST, east);
		layoutEast.putConstraint(SpringLayout.VERTICAL_CENTER, jlNFS, 35, SpringLayout.VERTICAL_CENTER, jlFTP);
		layoutEast.putConstraint(SpringLayout.EAST, jlNFS, -15, SpringLayout.WEST, jcbNFS);
		layoutEast.putConstraint(SpringLayout.VERTICAL_CENTER, jcbNIS, 35, SpringLayout.VERTICAL_CENTER, jcbNFS);
		layoutEast.putConstraint(SpringLayout.EAST, jcbNIS, -100, SpringLayout.EAST, east);
		layoutEast.putConstraint(SpringLayout.VERTICAL_CENTER, jlNIS, 35, SpringLayout.VERTICAL_CENTER, jlNFS);
		layoutEast.putConstraint(SpringLayout.EAST, jlNIS, -15, SpringLayout.WEST, jcbNIS);
		layoutEast.putConstraint(SpringLayout.VERTICAL_CENTER, jtfDirectoryName, 35, SpringLayout.VERTICAL_CENTER, jcbNIS);
		layoutEast.putConstraint(SpringLayout.WEST, jtfDirectoryName, 5, SpringLayout.WEST, east);
		layoutEast.putConstraint(SpringLayout.VERTICAL_CENTER, jcbAccounts, 75, SpringLayout.VERTICAL_CENTER, jtfDirectoryName);
		layoutEast.putConstraint(SpringLayout.WEST, jcbAccounts, 7, SpringLayout.WEST, east);
		layoutEast.putConstraint(SpringLayout.VERTICAL_CENTER, jcbExploits, 35, SpringLayout.VERTICAL_CENTER, jcbAccounts);
		layoutEast.putConstraint(SpringLayout.WEST, jcbExploits, 7, SpringLayout.WEST, east);
		
		south.add(west, BorderLayout.WEST);
		south.add(east, BorderLayout.EAST);
		south.add(southSouth, BorderLayout.SOUTH);
		
		add(north, BorderLayout.NORTH);
		add(south, BorderLayout.SOUTH);
	}
}