/**
 * Project_VASE Deploy package
 */
package vase.client.deploy;

import java.awt.Dimension;
import java.util.ArrayList;

import javax.swing.DefaultListModel;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;

import com.vmware.vim25.mo.Folder;
import com.vmware.vim25.mo.ManagedEntity;

/**
 * Refresh Thread Class
 * <br />
 * Thread is created after the GuiMain is constructed, then started
 * In the Globals interface, the REFRESH_INTERVAL static final value controls
 * how often this thread refreshes the data displayed on the GuiMain
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
		try
		{
			//Update Lists
			updateLists();
			
			//Update Table
			updateTable();
			
			DefaultMutableTreeNode top = (DefaultMutableTreeNode) main.datacenterTree.getModel().getRoot();
			Folder mainFolder = main.engine.dc.getVmFolder();
			refreshChildEntities(mainFolder, top);
			main.datacenterTree.expandAll(true);
		}
		
		catch (Exception e)
		{
			LOG.write("Error Updating Data", true);
			e.printStackTrace();
		}
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
		int selection = main.vmTable.getSelectedRow();
		main.vmTable.getModel().removeAllRows();
		Object[][] engineData = main.engine.gatherData();
		
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
	
	/**
	 * Updates the Lists on the Summary tab
	 * <br />
	 * Retrieves an Object[] from the CommandEngine, clears the lists, and adds
	 * new elements to each list's model.  Also gets the selection and sets the row
	 * in the list to be selected again, since redrawing the list will clear it.
	 */
	private void updateLists()
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
		
		main.jListVMs.setPreferredSize(new Dimension(250, vmModel.size() * 20));
		main.jListVMs.revalidate();
		main.jListVMs.repaint();
	}
	
	/**
	 * Scans for changes in the tree's child entities
	 * @param thisFolder the current directory
	 * @param top the treenode representation of the directory
	 * @throws Exception handled by the RefreshThread in GuiMain
	 */
	private void refreshChildEntities(Folder thisFolder, DefaultMutableTreeNode top)
	{
		try
		{
			ManagedEntity[] children = thisFolder.getChildEntity();
			ArrayList<DefaultMutableTreeNode> nodes = new ArrayList<DefaultMutableTreeNode>();
			
			for (int i = 0; i < main.datacenterTree.getModel().getChildCount(top); i++)
			{
				nodes.add((DefaultMutableTreeNode) top.getChildAt(i));
			}
			
			compare(children, nodes, top);
			
			for (DefaultMutableTreeNode thisNode : nodes)
			{
				if (thisNode.getUserObject() instanceof Folder)
				{
					refreshChildEntities((Folder) thisNode.getUserObject(), thisNode);
				}
			}
		}
		
		catch (NullPointerException e)
		{
			LOG.write("Error Refreshing Data: Could not gather VM list from datacenter", true);
		}
		
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}
	
	/**
	 * Compares a ManagedEntity object to the current TreeNode.  If they are not the
	 * same, it adds
	 * @param object
	 * @param current
	 * @param top
	 */
	private void compare(ManagedEntity[] objects, ArrayList<DefaultMutableTreeNode> nodes, 
			DefaultMutableTreeNode top)
	{
		//Check to Add
		for (ManagedEntity thisEntity : objects)
		{
			boolean found = false;
			for (DefaultMutableTreeNode thisNode : nodes)
			{
				try
				{
					ManagedEntity userObject = (ManagedEntity) thisNode.getUserObject(); 
					if (thisEntity.getName().equals(userObject.getName()))
					{
						found = true;
					}
				}
				
				catch (RuntimeException e)
				{
					found = true;
				}
			}
			
			if (!found)
			{
				top.add(new DefaultMutableTreeNode(thisEntity));
				((DefaultTreeModel) main.datacenterTree.getModel()).nodeStructureChanged(top);
			}
		}
		
		//Check to Remove
		for (DefaultMutableTreeNode thisNode : nodes)
		{
			boolean there = false;
			for (ManagedEntity thisEntity : objects)
			{
				try
				{
					ManagedEntity userObject = (ManagedEntity) thisNode.getUserObject();
					if (userObject.getName().equals(thisEntity.getName()))
					{
						there = true;
					}
				}
				
				catch (RuntimeException e)
				{
					there = false;
				}
			}
			
			if (!there)
			{
				top.remove(thisNode);
				((DefaultTreeModel) main.datacenterTree.getModel()).nodeStructureChanged(top);
			}
		}
	}
}