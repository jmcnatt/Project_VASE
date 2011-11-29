/**
 * Project_VASE Deploy virtual machine object package
 */
package vase.client.deploy.vmo;

import vase.client.thread.ProcessErrorThread;
import vase.client.thread.ProcessInputThread;
import vase.client.deploy.CommandEngine;
import vase.client.deploy.ProjectConstraints;

/**
 * Generates a script based on the constructor.  Used to invoke customization scripts
 * on new virtual machines.  Edit the constants for script parameters and script paths.
 * @author James McNatt & Brenton Kapral
 * @version Project_VASE Deploy
 */
public class Script implements ProjectConstraints
{	
	//Supported Scripts
	
	/**
	 * This script compiles as a Networking script
	 * @see Script#WIN_NETWORKING
	 * @see Script#RPM_NETWORKING
	 * @see Script#DEBIAN_NETWORKING
	 * @see Script#BSD_NETWORKING
	 */
	public static final int NETWORKING = 0;
	
	/**
	 * This scirpt compiles as a Windows Active Directory script
	 * @see Script#WIN_ACTIVEDIRECTORY_03
	 * @see Script#WIN_ACTIVEDIRECTORY_08
	 */
	public static final int ACTIVEDIRECTORY = 1;
	
	/**
	 * This scirpt compiles as a DNS script
	 * @see Script#WIN_DNS
	 * @see Script#RPM_DNS
	 * @see Script#DEBIAN_DNS
	 * @see Script#BSD_DNS
	 */
	public static final int DNS = 2;
	
	/**
	 * This script compiles as a DHCP script
	 * @see Script#WIN_DHCP
	 * @see Script#RPM_DHCP
	 * @see Script#DEBIAN_DHCP
	 * @see Script#BSD_DHCP
	 */
	public static final int DHCP = 3;
	
	/**
	 * This script compiles as a Windows File Server script
	 * @see Script#WIN_FILESERV
	 */
	public static final int FILESERV = 4;
	
	/**
	 * This script compiles as a Windows IIS script
	 * @see Script#WIN_IIS
	 */
	public static final int IIS = 5;
	
	/**
	 * This script compiles as an NFS script
	 * @see Script#RPM_NFS
	 * @see Script#DEBIAN_NFS
	 * @see Script#BSD_NFS
	 */
	public static final int NFS = 6;
	
	/**
	 * This script compiles as an NIS script
	 * @see Script#RPM_NIS
	 * @see Script#DEBIAN_NIS
	 * @see Script#BSD_NIS
	 */
	public static final int NIS = 7;
	
	/**
	 * This script compiles as a MAIL script
	 * @see Script#RPM_MAIL
	 * @see Script#DEBIAN_MAIL
	 * @see Script#BSD_MAIL
	 */
	public static final int MAIL = 8;
	
	/**
	 * This script compiles as a SAMBA script
	 * @see Script#RPM_SAMBA
	 * @see Script#DEBIAN_SAMBA
	 * @see Script#BSD_SAMBA
	 */
	public static final int SAMBA = 9;
	
	/**
	 * This script compiles as an FTP script
	 * @see Script#RPM_FTP
	 * @see Script#DEBIAN_FTP
	 * @see Script#BSD_FTP
	 */
	public static final int FTP = 10;
	
	/**
	 * This script compiles as an HTTP script
	 * @see Script#RPM_HTTP
	 * @see Script#DEBIAN_HTTP
	 * @see Script#BSD_HTTP
	 */
	public static final int HTTP = 11;
	
	/**
	 * This script compiles as a "Bind to" script
	 * @see Script#WIN_JOIN
	 * @see Script#RPM_JOIN
	 * @see Script#DEBIAN_JOIN
	 * @see Script#BSD_JOIN
	 */
	public static final int BIND = 12;
	
	/**
	 * This script compiles as an Accounts script
	 * @see Script#WIN_ACCOUNTS
	 * @see Script#RPM_ACCOUNTS
	 * @see Script#DEBIAN_ACCOUNTS
	 * @see Script#BSD_ACCOUNTS
	 */
	public static final int ACCOUNTS = 13;
	
	/**
	 * This script compiles as an Exploits installer script
	 * @see Script#WIN_EXPLOITS
	 * @see Script#RPM_EXPLOITS
	 * @see Script#DEBIAN_EXPLOITS
	 * @see Script#BSD_EXPLOITS
	 */
	public static final int EXPLOITS = 14;
	
	/**
	 * This script compiles as a Windows cleanup script
	 * @see Script#WIN_CLEANUP
	 */
	public static final int CLEANUP = 15;
	
	//Guest OS
	
	/**
	 * Microsoft Windows distributions
	 */
	public static final int WINDOWS = 100;
	
	/**
	 * RPM-based Linux distributions
	 */
	public static final int LINUX_RPM = 101;
	
	/**
	 * Debian-based Linux distributions
	 */
	public static final int LINUX_DEBIAN = 102;
	
	/**
	 * BSD and FreeBSD distributions
	 */
	public static final int BSD = 103;
	
	//Invoke Components
	
	/**
	 * Powershell resource in the Windows path
	 */
	public static final String POWERSHELL = "powershell";
	
	/**
	 * Path to main invoke powershell script
	 */
	public static final String INVOKE = "scripts/invoke.ps1";
	
	/**
	 * Default Windows username on all template VMs.  Password read from the ConfigReader
	 * @see ConfigReader#getDefaultWindowsPassword()
	 */
	public static final String WINDOWS_DEFAULT_USERNAME = "Administrator";
	
	/**
	 * Default Linux/BSD username on all template VMs.  Password read from the ConfigReader
	 * @see ConfigReader#getDefaultLinuxPassword()
	 */
	public static final String LINUX_DEFAULT_USERNAME = "root";
	
	/**
	 * Default Windows script destination on the guest VM virtual hard disk
	 */
	public static final String WINDOWS_SCRIPT_DESTINATION = "C:\\";
	
	/**
	 * Default Linux/BSD script destination on the guest VM virtual hard disk
	 */
	public static final String LINUX_SCRIPT_DESTINATION = "/tmp/";
	
	//Script Types
	
	/**
	 * Bat script type for Windows guest VM scripts
	 */
	public static final String BAT_SCRIPTTYPE = "bat";
	
	/**
	 * Powershell script type for Windows guest VM scripts
	 */
	public static final String POWERSHELL_SCRIPTTYPE = "powershell";
	
	/**
	 * Bash script type for Linux guest VM scripts
	 * <br />
	 * Note: Perl scripts should be invoked as Bash-type scripts
	 */
	public static final String BASH_SCRIPTTYPE = "bash";
	
	//Script names
	
	/**
	 * Windows network config script name
	 */
	public static final String WIN_NETWORKING = "win-network.bat";
	
	/**
	 * Windows Server 2003 Active Directory installation script name
	 */
	public static final String WIN_ACTIVEDIRECTORY_03 = "win-03dc.bat";
	
	/**
	 * Windows Server 2008 Active Directory installation script name
	 */
	public static final String WIN_ACTIVEDIRECTORY_08 = "win-08dc.bat";
	
	/**
	 * Windows Join Active Directory script name
	 */
	public static final String WIN_JOIN = "win-ad-join.bat";
	
	/**
	 * Windows Server DNS script name
	 */
	public static final String WIN_DNS = "win-dns.bat";
	
	/**
	 * Windows Server DHCP script name
	 */
	public static final String WIN_DHCP = "win-dhcp.bat";
	
	/**
	 * Windows Server IIS script name
	 */
	public static final String WIN_IIS = "win-iis.bat";
	
	/**
	 * Windows Server File Server script name
	 */
	public static final String WIN_FILESERV = "win-fileservices.bat";
	
	/**
	 * Windows user account install script name
	 */
	public static final String WIN_ACCOUNTS = "win-user.bat";
	
	/**
	 * Windows exploits install script name
	 */
	public static final String WIN_EXPLOITS = "win-exploits.bat";
	
	/**
	 * Windows cleanup script name
	 */
	public static final String WIN_CLEANUP = "win-cleanup.bat";
	
	/**
	 * RPM-based Linux network config script name
	 */
	public static final String RPM_NETWORKING = "rpm-network.pl";
	
	/**
	 * RPM-based Linux DHCP script name
	 */
	public static final String RPM_DHCP = "rpm-install-dhcp.pl";
	
	/**
	 * RPM-based Linux DNS script name
	 */
	public static final String RPM_DNS = "rpm-install-dns.pl";
	
	/**
	 * RPM-based Linux NIS script name
	 */
	public static final String RPM_NIS = "rpm-install-nis.pl";
	
	/**
	 * RPM-based Linux NFS script name
	 */
	public static final String RPM_NFS = "rpm-install-nfs.pl";
	
	/**
	 * RPM-based Linux HTTP script name
	 */
	public static final String RPM_HTTP = "rpm-install-http.pl";
	
	/**
	 * RPM-based Linux FTP script name
	 */
	public static final String RPM_FTP = "rpm-install-ftp.pl";
	
	/**
	 * RPM-based Linux Mail script name
	 */
	public static final String RPM_MAIL = "rpm-install-mail.pl";
	
	/**
	 * RPM-based Linux Samba script name
	 */
	public static final String RPM_SAMBA = "rpm-install-samba.pl";
	
	/**
	 * RPM-based Linux Join to NIS script name
	 */
	public static final String RPM_JOIN = "rpm-nis-join.pl";
	
	/**
	 * RPM-based Linux install user accounts script name
	 */
	public static final String RPM_ACCOUNTS = "rpm-accounts.pl";
	
	/**
	 * RPM-based Linux install exploits script name
	 */
	public static final String RPM_EXPLOITS = "rpm-exploits.pl";
	
	/**
	 * Debian-based Linux network config script name
	 */
	public static final String DEBIAN_NETWORKING = "debian-network.pl";
	
	/**
	 * Debian-based Linux DHCP script name
	 */
	public static final String DEBIAN_DHCP = "debian-install-dhcp.pl";
	
	/**
	 * Debian-based Linux DNS script name
	 */
	public static final String DEBIAN_DNS = "debian-install-dns.pl";
	
	/**
	 * Debian-based Linux NIS script name
	 */
	public static final String DEBIAN_NIS = "debian-install-nis.pl";
	
	/**
	 * Debian-based Linux NFS script name
	 */
	public static final String DEBIAN_NFS = "debian-install-nfs.pl";
	
	/**
	 * Debian-based Linux HTTP script name
	 */
	public static final String DEBIAN_HTTP = "debian-install-http.pl";
	
	/**
	 * Debian-based Linux FTP script name
	 */
	public static final String DEBIAN_FTP = "debian-install-ftp.pl";
	
	/**
	 * Debian-based Linux Mail script name
	 */
	public static final String DEBIAN_MAIL = "debian-install-mail.pl";
	
	/**
	 * Debian-based Linux Samba script name
	 */
	public static final String DEBIAN_SAMBA = "debian-install-samba.pl";
	
	/**
	 * Debian-based Linux Join to NIS script name
	 */
	public static final String DEBIAN_JOIN = "debian-nis-join.pl";
	
	/**
	 * Debian-based Linux install accounts script name
	 */
	public static final String DEBIAN_ACCOUNTS = "debian-accounts.pl";
	
	/**
	 * Debian-based Linux install exploits script name
	 */
	public static final String DEBIAN_EXPLOITS = "debian-exploits.pl";
	
	/**
	 * BSD/FreeBSD network config script name
	 */
	public static final String BSD_NETWORKING = "bsd-network.pl";
	
	/**
	 * BSD/FreeBSD DHCP script name
	 */
	public static final String BSD_DHCP = "bsd-install-dhcp.pl";
	
	/**
	 * BSD/FreeBSD DNS script name
	 */
	public static final String BSD_DNS = "bsd-install-dns.pl";
	
	/**
	 * BSD/FreeBSD NIS script name
	 */
	public static final String BSD_NIS = "bsd-install-nis.pl";
	
	/**
	 * BSD/FreeBSD NFS script name
	 */
	public static final String BSD_NFS = "bsd-install-nfs.pl";
	
	/**
	 * BSD/FreeBSD HTTP script name
	 */
	public static final String BSD_HTTP = "bsd-install-http.pl";
	
	/**
	 * BSD/FreeBSD FTP script name
	 */
	public static final String BSD_FTP = "bsd-install-ftp.pl";
	
	/**
	 * BSD/FreeBSD Mail script name
	 */
	public static final String BSD_MAIL = "bsd-install-mail.pl";
	
	/**
	 * BSD/FreeBSD Samba script name
	 */
	public static final String BSD_SAMBA = "bsd-install-samba.pl";
	
	/**
	 * BSD/FreeBSD Join to NIS script name
	 */
	public static final String BSD_JOIN = "bsd-nis-join.pl";
	
	/**
	 * BSD/FreeBSD install accounts script name
	 */
	public static final String BSD_ACCOUNTS = "bsd-accounts.pl";
	
	/**
	 * BSD/FreeBSD install exploits script name
	 */
	public static final String BSD_EXPLOITS = "bsd-exploits.pl";
	
	//DeployedVirtualMachine object passed in
	private DeployedVirtualMachine newVM;
	
	//Script text
	private String args;								//Copied script args
	private String script;								//Copied script
	private String scriptType;							//Script type (ex: bat)
	private String scriptDestination;					//Destination directory on guest
	private String username;							//Guest VM Username
	private String password;							//Guest VM password
	private CommandEngine engine;						//CommandEngine
	
	/**
	 * Main Constructor that creates the full script to be executed
	 * <br />
	 * See the constants in this class for creating an object of this class
	 * @param newVM the DeployedVirtualMachine object
	 * @param guestOS the guest operating system category. The script syntax is based on this category.
	 * @param scriptCommand the script syntax and arguments
	 * @param engine the command engine instance
	 */
	public Script(DeployedVirtualMachine newVM, int guestOS, int scriptCommand, CommandEngine engine)
	{
		this.newVM = newVM;
		this.engine = engine;
		
		switch (guestOS)
		{
			//Windows Scripts
			case WINDOWS:
			{
				//Script types for Windows
				switch (scriptCommand)
				{
					//Windows Network Config
					case NETWORKING:
					{
						script = WIN_NETWORKING;
						args = String.format("-%s %s %s %s %d %s %s",
								newVM.isStaticAddress() ? "static" : "dhcp", newVM.getIpAddr(),
								newVM.getNetmask(), newVM.getDefaultGateway(), 1, newVM.getDnsServer(),
								newVM.getHostName());
						break;
					}
					
					//Windows Server Active Directory
					case ACTIVEDIRECTORY:
					{
						if (newVM.getOsName().contains("2003"))
						{
							script = WIN_ACTIVEDIRECTORY_03;
						}
						
						else if (newVM.getOsName().contains("2008"))
						{
							script = WIN_ACTIVEDIRECTORY_08;
						}
						
						args = String.format("%s", newVM.getDomain());
						break;
					}
					
					//Windows Server DNS
					case DNS:
					{
						script = WIN_DNS;
						args = "None";
						break;
					}
					
					//Windows Server DHCP
					case DHCP:
					{
						script = WIN_DHCP;
						args = "None";
						break;
					}
					
					//Windows File Server
					case FILESERV:
					{
						script = WIN_FILESERV;
						args = "None";
						break;
					}
					
					//Windows Server IIS
					case IIS:
					{
						script = WIN_IIS;
						args = "None";
						break;
					}
					
					//Load Windows accounts
					case ACCOUNTS:
					{
						script = WIN_ACCOUNTS;
						args = Integer.toString(newVM.getAccountsCSV());
						break;
					}
					
					case BIND:
					{
						script = WIN_JOIN;
						args = String.format("%s", newVM.getDomain());
						break;
					}
					
					//Load Windows exploits
					case EXPLOITS:
					{
						script = WIN_EXPLOITS;
						args = "None";
						break;
					}
					
					//Clean up Script for windows
					case CLEANUP:
					{
						script = WIN_CLEANUP;
						args = "None";
						break;
					}
				}
				
				username = WINDOWS_DEFAULT_USERNAME;
				password = DEFAULT_WINDOWS_PASSWORD;
				scriptType = BAT_SCRIPTTYPE;
				scriptDestination = WINDOWS_SCRIPT_DESTINATION;
				break;
			}
			
			//RPM-based linux
			case LINUX_RPM:
			{
				//Script types for RPM-based linux
				switch (scriptCommand)
				{
					//RPM-based linux network config
					case NETWORKING:
					{
						script = RPM_NETWORKING;
						args = String.format("-%s -addr %s -netmask %s -gw %s -hostname %s -domain %s -dns %s",
								newVM.isStaticAddress() ? "static" : "dhcp", newVM.getIpAddr(),
								newVM.getNetmask(), newVM.getDefaultGateway(), newVM.getHostName(), 
								newVM.getDomain(), newVM.getDnsServer());
						break;
					}
					
					//RPM-based linux DNS installer
					case DNS:
					{
						script = RPM_DNS;
						args = "-start";
						break;
					}
					
					//RPM-based linux DHCP installer
					case DHCP:
					{
						script = RPM_DHCP;
						args = "-start";
						break;
					}
					
					//RPM-based linux mail installer
					case MAIL:
					{
						script = RPM_MAIL;
						args = "-start";
						break;
					}
					
					//RPM-based linux NFS installer
					case NFS:
					{
						script = RPM_NFS;
						args = "-start";
						break;
					}
					
					//RPM-based linux NIS installer
					case NIS:
					{
						script = RPM_NIS;
						args = String.format("-start -domain %s", newVM.getDomain());
						break;
					}
					
					//RPM-based linux Samba installer
					case SAMBA:
					{
						script = RPM_SAMBA;
						args = "-start";
						break;
					}
					
					//RPM-based linux HTTP installer
					case HTTP:
					{
						script = RPM_HTTP;
						args = String.format("-start -php");
						break;
					}
					
					//RPM-based linux FTP installer
					case FTP:
					{
						script = RPM_FTP;
						args = "-start";
						break;
					}
					
					//Load accounts
					case ACCOUNTS:
					{
						script = RPM_ACCOUNTS;
						args = Integer.toString(newVM.getAccountsCSV());
						break;
					}
					
					//Bind to NIS
					case BIND:
					{
						script = WIN_JOIN;
						args = String.format("%s", newVM.getDomain());
						break;
					}
					
					//Load Exploits
					case EXPLOITS:
					{
						script = RPM_EXPLOITS;
						args = "None";
						break;
					}
				}
				
				username = LINUX_DEFAULT_USERNAME;
				password = DEFAULT_LINUX_PASSWORD;
				scriptType = BASH_SCRIPTTYPE;
				scriptDestination = LINUX_SCRIPT_DESTINATION;
				break;
			}
			
			//Debian-based linux
			case LINUX_DEBIAN:
			{
				//Script types for RPM-based linux
				switch (scriptCommand)
				{
					//Debian-based linux network config
					case NETWORKING:
					{
						script = DEBIAN_NETWORKING;
						args = String.format("-%s -addr %s -netmask %s -gw %s -hostname %s -domain %s -dns %s",
								newVM.isStaticAddress() ? "static" : "dhcp", newVM.getIpAddr(),
								newVM.getNetmask(), newVM.getDefaultGateway(), newVM.getHostName(), 
								newVM.getDomain(), newVM.getDnsServer());
						break;
					}
					
					//Debian-based linux DNS installer
					case DNS:
					{
						script = DEBIAN_DNS;
						args = "-start";
						break;
					}
					
					//Debian-based linux DHCP installer
					case DHCP:
					{
						script = DEBIAN_DHCP;
						args = "-start";
						break;
					}
					
					//Debian-based linux mail installer
					case MAIL:
					{
						script = DEBIAN_MAIL;
						args = "-start";
						break;
					}
					
					//Debian-based linux NFS installer
					case NFS:
					{
						script = DEBIAN_NFS;
						args = "-start";
						break;
					}
					
					//Debian-based linux NIS installer
					case NIS:
					{
						script = DEBIAN_NIS;
						args = String.format("-start -domain %s", newVM.getDomain());
						break;
					}
					
					//Debian-based linux Samba installer
					case SAMBA:
					{
						script = DEBIAN_SAMBA;
						args = "-start";
						break;
					}
					
					//Debian-based linux HTTP installer
					case HTTP:
					{
						script = DEBIAN_HTTP;
						args = String.format("-start -php");
						break;
					}
					
					//Debian-based linux FTP installer
					case FTP:
					{
						script = DEBIAN_FTP;
						args = "-start";
						break;
					}
					
					//Load accounts
					case ACCOUNTS:
					{
						script = DEBIAN_ACCOUNTS;
						args = Integer.toString(newVM.getAccountsCSV());
						break;
					}
					
					//Bind to NIS
					case BIND:
					{
						script = WIN_JOIN;
						args = String.format("%s", newVM.getDomain());
						break;
					}
					
					//Load Exploits
					case EXPLOITS:
					{
						script = DEBIAN_EXPLOITS;
						args = "None";
						break;
					}
				}
				
				username = LINUX_DEFAULT_USERNAME;
				password = DEFAULT_LINUX_PASSWORD;
				scriptType = BASH_SCRIPTTYPE;
				scriptDestination = LINUX_SCRIPT_DESTINATION;
				break;
			}
			
			//BSD
			case BSD:
			{
				//Script types for RPM-based linux
				switch (scriptCommand)
				{
					//Debian-based linux network config
					case NETWORKING:
					{
						script = BSD_NETWORKING;
						args = String.format("-%s -addr %s -netmask %s -gw %s -hostname %s -domain %s -dns %s",
								newVM.isStaticAddress() ? "static" : "dhcp", newVM.getIpAddr(),
								newVM.getNetmask(), newVM.getDefaultGateway(), newVM.getHostName(), 
								newVM.getDomain(), newVM.getDnsServer());
						break;
					}
					
					//BSD DNS installer
					case DNS:
					{
						script = BSD_DNS;
						args = "-start";
						break;
					}
					
					//BSD DHCP installer
					case DHCP:
					{
						script = BSD_DHCP;
						args = "-start";
						break;
					}
					
					//BSD mail installer
					case MAIL:
					{
						script = BSD_MAIL;
						args = "-start";
						break;
					}
					
					//BSD NFS installer
					case NFS:
					{
						script = BSD_NFS;
						args = "-start";
						break;
					}
					
					//BSD NIS installer
					case NIS:
					{
						script = BSD_NIS;
						args = String.format("-start -domain %s", newVM.getDomain());
						break;
					}
					
					//BSD Samba installer
					case SAMBA:
					{
						script = BSD_SAMBA;
						args = "-start";
						break;
					}
					
					//BSD HTTP installer
					case HTTP:
					{
						script = BSD_HTTP;
						args = String.format("-start -php");
						break;
					}
					
					//BSD FTP installer
					case FTP:
					{
						script = BSD_FTP;
						args = "-start";
						break;
					}
					
					//Load accounts
					case ACCOUNTS:
					{
						script = BSD_ACCOUNTS;
						args = Integer.toString(newVM.getAccountsCSV());
						break;
					}
					
					//Bind to NIS
					case BIND:
					{
						script = WIN_JOIN;
						args = String.format("%s", newVM.getDomain());
						break;
					}
					
					//Load Exploits
					case EXPLOITS:
					{
						script = BSD_EXPLOITS;
						args = "None";
						break;
					}
				}
				
				username = LINUX_DEFAULT_USERNAME;
				password = DEFAULT_LINUX_PASSWORD;
				scriptType = BASH_SCRIPTTYPE;
				scriptDestination = LINUX_SCRIPT_DESTINATION;
				break;
			}
			
			
		}
	}
	
	/**
	 * Invokes the Script
	 * <br />
	 * Calls the powershell invoke script in the resources folder
	 */
	public void invoke() throws Exception
	{
		LOG.write("Invoking script...", true);
		String thisScript = String.format("%s %s \"%s %s %s '%s' %s '%s' %s %s %s %s\"",
										   POWERSHELL,
										   INVOKE,
										   engine.getCurrentServer(),
										   engine.getCurrentUsername(),
										   engine.getCurrentPassword(),
										   newVM.getVmName(),
										   script,
										   args,
										   scriptType,
										   scriptDestination,
										   username,
										   password);
		Process p = Runtime.getRuntime().exec(thisScript);
		
		Thread inputThread = new ProcessInputThread(p.getInputStream(), LOG);
		Thread errorThread = new ProcessErrorThread(p.getErrorStream(), LOG);
		
		inputThread.start();
		errorThread.start();
		p.getOutputStream().close();
		p.waitFor();
		
		LOG.write("Script invoke complete", true);
	}
}
