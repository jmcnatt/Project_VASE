/**
 * Project_VASE Deploy GUI package
 */
package vase.client.deploy.gui;

import java.awt.LayoutManager;

import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.SpringLayout;

import vase.client.Panel;
import vase.client.ScrollPane;
import vase.client.deploy.ProjectConstraints;

/**
 * Tab Superclass for the TabbedPane layout
 * @author James McNatt & Brenton Kapral
 * @see TabbedPane
 */
public class Tab extends Panel implements ProjectConstraints
{
	private static final long serialVersionUID = 4217950516183266020L;

	/**
	 * Main constructor
	 * @param manager the layout manager
	 */
	public Tab(LayoutManager manager)
	{
		super(manager, true);
		setBackground(COLOR_HEADER_BACKGROUND);
	}
	
	/**
	 * Creates a deployment label
	 * @param text
	 * @return
	 */
	public static JLabel createMainDeploymentLabel(String text)
	{
		JLabel label = new JLabel(text);
		label.setHorizontalTextPosition(JLabel.CENTER);
		label.setFont(FONT_LABEL_HEADER);
		label.setOpaque(false);
		
		return label;
	}
	
	/**
	 * Creates a general lab used in the Last Deployment Tab
	 * @param text the text for the label
	 * @return the pre-formatted label
	 */
	public JLabel createTabTitleLabel(String text)
	{
		JLabel label = new JLabel(text);
		label.setFont(FONT_LABEL_HEADER);
		label.setOpaque(false);
		
		return label;
	}
	
	/**
	 * Creates a general lab used in the Last Deployment Tab
	 * @param text the text for the label
	 * @return the pre-formatted label
	 */
	public JLabel createTabLabel(String text)
	{
		JLabel label = new JLabel(text);
		label.setOpaque(false);
		
		return label;
	}
	
	/**
	 * Creates the VM Summary JPanel taking in the table and the tabel's model
	 * @param table the summary table containing information about the VMs
	 * @param model the model the table uses
	 * @return panel the formatted JPanel
	 * @see GuiMain#makePanels()
	 */
	public Panel createMainVMPanel(JTable table)
	{
		ScrollPane scrollPane = new ScrollPane(table);
		Panel panel = new Panel(new SpringLayout(), false);
		SpringLayout layout = (SpringLayout) panel.getLayout();
		panel.setLayout(layout);
		panel.add(scrollPane);
		
		layout.putConstraint(SpringLayout.NORTH, scrollPane, 0, SpringLayout.NORTH, panel);
		layout.putConstraint(SpringLayout.WEST, scrollPane, 0, SpringLayout.WEST, panel);
		layout.putConstraint(SpringLayout.EAST, scrollPane, 0, SpringLayout.EAST, panel);
		layout.putConstraint(SpringLayout.SOUTH, scrollPane, 0, SpringLayout.SOUTH, panel);
		
		return panel;		
	}
}
