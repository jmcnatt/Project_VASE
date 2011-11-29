/**
 * Project_VASE Deploy GUI package
 */
package vase.client.deploy.gui;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JOptionPane;

import vase.client.deploy.ProjectConstraints;

/**
 * Gui Window Adapter - saves settings via the SettingsReader in ProjectConstraints
 * @author James McNatt
 * @version Project_VASE Deploy
 * @see SettingsReader#save()
 */
public class WindowListener extends WindowAdapter
{
	/**
	 * Window closing event that this class is overriding
	 * @param event the closing window event
	 */
	@Override
	public void windowClosing(WindowEvent event)
	{
		if (event.getSource() instanceof Main)
		{
			int choice = JOptionPane.showConfirmDialog((Main) event.getSource(), "Are you sure you want to quit?", "Exit VASE Deploy?", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
			if (choice == JOptionPane.YES_OPTION)
			{
				((Main) event.getSource()).engine.quit();
			}
		}
		
		else if (event.getSource() instanceof DeployWizard)
		{
			ProjectConstraints.LOG.write("Deployment Wizard cancelled");
		}
	}
}
