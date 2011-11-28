/**
 * Project_VASE Connect gui package
 */
package vase.client.connect.gui;

import java.awt.Event;
import java.awt.event.KeyEvent;

import javax.swing.JCheckBoxMenuItem;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JSeparator;
import javax.swing.KeyStroke;

import vase.client.connect.ProjectConstraints;

/**
 * Menu Bar for the GuiMain.  ActionListener is the GuiMain
 * @author James McNatt & Brenton Kapral
 * @version Project_VASE Connect
 */
public class MenuBar extends JMenuBar
{
	private static final long serialVersionUID = -2655512390219540195L;
	
	private String[] file = {"Launch Console", " ", "Save Settings", " ", "Logoff", "Exit"};
	private String[] vm = {"Power On", "Power Off", "Suspend", "Reset", "Shutdown", "Restart"};
	private String[] view = {"Refresh VM List", "Launch Console in Full Screen"};
	private String[] log = {"Save to Log File", "Clear Log File Contents"};
	private String[] help = {"Show Help Screen", " ", "Project_VASE on Sourceforge", "About VASE Connect"};
	
	/**
	 * Main Constructor
	 * Builds the menu, requires GuiMain for actionListeners
	 * @param main the Main GUI Window
	 */
	public MenuBar(ActionEventListener listener)
	{
		add(createMenu("File", file, listener));
		add(createMenu("VM", vm, listener));
		add(createMenu("View", view, listener));
		add(createMenu("Log", log, listener));
		add(createMenu("Help", help, listener));
	}
	
	/**
	 * Creates a menu entry in the menu bar
	 * <br />
	 * Sets the accelerators when appropriate
	 * @param menuName the name of the menu
	 * @param items the array of items in the menu
	 * @return the created and formatted menu to be added to the menu bar
	 */
	private JMenu createMenu(String menuName, String[] items, ActionEventListener listener)
	{
		JMenu menu = new JMenu(menuName);
		if (!menu.getText().equals("View")) menu.setMnemonic(menu.getText().charAt(0));
		else menu.setMnemonic(menu.getText().charAt(3));
		
		for (int i = 0; i < items.length; i++)
		{
			if (items[i].equals(" "))
			{
				menu.add(new JSeparator());
			}
			
			else if (items[i].equals("Launch Console in Full Screen"))
			{
				JCheckBoxMenuItem item = new JCheckBoxMenuItem(items[i]);
				item.setSelected(ProjectConstraints.SETTINGS_READER.fullScreen);
				item.addActionListener(listener);
				menu.add(item);
			}
			
			else if (items[i].equals("Save to Log File"))
			{
				JCheckBoxMenuItem item = new JCheckBoxMenuItem(items[i]);
				item.setSelected(true);
				item.addActionListener(listener);
				menu.add(item);
			}
			
			else
			{
				JMenuItem item = new JMenuItem(items[i]);
				if (items[i].equals("Refresh VM List")) item.setAccelerator(KeyStroke.getKeyStroke("F5"));
				if (items[i].equals("Show Help Screen")) item.setAccelerator(KeyStroke.getKeyStroke("F1"));
				if (items[i].equals("Save Settings")) item.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S, Event.CTRL_MASK));
				if (items[i].equals("Power On")) item.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_B, Event.CTRL_MASK));
				if (items[i].equals("Power Off")) item.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_E, Event.CTRL_MASK));
				if (items[i].equals("Suspend")) item.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_Z, Event.CTRL_MASK));
				if (items[i].equals("Reset")) item.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_T, Event.CTRL_MASK));
				if (items[i].equals("Shutdown")) item.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_D, Event.CTRL_MASK));
				if (items[i].equals("Launch Console")) item.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_W, Event.CTRL_MASK));
				if (items[i].equals("Restart")) item.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_R, Event.CTRL_MASK));
				item.addActionListener(listener);
				menu.add(item);
			}
		}
		
		return menu;
	}
}
