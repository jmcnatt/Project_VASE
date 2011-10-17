/**
 * Project_VASE Deploy package
 */
package vase.client.deploy;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.SpringLayout;

import vase.client.Utilities;

/**
 * Summary Tab in the TabbedPane, displayed in GuiMain in the center JPanel
 * <br />
 * This tab displayed the following information:
 * <ul>
 * <li>Folder List - list of all folders in the datacenter</li>
 * <li>Template List - list of all templates in the TemplateDir</li>
 * <li>Virtual Machines - list and quick summary of all Virtual Machines</li>
 * </ul>
 * Relies on the command engine to populate the JList data
 * @author James McNatt & Brenton Kapral
 * @version Project_VASE Deploy
 * @see TabbedPane
 * @see CommandEngine#getListFolders()
 * @see CommandEngine#getListTemplates()
 * @see CommandEngine#getListVMs()
 */
public class GuiVirtualMachineTab extends Tab
{
	private static final long serialVersionUID = -7015163893210646820L;
	private JLabel vmLabel;
	private JTable table;

	public GuiVirtualMachineTab(JTable table)
	{
		super(new SpringLayout());
		this.table = table;
		
		makeItems();
		makePanels();
	}
	
	/**
	 * Makes the attributes for this class
	 */
	private void makeItems()
	{
		vmLabel = Utilities.createTitleLabel("Virtual Machines", Utilities.VM_ICON);
	}
	
	/**
	 * Makes the panel and binds the label and table
	 */
	private void makePanels()
	{
		JPanel vmTable = super.createMainVMPanel(table);
		SpringLayout layout = (SpringLayout) this.getLayout();
		
		add(vmTable);
		add(vmLabel);
		
		layout.putConstraint(SpringLayout.NORTH, vmLabel, 0, SpringLayout.NORTH, this);
		layout.putConstraint(SpringLayout.WEST, vmLabel, 0, SpringLayout.WEST, this);
		layout.putConstraint(SpringLayout.EAST, vmLabel, 0, SpringLayout.EAST, this);
		layout.putConstraint(SpringLayout.NORTH, vmTable, 0, SpringLayout.SOUTH, vmLabel);
		layout.putConstraint(SpringLayout.EAST, vmTable, 0, SpringLayout.EAST, this);
		layout.putConstraint(SpringLayout.WEST, vmTable, 0, SpringLayout.WEST, this);
		layout.putConstraint(SpringLayout.SOUTH, vmTable, 0, SpringLayout.SOUTH, this);
	}
}
