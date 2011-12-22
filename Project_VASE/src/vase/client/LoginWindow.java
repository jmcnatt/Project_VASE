/**
 * Project_VASE Client package
 */
package vase.client;

import java.awt.BorderLayout;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SpringLayout;

/**
 * Login Splash superclass
 * Provides framework for creating a Login Splash to connect to vCenter and start the software
 * @author James McNatt & Brenton Kapral
 * @version Project_VASE
 */
public class LoginWindow extends Window
{
	private static final long serialVersionUID = 846718139335973947L;
	
	/**
	 * Login button
	 */
	public JButton jbLogin;
	
	/**
	 * Cancel button
	 */
	public JButton jbCancel;
	
	/**
	 * Help button
	 */
	public JButton jbHelp;
	
	/**
	 * Server text field
	 */
	public JTextField jtfServer;
	
	/**
	 * Username text field
	 */
	public JTextField jtfUsername;
	
	/**
	 * Password field
	 */
	public JPasswordField jtfPassword;
	
	/**
	 * Information text area
	 */
	public JTextArea jtaInfo;
	
	/**
	 * Identifier for Project_VASE Connect splash image
	 */
	public static final int CONNECT_SPLASH = 1;
	
	/**
	 * Identifier for Project_VASE Deploy splash image
	 */
	public static final int DEPLOY_SPLASH = 2;
	
	/**
	 * Main Constructor
	 */
	public LoginWindow(String title, String lastServer, String lastUser, int splash)
	{
		super(title);
		setBackground(COLOR_MAIN_BACKGROUND);
		setSize(DIM_MAIN_LOGIN_SPLASH);
		getContentPane().setMinimumSize(DIM_MAIN_LOGIN_SPLASH);
		getContentPane().setPreferredSize(DIM_MAIN_LOGIN_SPLASH);
		getContentPane().setMaximumSize(DIM_MAIN_LOGIN_SPLASH);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setResizable(false);
		
		makeItems(lastServer, lastUser);
		makePanels(splash);
		setMnemonics();
		
		pack();
		setVisible(true);
		setFocus();
	}
	
	/**
	 * Makes the attributes for this class
	 */
	private void makeItems(String lastServer, String lastUser)
	{
		jbLogin = Utilities.createMainButton("Login");
		jbCancel = Utilities.createMainButton("Cancel");
		jbHelp = Utilities.createMainButton("Help");
		jtfServer = Utilities.createMainTextField(lastServer);
		jtfUsername = Utilities.createMainTextField(lastUser);
		jtfPassword = Utilities.createMainPasswordField();
		jtaInfo = Utilities.createMainTextArea();
		jtaInfo.setFocusable(false);
		jtaInfo.setCursor(null);
		jtaInfo.setOpaque(false);
		jtaInfo.setFont(FONT_DEPLOY_LABEL);
		setInfoText();
	}	
	
	/**
	 * Makes the panels and sets the appropriate constraints
	 */
	private void makePanels(int splash)
	{
		Panel jpNorth = new Panel(new SpringLayout(), false, DIM_MAIN_LOGIN_NORTH);
		JLabel splashLabel = null;
		
		switch (splash)
		{
			case CONNECT_SPLASH:
			{
				splashLabel = new JLabel(new ImageIcon(getClass().getResource("/images/connect/splash.png")));
				break;
			}
			
			case DEPLOY_SPLASH:
			{
				splashLabel = new JLabel(new ImageIcon(getClass().getResource("/images/deploy/splash.png")));
				break;
			}
		}
			
		jpNorth.add(splashLabel);
		
		SpringLayout northLayout = (SpringLayout) jpNorth.getLayout();
		northLayout.putConstraint(SpringLayout.NORTH, splashLabel, 0, SpringLayout.NORTH, jpNorth);
		northLayout.putConstraint(SpringLayout.SOUTH, splashLabel, 0, SpringLayout.SOUTH, jpNorth);
		northLayout.putConstraint(SpringLayout.WEST, splashLabel, 0, SpringLayout.WEST, jpNorth);
		northLayout.putConstraint(SpringLayout.EAST, splashLabel, 0, SpringLayout.EAST, jpNorth);
		
		Panel jpSouth = new Panel(new SpringLayout(), false, DIM_MAIN_LOGIN_SOUTH);
		JLabel jlServer = Utilities.createMainLabel("Server Address: ");
		JLabel jlUsername = Utilities.createMainLabel("Username: ");
		JLabel jlPassword = Utilities.createMainLabel("Password: ");
		jpSouth.add(jlServer);
		jpSouth.add(jlUsername);
		jpSouth.add(jlPassword);
		jpSouth.add(jtfServer);
		jpSouth.add(jtfUsername);
		jpSouth.add(jtfPassword);
		jpSouth.add(jbLogin);
		jpSouth.add(jbCancel);
		jpSouth.add(jbHelp);
		jpSouth.add(jtaInfo);
		
		SpringLayout southLayout = (SpringLayout) jpSouth.getLayout();
		southLayout.putConstraint(SpringLayout.NORTH, jtaInfo, 10, SpringLayout.NORTH, jpSouth);
		southLayout.putConstraint(SpringLayout.WEST, jtaInfo, 50, SpringLayout.WEST, jpSouth);
		southLayout.putConstraint(SpringLayout.EAST, jtaInfo, -50, SpringLayout.EAST, jpSouth);
		southLayout.putConstraint(SpringLayout.VERTICAL_CENTER, jlServer, 20, SpringLayout.SOUTH, jtaInfo);
		southLayout.putConstraint(SpringLayout.EAST, jlServer, -100, SpringLayout.HORIZONTAL_CENTER, jpSouth);
		southLayout.putConstraint(SpringLayout.VERTICAL_CENTER, jlUsername, 30, SpringLayout.VERTICAL_CENTER, jlServer);
		southLayout.putConstraint(SpringLayout.EAST, jlUsername, -100, SpringLayout.HORIZONTAL_CENTER, jpSouth);
		southLayout.putConstraint(SpringLayout.VERTICAL_CENTER, jlPassword, 30, SpringLayout.VERTICAL_CENTER, jlUsername);
		southLayout.putConstraint(SpringLayout.EAST, jlPassword, -100, SpringLayout.HORIZONTAL_CENTER, jpSouth);
		
		southLayout.putConstraint(SpringLayout.VERTICAL_CENTER, jtfServer, 20, SpringLayout.SOUTH, jtaInfo);
		southLayout.putConstraint(SpringLayout.WEST, jtfServer, -80, SpringLayout.HORIZONTAL_CENTER, jpSouth);
		southLayout.putConstraint(SpringLayout.VERTICAL_CENTER, jtfUsername, 30, SpringLayout.VERTICAL_CENTER, jtfServer);
		southLayout.putConstraint(SpringLayout.WEST, jtfUsername, -80, SpringLayout.HORIZONTAL_CENTER, jpSouth);
		southLayout.putConstraint(SpringLayout.VERTICAL_CENTER, jtfPassword, 30, SpringLayout.VERTICAL_CENTER, jtfUsername);
		southLayout.putConstraint(SpringLayout.WEST, jtfPassword, -80, SpringLayout.HORIZONTAL_CENTER, jpSouth);
		southLayout.putConstraint(SpringLayout.VERTICAL_CENTER, jbLogin, -20, SpringLayout.SOUTH, jpSouth);
		southLayout.putConstraint(SpringLayout.EAST, jbLogin, -15, SpringLayout.WEST, jbCancel);
		southLayout.putConstraint(SpringLayout.VERTICAL_CENTER, jbCancel, -20, SpringLayout.SOUTH, jpSouth);
		southLayout.putConstraint(SpringLayout.HORIZONTAL_CENTER, jbCancel, 0, SpringLayout.HORIZONTAL_CENTER, jpSouth);
		southLayout.putConstraint(SpringLayout.VERTICAL_CENTER, jbHelp, -20, SpringLayout.SOUTH, jpSouth);
		southLayout.putConstraint(SpringLayout.WEST, jbHelp, 15, SpringLayout.EAST, jbCancel);
		
		getContentPane().setLayout(new BorderLayout());
		getContentPane().add(jpNorth, BorderLayout.NORTH);
		getContentPane().add(jpSouth, BorderLayout.SOUTH);
	}
	
	/**
	 * Sets the information text area visible text
	 */
	private void setInfoText()
	{
		String text = "Enter the IP Address or Hostname of the VMware vCenter Server hosting Project_VASE. " +
					  "Use the username or password of the local account residing on the server to manage, " +
					  "deploy and control Virtual Machines.";
		jtaInfo.setText(text);
	}
	
	/**
	 * Sets the default focus when loaded
	 */
	private void setFocus()
	{
		if (jtfServer.getText().equals("") || jtfServer.getText() == null)
		{
			jtfServer.requestFocusInWindow();
		}
		
		else if (jtfUsername.getText().equals("") || jtfUsername.getText() == null)
		{
			jtfUsername.requestFocusInWindow();
		}
		
		else
		{
			jtfPassword.requestFocusInWindow();
		}
	}
	
	/**
	 * Sets the mnemonic values for the buttons
	 */
	private void setMnemonics()
	{
		jbLogin.setMnemonic('L');
		jbCancel.setMnemonic('C');
		jbHelp.setMnemonic('H');
	}
	
	/**
	 * Processes the password for the ServiceInstance
	 * @param password the password from the PasswordField
	 * @return the password for use by the ServiceInstance
	 */
	public String processPassword(char[] password)
	{
		StringBuilder builder = new StringBuilder();
		for (Character each : password)
		{
			builder.append(each);
		}
		
		return builder.toString();
	}
	
	/**
	 * Enables all fields in the Window
	 */
	public void enableFields()
	{
		jbLogin.setEnabled(true);
		jbCancel.setEnabled(true);
		jbHelp.setEnabled(true);
		jtfServer.setEnabled(true);
		jtfUsername.setEnabled(true);
		jtfPassword.setEnabled(true);
	}
	
	/**
	 * Disables all fields in the Window
	 */
	public void disableFields()
	{
		jbLogin.setEnabled(false);
		jbCancel.setEnabled(false);
		jbHelp.setEnabled(false);
		jtfServer.setEnabled(false);
		jtfUsername.setEnabled(false);
		jtfPassword.setEnabled(false);
	}
	
	/**
	 * Displays an error message
	 * @param title the title of the dialog
	 * @param message the message in the error dialog
	 */
	public void showErrorMessage(String title, String message)
	{
		JOptionPane.showMessageDialog(this, message, title, JOptionPane.ERROR_MESSAGE);
		enableFields();
	}
}
