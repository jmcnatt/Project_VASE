/**
 * Project_VASE Deploy package
 */
package vase.client.deploy;

import java.awt.Dialog;
import java.awt.Frame;
import java.awt.image.BufferedImage;

import javax.imageio.ImageIO;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.UIManager;

/**
 * Dialog superclass for all properties dialog boxes and prompts
 * @author James McNatt & Brenton Kapral
 * @version Project_VASE Deploy
 */
public class GuiDialog extends JDialog implements GuiConstraints, ProjectConstraints
{
	private static final long serialVersionUID = 2309855462801751018L;
	
	/**
	 * Main Constructor. Sets the modality type
	 * @param owner the parent frame
	 */
	public GuiDialog(Frame owner)
	{
		super(owner);
		this.setModalityType(Dialog.ModalityType.APPLICATION_MODAL);
		BufferedImage image = null;
		try
		{
			image = ImageIO.read(getClass().getResource("/images/icon.png"));
			setIconImage(image);
			UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
		}
		
		catch (Exception e)
		{
			LOG.write("Unable to load WindowsUI\n" + e.getStackTrace(), true);
		}
	}
	
	/**
	 * Creates a JButton
	 * @param text the caption on the button
	 * @return button a formatted JButton for Dialogs
	 */
	public JButton createDialogButton(String text)
	{
		JButton button = new JButton(text);
		button.setPreferredSize(DIM_DEPLOY_BUTTON);
		button.setMaximumSize(DIM_DEPLOY_BUTTON);
		button.setMinimumSize(DIM_DEPLOY_BUTTON);
		
		return button;
	}
}