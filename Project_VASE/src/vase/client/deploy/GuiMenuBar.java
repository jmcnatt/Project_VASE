/**
 * Project_VASE Deploy package
 */
package vase.client.deploy;

import java.awt.Event;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;

import javax.swing.JCheckBoxMenuItem;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JSeparator;
import javax.swing.KeyStroke;

/**
 * Menu Bar for the GuiMain
 * <br />
 * Gathers Menu Items based on private String[] variables.  Blank items generate
 * separators in the menu.  Adds the GuiMain actionListener, which must be passed
 * in the constructor.
 * @author James McNatt & Brenton Kapral
 * @version Project_VASE Deploy
 */
public class GuiMenuBar extends JMenuBar
{
	private static final long serialVersionUID = 373125667481023004L;
	
	private String[] file = {"New VM", " ", "Save Settings", "Export Last Deployment", " ", "Logoff", "Exit"};
	private String[] deploy = {"New VM", "New Deployment Group", "New Deployment Team"};
	private String[] log = {"Update on System Log Tab", "Update on Main View", "Save to Log File", " ", "Clear"};
	private String[] view = {"Refresh", "Launch Console in Full Screen", " ", "Summary", "Virtual Machines", "Last Deployment", "System Log"};
	private String[] help = {"Show Help Tab", " ", "Project_VASE on Sourceforge", "About VASE Deploy"};
	private String[] clear = {"System Log Tab", "Main View Log", "Log File Contents"};
	private String[] vm = {"Launch Console", " ", "Power On", "Power Off", "Suspend", "Reset", "Shutdown", "Restart"};
	private String[] template = {"Deploy from Selected Template"};
	
	/**
	 * Main Constructor
	 * <br />
	 * Builds the menu bar for the GuiMain
	 * @param listener the GuiActionListener that is the action listener for all items
	 * @see GuiMain
	 * @see GuiActionListener
	 */
	public GuiMenuBar(GuiActionListener listener)
	{
		super();
		add(createMenu("File", 'F', file, listener));
		add(createMenu("Deploy", 'D', deploy, listener));
		add(createMenu("VM", 'V', vm, listener));
		add(createMenu("Template", 'T', template, listener));
		add(createMenu("Log", 'L', log, listener));
		add(createMenu("View", 'W', view, listener));
		add(createMenu("Help", 'H', help, listener));
	}
	
	/**
	 * Creates a menu for the GuiMenuBar
	 * @param name the name of the menu
	 * @param mnemonic the mnemonic to set
	 * @param items the array of menu item titles
	 * @param listener the action listener for the menu items
	 */
	private JMenu createMenu(String name, char mnemonic, String[] items, ActionListener listener)
	{
		JMenu menu = new JMenu(name);
		menu.setMnemonic(mnemonic);
		
		for (int i = 0; i < items.length; i++)
		{
			if (items[i].equals(" "))
			{
				menu.add(new JSeparator());
			}
			
			else if (items[i].equals("Clear"))
			{
				menu.add(createMenu("Clear", 'C', clear, listener));
			}
			
			else if (items[i].equals("Update on Main View") || items[i].equals("Update on System Log Tab") ||
					items[i].equals("Save to Log File"))
			{
				menu.add(createCheckBoxMenuItem(items[i], true, listener));
			}
			
			else if (items[i].equals("Launch Console in Full Screen"))
			{
				JCheckBoxMenuItem item = createCheckBoxMenuItem(items[i], false, listener);
				item.setAccelerator(KeyStroke.getKeyStroke("F11"));
				menu.add(item);
			}
			
			else
			{
				JMenuItem item = new JMenuItem(items[i]);
				
				//Set custom accelerators				
				if (items[i].equals("Refresh")) item.setAccelerator(KeyStroke.getKeyStroke("F5"));
				if (items[i].equals("Show Help Tab")) item.setAccelerator(KeyStroke.getKeyStroke("F1"));
				if (items[i].equals("Save Settings")) item.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S, Event.CTRL_MASK));
				if (items[i].equals("New VM")) item.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_N, Event.CTRL_MASK));
				if (items[i].equals("Power On")) item.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_B, Event.CTRL_MASK));
				if (items[i].equals("Power Off")) item.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_E, Event.CTRL_MASK));
				if (items[i].equals("Suspend")) item.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_Z, Event.CTRL_MASK));
				if (items[i].equals("Reset")) item.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_T, Event.CTRL_MASK));
				if (items[i].equals("Restart")) item.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_R, Event.CTRL_MASK));
				if (items[i].equals("Shutdown")) item.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_D, Event.CTRL_MASK));
				if (items[i].equals("Launch Console")) item.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_W, Event.CTRL_MASK));
				if (items[i].equals("Summary")) item.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_1, Event.CTRL_MASK));
				if (items[i].equals("Virtual Machines")) item.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_2, Event.CTRL_MASK));
				if (items[i].equals("Last Deployment")) item.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_3, Event.CTRL_MASK));
				if (items[i].equals("System Log")) item.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_4, Event.CTRL_MASK));
				
				item.addActionListener(listener);
				menu.add(item);
			}
		}
		
		return menu;
	}
	
	/**
	 * Creates a check box menu item for the GuiMenuBar
	 * @param text the name of the item
	 * @param isSelected whether or not the check box menu item is selected
	 * @param lister the action listener for the item
	 * @return
	 */
	private JCheckBoxMenuItem createCheckBoxMenuItem(String text, boolean isSelected, ActionListener lister)
	{
		JCheckBoxMenuItem item = new JCheckBoxMenuItem(text);
		item.addActionListener(lister);
		item.setSelected(isSelected);
		
		return item;
	}
}
