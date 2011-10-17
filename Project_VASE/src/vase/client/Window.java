/**
 * Project_VASE Client package
 */
package vase.client;

import java.awt.image.BufferedImage;

import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.UIManager;

/**
 * GUI Window superclass
 * <br />
 * Contains methods for creating formatted Swing components and sets the UIManager
 * values for various components
 * @author James McNatt & Brenton Kapral
 * @version Project_VASE
 */

public class Window extends JFrame implements InterfaceConstraints
{
	private static final long serialVersionUID = 2918402213330301127L;

	/**
	 * Default constructor calling the superclass JFrame's constructor
	 * <br />
	 * Sets the Program Icon and the UIManager selection
	 * @param title the title of the window
	 */
	public Window(String title)
	{
		super(title);
		BufferedImage image = null;
		try
		{
			image = ImageIO.read(getClass().getResource("/images/icon.png"));
			setIconImage(image);
			UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
		}
		
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}
}
