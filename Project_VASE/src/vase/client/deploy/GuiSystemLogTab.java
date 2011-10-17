/**
 * Project_VASE Deploy package
 */
package vase.client.deploy;

import javax.swing.JLabel;
import javax.swing.JTextArea;
import javax.swing.SpringLayout;

import vase.client.ScrollPane;
import vase.client.Utilities;

/**
 * System Log Tab in the TabbedPane, displayed in GuiMain in the center JPanel
 * <br />
 * This tab displays the Program Log
 * Relies on GuiMain to pass the instance of the JTextArea log
 * @author James McNatt & Brenton Kapral
 * @version Project_VASE Deploy
 * @see TabbedPane
 * @see GuiMain
 */
public class GuiSystemLogTab extends Tab
{
	private static final long serialVersionUID = -2657371242614027315L;
	
	private JTextArea jtaLog;
	private JLabel jlLog;
	
	/**
	 * Main constructor
	 * Sets the layout to SpringLayout and builds the tab component
	 */
	public GuiSystemLogTab(JTextArea jtaLog)
	{
		super(new SpringLayout());
		this.jtaLog = jtaLog;
		
		makeItems();
		makePanels();
	}
	
	/**
	 * Makes the attributes for this class
	 */
	private void makeItems()
	{
		jlLog = Utilities.createTitleLabel("System Log", Utilities.LOG_ICON);
	}
	
	/**
	 * Makes the panels for this Tab and sets the appropriate constraints
	 */
	private void makePanels()
	{
		ScrollPane jsp = new ScrollPane(jtaLog);
		SpringLayout layout = (SpringLayout) this.getLayout();
		
		add(jlLog);
		add(jsp);
		
		layout.putConstraint(SpringLayout.NORTH, jlLog, 0, SpringLayout.NORTH, this);
		layout.putConstraint(SpringLayout.EAST, jlLog, 0, SpringLayout.EAST, this);
		layout.putConstraint(SpringLayout.WEST, jlLog, 0, SpringLayout.WEST, this);
		layout.putConstraint(SpringLayout.NORTH, jsp, 0, SpringLayout.SOUTH, jlLog);
		layout.putConstraint(SpringLayout.SOUTH, jsp, 0, SpringLayout.SOUTH, this);
		layout.putConstraint(SpringLayout.WEST, jsp, 0, SpringLayout.WEST, this);
		layout.putConstraint(SpringLayout.EAST, jsp, 0, SpringLayout.EAST, this);
	}
}
