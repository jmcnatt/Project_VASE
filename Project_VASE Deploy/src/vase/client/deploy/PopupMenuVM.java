/**
 * Project_VASE Deploy package
 */
package vase.client.deploy;

import java.awt.Event;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;

import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;
import javax.swing.JSeparator;
import javax.swing.KeyStroke;

/**
 * Popup menu activated with right-click on the list in GuiMain
 * @author James McNatt & Brenton Kapral
 * @version Project_VASE Deploy
 */
public class PopupMenuVM extends JPopupMenu implements ActionListener
{
	private static final long serialVersionUID = -2846731766626303116L;
	
	private VirtualMachineExt thisVM;
	private CommandEngine engine;
	private JMenuItem jmiPowerOn;
	private JMenuItem jmiPowerOff;
	private JMenuItem jmiSuspend;
	private JMenuItem jmiReset;
	private JMenuItem jmiShutdown;
	private JMenuItem jmiRestart;
	private JMenuItem jmiSetTeam;
	private JMenuItem jmiRename;
	private JMenuItem jmiDelete;
	private JMenuItem jmiConnect;
	
	/**
	 * Creates a right-click popup menu to select power operations, set to
	 * the command engine
	 * @param engine the command engine instance
	 * @see ListListener
	 * @see CommandEngine
	 */
	public PopupMenuVM(CommandEngine engine)
	{
		this.engine = engine;
		makeItems();
		addListeners();
		addAccelerators();
		
		add(jmiPowerOn);
		add(jmiPowerOff);
		add(jmiSuspend);
		add(jmiReset);
		add(new JSeparator());
		add(jmiShutdown);
		add(jmiRestart);
		add(new JSeparator());
		add(jmiRename);
		add(jmiDelete);
		add(new JSeparator());
		add(jmiSetTeam);
		add(jmiConnect);
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
		jmiSetTeam = new JMenuItem("Set Team");
		jmiRename = new JMenuItem("Rename");
		jmiDelete = new JMenuItem("Delete from Disk");
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
		jmiSetTeam.addActionListener(this);
		jmiRename.addActionListener(this);
		jmiDelete.addActionListener(this);
		jmiConnect.addActionListener(this);
	}
	
	/**
	 * Sets the VirtualMachine associated with this Popup by getting the name object 
	 * from the CommandEngine using the name
	 * @param name the name of the VirtualMachine\
	 * @see CommandEngine#getVM(String)
	 * @see ListListener
	 */
	public void setThisVM(String name)
	{
		thisVM = engine.getVM(name);
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
		if (thisVM != null)
		{
			if (caption.equalsIgnoreCase("Power On"))
			{
				engine.powerOn(thisVM.getVM());
			}
			
			else if (caption.equalsIgnoreCase("Power Off"))
			{
				engine.powerOff(thisVM.getVM());
			}
			
			else if (caption.equalsIgnoreCase("Reset"))
			{
				engine.reset(thisVM.getVM());
			}
			
			else if (caption.equalsIgnoreCase("Suspend"))
			{
				engine.suspend(thisVM.getVM());
			}
			
			else if (caption.equalsIgnoreCase("Shutdown"))
			{
				engine.shutdown(thisVM.getVM());
			}
			
			else if (caption.equalsIgnoreCase("Restart"))
			{
				engine.restart(thisVM.getVM());
			}
			
			else if (caption.equalsIgnoreCase("Delete from Disk"))
			{
				engine.delete(thisVM.getVM());
			}
			
			else if (caption.equalsIgnoreCase("Rename"))
			{
				engine.rename(thisVM.getVM());
			}
			
			else if (caption.equalsIgnoreCase("Set Team"))
			{
				engine.setTeam(thisVM.getVM());
			}
			
			else if (caption.equalsIgnoreCase("Launch Console"))
			{
				engine.launchConsole(thisVM.getVM());
			}
		}
	}
}
