/**
 * Project_VASE Connect package
 */
package vase.client.connect;

import java.awt.Desktop;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.URL;

import javax.swing.JCheckBoxMenuItem;
import javax.swing.JOptionPane;

import com.vmware.vim25.mo.VirtualMachine;

import vase.client.HelpWindow;

/**
 * Main action listener for GuiMain
 * <br />
 * Handles the menu bar's action events
 * @author James McNatt & Brenton Kapral
 * @version Project_VASE Deploy
 * @see GuiMain
 * @see ActionListener
 */
public class GuiActionListener implements ActionListener, ProjectConstraints
{
	private GuiMain main;
	
	/**
	 * Main Constructor
	 * @param GuiMain main
	 */
	public GuiActionListener(GuiMain main)
	{
		this.main = main;
	}
	
	/**
	 * Action Performed
	 * Sends button and menu commands to the CommandEngine
	 * @param event the action event
	 */
	public void actionPerformed(ActionEvent event)
	{
		String command = event.getActionCommand();
		
		if (event.getSource() instanceof JCheckBoxMenuItem)
		{
			JCheckBoxMenuItem item = (JCheckBoxMenuItem) event.getSource();
			
			if (item.getText().equals("Launch Console in Full Screen"))
			{
				ProjectConstraints.SETTINGS_READER.setFullScreen(item.isSelected());
			}
			
			else if (item.getText().equals("Save to Log File"))
			{
				LOG.setLogFileEnabled(item.isSelected());
			}
		}
		
		else if (command.equals("Save Settings"))
		{
			SETTINGS_READER.save();
			JOptionPane.showMessageDialog(main, "Settings Saved Successfully", "Save Successful", JOptionPane.INFORMATION_MESSAGE);
		}
		
		else if (command.equals("Logoff"))
		{
			main.engine.logoff();
		}
		
		else if (command.equals("Exit"))
		{
			main.engine.quit();
		}
		
		else if (command.equals("Refresh VM List"))
		{
			main.startRefreshThread();
		}
		
		else if (command.equals("Clear Log File Contents"))
		{
			int choice = JOptionPane.showConfirmDialog(main, "Are you sure you want to clear the log file's contents?", 
					"Confirm Clear Log File Contents", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
			if (choice == JOptionPane.YES_OPTION)
			{
				LOG.clear();
				JOptionPane.showMessageDialog(main, "Log file cleared successfully", "Log File Cleared", JOptionPane.INFORMATION_MESSAGE);
			}
		}
		
		else if (command.equals("Project_VASE on Sourceforge"))
		{
			//Launch Default Browser
			
			if (!Desktop.isDesktopSupported())
			{
				LOG.write("Desktop system is not supported, cannot launch external link");
				JOptionPane.showMessageDialog(main, "Cannot launch external link", "Error", JOptionPane.ERROR_MESSAGE);
			}
			
			else
			{
				Desktop desktop = Desktop.getDesktop();
				
				if (!desktop.isSupported(Desktop.Action.BROWSE))
				{
					LOG.write("Desktop doesn't support the BROWSE Action");
					JOptionPane.showMessageDialog(main, "Cannot launch external link, browse action not supported", "Error", JOptionPane.ERROR_MESSAGE);
				}
				
				else
				{
					try
					{
						desktop.browse(new URL(SOURCEFORGE_WEB).toURI());
					}
					
					catch (Exception e)
					{
						e.printStackTrace();
					}
				}
			}
		}
		
		else if (command.equals("About VASE Connect"))
		{
			new AboutDialog(main);
		}
		
		else if (command.equals("Show Help Screen"))
		{
			new HelpWindow("Project_VASE Connect Help", "/html/connect/getting_started.html", ProjectConstraints.LOG);
		}
		
		//Require a list selection
		else
		{			
			if (main.jListVMs.getSelectedIndex() != -1)
			{
				Object[] data = (Object[]) main.jListVMs.getSelectedValue();
				VirtualMachine vm = (VirtualMachine) data[3];
				
				if (command.equals("Launch Console"))
				{
					main.engine.launchConsole(vm);
				}
				
				else if (command.equals("Power On"))
				{
					main.engine.powerOn(vm);
				}
				
				else if (command.equals("Power Off"))
				{
					main.engine.powerOff(vm);
				}
				
				else if (command.equals("Suspend"))
				{
					main.engine.suspend(vm);
				}
				
				else if (command.equals("Shutdown"))
				{
					main.engine.shutdown(vm);
				}
				
				else if (command.equals("Reset"))
				{
					main.engine.reset(vm);
				}
				
				else if (command.equals("Restart"))
				{
					main.engine.restart(vm);
				}
			}
			
			else
			{
				JOptionPane.showMessageDialog(main, "Please select a Virtual Machine from the list", "Error", JOptionPane.ERROR_MESSAGE);
			}
		}
	}
}