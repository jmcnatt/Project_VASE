/**
 * Project_VASE Deploy package
 */
package vase.client.deploy;

import java.rmi.RemoteException;
import java.util.Enumeration;

import javax.swing.BorderFactory;
import javax.swing.JTree;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreeNode;
import javax.swing.tree.TreePath;

import com.vmware.vim25.InvalidProperty;
import com.vmware.vim25.RuntimeFault;
import com.vmware.vim25.mo.Folder;
import com.vmware.vim25.mo.VirtualMachine;
import com.vmware.vim25.mo.VirtualApp;
import com.vmware.vim25.mo.ManagedEntity;

/**
 * Tree class displaying the folder structure of the datacenter on the
 * Summary Tab on the GuiMain
 * @author James McNatt & Brenton Kapral
 * @see GuiSummaryTab
 * @see GuiMain
 */
public class Tree extends JTree
{
	private static final long serialVersionUID = 8131581563936452886L;
	
	private CommandEngine engine;
	private DefaultMutableTreeNode datacenter;
	private DefaultTreeModel model;
	
	/**
	 * Main Constructor, builds a Tree
	 * @param engine the command engine instance
	 * @see Tree#build()
	 */
	public Tree(CommandEngine engine)
	{
		super(new DefaultMutableTreeNode("Loading Inventory..."));
		this.engine = engine;
		model = (DefaultTreeModel) getModel();
		setBorder(BorderFactory.createLineBorder(getBackground(), 3));
		setCellRenderer(new TreeDataRenderer());
		build();
		expandAll(true);
	}
	
	/**
	 * Builds the first instance of the Tree when the object is created
	 */
	public void build()
	{
		if (engine.dc != null)
		{
			datacenter = new DefaultMutableTreeNode(new TreeObject(engine.dc.getName(), TreeObject.DATACENTER));
			model.setRoot(datacenter);			
			
			try
			{
				if (engine.dc.getVmFolder().getChildEntity() != null)
				{
					//add children
					for (ManagedEntity entity : engine.dc.getVmFolder().getChildEntity())
					{
						DefaultMutableTreeNode thisNode = null;
						
						if (entity instanceof VirtualMachine)
						{
							if (((VirtualMachine) entity).getParent().getName().equals(ProjectConstraints.TEMPLATE_FOLDER))
							{
								thisNode = new DefaultMutableTreeNode(new TreeObject(((VirtualMachine) entity).getName(), TreeObject.TEMPLATE));
							}
							
							else
							{
								thisNode = new DefaultMutableTreeNode(new TreeObject(((VirtualMachine) entity).getName(), TreeObject.VIRTUAL_MACHINE));
							}
							
							datacenter.add(thisNode);
						}
						
						else if (entity instanceof VirtualApp)
						{
							thisNode = new DefaultMutableTreeNode(new TreeObject(((VirtualApp) entity).getName(), TreeObject.VIRTUAL_MACHINE));
							datacenter.add(thisNode);
						}
						
						else if (entity instanceof Folder)
						{
							FolderExt folder = new FolderExt((Folder) entity);
							thisNode = new DefaultMutableTreeNode(new TreeObject(((Folder) entity).getName(), TreeObject.FOLDER));
							datacenter.add(thisNode);
							getChildEntities(folder, thisNode);
						}						
					}
				}
			}
			
			catch (Exception e)
			{
				datacenter = new DefaultMutableTreeNode("No Data");
				e.printStackTrace();
			}
		}
		
		else
		{
			datacenter = new DefaultMutableTreeNode("No Data");
		}
		
		model.nodeStructureChanged(datacenter);
	}
	
	/**
	 * Gets the child entities of the folder and adds them to the tree.  If one
	 * of the children is a Folder, use recursion and re-run this method
	 * @param thisFolder the folder containing children
	 * @param top the tree node representation of the folder
	 * @throws RemoteException 
	 * @throws RuntimeFault 
	 * @throws InvalidProperty 
	 */
	public void getChildEntities(FolderExt thisFolder, DefaultMutableTreeNode top) throws InvalidProperty, RuntimeFault, RemoteException
	{
		ManagedEntity[] children = thisFolder.getFolder().getChildEntity();
		
		for (ManagedEntity entity : children)
		{
			DefaultMutableTreeNode currentNode = null;
			if (entity instanceof VirtualMachine)
			{
				if (((VirtualMachine) entity).getParent().getName().equals(ProjectConstraints.TEMPLATE_FOLDER))
				{
					currentNode = new DefaultMutableTreeNode(new TreeObject(((VirtualMachine) entity).getName(), TreeObject.TEMPLATE));
				}
				
				else
				{
					currentNode = new DefaultMutableTreeNode(new TreeObject(((VirtualMachine) entity).getName(), TreeObject.VIRTUAL_MACHINE));
				}			
				
				top.add(currentNode);
			}
			
			else if (entity instanceof VirtualApp)
			{
				currentNode = new DefaultMutableTreeNode(new TreeObject(((VirtualApp) entity).getName(), TreeObject.VIRTUAL_MACHINE));
				top.add(currentNode);
			}
			
			else if (entity instanceof Folder)
			{
				FolderExt folder = new FolderExt((Folder) entity);
				currentNode = new DefaultMutableTreeNode(new TreeObject(((Folder) entity).getName(), TreeObject.FOLDER));
				top.add(currentNode);
				getChildEntities(folder, currentNode);
			}				
		}
	}
	
	
	/**
	 * Expands the tree
	 * @param expand
	 */
	public void expandAll(boolean expand) 
	{
	    TreeNode root = (TreeNode) model.getRoot();

	    // Traverse tree from root
	    expandAll(new TreePath(root), expand);
	}
	
	
	/**
	 * Overloaded expandAll with the new TreePath of the child
	 * @param parent the parent treepath
	 * @param expand whether or not to expend the tree
	 */
	public void expandAll(TreePath parent, boolean expand) 
	{
	    // Traverse children
	    TreeNode node = (TreeNode) parent.getLastPathComponent();
	    
	    if (node.getChildCount() >= 0) 
	    {
	        for (@SuppressWarnings("unchecked")
			Enumeration<TreeNode> e = node.children(); e.hasMoreElements(); ) 
	        {
	            TreeNode n = e.nextElement();
	            TreePath path = parent.pathByAddingChild(n);
	            expandAll(path, expand);
	        }
	    }

	    // Expansion or collapse must be done bottom-up
	    if (expand) 
	    {
	        expandPath(parent);
	    } 
	    
	    else 
	    {
	        collapsePath(parent);
	    }
	}
}
