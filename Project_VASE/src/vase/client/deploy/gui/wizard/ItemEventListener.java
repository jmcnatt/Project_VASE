/**
 * Project_VASE Deploy GUI wizard package
 */
package vase.client.deploy.gui.wizard;

import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JRadioButton;

import vase.client.deploy.ProjectConstraints;
import vase.client.deploy.gui.DeployWizard;

/**
 * Item listener for the DeployWizard
 * <br />
 * Adds the appropriate swing components to this listener when constructed.
 * @author James McNatt & Brenton Kapral
 * @version Project_VASE Deploy
 * @see DeployWizard
 */
public class ItemEventListener implements ItemListener, ProjectConstraints
{
	/**
	 * The DeployWizard instance
	 */
	public DeployWizard main;
	
	/**
	 * Main Constructor
	 * @param main the DeployWizard instance
	 */
	public ItemEventListener(DeployWizard main)
	{
		this.main = main;
		
		addListeners();
	}
	
	/**
	 * Adds the attributes in various panels to this listener
	 */
	private void addListeners()
	{
		main.center.osPanel.jcbCategory.addItemListener(this);
		main.center.osPanel.jcbOsSelection.addItemListener(this);
		main.center.guestInfoPanel.jrbDHCP.addItemListener(this);
		main.center.guestInfoPanel.jrbStatic.addItemListener(this);
		main.center.windowsClientPanel.jcbBind.addItemListener(this);
		main.center.windowsServerPanel.jcbActiveDirectory.addItemListener(this);
		main.center.linuxClientPanel.jcbBind.addItemListener(this);
		main.center.linuxServerPanel.jcbNIS.addItemListener(this);
	}
	
	/**
	 * Handles Combo box events
	 * @param source the combo box source from the item event
	 */
	private void comboBoxEvent(JComboBox source, ItemEvent event)
	{
		String selection = (String) source.getSelectedItem();
		if (event.getStateChange() == ItemEvent.SELECTED)
		{
			if (source == main.center.osPanel.jcbCategory)
			{
				main.center.osPanel.jcbOsSelection.removeAllItems();
				if (selection.equals(WINDOWS))
				{
					main.center.osPanel.jcbOsSelection.addItem("Select Operating System");
					
					for (int i = 0; i < WINDOWS_CLIENT.length; i++)
					{
						main.center.osPanel.jcbOsSelection.addItem(WINDOWS_CLIENT[i]);
					}
					
					for (int i = 0; i < WINDOWS_SERVER.length; i++)
					{
						main.center.osPanel.jcbOsSelection.addItem(WINDOWS_SERVER[i]);
					}
					
					main.center.osPanel.jcbOsSelection.setSelectedItem("Select Operating System");
					main.center.osPanel.jlOsSelection.setVisible(true);
					main.center.osPanel.jcbOsSelection.setVisible(true);
				}
				
				else if (selection.equals(LINUX_RPM))
				{
					main.center.osPanel.jcbOsSelection.addItem("Select Operating System");
					
					for (int i = 0; i < RPM_SERVER.length; i++)
					{
						main.center.osPanel.jcbOsSelection.addItem(RPM_SERVER[i]);
					}
					
					for (int i = 0; i < RPM_CLIENT.length; i++)
					{
						main.center.osPanel.jcbOsSelection.addItem(RPM_CLIENT[i]);
					}
					
					main.center.osPanel.jcbOsSelection.setSelectedItem("Select Operating System");
					main.center.osPanel.jlOsSelection.setVisible(true);
					main.center.osPanel.jcbOsSelection.setVisible(true);
				}
				
				else if (selection.equals(LINUX_DEBIAN))
				{
					main.center.osPanel.jcbOsSelection.addItem("Select Operating System");
					
					for (int i = 0; i < DEBIAN_SERVER.length; i++)
					{
						main.center.osPanel.jcbOsSelection.addItem(DEBIAN_SERVER[i]);
					}
					
					for (int i = 0; i < DEBIAN_CLIENT.length; i++)
					{
						main.center.osPanel.jcbOsSelection.addItem(DEBIAN_CLIENT[i]);
					}
					
					main.center.osPanel.jcbOsSelection.setSelectedItem("Select Operating System");
					main.center.osPanel.jlOsSelection.setVisible(true);
					main.center.osPanel.jcbOsSelection.setVisible(true);
				}
				
				else if (selection.equals(BSD))
				{
					main.center.osPanel.jcbOsSelection.addItem("Select Operating System");
					
					for (int i = 0; i < BSD_SERVER.length; i++)
					{
						main.center.osPanel.jcbOsSelection.addItem(BSD_SERVER[i]);
					}
					
					main.center.osPanel.jcbOsSelection.setSelectedItem("Select Operating System");
					main.center.osPanel.jlOsSelection.setVisible(true);
					main.center.osPanel.jcbOsSelection.setVisible(true);
				}
				
				else if (selection.equals("Select Category"))
				{
					main.center.osPanel.jcbOsSelection.removeAllItems();
					main.center.osPanel.jcbOsSelection.setVisible(false);
					main.center.osPanel.jlOsSelection.setVisible(false);
					main.center.osPanel.jlOsValid.setVisible(false);
					main.south.jbNext.setEnabled(false);
				}
			}
			
			else if (source == main.center.osPanel.jcbOsSelection)
			{
				if (!selection.equals("Select Operating System"))
				{
					if (main.window.engine.isTemplate((String) main.center.osPanel.jcbOsSelection.getSelectedItem()))
					{
						main.center.osPanel.jlOsValid.setText("Operating System Template is Valid");
						main.south.jbNext.setEnabled(true);
					}
					
					else
					{
						main.center.osPanel.jlOsValid.setText("Operating System Template cannot be located in the Datacenter");
						main.south.jbNext.setEnabled(false);
					}
					
					main.center.osPanel.jlOsValid.setVisible(true);
				}
				
				else
				{
					main.center.osPanel.jlOsValid.setVisible(false);
					main.south.jbNext.setEnabled(false);
				}
			}
		}
	}
	
	/**
	 * Handles Radio Button events
	 */
	private void radioButtonEvent(JRadioButton source, ItemEvent event)
	{
		if (source == main.center.guestInfoPanel.jrbDHCP && event.getStateChange() == ItemEvent.SELECTED)
		{
			main.center.guestInfoPanel.jtfIP.setText("");
			main.center.guestInfoPanel.jtfNetmask.setText("");
			main.center.guestInfoPanel.jtfDefaultGateway.setText("");
			main.center.guestInfoPanel.jtfDnsServer.setText("");
			main.center.guestInfoPanel.jtfIP.setEditable(false);
			main.center.guestInfoPanel.jtfNetmask.setEditable(false);
			main.center.guestInfoPanel.jtfDefaultGateway.setEditable(false);
			main.center.guestInfoPanel.jtfDnsServer.setEditable(false);
		}
		
		else if (source == main.center.guestInfoPanel.jrbStatic && event.getStateChange() == ItemEvent.SELECTED)
		{
			main.center.guestInfoPanel.jtfIP.setEditable(true);
			main.center.guestInfoPanel.jtfNetmask.setEditable(true);
			main.center.guestInfoPanel.jtfDefaultGateway.setEditable(true);
			main.center.guestInfoPanel.jtfDnsServer.setEditable(true);
		}
	}
	
	/**
	 * Handles Check Box events
	 */
	private void checkBoxEvent(JCheckBox source, ItemEvent event)
	{
		if (source == main.center.windowsServerPanel.jcbActiveDirectory)
		{
			if (event.getStateChange() == ItemEvent.SELECTED)
			{
				main.center.windowsServerPanel.jtfDirectoryName.setVisible(true);
				main.center.windowsServerPanel.jtfDirectoryName.setText(main.center.guestInfoPanel.jtfDomainName.getText());
				main.center.windowsServerPanel.jlDirectoryName.setVisible(true);
				main.center.windowsServerPanel.jcbDNS.setSelected(true);
			}
			
			else if (event.getStateChange() == ItemEvent.DESELECTED)
			{
				main.center.windowsServerPanel.jtfDirectoryName.setVisible(false);
				main.center.windowsServerPanel.jtfDirectoryName.setText("");
				main.center.windowsServerPanel.jlDirectoryName.setVisible(false);
			}
		}
		
		else if (source == main.center.linuxServerPanel.jcbNIS)
		{
			if (event.getStateChange() == ItemEvent.SELECTED)
			{
				main.center.linuxServerPanel.jtfDirectoryName.setVisible(true);
				main.center.linuxServerPanel.jtfDirectoryName.setText(main.center.guestInfoPanel.jtfDomainName.getText());
				main.center.linuxServerPanel.jlDirectoryName.setVisible(true);
				main.center.linuxServerPanel.jcbNFS.setSelected(true);
			}
			
			else if (event.getStateChange() == ItemEvent.DESELECTED)
			{
				main.center.linuxServerPanel.jtfDirectoryName.setVisible(false);
				main.center.linuxServerPanel.jtfDirectoryName.setText("");
				main.center.linuxServerPanel.jlDirectoryName.setVisible(false);
			}
		}
		
		else if (source == main.center.linuxClientPanel.jcbBind)
		{
			if (event.getStateChange() == ItemEvent.SELECTED)
			{
				main.center.linuxClientPanel.jtfDirectoryName.setVisible(true);
				main.center.linuxClientPanel.jtfDirectoryName.setText(main.center.guestInfoPanel.jtfDomainName.getText());
				main.center.linuxClientPanel.jlDirectoryName.setVisible(true);					
			}
			
			else if (event.getStateChange() == ItemEvent.DESELECTED)
			{
				main.center.linuxClientPanel.jtfDirectoryName.setVisible(false);
				main.center.linuxClientPanel.jtfDirectoryName.setText("");
				main.center.linuxClientPanel.jlDirectoryName.setVisible(false);
			}
		}
		
		else if (source == main.center.windowsClientPanel.jcbBind)
		{
			if (event.getStateChange() == ItemEvent.SELECTED)
			{
				main.center.windowsClientPanel.jtfDirectoryName.setVisible(true);
				main.center.windowsClientPanel.jtfDirectoryName.setText(main.center.guestInfoPanel.jtfDomainName.getText());
				main.center.windowsClientPanel.jlDirectoryName.setVisible(true);
			}
			
			else if (event.getStateChange() == ItemEvent.DESELECTED)
			{
				main.center.windowsClientPanel.jtfDirectoryName.setVisible(false);
				main.center.windowsClientPanel.jtfDirectoryName.setText("");
				main.center.windowsClientPanel.jlDirectoryName.setVisible(false);
			}
		}
	}

	/* (non-Javadoc)
	 * @see java.awt.event.ItemListener#itemStateChanged(java.awt.event.ItemEvent)
	 */
	@Override
	public void itemStateChanged(ItemEvent event)
	{
		if (event.getSource() instanceof JComboBox) 
		{
			comboBoxEvent((JComboBox) event.getSource(), event);
		}
		
		else if (event.getSource() instanceof JRadioButton)
		{
			radioButtonEvent((JRadioButton) event.getSource(), event);
		}
		
		else if (event.getSource() instanceof JCheckBox)
		{
			checkBoxEvent((JCheckBox) event.getSource(), event);
		}
	}
}
