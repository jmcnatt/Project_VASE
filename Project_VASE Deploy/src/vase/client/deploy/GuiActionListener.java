/**
 * Project_VASE Deploy package
 */
package vase.client.deploy;

import java.awt.Desktop;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.URL;

import javax.swing.JCheckBoxMenuItem;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;

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
	 * Error message code for the VM List
	 * @see GuiActionListener#actionPerformed(ActionEvent)
	 */
	private static final int VM_LIST = 1;
	
	/**
	 * Error message code for the Template List
	 * @see GuiActionListener#actionPerformed(ActionEvent)
	 */
	private static final int TEMPLATE_LIST = 2;
	
	/**
	 * Main constructor
	 * <br />
	 * Mapped to the GuiMain and uses public variables for references in action events
	 * @param main the GuiMain window instance
	 */
	public GuiActionListener(GuiMain main)
	{
		this.main = main;
	}
	
	/**
	 * Checks to see if the given list has a selection
	 * <br />
	 * Used in the VM and Template menus during actionPerformed
	 * @param list the list to check
	 * @return true if the list has a selection
	 */
	private boolean isListSelected(List list)
	{
		boolean selected = false;
		if (list.getSelectedIndex() != -1) selected = true;
		return selected;
	}
	
	/**
	 * Displays an error dialog when there is no selection on either list
	 * @param code which list to reference, using the static final codes in this class
	 */
	private void showSelectionErrorMessage(int code)
	{
		String message = null;
		
		switch (code)
		{
			case VM_LIST:
			{
				message = "Please select a Virtual Machine from the list";
				break;
			}
			
			case TEMPLATE_LIST:
			{
				message = "Please select a Template from the list";
				break;
			}
		}
		
		JOptionPane.showMessageDialog(main, message, "Error", JOptionPane.ERROR_MESSAGE);
	}

	/**
	 * Performs the action
	 * @param event the action event
	 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	@Override
	public void actionPerformed(ActionEvent event)
	{
		Object source = event.getSource();
		String command = event.getActionCommand();
		
		//CheckBoxMenuItems - Log menu / VM Menu
		if (source instanceof JCheckBoxMenuItem)
		{
			JCheckBoxMenuItem item = (JCheckBoxMenuItem) event.getSource();
			
			if (command.equalsIgnoreCase("Update on System Log Tab"))
			{
				LOG.setSystemLogEnabled(item.isSelected());
			}
			
			else if (command.equalsIgnoreCase("Update on Main View"))
			{
				LOG.setMainLogEnabled(item.isSelected());
			}
			
			else if (command.equalsIgnoreCase("Save to Log File"))
			{
				LOG.setLogFileEnabled(item.isSelected());
			}
			
			else if (command.equalsIgnoreCase("Launch Console in Full Screen"))
			{
				SETTINGS_READER.setFullScreen(item.isSelected());
			}
		}
		
		else if (source instanceof JMenuItem)
		{			
			//File menu
			if (command.equalsIgnoreCase("New VM"))
			{
				new DeployWizard(main, DeployWizard.SINGLE_VM_MODE, null);
			}
			
			else if (command.equalsIgnoreCase("Save Settings"))
			{
				main.engine.saveSettings();
				JOptionPane.showMessageDialog(main, "Settings Saved Successfully", "Save Successful", JOptionPane.INFORMATION_MESSAGE);
			}
			
			else if (command.equalsIgnoreCase("Export Last Deployment"))
			{
				main.engine.exportLastDeployment(SETTINGS_READER.getLastDeployment(), main);
			}
			
			else if (command.equalsIgnoreCase("Logoff"))
			{
				main.engine.logoff();
			}
			
			else if (command.equalsIgnoreCase("Exit"))
			{
				int choice = JOptionPane.showConfirmDialog(main, "Are you sure you want to quit?", "Exit VASE Deploy?", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
				if (choice == JOptionPane.YES_OPTION)
				{
					main.engine.quit();
				}
			}
			
			//Deploy menu
			else if (command.equalsIgnoreCase("New Deployment Group"))
			{
				new DeployWizard(main, DeployWizard.GROUP_VMS_MODE, null);
			}
			
			else if (command.equalsIgnoreCase("New Deployment Team"))
			{
				new DeployWizard(main, DeployWizard.TEAM_VMS_MODE, null);
			}
			
			//VM Menu
			else if (command.equalsIgnoreCase("Launch Console"))
			{
				if (isListSelected(main.jListVMs))
				{
					VirtualMachineExt vm = (VirtualMachineExt) main.jListVMs.getSelectedValue();
					main.engine.launchConsole(vm.getVM());
				}
				
				else
				{
					showSelectionErrorMessage(VM_LIST);
				}
			}
			
			else if (command.equalsIgnoreCase("Power On"))
			{
				if (isListSelected(main.jListVMs))
				{
					VirtualMachineExt vm = (VirtualMachineExt) main.jListVMs.getSelectedValue();
					main.engine.powerOn(vm.getVM());
				}
				
				else
				{
					showSelectionErrorMessage(VM_LIST);
				}
			}
			
			else if (command.equalsIgnoreCase("Power Off"))
			{
				if (isListSelected(main.jListVMs))
				{
					VirtualMachineExt vm = (VirtualMachineExt) main.jListVMs.getSelectedValue();
					main.engine.powerOff(vm.getVM());
				}
				
				else
				{
					showSelectionErrorMessage(VM_LIST);
				}
			}
			
			else if (command.equalsIgnoreCase("Suspend"))
			{
				if (isListSelected(main.jListVMs))
				{
					VirtualMachineExt vm = (VirtualMachineExt) main.jListVMs.getSelectedValue();
					main.engine.suspend(vm.getVM());
				}
				
				else
				{
					showSelectionErrorMessage(VM_LIST);
				}
			}
			
			else if (command.equalsIgnoreCase("Shutdown"))
			{
				if (isListSelected(main.jListVMs))
				{
					VirtualMachineExt vm = (VirtualMachineExt) main.jListVMs.getSelectedValue();
					main.engine.shutdown(vm.getVM());
				}
				
				else
				{
					showSelectionErrorMessage(VM_LIST);
				}
			}
			
			else if (command.equalsIgnoreCase("Reset"))
			{
				if (isListSelected(main.jListVMs))
				{
					VirtualMachineExt vm = (VirtualMachineExt) main.jListVMs.getSelectedValue();
					main.engine.reset(vm.getVM());
				}
				
				else
				{
					showSelectionErrorMessage(VM_LIST);
				}
			}
			
			else if (command.equalsIgnoreCase("Restart"))
			{
				if (isListSelected(main.jListVMs))
				{
					VirtualMachineExt vm = (VirtualMachineExt) main.jListVMs.getSelectedValue();
					main.engine.restart(vm.getVM());
				}
				
				else
				{
					showSelectionErrorMessage(VM_LIST);
				}
			}
			
			//Template Menu
			else if (command.equalsIgnoreCase("Deploy from Selected Template"))
			{
				if (isListSelected(main.jListTemplates))
				{
					Template template = (Template) main.jListTemplates.getSelectedValue();
					new DeployWizard(main, DeployWizard.TEMPLATE_VM_MODE, template);
				}
				
				else
				{
					showSelectionErrorMessage(TEMPLATE_LIST);
				}
			}
			
			//Clear sub menu (Log Menu)
			else if (command.equalsIgnoreCase("Main View Log"))
			{
				main.jtaMainLog.setText("");
			}
			
			else if (command.equalsIgnoreCase("System Log Tab"))
			{
				main.jtaSystemLog.setText("");
			}
			
			else if (command.equalsIgnoreCase("Log File Contents"))
			{
				int choice = JOptionPane.showConfirmDialog(main, "Are you sure you want to clear the log file's contents?", 
						"Confirm Clear Log File Contents", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
				if (choice == JOptionPane.YES_OPTION)
				{
					LOG.clear();
					JOptionPane.showMessageDialog(main, "Log file cleared successfully", "Log File Cleared", JOptionPane.INFORMATION_MESSAGE);
				}
			}
			
			//View Menu
			else if (command.equalsIgnoreCase("Refresh"))
			{
				main.startRefreshThread();
			}
			
			else if (command.equalsIgnoreCase("Summary"))
			{
				main.tabbedPane.setSelectedIndex(0);
			}
			
			else if (command.equalsIgnoreCase("Virtual Machines"))
			{
				main.tabbedPane.setSelectedIndex(1);
			}
			
			else if (command.equalsIgnoreCase("Last Deployment"))
			{
				main.tabbedPane.setSelectedIndex(2);
			}
			
			else if (command.equalsIgnoreCase("System Log"))
			{
				main.tabbedPane.setSelectedIndex(3);
			}
			
			//Help Menu
			else if (command.equalsIgnoreCase("Show Help Tab"))
			{
				if (!main.tabbedPane.hasHelpTab())
				{
					main.tabbedPane.addHelpTab();
				}
				
				else
				{
					main.tabbedPane.removeHelpTab();
				}
			}
			
			else if (command.equalsIgnoreCase("Project_VASE on Sourceforge"))
			{
				//Launch Default Browser
    			
    			if (!Desktop.isDesktopSupported())
    			{
    				LOG.write("Desktop system is not supported, cannot launch external link", true);
    				JOptionPane.showMessageDialog(main, "Cannot launch external link", "Error", JOptionPane.ERROR_MESSAGE);
    			}
    			
    			else
    			{
    				Desktop desktop = Desktop.getDesktop();
    				
    				if (!desktop.isSupported(Desktop.Action.BROWSE))
    				{
    					LOG.write("Desktop doesn't support the BROWSE Action", true);
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
			
			else if (command.equalsIgnoreCase("About VASE Deploy"))
			{
				new AboutDialog(main);
			}
		}
	}
}
