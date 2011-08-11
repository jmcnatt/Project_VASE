# Invokes a script on a VM
# Author: James McNatt / Brent Kapral

#perform Powershell version check

if ($env:Processor_Architecture -ne "x86")

{

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

Connect-VIServer -Server $node -Protocol https -User $user -Password $password -WarningAction SilentlyContinue > $null

$VM = Get-VM($vmname);


#03 DC needs and inf file for the unattended install of AD.  This modifies the answer file and copies it to the VM
if($script -eq "win-03dc.bat"){  

	$ofile = "\\$node\share\03dc_orig.inf"
	$file = "\\$node\share\03dc.inf"
	copy $ofile $file;
	("NewDomainDNSName=" + $scriptparams) | Out-File -append $file;
	
	$netbios = $scriptparams.substring(0, ($scriptparams.indexof('.'))); #create netbios name, limit to 15 chars, make it uppercase
	if ($netbios.length -gt 15) {
		$netbios = $netbios.substring(0,15);
	}
	$netbios = $netbios.ToUpper();
	("DomainNetbiosName=" + $netbios) | Out-File -Append $file;
	Write-Host $netbios
	copy-vmguestfile -source $file -destination $vmdest -vm $vmname -localtoguest -guestuser $guser -guestpassword $gpassword -WarningAction SilentlyContinue;
	Remove-Item $file;
}
#08 DC needs and inf file for the unattended install of AD.  This modifies the answer file and copies it to the VM
if($script -eq "win-08dc.bat"){
	$ofile = "\\$node\share\08dc_orig.inf"
	$file = "\\$node\share\08dc.inf"
	copy $ofile $file;
	("NewDomainDNSName=" + $scriptparams) | Out-File -append $file;
	copy-vmguestfile -source $file -destination $vmdest -vm $vmname -localtoguest -guestuser $guser -guestpassword $gpassword -WarningAction SilentlyContinue;
	Remove-Item $file; 
}


#IIS needs additional files.  This calls another powershell script that copies those files to the VM
if($script -eq "win-iis.bat") {
copy-vmguestfile -source "$node\share\iis\chglist.vbs" -destination $vmdest -vm $VM -localtoguest -guestuser $guser -guestpassword $gpassword;
copy-vmguestfile -source "$node\share\iis\fcgisetup_1.5_rtw_x86.msi" -destination $vmdest -vm $VM -localtoguest -guestuser $guser -guestpassword $gpassword;
copy-vmguestfile -source "$node\share\iis\iis.inf" -destination $vmdest -vm $VM -localtoguest -guestuser $guser -guestpassword $gpassword;
copy-vmguestfile -source "$node\share\iis\php.ini" -destination $vmdest -vm $VM -localtoguest -guestuser $guser -guestpassword $gpassword;
copy-vmguestfile -source "$node\share\iis\php-5.3.6-nts-Win32-VC9-x86.msi" -destination $vmdest -vm $VM -localtoguest -guestuser $guser -guestpassword $gpassword;
copy-vmguestfile -source "$node\share\iis\setDir.reg" -destination $vmdest -vm $VM -localtoguest -guestuser $guser -guestpassword $gpassword;
copy-vmguestfile -source "$node\share\iis\test.php" -destination $vmdest -vm $VM -localtoguest -guestuser $guser -guestpassword $gpassword;
}

#DNS server copy files
if($script -eq "win-dns.bat") {
$file = "\\$node\share\dns\dns.inf"
copy-vmguestfile -source $file -destination $vmdest -vm $vmname -localtoguest -guestuser $guser -guestpassword $gpassword -WarningAction SilentlyContinue;
$file = "\\$node\share\dns\dnscmd.exe"
copy-vmguestfile -source $file -destination $vmdest -vm $vmname -localtoguest -guestuser $guser -guestpassword $gpassword -WarningAction SilentlyContinue;
}

#DHCP server copy files
if($script -eq "win-dhcp.bat") {
$file = "\\$node\share\dhcp\dhcp.inf"
Copy-VMGuestFile -Source $file -Destination $vmdest -VM $vmname -LocalToGuest -Guestuser $guser -GuestPassword $gpassword -WarningAction SilentlyContinue;
}

if($script -eq "win-user.bat") {
#[Reflection.Assembly]::LoadWithPartialName(“System.Web”) > $null -NEEDED TO GENERATE RANDOM PASSWORDS FOR ACCOUNTS
$r = Get-Random -Maximum ("10") #Max # must be equal to number of users.csv files
$list = Import-Csv \\$node\share\users\users$r.csv
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
	copy-vmguestfile -source "\\$node\share\$script" -destination $vmdest -vm $VM -localtoguest -guestuser $guser -guestpassword $gpassword -WarningAction SilentlyContinue
	$script = $vmdest + $script + " " + $scriptparams + " 2>NUL"
	Invoke-VMScript -VM $VM -GuestUser $guser -GuestPassword $gpassword -ScriptText $script -ScriptType $scripttype -WarningAction SilentlyContinue
	}
Disconnect-VIServer -Server $node -Force -Confirm:$false
Remove-PSSnapin VMware.VimAutomation.Core
Write-Host "`nEND_SCRIPT"
return
} 


#Copy script to VM & invoke it
copy-vmguestfile -source "\\$node\share\$script" -destination $vmdest -vm $VM -localtoguest -guestuser $guser -guestpassword $gpassword -WarningAction SilentlyContinue
$script = $vmdest + $script + " " + $scriptparams + " 2>NUL"

Write-Host "Running the following script: " $script

Invoke-VMScript -VM $VM -GuestUser $guser -GuestPassword $gpassword -ScriptText $script -ScriptType $scripttype -WarningAction SilentlyContinue
Disconnect-VIServer -Server $node -Force -Confirm:$false
Remove-PSSnapin VMware.VimAutomation.Core
return