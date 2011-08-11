/**
 * Project_VASE Deploy package
 */
package vase.client.deploy;

import java.awt.Color;
import java.awt.Insets;
import java.awt.image.BufferedImage;

import javax.imageio.ImageIO;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPasswordField;
import javax.swing.JRadioButton;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.UIManager;

/**
 * GUI Window superclass
 * <br />
 * Contains methods for creating formatted Swing components and sets the UIManager
 * values for various components
 * @author James McNatt & Brenton Kapral
 * @version Project_VASE Deploy
 */

public class GuiWindow extends JFrame implements ProjectConstraints, GuiConstraints
{
	private static final long serialVersionUID = 2918402213330301127L;

	/**
	 * Default constructor calling the superclass JFrame's constructor
	 * <br />
	 * Sets the Program Icon and the UIManager selection
	 * @param title the title of the window
	 */
	public GuiWindow(String title)
	{
		super(title);
		BufferedImage image = null;
		try
		{
			image = ImageIO.read(getClass().getResource("/images/icon.png"));
			setIconImage(image);
			UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
		}
		
		catch (Exception e)
		{
			LOG.write("Unable to load WindowsUI\n" + e.getStackTrace(), true);
		}
	}
	
	/**
	 * Creates a Label to be used in GuiMain
	 * @param text the text to use in the label
	 * @return the formatted label
	 */
	public JLabel createMainLabel(String text)
	{
		JLabel label = new JLabel(text);
		label.setOpaque(false);
		
		return label;
	}
	
	/**
	 * Creates an error label to be used in the LoginSplash
	 * @return the formatted label for the login splash
	 */
	public JLabel createMainErrorLabel()
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
	public JTextField createMainTextField(String text)
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
	public JPasswordField createMainPasswordField()
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
	public JButton createMainButton(String text)
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
	public JTextArea createMainTextArea()
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
	 * Creates a JButton used in the Deployment Wizard
	 * @param text the caption on the button
	 * @return button a formatted JButton for the Deployment Wizard
	 */
	public JButton createDeployMainButton(String text)
	{
		JButton button = new JButton(text);
		button.setEnabled(false);
		button.setPreferredSize(DIM_DEPLOY_BUTTON);
		button.setMaximumSize(DIM_DEPLOY_BUTTON);
		button.setMinimumSize(DIM_DEPLOY_BUTTON);
		
		return button;
	}
	
	/**
	 * Create a label for the title of the Deployment Wizard
	 * @param text the text to use in the label
	 * @return the formatted label
	 */
	public JLabel createDeployTitleLabel(String text)
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
	public JLabel createDeploySubLabel(String text)
	{
		JLabel label = new JLabel(text);
		label.setOpaque(false);
		label.setFont(FONT_DEPLOY_SUBLABEL);
		
		return label;
	}
	
	/**
	 * Creates a standard label for a JComponent in the Deployment Wizard
	 * @param text the text to use in the label
	 * @return the formatted label
	 */
	public JLabel createDeployComponentLabel(String text)
	{
		JLabel label = new JLabel(text);
		label.setOpaque(false);
		label.setFont(FONT_DEPLOY_COMPONENT_LABEL);
		//label.setForeground(Color.BLACK);
		
		return label;
	}
	
	/**
	 * Creates a RadioButton for the Deployment Wizard
	 * @param group the buttongroup for the radio button
	 * @param text the label for the radio button
	 * @return the formatted radio button
	 */
	public JRadioButton createDeployRadioButton(ButtonGroup group, String text)
	{
		JRadioButton button = new JRadioButton(text);
		group.add(button);
		button.setOpaque(false);
		button.setFont(FONT_DEPLOY_COMPONENT_LABEL);
		
		return button;
	}
	
	/**
	 * Creates a TextField for the Deployment Wizard
	 * @param text the text to start the field
	 * @return the formatted text field
	 */
	public JTextField createDeployTextField(String text)
	{
		JTextField textField = new JTextField();
		textField.setFont(FONT_DEPLOY_TEXT_FIELD);
		textField.setText(text);
		textField.setColumns(15);
		
		return textField;
	}
	
	/**
	 * Creates a CheckBox for the Deployment Wizard
	 * @return the formatted check box
	 */
	public JCheckBox createDeployCheckBox()
	{
		JCheckBox checkBox = new JCheckBox();
		checkBox.setSelected(false);
		checkBox.setOpaque(false);
		
		return checkBox;
	}
	
	/**
	 * Creates a ComboBox for the Deployment Wizard
	 * @return the formatted combo box
	 */
	public JComboBox createDeployComboBox()
	{
		JComboBox box = new JComboBox();
		box.setFont(FONT_DEPLOY_SUBLABEL);
		
		return box;
	}
	
	/**
	 * Creates a ComboBox for the Deployment Wizard using an array of Objects
	 * @param items the array of objects to display in the combo box
	 * @return the formatted combo box
	 */
	public JComboBox createDeployComboBox(Object[] items)
	{
		JComboBox box = new JComboBox(items);
		box.setFont(FONT_DEPLOY_SUBLABEL);
		
		return box;
	}
	
	/**
	 * Creates a TextAra for the Deployment Wizard, used for the end report
	 * @return the formatted text area
	 */
	public JTextArea createDeployTextArea()
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
}
