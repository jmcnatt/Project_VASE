/**
 * Project_VASE Deploy package
 */
package vase.client.deploy;

import java.awt.CardLayout;

import vase.client.Panel;

/**
 * Center panel for the Deploy Wizard
 * <br />
 * Contains the card layout and the identifies to switch screens
 * @author James McNatt & Brenton Kapral
 * @version Project_VASE Deploy
 * @see DeployWizard
 */
public class DeployWizardCenterPanel extends Panel
{
	private static final long serialVersionUID = -5632199886024884177L;

	/**
	 * Welcome Panel used in modes 3, 4
	 * Asks for the number of VMs
	 * @see DeployWizardWelcomePanel
	 */
	public DeployWizardWelcomePanel welcomePanel;
	
	/**
	 * OS Selection Panel used in modes 1, 3, 4
	 * @see DeployWizardWelcomePanel
	 */
	public DeployWizardOSPanel osPanel;
	
	/**
	 * Guest Information panel collecting hostname, domain, and network information
	 * @see DeployWizardGuestInfoPanel
	 */
	public DeployWizardGuestInfoPanel guestInfoPanel;
	
	/**
	 * Windows Client service panel in modes 1, 2, 3, 4
	 * @see DeployWizardWindowsClientPanel
	 */
	public DeployWizardWindowsClientPanel windowsClientPanel;
	
	/**
	 * Windows Server service panel in modes 1, 2, 3, 4
	 * @see DeployWizardWindowsServerPanel
	 */
	public DeployWizardWindowsServerPanel windowsServerPanel;
	
	/**
	 * Linux Client service panel in modes 1, 2, 3, 4
	 * @see DeployWizardLinuxClientPanel
	 */
	public DeployWizardLinuxClientPanel linuxClientPanel;
	
	/**
	 * Linux Server service panel in modes 1, 2, 3, 4
	 * @see DeployWizardLinuxServerPanel
	 */
	public DeployWizardLinuxServerPanel linuxServerPanel;
	
	/**
	 * Summary panel for every mode before completion of the wizard
	 * @see DeployWizardSummaryPanel
	 */
	public DeployWizardSummaryPanel summaryPanel;
	
	/**
	 * CardLayout identifier for the welcome panel
	 */
	public static final String WELCOME = "welcome";
	
	/**
	 * CardLayout identifier for the selectOS panel
	 */
	public static final String SELECTOS = "selectOS";
	
	/**
	 * CardLayout identifier for the guest information panel
	 */
	public static final String GUEST_INFO = "guestinfo";
	
	/**
	 * CardLayout identifier for the Windows Client panel
	 */
	public static final String WIN_CLIENT = "winclient";
	
	/**
	 * CardLayout identifier for the Windows Server panel
	 */
	public static final String WIN_SERVER = "winserver";
	
	/**
	 * CardLayout identifier for the Linux Client panel
	 */
	public static final String LINUX_CLIENT = "linuxcilent";
	
	/**
	 * CardLayout identifier for the Linux Server panel
	 */
	public static final String LINUX_SERVER = "linuxserver";
	
	/**
	 * CardLayout identifier for the Summary panel
	 */
	public static final String SUMMARY = "summary";
	
	/**
	 * The current state of the cardlayout
	 */
	public String currentState;
	
	/**
	 * The CardLayout responsible for showing all panels called by the ActionListener
	 * @see DeployWizardActionListener
	 * @see DeployWizard
	 */
	private CardLayout layout;
	
	/**
	 * Main Constructor
	 */
	public DeployWizardCenterPanel()
	{
		super(new CardLayout(), false, DIM_DEPLOY_CENTER);
		
		makeItems();
		makePanels();
	}
	
	/**
	 * Makes the attributes for this class
	 */
	private void makeItems()
	{
		layout = (CardLayout) getLayout();
		welcomePanel = new DeployWizardWelcomePanel();
		osPanel = new DeployWizardOSPanel();
		guestInfoPanel = new DeployWizardGuestInfoPanel();
		windowsClientPanel = new DeployWizardWindowsClientPanel();
		windowsServerPanel = new DeployWizardWindowsServerPanel();
		linuxClientPanel = new DeployWizardLinuxClientPanel();
		linuxServerPanel = new DeployWizardLinuxServerPanel();
		summaryPanel = new DeployWizardSummaryPanel();		
	}
	
	/**
	 * Adds the attributes to sub panels and adds them to this panel
	 */
	private void makePanels()
	{
		add(welcomePanel, WELCOME);
		add(osPanel, SELECTOS);
		add(guestInfoPanel, GUEST_INFO);
		add(windowsClientPanel, WIN_CLIENT);
		add(windowsServerPanel, WIN_SERVER);
		add(linuxClientPanel, LINUX_CLIENT);
		add(linuxServerPanel, LINUX_SERVER);
		add(summaryPanel, SUMMARY);
	}
	
	/**
	 * Changes which panel the card layout is showing
	 * @param identifier the card layout identifier
	 */
	public void show(String identifier)
	{
		layout.show(this, identifier);
		currentState = identifier;
	}
	
	/**
	 * Resets all text fields, check boxes, combo boxes, etc to their original starting stance
	 * <br />
	 * Called whenever the current OS number and the value of deployOS do not match, meaning
	 * there are more OS's to deploy and the wizard must repeat
	 */
	public void resetFields()
	{
		//SELECTOS screen
		osPanel.jcbOsSelection.setSelectedIndex(0);
		osPanel.jcbCategory.setSelectedIndex(0);
		
		//GUESTINFO screen
		guestInfoPanel.jtfVMName.setText("");
		guestInfoPanel.jtfHostname.setText("");
		guestInfoPanel.jtfIP.setText("");
		guestInfoPanel.jtfDomainName.setText("");
		guestInfoPanel.jrbStatic.setSelected(true);
		
		//Windows Server & Client
		windowsServerPanel.jcbDNS.setSelected(false);
		windowsServerPanel.jcbDHCP.setSelected(false);
		windowsServerPanel.jcbActiveDirectory.setSelected(false);
		windowsServerPanel.jcbFileServer.setSelected(false);
		windowsServerPanel.jcbIIS.setSelected(false);
		windowsServerPanel.jcbAccounts.setSelected(false);
		windowsServerPanel.jcbExploits.setSelected(false);
		windowsClientPanel.jcbBind.setSelected(false);
		windowsClientPanel.jcbAccounts.setSelected(false);
		windowsClientPanel.jcbExploits.setSelected(false);
		
		//Linux/BSD Server & Client
		linuxServerPanel.jcbDNS.setSelected(false);
		linuxServerPanel.jcbNFS.setSelected(false);
		linuxServerPanel.jcbHTTP.setSelected(false);
		linuxServerPanel.jcbFTP.setSelected(false);
		linuxServerPanel.jcbMail.setSelected(false);
		linuxServerPanel.jcbSamba.setSelected(false);
		linuxServerPanel.jcbDHCP.setSelected(false);
		linuxServerPanel.jcbAccounts.setSelected(false);
		linuxServerPanel.jcbExploits.setSelected(false);
		linuxClientPanel.jcbBind.setSelected(false);
		linuxClientPanel.jcbAccounts.setSelected(false);
		linuxClientPanel.jcbExploits.setSelected(false);
	}
}
