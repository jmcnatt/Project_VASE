/**
 * Project_VASE Deploy package
 */
package vase.client.deploy;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JButton;

import vase.client.FormValidator;

/**
 * Action listener for the DeployWizard
 * @author James McNatt & Brenton Kapral
 * @version Project_VASE Deploy
 * @see DeployWizard
 */
public class DeployWizardActionListener implements ActionListener, ProjectConstraints
{
	/**
	 * DeployWizard instance
	 */
	public DeployWizard main;
	
	/**
	 * VirtualMachine collection
	 */
	private ArrayList<DeployedVirtualMachine> virtualMachines;
	
	/**
	 * Main Constructor
	 * @param main
	 */
	public DeployWizardActionListener(DeployWizard main)
	{
		this.main = main;
		
		addListeners();
	}
	
	/**
	 * Adds the attributes in the various panels to this listener
	 */
	private void addListeners()
	{
		main.south.jbCancel.addActionListener(this);
		main.south.jbFinish.addActionListener(this);
		main.south.jbNext.addActionListener(this);
		main.south.jbSave.addActionListener(this);
	}
	
	/**
	 * Handles the cancel operation
	 */
	private void cancel()
	{
		main.dispose();
		LOG.write("Deploy Wizard cancelled");
	}
	
	/**
	 * Handles the next operation
	 * @see DeployWizardActionListener#actionPerformed(ActionEvent)
	 */
	private void next(String state)
	{
		virtualMachines = main.virtualMachines;
		
		//Determine which state the wizard is in, then take action
		if (state.equals(DeployWizardCenterPanel.WELCOME))
		{
			main.osTotal = Integer.parseInt((String) main.center.welcomePanel.jcbCount.getSelectedItem());
			for (int i = 0; i < main.osTotal; i++)
			{
				virtualMachines.add(new DeployedVirtualMachine());
				virtualMachines.get(i).setTeam((String) main.center.welcomePanel.jcbTeam.getSelectedItem());
			}
			
			main.stepTotal = (main.osTotal * 3) + 2;
			main.stepNumber++;
			main.updateTitleLabel("Select Virtual Machine Operating System");
			main.updateSubTitleLabel(true);
			main.center.show(DeployWizardCenterPanel.SELECTOS);
			main.center.osPanel.jcbCategory.requestFocus();
		}
		
		else if (state.equals(DeployWizardCenterPanel.SELECTOS))
		{
			//used only if SINGLE_VM_MODE
			if (main.mode == DeployWizard.SINGLE_VM_MODE) virtualMachines.add(new DeployedVirtualMachine());
			
			//configure new DeployedVirtualMachine with fields from SELECTOS
			String osName = (String) main.center.osPanel.jcbOsSelection.getSelectedItem();
			virtualMachines.get(main.osNumber - 1).setOsCategory((String) main.center.osPanel.jcbCategory.getSelectedItem());
			virtualMachines.get(main.osNumber - 1).setOsName(osName);
			main.chosenOSCategory = (String) main.center.osPanel.jcbCategory.getSelectedItem();
			
			//Check each array and determine if OS is client or server
			for (int i = 0; i < WINDOWS_CLIENT.length; i++)
			{
				if (virtualMachines.get(main.osNumber - 1).getOsName().equals(WINDOWS_CLIENT[i]))
				{
					virtualMachines.get(main.osNumber - 1).setOsType(CLIENT);
					main.chosenOSType = CLIENT;
				}
			}
			
			for (int i = 0; i < WINDOWS_SERVER.length; i++)
			{
				if (virtualMachines.get(main.osNumber - 1).getOsName().equals(WINDOWS_SERVER[i]))
				{
					virtualMachines.get(main.osNumber - 1).setOsType(SERVER);
					main.chosenOSType = SERVER;
				} 
			}
			
			for (int i = 0; i < RPM_CLIENT.length; i++)
			{
				if (virtualMachines.get(main.osNumber - 1).getOsName().equals(RPM_CLIENT[i]))
				{
					virtualMachines.get(main.osNumber - 1).setOsType(CLIENT);
					main.chosenOSType = CLIENT;
				} 
			}
			
			for (int i = 0; i < RPM_SERVER.length; i++)
			{
				if (virtualMachines.get(main.osNumber - 1).getOsName().equals(RPM_SERVER[i]))
				{
					virtualMachines.get(main.osNumber - 1).setOsType(SERVER);
					main.chosenOSType = SERVER;
				} 
			}
			
			for (int i = 0; i < DEBIAN_CLIENT.length; i++)
			{
				if (virtualMachines.get(main.osNumber - 1).getOsName().equals(DEBIAN_CLIENT[i]))
				{
					virtualMachines.get(main.osNumber - 1).setOsType(CLIENT);
					main.chosenOSType = CLIENT;
				} 
			}
			
			for (int i = 0; i < DEBIAN_SERVER.length; i++)
			{
				if (virtualMachines.get(main.osNumber - 1).getOsName().equals(DEBIAN_SERVER[i]))
				{
					virtualMachines.get(main.osNumber - 1).setOsType(SERVER);
					main.chosenOSType = SERVER;
				} 
			}
			
			for (int i = 0; i < BSD_SERVER.length; i++)
			{
				if (virtualMachines.get(main.osNumber - 1).getOsName().equals(BSD_SERVER[i]))
				{
					virtualMachines.get(main.osNumber - 1).setOsType(SERVER);
					main.chosenOSType = SERVER;
				} 
			}
			
			//move on to the next step
			main.stepNumber++;
			main.updateTitleLabel("Configure Settings for " + osName);		
			main.updateSubTitleLabel(true);
			main.center.show(DeployWizardCenterPanel.GUEST_INFO);
			main.center.guestInfoPanel.jtfVMName.requestFocusInWindow();
		}
		
		else if (state.equals(DeployWizardCenterPanel.GUEST_INFO))
		{
			//Validate this form
			if (!FormValidator.isValidVirtualMachineName(main.center.guestInfoPanel.jtfVMName.getText()))
			{
				FormValidator.showMessage(FormValidator.VM_NAME_INVALID, main);
			}
			
			else if (!FormValidator.isValidHostname(main.center.guestInfoPanel.jtfHostname.getText()))
			{
				FormValidator.showMessage(FormValidator.HOSTNAME_INVALID, main);
			}
			
			else if (!FormValidator.isValidDomain(main.center.guestInfoPanel.jtfDomainName.getText()))
			{
				FormValidator.showMessage(FormValidator.DOMAIN_INVALID, main);
			}
			
			else if (main.center.guestInfoPanel.jtfIP.isEditable() && 
					!FormValidator.isValidIPAddress(main.center.guestInfoPanel.jtfIP.getText()))
			{
				FormValidator.showMessage(FormValidator.IP_ADDRESS_INVALID, main);
			}
			
			else if (main.center.guestInfoPanel.jtfNetmask.isEditable() && 
					!FormValidator.isValidSubnetMask(main.center.guestInfoPanel.jtfNetmask.getText()))
			{
				FormValidator.showMessage(FormValidator.SUBNET_MASK_INVALID, main);
			}
			
			else if (main.center.guestInfoPanel.jtfDefaultGateway.isEditable() && 
					!FormValidator.isValidIPAddress(main.center.guestInfoPanel.jtfDefaultGateway.getText()))
			{
				FormValidator.showMessage(FormValidator.DEFAULT_GATEWAY_INVALID, main);
			}
			
			else if (main.center.guestInfoPanel.jtfDnsServer.isEditable() && 
					!FormValidator.isValidIPAddress(main.center.guestInfoPanel.jtfDnsServer.getText()))
			{
				FormValidator.showMessage(FormValidator.DNS_ADDRESS_INVALID, main);
			}
			
			//Validation successful, move on
			else
			{
				//use only if deploymentMode is CHOOSEN_VM_MODE
				if (main.mode == DeployWizard.TEMPLATE_VM_MODE)
				{
					virtualMachines.add(new DeployedVirtualMachine());
					virtualMachines.get(main.osNumber - 1).setOsCategory(main.template.getOsCategory());
					virtualMachines.get(main.osNumber - 1).setOsType(main.template.getOsType());
					virtualMachines.get(main.osNumber - 1).setOsName(main.template.getName());
					
					main.chosenOSType = main.template.getOsType();
					main.chosenOSCategory = main.template.getOsCategory();
				}
				
				//configure the new DeployedVirtualMachine from GUESTINFO step
				virtualMachines.get(main.osNumber - 1).setVmName(main.center.guestInfoPanel.jtfVMName.getText());
				virtualMachines.get(main.osNumber - 1).setHostName(main.center.guestInfoPanel.jtfHostname.getText());
				virtualMachines.get(main.osNumber - 1).setDomain(main.center.guestInfoPanel.jtfDomainName.getText());
				virtualMachines.get(main.osNumber - 1).setStaticAddress(main.center.guestInfoPanel.jrbStatic.isSelected());
				virtualMachines.get(main.osNumber - 1).setNetwork((String) main.center.guestInfoPanel.jcbVirtualNetworks.getSelectedItem());
				
				if (main.center.guestInfoPanel.jrbStatic.isSelected())
				{
					virtualMachines.get(main.osNumber - 1).setStaticAddress(true);
					virtualMachines.get(main.osNumber - 1).setIpAddr(main.center.guestInfoPanel.jtfIP.getText());
					virtualMachines.get(main.osNumber - 1).setNetmask(main.center.guestInfoPanel.jtfNetmask.getText());
					virtualMachines.get(main.osNumber - 1).setDefaultGateway(main.center.guestInfoPanel.jtfDefaultGateway.getText());
					virtualMachines.get(main.osNumber - 1).setDnsServer(main.center.guestInfoPanel.jtfDnsServer.getText());
				}
				
				//move on to the next step
				String chosenOS = virtualMachines.get(main.osNumber - 1).getOsName();
				main.stepNumber++;
				main.updateTitleLabel("Configure " + chosenOS + " Services");
				main.updateSubTitleLabel(true);				
				
				if (main.chosenOSCategory.equals(WINDOWS))
				{
					if (main.chosenOSType.equals(CLIENT))
					{
						main.center.show(DeployWizardCenterPanel.WIN_CLIENT);
					}
				
					else if (main.chosenOSType.equals(SERVER))
					{
						main.center.show(DeployWizardCenterPanel.WIN_SERVER);
					}
				}
				
				else if (main.chosenOSCategory.equals(LINUX_DEBIAN) || main.chosenOSCategory.equals(LINUX_RPM)
						|| main.chosenOSCategory.equals(BSD))
				{
					if (main.chosenOSType.equals(CLIENT))
					{
						main.center.show(DeployWizardCenterPanel.LINUX_CLIENT);
					}
				
					else if (main.chosenOSType.equals(SERVER))
					{
						main.center.show(DeployWizardCenterPanel.LINUX_SERVER);
					}
				}
			
				main.south.jbNext.requestFocus();
			}
		}
		
		else if (state.equals(DeployWizardCenterPanel.WIN_CLIENT) || state.equals(DeployWizardCenterPanel.WIN_SERVER) ||
				state.equals(DeployWizardCenterPanel.LINUX_CLIENT) || state.equals(DeployWizardCenterPanel.LINUX_SERVER))
		{
			if ((main.center.windowsClientPanel.jcbBind.isSelected() && !FormValidator.isValidDomain(main.center.windowsClientPanel.jtfDirectoryName.getText())) ||
					(main.center.windowsServerPanel.jcbActiveDirectory.isSelected() && !FormValidator.isValidDomain(main.center.windowsServerPanel.jtfDirectoryName.getText())) ||
					(main.center.linuxClientPanel.jcbBind.isSelected() && !FormValidator.isValidDomain(main.center.linuxClientPanel.jtfDirectoryName.getText())) ||
					(main.center.linuxServerPanel.jcbNIS.isSelected() && !FormValidator.isValidDomain(main.center.linuxServerPanel.jtfDirectoryName.getText())))		
			{
				FormValidator.showMessage(FormValidator.DOMAIN_INVALID, main);
			}
			
			else
			{
				//if windows, configure DeployedVirtualMachine accordingly
				if (main.chosenOSCategory.equals(WINDOWS))
				{
					virtualMachines.get(main.osNumber - 1).setDns(main.center.windowsServerPanel.jcbDNS.isSelected());
					virtualMachines.get(main.osNumber - 1).setDhcp(main.center.windowsServerPanel.jcbDHCP.isSelected());
					virtualMachines.get(main.osNumber - 1).setActiveDirectory(main.center.windowsServerPanel.jcbActiveDirectory.isSelected());
					virtualMachines.get(main.osNumber - 1).setFileServer(main.center.windowsServerPanel.jcbFileServer.isSelected());
					virtualMachines.get(main.osNumber - 1).setIis(main.center.windowsServerPanel.jcbIIS.isSelected());
					virtualMachines.get(main.osNumber - 1).setBind(main.center.windowsClientPanel.jcbBind.isSelected());
					
					if (main.center.windowsServerPanel.jcbExploits.isSelected() || main.center.windowsClientPanel.jcbExploits.isSelected())
					{
						virtualMachines.get(main.osNumber - 1).setExploits(true);
					}
					
					if (main.center.windowsServerPanel.jcbAccounts.isSelected() || main.center.windowsClientPanel.jcbAccounts.isSelected())
					{
						virtualMachines.get(main.osNumber - 1).setAccounts(true);
					}
					
					if (main.center.windowsClientPanel.jcbBind.isSelected())
					{
						virtualMachines.get(main.osNumber - 1).setDomain(main.center.windowsClientPanel.jtfDirectoryName.getText());
					}
				}
				
				else if (main.chosenOSCategory.equals(LINUX_RPM) || main.chosenOSCategory.equals(LINUX_DEBIAN) ||
						main.chosenOSCategory.equals(BSD))
				{
					virtualMachines.get(main.osNumber - 1).setDns(main.center.linuxServerPanel.jcbDNS.isSelected());
					virtualMachines.get(main.osNumber - 1).setDhcp(main.center.linuxServerPanel.jcbDHCP.isSelected());
					virtualMachines.get(main.osNumber - 1).setMail(main.center.linuxServerPanel.jcbMail.isSelected());
					virtualMachines.get(main.osNumber - 1).setHttp(main.center.linuxServerPanel.jcbHTTP.isSelected());
					virtualMachines.get(main.osNumber - 1).setFtp(main.center.linuxServerPanel.jcbFTP.isSelected());
					virtualMachines.get(main.osNumber - 1).setNfs(main.center.linuxServerPanel.jcbNFS.isSelected());
					virtualMachines.get(main.osNumber - 1).setSamba(main.center.linuxServerPanel.jcbSamba.isSelected());
					virtualMachines.get(main.osNumber - 1).setBind(main.center.linuxClientPanel.jcbBind.isSelected());
					
					if (main.center.linuxServerPanel.jcbAccounts.isSelected() || main.center.linuxClientPanel.jcbAccounts.isSelected())
					{
						virtualMachines.get(main.osNumber - 1).setAccounts(true);
					}
					
					if (main.center.linuxClientPanel.jcbExploits.isSelected() || main.center.linuxServerPanel.jcbExploits.isSelected())
					{
						virtualMachines.get(main.osNumber - 1).setExploits(true);
					}
					
					if (main.center.linuxClientPanel.jcbBind.isSelected())
					{
						virtualMachines.get(main.osNumber - 1).setDomain(main.center.linuxClientPanel.jtfDirectoryName.getText());
					}
				}
				
				//determine if the wizard is finished
				if (main.osNumber == main.osTotal)
				{
					main.stepNumber++;
					main.updateSubTitleLabel(false);
					main.updateTitleLabel("Virtual Machines Ready to Deploy");
					main.center.show(DeployWizardCenterPanel.SUMMARY);
					main.south.jbSave.setVisible(true);
					main.south.jbSave.setEnabled(true);
					main.south.jbFinish.setEnabled(true);
					main.south.jbNext.setEnabled(false);
					main.center.summaryPanel.writeSummary(virtualMachines);
					main.south.jbFinish.requestFocus();
				}
				
				//start from the SELECTOS step if wizard is not finished
				else
				{
					main.osNumber++;
					main.stepNumber++;
					main.updateSubTitleLabel(true);
					main.updateTitleLabel("Select Virtual Machine Operating System");
					main.center.resetFields();
					main.center.show(DeployWizardCenterPanel.SELECTOS);
					main.center.osPanel.jcbCategory.requestFocus();
				}
			}
		}
	}

	/* (non-Javadoc)
	 * ActionPerformed
	 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	@Override
	public void actionPerformed(ActionEvent event)
	{
		JButton source = (JButton) event.getSource();
		
		if (source == main.south.jbCancel)
		{
			cancel();
		}
		
		else if (source == main.south.jbNext)
		{
			next(main.center.currentState);
		}
		
		else if (source == main.south.jbFinish)
		{
			main.window.engine.deploy(virtualMachines);
			main.dispose();
		}
		
		else if (source == main.south.jbSave)
		{
			main.window.engine.exportLastDeployment(virtualMachines, main);
		}
	}
}
