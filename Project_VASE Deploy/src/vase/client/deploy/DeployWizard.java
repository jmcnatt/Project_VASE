/**
 * Project_VASE Deploy package
 */
package vase.client.deploy;

import java.awt.BorderLayout;
import java.util.ArrayList;

/**
 * Wizard that walks through a virtual machine deployment
 * Started in four different modes:
 * <ul>
 * <li>Mode 1 - Single VM from the selectOS menu</li>
 * <li>Mode 2 - Single VM by selecting a template first</li>
 * <li>Mode 3 - Multiple VMs from the welcome menu</li>
 * <li>Mode 4 - Multiple VMs from the welcome menu with a delegated team</li>
 * </ul>
 * @author James McNatt & Brenton Kapral
 * @version Project_VASE Deploy
 */
public class DeployWizard extends GuiDialog
{
	private static final long serialVersionUID = 4740717182618966777L;
	
	/**
	 * Deployment mode
	 * @see DeployWizard#SINGLE_VM_MODE
	 * @see DeployWizard#TEMPLATE_VM_MODE
	 * @see DeployWizard#GROUP_VMS_MODE
	 * @see DeployWizard#TEAM_VMS_MODE
	 */
	public int mode;
	
	/**
	 * When using mode 2, the template object
	 */
	public Template template;
	
	/**
	 * Main parent frame
	 */
	public GuiMain window;
	
	/**
	 * The total number of steps
	 */
	public int stepTotal;
	
	/**
	 * The current step number
	 */
	public int stepNumber = 1;
	
	/**
	 * The total number of operating systems to deploy
	 */
	public int osTotal = 1;
	
	/**
	 * The current Operating System
	 */
	public int osNumber = 1;
	
	/**
	 * The chosen OS type (CLIENT or SERVER)
	 */
	public String chosenOSType;
	
	/**
	 * The chosen OS Category
	 */
	public String chosenOSCategory;
	
	/**
	 * North panel containing the title, icon, and subtitle
	 */
	public DeployWizardNorthPanel north;
	
	/**
	 * Center panel containing the various screens
	 */
	public DeployWizardCenterPanel center;
	
	/**
	 * South panel containing the buttons
	 */
	public DeployWizardSouthPanel south;
	
	/**
	 * Main Action Listener
	 */
	public DeployWizardActionListener actionListener;
	
	/**
	 * Main Item Listener
	 */
	public DeployWizardItemListener itemListener;
	
	/**
	 * Collection of new virtual machines built by this wizard
	 */
	public ArrayList<DeployedVirtualMachine> virtualMachines;
	
	/**
	 * Single VM starting from the selectOS menu
	 */
	public static final int SINGLE_VM_MODE = 1;
	
	/**
	 * Single VM from a template
	 * template parameter in main constructor must not be null
	 * @see DeployWizard#DeployWizard(GuiWindow, int, Template)
	 */
	public static final int TEMPLATE_VM_MODE = 2;
	
	/**
	 * Group of VMs starting from the welcome menu
	 */
	public static final int GROUP_VMS_MODE = 3;
	
	/**
	 * Group of VMs with team designation starting from the welcome menu
	 */
	public static final int TEAM_VMS_MODE = 4;
	
	/**
	 * Main Constructor - builds the wizard
	 * @param window the parent window
	 * @param mode the deployment mode
	 * @param template the template used if mode 2
	 */
	public DeployWizard(GuiMain window, int mode, Template template)
	{
		super(window, "VASE Deployment Wizard", DIM_DEPLOY_WINDOW);
		this.window = window;
		this.mode = mode;
		this.template = template;
		
		addWindowListener(new GuiWindowListener());
		
		makeItems();
		makePanels();
		
		setStartingScreen();
		
		pack();
		setVisible(true);
	}
	
	/**
	 * Makes the attributes for this class
	 */
	private void makeItems()
	{
		north = new DeployWizardNorthPanel();
		center = new DeployWizardCenterPanel();
		south = new DeployWizardSouthPanel();
		actionListener = new DeployWizardActionListener(this);
		itemListener = new DeployWizardItemListener(this);
		virtualMachines = new ArrayList<DeployedVirtualMachine>();
	}
	
	/**
	 * Makes the panels and adds the attributes
	 */
	private void makePanels()
	{
		setLayout(new BorderLayout());
		add(north, BorderLayout.NORTH);
		add(center, BorderLayout.CENTER);
		add(south, BorderLayout.SOUTH);
	}
	
	/**
	 * Sets the attributes values depending on the first mode selection
	 */
	private void setStartingScreen()
	{
		//Set Visibility
		south.jbSave.setVisible(false);
		center.osPanel.jlOsSelection.setVisible(false);
		center.osPanel.jcbOsSelection.setVisible(false);
		
		//Populate Virtual Network list
		for (int i = 0; i < window.engine.getNetworksAsStrings().length; i++)
		{
			center.guestInfoPanel.jcbVirtualNetworks.addItem(window.engine.getNetworksAsStrings()[i]);
		}
		
		switch (mode)
		{
			case SINGLE_VM_MODE:
			{
				stepTotal = 4;
				updateSubTitleLabel(true);
				center.show(DeployWizardCenterPanel.SELECTOS);
				break;
			}
			
			case TEMPLATE_VM_MODE:
			{
				stepTotal = 3;
				updateTitleLabel("Configure Settings for " + template.getName());
				updateSubTitleLabel(true);
				center.show(DeployWizardCenterPanel.GUEST_INFO);
				south.jbNext.setEnabled(true);
				break;
			}
			
			case GROUP_VMS_MODE:
			{
				center.welcomePanel.jlTeam.setVisible(false);
				center.welcomePanel.jcbTeam.setVisible(false);
			}
			
			case TEAM_VMS_MODE:
			{
				stepTotal = 4;
				center.show(DeployWizardCenterPanel.WELCOME);
				south.jbNext.setEnabled(true);
				break;
			}
		}
	}
	
	/**
	 * Sets the SubTitle label text on the North Panel
	 * @param stepCount
	 * @param osCountPresent
	 * @param osCount
	 */
	public void updateSubTitleLabel(boolean osCountPresent)
	{
		String label = new String();
		
		label = "Step " + stepNumber + " of " + stepTotal;
		
		if (osCountPresent)
		{
			label += ". Operating System " + osNumber + " of " + osTotal;
		}
		
		north.jlSubtitle.setText(label);
	}
	
	/**
	 * Sets the title label text on the North Panel
	 * @param text the text to display
	 */
	public void updateTitleLabel(String text)
	{
		north.jlTitle.setText(text);
	}
}
