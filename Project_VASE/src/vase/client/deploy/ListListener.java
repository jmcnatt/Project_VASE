/**
 * Project_VASE Deploy package
 */
package vase.client.deploy;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JList;
import javax.swing.SwingUtilities;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import vase.client.List;

/**
 * Custom List MouseAdapter and ListSelectionListener class for List class; used in
 * the Summary Tab in GuiMain
 * <br />
 * Clears selection from one list when another list is selected and brings up
 * the right-click menu when right-click mouse event is fired
 * @author James McNatt & Brenton Kapral
 * @version Project_VASE Deploy
 */
public class ListListener extends MouseAdapter implements ListSelectionListener
{
	private List jlVMs;
	private List jlTemplates;
	private PopupMenuVM vmMenu;
	private PopupMenuTemplate templateMenu;
	private GuiMain main;
	
	/**
	 * Main constructor
	 * @param jlFolders the folders list
	 * @param jlTemplates the templates list
	 * @param jlVMs the vms list
	 * @param engine the command engine for deployment commands
	 */
	public ListListener(List jlTemplates, List jlVMs, GuiMain main)
	{
		this.main = main;
		this.jlVMs = jlVMs;
		this.jlTemplates = jlTemplates;
		vmMenu = new PopupMenuVM(main.engine);
		templateMenu = new PopupMenuTemplate(main.engine);
	}
	
	/**
	 * Clears the selection when another list is selected
	 */
	@Override
	public void valueChanged(ListSelectionEvent event)
	{
		if (event.getSource().equals(jlVMs))
		{
			jlTemplates.clearSelection();
		}
		
		else if (event.getSource().equals(jlTemplates))
		{
			jlVMs.clearSelection();
		}
	}
	
	/**
	 * Displays the right-click menu if the mouse event was a right-click
	 * <br />
	 * If the event is a double click, does the following:
	 * <ul>
	 * <li>For Template List - brings up the GuiDeployWizard in CHOOSEN_VM_MODE</li>
	 * <li>For VM List - bring up the console</li>
	 * </li>
	 * @param event the mouse event
	 */
	public void mouseClicked(MouseEvent event)
	{
		List list = (List) event.getSource();
		
		if (SwingUtilities.isRightMouseButton(event))
		{
			event.consume();
			
			try
			{
				list.setSelectedIndex(list.locationToIndex(event.getPoint()));
				showPopup(event, list);
			}
			
			catch (Exception e)
			{
				//do not display window
			}
		}
		
		else if (event.getSource().equals(jlTemplates))
		{
			if (event.getClickCount() == 2 && !event.isConsumed() && SwingUtilities.isLeftMouseButton(event))
			{
				event.consume();
				Template template = null;
				if (list.getSelectedIndex() != -1) template = (Template) list.getModel().getElementAt(list.getSelectedIndex());
				if (template != null)
				{
					new DeployWizard(main, DeployWizard.TEMPLATE_VM_MODE, template);
				}
			}
		}
		
		else if (event.getSource().equals(jlVMs))
		{
			if (event.getClickCount() == 2 && !event.isConsumed() && SwingUtilities.isLeftMouseButton(event))
			{
				event.consume();
				VirtualMachineExt vm = null;
				if (list.getSelectedIndex() != -1) vm = (VirtualMachineExt) list.getSelectedValue();
				if (vm != null)
				{
					main.engine.launchConsole(vm.getVM());
				}
			}
		}
	}
	
	/**
	 * Shows the List Popup depending on which list is selected and whether or not the selection is null
	 * @param event the mouse event
	 * @param list the list that was selected
	 */
	private void showPopup(MouseEvent event, JList list)
	{		
		int row = list.getSelectedIndex();
		if (row != -1)
		{
			if (list.equals(jlVMs) && ((VirtualMachineExt) list.getModel().getElementAt(row)).getName() != null)
			{
				vmMenu.setThisVM(((VirtualMachineExt) list.getModel().getElementAt(row)).getName());
				vmMenu.show(event.getComponent(), event.getX(), event.getY());
			}
			
			if (list.equals(jlTemplates) && ((Template) list.getModel().getElementAt(row)).getName() != null)
			{
				templateMenu.setThisTemplate((Template) list.getModel().getElementAt(row));
				templateMenu.show(event.getComponent(), event.getX(), event.getY());
			}
		}
	}
}
