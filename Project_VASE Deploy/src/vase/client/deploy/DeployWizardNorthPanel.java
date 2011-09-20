/**
 * Project_VASE Deploy package
 */
package vase.client.deploy;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JSeparator;

/**
 * North Panel for the Deploy Wizard
 * <br />
 * Displays the VASE icon, the title, and the subtitle.  Attribute of 
 * the DeployWizard class.
 * @author James McNatt & Brenton Kapral
 * @version Proejct_VASE Deploy
 * @see DeployWizard
 */
public class DeployWizardNorthPanel extends Panel
{
	private static final long serialVersionUID = 6884750550565271303L;
	
	/**
	 * Title label on the north panel
	 */
	public JLabel jlTitle;
	
	/**
	 * Subtitle label under the title label on the north panel
	 */
	public JLabel jlSubtitle;
	
	/**
	 * Main Constructor
	 */
	public DeployWizardNorthPanel()
	{
		super(new BorderLayout(), true);
		setBackground(COLOR_WIZARD_TITLE);
		setPreferredSize(DIM_DEPLOY_NORTH);
		
		makeItems();
		makePanels();
	}
	
	/**
	 * Makes the attributes for this class
	 */
	private void makeItems()
	{
		jlTitle = Utilities.createTitleLabel("Welcome to the VASE Deploy Wizard");
		jlSubtitle = Utilities.createDeployComponentLabel("Step 1 of 4");
	}
	
	/**
	 * Adds the attributes to sub panels and adds them to this panel
	 */
	private void makePanels()
	{
		Panel west = new Panel(new FlowLayout(FlowLayout.CENTER), false, DIM_DEPLOY_NORTH_WEST);
		Panel east = new Panel(new GridLayout(2,1), false, DIM_DEPLOY_NORTH_EAST);
		Panel eastNorth = new Panel(new FlowLayout(FlowLayout.CENTER), false);
		Panel eastSouth = new Panel(new FlowLayout(FlowLayout.CENTER), false);
		JLabel icon = new JLabel(new ImageIcon(getClass().getResource("/images/vase-icon.png")));
		
		eastNorth.add(jlTitle);
		eastSouth.add(jlSubtitle);
		
		west.add(icon);
		
		east.add(eastNorth);
		east.add(eastSouth);
		
		add(west, BorderLayout.WEST);
		add(east, BorderLayout.EAST);
		add(new JSeparator(), BorderLayout.SOUTH);
	}
	
	/**
	 * Sets the title of the Deploy Wizard
	 * @param text the text to set
	 */
	public void setTitle(String text)
	{
		jlTitle.setText(text);
	}
	
	/**
	 * Sets the text under the title of the Deploy Wizard
	 * @param text the text to set
	 */
	public void setSubTitle(String text)
	{
		jlSubtitle.setText(text);
	}
}
