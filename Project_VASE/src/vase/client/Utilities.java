/**
 * Project_VASE Deploy package
 */
package vase.client;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Insets;

import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPasswordField;
import javax.swing.JRadioButton;
import javax.swing.JTextArea;
import javax.swing.JTextField;

/**
 * Contains static methods to create GUI components
 * @author James McNatt & Brenton Kapral
 * @version Project_VASE
 */
public class Utilities implements InterfaceConstraints
{
	private static ImageIcon templateIcon = new ImageIcon(Utilities.class.getResource("/images/template-icon.png"));
	private static ImageIcon vmIcon = new ImageIcon(Utilities.class.getResource("/images/vm-icon.png"));
	private static ImageIcon folderIcon = new ImageIcon(Utilities.class.getResource("/images/folder-icon.png"));
	private static ImageIcon logIcon = new ImageIcon(Utilities.class.getResource("/images/system-icon.png"));
	private static ImageIcon deployIcon = new ImageIcon(Utilities.class.getResource("/images/deployment-icon.png"));
	private static ImageIcon helpIcon = new ImageIcon(Utilities.class.getResource("/images/help-icon.png"));
	
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
	 * Create a title label with blue background and icon
	 * @param text the text to use in the label
	 * @param icon the image icon to use
	 * @return the formatted label
	 */
	public static JLabel createTitleLabel(String text, int icon)
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
	
	/**
	 * Creates a Label to be used in GuiMain
	 * @param text the text to use in the label
	 * @return the formatted label
	 */
	public static JLabel createMainLabel(String text)
	{
		JLabel label = new JLabel(text);
		label.setOpaque(false);
		
		return label;
	}
	
	/**
	 * Creates an error label to be used in the LoginSplash
	 * @return the formatted label for the login splash
	 */
	public static JLabel createMainErrorLabel()
	{
		JLabel label = new JLabel();
		label.setOpaque(false);
		label.setForeground(COLOR_ERROR_LABEL);
		
		return label;
	}
	
	/**
	 * Creates a TextField for use in the GuiMain
	 * @param text the text to use in the field
	 * @return the formatted text field
	 */
	public static JTextField createMainTextField(String text)
	{
		JTextField jtf = new JTextField(text);
		jtf.setColumns(20);
		jtf.setFont(FONT_DEPLOY_TEXT_FIELD);
		
		return jtf;
	}
	
	/**
	 * Creates a PasswordField for use in the GuiMain
	 * @return the formatted password field
	 */
	public static JPasswordField createMainPasswordField()
	{
		JPasswordField jtf = new JPasswordField();
		jtf.setColumns(20);
		jtf.setFont(FONT_DEPLOY_TEXT_FIELD);
		
		return jtf;
	}
	
	/**
	 * Creates button to be used in the GuiMain
	 * @param text the text to display on the button
	 * @return the formatted button
	 */
	public static JButton createMainButton(String text)
	{
		JButton button = new JButton(text);
		button.setPreferredSize(DIM_DEPLOY_BUTTON);
		button.setMaximumSize(DIM_DEPLOY_BUTTON);
		button.setMinimumSize(DIM_DEPLOY_BUTTON);
		
		return button;
	}
	
	/**
	 * Creates a TextArea for use in the GuiMain
	 * @return the formatted text area
	 */
	public static JTextArea createMainTextArea()
	{
		JTextArea jta = new JTextArea();
		jta.setEditable(false);
		jta.setWrapStyleWord(true);
		jta.setLineWrap(true);
		jta.setFont(FONT_MAIN_LOG);
		jta.setBackground(Color.WHITE);
		
		return jta;
	}
	
	/**
	 * Create a title label at 18pt font
	 * @param text the text to use in the label
	 * @return the formatted label
	 */
	public static JLabel createTitleLabel(String text)
	{
		JLabel label = new JLabel();
		label.setText(text);
		label.setOpaque(false);
		label.setFont(FONT_DEPLOY_TITLE);
		
		return label;
	}
	
	/**
	 * Creates a free-standing label for the Deployment Wizard
	 * @param text the text to use in the label
	 * @return the formatted label
	 */
	public static JLabel createDeployLabel(String text)
	{
		JLabel label = new JLabel(text);
		label.setOpaque(false);
		label.setFont(FONT_DEPLOY_LABEL);
		
		return label;
	}
	
	/**
	 * Creates a label for another Gui component for the Deployment Wizard
	 * @param text the text to use in the label
	 * @return the formatted label
	 */
	public static JLabel createDeployComponentLabel(String text)
	{
		JLabel label = new JLabel(text);
		label.setOpaque(false);
		label.setFont(FONT_DEPLOY_COMPONENT_LABEL);
		
		return label;
	}
	
	/**
	 * Creates a combo box for use in the DeployWizard
	 * @param items the items to include in the combo box
	 * @param selectedIndex the starting selection index
	 * @param preferredSize the preferred size
	 * @return the formatted combo box
	 */
	public static JComboBox createDeployComboBox(Object[] items, int selectedIndex, Dimension preferredSize)
	{
		JComboBox box = new JComboBox();
		
		if (items != null)
		{
			for (Object object : items)
			{
				box.addItem(object);
			}
			
			box.setSelectedIndex(selectedIndex);
		}
		
		box.setPreferredSize(preferredSize);
		
		return box;
	}
	
	/**
	 * Creates a check box for use in the DeployWizard
	 * @param isChecked whether or not the check box is checked
	 * @return the formatted check box
	 */
	public static JCheckBox createDeployCheckBox(boolean isChecked)
	{
		JCheckBox box = new JCheckBox();
		box.setSelected(isChecked);
		box.setOpaque(false);
		
		return box;
	}
	
	/**
	 * Creates a text field for use in the DeployWizard
	 * @param text the text to show in the field
	 * @return the formatted text field
	 */
	public static JTextField createDeployTextField(String text)
	{
		JTextField textField = new JTextField();
		textField.setFont(FONT_DEPLOY_TEXT_FIELD);
		textField.setText(text);
		textField.setColumns(15);
		
		return textField;
	}
	
	/**
	 * Creates a Radio Button for use in the DeployWizard
	 * @param group the button group this button is associated with
	 * @param text the button's label
	 * @param isSelected whether or not this button is selected
	 * @return the formatted radio button
	 */
	public static JRadioButton createDeployRadioButton(ButtonGroup group, String text, boolean isSelected)
	{
		JRadioButton button = new JRadioButton(text);
		group.add(button);
		button.setOpaque(false);
		button.setSelected(isSelected);
		button.setFont(FONT_DEPLOY_COMPONENT_LABEL);
		
		return button;
	}
	
	/**
	 * Creates a Text Area for use in the DeployWizard
	 * @return the formatted text area
	 */
	public static JTextArea createDeployTextArea()
	{
		JTextArea jta = new JTextArea();
		jta.setFont(FONT_MAIN_LOG);
		jta.setBackground(Color.WHITE);
		jta.setEditable(true);
		jta.setWrapStyleWord(false);
		jta.setLineWrap(true);
		jta.setMargin(new Insets(20,20,20,20)); 
		
		return jta;
	}
	
	/**
	 * Creates a standard button for the login splash and the DeployWizard
	 * @param text the text to display on the button
	 * @param isEnabled whether or not the button is enabled
	 */
	public static JButton createButton(String text, boolean isEnabled)
	{
		JButton button = new JButton(text);
		button.setPreferredSize(DIM_DEPLOY_BUTTON);
		button.setEnabled(isEnabled);
		
		return button;
	}
}
