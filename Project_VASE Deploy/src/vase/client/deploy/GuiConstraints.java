/**
 * Project_VASE Deploy package
 */
package vase.client.deploy;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;

/**
 * Swing Constraints for all Gui/Swing components
 * Includes Fonts, Dimensions, Colors, etc needed as constants for any subclass 
 * extending GuiWindow or GuiDialog
 * @author James McNatt & Brenton Kapral
 * @version Project_VASE Deploy
 */
public interface GuiConstraints
{
	//Dimension Objects for GuiMain
	
	/**
	 * Dimensions of the main content pane
	 * @see GuiMain
	 */
	Dimension DIM_MAIN_CONTENT_PANE = new Dimension(850, 675);
	
	/**
	 * Default dimensions for labels in all tabs on the TabbedPane
	 * @see GuiWindow#createDeployTitleLabel(String)
	 */
	Dimension DIM_MAIN_CONTENT_SUMMARY_LABEL = new Dimension(250, 30);
	
	/**
	 * Dimensions for a tab in the Tabbed Pane
	 * @see TabbedPane
	 */
	Dimension DIM_MAIN_CONTENT_ACTIVE_PANE = new Dimension(850, 575);
	
	/**
	 * Dimensions for a single entry in the Last Deployedment tab
	 * @see GuiDeploymentTabs
	 * @see GuiDeploymentTabEntry
	 */
	Dimension DIM_MAIN_DEPLOYMENT_TAB_ENTRY = new Dimension(550, 275);
	
	/**
	 * Dimensions for the log on the GuiMain, displaying under the TabbedPane
	 * @see GuiMain
	 */
	Dimension DIM_MAIN_CONTENT_LOG = new Dimension(850, 100);
	
	/**
	 * Dimensions for LoginSplash content pane
	 * @see LoginSplash
	 */
	Dimension DIM_MAIN_LOGIN_SPLASH = new Dimension(600, 410);
	
	/**
	 * Dimensions for the north panel in the LoginSplash
	 * @see LoginSplash
	 */
	Dimension DIM_MAIN_LOGIN_NORTH = new Dimension(600, 200);
	
	/**
	 * Dimensions for the south panel in the LoginSplash
	 * @see LoginSplash
	 */
	Dimension DIM_MAIN_LOGIN_SOUTH = new Dimension(600, 210);
	
	//Dimension Object for Dialogs
	
	/**
	 * Dimensions for the AboutDialog content pane
	 * @see AboutDialog
	 */
	Dimension DIM_DIALOG_ABOUT_MAIN = new Dimension(400, 325);
	
	/**
	 * Dimensions for the AboutDialog north panel
	 * @see AboutDialog
	 */
	Dimension DIM_DIALOG_ABOUT_NORTH = new Dimension(400, 125);
	
	/**
	 * Dimensions for the AboutDialog south panel
	 * @see AboutDialog
	 */
	Dimension DIM_DIALOG_ABOUT_SOUTH = new Dimension(400, 200);
	
	//Dimension Objects for GuiDeployWizard
	
	/**
	 * Dimensions for the deploy wizard content pane
	 * @see GuiDeployWizard
	 */
	Dimension DIM_DEPLOY_WINDOW = new Dimension(600, 500);
	
	/**
	 * Dimensions for the deploy wizard north panel
	 * @see GuiDeployWizard
	 */
	Dimension DIM_DEPLOY_NORTH = new Dimension(600, 75);
	
	/**
	 * Dimensions for the deploy wizard south panel
	 * @see GuiDeployWizard
	 */
	Dimension DIM_DEPLOY_SOUTH = new Dimension(600, 50);
	
	/**
	 * Dimensions for the deploy wizard center panel
	 * @see GuiDeployWizard
	 */
	Dimension DIM_DEPLOY_CENTER = new Dimension(600, 375);
	
	/**
	 * Dimensions for the deploy wizard north-west panel
	 * @see GuiDeployWizard
	 */
	Dimension DIM_DEPLOY_NORTH_WEST = new Dimension(75, 75);
	
	/**
	 * Dimensions for the deploy wizard north-east pznel
	 * @see GuiDeployWizard
	 */
	Dimension DIM_DEPLOY_NORTH_EAST = new Dimension(525, 75);
	
	/**
	 * Dimensions for the deploy wizard center-north panel
	 * @see GuiDeployWizard
	 */
	Dimension DIM_DEPLOY_CENTER_NORTH = new Dimension(600, 75);
	
	/**
	 * Dimensions for the deploy wizard center-south panel
	 * @see GuiDeployWizard
	 */
	Dimension DIM_DEPLOY_CENTER_SOUTH = new Dimension(600, 300);
	
	/**
	 * Dimensions for the deploy wizard south-west panel
	 * @see GuiDeployWizard
	 */
	Dimension DIM_DEPLOY_CENTER_SOUTH_WEST = new Dimension(300, 275);
	
	/**
	 * Dimensions for the deploy wizard south-east panel
	 * @see GuiDeployWizard
	 */
	Dimension DIM_DEPLOY_CENTER_SOUTH_EAST = new Dimension(300, 275);
	
	/**
	 * Dimensions for the deploy wizard south-south panel
	 * @see GuiDeployWizard
	 */
	Dimension DIM_DEPLOY_CENTER_SOUTH_SOUTH = new Dimension(600, 25);
	
	/**
	 * Dimensions for the deploy wizard combo box for the number of teams
	 * @see GuiWindow#createDeployComboBox(Object[])
	 * @see GuiDeployWizard
	 */
	Dimension DIM_DEPLOY_COMBO_BOX_NUMBER = new Dimension(75, 20);
	
	/**
	 * Dimensions for the deploy wizard combo boxes for OS selection
	 * @see GuiWindow#createDeployComboBox(Object[])
	 * @see GuiDeployWizard
	 */
	Dimension DIM_DEPLOY_COMBO_BOX_SELECT = new Dimension(200, 20);
	
	/**
	 * Dimensions for the deploy wizard JButtons
	 * @see GuiWindow#createMainButton(String)
	 * @see GuiDeployWizard
	 */
	Dimension DIM_DEPLOY_BUTTON = new Dimension(70, 25);
	
	/**
	 * Dimensions for the HelpWindow content pane
	 * @see HelpWindow
	 */
	Dimension DIM_HELP_CONTENT_PANE = new Dimension(350, 600);
	
	//Color Objects
	
	/**
	 * Main background color for all GUI components
	 */
	Color COLOR_MAIN_BACKGROUND = new Color(240, 240, 240);
	
	/**
	 * Header background for title labels
	 */
	Color COLOR_HEADER_BACKGROUND = new Color(57, 81, 107);
	
	/**
	 * Header foreground for title labels
	 */
	Color COLOR_HEADER_FOREGROUND = new Color(255, 255, 255);
	
	/**
	 * Color when a swing component is selected
	 * @see ListIconRenderer
	 * @see TableDataRenderer
	 */
	Color COLOR_SWING_SELECTION = new Color(230, 230, 230);
	
	/**
	 * Background color for the deploy wizard
	 * @see GuiDeployWizard
	 */
	Color COLOR_WIZARD_BACKGROUND = new Color(240, 240, 240);
	
	/**
	 * Background color for the deploy wizard title (north panel)
	 * @see GuiDeployWizard
	 */
	Color COLOR_WIZARD_TITLE = new Color(245, 245, 245);
	
	/**
	 * Color of text in the VM List for Powered On VMs
	 * @see ListIconRenderer
	 */
	Color COLOR_POWERED_ON = new Color(8, 138, 0);
	
	/**
	 * Color of text in the VM List for Powered Off VMs
	 * @see ListIconRenderer
	 */
	Color COLOR_POWERED_OFF = new Color(225, 93, 85);
	
	/**
	 * Color of text in the VM List for Suspended VMs
	 * @see ListIconRenderer
	 */
	Color COLOR_SUSPENDED = new Color(243, 173, 93);
	
	/**
	 * Color of text on the Error Label on the LoginSplash
	 * @see LoginSplash
	 */
	Color COLOR_ERROR_LABEL = new Color(255, 79, 79);
	
	//Font Objects
	
	/**
	 * Title label font
	 * @see Tab#createTabTitleLabel(String)
	 */
	Font FONT_LABEL_HEADER = new Font("Tahoma", Font.BOLD, 16);
	
	/**
	 * Log font
	 */
	Font FONT_MAIN_LOG = new Font("Courier New", Font.PLAIN, 12);
	
	/**
	 * Deploy Wizard title font
	 * @see GuiDeployWizard
	 */
	Font FONT_DEPLOY_TITLE = new Font("Tahoma", Font.PLAIN, 18);
	
	/**
	 * Deploy wizard center labels
	 * @see GuiDeployWizard
	 */
	Font FONT_DEPLOY_SUBLABEL = new Font("Tahoma", Font.PLAIN, 12);
	
	/**
	 * Deploy wizard component labels for fields
	 * @see GuiWindow
	 * @see GuiDeployWizard
	 */
	Font FONT_DEPLOY_COMPONENT_LABEL = new Font("Tahoma", Font.BOLD, 12);
	
	/**
	 * Deploy wizard text field font
	 * @see GuiWindow
	 * @see GuiDeployWizard
	 */
	Font FONT_DEPLOY_TEXT_FIELD = new Font("Tahoma", Font.PLAIN, 12);
	
	//VMs tab - Column Headings
	
	/**
	 * Column names for the Virtual Machine Tab on the TabbedPane
	 * @see GuiVirtualMachineTab
	 * @see Table
	 * @see TableListener
	 * @see TabbedPane
	 */
	String[] COLUMN_HEADINGS = {" ", "Name", "Operating System", "Team", "Power Status", "CPU Usage", "Mem Usage", "Host", "IP Address", "Hostname", "Notes"};
}
