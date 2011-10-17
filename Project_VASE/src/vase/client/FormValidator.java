/**
 * Project_VASE Client package
 */
package vase.client;

import java.awt.Container;
import java.util.regex.Pattern;

import javax.swing.JOptionPane;

/**
 * Validates various fields in the GuiDeployWizard
 * @author James McNatt & Brenton Kapral
 * @version Project_VASE
 * @see GuiDeployWizard
 */
public class FormValidator
{
	/**
	 * Virtual Machine Name is invalid
	 */
	public static final int VM_NAME_INVALID = 1;
	
	/**
	 * Hostname is invalid
	 */
	public static final int HOSTNAME_INVALID = 2;
	
	/**
	 * Domain name is invalid
	 */
	public static final int DOMAIN_INVALID = 3;
	
	/**
	 * IP Address is invalid
	 */
	public static final int IP_ADDRESS_INVALID = 4;
	
	/**
	 * Subnet mask is invalid
	 */
	public static final int SUBNET_MASK_INVALID = 5;
	
	/**
	 * Default gateway is invalid
	 */
	public static final int DEFAULT_GATEWAY_INVALID = 6;
	
	/**
	 * DNS server address is invalid
	 */
	public static final int DNS_ADDRESS_INVALID = 7;
	
	/**
	 * All possible IPv4 subnet masks, excluding /0 and /32
	 * @see FormValidator#isValidSubnetMask(String)
	 */
	public static final String[] SUBNET_MASK_POSSIBILITIES = {
		"128.0.0.0",
		"192.0.0.0",
		"224.0.0.0",
		"240.0.0.0",
		"248.0.0.0",
		"252.0.0.0",
		"254.0.0.0",
		"255.0.0.0",
		"255.128.0.0",
		"255.192.0.0",
		"255.224.0.0",
		"255.240.0.0",
		"255.248.0.0",
		"255.252.0.0",
		"255.254.0.0",
		"255.255.0.0",
		"255.255.128.0",
		"255.255.192.0",
		"255.255.224.0",
		"255.255.240.0",
		"255.255.248.0",
		"255.255.252.0",
		"255.255.254.0",
		"255.255.255.0",
		"255.255.255.128",
		"255.255.255.192",
		"255.255.255.224",
		"255.255.255.240",
		"255.255.255.248",
		"255.255.255.252",
		"255.255.255.254"		
	};
	
	/**
	 * Validates an IP address in the GuiDeployWizard
	 * @param ip the IP address to check
	 * @return true if the IP address is valid
	 * @see GuiDeployWizard#actionPerformed(java.awt.event.ActionEvent)
	 */
	public static boolean isValidIPAddress(String ip)
	{
		String regex = "^(([1-9]|[1-9][0-9]|1[0-9][0-9]|2[0-4][0-9]|25[0-5])\\." +
					   "([0-9]|[1-9][0-9]|1[0-9][0-9]|2[0-4][0-9]|25[0-5])\\." + 
					   "([0-9]|[1-9][0-9]|1[0-9][0-9]|2[0-4][0-9]|25[0-5])\\." +
					   "([0-9]|[1-9][0-9]|1[0-9][0-9]|2[0-4][0-9]|25[0-5]))$";
		
		return Pattern.matches(regex, ip);
	}
	
	/**
	 * Validates a subnet mask against all possible subnet masks for an IPv4 network
	 * @param netmask the subnet mask to check
	 * @return true if the subnet mask is valid
	 * @see GuiDeployWizard#actionPerformed(java.awt.event.ActionEvent)
	 */
	public static boolean isValidSubnetMask(String netmask)
	{
		boolean valid = false;
		
		for (int i = 0; i < SUBNET_MASK_POSSIBILITIES.length; i++)
		{
			boolean match = Pattern.matches(SUBNET_MASK_POSSIBILITIES[i], netmask);
			if (match) valid = true;
		}
		
		return valid;
	}
	
	/**
	 * Validates a hostname for length and characters
	 * @param hostname the hostname to check
	 * @return true if the hostname is valid
	 * @see GuiDeployWizard#actionPerformed(java.awt.event.ActionEvent)
	 */
	public static boolean isValidHostname(String hostname)
	{
		String regex = "^[a-zA-Z0-9-]{1,15}$";
		return Pattern.matches(regex, hostname);
	}
	
	/**
	 * Validates a domain name
	 * @param domain the domain name to check
	 * @return true if the domain name is valid
	 * @see GuiDeployWizard#actionPerformed(java.awt.event.ActionEvent)
	 */
	public static boolean isValidDomain(String domain)
	{
		String regex = "^[a-zA-Z0-9.]{1,60}$";
		return Pattern.matches(regex, domain);
	}
	
	/**
	 * Validates a virtual machine before a virtual machine is created
	 * @param name the virtual machine name
	 * @return true if the virtual machine name is valid
	 * @see GuiDeployWizard
	 */
	public static boolean isValidVirtualMachineName(String name)
	{
		String regex = "^[a-zA-Z0-9.#_-]([a-zA-Z0-9.#_-]|\\s){1,30}[a-zA-Z0-9.#_-]";
		return Pattern.matches(regex, name);
	}
	
	/**
	 * Used to display an error message when an invalid property is entered
	 * @param messageCode the message code, customizing the error message
	 * @see FormValidator#VM_NAME_INVALID
	 * @see FormValidator#HOSTNAME_INVALID
	 * @see FormValidator#DOMAIN_INVALID
	 * @see FormValidator#IP_ADDRESS_INVALID
	 * @see FormValidator#SUBNET_MASK_INVALID
	 * @see FormValidator#DEFAULT_GATEWAY_INVALID
	 * @see FormValidator#DNS_ADDRESS_INVALID
	 */
	public static void showMessage(int messageCode, Container parent)
	{
		String message = "Error: ";
		
		switch (messageCode)
		{
			case VM_NAME_INVALID:
			{
				message += "Please enter a VM name containing no more than 32 characters, excluding special symbols";
				break;
			}
			
			case HOSTNAME_INVALID:
			{
				message += "Please enter a hostname less than 16 alphanumber characters";
				break;
			}
			
			case DOMAIN_INVALID:
			{
				message += "Please enter a valid domain name";
				break;
			}
			
			case IP_ADDRESS_INVALID:
			{
				message += "Please enter a valid IP address";
				break;
			}
			
			case SUBNET_MASK_INVALID:
			{
				message += "Please enter a valid subnet mask";
				break;
			}
			
			case DEFAULT_GATEWAY_INVALID:
			{
				message += "Please enter a valid default gateway address";
				break;
			}
			
			case DNS_ADDRESS_INVALID:
			{
				message += "Please enter a valid DNS server address";
				break;
			}
		}
		
		JOptionPane.showMessageDialog(parent, message + "\nSee \"Help\" for more information", 
				"Error: Invalid Form Field", JOptionPane.ERROR_MESSAGE);
	}
}
