/**
 * Project_VASE Connect gui package
 */
package vase.client.connect.gui;

import javax.swing.JSeparator;
import javax.swing.JTextArea;
import javax.swing.SpringLayout;

import vase.client.Panel;
import vase.client.ScrollPane;

/**
 * North Panel of the GuiMain
 * @author James McNatt & Brenton Kapral
 * @version Project_VASE Connect
 */
public class MainSouthPanel extends Panel
{
	private static final long serialVersionUID = -7890898388384771207L;
	private JTextArea log;

	/**
	 * Main Constructor
	 */
	public MainSouthPanel(JTextArea log)
	{
		super(new SpringLayout(), false, DIM_CONNECT_MAIN_CONTENT_PANE_SOUTH);
		this.log = log;
		
		makePanels();
	}
	
	/**
	 * Makes the panels and adds the attributes
	 */
	private void makePanels()
	{
		SpringLayout layout = (SpringLayout) getLayout();
		ScrollPane pane = new ScrollPane(log);
		JSeparator bar = new JSeparator();
		
		add(pane);
		add(bar);
		
		layout.putConstraint(SpringLayout.NORTH, bar, 0, SpringLayout.NORTH, this);
		layout.putConstraint(SpringLayout.EAST, bar, 0, SpringLayout.EAST, this);
		layout.putConstraint(SpringLayout.WEST, bar, 0, SpringLayout.WEST, this);
		layout.putConstraint(SpringLayout.NORTH, pane, 0, SpringLayout.SOUTH, bar);
		layout.putConstraint(SpringLayout.EAST, pane, 0, SpringLayout.EAST, this);
		layout.putConstraint(SpringLayout.WEST, pane, 0, SpringLayout.WEST, this);
		layout.putConstraint(SpringLayout.SOUTH, pane, 0, SpringLayout.SOUTH, this);
	}
}
