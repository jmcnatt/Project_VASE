/**
 * Project_VASE Deploy GUI tab package
 */
package vase.client.deploy.gui.tab;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.SpringLayout;

import vase.client.InterfaceConstraints;
import vase.client.Panel;
import vase.client.deploy.utils.IconRenderer;
import vase.client.deploy.vmo.DeployedVirtualMachine;

/**
 * Deployment Entry in the GuiDeploymentTab on the GuiMain
 * @author James McNatt & Brenton Kapral
 * @version Project_VASE Deploy
 * @see GuiDeploymentTab#updatePanel(java.util.ArrayList)
 */
public class DeploymentTabEntry extends Panel implements InterfaceConstraints
{
	private static final long serialVersionUID = -4642996141797957402L;
	private IconRenderer renderer = new IconRenderer();
	
	/**
	 * Constructs an entry with a DeployedVirtualMachine object
	 * @param deployed the deployed virtual machine
	 */
	public DeploymentTabEntry(DeployedVirtualMachine deployed)
	{
		super(new BorderLayout(), false, DIM_MAIN_DEPLOYMENT_TAB_ENTRY);
		Panel north = new Panel(new FlowLayout(FlowLayout.CENTER, 5, 5), false);
		Panel center = new Panel(new SpringLayout(), false);
		setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));
		
		//Title
		JLabel title = createTabTitleLabel(deployed.getOsName());
		title.setIcon(renderer.getIcon(deployed.getOsName()));
		title.setIconTextGap(20);
		title.setHorizontalTextPosition(JLabel.RIGHT);
		north.add(title);
		
		//Center
		JLabel name = createTabLabel("Name: ");
		JLabel team = createTabLabel("Team: ");
		JLabel hostname = createTabLabel("Hostname: ");
		JLabel domain = createTabLabel("Domain: ");
		JLabel ip = createTabLabel("IP Address: ");
		JLabel netmask = createTabLabel("Netmask: ");
		JLabel gateway = createTabLabel("Default Gateway: ");
		JLabel network = createTabLabel("Network Setting: ");
		JLabel services = createTabLabel("Services: ");
		JLabel exploits = createTabLabel("Load Exploits: ");
		JLabel accounts = createTabLabel("Load Accounts: ");
		JLabel jlName = createTabLabel(deployed.getVmName());
		JLabel jlTeam = createTabLabel(deployed.getTeam());
		JLabel jlHostname = createTabLabel(deployed.getHostName());
		JLabel jlDomain = createTabLabel(deployed.getDomain());
		JLabel jlNetwork = createTabLabel((deployed.isStaticAddress()) ? "Static" : "DHCP");
		JLabel jlIP = createTabLabel(deployed.getIpAddr());
		JLabel jlNetmask = createTabLabel(deployed.getNetmask());
		JLabel jlGateway = createTabLabel(deployed.getDefaultGateway());
		JLabel jlServices = createTabLabel(deployed.getServices());
		JLabel jlExploits = createTabLabel((deployed.isExploits()) ? "Yes" : "No");
		JLabel jlAccounts = createTabLabel((deployed.isAccounts()) ? "Yes" : "No");
		
		center.add(name);
		center.add(jlName);
		center.add(team);
		center.add(jlTeam);
		center.add(hostname);
		center.add(jlHostname);
		center.add(domain);
		center.add(jlDomain);
		center.add(network);
		center.add(jlNetwork);
		center.add(accounts);
		center.add(jlAccounts);
		center.add(services);
		center.add(jlServices);
		center.add(exploits);
		center.add(jlExploits);
		center.add(ip);
		center.add(jlIP);
		center.add(netmask);
		center.add(jlNetmask);
		center.add(gateway);
		center.add(jlGateway);
	
		SpringLayout layout = (SpringLayout) center.getLayout();
		
		layout.putConstraint(SpringLayout.VERTICAL_CENTER, name, 15, SpringLayout.NORTH, center);
		layout.putConstraint(SpringLayout.EAST, name, -5, SpringLayout.HORIZONTAL_CENTER, center);
		layout.putConstraint(SpringLayout.VERTICAL_CENTER, hostname, 20, SpringLayout.VERTICAL_CENTER, name);
		layout.putConstraint(SpringLayout.EAST, hostname, -5, SpringLayout.HORIZONTAL_CENTER, center);
		layout.putConstraint(SpringLayout.VERTICAL_CENTER, team, 20, SpringLayout.VERTICAL_CENTER, hostname);
		layout.putConstraint(SpringLayout.EAST, team, -5, SpringLayout.HORIZONTAL_CENTER, center);
		layout.putConstraint(SpringLayout.VERTICAL_CENTER, domain, 20, SpringLayout.VERTICAL_CENTER, team);
		layout.putConstraint(SpringLayout.EAST, domain, -5, SpringLayout.HORIZONTAL_CENTER, center);
		layout.putConstraint(SpringLayout.VERTICAL_CENTER, network, 20, SpringLayout.VERTICAL_CENTER, domain);
		layout.putConstraint(SpringLayout.EAST, network, -5, SpringLayout.HORIZONTAL_CENTER, center);
		layout.putConstraint(SpringLayout.VERTICAL_CENTER, jlName, 15, SpringLayout.NORTH, center);
		layout.putConstraint(SpringLayout.WEST, jlName, 5, SpringLayout.HORIZONTAL_CENTER, center);
		layout.putConstraint(SpringLayout.VERTICAL_CENTER, jlHostname, 20, SpringLayout.VERTICAL_CENTER, jlName);
		layout.putConstraint(SpringLayout.WEST, jlHostname, 5, SpringLayout.HORIZONTAL_CENTER, center);
		layout.putConstraint(SpringLayout.VERTICAL_CENTER, jlTeam, 20, SpringLayout.VERTICAL_CENTER, jlHostname);
		layout.putConstraint(SpringLayout.WEST, jlTeam, 5, SpringLayout.HORIZONTAL_CENTER, center);
		layout.putConstraint(SpringLayout.VERTICAL_CENTER, jlDomain, 20, SpringLayout.VERTICAL_CENTER, jlTeam);
		layout.putConstraint(SpringLayout.WEST, jlDomain, 5, SpringLayout.HORIZONTAL_CENTER, center);
		layout.putConstraint(SpringLayout.VERTICAL_CENTER, jlNetwork, 20, SpringLayout.VERTICAL_CENTER, jlDomain);
		layout.putConstraint(SpringLayout.WEST, jlNetwork, 5, SpringLayout.HORIZONTAL_CENTER, center);
		layout.putConstraint(SpringLayout.VERTICAL_CENTER, ip, 20, SpringLayout.VERTICAL_CENTER, network);
		layout.putConstraint(SpringLayout.EAST, ip, -5, SpringLayout.HORIZONTAL_CENTER, center);
		layout.putConstraint(SpringLayout.VERTICAL_CENTER, netmask, 20, SpringLayout.VERTICAL_CENTER, ip);
		layout.putConstraint(SpringLayout.EAST, netmask, -5, SpringLayout.HORIZONTAL_CENTER, center);
		layout.putConstraint(SpringLayout.VERTICAL_CENTER, gateway, 20, SpringLayout.VERTICAL_CENTER, netmask);
		layout.putConstraint(SpringLayout.EAST, gateway, -5, SpringLayout.HORIZONTAL_CENTER, center);
		layout.putConstraint(SpringLayout.VERTICAL_CENTER, jlIP, 20, SpringLayout.VERTICAL_CENTER, network);
		layout.putConstraint(SpringLayout.WEST, jlIP, 5, SpringLayout.HORIZONTAL_CENTER, center);
		layout.putConstraint(SpringLayout.VERTICAL_CENTER, jlNetmask, 20, SpringLayout.VERTICAL_CENTER, jlIP);
		layout.putConstraint(SpringLayout.WEST, jlNetmask, 5, SpringLayout.HORIZONTAL_CENTER, center);
		layout.putConstraint(SpringLayout.VERTICAL_CENTER, jlGateway, 20, SpringLayout.VERTICAL_CENTER, jlNetmask);
		layout.putConstraint(SpringLayout.WEST, jlGateway, 5, SpringLayout.HORIZONTAL_CENTER, center);
		layout.putConstraint(SpringLayout.VERTICAL_CENTER, services, 20, SpringLayout.VERTICAL_CENTER, gateway);
		layout.putConstraint(SpringLayout.EAST, services, -5, SpringLayout.HORIZONTAL_CENTER, center);
		layout.putConstraint(SpringLayout.VERTICAL_CENTER, accounts, 20, SpringLayout.VERTICAL_CENTER, services);
		layout.putConstraint(SpringLayout.EAST, accounts, -5, SpringLayout.HORIZONTAL_CENTER, center);
		layout.putConstraint(SpringLayout.VERTICAL_CENTER, exploits, 20, SpringLayout.VERTICAL_CENTER, accounts);
		layout.putConstraint(SpringLayout.EAST, exploits, -5, SpringLayout.HORIZONTAL_CENTER, center);
		layout.putConstraint(SpringLayout.VERTICAL_CENTER, jlServices, 20, SpringLayout.VERTICAL_CENTER, jlGateway);
		layout.putConstraint(SpringLayout.WEST, jlServices, 5, SpringLayout.HORIZONTAL_CENTER, center);
		layout.putConstraint(SpringLayout.VERTICAL_CENTER, jlAccounts, 20, SpringLayout.VERTICAL_CENTER, jlServices);
		layout.putConstraint(SpringLayout.WEST, jlAccounts, 5, SpringLayout.HORIZONTAL_CENTER, center);
		layout.putConstraint(SpringLayout.VERTICAL_CENTER, jlExploits, 20, SpringLayout.VERTICAL_CENTER, jlAccounts);
		layout.putConstraint(SpringLayout.WEST, jlExploits, 5, SpringLayout.HORIZONTAL_CENTER, center);
		
		add(north, BorderLayout.NORTH);
		add(center, BorderLayout.CENTER);
	}
	
	/**
	 * Creates a general lab used in the Last Deployment Tab
	 * @param text the text for the label
	 * @return the pre-formatted label
	 */
	public JLabel createTabTitleLabel(String text)
	{
		JLabel label = new JLabel(text);
		label.setFont(FONT_LABEL_HEADER);
		label.setOpaque(false);
		
		return label;
	}
	
	/**
	 * Creates a general lab used in the Last Deployment Tab
	 * @param text the text for the label
	 * @return the pre-formatted label
	 */
	public JLabel createTabLabel(String text)
	{
		JLabel label = new JLabel(text);
		label.setOpaque(false);
		
		return label;
	}
}
