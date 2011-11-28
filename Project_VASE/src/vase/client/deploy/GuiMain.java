/**
 * Project_VASE Deploy package
 */
package vase.client.deploy;

import java.awt.Container;
import javax.swing.JTextArea;
import javax.swing.SpringLayout;

import vase.client.List;
import vase.client.Panel;
import vase.client.ScrollPane;
import vase.client.Utilities;
import vase.client.Window;

import com.vmware.vim25.mo.ServiceInstance;

/**
 * Main GUI Window
 * <br />
 * Displays the frame containing the menu bar, tabbed pane, and the main log.
 * @author James McNatt & Brenton Kapral
 * @version Project_VASE Deploy
 */
public class GuiMain extends Window implements ProjectConstraints
{
	private static final long serialVersionUID = 4689787423690447894L;
	
	//Swing components
	
	/**
	 * List components displayed on the GuiMain
	 */
	public List jListTemplates, jListVMs;
	
	/**
	 * Tree view of the datacenter
	 */
	public Tree datacenterTree;
	
	/**
	 * Main Log on all views
	 */
	public JTextArea jtaMainLog;
	
	/**
	 * System log on the System Log tab
	 */
	public JTextArea jtaSystemLog;
	
	/**
	 * Virtual Machine stats table
	 */
	public Table vmTable;
	
	/**
	 * Panel containing all tabs
	 */
	public TabbedPane tabbedPane;
	
	/**
	 * Command Engine used to issue commands to the VMware vCenter Server
	 */
	public CommandEngine engine;
	
	/**
	 * Refresh worker
	 */
	public RefreshWorker worker;
	
	/**
	 * Main Constructor that builds the GUI window
	 * @param title
	 */
	public GuiMain(ServiceInstance si)
	{
		super("VASE Deploy");
		setResizable(true);
		setSize(DIM_MAIN_CONTENT_PANE);
		getContentPane().setPreferredSize(DIM_MAIN_CONTENT_PANE);
		getContentPane().setMaximumSize(DIM_MAIN_CONTENT_PANE);
		getContentPane().setMinimumSize(DIM_MAIN_CONTENT_PANE);
		getContentPane().setBackground(COLOR_MAIN_BACKGROUND);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
		
		engine = new CommandEngine(si, this);
		
		makeItems();
		setJMenuBar(new GuiMenuBar(new GuiActionListener(this)));
		makePanels();
		addListeners();
		
		//Set Visible
		setVisible(true);
		LOG.write("Welcome to Project_VASE Deploy");
		worker = new RefreshWorker(this);
	}
	
	/**
	 * Creates object components used, called with main constructor
	 * @see GuiMain#GuiMain(String, CommandEngine)
	 */
	private void makeItems()
	{
		jListTemplates = new List(engine.getListTemplates());
		jListVMs = new List(engine.getListVMs());
		datacenterTree = new Tree(engine);
		jtaMainLog = Utilities.createMainTextArea();
		jtaSystemLog = Utilities.createMainTextArea();
		vmTable = new Table(engine);	
		LOG.setWritableAreas(jtaMainLog, jtaSystemLog);
	}
	
	/**
	 * Makes the panels used in the GuiMain layout
	 * <br />
	 * Makes the tabbedPane model for view-switching
	 * @see GuiMain#GuiMain(String, CommandEngine)
	 */
	private void makePanels()
	{
		Container main = this.getContentPane();
		Panel jpNorth = new Panel(new SpringLayout(), false, DIM_MAIN_CONTENT_ACTIVE_PANE);
		Panel jpSouth = new Panel(new SpringLayout(), false, DIM_MAIN_CONTENT_LOG);
		ScrollPane jspLog = new ScrollPane(jtaMainLog);
		
		//North Pane - containing tabbedPane		
		SpringLayout northLayout = (SpringLayout) jpNorth.getLayout();
		Tab summaryTab = new GuiSummaryTab(datacenterTree, jListTemplates, jListVMs);
		Tab vmTab = new GuiVirtualMachineTab(vmTable);
		Tab logTab = new GuiSystemLogTab(jtaSystemLog);
		Tab deployTab = new GuiDeploymentTab();
		Tab helpTab = new GuiHelpTab();
		tabbedPane = new TabbedPane(summaryTab, vmTab, deployTab, logTab, helpTab);
		engine.setDeploymentTab(deployTab);

		jpNorth.add(tabbedPane);
		
		northLayout.putConstraint(SpringLayout.NORTH, tabbedPane, 0, SpringLayout.NORTH, jpNorth);
		northLayout.putConstraint(SpringLayout.SOUTH, tabbedPane, 0, SpringLayout.SOUTH, jpNorth);
		northLayout.putConstraint(SpringLayout.EAST, tabbedPane, 0, SpringLayout.EAST, jpNorth);
		northLayout.putConstraint(SpringLayout.WEST, tabbedPane, 0, SpringLayout.WEST, jpNorth);
		
		//South Pane - containing log
		SpringLayout southLayout = (SpringLayout) jpSouth.getLayout();
	
		jpSouth.add(jspLog);
	
		southLayout.putConstraint(SpringLayout.NORTH, jspLog, 0, SpringLayout.NORTH, jpSouth);
		southLayout.putConstraint(SpringLayout.SOUTH, jspLog, 0, SpringLayout.SOUTH, jpSouth);
		southLayout.putConstraint(SpringLayout.EAST, jspLog, -3, SpringLayout.EAST, jpSouth);
		southLayout.putConstraint(SpringLayout.WEST, jspLog, 0, SpringLayout.WEST, jpSouth);
		
		//Add to main container
		main.setLayout(new SpringLayout());
		main.add(jpNorth);
		main.add(jpSouth);
		
		SpringLayout layout = (SpringLayout) main.getLayout();
		layout.putConstraint(SpringLayout.SOUTH, jpSouth, 0, SpringLayout.SOUTH, main);
		layout.putConstraint(SpringLayout.EAST, jpSouth, 0, SpringLayout.EAST, main);
		layout.putConstraint(SpringLayout.WEST, jpSouth, 0, SpringLayout.WEST, main);
		layout.putConstraint(SpringLayout.NORTH, jpNorth, 0, SpringLayout.NORTH, main);
		layout.putConstraint(SpringLayout.EAST, jpNorth, 0, SpringLayout.EAST, main);
		layout.putConstraint(SpringLayout.WEST, jpNorth, 0, SpringLayout.WEST, main);
		layout.putConstraint(SpringLayout.SOUTH	, jpNorth, 0, SpringLayout.NORTH, jpSouth);
	}
	
	/**
	 * Adds the action listeners to the attributes
	 */
	private void addListeners()
	{
		ListIconRenderer renderer = new ListIconRenderer(jListTemplates, jListVMs);
		ListListener listener = new ListListener(jListTemplates, jListVMs, this);

		jListTemplates.setCellRenderer(renderer);
		jListVMs.setCellRenderer(renderer);
		jListTemplates.addListSelectionListener(listener);
		jListVMs.addListSelectionListener(listener);
		jListTemplates.addMouseListener(listener);
		jListVMs.addMouseListener(listener);
		
		addWindowListener(new GuiWindowListener());
	}
	
	/**
	 * Starts a refresh thread that will refresh the GUI
	 * @see RefreshThread
	 */
	public void startRefreshThread()
	{
		new RefreshThread(this).start();
	}
}
