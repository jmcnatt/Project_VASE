# Invokes a script on a VM
# Author: James McNatt / Brent Kapral

#perform Powershell version check

if ($env:Processor_Architecture -ne "x86"){

      write-warning "Running x86 PowerShell..." -WarningAction SilentlyContinue

            &"$env:WINDIR\syswow64\windowspowershell\v1.0\powershell.exe" -NonInteractive -NoProfile $myInvocation.Line
	  return
	  
}

add-pssnapin VMware.VimAutomation.Core
$node = $args[0]
$user = $args[1]
$password = $args[2]
$vmname = $args[3]
$script = $args[4]
$scriptparams = $args[5]
$scripttype = $args[6]
$vmdest = $args[7]
$guser = $args[8]
$gpassword = $args[9]
$sharename = "scripts"

Connect-VIServer -Server $node -Protocol https -User $user -Password $password -WarningAction SilentlyContinue > $null

$VM = Get-VM($vmname);


#03 DC needs and inf file for the unattended install of AD.  This modifies the answer file and copies it to the VM
if($script -eq "win-03dc.bat"){  

	$ofile = "\\$node\$sharename\03dc_orig.inf"
	$file = "\\$node\$sharename\03dc.inf"
	copy $ofile $file;
	
	
	$indx = $scriptparams.indexof('.') 2>$null
	
	if ($indx -ne "-1"){
	("NewDomainDNSName=" + $scriptparams) | Out-File -append $file;
	$netbios = $scriptparams.substring(0, $indx); #create netbios name, limit to 15 chars, make it uppercase
	} else {
	("NewDomainDNSName=" + $scriptparams + ".local") | Out-File -append $file;
	$netbios = $scriptparams;
	}
	if ($netbios.length -gt 15) {
		$netbios = $netbios.substring(0,15);
	}
	$netbios = $netbios.ToUpper();
	("DomainNetbiosName=" + $netbios) | Out-File -Append $file;
	
	$ctr = 0;
	$view = get-vm $vmname | get-view
	$ready = $view.guest.toolsstatus
	while ($ready -ne "toolsOk") {
		sleep 10
		$view = get-vm $vmname | get-view
		$ready = $view.guest.toolsstatus
		$ctr = $ctr + 1;
		if($ctr -eq 600) {
			write-host "Timed out waiting for VMWare tools to load on the VM."
			exit
		}
	}
	copy-vmguestfile -source $file -destination $vmdest -vm $vmname -localtoguest -guestuser $guser -guestpassword $gpassword -ToolsWaitSecs 200 -WarningAction SilentlyContinue;
	Remove-Item $file;
}
#08 DC needs and inf file for the unattended install of AD.  This modifies the answer file and copies it to the VM
if($script -eq "win-08dc.bat"){
	$ofile = "\\$node\$sharename\08dc_orig.inf"
	$file = "\\$node\$sharename\08dc.inf"
	copy $ofile $file;
	("NewDomainDNSName=" + $scriptparams) | Out-File -append $file;
	
	$ctr = 0;
	$view = get-vm $vmname | get-view
	$ready = $view.guest.toolsstatus
	while ($ready -ne "toolsOk") {
		sleep 10
		$view = get-vm $vmname | get-view
		$ready = $view.guest.toolsstatus
		$ctr = $ctr + 1;
		if($ctr -eq 600) {
			write-host "Timed out waiting for VMWare tools to load on the VM."
			exit
		}
	}
	copy-vmguestfile -source $file -destination $vmdest -vm $vmname -localtoguest -guestuser $guser -guestpassword $gpassword -ToolsWaitSecs 200 -WarningAction SilentlyContinue;
	Remove-Item $file; 
}


#IIS needs additional files.  This calls another powershell script that copies those files to the VM
if($script -eq "win-iis.bat") {
$ctr = 0;
	$view = get-vm $vmname | get-view
	$ready = $view.guest.toolsstatus
	while ($ready -ne "toolsOk") {
		sleep 10
		$view = get-vm $vmname | get-view
		$ready = $view.guest.toolsstatus
		$ctr = $ctr + 1;
		if($ctr -eq 600) {
			write-host "Timed out waiting for VMWare tools to load on the VM."
			exit
		}
	}

copy-vmguestfile -source "\\$node\$sharename\iis\chglist.vbs" -destination $vmdest -vm $VM -localtoguest -guestuser $guser -guestpassword $gpassword -ToolsWaitSecs 200 -WarningAction SilentlyContinue
copy-vmguestfile -source "\\$node\$sharename\iis\fcgisetup_1.5_rtw_x86.msi" -destination $vmdest -vm $VM -localtoguest -guestuser $guser -guestpassword $gpassword -ToolsWaitSecs 200 -WarningAction SilentlyContinue;
copy-vmguestfile -source "\\$node\$sharename\iis\iis.inf" -destination $vmdest -vm $VM -localtoguest -guestuser $guser -guestpassword $gpassword -ToolsWaitSecs 200 -WarningAction SilentlyContinue;
copy-vmguestfile -source "\\$node\$sharename\iis\php.ini" -destination $vmdest -vm $VM -localtoguest -guestuser $guser -guestpassword $gpassword -ToolsWaitSecs 200 -WarningAction SilentlyContinue;
copy-vmguestfile -source "\\$node\$sharename\iis\php-5.3.6-nts-Win32-VC9-x86.msi" -destination $vmdest -vm $VM -localtoguest -guestuser $guser -guestpassword $gpassword -ToolsWaitSecs 200 -WarningAction SilentlyContinue;
copy-vmguestfile -source "\\$node\$sharename\iis\setDir.reg" -destination $vmdest -vm $VM -localtoguest -guestuser $guser -guestpassword $gpassword -ToolsWaitSecs 200 -WarningAction SilentlyContinue;
copy-vmguestfile -source "\\$node\$sharename\iis\test.php" -destination $vmdest -vm $VM -localtoguest -guestuser $guser -guestpassword $gpassword -ToolsWaitSecs 200 -WarningAction SilentlyContinue;
}

#DNS server copy files
if($script -eq "win-dns.bat") {

$ctr = 0;
	$view = get-vm $vmname | get-view
	$ready = $view.guest.toolsstatus
	while ($ready -ne "toolsOk") {
		sleep 10
		$view = get-vm $vmname | get-view
		$ready = $view.guest.toolsstatus
		$ctr = $ctr + 1;
		if($ctr -eq 600) {
			write-host "Timed out waiting for VMWare tools to load on the VM."
			exit
		}
	}

$file = "\\$node\$sharename\dns\dns.inf"
copy-vmguestfile -source $file -destination $vmdest -vm $vmname -localtoguest -guestuser $guser -guestpassword $gpassword -ToolsWaitSecs 200 -WarningAction SilentlyContinue;
$file = "\\$node\$sharename\dns\dnscmd.exe"
copy-vmguestfile -source $file -destination $vmdest -vm $vmname -localtoguest -guestuser $guser -guestpassword $gpassword -ToolsWaitSecs 200 -WarningAction SilentlyContinue;
}

#DHCP server copy files
if($script -eq "win-dhcp.bat") {
	$ctr = 0;
	$view = get-vm $vmname | get-view
	$ready = $view.guest.toolsstatus
	while ($ready -ne "toolsOk") {
		sleep 10
		$view = get-vm $vmname | get-view
		$ready = $view.guest.toolsstatus
		$ctr = $ctr + 1;
		if($ctr -eq 600) {
			write-host "Timed out waiting for VMWare tools to load on the VM."
			exit
		}
	}

$file = "\\$node\$sharename\dhcp\dhcp.inf"
Copy-VMGuestFile -Source $file -Destination $vmdest -VM $vmname -LocalToGuest -Guestuser $guser -GuestPassword $gpassword -ToolsWaitSecs 200 -WarningAction SilentlyContinue;
}
#User copy files
if($script -eq "win-user.bat") {
#[Reflection.Assembly]::LoadWithPartialName(“System.Web”) > $null -NEEDED TO GENERATE RANDOM PASSWORDS FOR ACCOUNTS
$r = Get-Random -Maximum ("10") #Max # must be equal to number of users.csv files
$list = Import-Csv \\$node\$sharename\users\users$r.csv
#foreach ($i in $list) {
#	$i.password = [System.Web.Security.Membership]::GeneratePassword(8,0) -NEEDED TO GENERATE RANDOM PASSWORDS FOR ACCOUNTS
#	}
Write-Host "Users file is users$r.csv`n"
write-host "Usernames`tPasswords"
Write-Host "---------`t---------"
foreach ($i in $list) {
	"{0,-10}`t{1,-8}" -f $i.username,$i.password
	}
	Write-Host `n
foreach ($i in $list) {
	$scriptparams = ""
	$script = "win-user.bat"
	$scriptparams=$i.username+" "+$i.password
	$ctr = 0;
	$view = get-vm $vmname | get-view
	$ready = $view.guest.toolsstatus
	while ($ready -ne "toolsOk") {
		sleep 10
		$view = get-vm $vmname | get-view
		$ready = $view.guest.toolsstatus
		$ctr = $ctr + 1;
		if($ctr -eq 600) {
			write-host "Timed out waiting for VMWare tools to load on the VM."
			return
		}
	}
	
	copy-vmguestfile -source "\\$node\$sharename\$script" -destination $vmdest -vm $VM -localtoguest -guestuser $guser -guestpassword $gpassword -ToolsWaitSecs 200 -WarningAction SilentlyContinue
	$script = $vmdest + $script + " " + $scriptparams + " 2>NUL"
	Invoke-VMScript -VM $VM -GuestUser $guser -GuestPassword $gpassword -ScriptText $script -ScriptType $scripttype -ToolsWaitSecs 200 -WarningAction SilentlyContinue
	}
Disconnect-VIServer -Server $node -Force -Confirm:$false
Remove-PSSnapin VMware.VimAutomation.Core
return
} 
#AD Modify script
if($script -eq "win-ad-join.bat") {
	
	$file = "\\$node\$sharename\win-ad-join.bat"
	$ofile = "\\$node\$sharename\win-ad-join-orig.bat"
	#copy $ofile $file
	
	#echo "@ECHO OFF" | Out-File $file -Encoding "ASCII"
	"cd %SYSTEMROOT%\i386" | Out-File $file -Encoding ASCII
	"netsh int ip set dns `"Local Area Connection`" static %2 primary>NUL" |Out-File $file -Append -Encoding ASCII
	"netdom join %COMPUTERNAME% /Domain:$scriptparams /UserD:%1\Administrator /PasswordD:Windows-admin /Reboot:1" |Out-File $file -append -Encoding ASCII
	"rundll32 netplwiz.dll,ClearAutoLogon" | Out-File -Append $file -Encoding ASCII
	
	#Remove-Item $file;
	copy-vmguestfile -source $file -destination $vmdest -vm $vmname -localtoguest -guestuser $guser -guestpassword $gpassword -ToolsWaitSecs 200 -WarningAction SilentlyContinue;
	Write-Host "Running the following script: " $script
	$script = $script + " 2>NUL"
	Invoke-VMScript -VM $VM -GuestUser $guser -GuestPassword $gpassword -ScriptText $script -ScriptType $scripttype -ToolsWaitSecs 200 -WarningAction SilentlyContinue -ErrorAction SilentlyContinue
	Disconnect-VIServer -Server $node -Force -Confirm:$false 
	Remove-PSSnapin VMware.VimAutomation.Core
	return
	
}

if($script -eq "win-network.bat") {

$ctr = 0;
$view = get-vm $vmname | get-view
sleep 10
$ready = $view.guest.toolsstatus
while ($ready -ne "toolsOk") {

	sleep 10
	$view = get-vm $vmname | get-view
	$ready = $view.guest.toolsstatus
	$ctr = $ctr + 1;
	if($ctr -eq 600) {
		write-host "Timed out waiting for VMWare tools to load on the VM."
		exit
	}
}

copy-vmguestfile -source "\\$node\$sharename\$script" -destination $vmdest -vm $VM -localtoguest -guestuser $guser -guestpassword $gpassword -ToolsWaitSecs 200 -WarningAction SilentlyContinue
$script = $vmdest + $script + " " + $scriptparams
Write-Host "Running the following script: " $script
$script = $script + " 2>NUL"
Invoke-VMScript -VM $VM -GuestUser $guser -GuestPassword $gpassword -ScriptText $script -ScriptType $scripttype -ToolsWaitSecs 200 -WarningAction SilentlyContinue -ErrorAction SilentlyContinue

Get-Vm($vmname) | Restart-VMGuest >$null;

Disconnect-VIServer -Server $node -Force -Confirm:$false 
Remove-PSSnapin VMware.VimAutomation.Core
return


}



#Copy script to VM & invoke it
$ctr = 0;
$view = get-vm $vmname | get-view
sleep 10
$ready = $view.guest.toolsstatus
while ($ready -ne "toolsOk") {

	sleep 10
	$view = get-vm $vmname | get-view
	$ready = $view.guest.toolsstatus
	$ctr = $ctr + 1;
	if($ctr -eq 600) {
		write-host "Timed out waiting for VMWare tools to load on the VM."
		exit
	}
}

copy-vmguestfile -source "\\$node\$sharename\$script" -destination $vmdest -vm $VM -localtoguest -guestuser $guser -guestpassword $gpassword -ToolsWaitSecs 200 -WarningAction SilentlyContinue
$script = $vmdest + $script + " " + $scriptparams
Write-Host "Running the following script: " $script
$script = $script + " 2>NUL"
Invoke-VMScript -VM $VM -GuestUser $guser -GuestPassword $gpassword -ScriptText $script -ScriptType $scripttype -ToolsWaitSecs 200 -WarningAction SilentlyContinue -ErrorAction SilentlyContinue
Disconnect-VIServer -Server $node -Force -Confirm:$false 
Remove-PSSnapin VMware.VimAutomation.Core
return