/**
 * Project_VASE Deploy GUI package
 */
package vase.client.deploy.gui;

import javax.swing.ImageIcon;
import javax.swing.JTabbedPane;

/**
 * Tabbed Pane class used in the GuiMain class
 * @author James McNatt & Brenton Kapral
 * @version Project_VASE Deploy
 * @see GuiMain
 */
public class TabbedPane extends JTabbedPane
{
	private static final long serialVersionUID = -608629111480611753L;
	private ImageIcon summary = new ImageIcon(getClass().getResource("/images/deploy/home-icon-title.png"));
	private ImageIcon vm = new ImageIcon(getClass().getResource("/images/deploy/vm-icon-title.png"));
	private ImageIcon deploy = new ImageIcon(getClass().getResource("/images/deploy/deployment-icon-title.png"));
	private ImageIcon log = new ImageIcon(getClass().getResource("/images/deploy/system-icon-title.png"));
	private ImageIcon help = new ImageIcon(getClass().getResource("/images/deploy/help-icon-title.png"));
	
	private boolean helpTabPresent = false;
	
	/**
	 * Summary Tab
	 */
	public Tab summaryTab;
	
	/**
	 * Virtual Machine Tab
	 */
	public Tab vmTab;
	
	/**
	 * Last Deployment Tab
	 */
	public Tab deploymentTab;
	
	/**
	 * System Log Tab
	 */
	public Tab systemLogTab;
	
	/**
	 * Help Tab
	 */
	public Tab helpTab;
	
	/**
	 * Makes the TabbedPane
	 * @param summaryTab the summary tab object
	 * @param vmTab the vm tab object
	 * @param deploymentTab the last deployment tab object
	 * @param systemLogTab the system log tab object
	 * @param helpTab the help tab object
	 */
	public TabbedPane(Tab summaryTab, Tab vmTab, Tab deploymentTab,
			Tab systemLogTab, Tab helpTab)
	{
		super();
		setOpaque(false);
		
		this.summaryTab = summaryTab;
		this.vmTab = vmTab;
		this.deploymentTab = deploymentTab;
		this.systemLogTab = systemLogTab;
		this.helpTab = helpTab;
		
		//TabbedPane properties and elements
		setTabPlacement(TOP);
		insertTab("Summary", summary, summaryTab, "Summary", 0);
		insertTab("Virtual Machines", vm, vmTab, "Virtual Machines", 1);
		insertTab("Last Deployment", deploy, deploymentTab, "Last Deployment", 2);
		insertTab("System Log", log, systemLogTab, "System Log", 3);		
	}
	
	/**
	 * Removes the Help Tab
	 */
	public void removeHelpTab()
	{
		if (this.getTabCount() > 4)
		{
			this.removeTabAt(4);
			helpTabPresent = false;
		}
	}
	
	/**
	 * Adds the Help Tab
	 */
	public void addHelpTab()
	{
		insertTab("Help", help, helpTab, "Help", 4);
		helpTabPresent = true;
		setSelectedIndex(4);
	}
	
	/**
	 * Gets whether or not the Help Tab is shown
	 * @return true if the help tab is shown
	 */
	public boolean hasHelpTab()
	{
		return helpTabPresent;
	}
}