/**
 * Project_VASE Deploy Utility report package
 */
package vase.client.deploy.utils.report;

import java.io.File;

import javax.swing.filechooser.FileFilter;

/**
 * File filter for the JFileChooser dialog
 * @author James McNatt & Brenton Kapral
 * @version Project_VASE Deploy
 */
public class ReportGeneratorFileFilter extends FileFilter
{
	/**
	 * Traditional HTML extension
	 */
	public final static String HTML = "html";
	
	/**
	 * Modern HTM extension
	 */
	public final static String HTM = "htm";

	/* (non-Javadoc)
	 * @see javax.swing.filechooser.FileFilter#accept(java.io.File)
	 */
	@Override
	public boolean accept(File file)
	{
		if (file.isDirectory())
		{
			return true;
		}
		
		String ext = getExtension(file);
		
		if (ext != null)
		{
			if (ext.equals(HTM) || ext.equals(HTML))
			{
				return true;
			}
			
			else return false;
		}
		
		return false;
	}

	/* (non-Javadoc)
	 * @see javax.swing.filechooser.FileFilter#getDescription()
	 */
	@Override
	public String getDescription()
	{
		return "HyperText Markup Language Files";
	}
	
	/**
	 * Gets the extension of a file
	 * @param file the file
	 * @return the extension of the file
	 */
	public String getExtension(File file)
	{
		String ext = null;
		String name = file.getName();
		int index = name.lastIndexOf('.');
		
		if (index > 0 && index < name.length() - 1)
		{
			ext = name.substring(index + 1).toLowerCase();
		}
		
		return ext;
	}
}
