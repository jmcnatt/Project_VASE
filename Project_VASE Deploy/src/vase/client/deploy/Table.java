/**
 * Project_VASE Deploy package
 */
package vase.client.deploy;

import javax.swing.JTable;
import javax.swing.table.TableCellRenderer;

/**
 * Virtual Machine list table that displays data gathered from VirtualMachineExt objects
 * Contains a TableDataRenderer and a TableModel object, created with the constructor
 * @author James McNatt & Brenton Kapral
 * @version Proejct_VASE Deploy
 * @see JTable
 * @see TableDataRenderer
 * @see TableModel
 */
public class Table extends JTable implements GuiConstraints
{
	private static final long serialVersionUID = 5040222545937017817L;
	
	private TableDataRenderer renderer;
	private TableModel model;
	
	/**
	 * Creates the table
	 */
	public Table(CommandEngine engine)
	{
		//Create Features
		super();
		renderer = new TableDataRenderer();
		model = new TableModel(COLUMN_HEADINGS);
		
		//Set Properties
		setData(engine);
		setRowHeight(20);
		setFillsViewportHeight(true);
		setModel(model);
		setAutoResizeMode(AUTO_RESIZE_LAST_COLUMN);
		setColumnSelectionAllowed(false);
		
		//Set Column Widths
		getColumnModel().getColumn(0).setWidth(35);
		getColumnModel().getColumn(0).setMaxWidth(35);
		getColumnModel().getColumn(1).setWidth(125);
		getColumnModel().getColumn(1).setMaxWidth(125);
		getColumnModel().getColumn(2).setWidth(150);
		getColumnModel().getColumn(2).setMaxWidth(150);
		getColumnModel().getColumn(3).setWidth(75);
		getColumnModel().getColumn(3).setMaxWidth(75);
		getColumnModel().getColumn(4).setWidth(75);
		getColumnModel().getColumn(4).setMaxWidth(75);
		getColumnModel().getColumn(5).setWidth(75);
		getColumnModel().getColumn(5).setMaxWidth(75);
		getColumnModel().getColumn(6).setWidth(75);
		getColumnModel().getColumn(6).setMaxWidth(75);
		getColumnModel().getColumn(7).setWidth(75);
		getColumnModel().getColumn(7).setMaxWidth(75);
		getColumnModel().getColumn(8).setWidth(100);
		getColumnModel().getColumn(8).setMaxWidth(100);
		getColumnModel().getColumn(9).setWidth(125);
		getColumnModel().getColumn(9).setMaxWidth(125);
		
		//Add Listener
		this.addMouseListener(new TableListener(engine));
	}
	
	/**
	 * Returns the TableCellRenderer to render the table
	 * @return the object that renders the table
	 * @see JTable#getCellRenderer(int, int)
	 */
	@Override
	public TableCellRenderer getCellRenderer(int row, int col)
	{
		return renderer;
	}
	
	/**
	 * Gets the custom table model
	 * @see JTable#getModel()
	 * @see TableModel
	 */
	@Override
	public TableModel getModel()
	{
		return model;
	}
	
	private void setData(CommandEngine engine)
	{
		Object[][] engineData = engine.gatherData();
		
		for (int i = 0; i < engineData.length; i++)
		{
			Object[] rowData = engineData[i];
			for (int j = 0; j < rowData.length; j++)
			{
				getModel().setValueAt(i, j, rowData[j]);
			}
		}
		
		getModel().fireTableDataChanged();
	}
}
