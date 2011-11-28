/**
 * Project_VASE Connect list package
 */
package vase.client.connect.list;

import java.awt.Color;
import java.awt.Component;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.ListCellRenderer;
import javax.swing.SpringLayout;

import com.vmware.vim25.VirtualMachinePowerState;

import vase.client.InterfaceConstraints;

/**
 * Renders the JLists on the Summary tab in GuiMain
 * <br />
 * Responsible for creating JLabels, the actual content displayed in the JList.  
 * Grabs information from the Object in the JList (Array of Objects passed in the RefreshThread)
 * @author James McNatt
 * @version Project_VASE 0.1.0
 * @see GuiMain
 */
public class ListDataRenderer implements ListCellRenderer, InterfaceConstraints
{
	private static final long serialVersionUID = 3682446958335321543L;
	
	/**
	 * Renders the Panel on the List
	 * @param list the List to render
	 * @param value the object[] of data to represent
	 * @param index the row position
	 * @param isSelected whether or not the row is selected
	 * @param hasCellFocus whether or not the cell has focus
	 */
	public Component getListCellRendererComponent(JList list, Object value, int index, boolean isSelected,
			boolean hasCellFocus)
	{
		Object[] data = (Object[]) value;

		JPanel panel = new JPanel(new SpringLayout());
		SpringLayout layout = (SpringLayout) panel.getLayout();
		JLabel iconLabel = createIconLabel(new ImageIcon(getClass().getResource("/images/connect/vm-icon-small.png")));
		JLabel nameLabel = createNameLabel((String) data[0]);
		JLabel osLabel = createOSLabel((String) data[1]);
		JLabel powerLabel = createPowerLabel((String) data[2]);
		
		if (isSelected)
		{
			panel.setBackground(COLOR_SWING_SELECTION);
			iconLabel.setBorder(BorderFactory.createLineBorder(COLOR_SWING_SELECTION, 3));
			nameLabel.setBorder(BorderFactory.createLineBorder(COLOR_SWING_SELECTION, 3));
			osLabel.setBorder(BorderFactory.createLineBorder(COLOR_SWING_SELECTION, 3));
			powerLabel.setBorder(BorderFactory.createLineBorder(COLOR_SWING_SELECTION, 3));
		}
		
		else
		{
			panel.setBackground(Color.WHITE);
			iconLabel.setBorder(BorderFactory.createLineBorder(list.getBackground(), 3));
			nameLabel.setBorder(BorderFactory.createLineBorder(list.getBackground(), 3));
			osLabel.setBorder(BorderFactory.createLineBorder(list.getBackground(), 3));
			powerLabel.setBorder(BorderFactory.createLineBorder(list.getBackground(), 3));			
		}
		
		if (data[2].equals(VirtualMachinePowerState.poweredOn.name()))
		{
			nameLabel.setForeground(COLOR_POWERED_ON);
			osLabel.setForeground(COLOR_POWERED_ON);
			powerLabel.setForeground(COLOR_POWERED_ON);
		}
		
		else if (data[2].equals(VirtualMachinePowerState.suspended.name()))
		{
			nameLabel.setForeground(COLOR_SUSPENDED);
			osLabel.setForeground(COLOR_SUSPENDED);
			powerLabel.setForeground(COLOR_SUSPENDED);
		}
		
		else if (data[2].equals(VirtualMachinePowerState.poweredOff.name()))
		{
			nameLabel.setForeground(COLOR_POWERED_OFF);
			osLabel.setForeground(COLOR_POWERED_OFF);
			powerLabel.setForeground(COLOR_POWERED_OFF);
		}
		
		panel.setOpaque(true);
		panel.setPreferredSize(DIM_CONNECT_LIST_ENTRY);
		panel.add(iconLabel);
		panel.add(nameLabel);
		panel.add(osLabel);
		panel.add(powerLabel);
		
		layout.putConstraint(SpringLayout.NORTH, iconLabel, 0, SpringLayout.NORTH, panel);
		layout.putConstraint(SpringLayout.WEST, iconLabel, 0, SpringLayout.WEST, panel);
		layout.putConstraint(SpringLayout.NORTH, nameLabel, 0, SpringLayout.NORTH, panel);
		layout.putConstraint(SpringLayout.WEST, nameLabel, 0, SpringLayout.EAST, iconLabel);
		layout.putConstraint(SpringLayout.NORTH, osLabel, 0, SpringLayout.NORTH, panel);
		layout.putConstraint(SpringLayout.WEST, osLabel, 0, SpringLayout.EAST, nameLabel);
		layout.putConstraint(SpringLayout.NORTH, powerLabel, 0, SpringLayout.NORTH, panel);
		layout.putConstraint(SpringLayout.WEST, powerLabel, 0, SpringLayout.EAST, osLabel);		
		
		
		
		return panel;
	}
	
	/**
	 * Creates the Power State Label for the List
	 * @param text the text representation of the power state of the VM
	 * @return the formatted label for displaying the current power state of the vm
	 */
	private JLabel createPowerLabel(String text)
	{
		JLabel label = new JLabel(text);
		label.setPreferredSize(DIM_CONNECT_LIST_POWERSTATE);
		label.setOpaque(false);
		
		return label;
	}
	
	/**
	 * Creates the OS Label for the List
	 * @param text the name of the OS
	 * @return the formatted label for displaying the OS on the vm
	 */
	private JLabel createOSLabel(String text)
	{
		JLabel label = new JLabel(text);
		label.setPreferredSize(DIM_CONNECT_LIST_OS);
		label.setMaximumSize(DIM_CONNECT_LIST_OS);
		label.setMinimumSize(DIM_CONNECT_LIST_OS);
		label.setOpaque(false);
		
		return label;
	}
	
	/**
	 * Create the Name Label for the List
	 * @param text the name of the virtual machine
	 * @return the formatted label for displaying the vm name
	 */
	private JLabel createNameLabel(String text)
	{
		JLabel label = new JLabel(text);
		label.setPreferredSize(DIM_CONNECT_LIST_NAME);
		label.setMinimumSize(DIM_CONNECT_LIST_NAME);
		label.setMaximumSize(DIM_CONNECT_LIST_NAME);
		label.setOpaque(false);
		
		return label;
	}
	
	/**
	 * Creates the Icon Label for the List
	 * @param icon the icon to display
	 * @return the formatted label displaying the icon
	 */
	private JLabel createIconLabel(ImageIcon icon)
	{
		JLabel label = new JLabel(icon);
		label.setPreferredSize(DIM_CONNECT_LIST_ICON);
		label.setMinimumSize(DIM_CONNECT_LIST_ICON);
		label.setMaximumSize(DIM_CONNECT_LIST_ICON);
		label.setOpaque(false);
		
		return label;
	}
}
