/**
 * Project_VASE Connect package
 */
package vase.client.connect;

import javax.swing.SpringLayout;

import vase.client.List;
import vase.client.Panel;
import vase.client.ScrollPane;

/**
 * North Panel of the GuiMain
 * @author James McNatt & Brenton Kapral
 * @version Project_VASE Connect
 */
public class GuiCenterPanel extends Panel
{
	private static final long serialVersionUID = -7890898388384771207L;
	private List list;

	/**
	 * Main Constructor
	 */
	public GuiCenterPanel(List list)
	{
		super(new SpringLayout(), false, DIM_CONNECT_MAIN_CONTENT_PANE_CENTER);
		this.list = list;
		
		makePanels();
	}
	
	/**
	 * Makes the panels and adds the attributes
	 */
	private void makePanels()
	{
		SpringLayout layout = (SpringLayout) getLayout();
		ScrollPane pane = new ScrollPane(list);
		
		add(pane);
		
		layout.putConstraint(SpringLayout.NORTH, pane, 0, SpringLayout.NORTH, this);
		layout.putConstraint(SpringLayout.EAST, pane, 0, SpringLayout.EAST, this);
		layout.putConstraint(SpringLayout.WEST, pane, 0, SpringLayout.WEST, this);
		layout.putConstraint(SpringLayout.SOUTH, pane, 0, SpringLayout.SOUTH, this);
	}
}
