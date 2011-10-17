/**
 * Project_VASE Connect package
 */
package vase.client.connect;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JOptionPane;

/**
 * Gui Window Adapter - saves settings via the SettingsReader by calling the CommandEngine
 * @author James McNatt & Brenton Kapral
 * @version Project_VASE Connect
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
			int choice = JOptionPane.showConfirmDialog((GuiMain) event.getSource(), "Are you sure you want to quit?", "Exit VASE Connect?", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
			if (choice == JOptionPane.YES_OPTION)
			{
				((GuiMain) event.getSource()).engine.quit();
			}
		}
	}
}
