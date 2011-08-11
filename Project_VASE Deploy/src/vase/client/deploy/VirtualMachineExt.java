/**
 * Project_VASE Deploy package
 */
package vase.client.deploy;

import javax.swing.ImageIcon;

import com.vmware.vim25.VirtualMachineSummary;
import com.vmware.vim25.mo.VirtualMachine;

/**
 * VirtualMachine extended class providing more attributes used in displaying the data in
 * the GuiMain, on the Summary Tab and the Virtual Machine Tab.  Allows the user to flag, or mark,
 * the Virtual Machine for management purposes.  Requires that a VMware VirtualMachine object be
 * passed to this class so that the attributes can be updated.
 * @author James McNatt & Brenton Kapral
 * @version Project_VASE Deploy
 */
public class VirtualMachineExt implements ProjectConstraints
{
	private String name;					//Name of Guest
	private String guestOS;					//OS of Guest
	private String ipAddr;					//IP address of Guest
	private String hostName;				//Hostname of Guest
	private String memUsage;				//Memory usage
	private String cpuUsage;				//CPU usage
	private String host;					//Host the guest is residing on
	private String powerStatus;				//Power status (on,off,standby,suspend)
	private String description;				//Notes/description
	private ImageIcon icon = null;			//ImageIcon representation
	private String team;					//Management flag
	private VirtualMachineSummary summary;
	private VirtualMachine vm;				//Super class VM
	
	/**
	 * Constructs the VirtualMachineExt object based on VMware's VirtualMachine class.
	 * Updates the attributes with data gathered from the VirtualMachine
	 * @param vm the VirtualMachine
	 */
	public VirtualMachineExt(VirtualMachine vm)
	{
		this.vm = vm;
		update();
	}
	
	/**
	 * Updates the attributes
	 */
	public void update()
	{
		summary = vm.getSummary();
		try {name = summary.config.name;} catch (Exception e) {name = "Unknown";}
		try {guestOS = summary.guest.guestFullName;} catch (Exception e) {guestOS = "Unknown";}
		try {ipAddr = summary.guest.ipAddress;} catch (Exception e) {ipAddr = "Unknown";}
		try {hostName = summary.guest.hostName;} catch (Exception e) {hostName = "Unknown";}
		try {memUsage = summary.quickStats.hostMemoryUsage.toString();} catch (Exception e){ }
		try {cpuUsage = summary.quickStats.overallCpuUsage.toString();} catch (Exception e){ }
		try {host = summary.runtime.host.get_value();} catch (Exception e) {host = "Unknown";}
		try {powerStatus = summary.runtime.powerState.name();} catch (Exception e) {powerStatus = "Unknown";}
		try {description = summary.config.annotation;} catch (Exception e) {description = "Unknown";}
		
		//Set the Icon
		icon = new IconRenderer().getIcon(guestOS);
		try
		{
			if (!vm.getParent().getName().equalsIgnoreCase(ROOT_FOLDER))
			{
				for (String name : TEAM_NAMES)
				{
					if (vm.getParent().getName().equalsIgnoreCase(name))
					{
						team = vm.getParent().getName();
						TEAMS.put(getName(), vm.getParent().getName());
					}
				}
			}
			
			else if (TEAMS.containsKey(name))
			{
				team = TEAMS.get(name);
			}
			
			else
			{
				team = "< Not Set >";
			}
		}
		
		catch (Exception e)
		{
			team = "< Not Set >";
		}
	}

	/**
	 * @return the name
	 */
	public String getName()
	{
		return name;
	}

	/**
	 * @param name the name to set
	 */
	public void setName(String name)
	{
		this.name = name;
	}

	/**
	 * @return the guestOS
	 */
	public String getGuestOS()
	{
		return guestOS;
	}

	/**
	 * @param guestOS the guestOS to set
	 */
	public void setGuestOS(String guestOS)
	{
		this.guestOS = guestOS;
	}

	/**
	 * @return the ipAddr
	 */
	public String getIpAddr()
	{
		return ipAddr;
	}

	/**
	 * @param ipAddr the ipAddr to set
	 */
	public void setIpAddr(String ipAddr)
	{
		this.ipAddr = ipAddr;
	}

	/**
	 * @return the hostName
	 */
	public String getHostName()
	{
		return hostName;
	}

	/**
	 * @param hostName the hostName to set
	 */
	public void setHostName(String hostName)
	{
		this.hostName = hostName;
	}

	/**
	 * @return the memUsage
	 */
	public String getMemUsage()
	{
		return memUsage + " MB";
	}

	/**
	 * @param memUsage the memUsage to set
	 */
	public void setMemUsage(String memUsage)
	{
		this.memUsage = memUsage;
	}

	/**
	 * @return the cpuUsage
	 */
	public String getCpuUsage()
	{
		return cpuUsage + " MHz";
	}

	/**
	 * @param cpuUsage the cpuUsage to set
	 */
	public void setCpuUsage(String cpuUsage)
	{
		this.cpuUsage = cpuUsage;
	}

	/**
	 * @return the host
	 */
	public String getHost()
	{
		return host;
	}

	/**
	 * @param host the host to set
	 */
	public void setHost(String host)
	{
		this.host = host;
	}

	/**
	 * @return the powerStatus
	 */
	public String getPowerStatus()
	{
		return powerStatus;
	}

	/**
	 * @param powerStatus the powerStatus to set
	 */
	public void setPowerStatus(String powerStatus)
	{
		this.powerStatus = powerStatus;
	}

	/**
	 * @return the description
	 */
	public String getDescription()
	{
		return description;
	}

	/**
	 * @param description the description to set
	 */
	public void setDescription(String description)
	{
		this.description = description;
	}

	/**
	 * @return the icon
	 */
	public ImageIcon getIcon()
	{
		return icon;
	}

	/**
	 * @param icon the icon to set
	 */
	public void setIcon(ImageIcon icon)
	{
		this.icon = icon;
	}

	/**
	 * @return the flag
	 */
	public String getTeam()
	{
		return team;
	}

	/**
	 * @param flag the flag to set
	 */
	public void setFlag(String team)
	{
		this.team = team;
	}
	
	/**
	 * Gets the VirtualMachine object associated with this object
	 * @return the VirtualMachine
	 */
	public VirtualMachine getVM()
	{
		return vm;
	}
}
