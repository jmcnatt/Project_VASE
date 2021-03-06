/**
 * Project_VASE Deploy menu package
 */
package vase.client.deploy.menu;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;

import vase.client.deploy.CommandEngine;
import vase.client.deploy.ProjectConstraints;
import vase.client.deploy.gui.DeployWizard;
import vase.client.deploy.vmo.Template;

/**
 * Popup menu (right-click) for the templates in the Summary tab
 * @author James McNatt & Brenton Kapral
 * @version Project_VASE Deploy
 */
public class PopupMenuTemplate extends JPopupMenu implements ActionListener
{
	private static final long serialVersionUID = -4694617040355012528L;
	
	private Template template;
	private JMenuItem jmiDeploy;
	private CommandEngine engine;
	
	/**
	 * Constructs the menu and its attributes, then adds the attributes to the menu
	 * @param engine the command engine to launch commands from
	 */
	public PopupMenuTemplate(CommandEngine engine)
	{
		this.engine = engine;
		
		makeItems();
		addListeners();
		
		add(jmiDeploy);		
	}
	
	/**
	 * Makes the attributes for this class
	 */
	private void makeItems()
	{
		jmiDeploy = new JMenuItem("Deploy");
	}
	
	/**
	 * Adds the action listeners
	 */
	private void addListeners()
	{
		jmiDeploy.addActionListener(this);
	}
	
	/**
	 * Sets the template attribute, needed for the action listener
	 * @param template
	 */
	public void setThisTemplate(Template template)
	{
		this.template = template;
	}
	
	/**
	 * Listens for the actions
	 * @param event the action event
	 */
	public void actionPerformed(ActionEvent event)
	{
		String caption = event.getActionCommand();
		
		if (caption.equalsIgnoreCase("Deploy") && template != null)
		{
			ProjectConstraints.LOG.write("Starting Deploy Wizard...");
			new DeployWizard(engine.main, DeployWizard.TEMPLATE_VM_MODE, template);
		}
	}
}
