/**
 * Project_VASE Deploy package
 */
package vase.client.deploy;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.SwingUtilities;

/**
 * Table Listener which triggers the Right-click menu
 * @author James McNatt & Brenton Kapral
 * @version Project_VASE Deploy
 */
public class TableListener extends MouseAdapter
{
	private PopupMenuVM menu;
	private CommandEngine engine;
	
	/**
	 * Main Constructor
	 * @param engine the command engine instance
	 */
	public TableListener(CommandEngine engine)
	{
		this.engine = engine;
		menu = new PopupMenuVM(engine);
	}
	
	/**
	 * Mouse clicked that generates the right-click menu or brings up the VM console 
	 * with a double-click action
	 * @param event the mouse event
	 */
	public void mouseClicked(MouseEvent event)
	{
		Table table = (Table) event.getSource();
		
		if (SwingUtilities.isRightMouseButton(event))
		{
			try
			{
				int row = table.rowAtPoint(event.getPoint());
				table.getSelectionModel().setSelectionInterval(row, row);
				showPopup(event, table);
			}
			
			catch (Exception e)
			{
				//do not display window
			}
		}
		
		else if (event.getClickCount() == 2 && !event.isConsumed())
		{
			event.consume();
			String vmName = null;
			if (table.getSelectedRow() != -1) vmName = (String) table.getModel().getValueAt(table.getSelectedRow(), 1);
			if (vmName != null)
			{
				VirtualMachineExt vm = engine.getVM(vmName);
				if (vm != null) engine.launchConsole(vm.getVM());
				
			}
		}
	}
	
	/**
	 * Shows the popup menu on the frame
	 * @param event the mouse event
	 * @param table the referenced table object
	 */
	private void showPopup(MouseEvent event, Table table)
	{
		int row = table.rowAtPoint(event.getPoint());
		if (table.getSelectedRow() != -1 && table.getModel().getValueAt(row, 1) != null)
		{
			menu.setThisVM((String) table.getModel().getValueAt(row, 1));
			menu.show(event.getComponent(), event.getX(), event.getY());
		}		
	}
}
