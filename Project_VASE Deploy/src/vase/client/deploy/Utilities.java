/**
 * Project_VASE Deploy package
 */
package vase.client.deploy;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Insets;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JRadioButton;
import javax.swing.JTextArea;
import javax.swing.JTextField;

/**
 * Contains static methods to create GUI components
 * @author James McNatt & Brenton Kapral
 * @version Project_VASE Deploy
 */
public class Utilities implements GuiConstraints
{
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
