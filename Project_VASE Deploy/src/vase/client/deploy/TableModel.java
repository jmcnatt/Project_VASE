/**
 * Project_VASE Deploy package
 */
package vase.client.deploy;

import java.util.Vector;

import javax.swing.table.AbstractTableModel;

/**
 * Custom table model for the Virtual Machine tab on GuiMain
 * <br />
 * This model includes two additional constructors and an additional setValueAt
 * method which will create a new row in the table if the row index is out of bounds.
 * 
 * @author James McNatt & Brenton Kapral
 * @version Project_VASE Deploy
 */

public class TableModel extends AbstractTableModel implements GuiConstraints
{
	private static final long serialVersionUID = 8413149414045831647L;
	private String[] columnNames;				//The column Names - edit ProjectConstraints to change
	private Vector<Object[]> data;				//Collection of Object[] (each row)
	
	/**
	 * Default Constructor
	 * <br />
	 * columnNames must be defined using the mutator method, blank vector created
	 */
	public TableModel()
	{
		columnNames = null;
		data = new Vector<Object[]>();
	}
	
	/**
	 * One-param Constructor
	 * <br />
	 * Takes in columnNames, black vector created
	 * @param columnNames the column headings from ProjectConstraints
	 */
	public TableModel(String[] columnNames)
	{
		this.columnNames = columnNames;
		data = new Vector<Object[]>();
	}
	
	/**
	 * Main constructor
	 * <br />
	 * Takes in both the data and the column headings
	 * @param columnNames the column headings from ProjectConstraints
	 * @param data the vector of rows, or Object[]
	 */
	public TableModel(String[] columnNames, Vector<Object[]> data)
	{
		this.columnNames = columnNames;
		this.data = data;
	}
	
	/**
	 * Sets the columnNames, must be called if using the default constructor
	 * @param columnNames the column headings from ProjectConstraints
	 */
	public void setHeadings(String[] columnNames)
	{
		this.columnNames = columnNames;
	}
	
	/**
	 * Gets the number of rows by getting the size of the Vector
	 * @return the number of rows in the table
	 */
	public int getRowCount()
	{
		return data.size();
	}
	
	/**
	 * Gets the number of columns by looking at the columnNames attribute
	 * @return the number of columns in the table
	 */
	public int getColumnCount()
	{
		return columnNames.length;
	}
	
	/**
	 * Gets a specific value at any given intersection of rows and columns in
	 * the table
	 * @param rowIndex the row number, beginning at 0
	 * @param columnIndex, the column number, beginning at 0
	 * @return
	 */
	public Object getValueAt(int rowIndex, int columnIndex)
	{
		Object[] objects = data.get(rowIndex);
		return objects[columnIndex];
	}
	
	/**
	 * Updated setValueAt method.  Sets an Object at any given intersection of
	 * rows and columns in the table
	 * <br />
	 * If the vector size is the same as the row index (meaning the row index
	 * is out of bounds, a blank row with an Object[] the length of the
	 * COLUMN_HEADINGS variable in the Globals interface is created.
	 * <br />
	 * <strong>Note: </strong>This method is called by the RefreshThread class
	 * @param rowIndex the row number, beginning at 0
	 * @param columnIndex, the column number, beginning at 0
	 * @param object the object to place in the table
	 */
	public void setValueAt(int rowIndex, int columnIndex, Object object)
	{
		if (data.size() == rowIndex)
		{
			Object[] values = new Object[COLUMN_HEADINGS.length];
			data.add(values);
		}
		
		data.get(rowIndex)[columnIndex] = object;
		
	}
	
	/**
	 * Gets the Class of the objects in a column
	 * @param columnIndex the column number, beginning at 0
	 * @return the class of the objects in a column
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public Class getColumnClass(int columnIndex)
	{
		Object[] objects = data.get(0);
		return (objects == null?Object.class:objects.getClass());
		//return objects[columnIndex].getClass();
	}
	
	/**
	 * Gets the name of the column, using the heading
	 * @param columnIndex the column number, beginning at 0
	 * @return the name of the specified column
	 */
	public String getColumnName(int columnIndex)
	{
		return columnNames[columnIndex];
	}
	
	/**
	 * Adds a row to the table
	 * @param row the row to be added
	 */
	public void addRow(Object[] row)
	{
		data.add(row);
	}
	
	/**
	 * Removes a row in the table
	 * @param index the row number to be removed
	 */
	public void removeRow(int index)
	{
		data.remove(index);
	}
	
	/**
	 * Removes all rows in the table
	 */
	public void removeAllRows()
	{
		data.clear();
	}
	
	/**
	 * Takes the information in the Vector of Object[] and returns an
	 * Object[][] for use in comparing it to the engine's gatherData()
	 * @return the built table, an Object[][]
	 */
	public Object[][] gatherData()
	{
		Object[][] returnData = new Object[data.size()][COLUMN_HEADINGS.length];
		for (int i = 0; i < data.size(); i++)
		{
			Object[] thisRow = data.get(i);
			for (int j = 0; j < thisRow.length; j++)
			{
				returnData[i][j] = thisRow[j];
			}
		}
		
		return returnData;
	}
}