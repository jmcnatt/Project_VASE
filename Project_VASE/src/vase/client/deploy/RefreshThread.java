/**
 * Project_VASE Deploy package
 */
package vase.client.deploy;

import java.util.ArrayList;

import javax.swing.DefaultListModel;
import javax.swing.SwingUtilities;

import vase.client.ThreadExt;

/**
 * Refresh Thread Class
 * <br />
 * Thread is created after the GuiMain is constructed, then started
 * whenever an update is needed. The RefreshWorker class starts an instance of
 * this class at the interval specified on the REFRESH_INTERVAL
 * @author James McNatt & Brenton Kapral
 * @version Project_VASE Deploy
 */
public class RefreshThread extends ThreadExt
{
	private GuiMain main;
	
	/**
	 * Main Constructor
	 * @param main the GuiMain instance containing attributes needed to be refreshed
	 */
	public RefreshThread(GuiMain main)
	{
		this.main = main;
	}
	
	/**
	 * Run Method, runs the thread
	 */
	public void run()
	{
		main.engine.refresh();
		
		//Update Lists
		updateLists();
		
		//Update Table
		updateTable();
		
		//Update Tree
		updateTree();
	}
	
	/**
	 * Updates the Tree on the Summary Tab
	 * Removes all elements, rebuilds and expands the tree
	 */
	private void updateTree()
	{
		SwingUtilities.invokeLater(new Runnable() 
		{
			public void run()
			{
				try
				{
					main.datacenterTree.removeAll();
					main.datacenterTree.build();
					main.datacenterTree.expandAll(true);
				}
				
				catch (Exception e)
				{
					ProjectConstraints.LOG.write("Error Updating Data", true);
					ProjectConstraints.LOG.printStackTrace(e);
				}
			}
		});
	}
	
	/**
	 * Updates the Table on the Virtual Machine Tab
	 * <br />
	 * Creates an Object[][] from the CommandEngine, iterates through
	 * each object and fills the table with data.  Also gets the selection, and
	 * sets that same row to be selected again, since redrawing the table will clear it.
	 */
	private void updateTable()
	{
		SwingUtilities.invokeLater(new Runnable() 
		{
			public void run()
			{
				int selection = main.vmTable.getSelectedRow();
				main.vmTable.getModel().removeAllRows();
				Object[][] engineData = main.engine.gatherTableData();
				
				for (int i = 0; i < engineData.length; i++)
				{
					Object[] rowData = engineData[i];
					for (int j = 0; j < rowData.length; j++)
					{
						main.vmTable.getModel().setValueAt(i, j, rowData[j]);
					}
				}
				
				main.vmTable.getModel().fireTableDataChanged();
				
				//If items were removed, can't selected them.  Check to see if the list size changed
				if (selection <= main.vmTable.getModel().getRowCount() - 1)
				{
					main.vmTable.getSelectionModel().setSelectionInterval(selection, selection);
				}
			}
		});
	}
	
	/**
	 * Updates the Lists on the Summary tab
	 * <br />
	 * Retrieves an ArrayList<VirtualMachineExt> from the CommandEngine, clears the lists, and adds
	 * new elements to each list's model.  Also gets the selection and sets the row
	 * in the list to be selected again, since redrawing the list will clear it.
	 */
	private void updateLists()
	{
		SwingUtilities.invokeLater(new Runnable() 
		{
			public void run()
			{
				DefaultListModel templateModel = (DefaultListModel) main.jListTemplates.getModel();
				DefaultListModel vmModel = (DefaultListModel) main.jListVMs.getModel();
				int templateSelection = main.jListTemplates.getSelectionModel().getMinSelectionIndex();
				int vmSelection = main.jListVMs.getSelectionModel().getMinSelectionIndex();
				
				ArrayList<VirtualMachineExt> currentVMs = main.engine.getListVMs();
				vmModel.removeAllElements();
				
				for (VirtualMachineExt vm : currentVMs)
				{
					vmModel.addElement(vm);
				}
				
				//If items were removed, can't selected them.  Check to see if the list size changed
				
				if (templateSelection <= templateModel.getSize() - 1)
				{
					main.jListTemplates.getSelectionModel().setSelectionInterval(templateSelection, templateSelection);
				}
				
				if (vmSelection <= vmModel.getSize() - 1)
				{
					main.jListVMs.getSelectionModel().setSelectionInterval(vmSelection, vmSelection);
				}
			}
		});
	}
}