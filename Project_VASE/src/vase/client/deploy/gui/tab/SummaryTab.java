/**
 * Project_VASE Deploy GUI tab package
 */
package vase.client.deploy.gui.tab;

import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JScrollPane;
import javax.swing.SpringLayout;

import vase.client.ScrollPane;
import vase.client.Utilities;
import vase.client.deploy.gui.Tab;
import vase.client.deploy.gui.tree.Tree;

/**
 * Summary Tab in the TabbedPane, displayed in GuiMain in the center JPanel
 * <br />
 * This tab displays the following information:
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
public class SummaryTab extends Tab
{
	private static final long serialVersionUID = -6586831817437381689L;
	private Tree jlFolders;
	private JList jlVMs;
	private JList jlTemplates;
	private JLabel foldersLabel;
	private JLabel vmLabel;
	private JLabel templateLabel;
	
	/**
	 * Main Constructor - takes in JList from GuiMain
	 * @param jlFolders the list of folders
	 * @param jlTemplates the list of templates
	 * @param jlVMs the list of Virtual Machines
	 */
	public SummaryTab(Tree datacenterTree, JList jlTemplates, JList jlVMs)
	{
		super(new SpringLayout());
		this.jlTemplates = jlTemplates;
		this.jlVMs = jlVMs;
		this.jlFolders = datacenterTree;
		
		makeItems();
		makePanels();
	}
	
	/**
	 * Makes the attributes for this class
	 */
	private void makeItems()
	{
		foldersLabel = Utilities.createTitleLabel("Folders", Utilities.FOLDER_ICON);
		vmLabel = Utilities.createTitleLabel("Virtual Machines", Utilities.VM_ICON);
		templateLabel = Utilities.createTitleLabel("Templates", Utilities.TEMPLATE_ICON);
	}
	
	/**
	 * Makes the panels for this Tab and sets the appropriate constraints
	 */
	private void makePanels()
	{
		ScrollPane jspFolders = new ScrollPane(jlFolders);
		ScrollPane jspTemplates = new ScrollPane(jlTemplates);
		ScrollPane jspVMs = new ScrollPane(jlVMs);
		
		jspFolders.setBorder(null);
		jspFolders.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		jspFolders.setPreferredSize(DIM_MAIN_CONTENT_SUMMARY_LABEL);
		
		jspTemplates.setBorder(null);
		jspTemplates.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		jspTemplates.setPreferredSize(DIM_MAIN_CONTENT_SUMMARY_LABEL);
		
		jspVMs.setBorder(null);
		jspVMs.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		jspVMs.setPreferredSize(DIM_MAIN_CONTENT_SUMMARY_LABEL);
		
		add(jspFolders);
		add(jspVMs);
		add(jspTemplates);
		add(foldersLabel);
		add(vmLabel);
		add(templateLabel);
		
		SpringLayout layout = (SpringLayout) this.getLayout();
		
		layout.putConstraint(SpringLayout.NORTH, foldersLabel, 0, SpringLayout.NORTH, this);
		layout.putConstraint(SpringLayout.WEST, foldersLabel, 0, SpringLayout.WEST, this);
		layout.putConstraint(SpringLayout.NORTH, jspFolders, 0, SpringLayout.SOUTH, foldersLabel);
		layout.putConstraint(SpringLayout.WEST, jspFolders, 0, SpringLayout.WEST, this);
		layout.putConstraint(SpringLayout.SOUTH, jspFolders, 1, SpringLayout.SOUTH, this);
		layout.putConstraint(SpringLayout.NORTH, templateLabel, 0, SpringLayout.NORTH, this);
		layout.putConstraint(SpringLayout.WEST, templateLabel, 1, SpringLayout.EAST, foldersLabel);
		layout.putConstraint(SpringLayout.NORTH, jspTemplates, 0, SpringLayout.SOUTH, templateLabel);
		layout.putConstraint(SpringLayout.WEST, jspTemplates, 1, SpringLayout.EAST, jspFolders);
		layout.putConstraint(SpringLayout.SOUTH, jspTemplates, 1, SpringLayout.SOUTH, this);
		layout.putConstraint(SpringLayout.NORTH, vmLabel, 0, SpringLayout.NORTH, this);
		layout.putConstraint(SpringLayout.WEST, vmLabel, 1, SpringLayout.EAST, templateLabel);
		layout.putConstraint(SpringLayout.EAST, vmLabel, 0, SpringLayout.EAST, this);
		layout.putConstraint(SpringLayout.NORTH, jspVMs, 0, SpringLayout.SOUTH, vmLabel);
		layout.putConstraint(SpringLayout.WEST, jspVMs, 1, SpringLayout.EAST, jspTemplates);
		layout.putConstraint(SpringLayout.SOUTH, jspVMs, 1, SpringLayout.SOUTH, this);
		layout.putConstraint(SpringLayout.EAST, jspVMs, 0, SpringLayout.EAST, this);
	}
}
