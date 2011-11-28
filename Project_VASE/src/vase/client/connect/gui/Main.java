/**
 * Project_VASE Connect gui package
 */
package vase.client.connect.gui;

import java.awt.BorderLayout;
import javax.swing.JTextArea;
import vase.client.List;
import vase.client.Panel;
import vase.client.Utilities;
import vase.client.Window;
import vase.client.connect.CommandEngine;
import vase.client.connect.list.ListDataRenderer;
import vase.client.connect.list.ListListener;
import vase.client.connect.ProjectConstraints;
import vase.client.connect.gui.refresh.RefreshThread;
import vase.client.connect.gui.refresh.RefreshWorker;

import com.vmware.vim25.mo.ServiceInstance;

/**
 * Main Gui/User interface presenting the VM list, power operation buttons, the 
 * launch button, and the menu bar.
 * @author James McNatt & Brenton Kapral
 * @version Project_VASE Connect
 */
public class Main extends Window implements ProjectConstraints
{
	private static final long serialVersionUID = 7837947212807014566L;
	
	private JTextArea jtaLog;
	
	/**
	 * CommandEngine instance
	 */
	public CommandEngine engine;
	
	/**
	 * Main List
	 */
	public List jListVMs;
	
	/**
	 * RefreshWorker
	 */
	public RefreshWorker worker;
	
	
	/**
	 * Main Constructor
	 * <br />
	 * Requires a ServiceInstance object passed from the LoginSplash, passed to the CommandEngine
	 * object created with this constructor.
	 * @param si the ServiceInstance representation of the connection to vCenter
	 */
	public Main(ServiceInstance si)
	{
		super("VASE Connect");
		setResizable(false);
		setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
		setSize(DIM_CONNECT_MAIN_CONTENT_PANE);
		getContentPane().setPreferredSize(DIM_CONNECT_MAIN_CONTENT_PANE);
		setLocationRelativeTo(null);
		setJMenuBar(new MenuBar(new ActionEventListener(this)));
		
		engine = new CommandEngine(si, this);
		
		makeItems();
		makePanels();
		addListeners();
		
		engine.populateList();
		
		pack();
		setVisible(true);
		LOG.write("Welcome to Project_VASE Connect");
		worker = new RefreshWorker(this);
	}
	
	/**
	 * Makes the attributes for this class
	 */
	private void makeItems()
	{		
		jListVMs = new List();
		jtaLog = Utilities.createMainTextArea();
		LOG.setWritableAreas(jtaLog, null);
	}
	
	/**
	 * Makes the panels for the GUI
	 */
	private void makePanels()
	{
		getContentPane().setLayout(new BorderLayout());
		
		Panel north = new MainNorthPanel();
		Panel center = new MainCenterPanel(jListVMs);
		Panel south = new MainSouthPanel(jtaLog);
		
		add(north, BorderLayout.NORTH);
		add(center, BorderLayout.CENTER);
		add(south, BorderLayout.SOUTH);
	}
	
	/**
	 * Adds the action listeners to the attributes in this class
	 */
	private void addListeners()
	{
		addWindowListener(new WindowListener());
		
		ListListener listener = new ListListener(jListVMs, engine);
		jListVMs.addMouseListener(listener);
		jListVMs.setCellRenderer(new ListDataRenderer());
	}
	
	/**
	 * Refreshes the main list
	 */
	public void startRefreshThread()
	{
		new RefreshThread(this).start();
	}
}