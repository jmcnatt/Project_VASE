/**
 * Project_VASE Deploy package
 */
package vase.client.deploy;

import java.awt.LayoutManager;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.SpringLayout;

/**
 * Tab Superclass for the TabbedPane layout
 * @author James McNatt & Brenton Kapral
 * @see TabbedPane
 */
public class Tab extends JPanel implements GuiConstraints, ProjectConstraints
{
	private static final long serialVersionUID = -8743617204701604712L;
	private ImageIcon templateIcon = new ImageIcon(getClass().getResource("/images/template-icon.png"));
	private ImageIcon vmIcon = new ImageIcon(getClass().getResource("/images/vm-icon.png"));
	private ImageIcon folderIcon = new ImageIcon(getClass().getResource("/images/folder-icon.png"));
	private ImageIcon logIcon = new ImageIcon(getClass().getResource("/images/system-icon.png"));
	private ImageIcon deployIcon = new ImageIcon(getClass().getResource("/images/deployment-icon.png"));
	private ImageIcon helpIcon = new ImageIcon(getClass().getResource("/images/help-icon.png"));
	
	/**
	 * Template Icon
	 */
	public static final int TEMPLATE_ICON = 1;
	
	/**
	 * Virtual Machine Icon
	 */
	public static final int VM_ICON = 2;
	
	/**
	 * Folder Icon
	 */
	public static final int FOLDER_ICON = 3;
	
	/**
	 * Last Deployment Icon
	 */
	public static final int DEPLOY_ICON = 4;
	
	/**
	 * System Log Icon
	 */
	public static final int LOG_ICON = 5;
	
	/**
	 * Help Icon
	 */
	public static final int HELP_ICON = 6;
	
	/**
	 * Main constructor
	 * @param manager the layout manager
	 */
	public Tab(LayoutManager manager)
	{
		super(manager);
		setOpaque(true);
		setBackground(COLOR_HEADER_BACKGROUND);
	}
	
	/**
	 * Creates a pre-formatted JLabel for the GuiMain class
	 * <br />
	 * <strong>Used for: </strong>Summary tab on the GuiMain
	 * @param text the caption for the JLabel
	 * @return the pre-formatted JLabel
	 */
	public JLabel createMainStatusLabel(String text, int icon)
	{
		JLabel label = new JLabel(text);
		
		switch (icon)
		{
			case 1:
			{
				label.setIcon(templateIcon);
				break;
			}
			
			case 2:
			{
				label.setIcon(vmIcon);
				break;
			}
			
			case 3:
			{
				label.setIcon(folderIcon);
				break;
			}
			
			case 4:
			{
				label.setIcon(deployIcon);
				break;
			}
			
			case 5:
			{
				label.setIcon(logIcon);
				break;
			}
			
			case 6:
			{
				label.setIcon(helpIcon);
				break;
			}
		}
		
		
		label.setIconTextGap(10);
		label.setHorizontalTextPosition(JLabel.RIGHT);
		label.setBackground(COLOR_HEADER_BACKGROUND);
		label.setForeground(COLOR_HEADER_FOREGROUND);
		label.setFont(FONT_LABEL_HEADER);
		label.setPreferredSize(DIM_MAIN_CONTENT_SUMMARY_LABEL);
		label.setBorder(BorderFactory.createLineBorder(COLOR_HEADER_BACKGROUND, 5));
		label.setOpaque(true);
			
		return label;
	}
	
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
	public JPanel createMainVMPanel(JTable table)
	{
		JScrollPane scrollPane = new JScrollPane(table);
		JPanel panel = new JPanel();
		SpringLayout layout = new SpringLayout();
		panel.setLayout(layout);
		panel.add(scrollPane);
		
		layout.putConstraint(SpringLayout.NORTH, scrollPane, 0, SpringLayout.NORTH, panel);
		layout.putConstraint(SpringLayout.WEST, scrollPane, 0, SpringLayout.WEST, panel);
		layout.putConstraint(SpringLayout.EAST, scrollPane, 0, SpringLayout.EAST, panel);
		layout.putConstraint(SpringLayout.SOUTH, scrollPane, 0, SpringLayout.SOUTH, panel);
		
		return panel;		
	}
}
