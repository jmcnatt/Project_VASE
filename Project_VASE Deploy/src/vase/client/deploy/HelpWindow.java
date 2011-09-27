/**
 * Project_VASE Deploy package
 */
package vase.client.deploy;

import java.awt.Desktop;
import java.awt.GraphicsEnvironment;
import java.io.IOException;
import java.net.URISyntaxException;

import javax.swing.JEditorPane;
import javax.swing.JOptionPane;
import javax.swing.SpringLayout;
import javax.swing.event.HyperlinkEvent;
import javax.swing.event.HyperlinkListener;

/**
 * Help file viewer, using a JEditorPane to view the html help file in the html directory
 * @author James McNatt & Brenton Kapral
 * @version Project_VASE Deploy
 */
public class HelpWindow extends GuiWindow implements HyperlinkListener
{
	private static final long serialVersionUID = -6778508140106740255L;
	private JEditorPane viewer;
	
	/**
	 * Main constructor
	 * <br />
	 * Builds the help window in the top right-hand corner of the screen
	 */
	public HelpWindow()
	{
		super("VASE Deploy Help");
		setResizable(false);
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		setSize(DIM_HELP_CONTENT_PANE);
		getContentPane().setPreferredSize(DIM_HELP_CONTENT_PANE);
		getContentPane().setMinimumSize(DIM_HELP_CONTENT_PANE);
		getContentPane().setMaximumSize(DIM_HELP_CONTENT_PANE);
		setLocation((int) (GraphicsEnvironment.getLocalGraphicsEnvironment().getMaximumWindowBounds().getWidth() - (getWidth() + 15)), 5);

		makeItems();
		makePanels();
		
		pack();
		setVisible(true);
	}
	
	/**
	 * Makes the attributes for this class
	 */
	private void makeItems()
	{
		try
		{
			viewer = new JEditorPane(getClass().getResource("/html/getting_started.html"));
			viewer.addHyperlinkListener(this);
			viewer.setEditable(false);
		}
		
		catch (IOException e)
		{
			LOG.write("Error loading help file");
			LOG.printStackTrace(e);
			
			e.printStackTrace();
		}
	}
	
	/**
	 * Makes the panels for this GUI Window
	 */
	private void makePanels()
	{
		getContentPane().setLayout(new SpringLayout());
		SpringLayout layout = (SpringLayout) getContentPane().getLayout();
		ScrollPane pane = new ScrollPane(viewer);
		
		add(pane);
		
		layout.putConstraint(SpringLayout.NORTH, pane, 0, SpringLayout.NORTH, getContentPane());
		layout.putConstraint(SpringLayout.SOUTH, pane, 0, SpringLayout.SOUTH, getContentPane());
		layout.putConstraint(SpringLayout.EAST, pane, 0, SpringLayout.EAST, getContentPane());
		layout.putConstraint(SpringLayout.WEST, pane, 0, SpringLayout.WEST, getContentPane());
	}
	
	/**
	 * Hyperlink listener to parse bookmarks on HTML help file
	 * @param event the hyperlink click event
	 */
	public void hyperlinkUpdate(HyperlinkEvent event) 
	{
		if (event.getEventType() == HyperlinkEvent.EventType.ACTIVATED) 
	    {
	    	try 
	    	{
	    		if (!event.getURL().toString().contains("http"))
	    		{
	    			viewer.setPage(event.getURL());
	    		}
	    		
	    		else
	    		{
	    			//Launch Default Browser
	    			
	    			if (!Desktop.isDesktopSupported())
	    			{
	    				LOG.write("Desktop system is not supported, cannot launch external link");
	    				JOptionPane.showMessageDialog(this, "Cannot launch external link", "Error", JOptionPane.ERROR_MESSAGE);
	    			}
	    			
	    			else
	    			{
	    				Desktop desktop = Desktop.getDesktop();
	    				
	    				if (!desktop.isSupported(Desktop.Action.BROWSE))
	    				{
	    					LOG.write("Desktop doesn't support the BROWSE Action");
	    					JOptionPane.showMessageDialog(this, "Cannot launch external link, browse action not supported", "Error", JOptionPane.ERROR_MESSAGE);
	    				}
	    				
	    				else
	    				{
	    					desktop.browse(event.getURL().toURI());
	    				}
	    			}
	    		}
	    	} 
	    	
	    	catch (IOException e) 
	    	{
	    		LOG.printStackTrace(e);
	    		
	    		LOG.write("Error parsing hyperlink in help file");
	    	}
	    	
			catch (URISyntaxException e)
			{
				LOG.printStackTrace(e);
				
				e.printStackTrace();
			}
	    }
	}
}
