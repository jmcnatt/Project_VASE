/**
 * Project_VASE Client package
 */
package vase.client;

import java.io.File;
import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import javax.swing.JTextArea;

/**
 * Main Log and Event writer
 * <br />
 * Responsible for writing to the visible log displayed on a Window, the SystemLog
 * on the System Log tab in the Main, and the Log File
 * @author James McNatt & Brenton Kapral
 * @version Project_VASE
 */
public class LogWriter
{
	private File logFile;
	private PrintWriter writer;
	private boolean mainLogEnabled = true;
	private boolean systemLogEnabled = true;
	private boolean logFileEnabled = true;
	private JTextArea jtaMain;
	private JTextArea jtaSystem;
	private Calendar cal;
	private SimpleDateFormat sdf;
	
	private static final String DATE_FORMAT = "HH:mm:ss:SSS";
	
	/**
	 * Default Constructor
	 * <br />
	 * Assigns logFile based on deploy.conf, sets up the OutputStreamWriter
	 */
	public LogWriter(String location)
	{
			sdf = new SimpleDateFormat(DATE_FORMAT);
			jtaMain = new JTextArea();
			jtaSystem = new JTextArea();
			logFile = new File(location);
			
			FileOutputStream fos = null;
			
			try
			{
				fos = new FileOutputStream(logFile, true);
			}
			
			catch (Exception e)
			{
				e.printStackTrace();
				try
				{
					fos  = new FileOutputStream(new File("events.log"), true);
				}
				
				catch (Exception f)
				{
					System.err.println(getTime() + "\nError in creating the log.");
				}
			}
			
			writer = new PrintWriter(fos);
			write("------------------------------------------------------------------------");
			write("Program operation began");
	}
	
	/**
	 * Assigned the instance of the JTextAreas displaying the logs on a Window
	 * to the variables called by this log writer, allowing the logs to be displayed.
	 * @param main the main event viewer on the GuiMain
	 * @param system the system log
	 */
	public void setWritableAreas(JTextArea main, JTextArea system)
	{
		if (main != null) jtaMain = main;
		if (system != null) jtaSystem = system;
	}
	
	/**
	 * Sets whether or not to write to the MainLog
	 * @param mainLogEnabled the mainLogEnabled to set
	 */
	public void setMainLogEnabled(boolean mainLogEnabled)
	{
		this.mainLogEnabled = mainLogEnabled;
	}

	/**
	 * Sets whether or not to write to the SystemLog
	 * @param systemLogEnabled the systemLogEnabled to set
	 */
	public void setSystemLogEnabled(boolean systemLogEnabled)
	{
		this.systemLogEnabled = systemLogEnabled;
	}

	/**
	 * Sets whether or not to write to the LogFile
	 * @param logFileEnabled the logFileEnabled to set
	 */
	public void setLogFileEnabled(boolean logFileEnabled)
	{
		this.logFileEnabled = logFileEnabled;
	}

	/**
	 * Writes to the MainLog, SystemLog, and the LogFile
	 * @param entry the log entry to write
	 */
	public synchronized void write(String entry)
	{
		final String logEntry = "[" + getTime() + "]  " + entry;
		
		if (mainLogEnabled)
		{	
			jtaMain.append(logEntry + "\n");
			jtaMain.setCaretPosition(jtaMain.getText().length());
		}
		
		if (systemLogEnabled)
		{
			jtaSystem.append(logEntry + "\n");
			jtaSystem.setCaretPosition(jtaSystem.getText().length());
		}
		
		if (logFileEnabled)
		{
			try
			{
				writer.println(logEntry);
				writer.flush();
			}
			
			catch (Exception e)
			{
				System.err.println(getTime() + "\nError in writing to the log.");
			}
		}
	}
	
	/**
	 * Writes to the SystemLog and the LogFile only, not the MainLog
	 * @param entry the log entry
	 * @param toSystemOnly boolean for not writing to the MainLog
	 */
	public synchronized void write(String entry, boolean toSystemOnly)
	{
		String logEntry = "[" + getTime() + "]  " + entry;
		
		if (toSystemOnly)
		{
			if (systemLogEnabled)
			{
				jtaSystem.append(logEntry + "\n");
				jtaSystem.setCaretPosition(jtaSystem.getText().length());
			}
		}
			
		if (logFileEnabled)
		{
			try
			{
				writer.println(logEntry);
				writer.flush();
			}
			
			catch (Exception e)
			{
				System.err.println(getTime() + "\nError in writing to the log.");
			}
		}		
	}
	
	/**
	 * Clears the LogFile
	 */
	public void clear()
	{
		PrintWriter clearer = null;
		
		try
		{
			clearer = new PrintWriter(logFile);
			clearer.println("");
		}
		
		catch (Exception e)
		{
			e.printStackTrace();
		}
		
		finally
		{
			if (clearer != null)
			{
				try
				{
					clearer.close();
				}
				
				catch (Exception e)
				{
					e.printStackTrace();
				}
			}
		}
	}
	
	/**
	 * Gets the formatted system time using the static final string DATE_FORMAT
	 * @return time the formatted system time
	 * @see LogWriter#DATE_FORMAT
	 */
	private String getTime()
	{
		cal = Calendar.getInstance();
		return sdf.format(cal.getTime());
	}
	
	/**
	 * Prints the stack trace of an exception to the log file
	 * @param e the exception whose stack trace to print
	 */
	public void printStackTrace(Exception e)
	{
		StringWriter sw = new StringWriter();
		PrintWriter writer = new PrintWriter(sw);
		writer.print("[" + e.getClass().getName() + "]");
		writer.print(e.getMessage() + " \n");
		e.printStackTrace(writer);
		write(sw.toString(), false);
	}
}
