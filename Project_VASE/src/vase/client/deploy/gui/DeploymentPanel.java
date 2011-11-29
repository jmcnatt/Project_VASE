/**
 * Project_VASE Deploy GUI package
 */
package vase.client.deploy.gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.util.ArrayList;

import javax.swing.SpringLayout;

import vase.client.Panel;
import vase.client.deploy.gui.tab.DeploymentTabEntry;
import vase.client.deploy.vmo.DeployedVirtualMachine;

/**
 * Panel for the Last Deployment Tab
 * @author James McNatt & Brenton Kapral
 * @version Project_VASE Deploy
 * @see GuiDeploymentTabEntry
 */
public class DeploymentPanel extends Panel
{
	private static final long serialVersionUID = 1771961894888352498L;
	
	/**
	 * Main Constructor
	 * <br />
	 * If the deployedVirtualMachine collection from the settings file is blank or
	 * empty, shows the default label
	 * @param deployedVMs
	 */
	public DeploymentPanel(ArrayList<DeployedVirtualMachine> deployedVMs)
	{
		super(new FlowLayout(FlowLayout.CENTER, 50, 50), true);
		
		if (deployedVMs == null || deployedVMs.size() == 0)
		{
			add(Tab.createMainDeploymentLabel("[ No Deployment Data ]"));
		}
		
		else
		{
			update(deployedVMs);
		}
		
		setBackground(Color.WHITE);
	}
	
	/**
	 * Updates the contents of the LastDeploymentTab
	 * @param deployedVMs the list of DeployedVirtualMachines to send to the panel
	 */
	public void update(ArrayList<DeployedVirtualMachine> deployedVMs)
	{
		setLayout(new SpringLayout());
		int count = 0;
		
		SpringLayout mainLayout = (SpringLayout) getLayout();
		
		//for each deployed VM, create a jpanel displaying its summary
		for (DeployedVirtualMachine deployed : deployedVMs)
		{
			DeploymentTabEntry panel = new DeploymentTabEntry(deployed);
			add(panel);
			mainLayout.putConstraint(SpringLayout.NORTH, panel, (count * 300) + 20, SpringLayout.NORTH, this);
			mainLayout.putConstraint(SpringLayout.HORIZONTAL_CENTER, panel, 0, SpringLayout.HORIZONTAL_CENTER, this);
			
			count++;
		}
		
		Dimension size = new Dimension(560, (320 * count));
		setPreferredSize(size);
		setMaximumSize(size);
		setMinimumSize(size);
	}
}
