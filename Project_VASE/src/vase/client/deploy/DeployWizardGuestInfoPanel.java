/**
 * Project_VASE Deploy package
 */
package vase.client.deploy;

import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.ButtonGroup;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;
import javax.swing.SpringLayout;

import vase.client.Panel;
import vase.client.Utilities;

/**
 * Guest information panel collecting hostname, domain, and network information
 * Used in modes 1, 2, 3 & 4
 * @author James McNatt & Brenton Kapral
 * @version Project_VASE Deploy
 */
public class DeployWizardGuestInfoPanel extends Panel
{
	private static final long serialVersionUID = 5988589241422829699L;
	
	/**
	 * Label - VM name
	 */
	public JLabel jlVMName;
	
	/**
	 * Label - VM Hostname
	 */
	public JLabel jlHostname;
	
	/**
	 * Label - VM Domain Name
	 */
	public JLabel jlDomainName;
	
	/**
	 * Label - Network Settings
	 */
	public JLabel jlNetwork;
	
	/**
	 * Label - IP address
	 */
	public JLabel jlIP;
	
	/**
	 * Label - Subnet Mask
	 */
	public JLabel jlNetmask;
	
	/**
	 * Label - Default Gateway
	 */
	public JLabel jlDefaultGateway;
	
	/**
	 * Label - DNS Server
	 */
	public JLabel jlDnsServer;
	
	/**
	 * Label - Virtual Networks
	 */
	public JLabel jlVirtualNetworks;
	
	/**
	 * Text field - VM Name
	 */
	public JTextField jtfVMName;
	
	/**
	 * Text field - VM hostname
	 */
	public JTextField jtfHostname;
	
	/**
	 * Text field - VM Domain Name
	 */
	public JTextField jtfDomainName;
	
	/**
	 * Text field - IP address
	 */
	public JTextField jtfIP;
	
	/**
	 * Text field - Subnet Mask
	 */
	public JTextField jtfNetmask;
	
	/**
	 * Text field - Default gateway
	 */
	public JTextField jtfDefaultGateway;
	
	/**
	 * Radio button - Static network settings
	 */
	public JRadioButton jrbStatic;
	
	/**
	 * Radio buton - DHCP network settings
	 */
	public JRadioButton jrbDHCP;
	
	/**
	 * Text Field - DNS server address
	 */
	public JTextField jtfDnsServer;
	
	/**
	 * Combo box - virtual network list
	 */
	public JComboBox jcbVirtualNetworks;

	/**
	 * Main Constructor
	 */
	public DeployWizardGuestInfoPanel()
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
		ButtonGroup group = new ButtonGroup();
		jlVMName = Utilities.createDeployComponentLabel("VM Name: ");
		jlHostname = Utilities.createDeployComponentLabel("Hostname: ");
		jlDomainName = Utilities.createDeployComponentLabel("Domain Name: ");
		jlNetwork = Utilities.createDeployComponentLabel("Network Settings: ");
		jlIP = Utilities.createDeployComponentLabel("IP Address: ");
		jlNetmask = Utilities.createDeployComponentLabel("Subnet Mask: ");
		jlDefaultGateway = Utilities.createDeployComponentLabel("Default Gateway: ");
		jlDnsServer = Utilities.createDeployComponentLabel("DNS Server: ");
		jlVirtualNetworks = Utilities.createDeployComponentLabel("Virtual Network: ");
		jtfVMName = Utilities.createDeployTextField("");
		jtfHostname = Utilities.createDeployTextField("");
		jtfDomainName = Utilities.createDeployTextField("");
		jtfIP = Utilities.createDeployTextField("");
		jtfNetmask = Utilities.createDeployTextField("255.255.255.0");
		jtfDefaultGateway = Utilities.createDeployTextField("");
		jtfDnsServer = Utilities.createDeployTextField("");
		jrbStatic = Utilities.createDeployRadioButton(group, "Static", true);
		jrbDHCP = Utilities.createDeployRadioButton(group, "DHCP", false);
		jcbVirtualNetworks = Utilities.createDeployComboBox(null, 0, DIM_DEPLOY_COMBO_BOX_SELECT);
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
		
		north.add(Utilities.createDeployLabel(DEPLOY_GUESTINFO));
		
		west.add(jlVMName);
		west.add(jlHostname);
		west.add(jlDomainName);
		west.add(jlNetwork);
		west.add(jlIP);
		west.add(jlNetmask);
		west.add(jlDefaultGateway);
		west.add(jlDnsServer);
		west.add(jlVirtualNetworks);
		layoutWest.putConstraint(SpringLayout.VERTICAL_CENTER, jlVMName, 15, SpringLayout.NORTH, west);
		layoutWest.putConstraint(SpringLayout.EAST, jlVMName, -20, SpringLayout.EAST, west);
		layoutWest.putConstraint(SpringLayout.VERTICAL_CENTER, jlHostname, 30, SpringLayout.VERTICAL_CENTER, jlVMName);
		layoutWest.putConstraint(SpringLayout.EAST, jlHostname, -20, SpringLayout.EAST, west);
		layoutWest.putConstraint(SpringLayout.VERTICAL_CENTER, jlDomainName, 30, SpringLayout.VERTICAL_CENTER, jlHostname);
		layoutWest.putConstraint(SpringLayout.EAST, jlDomainName, -20, SpringLayout.EAST, west);
		layoutWest.putConstraint(SpringLayout.VERTICAL_CENTER, jlNetwork, 30, SpringLayout.VERTICAL_CENTER, jlDomainName);
		layoutWest.putConstraint(SpringLayout.EAST, jlNetwork, -20, SpringLayout.EAST, west);
		layoutWest.putConstraint(SpringLayout.VERTICAL_CENTER, jlIP, 30, SpringLayout.VERTICAL_CENTER, jlNetwork);
		layoutWest.putConstraint(SpringLayout.EAST, jlIP, -20, SpringLayout.EAST, west);
		layoutWest.putConstraint(SpringLayout.VERTICAL_CENTER, jlNetmask, 30, SpringLayout.VERTICAL_CENTER, jlIP);
		layoutWest.putConstraint(SpringLayout.EAST, jlNetmask, -20, SpringLayout.EAST, west);
		layoutWest.putConstraint(SpringLayout.VERTICAL_CENTER, jlDefaultGateway, 30, SpringLayout.VERTICAL_CENTER, jlNetmask);
		layoutWest.putConstraint(SpringLayout.EAST, jlDefaultGateway, -20, SpringLayout.EAST, west);
		layoutWest.putConstraint(SpringLayout.VERTICAL_CENTER, jlDnsServer, 30, SpringLayout.VERTICAL_CENTER, jlDefaultGateway);
		layoutWest.putConstraint(SpringLayout.EAST, jlDnsServer, -20, SpringLayout.EAST, west);
		layoutWest.putConstraint(SpringLayout.VERTICAL_CENTER, jlVirtualNetworks, 30, SpringLayout.VERTICAL_CENTER, jlDnsServer);
		layoutWest.putConstraint(SpringLayout.EAST, jlVirtualNetworks, -20, SpringLayout.EAST, west);
		
		east.add(jtfVMName);
		east.add(jtfHostname);
		east.add(jtfDomainName);
		east.add(jtfIP);
		east.add(jtfNetmask);
		east.add(jtfDefaultGateway);
		east.add(jrbDHCP);
		east.add(jrbStatic);
		east.add(jtfDnsServer);
		east.add(jcbVirtualNetworks);
		layoutEast.putConstraint(SpringLayout.VERTICAL_CENTER, jtfVMName, 15, SpringLayout.NORTH, east);
		layoutEast.putConstraint(SpringLayout.WEST, jtfVMName, 10, SpringLayout.WEST, east);
		layoutEast.putConstraint(SpringLayout.VERTICAL_CENTER, jtfHostname, 30, SpringLayout.VERTICAL_CENTER, jtfVMName);
		layoutEast.putConstraint(SpringLayout.WEST, jtfHostname, 10, SpringLayout.WEST, east);
		layoutEast.putConstraint(SpringLayout.VERTICAL_CENTER, jtfDomainName, 30, SpringLayout.VERTICAL_CENTER, jtfHostname);
		layoutEast.putConstraint(SpringLayout.WEST, jtfDomainName, 10, SpringLayout.WEST, east);
		layoutEast.putConstraint(SpringLayout.VERTICAL_CENTER, jrbStatic, 30, SpringLayout.VERTICAL_CENTER, jtfDomainName);
		layoutEast.putConstraint(SpringLayout.WEST, jrbStatic, 10, SpringLayout.WEST, east);
		layoutEast.putConstraint(SpringLayout.VERTICAL_CENTER, jrbDHCP, 30, SpringLayout.VERTICAL_CENTER, jtfDomainName);
		layoutEast.putConstraint(SpringLayout.WEST, jrbDHCP, 10, SpringLayout.EAST, jrbStatic);
		layoutEast.putConstraint(SpringLayout.VERTICAL_CENTER, jtfIP, 30, SpringLayout.VERTICAL_CENTER, jrbStatic);
		layoutEast.putConstraint(SpringLayout.WEST, jtfIP, 10, SpringLayout.WEST, east);
		layoutEast.putConstraint(SpringLayout.VERTICAL_CENTER, jtfNetmask, 30, SpringLayout.VERTICAL_CENTER, jtfIP);
		layoutEast.putConstraint(SpringLayout.WEST, jtfNetmask, 10, SpringLayout.WEST, east);
		layoutEast.putConstraint(SpringLayout.VERTICAL_CENTER, jtfDefaultGateway, 30, SpringLayout.VERTICAL_CENTER, jtfNetmask);
		layoutEast.putConstraint(SpringLayout.WEST, jtfDefaultGateway, 10, SpringLayout.WEST, east);
		layoutEast.putConstraint(SpringLayout.VERTICAL_CENTER, jtfDnsServer, 30, SpringLayout.VERTICAL_CENTER, jtfDefaultGateway);
		layoutEast.putConstraint(SpringLayout.WEST, jtfDnsServer, 10, SpringLayout.WEST, east);
		layoutEast.putConstraint(SpringLayout.VERTICAL_CENTER, jcbVirtualNetworks, 30, SpringLayout.VERTICAL_CENTER, jtfDnsServer);
		layoutEast.putConstraint(SpringLayout.WEST, jcbVirtualNetworks, 10, SpringLayout.WEST, east);
		
		southSouth.add(Utilities.createDeployLabel("Note: Virtual Machine may be not be remotely accessible if DHCP is enabled"));
		
		south.add(west, BorderLayout.WEST);
		south.add(east, BorderLayout.EAST);
		south.add(southSouth, BorderLayout.SOUTH);
		
		add(north, BorderLayout.NORTH);
		add(south, BorderLayout.SOUTH);
	}
}
