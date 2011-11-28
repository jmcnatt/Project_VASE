/**
 * Project_VASE Connect gui package
 */
package vase.client.connect.gui;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JOptionPane;

/**
 * Gui Window Adapter - saves settings via the SettingsReader by calling the CommandEngine
 * @author James McNatt & Brenton Kapral
 * @version Project_VASE Connect
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
			int choice = JOptionPane.showConfirmDialog((Main) event.getSource(), "Are you sure you want to quit?", "Exit VASE Connect?", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
			if (choice == JOptionPane.YES_OPTION)
			{
				((Main) event.getSource()).engine.quit();
			}
		}
	}
}
