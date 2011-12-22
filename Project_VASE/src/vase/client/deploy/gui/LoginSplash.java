/**
 * Project_VASE Connect GUI package
 */
package vase.client.deploy.gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.net.MalformedURLException;
import java.net.URL;
import java.rmi.RemoteException;

import vase.client.HelpWindow;
import vase.client.LoginWindow;
import vase.client.deploy.ProjectConstraints;

import com.vmware.vim25.mo.ServiceInstance;

/**
 * Displays the login prompt before the GuiMain is called
 * <strong>Note: </strong>Contains the main method for the project
 * @author James McNatt & Brenton Kapral
 * @version Project_VASE Connect
 */
public class LoginSplash extends LoginWindow implements ActionListener
{
	private static final long serialVersionUID = 4923447780512338230L;

	/**
	 * Main Constructor that builds the LoginSplash
	 */
	public LoginSplash()
	{
		super("VASE Deploy: Login", ProjectConstraints.SETTINGS_READER.lastServer, ProjectConstraints.SETTINGS_READER.lastUser, DEPLOY_SPLASH);
		addListeners();
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
	 * Starts the login process
	 */
	private void login()
	{
		disableFields();
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
				showErrorMessage("Login Error", "Could not login to the vCenter server. Check the address of the server");
				jtfServer.setText("");
				jtfPassword.setText("");
				jtfUsername.setText("");
				jtfServer.requestFocusInWindow();
			}
			
			catch (RemoteException e)
			{
				showErrorMessage("Login Error", "Could not login to the vCenter server");
				jtfPassword.setText("");
				jtfUsername.setText("");
				jtfUsername.requestFocusInWindow();
			}
			
			catch (Exception e)
			{
				showErrorMessage("Login Error", "Error processing login");
				ProjectConstraints.LOG.printStackTrace(e);
				jtfServer.setText("");
				jtfPassword.setText("");
				jtfUsername.setText("");
				jtfServer.requestFocusInWindow();
			}
		}
		
		else
		{
			showErrorMessage("Login Error", "Please enter the vCenter server and credentials");
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
