/**
 * Project_VASE Connect package
 */
package vase.client.connect;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.net.MalformedURLException;
import java.net.URL;
import java.rmi.RemoteException;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPasswordField;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SpringLayout;

import vase.client.HelpWindow;
import vase.client.Panel;
import vase.client.Utilities;
import vase.client.Window;
import vase.client.connect.gui.Main;

import com.vmware.vim25.mo.ServiceInstance;

/**
 * Displays the login prompt before the GuiMain is called
 * <strong>Note: </strong>Contains the main method for the project
 * @author James McNatt & Brenton Kapral
 * @version Project_VASE Connect
 */
public class LoginSplash extends Window implements ActionListener
{
	private static final long serialVersionUID = -8409015528275680765L;
	
	private JButton jbLogin, jbCancel, jbHelp;
	private JTextField jtfServer;
	private JTextField jtfUsername;
	private JPasswordField jtfPassword;
	private JTextArea jtaInfo;
	private JLabel jlError;
	
	/**
	 * Main Constructor that builds the LoginSplash
	 */
	public LoginSplash()
	{
		super("VASE Deploy: Login");
		setBackground(COLOR_MAIN_BACKGROUND);
		setSize(DIM_MAIN_LOGIN_SPLASH);
		getContentPane().setMinimumSize(DIM_MAIN_LOGIN_SPLASH);
		getContentPane().setPreferredSize(DIM_MAIN_LOGIN_SPLASH);
		getContentPane().setMaximumSize(DIM_MAIN_LOGIN_SPLASH);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setResizable(false);
		
		makeItems();
		makePanels();
		addListeners();
		setMnemonics();
		
		pack();
		setVisible(true);
		setFocus();
	}
	
	/**
	 * Makes the attributes for this class
	 */
	private void makeItems()
	{
		jbLogin = Utilities.createMainButton("Login");
		jbCancel = Utilities.createMainButton("Cancel");
		jbHelp = Utilities.createMainButton("Help");
		jtfServer = Utilities.createMainTextField(ProjectConstraints.SETTINGS_READER.lastServer);
		jtfUsername = Utilities.createMainTextField(ProjectConstraints.SETTINGS_READER.lastUser);
		jtfPassword = Utilities.createMainPasswordField();
		jlError = Utilities.createMainErrorLabel();
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
	private void makePanels()
	{
		Panel jpNorth = new Panel(new SpringLayout(), false, DIM_MAIN_LOGIN_NORTH);
		JLabel splashLabel = new JLabel(new ImageIcon(getClass().getResource("/images/connect/splash.png")));
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
		jpSouth.add(jlError);
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
		southLayout.putConstraint(SpringLayout.HORIZONTAL_CENTER, jlError, 0, SpringLayout.HORIZONTAL_CENTER, jpSouth);
		southLayout.putConstraint(SpringLayout.VERTICAL_CENTER, jlError, 20, SpringLayout.VERTICAL_CENTER, jtfPassword);
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
		String text = "Enter the IP Address or Hostname of the VMware vCenter Server hosting Project_VASE.  " +
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
	 * Adds the listeners for the button and the window
	 */
	private void addListeners()
	{
		jbLogin.addActionListener(this);
		jbCancel.addActionListener(this);
		jbHelp.addActionListener(this);
		
		class LoginKeyAdapter extends KeyAdapter
		{
			public void keyPressed(KeyEvent event)
			{
				int key = event.getKeyCode();
				if (key == KeyEvent.VK_ENTER)
				{
					login();
				}
			}
		}
		
		jbLogin.addKeyListener(new LoginKeyAdapter());
		jtfPassword.addKeyListener(new LoginKeyAdapter());
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
	private String processPassword(char[] password)
	{
		StringBuilder builder = new StringBuilder();
		for (Character each : password)
		{
			builder.append(each);
		}
		
		return builder.toString();
	}
	
	/**
	 * Starts the login process
	 */
	private void login()
	{
		if (!(jtfServer.getText().equals("") || jtfUsername.getText().equals("") ||
				jtfPassword.getPassword().length == 0))
		{
			try
			{
				String address = null;
				String url = null;
				
				if (jtfServer.getText().startsWith("https://"))
				{
					url = jtfServer.getText();
					address = ((url.split("//")[1]).split("/")[0]);
				}
				
				else
				{
					url = "https://" + jtfServer.getText() + "/sdk";
					address = jtfServer.getText();
				}
				
				String password = processPassword(jtfPassword.getPassword());
				ServiceInstance si = 
					new ServiceInstance(new URL(url), jtfUsername.getText(), password, true);
				
				//Reset Error Field
				jlError.setText("");
				
				//Build the GuiMain
				Main main = new Main(si);
				
				//Set CommandEngine credentials for Scripts
				main.engine.setCurrentServer(address);
				main.engine.setCurrentUsername(jtfUsername.getText());
				
				ProjectConstraints.LOG.write("Logged in as " + main.engine.getCurrentUsername() + " to " + 
						main.engine.getCurrentServer());
				
				//Dispose the window
				this.dispose();
			}
			
			catch (MalformedURLException e)
			{
				jlError.setText("Could not login to the vCenter server.  Check the address of the server");
				jtfServer.setText("");
				jtfPassword.setText("");
				jtfUsername.setText("");
				jtfServer.requestFocusInWindow();
			}
			
			catch (RemoteException e)
			{
				jlError.setText("Could not login to the vCenter server");
				jtfPassword.setText("");
				jtfUsername.setText("");
				jtfUsername.requestFocusInWindow();
			}
			
			catch (Exception e)
			{
				jlError.setText("Error processing login");
				e.printStackTrace();
				jtfServer.setText("");
				jtfPassword.setText("");
				jtfUsername.setText("");
				jtfServer.requestFocusInWindow();
			}
		}
		
		else
		{
			jlError.setText("Please enter the server address, username, and password");
		}
	}
	
	/**
	 * ActionPerformed to initiate login.  Creates service instance and launches
	 * GuiMain if successful
	 * @param event the action event, fired by the login button
	 */
	public void actionPerformed(ActionEvent event)
	{
		if (event.getActionCommand().equals("Login"))
		{
			login();				
		}
		
		else if (event.getActionCommand().equals("Cancel"))
		{
			ProjectConstraints.SETTINGS_READER.save();
			System.exit(0);
		}
		
		else if (event.getActionCommand().equals("Help"))
		{
			new HelpWindow("Project_VASE Connect Help", "/html/connect/getting_started.html", ProjectConstraints.LOG);
		}
	}
}
