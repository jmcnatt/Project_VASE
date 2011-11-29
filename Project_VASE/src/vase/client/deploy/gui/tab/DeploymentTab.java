/**
 * Project_VASE Deploy GUI tab package
 */
package vase.client.deploy.gui.tab;

import java.util.ArrayList;

import javax.swing.JLabel;
import javax.swing.SpringLayout;

import vase.client.ScrollPane;
import vase.client.Utilities;
import vase.client.deploy.gui.DeploymentPanel;
import vase.client.deploy.gui.Tab;
import vase.client.deploy.vmo.DeployedVirtualMachine;

/**
 * Last Deployment Tab in the TabbedPane, displayed in the GuiMain
 * <br />
 * Displays a summary of the last deployment made by the wizard
 * @author James McNatt & Brenton Kapral
 * @version Project_VASE Deploy
 * @see TabbedPane
 * @see GuiMain
 */
public class DeploymentTab extends Tab
{
	private static final long serialVersionUID = 958267055923911025L;
	
	private JLabel jlDeployment;
	private DeploymentPanel deployment;
	private ScrollPane jsp;
	
	/**
	 * Main constructor
	 * Sets the layout to SpringLayout and builds the tab component
	 */
	public DeploymentTab()
	{
		super(new SpringLayout());
		makeItems();
		makePanels();
	}
	
	/**
	 * Makes the attributes for this class
	 */
	private void makeItems()
	{
		jlDeployment = Utilities.createTitleLabel("Last Deployment", Utilities.DEPLOY_ICON);
		deployment = new DeploymentPanel(SETTINGS_READER.getLastDeployment());
	}
	
	/**
	 * Makes the panels for this Tab and sets the appropriate constraints
	 */
	private void makePanels()
	{
		jsp = new ScrollPane(deployment);
		
		SpringLayout layout = (SpringLayout) this.getLayout();
		add(jlDeployment);
		add(jsp);
		
		layout.putConstraint(SpringLayout.NORTH, jlDeployment, 0, SpringLayout.NORTH, this);
		layout.putConstraint(SpringLayout.EAST, jlDeployment, 0, SpringLayout.EAST, this);
		layout.putConstraint(SpringLayout.WEST, jlDeployment, 0, SpringLayout.WEST, this);
		layout.putConstraint(SpringLayout.NORTH, jsp, 0, SpringLayout.SOUTH, jlDeployment);
		layout.putConstraint(SpringLayout.WEST, jsp, 0, SpringLayout.WEST, this);
		layout.putConstraint(SpringLayout.EAST, jsp, 0, SpringLayout.EAST, this);
		layout.putConstraint(SpringLayout.SOUTH, jsp, 0, SpringLayout.SOUTH, this);
	}
	
	/**
	 * Updates the panel with the new selection of DeployedVirtualMachine objects
	 * <br />
	 * Repaints and revalidates the panel after the update
	 * @param deployedVMs the new list of DeployedVirtualMachines
	 */
	public void updatePanel(ArrayList<DeployedVirtualMachine> deployedVMs)
	{
		deployment.removeAll();
		deployment.update(deployedVMs);
		deployment.validate();
		deployment.repaint();
		
		SETTINGS_READER.setLastDeployment(deployedVMs);		
	}
}
