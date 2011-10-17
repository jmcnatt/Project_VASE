/**
 * Project_VASE Deploy package
 */
package vase.client.deploy;

import java.awt.Color;
import java.awt.Component;

import javax.swing.BorderFactory;
import javax.swing.DefaultListCellRenderer;
import javax.swing.JLabel;
import javax.swing.JList;

import vase.client.InterfaceConstraints;

import com.vmware.vim25.VirtualMachinePowerState;

/**
 * Renders the JLists on the Summary tab in GuiMain
 * <br />
 * Responsible for creating JLabels, the actual content displayed in the JList.  
 * Grabs information from the Object in the JList (Array of Objects passed in the RefreshThread)
 * @author James McNatt & Brenton Kapral
 * @version Project_VASE Deploy
 * @see GuiMain
 */
public class ListIconRenderer extends DefaultListCellRenderer implements InterfaceConstraints
{
	private static final long serialVersionUID = -2975587475675878232L;
	private JList jlTemplates;
	private JList jlVMs;
	
	public ListIconRenderer(JList jlTemplates, JList jlVMs)
	{
		this.jlTemplates = jlTemplates;
		this.jlVMs = jlVMs;
	}
	
	/**
	 * Overrides the getListCellRendererComponent
	 * <br />
	 * Returns a preformatted JLabel for the JLists
	 * @see DefaultListCellRenderer#getListCellRendererComponent(JList, Object, int, boolean, boolean)
	 */
	@Override
	public Component getListCellRendererComponent(JList list, Object value, int index,
			boolean isSelected, boolean cellHasFocus)
	{
		JLabel label = null;
		if (list.equals(jlTemplates))
		{
			Template thisTemplate = (Template) value;
			label = new JLabel(thisTemplate.getName());
			label.setIcon(thisTemplate.getIcon());
			label.setIconTextGap(5);
			label.setOpaque(true);			
		}
		
		else if (list.equals(jlVMs))
		{
			VirtualMachineExt thisVM = (VirtualMachineExt) value;
			String guestOS = thisVM.getGuestOS();
			if (guestOS == null) guestOS = "OS Unknown";
			label = new JLabel(thisVM.getName() + "   (" + guestOS + ")   [" + thisVM.getPowerStatus() + "]");
			label.setIcon(thisVM.getIcon());
			label.setIconTextGap(5);
			label.setOpaque(true);
		
			
			if (thisVM.getPowerStatus().equals(VirtualMachinePowerState.poweredOn.name()))
			{
				label.setForeground(COLOR_POWERED_ON);
			}
			
			else if (thisVM.getPowerStatus().equals(VirtualMachinePowerState.suspended.name()))
			{
				label.setForeground(COLOR_SUSPENDED);
			}
			
			else if (thisVM.getPowerStatus().equals(VirtualMachinePowerState.poweredOff.name()))
			{
				label.setForeground(COLOR_POWERED_OFF);
			}
			
			else
			{
				label.setForeground(Color.BLACK);
			}
		}
		
		if (isSelected)
		{
			label.setBackground(COLOR_SWING_SELECTION);
			label.setBorder(BorderFactory.createLineBorder(COLOR_SWING_SELECTION, 3));
		}
		
		else
		{
			label.setBackground(list.getBackground());
			label.setBorder(BorderFactory.createLineBorder(list.getBackground(), 3));
		}
		
		return label;
	}
}