/**
 * Project_VASE Client package
 */
package vase.client;

import java.util.ArrayList;

import javax.swing.DefaultListModel;
import javax.swing.JList;

/**
 * List class containing elements that display data on the Summary Tab.  Data is
 * gathered from the CommandEngine and could be an instance of VirtualMachineExt,
 * FolderExt, or Template.  Contains a listener and a DefaultListModel
 * @author James McNatt & Brenton Kapral
 * @version Project_VASE
 */
public class List extends JList
{
	private static final long serialVersionUID = -6938029746405080495L;
	private DefaultListModel model;
	
	public List()
	{
		super();
		model = new DefaultListModel();
		setModel(model);
		setLayoutOrientation(JList.VERTICAL);
	}
	
	/**
	 * Main Constructor, overloaded
	 * @param contents the contents of the list
	 */
	public List(ArrayList<?> contents)
	{
		super();
		model = new DefaultListModel();
		setModel(model);
		
		for (Object each : contents)
		{
			model.addElement(each);
		}
		
		setLayoutOrientation(JList.VERTICAL);
	}
}
