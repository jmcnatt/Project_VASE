/**
 * Project_VASE Connect menu package
 */
package vase.client.connect.menu;

import java.awt.Event;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;

import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;
import javax.swing.JSeparator;
import javax.swing.KeyStroke;

import vase.client.connect.CommandEngine;

import com.vmware.vim25.mo.VirtualMachine;

/**
 * Popup menu activated with right-click on the list in GuiMain
 * @author James McNatt & Brenton Kapral
 * @version Project_VASE Connect
 */
public class PopupMenuVM extends JPopupMenu implements ActionListener
{
	private static final long serialVersionUID = -2846731766626303116L;
	
	private CommandEngine engine;
	private JMenuItem jmiPowerOn;
	private JMenuItem jmiPowerOff;
	private JMenuItem jmiSuspend;
	private JMenuItem jmiReset;
	private JMenuItem jmiShutdown;
	private JMenuItem jmiRestart;
	private JMenuItem jmiConnect;
	private VirtualMachine vm;
	
	/**
	 * Creates a right-click popup menu to select power operations, set to
	 * the command engine
	 * @param engine the command engine instance
	 * @param name the name of the virtual machine selected
	 * @see ListListener
	 * @see CommandEngine
	 */
	public PopupMenuVM(CommandEngine engine, VirtualMachine vm)
	{
		this.vm = vm;
		this.engine = engine;
		makeItems();
		addListeners();
		addAccelerators();
		
		//Add the menus
		add(jmiConnect);
		add(new JSeparator());
		add(jmiPowerOn);
		add(jmiPowerOff);
		add(jmiSuspend);
		add(jmiReset);
		add(new JSeparator());
		add(jmiShutdown);
		add(jmiRestart);
	}
	
	/**
	 * Makes the attributes for this class
	 */
	private void makeItems()
	{
		jmiPowerOn = new JMenuItem("Power On");
		jmiPowerOff = new JMenuItem("Power Off");
		jmiSuspend = new JMenuItem("Suspend");
		jmiReset = new JMenuItem("Reset");
		jmiShutdown = new JMenuItem("Shutdown");
		jmiRestart = new JMenuItem("Restart");
		jmiConnect = new JMenuItem("Launch Console");
	}
	
	/**
	 * Adds this as the action listener to all menu items
	 */
	private void addListeners()
	{
		jmiPowerOn.addActionListener(this);
		jmiPowerOff.addActionListener(this);
		jmiSuspend.addActionListener(this);
		jmiReset.addActionListener(this);
		jmiShutdown.addActionListener(this);
		jmiRestart.addActionListener(this);
		jmiConnect.addActionListener(this);
	}
	
	/**
	 * Adds the keyboard shortcuts (accelerators)
	 */
	private void addAccelerators()
	{
		jmiPowerOn.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_B, Event.CTRL_MASK));
		jmiPowerOff.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_E, Event.CTRL_MASK));
		jmiReset.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_T, Event.CTRL_MASK));
		jmiShutdown.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_D, Event.CTRL_MASK));
		jmiSuspend.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_Z, Event.CTRL_MASK));
		jmiRestart.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_R, Event.CTRL_MASK));
		jmiConnect.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_W, Event.CTRL_MASK));
	}
	
	/**
	 * Calls commands from the CommandEngine based on the Action Command
	 * @param event the action event from the menu items
	 */
	public void actionPerformed(ActionEvent event)
	{
		String caption = event.getActionCommand();
		if (vm != null)
		{
			if (caption.equalsIgnoreCase("Power On"))
			{
				engine.powerOn(vm);
			}
			
			else if (caption.equalsIgnoreCase("Power Off"))
			{
				engine.powerOff(vm);
			}
			
			else if (caption.equalsIgnoreCase("Reset"))
			{
				engine.reset(vm);
			}
			
			else if (caption.equalsIgnoreCase("Suspend"))
			{
				engine.suspend(vm);
			}
			
			else if (caption.equalsIgnoreCase("Shutdown"))
			{
				engine.shutdown(vm);
			}
			
			else if (caption.equalsIgnoreCase("Restart"))
			{
				engine.restart(vm);
			}
			
			else if (caption.equalsIgnoreCase("Launch Console"))
			{
				engine.launchConsole(vm);
			}
		}
	}
}
