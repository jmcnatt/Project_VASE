/**
 * Project_VASE Deploy
 */
package vase.client.deploy;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Random;

/**
 * Representation of a new Virtual Machine to be deployed.  Created during the
 * deployment wizard and used to generate the report on the LastDeployment tab in 
 * GuiMain and used in the command engine to actually deploy a new virtual machine to
 * a VirtualMachine object.
 * @author James McNatt & Brenton Kapral
 * @version Project_VASE Deploy
 */
public class DeployedVirtualMachine implements ProjectConstraints, Serializable
{
	private static final long serialVersionUID = -7927007364955586322L;
	
	//Generator for accounts CSV file
	private Random generator = new Random();
	
	//VM attributes
	private String vmName;
	private String team = "< Not Set >";
	private String network;
	private String hostName;
	private String ipAddr;
	private String netmask;
	private String defaultGateway;
	private String domain;
	private String osCategory;
	private String osType;
	private String osName;
	private String dnsServer;
	private String domainControllerIpAddress;
	private boolean staticAddress = true;
	
	//Service attributes
	private boolean http = false;
	private boolean nfs = false;;
	private boolean nis = false;
	private boolean dhcp = false;
	private boolean samba = false;
	private boolean https = false;
	private boolean dns = false;
	private boolean mail = false;
	private boolean ftp = false;
	private boolean activeDirectory = false;
	private boolean iis = false;
	private boolean fileServer = false;
	private boolean exploits = false;
	private boolean accounts = false;
	private boolean bind = false;
	private ArrayList<String> services;							//Holds confirmed services
	private int accountsCSV;
	
	/**
	 * List of Script objects that can be deployed
	 */
	public transient ArrayList<Script> scripts;					//Holds the Scripts

	/**
	 * Default Constructor
	 * <br />
	 * Sets the vmName to "Unknown".  Used when a blank ArrayList of DeployedVirtualMachines is
	 * created in the Deployment Wizard
	 */
	public DeployedVirtualMachine()
	{
		vmName = "Unknown";
		services = new ArrayList<String>();
		scripts = new ArrayList<Script>();
		accountsCSV = generator.nextInt(ACCOUNT_CSV_COUNT);
	}
	
	/**
	 * Main constructor, used in the first dialog of the DeployedVirtualMachine build
	 * process in the GuiDeployWizard
	 * @param osCategory the category of OS (Windows, Linux, etc)
	 * @param osType the OS type (CLIENT or SERVER)
	 * @param osName the OS name
	 */
	public DeployedVirtualMachine(String osCategory, String osType, String osName)
	{
		this.osCategory = osCategory;
		this.osType = osType;
		this.osName = osName;
		accountsCSV = generator.nextInt(ACCOUNT_CSV_COUNT);
	}	

	/**
	 * Gets the name of this deployed virtual machine
	 * @return the vmName
	 */
	public String getVmName()
	{
		return vmName;
	}

	/**
	 * Sets the name of this deployed virtual machine
	 * @param vmName the vmName to set
	 */
	public void setVmName(String vmName)
	{
		this.vmName = vmName;
	}
	
	/**
	 * Gets the team name of this deployed virtual machine
	 * @return the team
	 */
	public String getTeam()
	{
		return team;
	}

	/**
	 * Sets the team of this deployed virtual machine
	 * @param team the team to set
	 */
	public void setTeam(String team)
	{
		this.team = team;
	}

	/**
	 * Sets the name of the network
	 * @param portgroup the network to set
	 */
	public void setNetwork(String network)
	{
		this.network = network;
	}

	/**
	 * Gets the name of the network
	 * @return the network
	 */
	public String getNetwork()
	{
		return network;
	}

	/**
	 * Gets the hostname of this deployed virtual machine
	 * @return the hostName
	 */
	public String getHostName()
	{
		return hostName;
	}

	/**
	 * Sets the hostname of this deployed virtual machine
	 * @param hostName the hostName to set
	 */
	public void setHostName(String hostName)
	{
		this.hostName = hostName;
	}

	/**
	 * Gets the IP Address of this deployed virtual machine
	 * @return the ipAddr
	 */
	public String getIpAddr()
	{
		return ipAddr;
	}

	/**
	 * Sets the IP address of this deployed virtual machine
	 * @param ipAddr the ipAddr to set
	 */
	public void setIpAddr(String ipAddr)
	{
		this.ipAddr = ipAddr;
	}

	/**
	 * Gets the subnet mask of this deployed virtual machine
	 * @return the netmask
	 */
	public String getNetmask()
	{
		return netmask;
	}

	/**
	 * Sets the subnet mask of this deployed virtual machine
	 * @param netmask the netmask to set
	 */
	public void setNetmask(String netmask)
	{
		this.netmask = netmask;
	}

	/**
	 * Gets the default gateway of this deployed virtual machine
	 * @return the defaultGateway
	 */
	public String getDefaultGateway()
	{
		return defaultGateway;
	}

	/**
	 * Sets the default gateway of this deployed virtual machine
	 * @param defaultGateway the defaultGateway to set
	 */
	public void setDefaultGateway(String defaultGateway)
	{
		this.defaultGateway = defaultGateway;
	}

	/**
	 * Sets the DNS Server of this deployedvirtual machine
	 * @param dnsServer the dnsServer to set
	 */
	public void setDnsServer(String dnsServer)
	{
		this.dnsServer = dnsServer;
	}

	/**
	 * Gets the DNS server of this deployed virtual machine
	 * @return the dnsServer
	 */
	public String getDnsServer()
	{
		return dnsServer;
	}

	/**
	 * Gets the domain of this deployed virtual machine
	 * @return the domain
	 */
	public String getDomain()
	{
		return domain;
	}

	/**
	 * Sets the domain of this deployed virtual machine
	 * @param domain the domain to set
	 */
	public void setDomain(String domain)
	{
		this.domain = domain;
	}

	/**
	 * Gets the OS Category of this deployed virtual machine
	 * @return the osCategory
	 */
	public String getOsCategory()
	{
		return osCategory;
	}

	/**
	 * Sets the OS Category of this deployed virtual machine
	 * @param osCategory the osCategory to set
	 */
	public void setOsCategory(String osCategory)
	{
		this.osCategory = osCategory;
	}

	/**
	 * Gets the OS type for this deployed virtual machine
	 * @return the osType
	 */
	public String getOsType()
	{
		return osType;
	}

	/**
	 * Sets the OS type for this deployed virtual machine
	 * @param osType the osType to set
	 */
	public void setOsType(String osType)
	{
		this.osType = osType;
	}

	/**
	 * Sets the OS name for this deployed virtual machine
	 * @return the osName
	 */
	public String getOsName()
	{
		return osName;
	}

	/**
	 * Gets the OS name for this deployed virtual machine
	 * @param osName the osName to set
	 */
	public void setOsName(String osName)
	{
		this.osName = osName;
	}
	
	/**
	 * Gets the CSV file number for the account generation script
	 * @return account CSV file identifier
	 */
	public int getAccountsCSV()
	{
		return accountsCSV;
	}

	/**
	 * Gets the IP address of the domain controller when binding a client to active directory
	 * @return the domainControllerIpAddress
	 */
	public String getDomainControllerIpAddress()
	{
		return domainControllerIpAddress;
	}

	/**
	 * Sets the IP address of the domain controller when binding a client to active directory
	 * @param domainControllerIpAddress the domainControllerIpAddress to set
	 */
	public void setDomainControllerIpAddress(String domainControllerIpAddress)
	{
		this.domainControllerIpAddress = domainControllerIpAddress;
	}

	/**
	 * Whether or not the deployed virtual machine has a static IP address
	 * @return the staticAddress
	 */
	public boolean isStaticAddress()
	{
		return staticAddress;
	}

	/**
	 * Sets whether or not the deployed virtual machine has a static IP address
	 * @param staticAddress the staticAddress to set
	 */
	public void setStaticAddress(boolean staticAddress)
	{
		this.staticAddress = staticAddress;
	}

	/**
	 * Gets whether or not HTTP should be installed
	 * @return true if HTTP is to be installed
	 */
	public boolean isHttp()
	{
		return http;
	}

	/**
	 * Sets whether or not HTTP should be installed
	 * @param http whether or not to install HTTP
	 */
	public void setHttp(boolean http)
	{
		this.http = http;
	}

	/**
	 * Gets whether or not NFS should be installed
	 * @return true if NFS is to be installed
	 */
	public boolean isNfs()
	{
		return nfs;
	}

	/**
	 * Sets whether or not NFS should be installed
	 * @param nfs whether or not to install NFS
	 */
	public void setNfs(boolean nfs)
	{
		this.nfs = nfs;
	}

	/**
	 * Gets whether or not NIS should be installed
	 * @return true if NIS is to be installed
	 */
	public boolean isNis()
	{
		return nis;
	}

	/**
	 * Sets whether or not NIS should be installed
	 * @param nis whether or not to install NIS
	 */
	public void setNis(boolean nis)
	{
		this.nis = nis;
	}

	/**
	 * Gets whether or not DHCP should be installed
	 * @return true if DHCP is to be installed
	 */
	public boolean isDhcp()
	{
		return dhcp;
	}

	/**
	 * Sets whether or not DHCP should be installed
	 * @param dhcp whether or not to install DHCP
	 */
	public void setDhcp(boolean dhcp)
	{
		this.dhcp = dhcp;
	}

	/**
	 * Gets whether or not Samba should be installed
	 * @return true if Samba is to be installed
	 */
	public boolean isSamba()
	{
		return samba;
	}

	/**
	 * Sets whether or not Samba should be installed
	 * @param samba whether or not to install Samba
	 */
	public void setSamba(boolean samba)
	{
		this.samba = samba;
	}

	/**
	 * Gets whether or not HTTPS should be installed
	 * @return true if HTTPS is to be installed
	 */
	public boolean isHttps()
	{
		return https;
	}

	/**
	 * Sets whether or not HTTPS should be installed
	 * @param https whether or not to install HTTPS
	 */
	public void setHttps(boolean https)
	{
		this.https = https;
	}

	/**
	 * Gets whether or not DNS should be installed
	 * @return true if DNS is to be installed
	 */
	public boolean isDns()
	{
		return dns;
	}

	/**
	 * Sets whether or not DNS should be installed
	 * @param dns whether or not to install DNS
	 */
	public void setDns(boolean dns)
	{
		this.dns = dns;
	}

	/**
	 * Gets whether or not Mail should be installed
	 * @return true if Mail is to be installed
	 */
	public boolean isMail()
	{
		return mail;
	}

	/**
	 * Sets whether or not Mail should be installed
	 * @param mail whether or not to install Mail
	 */
	public void setMail(boolean mail)
	{
		this.mail = mail;
	}

	/**
	 * Gets whether or not FTP should be installed
	 * @return true if FTP is to be installed
	 */
	public boolean isFtp()
	{
		return ftp;
	}

	/**
	 * Sets whether or not FTP should be installed
	 * @param ftp whether or not to install FTP
	 */
	public void setFtp(boolean ftp)
	{
		this.ftp = ftp;
	}

	/**
	 * Gets whether or not Active Directory should be installed
	 * @return true if Active Directory is to be installed
	 */
	public boolean isActiveDirectory()
	{
		return activeDirectory;
	}

	/**
	 * Sets whether or not Active Directory should be installed
	 * @param activeDirectory whether or not to install Active Directory
	 */
	public void setActiveDirectory(boolean activeDirectory)
	{
		this.activeDirectory = activeDirectory;
	}

	/**
	 * Gets whether or not IIS should be installed
	 * @return true if IIS is to be installed
	 */
	public boolean isIis()
	{
		return iis;
	}

	/**
	 * Sets whether or not IIS should be installed
	 * @param iis whether or not to install IIS
	 */
	public void setIis(boolean iis)
	{
		this.iis = iis;
	}

	/**
	 * Gets whether or not to install Windows File Server
	 * @return true if Windows File Server is to be installed
	 */
	public boolean isFileServer()
	{
		return fileServer;
	}

	/**
	 * Sets whether or not Windows File Server should be installed
	 * @param fileServer whether or not to install Windows File Server
	 */
	public void setFileServer(boolean fileServer)
	{
		this.fileServer = fileServer;
	}

	/**
	 * Gets whether or not to install exploits
	 * @return true if exploits is to be installed
	 */
	public boolean isExploits()
	{
		return exploits;
	}

	/**
	 * Sets whether or not exploits should installed
	 * @param exploits whether or not to install exploits
	 */
	public void setExploits(boolean exploits)
	{
		this.exploits = exploits;
	}

	/**
	 * Gets whether or not to install accounts
	 * @return true if accounts should be installed
	 */
	public boolean isAccounts()
	{
		return accounts;
	}

	/**
	 * Sets whether or not accounts should installed
	 * @param accounts whether or not to install accounts
	 */
	public void setAccounts(boolean accounts)
	{
		this.accounts = accounts;
	}

	/**
	 * Gets whether or not to bind this deployed virtual machine to 
	 * NIS or Active Directory
	 * @return true if this deployed virtual machine is to be bound to NIS 
	 * or Active Directory
	 */
	public boolean isBind()
	{
		return bind;
	}

	/**
	 * Sets whether or not this deployed virtual machine should be bound to NIS 
	 * or Active Directory
	 * @param bind whether or not to bind this deployed virtual machine to NIS 
	 * or Active Directory
	 */
	public void setBind(boolean bind)
	{
		this.bind = bind;
	}
	
	/**
	 * Collects the services in one list to be used in the toString() method
	 * @see DeployedVirtualMachine#toString()
	 */
	private void collectServices()
	{
		if (isNis()) 
		{
			services.add("NIS");
		}
		
		if (isNfs())
		{
			services.add("NFS");
		}
		
		if (isDns())
		{
			services.add("DNS");
		}
		
		if (isDhcp())
		{
			services.add("DHCP");
		}
		
		if (isMail())
		{
			services.add("Mail");
		}
		
		if (isHttp())
		{
			services.add("HTTP");
		}
		
		if (isFtp())
		{
			services.add("FTP");
		}
		
		if (isSamba())
		{
			services.add("Samba");
		}
		
		if (isHttps())
		{
			services.add("HTTPS");
		}
		
		if (isActiveDirectory())
		{
			services.add("Active Directory");
		}
		
		if (isFileServer())
		{
			services.add("File Server");
		}
		
		if (isIis())
		{
			services.add("IIS");
		}
		
		if (osCategory.equals(WINDOWS) && isBind())
		{
			services.add("Bind to Active Directory");
		}
		
		else if (!osCategory.equals(WINDOWS) && isBind())
		{
			services.add("Bind to NIS");
		}
	}

	/**
	 * Overrides the toString() method
	 * Prints out a list for use in the summary screen in the GuiDeployWizard
	 * @see java.lang.Object#toString()
	 * @see GuiDeployWizard
	 */
	@Override
	public String toString()
	{
		collectServices();
		if (services.size() == 0) services.add("None");
		String output = new String();
		String network = "Static";
		String loadExploits = "Yes";
		String loadAccounts = "Yes";
		String setBind = "Yes";
		
		if (!isStaticAddress())
		{
			network = "DHCP";
		}
		
		if (!isExploits())
		{
			loadExploits = "No";
		}
		
		if (!isAccounts())
		{
			loadAccounts = "No";
		}
		
		if (ipAddr == null)
		{
			ipAddr = "DHCP";
		}
		
		if (netmask == null)
		{
			netmask = "DHCP";
		}
		
		if (defaultGateway == null)
		{
			defaultGateway = "DHCP";
		}
		
		if (dnsServer == null)
		{
			dnsServer = "DHCP";
		}
		
		if (!isBind())
		{
			setBind = "No";
		}
		
		if (osCategory.equals(WINDOWS))
		{
			if (osType.equals(SERVER))
			{
				output = String.format("%s%n" +
									   "  VM Name:          %s%n" +
									   "  Team:             %s%n" +
									   "  Network Adapter:  %s%n" +
									   "  IP Address:       %s%n" +
									   "  Subnet Mask:      %s%n" +
									   "  Default Gateway:  %s%n" +
									   "  Hostname:         %s%n" +
									   "  Domain Name:      %s%n" +
									   "  Services:         ",
				getOsName(), getVmName(), getTeam(), network, getIpAddr(), getNetmask(),
				getDefaultGateway(), getHostName(), getDomain());
				
				output += getServices();
				
				output += "\n  Load Accounts:    " + loadAccounts + "\n" +
						    "  Load Exploits:    " + loadExploits + "\n\n";
			}
			
			else if (osType.equals(CLIENT))
			{
				output = String.format("%s%n" +
						   "  VM Name:          %s%n" +
						   "  Team:             %s%n" +
						   "  Network Adapter:  %s%n" +
						   "  IP Address:       %s%n" +
						   "  Subnet Mask:      %s%n" +
						   "  Default Gateway:  %s%n" +
						   "  Hostname:         %s%n" +
						   "  Domain Name:      %s%n" +
						   "  Bind to AD:       %s%n" +
						   "  Load Accounts:    %s%n" +
						   "  Load Exploits:    %s%n%n",
				getOsName(), getVmName(), getTeam(), network, getIpAddr(), getNetmask(),
				getDefaultGateway(), getHostName(), getDomain(), setBind,
				loadAccounts, loadExploits);
			}
		}
		
		else if (osCategory.equals(LINUX_DEBIAN) || osCategory.equals(LINUX_RPM) || osCategory.equals(BSD))
		{
			if (osType.equals(SERVER))
			{
				output = String.format("%s%n" +
									   "  VM Name:          %s%n" +
									   "  Team:             %s%n" +
									   "  Network Adapter:  %s%n" +
									   "  IP Address:       %s%n" +
									   "  Subnet Mask:      %s%n" +
									   "  Default Gateway:  %s%n" +
									   "  Hostname:         %s%n" +
									   "  Domain Name:      %s%n" +
									   "  Services:         ",
				getOsName(), getVmName(), getTeam(), network, getIpAddr(), getNetmask(),
				getDefaultGateway(), getHostName(), getDomain());
				
				output += getServices();
				
				output += "\n  Load Accounts:    " + loadAccounts + "\n" +
						    "  Load Exploits:    " + loadExploits + "\n\n";
			}
			
			else if (osType.equals(CLIENT))
			{
				output = String.format("%s%n" +
						   "  VM Name:          %s%n" +
						   "  Team:             %s%n" +
						   "  Network Adapter:  %s%n" +
						   "  IP Address:       %s%n" +
						   "  Subnet Mask:      %s%n" +
						   "  Default Gateway:  %s%n" +
						   "  Hostname:         %s%n" +
						   "  Domain Name:      %s%n" +
						   "  Bind to NIS:      %s%n" +
						   "  Load Accounts:    %s%n" +
						   "  Load Exploits:    %s%n%n",
				getOsName(), getVmName(), getTeam(), network, getIpAddr(), getNetmask(),
				getDefaultGateway(), getHostName(), getDomain(), setBind,
				loadAccounts, loadExploits);
			}
		}
						
		return output;
	}
	
	/**
	 * Gets the services in a single string
	 * @return all of the selected services
	 */
	public String getServices()
	{
		String output = new String();
		int i = 0;
		for (String each : services)
		{
			output += each;
			if (i < services.size() - 1)
			{
				output += ", ";
			}
			
			i++;
		}
		
		return output;
	}
	
	/**
	 * Compiles the set of scripts to be run on the VirtualMachine corresponding to this
	 * DeployedVirtualMachine object.  It modifies the ArrayList of Scripts which can be
	 * accessed by the DeployThread in the CommandEngine
	 * @see CommandEngine#deploy(ArrayList)
	 * @see DeployedVirtualMachine#scripts
	 */
	public void compileScripts(CommandEngine engine)
	{
		//Scripts for Windows
		if (osCategory.equals(WINDOWS))
		{
			LOG.write(getVmName() + ": Generating Network Config Script", true);
			Script networking = new Script(this, Script.WINDOWS, Script.NETWORKING, engine);
			scripts.add(networking);
			
			if (isActiveDirectory())
			{
				LOG.write(getVmName() + ": Generating Active Directory Script", true);
				Script script = new Script(this, Script.WINDOWS, Script.ACTIVEDIRECTORY, engine);
				scripts.add(script);
			}
			
			if (!isActiveDirectory() && isDns())
			{
				LOG.write(getVmName() + ": Generating DNS Script", true);
				Script script = new Script (this, Script.WINDOWS, Script.DNS, engine);
				scripts.add(script);
			}
			
			if (isDhcp())
			{
				LOG.write(getVmName() + ": Generating DHCP Script", true);
				Script script = new Script(this, Script.WINDOWS, Script.DHCP, engine);
				scripts.add(script);
			}
			
			if (isIis())
			{
				LOG.write(getVmName() + ": Generating IIS Script", true);
				Script script = new Script(this, Script.WINDOWS, Script.IIS, engine);
				scripts.add(script);
			}
			
			if (isFileServer())
			{
				LOG.write(getVmName() + ": Generating File Server Script", true);
				Script script = new Script(this, Script.WINDOWS, Script.FILESERV, engine);
				scripts.add(script);
			}
			
			if (isBind())
			{
				LOG.write(getVmName() + ": Generating Active Directory Bind Script", true);
				Script script = new Script(this, Script.WINDOWS, Script.BIND, engine);
				scripts.add(script);
			}
			
			if (isAccounts())
			{
				LOG.write(getVmName() + ": Generating User Accounts Script", true);
				LOG.write("Loading Accounts CSV #" + accountsCSV, true);
				Script script = new Script(this, Script.WINDOWS, Script.ACCOUNTS, engine);
				scripts.add(script);
			}
			
			if (isExploits())
			{
				LOG.write(getVmName() + ": Generating Exploit Script", true);
				Script script = new Script(this, Script.WINDOWS, Script.EXPLOITS, engine);
				scripts.add(script);
			}
			
			LOG.write(getVmName() + ": Generating Cleanup Script", true);
			scripts.add(new Script(this, Script.WINDOWS, Script.CLEANUP, engine));
		}
		
		//Scripts for RPM-based Linux
		if (osCategory.equals(LINUX_RPM))
		{
			LOG.write(getVmName() + ": Generating Network Config Script", true);
			Script networking = new Script(this, Script.LINUX_RPM, Script.NETWORKING, engine);
			scripts.add(networking);
			
			if (isNfs())
			{
				LOG.write(getVmName() + ": Generating NFS Script", true);
				Script script = new Script(this, Script.LINUX_RPM, Script.NFS, engine);
				scripts.add(script);
			}
			
			if (isNfs() && isNis())
			{
				LOG.write(getVmName() + ": Generating NIS Script", true);
				Script script = new Script (this, Script.LINUX_RPM, Script.NIS, engine);
				scripts.add(script);
			}
			
			if (isDhcp())
			{
				LOG.write(getVmName() + ": Generating DHCP Script", true);
				Script script = new Script(this, Script.LINUX_RPM, Script.DHCP, engine);
				scripts.add(script);
			}
			
			if (isDns())
			{
				LOG.write(getVmName() + ": Generating DNS Script", true);
				Script script = new Script(this, Script.LINUX_RPM, Script.DNS, engine);
				scripts.add(script);
			}
			
			if (isMail())
			{
				LOG.write(getVmName() + ": Generating Mail Script", true);
				Script script = new Script(this, Script.LINUX_RPM, Script.MAIL, engine);
				scripts.add(script);
			}
			
			if (isSamba())
			{
				LOG.write(getVmName() + ": Generating Samba Script", true);
				Script script = new Script(this, Script.LINUX_RPM, Script.SAMBA, engine);
				scripts.add(script);
			}
			
			if (isHttp())
			{
				LOG.write(getVmName() + ": Generating HTTP Script", true);
				Script script = new Script(this, Script.LINUX_RPM, Script.HTTP, engine);
				scripts.add(script);
			}
			
			if (isBind())
			{
				LOG.write(getVmName() + ": Generating NIS Bind Script", true);
				Script script = new Script(this, Script.LINUX_RPM, Script.BIND, engine);
				scripts.add(script);
			}
			
			if (isAccounts())
			{
				LOG.write(getVmName() + ": Generating User Accounts Script", true);
				Script script = new Script(this, Script.LINUX_RPM, Script.ACCOUNTS, engine);
				scripts.add(script);
			}
			
			if (isExploits())
			{
				LOG.write(getVmName() + ": Generating Exploit Script", true);
				Script script = new Script(this, Script.LINUX_RPM, Script.EXPLOITS, engine);
				scripts.add(script);
			}
		}
		
		//Scripts for Debian-based Linux
		if (osCategory.equals(LINUX_DEBIAN))
		{
			LOG.write(getVmName() + ": Generating Network Config Script", true);
			Script networking = new Script(this, Script.LINUX_DEBIAN, Script.NETWORKING, engine);
			scripts.add(networking);
			
			if (isNfs())
			{
				LOG.write(getVmName() + ": Generating NFS Script", true);
				Script script = new Script(this, Script.LINUX_RPM, Script.NFS, engine);
				scripts.add(script);
			}
			
			if (isNfs() && isNis())
			{
				LOG.write(getVmName() + ": Generating NIS Script", true);
				Script script = new Script (this, Script.LINUX_RPM, Script.NIS, engine);
				scripts.add(script);
			}
			
			if (isDhcp())
			{
				LOG.write(getVmName() + ": Generating DHCP Script", true);
				Script script = new Script(this, Script.LINUX_RPM, Script.DHCP, engine);
				scripts.add(script);
			}
			
			if (isDns())
			{
				LOG.write(getVmName() + ": Generating DNS Script", true);
				Script script = new Script(this, Script.LINUX_RPM, Script.DNS, engine);
				scripts.add(script);
			}
			
			if (isMail())
			{
				LOG.write(getVmName() + ": Generating Mail Script", true);
				Script script = new Script(this, Script.LINUX_RPM, Script.MAIL, engine);
				scripts.add(script);
			}
			
			if (isSamba())
			{
				LOG.write(getVmName() + ": Generating Samba Script", true);
				Script script = new Script(this, Script.LINUX_RPM, Script.SAMBA, engine);
				scripts.add(script);
			}
			
			if (isHttp())
			{
				LOG.write(getVmName() + ": Generating HTTP Script", true);
				Script script = new Script(this, Script.LINUX_RPM, Script.HTTP, engine);
				scripts.add(script);
			}
			
			if (isBind())
			{
				LOG.write(getVmName() + ": Generating NIS Bind Script", true);
				Script script = new Script(this, Script.LINUX_RPM, Script.BIND, engine);
				scripts.add(script);
			}
			
			if (isAccounts())
			{
				LOG.write(getVmName() + ": Generating User Accounts Script", true);
				Script script = new Script(this, Script.LINUX_RPM, Script.ACCOUNTS, engine);
				scripts.add(script);
			}
			
			if (isExploits())
			{
				LOG.write(getVmName() + ": Generating Exploit Script", true);
				Script script = new Script(this, Script.LINUX_RPM, Script.EXPLOITS, engine);
				scripts.add(script);
			}
		}
		
		//Scripts for BSD
		if (osCategory.equals(BSD))
		{
			LOG.write(getVmName() + ": Generating Network Config Script", true);
			Script networking = new Script(this, Script.BSD, Script.NETWORKING, engine);
			scripts.add(networking);
			
			if (isNfs())
			{
				LOG.write(getVmName() + ": Generating NFS Script", true);
				Script script = new Script(this, Script.BSD, Script.NFS, engine);
				scripts.add(script);
			}
			
			if (isNfs() && isNis())
			{
				LOG.write(getVmName() + ": Generating NIS Script", true);
				Script script = new Script (this, Script.BSD, Script.NIS, engine);
				scripts.add(script);
			}
			
			if (isDhcp())
			{
				LOG.write(getVmName() + ": Generating DHCP Script", true);
				Script script = new Script(this, Script.BSD, Script.DHCP, engine);
				scripts.add(script);
			}
			
			if (isDns())
			{
				LOG.write(getVmName() + ": Generating DNS Script", true);
				Script script = new Script(this, Script.BSD, Script.DNS, engine);
				scripts.add(script);
			}
			
			if (isMail())
			{
				LOG.write(getVmName() + ": Generating Mail Script", true);
				Script script = new Script(this, Script.BSD, Script.MAIL, engine);
				scripts.add(script);
			}
			
			if (isSamba())
			{
				LOG.write(getVmName() + ": Generating Samba Script", true);
				Script script = new Script(this, Script.BSD, Script.SAMBA, engine);
				scripts.add(script);
			}
			
			if (isHttp())
			{
				LOG.write(getVmName() + ": Generating HTTP Script", true);
				Script script = new Script(this, Script.BSD, Script.HTTP, engine);
				scripts.add(script);
			}
			
			if (isBind())
			{
				LOG.write(getVmName() + ": Generating NIS Bind Script", true);
				Script script = new Script(this, Script.BSD, Script.BIND, engine);
				scripts.add(script);
			}
			
			if (isAccounts())
			{
				LOG.write(getVmName() + ": Generating User Accounts Script", true);
				Script script = new Script(this, Script.BSD, Script.ACCOUNTS, engine);
				scripts.add(script);
			}
			
			if (isExploits())
			{
				LOG.write(getVmName() + ": Generating Exploit Script", true);
				Script script = new Script(this, Script.BSD, Script.EXPLOITS, engine);
				scripts.add(script);
			}
		}
	}
}
