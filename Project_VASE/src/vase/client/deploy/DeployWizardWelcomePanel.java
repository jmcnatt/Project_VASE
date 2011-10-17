/**
 * Project_VASE Deploy package
 */
package vase.client.deploy;

import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.SpringLayout;

import vase.client.Panel;
import vase.client.Utilities;

/**
 * Welcome panel used in modes 3 & 4
 * Asks for the number of VMs, and if in mode 4, the team
 * @author James McNatt & Brenton Kapral
 * @version Project_VASE Deploy
 */
public class DeployWizardWelcomePanel extends Panel
{
	private static final long serialVersionUID = -2207055760542047418L;
	
	/**
	 * Label - number of VMs to deploy
	 */
	public JLabel jlCount;
	
	/**
	 * Label - Team selection
	 */
	public JLabel jlTeam;
	
	/**
	 * ComboBox - Number of VMs to deploy
	 */
	public JComboBox jcbCount;
	
	/**
	 * ComboBox - Team selection
	 */
	public JComboBox jcbTeam;	

	/**
	 * Main Constructor
	 */
	public DeployWizardWelcomePanel()
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
		String[] numTeams = new String[ProjectConstraints.MAX_VM_DEPLOY];
		
		for (int i = 0; i < ProjectConstraints.MAX_VM_DEPLOY; i++)
		{
			numTeams[i] = Integer.toString(i + 1);
		}
		
		jlCount = Utilities.createDeployComponentLabel("Number of VMs to Deploy: ");
		jlTeam = Utilities.createDeployComponentLabel("Team: ");
		jcbTeam = Utilities.createDeployComboBox(ProjectConstraints.TEAM_NAMES, 0, DIM_DEPLOY_COMBO_BOX_NUMBER);
		jcbCount = Utilities.createDeployComboBox(numTeams, 0, DIM_DEPLOY_COMBO_BOX_NUMBER);
	}
	
	/**
	 * Makes the panels for this class and adds the attributes
	 */
	private void makePanels()
	{
		Panel north = new Panel(new FlowLayout(FlowLayout.CENTER), false, DIM_DEPLOY_CENTER_NORTH);
		Panel south = new Panel(new SpringLayout(), false, DIM_DEPLOY_CENTER_SOUTH);
		SpringLayout layout = (SpringLayout) south.getLayout();
		
		north.add(Utilities.createDeployLabel(DEPLOY_WELCOME));
		
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
		
		add(north, BorderLayout.NORTH);
		add(south, BorderLayout.SOUTH);
	}
}
