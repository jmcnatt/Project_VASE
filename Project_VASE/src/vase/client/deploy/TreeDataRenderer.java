/**
 * Project_VASE Deploy package
 */
package vase.client.deploy;

import java.awt.Component;

import javax.swing.JLabel;
import javax.swing.JTree;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeCellRenderer;

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
		TreeObject object = (TreeObject) ((DefaultMutableTreeNode) node).getUserObject();
		object.setExpanded(expanded);
		JLabel label = new JLabel();
		label.setOpaque(false);
		label.setIconTextGap(10);
		label.setHorizontalTextPosition(JLabel.RIGHT);
		
		label.setText(object.getName());
		label.setIcon(object.getIcon());
		
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
