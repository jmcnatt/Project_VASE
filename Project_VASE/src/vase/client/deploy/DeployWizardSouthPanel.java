/**
 * Project_VASE Deploy package
 */
package vase.client.deploy;

import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JSeparator;

import vase.client.Panel;
import vase.client.Utilities;

/**
 * South Panel for the DeployWizard
 * <br />
 * Contains the buttons for the wizard
 * @author James McNatt & Brenton Kapral
 * @version Project_VASE Deploy
 */
public class DeployWizardSouthPanel extends Panel
{
	private static final long serialVersionUID = 7182308462590951805L;

	/**
	 * Button - Next
	 */
	public JButton jbNext;
	
	/**
	 * Button - Save
	 */
	public JButton jbSave;
	
	/**
	 * Button - Finish
	 */
	public JButton jbFinish;
	
	/**
	 * Button - Cancel
	 */
	public JButton jbCancel;
	
	/**
	 * Main Constructor
	 */
	public DeployWizardSouthPanel()
	{
		super(new BorderLayout(), false, DIM_DEPLOY_SOUTH);
		
		makeItems();
		makePanels();
		setMnemonics();
	}
	
	/**
	 * Makes the attributes for this class
	 */
	private void makeItems()
	{
		jbNext = Utilities.createButton("Next", false);
		jbCancel = Utilities.createButton("Cancel", true);
		jbSave = Utilities.createButton("Save", false);
		jbFinish = Utilities.createButton("Finish", false);
	}
	
	/**
	 * Makes the panels and adds the attributes
	 */
	private void makePanels()
	{
		Panel south = new Panel(new FlowLayout(FlowLayout.CENTER, 10, 10), false, DIM_DEPLOY_SOUTH);
		south.add(jbNext);
		south.add(jbFinish);
		south.add(jbCancel);
		south.add(jbSave);
		
		add(new JSeparator(), BorderLayout.NORTH);
		add(south, BorderLayout.SOUTH);
	}
	
	/**
	 * Sets the mnemonics on the buttons
	 */
	private void setMnemonics()
	{
		jbNext.setMnemonic('N');
		jbFinish.setMnemonic('F');
		jbCancel.setMnemonic('C');
		jbSave.setMnemonic('S');
	}
}
