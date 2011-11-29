/**
 * Project_VASE Deploy GUI wizard package
 */
package vase.client.deploy.gui.wizard;

import java.awt.Color;
import java.util.ArrayList;

import javax.swing.JLabel;
import javax.swing.JTextArea;
import javax.swing.SpringLayout;

import vase.client.Panel;
import vase.client.ScrollPane;
import vase.client.Utilities;
import vase.client.deploy.vmo.DeployedVirtualMachine;

/**
 * Windows Client panel used to customize a Windows client
 * @author James McNatt & Brenton Kapral
 * @version Project_VASE Deploy
 */
public class SummaryPanel extends Panel
{
	private static final long serialVersionUID = 8625164844708555371L;
	
	/**
	 * Label - title of this panel
	 */
	public JLabel jlSummary;
	
	/**
	 * Text Area - Contains the summary
	 */
	public JTextArea jtaSummary;

	/**
	 * Main Constructor
	 */
	public SummaryPanel()
	{
		super(new SpringLayout(), false, DIM_DEPLOY_CENTER);
		
		makeItems();
		makePanels();
	}
	
	/**
	 * Makes the attributes for this class
	 */
	private void makeItems()
	{
		jlSummary = Utilities.createDeployLabel("Click finish to begin the Virtual Machine deployment process");
		jtaSummary = Utilities.createDeployTextArea();
	}
	
	/**
	 * Makes the panels and adds the attributes
	 */
	private void makePanels()
	{
		SpringLayout layout = (SpringLayout) getLayout();
		ScrollPane pane = new ScrollPane(jtaSummary);
		
		setOpaque(true);
		setBackground(Color.WHITE);
		
		add(jlSummary);
		add(pane);
		layout.putConstraint(SpringLayout.NORTH, jlSummary, 5, SpringLayout.NORTH, this);
		layout.putConstraint(SpringLayout.HORIZONTAL_CENTER, jlSummary, 0, SpringLayout.HORIZONTAL_CENTER, this);
		layout.putConstraint(SpringLayout.NORTH, pane, 5, SpringLayout.SOUTH, jlSummary);
		layout.putConstraint(SpringLayout.SOUTH, pane, 0, SpringLayout.SOUTH, this);
		layout.putConstraint(SpringLayout.EAST, pane, 0, SpringLayout.EAST, this);
		layout.putConstraint(SpringLayout.WEST, pane, 0, SpringLayout.WEST, this);
	}
	
	/**
	 * Writes out the complete summary to the final step in the wizard
	 * @param machines the ArrayList of DeployedVirtualMachines created by the wizard
	 */
	public void writeSummary(ArrayList<DeployedVirtualMachine> machines)
	{
		for (DeployedVirtualMachine each : machines)
		{
			jtaSummary.append(each.toString());
		}
		
		jtaSummary.setCaretPosition(0);
	}
}