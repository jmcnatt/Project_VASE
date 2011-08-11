/**
 * Project_VASE Deploy package
 */
package vase.client.deploy;

import java.awt.Color;
import java.io.IOException;
import javax.swing.BorderFactory;
import javax.swing.JEditorPane;
import javax.swing.JLabel;
import javax.swing.SpringLayout;

/**
 * Help Tab in the TabbedPane, displayed in GuiMain in the center JPanel
 * <br />
 * This tab displays the Help when called via the 'Show Help Tab' in the menu bar
 * @author James McNatt & Brenton Kapral
 * @version Project_VASE Deploy
 * @see TabbedPane
 * @see GuiMain
 */
public class GuiHelpTab extends Tab
{
	private static final long serialVersionUID = 4333723897836971350L;
	private JEditorPane viewer = null;
	private JLabel jlHelp;

	public GuiHelpTab()
	{
		super(new SpringLayout());
		makeItems();
		makePanels();
	}
	
	/**
	 * Makes the attributes for this class
	 */
	public void makeItems()
	{
		try
		{
			viewer = new JEditorPane(getClass().getResource("/html/help.html"));
			viewer.setEditable(false);
			viewer.setBorder(BorderFactory.createLineBorder(Color.WHITE, 5));
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
		
		jlHelp = createMainStatusLabel("Help", HELP_ICON);
		
	}
	
	/**
	 * Makes the panels for this Tab and sets the appropriate constraints
	 */
	public void makePanels()
	{
		SpringLayout layout = (SpringLayout) this.getLayout();
				
		ScrollPane jsp = new ScrollPane(viewer);
		add(jlHelp);
		add(jsp);
		
		layout.putConstraint(SpringLayout.NORTH, jlHelp, 0, SpringLayout.NORTH, this);
		layout.putConstraint(SpringLayout.EAST, jlHelp, 0, SpringLayout.EAST, this);
		layout.putConstraint(SpringLayout.WEST, jlHelp, 0, SpringLayout.WEST, this);
		layout.putConstraint(SpringLayout.NORTH, jsp, 0, SpringLayout.SOUTH, jlHelp);
		layout.putConstraint(SpringLayout.SOUTH, jsp, 0, SpringLayout.SOUTH, this);
		layout.putConstraint(SpringLayout.WEST, jsp, 0, SpringLayout.WEST, this);
		layout.putConstraint(SpringLayout.EAST, jsp, 0, SpringLayout.EAST, this);
	}
}