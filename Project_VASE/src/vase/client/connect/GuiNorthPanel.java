/**
 * Project_VASE Connect package
 */
package vase.client.connect;

import javax.swing.JLabel;
import javax.swing.SpringLayout;

import vase.client.Panel;
import vase.client.Utilities;

/**
 * North Panel of the GuiMain
 * @author James McNatt & Brenton Kapral
 * @version Project_VASE Connect
 */
public class GuiNorthPanel extends Panel
{
	private static final long serialVersionUID = -7890898388384771207L;
	private JLabel title;

	/**
	 * Main Constructor
	 */
	public GuiNorthPanel()
	{
		super(new SpringLayout(), false, DIM_CONNECT_MAIN_CONTENT_PANE_NORTH);
		
		makeItems();
		makePanels();
	}
	
	/**
	 * Makes the attributes for this class
	 */
	private void makeItems()
	{
		title = Utilities.createTitleLabel("Virtual Machines", Utilities.VM_ICON);
	}
	
	/**
	 * Makes the panels and adds the attributes
	 */
	private void makePanels()
	{
		SpringLayout layout = (SpringLayout) getLayout();
		
		add(title);
		
		layout.putConstraint(SpringLayout.NORTH, title, 0, SpringLayout.NORTH, this);
		layout.putConstraint(SpringLayout.EAST, title, 0, SpringLayout.EAST, this);
		layout.putConstraint(SpringLayout.WEST, title, 0, SpringLayout.WEST, this);
		layout.putConstraint(SpringLayout.SOUTH, title, 0, SpringLayout.SOUTH, this);
	}
}
