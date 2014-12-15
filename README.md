android-testhotspot
===================

Android HotSpot Api and much more.
For Api Doc please visit <http://mady4ever.github.io/TestHotspotdoc/>
Or Webside <http://mady4ever.github.io/android-testhotspot>

1-wifiHotSpots.java:

Helps You To Connect To Hotspot ,
Get All Hotspots Around You,
Sort All Hotspots By Signle Level,
Start Device Local Hotspot ,
Start and Stop Scanning For Hotspots Or You Can Use Time For Scanning Periodically,
Check If Device is Connect To Hotspot ,
Check If Device Local Hotspot is turned on ,
Get Signal Frequency or Level…..

Some example codes. 
Following wifiHotSpots object is created like following.


`
wifiHotSpots hotutil =new wifiHotSpots(this);
`

Start HotSpot

`
					if(hotutil.startHotSpot(true)){
						Toast.makeText(getApplicationContext(), " Device HotSpot is Turned On", Toast.LENGTH_LONG).show();
					}else{
						Toast.makeText(getApplicationContext(), "Device HotSpot is Not Turned On", Toast.LENGTH_LONG).show();
					}
`

Stop HotSpot

`
					if(hotutil.startHotSpot(false)){
						Toast.makeText(getApplicationContext(), " Device HotSpot is Turned Off", Toast.LENGTH_LONG).show();
					}else{
						Toast.makeText(getApplicationContext(), "Device HotSpot is Not Turned Off", Toast.LENGTH_LONG).show();
					}
`

Edit/Set/Change HotSpot SSID and Password.

`
					if(hotutil.setHotSpot("SSID","PASSWORD")){
						Toast.makeText(getApplicationContext(), " SSID And PassWord Of Device HotSpot is Changed ", Toast.LENGTH_LONG).show();
					}else{
						Toast.makeText(getApplicationContext(), "SSID And PassWord Of Device HotSpot Not Chaged", Toast.LENGTH_LONG).show();
					}
`

Connect To Specific HotSpot.

`

					if(hotutil.connectToHotspot("SSID2", "123123123")){
						Toast.makeText(getApplicationContext(), " Device is Conected to This HotSpot ", Toast.LENGTH_LONG).show();
					}else{
						Toast.makeText(getApplicationContext(), "Device is Not Conected to This HotSpot", Toast.LENGTH_LONG).show();
					}

`

Add Wifi Network.

`
					hotutil.addWifiNetwork("ssid22", "pass", "WEP"); //Trird argument can be "WEP","OPEN","WAP"
`

Delete/Remove Wifi Network.

`
					hotutil.removeWifiNetwork("ssid22");
`

Scan Wifi NetWorks.


`

					 List<ScanResult> results = hotutil.getHotspotsList();
				      
				        for (ScanResult result : results) {
				            Toast.makeText(getApplicationContext(), result.SSID + " " + result.level,
				                    Toast.LENGTH_SHORT).show();
				            }

`


Sort HotSpots by N/w level.

`

					 List<ScanResult> results2 = hotutil.sortHotspotsByLevel();
				      
				        for (ScanResult result : results2) {
				            Toast.makeText(getApplicationContext(), result.SSID + " "+ result.level,
				                    Toast.LENGTH_SHORT).show();
				            }

`

Periodically Scan.

`
					hotutil.startScan(1,50000);
`

Stop Scan.

`
					hu.stopScan();  
`

Misc. methods.

`
					Toast.makeText(getApplicationContext(),"Security Mode"+ hu.getSecurityModeBySSID(wu.getSSID())+"\n"+"Signal Frequencey"+ Integer.toString(hu.getApfrequency(wu.getSSID()))+"\n"+"Signal Level"+Integer.toString(hu.getApSignalLevel(wu.getSSID()))+"\n"+"Capabilities"+ hu.getApCapabilities(wu.getSSID())+"\n", Toast.LENGTH_LONG).show();
					
`


2-wifiStatus.java :

Helps You To Check If Device Is Support Wifi or Wifi direct,
Check If Wifi Is Enabled Or Not ,
Turn On Off And Toggle Wifi,
Check If Device Is Connect To Internet Or Not ,
Get SSID,BSSID,RSSI,Link Speed,Signal Strength,
Get Type Of Connection Used By Mobile Now All This Just Use One Method checkWifi(int state)

Some examples of wifiStatus.
Considering wifiStatus object is created like following.

` 
WifiStatus wu = new WifiStatus(this);

`

Check Device Support Wifi.

`

					if(wu.checkWifi(wu.SUPPORT_WIFI)){
						Toast.makeText(getApplicationContext(), "Yes Device Support Wifi", Toast.LENGTH_LONG).show();
					}else{
						Toast.makeText(getApplicationContext(), "No Device Not Support Wifi", Toast.LENGTH_LONG).show();
					}
					

`

Check Device Support Wifi Direct.

`

					if(wu.checkWifi(wu.SUPPORT_WIFI_DIRECT)){
						Toast.makeText(getApplicationContext(), "Yes Device Support Wifi Direct", Toast.LENGTH_LONG).show();
					}else{
						Toast.makeText(getApplicationContext(), "No Device Not Support Wifi Wifi Direct", Toast.LENGTH_LONG).show();
					}
					

`

Check Wifi Is On.

`

					if(wu.checkWifi(wu.IS_WIFI_ON)){
						Toast.makeText(getApplicationContext(), "Yes Wifi Is Tured On", Toast.LENGTH_LONG).show();
					}else{
						Toast.makeText(getApplicationContext(), "Wifi Is Tured Off", Toast.LENGTH_LONG).show();
					}
					

`

Turn on WIFI.

`

					if(wu.checkWifi(wu.WIFI_ON)){
						Toast.makeText(getApplicationContext(), " Yes Wifi Is Tured On", Toast.LENGTH_LONG).show();
					}else{
						Toast.makeText(getApplicationContext(), "Wifi Not Tured On", Toast.LENGTH_LONG).show();
					}

`

Turn Off WIFI.

`

					if(wu.checkWifi(wu.WIFI_OFF)){
						Toast.makeText(getApplicationContext(), " Wifi Is Tured Off", Toast.LENGTH_LONG).show();
					}else{
						Toast.makeText(getApplicationContext(), "Wifi Not Tured Off", Toast.LENGTH_LONG).show();
					}

`

Check Wifi Connected to HotSpot.

`

					if(wu.checkWifi(wu.CONECT_HOTSPOT)){
						Toast.makeText(getApplicationContext(), "Yes  Device is Coneceting To Hostspot", Toast.LENGTH_LONG).show();
					}else{
						Toast.makeText(getApplicationContext(), "No  Device is Not Coneceting To Hostspot", Toast.LENGTH_LONG).show();
					}

`

Check Connected to Internet.

`

					if(wu.checkWifi(wu.CONECT_INTERNET)){
						Toast.makeText(getApplicationContext(), "Yes  Device is Coneceting To Internet", Toast.LENGTH_LONG).show();
					}else{
						Toast.makeText(getApplicationContext(), "No  Device is Not Coneceting To Internet", Toast.LENGTH_LONG).show();
					}

`

Check Device is Connected to Internet via Wifi.

`

					if(wu.checkWifi(wu.DATA_BY_WIFI)){
						Toast.makeText(getApplicationContext(), "Yes  Device is Coneceting Via Wifi", Toast.LENGTH_LONG).show();
					}else{
						Toast.makeText(getApplicationContext(), "No  Device is Not Coneceting Via Wifi", Toast.LENGTH_LONG).show();
					}

`

Misc. api's

`

					Toast.makeText(getApplicationContext(), "BSSID "+wu.getBSSID()+"\n"+"RSSI "+wu.getRSSI()+"\n"+"SSID "+wu.getSSID()+"\n"+"SIGNL STRENGHT "+wu.getSignalStrength()+"\n"+"LINK SPEED "+wu.getLinkSpeed()+"\n"+wu.getLinkSpeedUnits(), Toast.LENGTH_LONG).show();

`

3-wifiSocket.java :

Helps You To Send And Receive Text Message In Wifi Network, Send And Receive File In Wifi Network Using Socket


Examples:-

Considering object is created like following.

`
			WifiSocket ws  =  new WifiSocket(this);

`

Send Message to specific ip and port.

`

                	ws.sendMessage("192.168.1.102",5000,"Message from Heaven");

`

Receive Message at specifc Port.

`

                 	ws.receiveMessage(5000, 10, new Runnable() {

						@Override
						public void run() {
							((Activity)mContext).runOnUiThread(new Runnable() {
				                 
                 	    		public void run() {
                 	    	    	
                 	    	    	Toast.makeText(getApplicationContext(), ":::"+ws.receivedMessage+":::",
            			                    Toast.LENGTH_SHORT).show(); 
                 	    	    	
                 	    	    }
                 	    	});
                 	    	Log.i("Receive text :: ",":::"+ws.receivedMessage+":::" ); 
                 	    	
                 	    }			    	
                	});
                	
`


Send File.

`

            		ws.sendFile("192.168.1.102", 5000, Environment.getExternalStorageDirectory()+File.separator+"test.pdf");

`

Receive File.

`

                	ws.receiveFile(5000, Environment.getExternalStorageDirectory()+File.separator+"test.pdf");

`


4-wifiAddresses.java:

Helps You To Get All Devices Connect to HotSpot Get Gateway,
Device Ip Address Or MAC Address,
Ping IP Address And Get Result Of Pinging IP,
Get MAC Address From ARP File,
Check If IP Live Using DNS Or ARP File Or Using Socket And PORT,

Examples.

Considering object is created like.

`
wifiAddresses au = new wifiAddresses(this);
`
Gateway Ip/Mac Device Mac/Ip etc.

`

					Toast.makeText(getApplicationContext(),"GatWay IP:" +au.getGatewayIPAddress()+"\n"+"GatWay MAC"+au.getGatWayMacAddress()+"\n"+"Device IP"+au.getDeviceIPAddress()+"\n"+"Device MAC"+au.getDeviceMacAddress(),
		                    Toast.LENGTH_SHORT).show();

`

Ping to Specific Ip.

`

					if(au.pingCmd("192.168.1.101")){
						Toast.makeText(getApplicationContext(), "This IP is Live",
			                    Toast.LENGTH_SHORT).show();
						
					}else{
						Toast.makeText(getApplicationContext(), "No This IP Not Live",
			                    Toast.LENGTH_SHORT).show();
					}
					
`

Get Ping result.

`
					Toast.makeText(getApplicationContext(), au.getPingResulta("192.168.1.101"),
		                    Toast.LENGTH_SHORT).show();
		                    
`

Get Mac from arp request.

`
					Toast.makeText(getApplicationContext(), au.getArpMacAddress("192.168.1.101"),
		                    Toast.LENGTH_SHORT).show();
`


Check DNS is live.

`

					au.dnsLive("192.168.1.101",new Runnable() {
                 	    public void run() {
                 	    	
                 	    	((Activity)mContext).runOnUiThread(new Runnable() {
                 
                 	    		public void run() {
                 	    	    	if(au.isDnsLive){
                 	    	    		Toast.makeText(getApplicationContext(), "This IP is Live Using Dns Test",
                			                    Toast.LENGTH_SHORT).show(); 
                 	    	    	}else{
                 	    	    		Toast.makeText(getApplicationContext(), "This IP is Not Live Using Dns Test",
                			                    Toast.LENGTH_SHORT).show(); 
                 	    	    	}
                 	    	    	
                 	    	    	
                 	    	    }
                 	    	});
                 	    	 
                 	    	
                 	    }
                 	});
                 	

`


Check Port live.

`

					au.portLive("192.168.1.101",new Runnable() {
                 	    public void run() {
                 	    	
                 	    	((Activity)mContext).runOnUiThread(new Runnable() {
                 
                 	    		public void run() {
                 	    	    	if(au.isDnsLive){
                 	    	    		Toast.makeText(getApplicationContext(), "This IP is Live Using Socket with some Ports test ",
                			                    Toast.LENGTH_SHORT).show(); 
                 	    	    	}else{
                 	    	    		Toast.makeText(getApplicationContext(), "No This IP Not Live Using Socket with some Ports test",
                			                    Toast.LENGTH_SHORT).show(); 
                 	    	    	}
                 	    	    	
                 	    	    	
                 	    	    }
                 	    	});
                 	    	 
                 	    	
                 	    }
                 	});
                 	
`

Get All devices ip.

`

                	List<String> results3 = au.getAllDevicesIp();
				      
			        for (String result : results3) {
			            Toast.makeText(getApplicationContext(),result,
			                    Toast.LENGTH_SHORT).show();
			            }

`

Check Device is Rooted.

`

		au.CheckRoot();

`

Misc. 

`
//set static Ip.

public void setStaticIpInfo(String ip,String netMask,String gateWay,String dns1,String dns2)

`


1-wifiHotSpots.java:

– Method For Connecting to WiFi Network (hotspot)

– Method To Check if The Device Is Connected to Hotspot using wifi

– Method To Get hotspot Max Level of all Hotspots Around you

– Method To Sort All Hotspots Around you By Level

– Method To Get List of WIFI Networks (hotspots) Around you

– Method To Get and showing List of WIFI Networks (hotspots) Around you

– Method To turn ON/OFF a Local Device Access Point

– Method To Change SSID and Password of Local Device Access Point

– Method To shred all Configured wifi Networks in Device

– Method To shred Configured wifi Network By SSID

– Method To gets a list of the wifi profiles from the system and returns them

– Method To add Wifi Network in Device

– Method To Get WiFi password From wpa_supplicant.conf file By SSID

– Method To Get Ap Capabilities,Ap frequency,Ap Signal Level

– Method To Get Security Mode By Network SSID

2-wifiStatus.java :


– Method To Check if the Device Support Wifi or not

– Method To Check if Wifi Direct is Supported or not in this android version

– Method To Check if The Device Is Connected to Internet

– Method To Check if wifi is enabled

– Method To Check if The Device Is Connected to Hotspot using wifi

– Method To Run wifi Settings Activity Using Intent

– Method To Get SSID,BSSID,RSSI, Signal Strength,Link Speed,

– Method To Get type of connection used by mobile now

– Method to Get wifi Connection Status DISABLING,DISABLED,ENABLING,ENABLED,UNKNOWN


3-wifiSocket.java:


– Method To send message text in Wifi Network using socket

– Method To Receive message text From Wifi Network using socket

– Method To send File in Wifi Network using socket

– Method To Receive File From Wifi Network using socket


4-wifiAddresses.java:


– Method To Gets a list of all clients Ip Addresses connected to the Hotspot

– Method To Get Gateway Ip Address ,Device Ip Address

– Method To Get Device MAC Address,MAC Address of GatWay(BSSID)

– Method To Ping IP Address,Get Result of pinging IP Address

– Method To Get MAC Address From ARP File

– Method To Check if IP Live Using ARP

– Method To Check if IP Live Using DNS

– Method To Check if IP Live Using Socket And PORT

– Method To Gets a list of all clients Ip Addresses connected to the Hotspot from ARP file

– Method To Set static IP address, netmask, gateway

– Method To Manually Change static IP address, netmask, gateway


