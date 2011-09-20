/**
 * Project_VASE Deploy package
 */
package vase.client.deploy;

import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.SpringLayout;

/**
 * Center panel showing which operating system to select for a particular
 * VM.  Used in modes 1, 3, and 4
 * @author James McNatt & Brenton Kapral
 * @version Project_VASE Deploy
 */
public class DeployWizardOSPanel extends Panel
{
	private static final long serialVersionUID = -2681058619208013942L;
	
	/**
	 * Title label in the north panel
	 */
	public JLabel jlSelectOs;
	
	/**
	 * Label displaying whether or not the OS choice is valid
	 */
	public JLabel jlOsValid;
	
	/**
	 * Component label for the category of the OS
	 */
	public JLabel jlOsCategory;
	
	/**
	 * Component label for the specific OS
	 */
	public JLabel jlOsSelection;
	
	/**
	 * Combo Box for the OS category selection
	 */
	public JComboBox jcbCategory;
	
	/**
	 * Combo Box for the specific OS selection
	 */
	public JComboBox jcbOsSelection;
	
	/**
	 * Main Constructor
	 */
	public DeployWizardOSPanel()
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
		jlSelectOs = Utilities.createDeployLabel(DEPLOY_SELECTOS);
		jlOsValid = Utilities.createDeployLabel("");
		jlOsCategory = Utilities.createDeployComponentLabel("Operating System Category: ");
		jlOsSelection = Utilities.createDeployComponentLabel("Guest Operating System: ");
		jcbCategory = Utilities.createDeployComboBox(OPERATING_SYSTEM_TYPES, 0, DIM_DEPLOY_COMBO_BOX_SELECT);
		jcbOsSelection = Utilities.createDeployComboBox(null, 0, DIM_DEPLOY_COMBO_BOX_SELECT);
	}
	
	/**
	 * Makes the panels for this class and adds the attributes
	 */
	private void makePanels()
	{
		SpringLayout layoutEast = new SpringLayout();
		SpringLayout layoutWest = new SpringLayout();
		Panel north = new Panel(new FlowLayout(FlowLayout.CENTER), false, DIM_DEPLOY_CENTER_NORTH);
		Panel south = new Panel(new BorderLayout(), false, DIM_DEPLOY_CENTER_SOUTH);
		Panel east = new Panel(layoutEast, false, DIM_DEPLOY_CENTER_SOUTH_EAST);
		Panel west = new Panel(layoutWest, false, DIM_DEPLOY_CENTER_SOUTH_WEST);
		Panel southSouth = new Panel(new FlowLayout(FlowLayout.CENTER), false, DIM_DEPLOY_CENTER_SOUTH_SOUTH);
		
		north.add(jlSelectOs);
		
		west.add(jlOsCategory);
		west.add(jlOsSelection);
		layoutWest.putConstraint(SpringLayout.VERTICAL_CENTER, jlOsCategory, 15, SpringLayout.NORTH, west);
		layoutWest.putConstraint(SpringLayout.EAST, jlOsCategory, -20, SpringLayout.EAST, west);
		layoutWest.putConstraint(SpringLayout.VERTICAL_CENTER, jlOsSelection, 30, SpringLayout.VERTICAL_CENTER, jlOsCategory);
		layoutWest.putConstraint(SpringLayout.EAST, jlOsSelection, -20, SpringLayout.EAST, west);
		
		east.add(jcbCategory);
		east.add(jcbOsSelection);
		layoutEast.putConstraint(SpringLayout.VERTICAL_CENTER, jcbCategory, 15, SpringLayout.NORTH, east);
		layoutEast.putConstraint(SpringLayout.WEST, jcbCategory, 10, SpringLayout.WEST, east);
		layoutEast.putConstraint(SpringLayout.VERTICAL_CENTER, jcbOsSelection, 30, SpringLayout.VERTICAL_CENTER, jcbCategory);
		layoutEast.putConstraint(SpringLayout.WEST, jcbOsSelection, 10, SpringLayout.WEST, east);
		
		southSouth.add(jlOsValid);
		
		south.add(west, BorderLayout.WEST);
		south.add(east, BorderLayout.EAST);
		south.add(southSouth, BorderLayout.SOUTH);
		
		add(north, BorderLayout.NORTH);
		add(south, BorderLayout.SOUTH);		
	}
}
