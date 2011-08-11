/**
 * Project_VASE Deploy package
 */
package vase.client.deploy;

import java.awt.Color;
import java.awt.FlowLayout;
import java.util.ArrayList;

import javax.swing.JPanel;
import javax.swing.SpringLayout;

/**
 * Panel for the Last Deployment Tab
 * @author James McNatt & Brenton Kapral
 * @version Project_VASE Deploy
 * @see GuiDeploymentTabEntry
 */
public class GuiDeploymentPanel extends JPanel
{
	private static final long serialVersionUID = 1771961894888352498L;
	
	/**
	 * Main Constructor
	 * <br />
	 * If the deployedVirtualMachine collection from the settings file is blank or
	 * empty, shows the default label
	 * @param deployedVMs
	 */
	public GuiDeploymentPanel(ArrayList<DeployedVirtualMachine> deployedVMs)
	{
		if (deployedVMs == null || deployedVMs.size() == 0)
		{
			setLayout(new FlowLayout(FlowLayout.CENTER, 50, 50));
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
			GuiDeploymentTabEntry panel = new GuiDeploymentTabEntry(deployed);
			add(panel);
			mainLayout.putConstraint(SpringLayout.NORTH, panel, (count * 300) + 20, SpringLayout.NORTH, this);
			mainLayout.putConstraint(SpringLayout.WEST, panel, -275, SpringLayout.HORIZONTAL_CENTER, this);
			
			count++;
		}
	}
}
