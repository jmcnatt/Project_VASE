/**
 * Project_VASE Client package
 */
package vase.client;

import java.awt.Component;

import javax.swing.JScrollPane;

/**
 * Custom scrollpane class.  Sets the border and scroll policies in the constructor
 * @author James McNatt & Brenton Kapral
 * @version Project_VASE
 */
public class ScrollPane extends JScrollPane
{
	private static final long serialVersionUID = -2065172216679919665L;

	/**
	 * Sets the Border to null
	 * Sets the policies
	 * @param component the passed component to view
	 */
	public ScrollPane(Component component)
	{
		super(component);
		setHorizontalScrollBarPolicy(HORIZONTAL_SCROLLBAR_AS_NEEDED);
		setVerticalScrollBarPolicy(VERTICAL_SCROLLBAR_ALWAYS);
		setBorder(null);
	}
}
