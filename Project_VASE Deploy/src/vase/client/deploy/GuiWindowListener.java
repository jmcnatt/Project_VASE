/**
 * Project_VASE Deploy package
 */
package vase.client.deploy;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JOptionPane;

/**
 * Gui Window Adapter - saves settings via the SettingsReader in ProjectConstraints
 * @author James McNatt
 * @version Project_VASE Deploy
 * @see SettingsReader#save()
 */
public class GuiWindowListener extends WindowAdapter
{
	/**
	 * Window closing event that this class is overriding
	 * @param event the closing window event
	 */
	@Override
	public void windowClosing(WindowEvent event)
	{
		if (event.getSource() instanceof GuiMain)
		{
			int choice = JOptionPane.showConfirmDialog((GuiMain) event.getSource(), "Are you sure you want to quit?", "Exit VASE Deploy?", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
			if (choice == JOptionPane.YES_OPTION)
			{
				((GuiMain) event.getSource()).engine.quit();
			}
		}
		
		else if (event.getSource() instanceof GuiDeployWizard)
		{
			GuiDeployWizard.LOG.write("Deployment Wizard cancelled");
		}
	}
}
