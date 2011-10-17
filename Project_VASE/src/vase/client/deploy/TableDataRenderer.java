/**
 * Project_VASE Deploy package
 */
package vase.client.deploy;

import java.awt.Color;
import java.awt.Component;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.table.TableCellRenderer;

import vase.client.InterfaceConstraints;

/**
 * Renders the Table data on the Virtual Machines tab in GuiMain
 * <br />
 * Responsible for creating JLabels, the actual content displayed in the Table.  
 * Grabs information from the Object in the Table's cell
 * @author James McNatt & Brenton Kapral
 * @version Project_VASE Deploy
 * @see Table
 */
public class TableDataRenderer implements TableCellRenderer, InterfaceConstraints
{

	/**
	 * @see javax.swing.table.TableCellRenderer#getTableCellRendererComponent(javax.swing.JTable, java.lang.Object, boolean, boolean, int, int)
	 */
	@Override
	public Component getTableCellRendererComponent(JTable table, Object value,
			boolean isSelected, boolean hasFocus, int row, int col)
	{
		JLabel label = null;
		
		if (value instanceof ImageIcon)
		{
			ImageIcon icon = (ImageIcon) value;
			label = new JLabel(icon);
		}
		
		else if (value instanceof String)
		{
			String text = (String) value;
			label = new JLabel(text);
		}
		
		else
		{
			label = new JLabel();
		}
		
		
		if (isSelected)
		{
			label.setBackground(COLOR_SWING_SELECTION);
			label.setBorder(BorderFactory.createLineBorder(COLOR_SWING_SELECTION, 3));
		}
		
		else
		{
			label.setBackground(table.getBackground());
			label.setBorder(BorderFactory.createLineBorder(Color.WHITE, 3));
		}
		
		label.setOpaque(true);
		
		return label;
	}
}
