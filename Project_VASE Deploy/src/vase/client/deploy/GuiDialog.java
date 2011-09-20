/**
 * Project_VASE Deploy package
 */
package vase.client.deploy;

import java.awt.Dialog;
import java.awt.Dimension;
import java.awt.Frame;
import java.awt.image.BufferedImage;

import javax.imageio.ImageIO;
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
	public GuiDialog(Frame owner, String title, Dimension preferredSize)
	{
		super(owner);
		setPreferredSize(preferredSize);
		setSize(preferredSize);
		setTitle(title);
		setModalityType(Dialog.ModalityType.APPLICATION_MODAL);
		setResizable(false);
		setLocationRelativeTo(null);
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
}