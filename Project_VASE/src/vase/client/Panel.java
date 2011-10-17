/**
 * Project_VASE Client package
 */
package vase.client;

import java.awt.Dimension;
import java.awt.LayoutManager;

import javax.swing.JPanel;

/**
 * Superclass for panels in the DeployWizard
 * @author James McNatt & Brenton Kapral
 * @version Project_VASE
 */
public class Panel extends JPanel implements InterfaceConstraints
{
	private static final long serialVersionUID = 94645707954332962L;
	
	/**
	 * Main Constructor
	 * @param manager the layout manager
	 * @param isOpaque opaque if true
	 */
	public Panel(LayoutManager manager, boolean isOpaque)
	{
		super(manager);
		setOpaque(isOpaque);
	}
	
	/**
	 * Overloaded constructor adding the preferred size dimension
	 * @param manager the layout manager
	 * @param isOpaque opaque if true
	 * @param preferredSize the preferred size of the panel
	 */
	public Panel(LayoutManager manager, boolean isOpaque, Dimension preferredSize)
	{
		super(manager);
		setOpaque(isOpaque);
		setPreferredSize(preferredSize);
	}
}
