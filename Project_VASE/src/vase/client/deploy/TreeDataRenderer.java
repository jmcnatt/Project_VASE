/**
 * Project_VASE Deploy package
 */
package vase.client.deploy;

import java.awt.Component;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JTree;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeCellRenderer;

import com.vmware.vim25.VirtualMachinePowerState;
import com.vmware.vim25.mo.Datacenter;
import com.vmware.vim25.mo.Folder;
import com.vmware.vim25.mo.VirtualApp;
import com.vmware.vim25.mo.VirtualMachine;

/**
 * Renders the Tree data on the Summary tab in GuiMain
 * <br />
 * Responsible for creating JLabels, the actual content displayed in the Tree.  
 * Grabs information from the Object in the node
 * @author James McNatt & Brenton Kapral
 * @version Project_VASE Deploy
 * @see GuiMain
 */
public class TreeDataRenderer extends DefaultTreeCellRenderer
{
	private static final long serialVersionUID = -8196588739602146929L;
	
	/**
	 * Renders the data on the Tree in the GuiMain
	 * @see DefaultTreeCellRenderer#getTreeCellRendererComponent(JTree, Object, boolean, boolean, boolean, int, boolean)
	 */
	@Override
	public Component getTreeCellRendererComponent(JTree tree, Object node,
            boolean selected, boolean expanded, boolean leaf, int row, boolean hasFocus)
	{
		Object value = ((DefaultMutableTreeNode) node).getUserObject();
		JLabel label = new JLabel();
		label.setOpaque(false);
		label.setIconTextGap(10);
		label.setHorizontalTextPosition(JLabel.RIGHT);
		
		if (value instanceof Datacenter)
		{
			label.setText(((Datacenter) value).getName());
			label.setIcon(new ImageIcon(getClass().getResource("/images/deploy/datacenter-icon.png")));
		}
		
		else if (value instanceof Folder)
		{
			try
			{
				label.setText(((Folder) value).getName());
				
				if (expanded)
				{
					label.setIcon(new ImageIcon(getClass().getResource("/images/deploy/folder-icon-small-open.png")));
				}
				
				else
				{
					label.setIcon(new ImageIcon(getClass().getResource("/images/deploy/folder-icon-small-closed.png")));
				}
			}
			
			catch (RuntimeException e)
			{
				//Do Nothing
			}
		}
		
		else if (value instanceof VirtualMachine)
		{
			try
			{
				VirtualMachine vm = (VirtualMachine) value;
				Folder parent = (Folder) vm.getParent();
				label.setText(vm.getName());
				
				if (parent.getName().equals(ProjectConstraints.TEMPLATE_FOLDER))
				{
					label.setIcon(new ImageIcon(getClass().getResource("/images/deploy/template-icon-small.png")));
				}
				
				else
				{
					if (vm.getSummary().runtime.powerState.name().equals(VirtualMachinePowerState.poweredOn.name()))
					{
						label.setIcon(new ImageIcon(getClass().getResource("/images/deploy/vm-icon-small-poweredon.png")));
					}
					else
					{
						label.setIcon(new ImageIcon(getClass().getResource("/images/deploy/vm-icon-small.png")));
					}
				}
			}
			
			catch (RuntimeException e)
			{
				//Do nothing
			}
		}
		
		else if (value instanceof VirtualApp)
		{
			label.setText(((VirtualApp) value).getName());
			label.setIcon(new ImageIcon(getClass().getResource("/images/deploy/vapp-icon.png")));
		}
		
		else
		{
			label.setText(value.toString());
			label.setIcon(new ImageIcon(getClass().getResource("/images/deploy/blank.png")));
		}
		
		if (selected)
		{
			label.setBackground(tree.getBackground());
		}
		
		else
		{
			label.setBackground(tree.getBackground());
		}
		
		return label;
	}
}
