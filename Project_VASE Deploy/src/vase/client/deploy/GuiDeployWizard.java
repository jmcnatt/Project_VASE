/**
 * Project_VASE Deploy package
 */
package vase.client.deploy;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.ArrayList;

import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SpringLayout;

/**
 * Deployment Wizard, prompting the user for input regarding a deployment
 * <br />
 * Steps include:
 * <ul>
 * <li>Select number of OS's</li>
 * <li>Select OS Category / OS Name</li>
 * <li>Configure guest information</li>
 * <li>Specify Services</li>
 * <li>Review and finish</li>
 * <br />
 * This class handles the entire wizard.  Create an object of this class to load the
 * wizard, dipose the frame when complete.
 * <br />
 * <strong>Note: </strong>Extends GuiWindow
 * @author James McNatt & Brenton Kapral
 * @version Project_VASE Deploy
 */

public class GuiDeployWizard extends GuiWindow implements ActionListener, ItemListener
{
	private static final long serialVersionUID = 299367728052075948L;

	private CommandEngine engine;						//Command Engine for deployment
	
	//These are CardLayout components & wizard logic controls
	private int deployCount = 1;						//Number of VMs to Deploy
	private int deploymentMode = 1;						//Deployment mode setting (vm, group, team)
	private int stepNumber = 1;							//The Step number the user is on
	private int osNumber = 1;							//The OS the wizard is currently on (out of total number)
	private int stepTotal = 5;							//The total number of steps in the wizard
	private String currentState = WELCOME;				//The current state of the CardLayout
	private String choosenOSCategory;					//The chosen OS's category (Windows, linux, etc)
	private String choosenOSType;						//The chosen OS's type (CLIENT or SERVER)
	private Template template;							//If deploying from template, use this
	
	//Wizard Swing components
	private CardLayout centerLayout;					//The CardLayout
	private JComboBox jcbCount;							//ComboBox - number of VMs
	private JComboBox jcbCategory;						//ComboBox - category of OS
	private JComboBox jcbOS;							//ComboBox - specific OS
	private JComboBox jcbTeam;							//ComboBox - Team selection
	private JComboBox jcbVirtualNetworks;				//ComboBox - Virtual Networks for VM
	private JTextField jtfVMName;						//TextField - name of VM
	private JTextField jtfHostname;						//TextField - hostname of VM
	private JTextField jtfDomainName;					//TextField - domain name of VM
	private JTextField jtfIP;							//TextField - IP address of VM
	private JTextField jtfNetmask;						//TextField - Subnet mask of VM
	private JTextField jtfDefaultGateway;				//TextField - Default gateway of VM
	private JTextField jtfDnsServer;					//TextField - DNS Server of VM
	private JTextField jtfDirectoryName;				//TextField - Active Directory Domain Name
	private JTextField jtfNISName;						//TextField - NIS name
	private JTextField jtfNISBind;						//TextField - name of domain for NIS bind
	private JTextField jtfDirectoryBind;				//TextField - name of domain for AD bind
	private JRadioButton jrbStatic, jrbDHCP;			//RadioButton - static IP or DHCP
	private JButton jbNext;								//Button - Next
	private JButton jbFinish;							//Button - Finish (only enabled at the end)
	private JButton jbSave;								//Button - Save (only visible at the end)
	private JButton jbCancel;							//Button - Cancel
	private JPanel jpCenter;							//Panel containing the cardLayout
	private JLabel jlTitle;								//Label - Shows the title of the wizard
	private JLabel jlSteps;								//Label - Shows number of steps in the wizard
	private JLabel jlOSValid;							//Label - Shows if OS is a valid template
	private JLabel jlOS;								//Label - Select Operating System
	private JLabel jlDirectoryName;						//Label - Windows Server Active Directory Name
	private JLabel jlNISName;							//Label - Linux/BSD Server NIS Domain Name
	private JLabel jlNISBind;							//Label - Linux/BSD Client Bind to NIS
	private JLabel jlDirectoryBind;						//Label - Windows Client Bind to Active Directory
	private JLabel jlTeam;								//Label - Select Team
	private JSeparator jsBarTop, jsBarBottom;			//JSeparators
	private JCheckBox jCheckDNS;						//DNS
	private JCheckBox jCheckDHCP;						//DHCP
	private JCheckBox jCheckActiveDirectory;			//Active Directory
	private JCheckBox jCheckFileServer;					//File Server role
	private JCheckBox jCheckIIS;						//IIS
	private JCheckBox jCheckAccounts;					//Load a set of user accounts
	private JCheckBox jCheckAccountsClient;				//Load a set of user accounts (client)
	private JCheckBox jCheckExploits;					//Load Exploits (Windows Server)
	private JCheckBox jCheckClientExploits;				//Load Exploits (Windows Client)
	private JCheckBox jCheckBind;						//Bind to NIS or AD
	private JCheckBox jLinuxNFS;						//NFS
	private JCheckBox jLinuxNIS;						//NIS
	private JCheckBox jLinuxHTTP;						//HTTP
	private JCheckBox jLinuxFTP;						//FTP
	private JCheckBox jLinuxMail;						//Email services
	private JCheckBox jLinuxDNS;						//DNS (Linux)
	private JCheckBox jLinuxDHCP;						//DHCP (Linux)
	private JCheckBox jLinuxBind;						//Bind NIS client to server			
	private JCheckBox jLinuxAccounts;					//Load Accounts (linux server)
	private JCheckBox jLinuxAccountsClient;				//Load accounts (linux client)
	private JCheckBox jLinuxSamba;						//Samba
	private JCheckBox jLinuxExploits;					//Load Exploits (Linux Server)
	private JCheckBox jLinuxClientExploits;				//Load Exploits (Linux Client)
	private JTextArea jtaDeploySummary;					//Deployment summary field
	
	//CardLayout identifiers
	private static final String SELECTOS = "selectos";
	private static final String GUESTINFO = "guestinfo";
	private static final String WELCOME = "welcome";
	private static final String SERVICES_WINDOWS_SERVER = "servicesWindowsServer";
	private static final String SERVICES_WINDOWS_CLIENT = "servicesWindowsClient";
	private static final String SERVICES_LINUX_SERVER = "servicesLinuxServer";
	private static final String SERVICES_LINUX_CLIENT = "servicesLinuxClient";
	private static final String DEPLOYMENT_SUMMARY = "deploymentSummary";
	
	//Deployment Modes
	/**
	 * Used to show the deployment wizard with only one VM, skips the first steps
	 */
	public static final int SINGLE_VM_MODE = 1;
	
	/**
	 * Used to show the deployment wizard with all steps; multiple VMs supported
	 */
	public static final int GROUP_VM_MODE = 2;
	
	/**
	 * Used to show the wizard from a template, skips the first two steps
	 */
	public static final int CHOOSEN_VM_MODE = 3;
	
	/**
	 * Used to show the deployment wizard in group mode with team support
	 */
	public static final int TEAM_MODE = 4;
	
	//Final collection of deployed virtual machines
	private ArrayList<DeployedVirtualMachine> virtualMachines;
	
	/**
	 * Main Constructor for the Deploy Wizard
	 * <br />
	 * Sets the dimensions of the window, sets the background, and calls the base methods
	 * to build the entire user interface
	 * <ul>
	 * <li>makeItems() - defines the attributes</li>
	 * <li>makePanels() - builds the layout and JPanels</li>
	 * <li>addListeners() - adds the ActionListeners and ItemListeners to the attributes</li>
	 * <li>setMnemonics() - sets the mnemonics on all items</li>
	 * <br />
	 * Then sets visible to true once the GUI has been made
	 * @see GuiDeployWizard#makeItems()
	 * @see GuiDeployWizard#makePanels()
	 * @see GuiDeployWizard#addListeners()
	 * @see GuiDeployWizard#setMnemonics()
	 */

	public GuiDeployWizard(CommandEngine engine, int deploymentMode, Template template)
	{
		super("New Deployment Wizard");
		this.deploymentMode = deploymentMode;
		this.template = template;
		this.engine = engine;
		setAlwaysOnTop(true);
		setSize(DIM_DEPLOY_WINDOW);
		getContentPane().setPreferredSize(DIM_DEPLOY_WINDOW);
		getContentPane().setMaximumSize(DIM_DEPLOY_WINDOW);
		getContentPane().setMinimumSize(DIM_DEPLOY_WINDOW);
		getContentPane().setBackground(COLOR_WIZARD_BACKGROUND);
		setLocationRelativeTo(null);
		setResizable(false);
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		
		//Adjust the steps depending on the mode
		if (deploymentMode == SINGLE_VM_MODE) stepTotal--;
		if (deploymentMode == CHOOSEN_VM_MODE) stepTotal = stepTotal - 2;
		
		makeItems();
		makePanels();
		addListeners();
		setMnemonics();
		setVisible(true);
		LOG.write("Starting New Deployment Wizard");
		jbNext.requestFocus();
	}
	
	/**
	 * Makes the attributes, called by the main constructor
	 */
	private void makeItems()
	{
		jbNext = createDeployMainButton("Next");
		jbCancel = createDeployMainButton("Cancel");
		jbFinish = createDeployMainButton("Finish");
		jbSave = createDeployMainButton("Save");
		jlTitle = createDeployTitleLabel("Welcome to the VASE Deployment Wizard");
		jlSteps = createDeployComponentLabel("Step " + stepNumber+ " of " + stepTotal);
		jlOS = createDeployComponentLabel("Operating System: ");
		jlDirectoryName = createDeployComponentLabel("Domain Name: ");
		jlNISName = createDeployComponentLabel("Domain Name: ");
		jlNISBind = createDeployComponentLabel("Domain Name: ");
		jlDirectoryBind = createDeployComponentLabel("Domain Name: ");
		jlOSValid = createDeploySubLabel(" ");
		jlTeam = createDeployComponentLabel("Team: ");
		
		//Separator bars for the wizard's body
		jsBarTop = new JSeparator();
		jsBarTop.setForeground(Color.GRAY);
		jsBarBottom = new JSeparator();
		jsBarBottom.setForeground(Color.GRAY);
		
		//Set the enabled buttons
		if (deploymentMode == SINGLE_VM_MODE)
		{
			jbNext.setEnabled(false);
		}
		
		else
		{
			jbNext.setEnabled(true);
		}
		
		jbCancel.setEnabled(true);
		jbSave.setVisible(false);
		
		//set the OS label & osValid label to not visible
		jlOSValid.setVisible(false);
		jlOS.setVisible(false);
		
		//make Virtual Machine deploy number combo box
		jcbCount = createDeployComboBox();
		for (int i = 1; i <= MAX_VM_DEPLOY; i++)
		{
			jcbCount.addItem(Integer.toString(i));
		}
		jcbCount.setSelectedIndex(0);
		jcbCount.setMinimumSize(DIM_DEPLOY_COMBO_BOX_NUMBER);
		jcbCount.setPreferredSize(DIM_DEPLOY_COMBO_BOX_NUMBER);
		
		//make the team combo box
		jcbTeam = createDeployComboBox(TEAM_NAMES);
		jcbTeam.setSelectedIndex(0);
		jcbTeam.setPreferredSize(DIM_DEPLOY_COMBO_BOX_NUMBER);
		jcbTeam.setMaximumSize(DIM_DEPLOY_COMBO_BOX_NUMBER);
		jcbTeam.setMinimumSize(DIM_DEPLOY_COMBO_BOX_NUMBER);
		
		//make the Virtual Machine category combo box
		jcbCategory = createDeployComboBox(OPERATING_SYSTEM_TYPES);
		jcbCategory.setSelectedItem(" ");
		jcbCategory.setPreferredSize(DIM_DEPLOY_COMBO_BOX_SELECT);
		jcbCategory.setMaximumSize(DIM_DEPLOY_COMBO_BOX_SELECT);
		jcbCategory.setMinimumSize(DIM_DEPLOY_COMBO_BOX_SELECT);
		
		//make the Virtual Machine operating system blank combo box
		jcbOS = createDeployComboBox();
		jcbOS.setVisible(false);
		jcbOS.setPreferredSize(DIM_DEPLOY_COMBO_BOX_SELECT);
		jcbOS.setMaximumSize(DIM_DEPLOY_COMBO_BOX_SELECT);
		jcbOS.setMinimumSize(DIM_DEPLOY_COMBO_BOX_SELECT);
		
		//Make the Team Fields not visible
		jlTeam.setVisible(false);
		jcbTeam.setVisible(false);
		
		//TextFields
		jtfVMName = createDeployTextField("");
		jtfHostname = createDeployTextField("localhost");
		jtfDomainName = createDeployTextField("localdomain");
		jtfIP = createDeployTextField("");
		jtfNetmask = createDeployTextField("255.255.255.0");
		jtfDefaultGateway = createDeployTextField("");
		jtfDnsServer = createDeployTextField("");
		jtfDirectoryName = createDeployTextField("");
		jtfNISName = createDeployTextField("");
		jtfDirectoryBind = createDeployTextField("");
		jtfNISBind = createDeployTextField("");
		jcbVirtualNetworks = createDeployComboBox(engine.getNetworksAsStrings());
		
		//Check Boxes
		jCheckDNS = createDeployCheckBox();
		jCheckDHCP = createDeployCheckBox();
		jCheckActiveDirectory = createDeployCheckBox();
		jCheckFileServer = createDeployCheckBox();
		jCheckIIS = createDeployCheckBox();
		jCheckAccounts = createDeployCheckBox();
		jCheckAccountsClient = createDeployCheckBox();
		jCheckExploits = createDeployCheckBox();
		jCheckClientExploits = createDeployCheckBox();
		jCheckBind = createDeployCheckBox();
		jLinuxNFS = createDeployCheckBox();
		jLinuxNIS = createDeployCheckBox();
		jLinuxHTTP = createDeployCheckBox();
		jLinuxFTP = createDeployCheckBox();
		jLinuxDNS = createDeployCheckBox();
		jLinuxDHCP = createDeployCheckBox();
		jLinuxMail = createDeployCheckBox();
		jLinuxAccounts = createDeployCheckBox();
		jLinuxAccountsClient = createDeployCheckBox();
		jLinuxExploits = createDeployCheckBox();
		jLinuxClientExploits = createDeployCheckBox();
		jLinuxSamba = createDeployCheckBox();
		jLinuxBind = createDeployCheckBox();
		
		//Set Windows Server Active Directory Domain Name to not visible
		jlDirectoryName.setVisible(false);
		jtfDirectoryName.setVisible(false);
		
		//Set Linux NIS Server name to not visible
		jlNISName.setVisible(false);
		jtfNISName.setVisible(false);
		
		//Set Windows Client Active Directory domain name to not visible
		jlDirectoryBind.setVisible(false);
		jtfDirectoryBind.setVisible(false);
		
		//Set Linux NIS Client name to not visible
		jlNISBind.setVisible(false);
		jtfNISBind.setVisible(false);
		
		//make the ArrayList to hold the VMs to deploy
		virtualMachines = new ArrayList<DeployedVirtualMachine>();
		
		jtaDeploySummary = createDeployTextArea();
	}
	
	/**
	 * Creates the primary JPanels displayed in the JFrame's main container.
	 * <br />
	 * The main container uses BorderLayout and contains the following JPanels:
	 * <ul>
	 * <li>NORTH - jpNorth (wizard title, logo, step count)</li>
	 * <li>CENTER - jpCenter with CardLayout (body of wizard that changes with steps)</li>
	 * <li>SOUTH - jpSouth (buttons)
	 * </ul>
	 * @see GuiDeployWizard#createNorthPanel()
	 * @see GuiDeployWizard#createDeployWelcome()
	 * @see GuiDeployWizard#createDeploySelectOS()
	 * @see GuiDeployWizard#createDeployGuestInfo()
	 * @see GuiDeployWizard#createDeployServicesWindowsServer()
	 * @see GuiDeployWizard#createDeployServicesWindowsClient()
	 * @see GuiDeployWizard#createDeployServicesLinuxClient()
	 * @see GuiDeployWizard#createDeployServicesLinuxServer()
	 * @see GuiDeployWizard#createDeploySummary()
	 * @see GuiDeployWizard#createSouthPanel()
	 */
	private void makePanels()
	{
		JPanel jpNorth = createNorthPanel();
		JPanel jpSouth = createSouthPanel();
		JPanel jpDeployWelcome = createDeployWelcome();
		JPanel jpDeploySelectOS = createDeploySelectOS();
		JPanel jpDeployGuestInfo = createDeployGuestInfo();
		JPanel jpDeployServicesWindowsServer = createDeployServicesWindowsServer();
		JPanel jpDeployServicesWindowsClient = createDeployServicesWindowsClient();
		JPanel jpDeployServicesLinuxClient = createDeployServicesLinuxClient();
		JPanel jpDeployServicesLinuxServer = createDeployServicesLinuxServer();
		JPanel jpDeploySummary = createDeploySummary();
		centerLayout = new CardLayout();
		
		jpCenter = new JPanel(centerLayout);
		jpCenter.add(jpDeployWelcome, WELCOME);
		jpCenter.add(jpDeploySelectOS, SELECTOS);
		jpCenter.add(jpDeployGuestInfo, GUESTINFO);
		jpCenter.add(jpDeployServicesWindowsServer, SERVICES_WINDOWS_SERVER);
		jpCenter.add(jpDeployServicesWindowsClient, SERVICES_WINDOWS_CLIENT);
		jpCenter.add(jpDeployServicesLinuxServer, SERVICES_LINUX_SERVER);
		jpCenter.add(jpDeployServicesLinuxClient, SERVICES_LINUX_CLIENT);
		jpCenter.add(jpDeploySummary, DEPLOYMENT_SUMMARY);
		
		//if deploymentMode is SINGLE_VM_MODE, only need 1 VM
		switch (deploymentMode)
		{
			case 1:
			{
				centerLayout.show(jpCenter, SELECTOS);
				currentState = SELECTOS;
				break;
			}
			
			case 2:
			{
				centerLayout.show(jpCenter, WELCOME);
				break;
			}
			
			case 3:
			{
				centerLayout.show(jpCenter, GUESTINFO);
				currentState = GUESTINFO;
				break;
			}
			
			case 4:
			{
				jlTeam.setVisible(true);
				jcbTeam.setVisible(true);
			}
		}
		
		Container main = getContentPane();
		main.setLayout(new BorderLayout());
		main.add(jpNorth, BorderLayout.NORTH);
		main.add(jpSouth, BorderLayout.SOUTH);
		main.add(jpCenter, BorderLayout.CENTER);
	}
	
	/**
	 * Sets the Mnemonic values on the Wizard's components
	 * @see GuiDeployWizard#GuiDeployWizard()
	 */
	private void setMnemonics()
	{
		jbNext.setMnemonic('N');
		jbCancel.setMnemonic('C');
		jbFinish.setMnemonic('F');
		jbSave.setMnemonic('S');
	}
	
	/**
	 * Adds the ActionListeners and ItemListeners to the attributes that require them.
	 * Any listeners needed are within the class
	 * @see GuiDeployWizard#GuiDeployWizard()
	 */
	private void addListeners()
	{
		addWindowListener(new GuiWindowListener());
		
		jbCancel.addActionListener(this);
		jbFinish.addActionListener(this);
		jbNext.addActionListener(this);
		jbSave.addActionListener(this);
		jcbCount.addActionListener(this);
		jcbCategory.addItemListener(this);
		jcbOS.addItemListener(this);
		jrbDHCP.addItemListener(this);
		jrbStatic.addItemListener(this);
		jCheckActiveDirectory.addItemListener(this);
		jLinuxNIS.addItemListener(this);
		jCheckBind.addItemListener(this);
		jLinuxBind.addItemListener(this);
	}
	
	/**
	 * Create the JPanel for the top of the wizard, containing the logo, title, and step count
	 * <br />
	 * This JPanel is added to the CardLayout during the makePanels() method
	 * @return panel completed and formatted JPanel containing the top of the wizard
	 * @see GuiDeployWizard#makePanels()
	 */
	private JPanel createNorthPanel()
	{
		JPanel panel = new JPanel(new BorderLayout());
		JPanel east = new JPanel(new GridLayout(2,1));
		JPanel west = new JPanel(new FlowLayout(FlowLayout.CENTER));
		JPanel eastNorth = new JPanel(new FlowLayout(FlowLayout.CENTER));
		JPanel eastSouth = new JPanel(new FlowLayout(FlowLayout.CENTER));
		
		west.setOpaque(false);
		west.setPreferredSize(DIM_DEPLOY_NORTH_WEST);
		west.setMaximumSize(DIM_DEPLOY_NORTH_WEST);
		west.setMinimumSize(DIM_DEPLOY_NORTH_WEST);
		JLabel icon = new JLabel(new ImageIcon(getClass().getResource("/images/vase-icon.png")));
		west.add(icon);
		
		eastNorth.add(jlTitle);
		eastSouth.add(jlSteps);
		eastNorth.setOpaque(false);
		eastSouth.setOpaque(false);
		east.setOpaque(false);
		east.setPreferredSize(DIM_DEPLOY_NORTH_EAST);
		east.setMaximumSize(DIM_DEPLOY_NORTH_EAST);
		east.setMinimumSize(DIM_DEPLOY_NORTH_EAST);
		east.add(eastNorth);
		east.add(eastSouth);
		
		panel.setOpaque(true);
		panel.setBackground(COLOR_WIZARD_TITLE);
		panel.setPreferredSize(DIM_DEPLOY_NORTH);
		panel.setMaximumSize(DIM_DEPLOY_NORTH);
		panel.setMinimumSize(DIM_DEPLOY_NORTH);
		panel.add(west, BorderLayout.WEST);
		panel.add(east, BorderLayout.EAST);
		panel.add(jsBarTop, BorderLayout.SOUTH);
		
		return panel;
	}
	
	/**
	 * Create the JPanel containing the Cancel, Finish, and Next buttons
	 * <br />
	 * This JPanel is added to the CardLayout during the makePanels() method
	 * @return panel completed and formatted JPanel containing the Cancel, Finish, and Next buttons
	 * @see GuiDeployWizard#makePanels()
	 */
	private JPanel createSouthPanel()
	{
		JPanel panel = new JPanel();
		JPanel south = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
		panel.setLayout(new BorderLayout());
		panel.setOpaque(false);
		panel.setPreferredSize(DIM_DEPLOY_SOUTH);
		panel.setMaximumSize(DIM_DEPLOY_SOUTH);
		panel.setMinimumSize(DIM_DEPLOY_SOUTH);
		south.setOpaque(false);
		south.add(jbNext, BorderLayout.SOUTH);
		south.add(jbFinish, BorderLayout.SOUTH);
		south.add(jbCancel, BorderLayout.SOUTH);
		south.add(jbSave, BorderLayout.SOUTH);
		panel.add(south, BorderLayout.SOUTH);
		panel.add(jsBarBottom, BorderLayout.NORTH);
		
		return panel;
	}
	
	/**
	 * Create the JPanel for the welcome screen (and no. of VMs selection)
	 * <br />
	 * This JPanel is added to the CardLayout during the makePanels() method
	 * @return panel completed and formatted JPanel containing the welcome screen (and no. of VMs selection)
	 * @see GuiDeployWizard#makePanels()
	 */
	private JPanel createDeployWelcome()
	{
		JPanel panel = new JPanel();
		JPanel north = new JPanel(new FlowLayout(FlowLayout.CENTER));
		JPanel south = new JPanel(new SpringLayout());
		JLabel jlCount = createDeployComponentLabel("Number of VMs to Deploy: ");
		
		
		north.setPreferredSize(DIM_DEPLOY_CENTER_NORTH);
		north.setMaximumSize(DIM_DEPLOY_CENTER_NORTH);
		north.setMinimumSize(DIM_DEPLOY_CENTER_NORTH);
		north.setOpaque(false);		
		north.add(createDeploySubLabel(DEPLOY_WELCOME));
		
		SpringLayout layout = (SpringLayout) south.getLayout();
		south.setPreferredSize(DIM_DEPLOY_CENTER_SOUTH);
		south.setMaximumSize(DIM_DEPLOY_CENTER_SOUTH);
		south.setMinimumSize(DIM_DEPLOY_CENTER_SOUTH);
		south.add(jlCount);
		south.add(jcbCount);
		south.add(jlTeam);
		south.add(jcbTeam);
		
		layout.putConstraint(SpringLayout.VERTICAL_CENTER, jlCount, 15, SpringLayout.NORTH, south);
		layout.putConstraint(SpringLayout.EAST, jlCount, -5, SpringLayout.HORIZONTAL_CENTER, south);
		layout.putConstraint(SpringLayout.VERTICAL_CENTER, jlTeam, 30, SpringLayout.VERTICAL_CENTER, jlCount);
		layout.putConstraint(SpringLayout.EAST, jlTeam, -5, SpringLayout.HORIZONTAL_CENTER, south);
		layout.putConstraint(SpringLayout.VERTICAL_CENTER, jcbCount, 15, SpringLayout.NORTH, south);
		layout.putConstraint(SpringLayout.WEST, jcbCount, 5, SpringLayout.HORIZONTAL_CENTER, south);
		layout.putConstraint(SpringLayout.VERTICAL_CENTER, jcbTeam, 30, SpringLayout.VERTICAL_CENTER, jcbCount);
		layout.putConstraint(SpringLayout.WEST, jcbTeam, 5, SpringLayout.HORIZONTAL_CENTER, south);		
		
		panel.setLayout(new BorderLayout());
		panel.setOpaque(false);
		panel.setPreferredSize(DIM_DEPLOY_CENTER);
		panel.setMaximumSize(DIM_DEPLOY_CENTER);
		panel.setMinimumSize(DIM_DEPLOY_CENTER);
		panel.add(north, BorderLayout.NORTH);
		panel.add(south, BorderLayout.SOUTH);
		
		return panel;
	}
	
	/**
	 * Create the JPanel for the boxes for OS selection
	 * <br />
	 * This JPanel is added to the CardLayout during the makePanels() method
	 * @return panel completed and formatted JPanel containing the boxes for OS selection
	 * @see GuiDeployWizard#makePanels()
	 */
	private JPanel createDeploySelectOS()
	{
		SpringLayout layoutWest = new SpringLayout();
		SpringLayout layoutEast = new SpringLayout();
		JPanel panel = new JPanel();
		JPanel north = new JPanel(new FlowLayout(FlowLayout.CENTER));
		JPanel south = new JPanel(new BorderLayout());
		JPanel east = new JPanel(layoutEast);
		JPanel west = new JPanel(layoutWest);
		JPanel southSouth = new JPanel(new FlowLayout(FlowLayout.CENTER));
		JLabel jlType = createDeployComponentLabel("Guest Virtual Machine OS Type: ");
		
		north.setPreferredSize(DIM_DEPLOY_CENTER_NORTH);
		north.setMaximumSize(DIM_DEPLOY_CENTER_NORTH);
		north.setMinimumSize(DIM_DEPLOY_CENTER_NORTH);
		north.setOpaque(false);		
		north.add(createDeploySubLabel(DEPLOY_SELECTOS));
		
		south.setOpaque(false);
		south.setPreferredSize(DIM_DEPLOY_CENTER_SOUTH);
		south.setMaximumSize(DIM_DEPLOY_CENTER_SOUTH);
		south.setMinimumSize(DIM_DEPLOY_CENTER_SOUTH);
		
		west.setOpaque(false);
		west.setPreferredSize(DIM_DEPLOY_CENTER_SOUTH_WEST);
		west.setMaximumSize(DIM_DEPLOY_CENTER_SOUTH_WEST);
		west.setMinimumSize(DIM_DEPLOY_CENTER_SOUTH_WEST);
		west.add(jlType);
		west.add(jlOS);
		layoutWest.putConstraint(SpringLayout.VERTICAL_CENTER, jlType, 15, SpringLayout.NORTH, west);
		layoutWest.putConstraint(SpringLayout.EAST, jlType, -20, SpringLayout.EAST, west);
		layoutWest.putConstraint(SpringLayout.VERTICAL_CENTER, jlOS, 30, SpringLayout.VERTICAL_CENTER, jlType);
		layoutWest.putConstraint(SpringLayout.EAST, jlOS, -20, SpringLayout.EAST, west);
		
		east.setOpaque(false);
		east.setPreferredSize(DIM_DEPLOY_CENTER_SOUTH_EAST);
		east.setMaximumSize(DIM_DEPLOY_CENTER_SOUTH_EAST);
		east.setMinimumSize(DIM_DEPLOY_CENTER_SOUTH_EAST);
		east.add(jcbCategory);
		east.add(jcbOS);
		layoutEast.putConstraint(SpringLayout.VERTICAL_CENTER, jcbCategory, 15, SpringLayout.NORTH, east);
		layoutEast.putConstraint(SpringLayout.WEST, jcbCategory, 10, SpringLayout.WEST, east);
		layoutEast.putConstraint(SpringLayout.VERTICAL_CENTER, jcbOS, 30, SpringLayout.VERTICAL_CENTER, jcbCategory);
		layoutEast.putConstraint(SpringLayout.WEST, jcbOS, 10, SpringLayout.WEST, east);
		
		southSouth.setOpaque(false);
		southSouth.setPreferredSize(DIM_DEPLOY_CENTER_SOUTH_SOUTH);
		southSouth.setMaximumSize(DIM_DEPLOY_CENTER_SOUTH_SOUTH);
		southSouth.setMinimumSize(DIM_DEPLOY_CENTER_SOUTH_SOUTH);
		southSouth.add(jlOSValid);
		
		south.add(west, BorderLayout.WEST);
		south.add(east, BorderLayout.EAST);
		south.add(southSouth, BorderLayout.SOUTH);
		
		panel.setLayout(new BorderLayout());
		panel.setOpaque(false);
		panel.setPreferredSize(DIM_DEPLOY_CENTER);
		panel.setMaximumSize(DIM_DEPLOY_CENTER);
		panel.setMinimumSize(DIM_DEPLOY_CENTER);
		panel.add(north, BorderLayout.NORTH);
		panel.add(south, BorderLayout.SOUTH);
		
		return panel;
	}
	
	/**
	 * Create the JPanel for the Guest Information screen during the Deploy Wizard
	 * <br />
	 * This JPanel is added to the CardLayout during the makePanels() method
	 * @return completed and formatted JPanel containing fields for guest information
	 * @see GuiDeployWizard#makePanels()
	 */
	private JPanel createDeployGuestInfo()
	{
		SpringLayout layoutWest = new SpringLayout();
		SpringLayout layoutEast = new SpringLayout();
		JPanel panel = new JPanel();
		JPanel north = new JPanel(new FlowLayout(FlowLayout.CENTER));
		JPanel south = new JPanel(new BorderLayout());
		JPanel east = new JPanel(layoutEast);
		JPanel west = new JPanel(layoutWest);
		JPanel southSouth = new JPanel(new FlowLayout(FlowLayout.CENTER));
		JLabel jlVMName = createDeployComponentLabel("Virtual Machine Name: ");
		JLabel jlHostname = createDeployComponentLabel("Guest Hostname: ");
		JLabel jlDomainName = createDeployComponentLabel("Domain Name: ");
		JLabel jlIP = createDeployComponentLabel("IP Address: ");
		JLabel jlNetmask = createDeployComponentLabel("Subnet Mask: ");
		JLabel jlDefaultGateway = createDeployComponentLabel("Default Gateway: ");
		JLabel jlDnsServer = createDeployComponentLabel("DNS Server: ");
		JLabel jlNetwork = createDeployComponentLabel("Network Settings: ");
		JLabel jlVirtualNetworks = createDeployComponentLabel("Virtual Network: ");
		ButtonGroup group = new ButtonGroup();
		jrbDHCP = createDeployRadioButton(group, "DHCP");
		jrbStatic = createDeployRadioButton(group, "Static");		
		jrbStatic.setSelected(true);		
		
		north.setPreferredSize(DIM_DEPLOY_CENTER_NORTH);
		north.setMaximumSize(DIM_DEPLOY_CENTER_NORTH);
		north.setMinimumSize(DIM_DEPLOY_CENTER_NORTH);
		north.setOpaque(false);		
		north.add(createDeploySubLabel(DEPLOY_GUESTINFO));
		
		south.setOpaque(false);
		south.setPreferredSize(DIM_DEPLOY_CENTER_SOUTH);
		south.setMaximumSize(DIM_DEPLOY_CENTER_SOUTH);
		south.setMinimumSize(DIM_DEPLOY_CENTER_SOUTH);
		
		west.setOpaque(false);
		west.setPreferredSize(DIM_DEPLOY_CENTER_SOUTH_WEST);
		west.setMaximumSize(DIM_DEPLOY_CENTER_SOUTH_WEST);
		west.setMinimumSize(DIM_DEPLOY_CENTER_SOUTH_WEST);
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
		
		east.setOpaque(false);
		east.setPreferredSize(DIM_DEPLOY_CENTER_SOUTH_EAST);
		east.setMaximumSize(DIM_DEPLOY_CENTER_SOUTH_EAST);
		east.setMinimumSize(DIM_DEPLOY_CENTER_SOUTH_EAST);
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
		
		southSouth.setOpaque(false);
		southSouth.setPreferredSize(DIM_DEPLOY_CENTER_SOUTH_SOUTH);
		southSouth.setMaximumSize(DIM_DEPLOY_CENTER_SOUTH_SOUTH);
		southSouth.setMinimumSize(DIM_DEPLOY_CENTER_SOUTH_SOUTH);
		southSouth.add(createDeploySubLabel("Note: Virtual Machine may be not be remotely accessible if DHCP is enabled"));
		
		south.add(west, BorderLayout.WEST);
		south.add(east, BorderLayout.EAST);
		south.add(southSouth, BorderLayout.SOUTH);
		
		panel.setLayout(new BorderLayout());
		panel.setOpaque(false);
		panel.setPreferredSize(DIM_DEPLOY_CENTER);
		panel.setMaximumSize(DIM_DEPLOY_CENTER);
		panel.setMinimumSize(DIM_DEPLOY_CENTER);
		panel.add(north, BorderLayout.NORTH);
		panel.add(south, BorderLayout.SOUTH);
		
		return panel;
	}
	
	/**
	 * Create the JPanel for the Windows server service selection
	 * <br />
	 * This JPanel is added to the CardLayout during the makePanels() method
	 * @return panel completed and formatted JPanel containing fields for Windows server selection
	 * @see GuiDeployWizard#makePanels()
	 */
	private JPanel createDeployServicesWindowsServer()
	{
		SpringLayout layoutWest = new SpringLayout();
		SpringLayout layoutEast = new SpringLayout();
		JPanel panel = new JPanel();
		JPanel north = new JPanel(new FlowLayout(FlowLayout.CENTER));
		JPanel south = new JPanel(new BorderLayout());
		JPanel east = new JPanel(layoutEast);
		JPanel west = new JPanel(layoutWest);
		JPanel southSouth = new JPanel(new FlowLayout(FlowLayout.CENTER));
		JLabel jlDNS = createDeployComponentLabel("DNS Server: ");
		JLabel jlDHCP = createDeployComponentLabel("DHCP Server: ");
		JLabel jlActiveDirectory = createDeployComponentLabel("Active Directory Domain Controller: ");
		JLabel jlFileServer = createDeployComponentLabel("File Server: ");
		JLabel jlIIS = createDeployComponentLabel("IIS Server: ");
		JLabel jlAccounts = createDeployComponentLabel("Load User Accounts: ");
		JLabel jlExploits = createDeployComponentLabel("Load Exploits: ");
		
		north.setPreferredSize(DIM_DEPLOY_CENTER_NORTH);
		north.setMaximumSize(DIM_DEPLOY_CENTER_NORTH);
		north.setMinimumSize(DIM_DEPLOY_CENTER_NORTH);
		north.setOpaque(false);		
		north.add(createDeploySubLabel(DEPLOY_WINDOWS_SERVER));
		
		south.setOpaque(false);
		south.setPreferredSize(DIM_DEPLOY_CENTER_SOUTH);
		south.setMaximumSize(DIM_DEPLOY_CENTER_SOUTH);
		south.setMinimumSize(DIM_DEPLOY_CENTER_SOUTH);
		
		west.setOpaque(false);
		west.setPreferredSize(DIM_DEPLOY_CENTER_SOUTH_WEST);
		west.setMaximumSize(DIM_DEPLOY_CENTER_SOUTH_WEST);
		west.setMinimumSize(DIM_DEPLOY_CENTER_SOUTH_WEST);
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
		east.add(jCheckDNS);
		east.add(jCheckDHCP);
		east.add(jCheckActiveDirectory);
		east.add(jtfDirectoryName);
		east.add(jCheckFileServer);
		east.add(jCheckAccounts);
		east.add(jCheckExploits);
		east.add(jCheckIIS);
		layoutEast.putConstraint(SpringLayout.VERTICAL_CENTER, jCheckDNS, 15, SpringLayout.NORTH, east);
		layoutEast.putConstraint(SpringLayout.WEST, jCheckDNS, 10, SpringLayout.WEST, east);
		layoutEast.putConstraint(SpringLayout.VERTICAL_CENTER, jCheckDHCP, 35, SpringLayout.VERTICAL_CENTER, jCheckDNS);
		layoutEast.putConstraint(SpringLayout.WEST, jCheckDHCP, 10, SpringLayout.WEST, east);
		layoutEast.putConstraint(SpringLayout.VERTICAL_CENTER, jCheckActiveDirectory, 35, SpringLayout.VERTICAL_CENTER, jCheckDHCP);
		layoutEast.putConstraint(SpringLayout.WEST, jCheckActiveDirectory, 10, SpringLayout.WEST, east);
		layoutEast.putConstraint(SpringLayout.VERTICAL_CENTER, jtfDirectoryName, 35, SpringLayout.VERTICAL_CENTER, jCheckActiveDirectory);
		layoutEast.putConstraint(SpringLayout.WEST, jtfDirectoryName, 10, SpringLayout.WEST, east);
		layoutEast.putConstraint(SpringLayout.VERTICAL_CENTER, jCheckFileServer, 35, SpringLayout.VERTICAL_CENTER, jtfDirectoryName);
		layoutEast.putConstraint(SpringLayout.WEST, jCheckFileServer, 10, SpringLayout.WEST, east);
		layoutEast.putConstraint(SpringLayout.VERTICAL_CENTER, jCheckIIS, 35, SpringLayout.VERTICAL_CENTER, jCheckFileServer);
		layoutEast.putConstraint(SpringLayout.WEST, jCheckIIS, 10, SpringLayout.WEST, east);
		layoutEast.putConstraint(SpringLayout.VERTICAL_CENTER, jCheckAccounts, 35, SpringLayout.VERTICAL_CENTER, jCheckIIS);
		layoutEast.putConstraint(SpringLayout.WEST, jCheckAccounts, 10, SpringLayout.WEST, east);
		layoutEast.putConstraint(SpringLayout.VERTICAL_CENTER, jCheckExploits, 35, SpringLayout.VERTICAL_CENTER, jCheckAccounts);
		layoutEast.putConstraint(SpringLayout.WEST, jCheckExploits, 10, SpringLayout.WEST, east);
		
		southSouth.setOpaque(false);
		southSouth.setPreferredSize(DIM_DEPLOY_CENTER_SOUTH_SOUTH);
		southSouth.setMaximumSize(DIM_DEPLOY_CENTER_SOUTH_SOUTH);
		southSouth.setMinimumSize(DIM_DEPLOY_CENTER_SOUTH_SOUTH);
		
		south.add(west, BorderLayout.WEST);
		south.add(east, BorderLayout.EAST);
		south.add(southSouth, BorderLayout.SOUTH);
		
		panel.setLayout(new BorderLayout());
		panel.setOpaque(false);
		panel.setPreferredSize(DIM_DEPLOY_CENTER);
		panel.setMaximumSize(DIM_DEPLOY_CENTER);
		panel.setMinimumSize(DIM_DEPLOY_CENTER);
		panel.add(north, BorderLayout.NORTH);
		panel.add(south, BorderLayout.SOUTH);
		
		return panel;
	}
	
	/**
	 * Creates the JPanel for the Windows Client service selection
	 * <br />
	 * This JPanel is added to the CardLayout during the makePanels() method
	 * @return panel the completed and formatted JPanel containing the fields for Windows client selection
	 * @see GuiDeployWizard#makePanels()
	 */
	private JPanel createDeployServicesWindowsClient()
	{
		SpringLayout layoutWest = new SpringLayout();
		SpringLayout layoutEast = new SpringLayout();
		JPanel panel = new JPanel();
		JPanel north = new JPanel(new FlowLayout(FlowLayout.CENTER));
		JPanel south = new JPanel(new BorderLayout());
		JPanel east = new JPanel(layoutEast);
		JPanel west = new JPanel(layoutWest);
		JPanel southSouth = new JPanel(new FlowLayout(FlowLayout.CENTER));
		JLabel jlAccounts = createDeployComponentLabel("Load User Accounts: ");
		JLabel jlExploits = createDeployComponentLabel("Load Exploits: ");
		JLabel jlBind = createDeployComponentLabel("Bind to Active Directory: ");
		
		north.setPreferredSize(DIM_DEPLOY_CENTER_NORTH);
		north.setMaximumSize(DIM_DEPLOY_CENTER_NORTH);
		north.setMinimumSize(DIM_DEPLOY_CENTER_NORTH);
		north.setOpaque(false);		
		north.add(createDeploySubLabel(DEPLOY_WINDOWS_CLIENT));
		
		south.setOpaque(false);
		south.setPreferredSize(DIM_DEPLOY_CENTER_SOUTH);
		south.setMaximumSize(DIM_DEPLOY_CENTER_SOUTH);
		south.setMinimumSize(DIM_DEPLOY_CENTER_SOUTH);
		
		west.setOpaque(false);
		west.setPreferredSize(DIM_DEPLOY_CENTER_SOUTH_WEST);
		west.setMaximumSize(DIM_DEPLOY_CENTER_SOUTH_WEST);
		west.setMinimumSize(DIM_DEPLOY_CENTER_SOUTH_WEST);		
		west.add(jlBind);
		west.add(jlDirectoryBind);
		west.add(jlAccounts);
		west.add(jlExploits);
		layoutWest.putConstraint(SpringLayout.VERTICAL_CENTER, jlBind, 15, SpringLayout.NORTH, west);
		layoutWest.putConstraint(SpringLayout.EAST, jlBind, -5, SpringLayout.EAST, west);
		layoutWest.putConstraint(SpringLayout.VERTICAL_CENTER, jlDirectoryBind, 35, SpringLayout.VERTICAL_CENTER, jlBind);
		layoutWest.putConstraint(SpringLayout.EAST, jlDirectoryBind, -5, SpringLayout.EAST, west);
		layoutWest.putConstraint(SpringLayout.VERTICAL_CENTER, jlAccounts, 75, SpringLayout.VERTICAL_CENTER, jlDirectoryBind);
		layoutWest.putConstraint(SpringLayout.EAST, jlAccounts, -5, SpringLayout.EAST, west);
		layoutWest.putConstraint(SpringLayout.VERTICAL_CENTER, jlExploits, 35, SpringLayout.VERTICAL_CENTER, jlAccounts);
		layoutWest.putConstraint(SpringLayout.EAST, jlExploits, -5, SpringLayout.EAST, west);
		
		east.setOpaque(false);
		east.setPreferredSize(DIM_DEPLOY_CENTER_SOUTH_EAST);
		east.setMaximumSize(DIM_DEPLOY_CENTER_SOUTH_EAST);
		east.setMinimumSize(DIM_DEPLOY_CENTER_SOUTH_EAST);
		east.add(jCheckBind);
		east.add(jtfDirectoryBind);
		east.add(jCheckAccountsClient);
		east.add(jCheckClientExploits);
		layoutEast.putConstraint(SpringLayout.VERTICAL_CENTER, jCheckBind, 15, SpringLayout.NORTH, east);
		layoutEast.putConstraint(SpringLayout.WEST, jCheckBind, 5, SpringLayout.WEST, east);
		layoutEast.putConstraint(SpringLayout.VERTICAL_CENTER, jtfDirectoryBind, 35, SpringLayout.VERTICAL_CENTER, jCheckBind);
		layoutEast.putConstraint(SpringLayout.WEST, jtfDirectoryBind, 5, SpringLayout.WEST, east);
		layoutEast.putConstraint(SpringLayout.VERTICAL_CENTER, jCheckAccountsClient, 75, SpringLayout.VERTICAL_CENTER, jtfDirectoryBind);
		layoutEast.putConstraint(SpringLayout.WEST, jCheckAccountsClient, 5, SpringLayout.WEST, east);
		layoutEast.putConstraint(SpringLayout.VERTICAL_CENTER, jCheckClientExploits, 35, SpringLayout.VERTICAL_CENTER, jCheckAccountsClient);
		layoutEast.putConstraint(SpringLayout.WEST, jCheckClientExploits, 5, SpringLayout.WEST, east);
		
		southSouth.setOpaque(false);
		southSouth.setPreferredSize(DIM_DEPLOY_CENTER_SOUTH_SOUTH);
		southSouth.setMaximumSize(DIM_DEPLOY_CENTER_SOUTH_SOUTH);
		southSouth.setMinimumSize(DIM_DEPLOY_CENTER_SOUTH_SOUTH);
		southSouth.add(createDeploySubLabel("Note: Users should not be added if client is bound to Active Directory"));
		
		south.add(west, BorderLayout.WEST);
		south.add(east, BorderLayout.EAST);
		south.add(southSouth, BorderLayout.SOUTH);
		
		panel.setLayout(new BorderLayout());
		panel.setOpaque(false);
		panel.setPreferredSize(DIM_DEPLOY_CENTER);
		panel.setMaximumSize(DIM_DEPLOY_CENTER);
		panel.setMinimumSize(DIM_DEPLOY_CENTER);
		panel.add(north, BorderLayout.NORTH);
		panel.add(south, BorderLayout.SOUTH);
		
		return panel;
	}
	
	/**
	 * Create the JPanel for the Linux/BSD service selection
	 * <br />
	 * This JPanel is added to the CardLayout during the makePanels() method
	 * @return panel completed and formatted JPanel containing fields for Linux/BSD service selection
	 * @see GuiDeployWizard#makePanels()
	 */
	private JPanel createDeployServicesLinuxServer()
	{
		SpringLayout layoutWest = new SpringLayout();
		SpringLayout layoutEast = new SpringLayout();
		JPanel panel = new JPanel();
		JPanel north = new JPanel(new FlowLayout(FlowLayout.CENTER));
		JPanel south = new JPanel(new BorderLayout());
		JPanel east = new JPanel(layoutEast);
		JPanel west = new JPanel(layoutWest);
		JPanel southSouth = new JPanel(new FlowLayout(FlowLayout.CENTER));
		JLabel jlDNS = createDeployComponentLabel("DNS Server: ");
		JLabel jlDHCP = createDeployComponentLabel("DHCP Server: ");
		JLabel jlNIS = createDeployComponentLabel("NIS Server: ");
		JLabel jlNFS = createDeployComponentLabel("NFS Server: ");
		JLabel jlMail = createDeployComponentLabel("Mail Server: ");
		JLabel jlHTTP = createDeployComponentLabel("HTTP Server: ");
		JLabel jlFTP = createDeployComponentLabel("FTP Server: ");
		JLabel jlAccounts = createDeployComponentLabel("Load User Accounts: ");
		JLabel jlExploits = createDeployComponentLabel("Load Exploits: ");
		JLabel jlSamba = createDeployComponentLabel("Samba Server: ");
		
		north.setPreferredSize(DIM_DEPLOY_CENTER_NORTH);
		north.setMaximumSize(DIM_DEPLOY_CENTER_NORTH);
		north.setMinimumSize(DIM_DEPLOY_CENTER_NORTH);
		north.setOpaque(false);		
		north.add(createDeploySubLabel(DEPLOY_LINUX_SERVER));
		
		south.setOpaque(false);
		south.setPreferredSize(DIM_DEPLOY_CENTER_SOUTH);
		south.setMaximumSize(DIM_DEPLOY_CENTER_SOUTH);
		south.setMinimumSize(DIM_DEPLOY_CENTER_SOUTH);
		
		west.setOpaque(false);
		west.setPreferredSize(DIM_DEPLOY_CENTER_SOUTH_WEST);
		west.setMaximumSize(DIM_DEPLOY_CENTER_SOUTH_WEST);
		west.setMinimumSize(DIM_DEPLOY_CENTER_SOUTH_WEST);
		west.add(jlDNS);
		west.add(jLinuxDNS);
		west.add(jlHTTP);
		west.add(jLinuxHTTP);
		west.add(jlMail);
		west.add(jLinuxMail);
		west.add(jlSamba);
		west.add(jLinuxSamba);
		west.add(jlNISName);
		west.add(jlAccounts);
		west.add(jlExploits);
		layoutWest.putConstraint(SpringLayout.VERTICAL_CENTER, jLinuxDNS, 15, SpringLayout.NORTH, west);
		layoutWest.putConstraint(SpringLayout.EAST, jLinuxDNS, -50, SpringLayout.EAST, west);
		layoutWest.putConstraint(SpringLayout.VERTICAL_CENTER, jlDNS, 15, SpringLayout.NORTH, west);
		layoutWest.putConstraint(SpringLayout.EAST, jlDNS, -15, SpringLayout.WEST, jLinuxDNS);
		layoutWest.putConstraint(SpringLayout.VERTICAL_CENTER, jLinuxHTTP, 35, SpringLayout.VERTICAL_CENTER, jLinuxDNS);
		layoutWest.putConstraint(SpringLayout.EAST, jLinuxHTTP, -50, SpringLayout.EAST, west);
		layoutWest.putConstraint(SpringLayout.VERTICAL_CENTER, jlHTTP, 35, SpringLayout.VERTICAL_CENTER, jlDNS);
		layoutWest.putConstraint(SpringLayout.EAST, jlHTTP, -15, SpringLayout.WEST, jLinuxHTTP);
		layoutWest.putConstraint(SpringLayout.VERTICAL_CENTER, jLinuxMail, 35, SpringLayout.VERTICAL_CENTER, jLinuxHTTP);
		layoutWest.putConstraint(SpringLayout.EAST, jLinuxMail, -50, SpringLayout.EAST, west);
		layoutWest.putConstraint(SpringLayout.VERTICAL_CENTER, jlMail, 35, SpringLayout.VERTICAL_CENTER, jlHTTP);
		layoutWest.putConstraint(SpringLayout.EAST, jlMail, -15, SpringLayout.WEST, jLinuxMail);
		layoutWest.putConstraint(SpringLayout.VERTICAL_CENTER, jLinuxSamba, 35, SpringLayout.VERTICAL_CENTER, jLinuxMail);
		layoutWest.putConstraint(SpringLayout.EAST, jLinuxSamba, -50, SpringLayout.EAST, west);
		layoutWest.putConstraint(SpringLayout.VERTICAL_CENTER, jlSamba, 35, SpringLayout.VERTICAL_CENTER, jlMail);
		layoutWest.putConstraint(SpringLayout.EAST, jlSamba, -15, SpringLayout.WEST, jLinuxSamba);
		layoutWest.putConstraint(SpringLayout.VERTICAL_CENTER, jlNISName, 35, SpringLayout.VERTICAL_CENTER, jLinuxSamba);
		layoutWest.putConstraint(SpringLayout.EAST, jlNISName, -5, SpringLayout.EAST, west);
		layoutWest.putConstraint(SpringLayout.VERTICAL_CENTER, jlAccounts, 75, SpringLayout.VERTICAL_CENTER, jlNISName);
		layoutWest.putConstraint(SpringLayout.EAST, jlAccounts, -7, SpringLayout.EAST, west);
		layoutWest.putConstraint(SpringLayout.VERTICAL_CENTER, jlExploits, 35, SpringLayout.VERTICAL_CENTER, jlAccounts);
		layoutWest.putConstraint(SpringLayout.EAST, jlExploits, -7, SpringLayout.EAST, west);
		
		east.setOpaque(false);
		east.setPreferredSize(DIM_DEPLOY_CENTER_SOUTH_EAST);
		east.setMaximumSize(DIM_DEPLOY_CENTER_SOUTH_EAST);
		east.setMinimumSize(DIM_DEPLOY_CENTER_SOUTH_EAST);
		east.add(jlDHCP);
		east.add(jLinuxDHCP);
		east.add(jlFTP);
		east.add(jLinuxFTP);
		east.add(jlNFS);
		east.add(jLinuxNFS);
		east.add(jlNIS);
		east.add(jLinuxNIS);
		east.add(jtfNISName);
		east.add(jLinuxAccounts);
		east.add(jLinuxExploits);		
		layoutEast.putConstraint(SpringLayout.VERTICAL_CENTER, jLinuxDHCP, 15, SpringLayout.NORTH, east);
		layoutEast.putConstraint(SpringLayout.EAST, jLinuxDHCP, -100, SpringLayout.EAST, east);
		layoutEast.putConstraint(SpringLayout.VERTICAL_CENTER, jlDHCP, 15, SpringLayout.NORTH, east);
		layoutEast.putConstraint(SpringLayout.EAST, jlDHCP, -15, SpringLayout.WEST, jLinuxDHCP);
		layoutEast.putConstraint(SpringLayout.VERTICAL_CENTER, jLinuxFTP, 35, SpringLayout.VERTICAL_CENTER, jLinuxDHCP);
		layoutEast.putConstraint(SpringLayout.EAST, jLinuxFTP, -100, SpringLayout.EAST, east);
		layoutEast.putConstraint(SpringLayout.VERTICAL_CENTER, jlFTP, 35, SpringLayout.VERTICAL_CENTER, jlDHCP);
		layoutEast.putConstraint(SpringLayout.EAST, jlFTP, -15, SpringLayout.WEST, jLinuxFTP);
		layoutEast.putConstraint(SpringLayout.VERTICAL_CENTER, jLinuxNFS, 35, SpringLayout.VERTICAL_CENTER, jLinuxFTP);
		layoutEast.putConstraint(SpringLayout.EAST, jLinuxNFS, -100, SpringLayout.EAST, east);
		layoutEast.putConstraint(SpringLayout.VERTICAL_CENTER, jlNFS, 35, SpringLayout.VERTICAL_CENTER, jlFTP);
		layoutEast.putConstraint(SpringLayout.EAST, jlNFS, -15, SpringLayout.WEST, jLinuxNFS);
		layoutEast.putConstraint(SpringLayout.VERTICAL_CENTER, jLinuxNIS, 35, SpringLayout.VERTICAL_CENTER, jLinuxNFS);
		layoutEast.putConstraint(SpringLayout.EAST, jLinuxNIS, -100, SpringLayout.EAST, east);
		layoutEast.putConstraint(SpringLayout.VERTICAL_CENTER, jlNIS, 35, SpringLayout.VERTICAL_CENTER, jlNFS);
		layoutEast.putConstraint(SpringLayout.EAST, jlNIS, -15, SpringLayout.WEST, jLinuxNIS);
		layoutEast.putConstraint(SpringLayout.VERTICAL_CENTER, jtfNISName, 35, SpringLayout.VERTICAL_CENTER, jLinuxNIS);
		layoutEast.putConstraint(SpringLayout.WEST, jtfNISName, 5, SpringLayout.WEST, east);
		layoutEast.putConstraint(SpringLayout.VERTICAL_CENTER, jLinuxAccounts, 75, SpringLayout.VERTICAL_CENTER, jtfNISName);
		layoutEast.putConstraint(SpringLayout.WEST, jLinuxAccounts, 7, SpringLayout.WEST, east);
		layoutEast.putConstraint(SpringLayout.VERTICAL_CENTER, jLinuxExploits, 35, SpringLayout.VERTICAL_CENTER, jLinuxAccounts);
		layoutEast.putConstraint(SpringLayout.WEST, jLinuxExploits, 7, SpringLayout.WEST, east);
		
		southSouth.setOpaque(false);
		southSouth.setPreferredSize(DIM_DEPLOY_CENTER_SOUTH_SOUTH);
		southSouth.setMaximumSize(DIM_DEPLOY_CENTER_SOUTH_SOUTH);
		southSouth.setMinimumSize(DIM_DEPLOY_CENTER_SOUTH_SOUTH);
		//southSouth.add(createDeploySubLabel());
		
		south.add(west, BorderLayout.WEST);
		south.add(east, BorderLayout.EAST);
		south.add(southSouth, BorderLayout.SOUTH);
		
		panel.setLayout(new BorderLayout());
		panel.setOpaque(false);
		panel.setPreferredSize(DIM_DEPLOY_CENTER);
		panel.setMaximumSize(DIM_DEPLOY_CENTER);
		panel.setMinimumSize(DIM_DEPLOY_CENTER);
		panel.add(north, BorderLayout.NORTH);
		panel.add(south, BorderLayout.SOUTH);
		
		return panel;
	}
	
	/**
	 * Create the JPanel for the Linux/BSD service client selection
	 * <br />
	 * This JPanel is added to the CardLayout during the makePanels() method
	 * @return panel completed and formatted JPanel containing fields for Linux/BSD client selection
	 * @see GuiDeployWizard#makePanels()
	 */
	private JPanel createDeployServicesLinuxClient()
	{
		SpringLayout layoutWest = new SpringLayout();
		SpringLayout layoutEast = new SpringLayout();
		JPanel panel = new JPanel();
		JPanel north = new JPanel(new FlowLayout(FlowLayout.CENTER));
		JPanel south = new JPanel(new BorderLayout());
		JPanel east = new JPanel(layoutEast);
		JPanel west = new JPanel(layoutWest);
		JPanel southSouth = new JPanel(new FlowLayout(FlowLayout.CENTER));
		JLabel jlAccounts = createDeployComponentLabel("Load User Accounts: ");
		JLabel jlExploits = createDeployComponentLabel("Load Exploits: ");
		JLabel jlBind = createDeployComponentLabel("Bind to NIS Server: ");
		
		north.setPreferredSize(DIM_DEPLOY_CENTER_NORTH);
		north.setMaximumSize(DIM_DEPLOY_CENTER_NORTH);
		north.setMinimumSize(DIM_DEPLOY_CENTER_NORTH);
		north.setOpaque(false);		
		north.add(createDeploySubLabel(DEPLOY_LINUX_CLIENT));
		
		south.setOpaque(false);
		south.setPreferredSize(DIM_DEPLOY_CENTER_SOUTH);
		south.setMaximumSize(DIM_DEPLOY_CENTER_SOUTH);
		south.setMinimumSize(DIM_DEPLOY_CENTER_SOUTH);
		
		west.setOpaque(false);
		west.setPreferredSize(DIM_DEPLOY_CENTER_SOUTH_WEST);
		west.setMaximumSize(DIM_DEPLOY_CENTER_SOUTH_WEST);
		west.setMinimumSize(DIM_DEPLOY_CENTER_SOUTH_WEST);		
		west.add(jlBind);
		west.add(jlNISBind);
		west.add(jlAccounts);
		west.add(jlExploits);
		layoutWest.putConstraint(SpringLayout.VERTICAL_CENTER, jlBind, 15, SpringLayout.NORTH, west);
		layoutWest.putConstraint(SpringLayout.EAST, jlBind, -5, SpringLayout.EAST, west);
		layoutWest.putConstraint(SpringLayout.VERTICAL_CENTER, jlNISBind, 35, SpringLayout.VERTICAL_CENTER, jlBind);
		layoutWest.putConstraint(SpringLayout.EAST, jlNISBind, -5, SpringLayout.EAST, west);
		layoutWest.putConstraint(SpringLayout.VERTICAL_CENTER, jlAccounts, 75, SpringLayout.VERTICAL_CENTER, jlNISBind);
		layoutWest.putConstraint(SpringLayout.EAST, jlAccounts, -5, SpringLayout.EAST, west);
		layoutWest.putConstraint(SpringLayout.VERTICAL_CENTER, jlExploits, 35, SpringLayout.VERTICAL_CENTER, jlAccounts);
		layoutWest.putConstraint(SpringLayout.EAST, jlExploits, -5, SpringLayout.EAST, west);
		
		east.setOpaque(false);
		east.setPreferredSize(DIM_DEPLOY_CENTER_SOUTH_EAST);
		east.setMaximumSize(DIM_DEPLOY_CENTER_SOUTH_EAST);
		east.setMinimumSize(DIM_DEPLOY_CENTER_SOUTH_EAST);
		east.add(jLinuxBind);
		east.add(jtfNISBind);
		east.add(jLinuxAccountsClient);
		east.add(jLinuxClientExploits);
		layoutEast.putConstraint(SpringLayout.VERTICAL_CENTER, jLinuxBind, 15, SpringLayout.NORTH, east);
		layoutEast.putConstraint(SpringLayout.WEST, jLinuxBind, 5, SpringLayout.WEST, east);
		layoutEast.putConstraint(SpringLayout.VERTICAL_CENTER, jtfNISBind, 35, SpringLayout.VERTICAL_CENTER, jLinuxBind);
		layoutEast.putConstraint(SpringLayout.WEST, jtfNISBind, 5, SpringLayout.WEST, east);
		layoutEast.putConstraint(SpringLayout.VERTICAL_CENTER, jLinuxAccountsClient, 75, SpringLayout.VERTICAL_CENTER, jtfNISBind);
		layoutEast.putConstraint(SpringLayout.WEST, jLinuxAccountsClient, 5, SpringLayout.WEST, east);
		layoutEast.putConstraint(SpringLayout.VERTICAL_CENTER, jLinuxClientExploits, 35, SpringLayout.VERTICAL_CENTER, jLinuxAccountsClient);
		layoutEast.putConstraint(SpringLayout.WEST, jLinuxClientExploits, 5, SpringLayout.WEST, east);
		
		southSouth.setOpaque(false);
		southSouth.setPreferredSize(DIM_DEPLOY_CENTER_SOUTH_SOUTH);
		southSouth.setMaximumSize(DIM_DEPLOY_CENTER_SOUTH_SOUTH);
		southSouth.setMinimumSize(DIM_DEPLOY_CENTER_SOUTH_SOUTH);
		southSouth.add(createDeploySubLabel("Note: Users should not be added if client is bound to NIS"));
		
		south.add(west, BorderLayout.WEST);
		south.add(east, BorderLayout.EAST);
		south.add(southSouth, BorderLayout.SOUTH);
		
		panel.setLayout(new BorderLayout());
		panel.setOpaque(false);
		panel.setPreferredSize(DIM_DEPLOY_CENTER);
		panel.setMaximumSize(DIM_DEPLOY_CENTER);
		panel.setMinimumSize(DIM_DEPLOY_CENTER);
		panel.add(north, BorderLayout.NORTH);
		panel.add(south, BorderLayout.SOUTH);
		
		return panel;
	}
	
	/**
	 * Creates the Panel that holds the JTextField containing the summary
	 * @return the completed summary JPanel
	 */
	private JPanel createDeploySummary()
	{
		SpringLayout layout = new SpringLayout();
		JPanel panel = new JPanel(layout);
		JScrollPane jsp = new JScrollPane(jtaDeploySummary);
		JLabel summary = createDeploySubLabel("Click finish to begin the Virtual Machine deployment process");
		summary.setHorizontalAlignment(JLabel.CENTER);
		
		panel.setOpaque(false);
		panel.setPreferredSize(DIM_DEPLOY_CENTER);
		panel.setMaximumSize(DIM_DEPLOY_CENTER);
		panel.setMinimumSize(DIM_DEPLOY_CENTER);
		panel.add(summary);
		panel.add(jsp);
		layout.putConstraint(SpringLayout.NORTH, summary, 5, SpringLayout.NORTH, panel);
		layout.putConstraint(SpringLayout.WEST, summary, 0, SpringLayout.WEST, panel);
		layout.putConstraint(SpringLayout.EAST, summary, 0, SpringLayout.EAST, panel);
		layout.putConstraint(SpringLayout.NORTH, jsp, 5, SpringLayout.SOUTH, summary);
		layout.putConstraint(SpringLayout.SOUTH, jsp, 0, SpringLayout.SOUTH, panel);
		layout.putConstraint(SpringLayout.EAST, jsp, 0, SpringLayout.EAST, panel);
		layout.putConstraint(SpringLayout.WEST, jsp, 0, SpringLayout.WEST, panel);
	
		return panel;
	}
	
	/**
	 * Handles the events from the "Cancel", "Finish", and "Next" buttons
	 * <br />
	 * Determines the current step and what OS the wizard is on using the osCount,
	 * deployOS, stepNumber, and stepTotal attributes.  It uses the currentState attribute
	 * to determine the CardLayout's next position.
	 * <br />
	 * Updates the DeployedVirtualMachine ArrayList with user input to build toward the final
	 * product of a completed array of DeployVirtualMachines to call scripts
	 * @param event the ActionEvent
	 */
	public void actionPerformed(ActionEvent event)
	{
		try
		{
			Object source = event.getSource();
			
			if (source instanceof JButton)
			{
				JButton button = (JButton) source;
				if (button.getActionCommand().equalsIgnoreCase("Cancel"))
				{
					dispose();
					LOG.write("Deployment Wizard cancelled");
				}
				
				else if (button.getActionCommand().equalsIgnoreCase("Next"))
				{
					stepNumber++;
					if (currentState.equals(WELCOME))
					{
						deployCount = Integer.parseInt((String) jcbCount.getSelectedItem());
						for (int i = 0; i < deployCount; i++)
						{
							virtualMachines.add(new DeployedVirtualMachine());
							virtualMachines.get(i).setTeam((String) jcbTeam.getSelectedItem());
						}
						
						stepTotal = deployCount * 3 + 2;
						jlSteps.setText("Step " + stepNumber + " of " + stepTotal + ",  Operating System " + osNumber + " of " + deployCount);
						jlTitle.setText("Select Virtual Machine Operating System");
						centerLayout.show(jpCenter, SELECTOS);
						currentState = SELECTOS;
						jcbCategory.requestFocus();
					}
					
					else if (currentState.equals(SELECTOS))
					{
						//used only if SINGLE_VM_MODE
						if (deploymentMode == SINGLE_VM_MODE) virtualMachines.add(new DeployedVirtualMachine());
						
						//configure new DeployedVirtualMachine with fields from SELECTOS
						virtualMachines.get(osNumber - 1).setOsCategory((String) jcbCategory.getSelectedItem());
						virtualMachines.get(osNumber - 1).setOsName((String) jcbOS.getSelectedItem());
						choosenOSCategory = (String) jcbCategory.getSelectedItem();
						
						//Check each array and determine if OS is client or server
						for (int i = 0; i < WINDOWS_CLIENT.length; i++)
						{
							if (virtualMachines.get(osNumber - 1).getOsName().equals(WINDOWS_CLIENT[i]))
							{
								virtualMachines.get(osNumber - 1).setOsType(CLIENT);
								choosenOSType = CLIENT;
							}
						}
						
						for (int i = 0; i < WINDOWS_SERVER.length; i++)
						{
							if (virtualMachines.get(osNumber - 1).getOsName().equals(WINDOWS_SERVER[i]))
							{
								virtualMachines.get(osNumber - 1).setOsType(SERVER);
								choosenOSType = SERVER;
							} 
						}
						
						for (int i = 0; i < RPM_CLIENT.length; i++)
						{
							if (virtualMachines.get(osNumber - 1).getOsName().equals(RPM_CLIENT[i]))
							{
								virtualMachines.get(osNumber - 1).setOsType(CLIENT);
								choosenOSType = CLIENT;
							} 
						}
						
						for (int i = 0; i < RPM_SERVER.length; i++)
						{
							if (virtualMachines.get(osNumber - 1).getOsName().equals(RPM_SERVER[i]))
							{
								virtualMachines.get(osNumber - 1).setOsType(SERVER);
								choosenOSType = SERVER;
							} 
						}
						
						for (int i = 0; i < DEBIAN_CLIENT.length; i++)
						{
							if (virtualMachines.get(osNumber - 1).getOsName().equals(DEBIAN_CLIENT[i]))
							{
								virtualMachines.get(osNumber - 1).setOsType(CLIENT);
								choosenOSType = CLIENT;
							} 
						}
						
						for (int i = 0; i < DEBIAN_SERVER.length; i++)
						{
							if (virtualMachines.get(osNumber - 1).getOsName().equals(DEBIAN_SERVER[i]))
							{
								virtualMachines.get(osNumber - 1).setOsType(SERVER);
								choosenOSType = SERVER;
							} 
						}
						
						for (int i = 0; i < BSD_SERVER.length; i++)
						{
							if (virtualMachines.get(osNumber - 1).getOsName().equals(BSD_SERVER[i]))
							{
								virtualMachines.get(osNumber - 1).setOsType(SERVER);
								choosenOSType = SERVER;
							} 
						}
						
						//move on to the next step
						jlTitle.setText("Configure This Virtual Machine");		
						jlSteps.setText("Step " + stepNumber + " of " + stepTotal + ",  Operating System " + osNumber + " of " + deployCount);
						centerLayout.show(jpCenter, GUESTINFO);
						currentState = GUESTINFO;
						jtfVMName.requestFocusInWindow();
					}
					
					else if (currentState.equals(GUESTINFO))
					{
						//Validate this form
						if (!FormValidator.isValidVirtualMachineName(jtfVMName.getText()))
						{
							FormValidator.showMessage(FormValidator.VM_NAME_INVALID, this);
						}
						
						else if (!FormValidator.isValidHostname(jtfHostname.getText()))
						{
							FormValidator.showMessage(FormValidator.HOSTNAME_INVALID, this);
						}
						
						else if (jtfIP.isEditable() && !FormValidator.isValidIPAddress(jtfIP.getText()))
						{
							FormValidator.showMessage(FormValidator.IP_ADDRESS_INVALID, this);
						}
						
						else if (jtfNetmask.isEditable() && !FormValidator.isValidSubnetMask(jtfNetmask.getText()))
						{
							FormValidator.showMessage(FormValidator.SUBNET_MASK_INVALID, this);
						}
						
						else if (jtfDefaultGateway.isEditable() && !FormValidator.isValidIPAddress(jtfDefaultGateway.getText()))
						{
							FormValidator.showMessage(FormValidator.DEFAULT_GATEWAY_INVALID, this);
						}
						
						else if (jtfDnsServer.isEditable() && !FormValidator.isValidIPAddress(jtfDnsServer.getText()))
						{
							FormValidator.showMessage(FormValidator.DNS_ADDRESS_INVALID, this);
						}
						
						//Validation successful, move on
						else
						{
							//use only if deploymentMode is CHOOSEN_VM_MODE
							if (deploymentMode == CHOOSEN_VM_MODE)
							{
								virtualMachines.add(new DeployedVirtualMachine());
								virtualMachines.get(osNumber - 1).setOsCategory(template.getOsCategory());
								virtualMachines.get(osNumber - 1).setOsType(template.getOsType());
								virtualMachines.get(osNumber - 1).setOsName(template.getName());
								
								choosenOSType = template.getOsType();
								choosenOSCategory = template.getOsCategory();
							}
							
							//configure the new DeployedVirtualMachine from GUESTINFO step
							virtualMachines.get(osNumber - 1).setVmName(jtfVMName.getText());
							virtualMachines.get(osNumber - 1).setHostName(jtfHostname.getText());
							virtualMachines.get(osNumber - 1).setDomain(jtfDomainName.getText());
							virtualMachines.get(osNumber - 1).setStaticAddress(jrbStatic.isSelected());
							virtualMachines.get(osNumber - 1).setNetwork((String) jcbVirtualNetworks.getSelectedItem());
							
							if (jrbStatic.isSelected())
							{
								virtualMachines.get(osNumber - 1).setStaticAddress(true);
								virtualMachines.get(osNumber - 1).setIpAddr(jtfIP.getText());
								virtualMachines.get(osNumber - 1).setNetmask(jtfNetmask.getText());
								virtualMachines.get(osNumber - 1).setDefaultGateway(jtfDefaultGateway.getText());
								virtualMachines.get(osNumber - 1).setDnsServer(jtfDnsServer.getText());
							}
							
							//move on to the next step
							jlSteps.setText("Step " + stepNumber + " of " + stepTotal + ".  Operating System " + osNumber + " of " + deployCount);
							if (choosenOSCategory.equals(WINDOWS))
							{
								if (choosenOSType.equals(CLIENT))
								{
									jlTitle.setText("Configure Windows Client Services");
									centerLayout.show(jpCenter, SERVICES_WINDOWS_CLIENT);
									currentState = SERVICES_WINDOWS_CLIENT;
									jbNext.requestFocus();
								}
							
								else if (choosenOSType.equals(SERVER))
								{
									jlTitle.setText("Configure Windows Server Services");
									centerLayout.show(jpCenter, SERVICES_WINDOWS_SERVER);
									currentState = SERVICES_WINDOWS_SERVER;
									jbNext.requestFocus();
								}
							}
							
							else if (choosenOSCategory.equals(LINUX_DEBIAN) || choosenOSCategory.equals(LINUX_RPM)
									|| choosenOSCategory.equals(BSD))
							{
								if (choosenOSType.equals(CLIENT))
								{
									jlTitle.setText("Configure Linux/BSD Services");
									centerLayout.show(jpCenter, SERVICES_LINUX_CLIENT);
									currentState = SERVICES_LINUX_CLIENT;
									jbNext.requestFocus();
								}
							
								else if (choosenOSType.equals(SERVER))
								{
									jlTitle.setText("Configure Linux/BSD Services");
									centerLayout.show(jpCenter, SERVICES_LINUX_SERVER);
									currentState = SERVICES_LINUX_SERVER;
									jbNext.requestFocus();
								}
							}
						}
					}				
				
					else if (currentState.equals(SERVICES_WINDOWS_CLIENT) || currentState.equals(SERVICES_WINDOWS_SERVER) ||
								currentState.equals(SERVICES_LINUX_CLIENT) || currentState.equals(SERVICES_LINUX_SERVER))
					{
						if ((jCheckBind.isSelected() && !FormValidator.isValidDomain(jtfDirectoryBind.getText())) ||
								(jLinuxBind.isSelected() && !FormValidator.isValidDomain(jtfNISBind.getText())) ||
								(jLinuxNIS.isSelected() && !FormValidator.isValidDomain(jtfNISName.getText())) ||
								(jCheckActiveDirectory.isSelected() && !FormValidator.isValidDomain(jtfDirectoryName.getText())))		
						{
							FormValidator.showMessage(FormValidator.DOMAIN_INVALID, this);
						}
						
						else
						{
							//if windows, configure DeployedVirtualMachine accordingly
							if (choosenOSCategory.equals(WINDOWS))
							{
								virtualMachines.get(osNumber - 1).setDns(jCheckDNS.isSelected());
								virtualMachines.get(osNumber - 1).setDhcp(jCheckDHCP.isSelected());
								virtualMachines.get(osNumber - 1).setActiveDirectory(jCheckActiveDirectory.isSelected());
								virtualMachines.get(osNumber - 1).setFileServer(jCheckFileServer.isSelected());
								virtualMachines.get(osNumber - 1).setIis(jCheckIIS.isSelected());
								virtualMachines.get(osNumber - 1).setBind(jCheckBind.isSelected());
								
								if (jCheckExploits.isSelected() || jCheckClientExploits.isSelected())
								{
									virtualMachines.get(osNumber - 1).setExploits(true);
								}
								
								if (jCheckAccounts.isSelected() || jCheckAccountsClient.isSelected())
								{
									virtualMachines.get(osNumber - 1).setAccounts(true);
								}
								
								if (jCheckActiveDirectory.isSelected())
								{
									virtualMachines.get(osNumber - 1).setDomain(jtfDirectoryName.getText());
								}
							}
							
							else if (choosenOSCategory.equals(LINUX_RPM) || choosenOSCategory.equals(LINUX_DEBIAN) ||
									choosenOSCategory.equals(BSD))
							{
								virtualMachines.get(osNumber - 1).setDns(jLinuxDNS.isSelected());
								virtualMachines.get(osNumber - 1).setDhcp(jLinuxDHCP.isSelected());
								virtualMachines.get(osNumber - 1).setMail(jLinuxMail.isSelected());
								virtualMachines.get(osNumber - 1).setHttp(jLinuxHTTP.isSelected());
								virtualMachines.get(osNumber - 1).setFtp(jLinuxFTP.isSelected());
								virtualMachines.get(osNumber - 1).setNfs(jLinuxNFS.isSelected());
								virtualMachines.get(osNumber - 1).setSamba(jLinuxSamba.isSelected());
								virtualMachines.get(osNumber - 1).setBind(jLinuxBind.isSelected());
								
								if (jLinuxAccountsClient.isSelected() || jLinuxAccounts.isSelected())
								{
									virtualMachines.get(osNumber - 1).setAccounts(true);
								}
								
								if (jLinuxExploits.isSelected() || jLinuxClientExploits.isSelected())
								{
									virtualMachines.get(osNumber - 1).setExploits(true);
								}
								
								if (jLinuxNIS.isSelected())
								{
									virtualMachines.get(osNumber - 1).setDomain(jtfNISName.getText());
								}
							}
							
							//determine if the wizard is finished
							if (osNumber == deployCount)
							{
								jlSteps.setText("Step " + stepNumber + " of " + stepTotal);
								jlTitle.setText("Virtual Machines Ready to Deploy");
								centerLayout.show(jpCenter, DEPLOYMENT_SUMMARY);
								currentState = DEPLOYMENT_SUMMARY;
								jbSave.setVisible(true);
								jbSave.setEnabled(true);
								jbFinish.setEnabled(true);
								jbNext.setEnabled(false);
								writeSummary(virtualMachines);
								jbFinish.requestFocus();
							}
							
							//start from the SELECTOS step if wizard is not finished
							else
							{
								osNumber++;
								centerLayout.show(jpCenter, SELECTOS);
								currentState = SELECTOS;
								resetFields();
								jlSteps.setText("Step " + stepNumber + " of " + stepTotal + ",  Operating System " + osNumber + " of " + deployCount);
								jlTitle.setText("Select Virtual Machine Operating System");
								jcbCategory.requestFocus();
							}
						}
					}
				}
				
				else if (button.getActionCommand().equalsIgnoreCase("Finish"))
				{
					engine.deploy(virtualMachines);
					dispose();
				}
				
				else if (button.getActionCommand().equalsIgnoreCase("Save"))
				{
					engine.exportLastDeployment(virtualMachines);
				}
			} //end if source is instanceof JButton
		}
		
		catch (NullPointerException e)
		{
			JOptionPane.showMessageDialog(this, "An error occurred with the deployment.\n" +
												"Check the vase.conf file and insure than the Operating Systems listed\n" +
												"match the template listing, then restart VASE Deploy", "Error", JOptionPane.ERROR_MESSAGE);
			LOG.write("NullPointer during Deployment Wizard in " + currentState + ", " + deploymentMode, true);
			e.printStackTrace();
			dispose();
		}
	}
	
	/**
	 * Monitors the event-state changing for combo boxes
	 * <br />
	 * For the SelectOS Dialog:
	 * <ul>
	 * <li>If the source is jcbCategory, fills the jcbOS combo box with choices</li>
	 * <li>If the source is jcbOS, validates the template using isTemplate() from the
	 * CommandEngine object, passed by the constructor
	 * </ul>
	 * <strong>Note: </strong>Next button is only enabled when template is valid
	 * @param event the itemStateChanged event fired by a combo box
	 */
	public void itemStateChanged(ItemEvent event)
	{
		//JComboBox
		if (event.getSource() instanceof JComboBox)
		{
			JComboBox jcb = (JComboBox) event.getSource();
			String selection = (String) jcb.getSelectedItem();
			if (event.getStateChange() == ItemEvent.SELECTED)
			{
				if (event.getSource().equals(jcbCategory))
				{
					jcbOS.removeAllItems();
					if (selection.equals(WINDOWS))
					{
						jcbOS.addItem("Select Operating System");
						
						for (int i = 0; i < WINDOWS_CLIENT.length; i++)
						{
							jcbOS.addItem(WINDOWS_CLIENT[i]);
						}
						
						for (int i = 0; i < WINDOWS_SERVER.length; i++)
						{
							jcbOS.addItem(WINDOWS_SERVER[i]);
						}
						
						jcbOS.setSelectedItem("Select Operating System");
						jlOS.setVisible(true);
						jcbOS.setVisible(true);
					}
					
					else if (selection.equals(LINUX_RPM))
					{
						jcbOS.addItem("Select Operating System");
						
						for (int i = 0; i < RPM_SERVER.length; i++)
						{
							jcbOS.addItem(RPM_SERVER[i]);
						}
						
						for (int i = 0; i < RPM_CLIENT.length; i++)
						{
							jcbOS.addItem(RPM_CLIENT[i]);
						}
						
						jcbOS.setSelectedItem("Select Operating System");
						jlOS.setVisible(true);
						jcbOS.setVisible(true);
					}
					
					else if (selection.equals(LINUX_DEBIAN))
					{
						jcbOS.addItem("Select Operating System");
						
						for (int i = 0; i < DEBIAN_SERVER.length; i++)
						{
							jcbOS.addItem(DEBIAN_SERVER[i]);
						}
						
						for (int i = 0; i < DEBIAN_CLIENT.length; i++)
						{
							jcbOS.addItem(DEBIAN_CLIENT[i]);
						}
						
						jcbOS.setSelectedItem("Select Operating System");
						jlOS.setVisible(true);
						jcbOS.setVisible(true);
					}
					
					else if (selection.equals(BSD))
					{
						jcbOS.addItem("Select Operating System");
						
						for (int i = 0; i < BSD_SERVER.length; i++)
						{
							jcbOS.addItem(BSD_SERVER[i]);
						}
						
						jcbOS.setSelectedItem("Select Operating System");
						jlOS.setVisible(true);
						jcbOS.setVisible(true);
					}
					
					else if (selection.equals("Select Category"))
					{
						jcbOS.removeAllItems();
						jcbOS.setVisible(false);
						jlOS.setVisible(false);
						jlOSValid.setVisible(false);
						jbNext.setEnabled(false);
					}
				}
				
				else if (event.getSource().equals(jcbOS))
				{
					if (!selection.equals("Select Operating System"))
					{
						if (engine.isTemplate((String) jcbOS.getSelectedItem()))
						{
							jlOSValid.setText("Operating System Template is Valid");
							jbNext.setEnabled(true);
						}
						
						else
						{
							jlOSValid.setText("Operating System Template cannot be located in the Datacenter");
							jbNext.setEnabled(false);
						}
						
						jlOSValid.setVisible(true);
					}
					
					else
					{
						jlOSValid.setVisible(false);
						jbNext.setEnabled(false);
					}
				}
			} //end if source
		} //end if instanceof JComboBox
		
		//JRadioButton
		else if (event.getSource() instanceof JRadioButton)
		{
			JRadioButton button = (JRadioButton) event.getSource();
			if (button.equals(jrbDHCP) && event.getStateChange() == ItemEvent.SELECTED)
			{
				jtfIP.setBackground(COLOR_WIZARD_BACKGROUND);
				jtfNetmask.setBackground(COLOR_WIZARD_BACKGROUND);
				jtfDefaultGateway.setBackground(COLOR_WIZARD_BACKGROUND);
				jtfIP.setText("");
				jtfNetmask.setText("");
				jtfDefaultGateway.setText("");
				jtfIP.setEditable(false);
				jtfNetmask.setEditable(false);
				jtfDefaultGateway.setEditable(false);
				jtfDnsServer.setEditable(false);
			}
			
			else if (button.equals(jrbStatic) && event.getStateChange() == ItemEvent.SELECTED)
			{
				jtfIP.setBackground(Color.WHITE);
				jtfNetmask.setBackground(Color.WHITE);
				jtfDefaultGateway.setBackground(Color.WHITE);
				jtfIP.setEditable(true);
				jtfNetmask.setEditable(true);
				jtfDefaultGateway.setEditable(true);
				jtfDnsServer.setEditable(true);
			}
		}
		
		//JCheckBox
		else if (event.getSource() instanceof JCheckBox)
		{
			JCheckBox checkBox = (JCheckBox) event.getSource();
			if (checkBox.equals(jCheckActiveDirectory))
			{
				if (event.getStateChange() == ItemEvent.SELECTED)
				{
					jtfDirectoryName.setVisible(true);
					jtfDirectoryName.setText(jtfDomainName.getText());
					jlDirectoryName.setVisible(true);
					jCheckDNS.setSelected(true);
				}
				
				else if (event.getStateChange() == ItemEvent.DESELECTED)
				{
					jtfDirectoryName.setVisible(false);
					jlDirectoryName.setVisible(false);
				}
			}
			
			else if (checkBox.equals(jLinuxNIS))
			{
				if (event.getStateChange() == ItemEvent.SELECTED)
				{
					jtfNISName.setVisible(true);
					jtfNISName.setText(jtfDomainName.getText());
					jlNISName.setVisible(true);
					jLinuxNFS.setSelected(true);
				}
				
				else if (event.getStateChange() == ItemEvent.DESELECTED)
				{
					jtfNISName.setVisible(false);
					jlNISName.setVisible(false);
				}
			}
			
			else if (checkBox.equals(jLinuxBind))
			{
				if (event.getStateChange() == ItemEvent.SELECTED)
				{
					jtfNISBind.setVisible(true);
					jlNISBind.setVisible(true);					
				}
				
				else if (event.getStateChange() == ItemEvent.DESELECTED)
				{
					jtfNISBind.setVisible(false);
					jlNISBind.setVisible(false);
				}
			}
			
			else if (checkBox.equals(jCheckBind))
			{
				if (event.getStateChange() == ItemEvent.SELECTED)
				{
					jtfDirectoryBind.setVisible(true);
					jlDirectoryBind.setVisible(true);
				}
				
				else if (event.getStateChange() == ItemEvent.DESELECTED)
				{
					jtfDirectoryBind.setVisible(false);
					jlDirectoryBind.setVisible(false);
				}
			}
		}
	}
	
	/**
	 * Resets all text fields, check boxes, combo boxes, etc to their original starting stance
	 * <br />
	 * Called whenever the current OS number and the value of deployOS do not match, meaning
	 * there are more OS's to deploy and the wizard must repeat
	 */
	private void resetFields()
	{
		//SELECTOS screen
		jcbOS.setSelectedIndex(0);
		jcbCategory.setSelectedIndex(0);
		
		//GUESTINFO screen
		jtfVMName.setText("");
		jtfHostname.setText("");
		jtfIP.setText("");
		jtfDirectoryName.setText("");
		jrbStatic.setSelected(true);
		
		//Windows Server & Client
		jCheckDNS.setSelected(false);
		jCheckDHCP.setSelected(false);
		jCheckActiveDirectory.setSelected(false);
		jCheckFileServer.setSelected(false);
		jCheckAccounts.setSelected(false);
		jCheckExploits.setSelected(false);
		jCheckBind.setSelected(false);
		jCheckAccountsClient.setSelected(false);
		jCheckClientExploits.setSelected(false);
		jCheckIIS.setSelected(false);
		
		//Linux/BSD Server & Client
		jLinuxNIS.setSelected(false);
		jLinuxNFS.setSelected(false);
		jLinuxHTTP.setSelected(false);
		jLinuxFTP.setSelected(false);
		jLinuxMail.setSelected(false);
		jLinuxDNS.setSelected(false);
		jLinuxDHCP.setSelected(false);
		jLinuxBind.setSelected(false);
		jLinuxAccounts.setSelected(false);
		jLinuxExploits.setSelected(false);
		jLinuxClientExploits.setSelected(false);
		jLinuxAccountsClient.setSelected(false);
		jLinuxSamba.setSelected(false);
	}
	
	/**
	 * Writes out the complete summary to the final step in the wizard
	 * @param machines the ArrayList of DeployedVirtualMachines created by the wizard
	 */
	private void writeSummary(ArrayList<DeployedVirtualMachine> machines)
	{
		for (DeployedVirtualMachine each : machines)
		{
			jtaDeploySummary.append(each.toString());
		}
		
		jtaDeploySummary.setCaretPosition(0);
	}
}