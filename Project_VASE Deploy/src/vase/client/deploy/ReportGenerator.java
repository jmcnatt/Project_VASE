/**
 * Project_VASE Deploy package
 */
package vase.client.deploy;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * Generates a report based on an ArrayList of DeployedVirtualMachine objects
 * Takes in a File representing the output of this generator
 * @author James McNatt & Brenton Kapral
 * @version Project_VASE Deploy
 */
public class ReportGenerator implements ProjectConstraints
{
	PrintWriter writer = null;
	
	/**
	 * Location of the CSV files on the remote server
	 */
	public static final String LOCATION = "\\share\\users\\";
	
	/**
	 * Makes the report file
	 * @param file the destination file (.html)
	 * @param deployed the collection of DeployedVirtualMachine objects
	 * @param engine the CommandEngine to get the server url from
	 * @throws IOException catch this exception when calling this method
	 */
	public void makeReport(File file, ArrayList<DeployedVirtualMachine> deployed, CommandEngine engine) 
		throws IOException
	{        
        //Create Writer
        writer = new PrintWriter(new BufferedWriter(new FileWriter(file)));
        buildHeader();
        buildBody(deployed, engine);
        buildCloser();
	}
	
	/**
	 * Builds the header of the html file, also contains the stylesheet
	 * @throws IOException
	 */
	private void buildHeader() throws IOException
	{
		writer.println("<!DOCTYPE html PUBLIC \"-//W3C//DTD XHTML 1.0 Transitional//EN\"");
		writer.println("\"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd\">");
		writer.println("<html>");
		writer.println("<head>");
		writer.println("<title>Project_VASE Deployment Report</title>");
		writer.println("<style type=\"text/css\">");
		writer.println("table.main {");
		writer.println("margin-left: auto;");
		writer.println("margin-right: auto; }");
		writer.println("td, th, h1, h2, font {");
		writer.println("font-family: verdana, helvetica, sans-serif;");
		writer.println("color: black; }");
		writer.println("th {");
		writer.println("background: #DDDDDD;");
		writer.println("text-align: center;");
		writer.println("font-size: 24pt; }");
		writer.println("h1 {");
		writer.println("font-size: 14pt; }");
		writer.println("h2 {");
		writer.println("font-size: 12pt; }");
		writer.println("td {");
		writer.println("font-size: 10pt; }");
		writer.println(".footer {");
		writer.println("font-size: 8pt;");
		writer.println("text-align: center; }");
		writer.println("</style>");
		writer.println("</head>");
		writer.flush();
	}
	
	/**
	 * Builds the body of the html file using the list of DeployedVirtualMachine objects
	 * @param deployed the collection of DeployedVirtualMachine objects
	 * @param engine the CommandEngine to get the URL for the remote server
	 * @throws IOException
	 */
	private void buildBody(ArrayList<DeployedVirtualMachine> deployed, CommandEngine engine) 
		throws IOException
	{
		writer.println("<body>");
		writer.println("<table class=\"main\" width=\"850\" border=\"1\" cellpadding=\"10\" cellspacing=\"0\">");
		writer.println("<tr>");
		writer.println("<th>Project_VASE Deployment Report</th>");
		writer.println("</tr>");
		writer.println("<tr>");
		writer.println("<td>");
		
		if (!deployed.get(0).getTeam().equalsIgnoreCase("< Not Set >"))
		{
			writer.println("<h1>" + deployed.get(0).getTeam() + "</h1>");
		}

		writer.println("Number of VMs: " + deployed.size());
		writer.println("</td>");
		writer.println("</tr>");
		writer.flush();
		
		//Load Table Row for each entry
		for (DeployedVirtualMachine each : deployed)
		{
			writer.println("<tr>");
			writer.println("<td>");
			writer.println("<h2>" + each.getVmName() + "</h2>");
			writer.println("Operating System: " + each.getOsName() + "<br />");
			writer.println("Hostname: " + each.getHostName() + "<br />");
			writer.println("Domain: " + each.getDomain() + "<br />");
			writer.println("IP Address: " + each.getIpAddr() + "<br />");
			writer.println("Subnet Mask: " + each.getNetmask() + "<br />");
			writer.println("Default Gateway: " + each.getDefaultGateway() + "<br />");
			writer.println("DNS Server: " + each.getDefaultGateway() + "<br />");
			writer.println("Services: " + each.getServices() + "<br />");
			writer.println("Load Exploits: " + (each.isExploits() ? "Yes" : "No") + "<br />");
			writer.println("Load Accounts: " + (each.isAccounts()? "Yes" : "No") + "<br />");
			writer.println("<br />");
			
			if (each.isAccounts())
			{
				Scanner reader = null;
				try
				{
					File csv = new File("\\\\" + engine.getCurrentServer() + LOCATION + 
							"users" + each.getAccountsCSV() + ".csv");
					reader = new Scanner(new BufferedReader(new FileReader(csv)));
					writer.println("<strong>User Account List:</strong><br />");
					writer.println("<table width=\"250\" border=\"0\" cellpadding=\"5\">");
					
					while (reader.hasNextLine())
					{
						String[] values = reader.nextLine().split(",");
						writer.println("<tr>");
						writer.println("<td>");
						writer.println(values[0]);
						writer.println("</td>");
						writer.println("<td>");
						writer.println(values[1]);
						writer.println("</td>");
						writer.println("</tr>");
					}
					
					writer.println("</table>");
				}
				
				catch (ArrayIndexOutOfBoundsException e)
				{
					LOG.write(each.getVmName() + ": CSV file #" + each.getAccountsCSV()+ " not formatted correctly", true);
					e.printStackTrace();
				}
				
				catch (FileNotFoundException e)
				{
					LOG.write(each.getVmName() + ": CSV file not found for report generation", true);
					e.printStackTrace();
				}
				
				catch (Exception e)
				{
					LOG.write(each.getVmName() + ": Error reading csv for report generation", true);
					e.printStackTrace();
				}
				
				finally
				{
					if (reader != null)
					{
						reader.close();
					}
				}
			}
			
			writer.println("<br />");
			writer.println("<strong>Administrator credentials:</strong><br />");
			writer.println("<table width=\"250\" border=\"0\" cellpadding=\"5\">");
			
			if (each.getOsCategory().equals(WINDOWS))
			{	
				writer.println("<tr>");
				writer.println("<td>");
				writer.println("Administrator");
				writer.println("</td>");
				writer.println("<td>");
				writer.println(DEFAULT_WINDOWS_PASSWORD);
				writer.println("</td>");
				writer.println("</tr>");
			}
			
			else
			{
				writer.println("root&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;" + DEFAULT_LINUX_PASSWORD);
			}
			
			writer.println("</table>");
			writer.println("</td>");
			writer.println("</tr>");
			writer.flush();
		}
	}
	
	/**
	 * Builds the closing portion of the html file
	 */
	private void buildCloser()
	{
		writer.println("</table>");
		writer.println("<p class=\"footer\">Copyright (c) 2011 Rochester Institute of Technology</p>");
		writer.println("</body>");
		writer.println("</html>");
		writer.flush();
	}
}
