/* 
 * Copyright (C) 2013-2014 www.Andbrain.com 
 * Faster and more easily to create android apps
 * 
 * */
package com.mady.wifi.api;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiConfiguration;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.widget.ListView;
import android.widget.Toast;


public  class wifiHotSpots {
	WifiManager mWifiManager;
    WifiInfo  mWifiInfo ;
    Context mContext;
    List<ScanResult> mResults;
    ListView mNetworksList;
    ScanResultsAdapter mAdapter;
	WifiReceiver mReceiver;
	Timer mTimer;
	public static boolean isConnectToHotSpotRunning=false;
	 
	public  wifiHotSpots(Context c) {
		  mContext=c;
		  mWifiManager=(WifiManager)  mContext.getSystemService(Context.WIFI_SERVICE);
		  mWifiInfo = mWifiManager.getConnectionInfo();	

	}
	
	/**
     * Method for Connecting  to WiFi Network (hotspot)
     * 
     * @param netSSID of WiFi Network (hotspot)
     * @param netPass  put password or  "" for open network
     * 
     * return true if connected to hotspot successfully
     */
    public boolean connectToHotspot(String netSSID, String netPass) {
    	
    	isConnectToHotSpotRunning= true;
        WifiConfiguration wifiConf = new WifiConfiguration();
        List<ScanResult> scanResultList=mWifiManager.getScanResults();
        
        if(mWifiManager.isWifiEnabled()){
        		
        for (ScanResult result : scanResultList) {
        	
            if (result.SSID.equals(netSSID)) {
            
            	removeWifiNetwork(result.SSID);
                String mode = getSecurityMode(result);
                
            if (mode.equalsIgnoreCase("OPEN")) {

            	wifiConf.SSID = "\"" + netSSID + "\"";
		        wifiConf.allowedKeyManagement.set(WifiConfiguration.KeyMgmt.NONE);
	            int res = mWifiManager.addNetwork(wifiConf);
	            mWifiManager.disconnect();
		        mWifiManager.enableNetwork(res, true);
		        mWifiManager.reconnect();
	            mWifiManager.setWifiEnabled(true);
	            isConnectToHotSpotRunning=false;
	            return true;
	                    
	          } else if (mode.equalsIgnoreCase("WEP")) {
                    	
                    	wifiConf.SSID = "\"" + netSSID + "\"";
                    	wifiConf.wepKeys[0] = "\"" + netPass + "\"";
                    	wifiConf.wepTxKeyIndex = 0;
                    	wifiConf.allowedKeyManagement.set(WifiConfiguration.KeyMgmt.NONE);
                    	wifiConf.allowedGroupCiphers.set(WifiConfiguration.GroupCipher.WEP40);
                        int res = mWifiManager.addNetwork(wifiConf);
                        mWifiManager.disconnect();
		                mWifiManager.enableNetwork(res, true);
		                mWifiManager.reconnect();
                        mWifiManager.setWifiEnabled(true);
                        isConnectToHotSpotRunning=false;
                        return true;
                        
                           }else{
                        	   
                	    wifiConf.SSID = "\"" + netSSID + "\"";
                	    wifiConf.preSharedKey = "\"" + netPass + "\"";
                	    wifiConf.hiddenSSID = true;
	                	wifiConf.status = WifiConfiguration.Status.ENABLED;
	                	wifiConf.allowedGroupCiphers.set(WifiConfiguration.GroupCipher.TKIP);
	                	wifiConf.allowedGroupCiphers.set(WifiConfiguration.GroupCipher.CCMP);
	                	wifiConf.allowedKeyManagement.set(WifiConfiguration.KeyMgmt.WPA_PSK);
	                	wifiConf.allowedPairwiseCiphers.set(WifiConfiguration.PairwiseCipher.TKIP);
	                	wifiConf.allowedPairwiseCiphers.set(WifiConfiguration.PairwiseCipher.CCMP);
	                	wifiConf.allowedProtocols.set(WifiConfiguration.Protocol.RSN);
	                	wifiConf.allowedProtocols.set(WifiConfiguration.Protocol.WPA);
		                int res = mWifiManager.addNetwork(wifiConf);
		                mWifiManager.disconnect();
		                mWifiManager.enableNetwork(res, true);
		                mWifiManager.reconnect();
		             	mWifiManager.saveConfiguration();  
	 	                mWifiManager.setWifiEnabled(true);
	 	               isConnectToHotSpotRunning=false;
	 	               return true;
	                      
                }
            }
        }
        }
        isConnectToHotSpotRunning=false;
        return false;
    }
   /**
     * Check if The Device Is Connected to Hotspot using wifi
     * 
     * @return true if device connect to Hotspot 
     */
    public boolean  isConnectedToAP(){
		ConnectivityManager connectivity = (ConnectivityManager)mContext
		        .getSystemService(Context.CONNECTIVITY_SERVICE);
		if (connectivity != null) {
		    NetworkInfo info = connectivity.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
		    if (info != null) {
		        if (info.isConnected()) {
			            return true;
		        }
		    }
		}
		return false;
	}
    /**
     * Method to Get hotspot Max Level of all Hotspots Around you
     * 
     * @return a highest level hotspot 
     */
    public ScanResult getHotspotMaxLevel(){
    	List<ScanResult> hotspotList=mWifiManager.getScanResults();
    	  if (hotspotList != null) {
	            final int size = hotspotList.size();
		            if (size == 0){ 
	            	return null;
	                } else {
	                ScanResult maxLevel = hotspotList.get(0);
	                for (ScanResult result : hotspotList) {
	                      if (WifiManager.compareSignalLevel(maxLevel.level,
	                            result.level) < 0) {
	                        maxLevel = result;
	                    }
	                }
	                return maxLevel;
	            }
	        }else{
	        	return null;
	        }
	    }
    /**
     * Method to Get hotspot Max Level of all Hotspots in hotspotList list 
     * 
     * @param  hotspotList list of Hotspots 
     * @return a highest level hotspot 
     */
    public ScanResult getHotspotMaxLevel(List<ScanResult> hotspotList){
    	
    	  if (hotspotList != null) {
	            final int size = hotspotList.size();
		            if (size == 0){ 
	            	return null;
	            } else {
	                ScanResult maxSignal = hotspotList.get(0);
	              
	                for (ScanResult result : hotspotList) {
	                      if (WifiManager.compareSignalLevel(maxSignal.level,
	                            result.level) < 0) {
	                        maxSignal = result;
	                    }
	                }
	                return maxSignal;
	            }
	        }else{
	        	return null;
	        }
	        
	    }
    /**
     * sort All  Hotspots Around you By Level
     * 
     * @return sorted hotspots List
     */
    public List<ScanResult> sortHotspotsByLevel(){
    	List<ScanResult> hotspotList=mWifiManager.getScanResults();
    	List<ScanResult> sorthotspotsList=new ArrayList<ScanResult>();
    	ScanResult result;
  	    while(!hotspotList.isEmpty()){
  	        result=getHotspotMaxLevel(hotspotList); 
  	        sorthotspotsList.add(result);
  	        hotspotList.remove(result);  
  	    }
  	              
	    return sorthotspotsList;
    }
    /**
     * sort Hotspots in hotspotList By Level
     * 
     * @return sorted hotspots List
     */
    public List<ScanResult> sortHotspotsByLevel(List<ScanResult> hotspotList){
    	List<ScanResult> hotspotList2=hotspotList;
    	List<ScanResult> sorthotspotsList=new ArrayList<ScanResult>();
    	ScanResult result;
  	    while(!hotspotList2.isEmpty()){
  	         result=getHotspotMaxLevel(hotspotList2); 
  	         sorthotspotsList.add(result);
  	         hotspotList2.remove(result);  
  	    }
 	    return sorthotspotsList;
	   }
    /**
     * Method to Get  List of  WIFI Networks (hotspots) Around you
     * 
     * @return List  of networks (hotspots)
     */
    public List<ScanResult> getHotspotsList(){
    	
    	if(mWifiManager.isWifiEnabled()) {
    	 	
    	 	if(mWifiManager.startScan()){
    	 		return mWifiManager.getScanResults();	
    	 	}
    		
		}
	return null;
    }
   /**
     * Method to Get and showing  List of  WIFI Networks (hotspots) Around you
     * 
     * @param List a listview for showing list of networks (hotspots)
     */
	public void showHotspotsList(ListView List){
		if(mWifiManager.isWifiEnabled()) {
			mReceiver = new WifiReceiver();
			scanNetworks();
			mNetworksList = mNetworksList==null ? List : mNetworksList;
			if(mResults!=null){
			mAdapter = new ScanResultsAdapter(mContext,mResults);
			mNetworksList.setAdapter(mAdapter);
			}
		} 
		else Toast.makeText(mContext,"wifi is not enabled", Toast.LENGTH_LONG).show();
	}
	
    public void scanNetworks() {
		boolean scan = mWifiManager.startScan();
		
		if(scan) {
			mResults = mWifiManager.getScanResults();
			
		} else
			switch(mWifiManager.getWifiState()) {
			case WifiManager.WIFI_STATE_DISABLING:
				Toast.makeText(mContext,"wifi disabling", Toast.LENGTH_LONG).show();
				break;
			case WifiManager.WIFI_STATE_DISABLED:
				Toast.makeText(mContext, "wifi disabled", Toast.LENGTH_LONG).show();
				break;
			case WifiManager.WIFI_STATE_ENABLING:
				Toast.makeText(mContext, "wifi enabling", Toast.LENGTH_LONG).show();
				break;
			case WifiManager.WIFI_STATE_ENABLED:
				Toast.makeText(mContext, "wifi enabled", Toast.LENGTH_LONG).show();
				break;
			case WifiManager.WIFI_STATE_UNKNOWN:
				Toast.makeText(mContext,"wifi unknown state", Toast.LENGTH_LONG).show();
				break;
			}

	}
   
    /**
     * 
     */
	
	
 	class WifiReceiver extends BroadcastReceiver {

		public List<ScanResult> getResults() {
			return mResults;
		}

		public WifiManager getManager() {
			return mWifiManager;
		}

		@Override
		public void onReceive(Context c, Intent intent) {
			mResults = mWifiManager.getScanResults();
			mAdapter = new ScanResultsAdapter(mContext, mResults);
			mNetworksList.setAdapter(mAdapter);
			mAdapter.notifyDataSetChanged();
			
		}
	}
 	/**
     * Method to turn ON/OFF a  Access Point  
     * 
     * @param enable Put true if you want to start  Access Point  
     * @return true if AP is started
     */
    public boolean startHotSpot(boolean enable) {
    	mWifiManager.setWifiEnabled(false);
        Method[] mMethods = mWifiManager.getClass().getDeclaredMethods();
        for (Method mMethod : mMethods) {
            if (mMethod.getName().equals("setWifiApEnabled")) {
                try {
                    mMethod.invoke(mWifiManager, null, enable);
                    return true;
                } catch (Exception ex) {
                }
                break;
            }
        }
        return false;
    }
    /**
     * Method to Change SSID and Password of Device Access Point and Start HotSpot 
     * 
     * @param SSID a new SSID of your Access Point
     * @param passWord a new password you want for your Access Point
     */
    public boolean setAndStartHotSpot(boolean enable, String SSID)
    {
    	//For simple implementation I am creating the open hotspot.
    	Method[] mMethods = mWifiManager.getClass().getDeclaredMethods();
    	for(Method mMethod: mMethods){
    		{
    			if(mMethod.getName().equals("setWifiApEnabled")) {
    				WifiConfiguration netConfig = new WifiConfiguration();
    				netConfig.SSID = SSID;
    				netConfig.allowedKeyManagement.set(WifiConfiguration.KeyMgmt.NONE);
    				try{
    				mMethod.invoke(mWifiManager, netConfig,true);
    				}catch(Exception e)
    				{
    					return false;
    				}
    				startHotSpot(enable);
    			}
    		}
    	}
    	return enable;
    }
   /**
     * Method to Change SSID and Password of Device Access Point 
     * 
     * @param SSID a new SSID of your Access Point
     * @param passWord a new password you want for your Access Point
     */
    public boolean setHotSpot(String SSID,String passWord){
    	/*
    	 * Before setting the HotSpot with specific Id delete the default AP Name.
    	 */
    	/*
    		List<WifiConfiguration> list = mWifiManager.getConfiguredNetworks();
    	 for( WifiConfiguration i : list ) {
    	  if(i.SSID != null && i.SSID.equals(SSID)) {
    	     //wm.disconnect();
    	     //wm.enableNetwork(i.networkId, true);
    	     //wm.reconnect();                
    		  //mWifiManager.disableNetwork(i.networkId);
    		  mWifiManager.removeNetwork(i.networkId);
    		  mWifiManager.saveConfiguration();
    	     break;
    	  }
    	 }
    	*/
    		//mWifiManager.acquire();
     	    Method[] mMethods = mWifiManager.getClass().getDeclaredMethods();
    	  
    	    for(Method mMethod: mMethods){
    	     
    	        if(mMethod.getName().equals("setWifiApEnabled")) {
    	            WifiConfiguration netConfig = new WifiConfiguration();
    	            if(passWord==""){
    	            	netConfig.SSID = SSID;
        	            netConfig.allowedAuthAlgorithms.set(WifiConfiguration.AuthAlgorithm.OPEN);
        	            netConfig.allowedProtocols.set(WifiConfiguration.Protocol.RSN);
        	            netConfig.allowedProtocols.set(WifiConfiguration.Protocol.WPA);
        	            netConfig.allowedKeyManagement.set(WifiConfiguration.KeyMgmt.NONE); 	
    	            }else{
      	            netConfig.SSID = SSID ;
    	            netConfig.preSharedKey = passWord;
    	            netConfig.hiddenSSID = true;
    	            netConfig.status = WifiConfiguration.Status.ENABLED;
    	            netConfig.allowedGroupCiphers.set(WifiConfiguration.GroupCipher.TKIP);
    	            netConfig.allowedGroupCiphers.set(WifiConfiguration.GroupCipher.CCMP);
    	            netConfig.allowedKeyManagement.set(WifiConfiguration.KeyMgmt.WPA_PSK);
    	            netConfig.allowedPairwiseCiphers.set(WifiConfiguration.PairwiseCipher.TKIP);
    	            netConfig.allowedPairwiseCiphers.set(WifiConfiguration.PairwiseCipher.CCMP);
    	            netConfig.allowedProtocols.set(WifiConfiguration.Protocol.RSN);
    	            netConfig.allowedProtocols.set(WifiConfiguration.Protocol.WPA);
    	            }
    	            try {    	       
    	                mMethod.invoke(mWifiManager, netConfig,true);
    	                mWifiManager.saveConfiguration();
    	                return true;
    	           
    	            } catch (Exception e) {
    	              
    	            }
    	        }
    	    }
    	    return false; 
      }
 	 /**
     * @return true if Wifi Access Point Enabled
     */
    public boolean isWifiApEnabled() {
        try {
            Method method = mWifiManager.getClass().getMethod("isWifiApEnabled");
            return (Boolean)method.invoke(mWifiManager);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }
   /**
     * shred all  Configured wifi Networks
     */
    public boolean shredAllWifi(){
    	Context context =  mContext;
    	 mWifiManager = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
    	
    	if( mWifiInfo != null ){
    	    for(WifiConfiguration conn:  mWifiManager.getConfiguredNetworks()){
    	    	 mWifiManager.removeNetwork(conn.networkId);
    	    }
    	    
    	    mWifiManager.saveConfiguration(); 
    	    return true;
    	}
    	return false;
	}
    /**
     * This gets a list of the wifi profiles from the system and returns them.
     * @return List<WifiConfigurationg> : a list of all the profile names.
     */
    public ArrayList<WifiConfiguration> getProfiles(){
    	ArrayList<WifiConfiguration> profileList =new ArrayList<WifiConfiguration>();
      	if( mWifiInfo != null ){ 
    	    for(WifiConfiguration conn: mWifiManager.getConfiguredNetworks()){
    	    	profileList.add(conn);
    	    	  }
    	}
    	return profileList;
    }
    /**
     * Method to add Wifi Network
     * 
     * @param netSSID of WiFi Network (hotspot)
     * @param netPass  put password 
     * @param netType Network Security Type   OPEN PSK EAP OR WEP
     */
    public void addWifiNetwork(String netSSID, String netPass,String netType) {
    	 
    	  WifiConfiguration wifiConf = new WifiConfiguration();
               if (netType.equalsIgnoreCase("OPEN")) {

              			wifiConf.SSID = "\"" + netSSID + "\"";
  		            	wifiConf.allowedKeyManagement.set(WifiConfiguration.KeyMgmt.NONE);
  	                    mWifiManager.addNetwork(wifiConf);
  	                    mWifiManager.saveConfiguration(); 
  	                    
  	                } else if (netType.equalsIgnoreCase("WEP")) {
                      	
                     	wifiConf.SSID = "\"" + netSSID + "\"";
                      	wifiConf.wepKeys[0] = "\"" + netPass + "\"";
                      	wifiConf.wepTxKeyIndex = 0;
                      	wifiConf.allowedKeyManagement.set(WifiConfiguration.KeyMgmt.NONE);
                      	wifiConf.allowedGroupCiphers.set(WifiConfiguration.GroupCipher.WEP40);
                        mWifiManager.addNetwork(wifiConf);
                        mWifiManager.saveConfiguration(); 
                          
  	                	
                             }else{
                          	   
                  	    wifiConf.SSID = "\"" + netSSID + "\"";
                  	    wifiConf.preSharedKey = "\"" + netPass + "\"";
                  	    wifiConf.hiddenSSID = true;
  	                	wifiConf.status = WifiConfiguration.Status.ENABLED;
  	                	wifiConf.allowedGroupCiphers.set(WifiConfiguration.GroupCipher.TKIP);
  	                	wifiConf.allowedGroupCiphers.set(WifiConfiguration.GroupCipher.CCMP);
  	                	wifiConf.allowedKeyManagement.set(WifiConfiguration.KeyMgmt.WPA_PSK);
  	                	wifiConf.allowedPairwiseCiphers.set(WifiConfiguration.PairwiseCipher.TKIP);
  	                	wifiConf.allowedPairwiseCiphers.set(WifiConfiguration.PairwiseCipher.CCMP);
  	                	wifiConf.allowedProtocols.set(WifiConfiguration.Protocol.RSN);
  	                	wifiConf.allowedProtocols.set(WifiConfiguration.Protocol.WPA);
  		                mWifiManager.addNetwork(wifiConf);
  		               	mWifiManager.saveConfiguration();  
  	 	               
  	                      
                  }
              
          
            
    }
    
    /**
     *shred  Configured wifi Network By SSID
     * @param ssid of wifi Network
     */
    public void removeWifiNetwork(String ssid) {
        List<WifiConfiguration> configs = mWifiManager.getConfiguredNetworks();
        if (configs != null) {
            for (WifiConfiguration config : configs) {
                if (config.SSID.contains(ssid)) {
                    mWifiManager.disableNetwork(config.networkId);
                    mWifiManager.removeNetwork(config.networkId);
                }
            }
        }
        mWifiManager.saveConfiguration();
    }
    /**
     * get Connection Info
     * @return WifiInfo
     */
    public WifiInfo getConnectionInfo() {
        return mWifiManager.getConnectionInfo();
    }
    /**
  	 * Get WiFi password From wpa_supplicant.conf file By SSID
  	 * 
  	 * @param SSID
  	 *            
  	 */
    boolean gotRoot=false;
  	public String getWifiPassword(String SSID) {
    		File wpaFile = new File(mContext.getCacheDir(), "wpa_supplicant.conf");
  		if (!wpaFile.exists()) {
  			CheckRoot();
  			if (this.gotRoot ){
  				final String command = "cat /data/misc/wifi/wpa_supplicant.conf"
  						+ " > "
  						+ wpaFile.getAbsolutePath()
  						+ "\n chmod 666 "
  						+ wpaFile.getAbsolutePath();
  				if (!runAsRoot(command)) {
  					
  					this.gotRoot = false;
  					return null;
  				}
  			} else {
  				return null;
  			}
  		}
  		wpaFile = new File(wpaFile.getAbsolutePath());
  		if (!wpaFile.exists()) {
  			Toast.makeText(mContext,"error read wpa_supplicant.conf file", Toast.LENGTH_LONG).show();
  			return null;
  		}
  		try {
  			@SuppressWarnings("resource")
			BufferedReader bufRead = new BufferedReader(new FileReader(wpaFile));
  			String line;
  			StringBuffer stringBuf = new StringBuffer();
  			while ((line = bufRead.readLine()) != null) {
  				if (line.startsWith("network=") || line.equals("}")) {
  					String config = stringBuf.toString();
  					if (config.contains("ssid=" + SSID)) {
  						int i = config.indexOf("wep_key0=");
  						int len;
  						if (i < 0) {
  							i = config.indexOf("psk=");
  							len = "psk=".length();
  						} else {
  							len = "wep_key0=".length();
  						}
  						if (i < 0) {
  							return null;
  						}
 						return config.substring(i + len + 1,
  								config.indexOf("\n", i) - 1);

  					}
  					stringBuf = new StringBuffer();
  				}
  				stringBuf.append(line + "\n");
  			}
  			bufRead.close();	
  		} catch (Exception e) {
   			Toast.makeText(mContext, "error read wpa_supplicant.conf file", Toast.LENGTH_LONG)
  					.show();
  			return null;
  		}
  		return null;
  	}
   	/**
   	 * 
   	 */
    	 
	public void CheckRoot()
    {                    
        Process pro;
        try {  
               pro = Runtime.getRuntime().exec("su");   
               DataOutputStream outStr = new DataOutputStream(pro.getOutputStream()); 
               
               outStr.writeBytes("echo \"salam alikoum\" >/data/Test.txt\n");  
               outStr.writeBytes("exit\n");  
               outStr.flush();  
               
               try {  
                  pro.waitFor();  
                       if (pro.exitValue() == 0) {  
                          this.gotRoot=true;
                       }  
                       else {  
                            this.gotRoot=false;  
                       }  
               } catch (InterruptedException e) {  
            	   this.gotRoot=false;
               }  
            } catch (IOException e) {  
             	 this.gotRoot=false;
            }  
    }
   	/**
	 * Run command as root.
	 * 
	 * @param command
	 * @return true, if command was successfully executed
	 */
	private static boolean runAsRoot(final String command) {
		try {
								
			Process pro = Runtime.getRuntime().exec("su");
			DataOutputStream outStr = new DataOutputStream(pro.getOutputStream());
			
			 outStr.writeBytes(command);
			 outStr.writeBytes("\nexit\n");
			 outStr.flush();
			 
			int retval = pro.waitFor();
			
			return (retval == 0);
			
		    } catch (Exception e) {
		    	
			return false;
			
		    }
	}
	 /**
  	 * Method to Get Ap Capabilities
  	 * 
  	 * @param SSID Name of HotSpot
  	 * @return String contain Ap Capabilities
  	 */
    public String getApCapabilities(String mSSID){
    	scanNetworks();
	      for (ScanResult r : mResults) {
         if(r.SSID.equals(mSSID)){
    	     return r.capabilities;
    	     }
    	   }
    	 
    	return null;
 } 
    /**
  	 * Method to Get Ap frequency
  	 * 
  	 * @param SSID Name of HotSpot
  	 * @return int contain Link Speed
  	 */
    public int getApfrequency(String mSSID){
    	scanNetworks();
 	      for (ScanResult r : mResults) {
           if(r.SSID.equals(mSSID)){
      	     return r.frequency;
      	     }
      	   }
      	 
	    	return 0;
 } 
   
    
    /**
  	 * Method to Get Ap Signal Level
  	 * 
  	 * @param SSID Name of HotSpot
  	 * @return int contain Link Speed
  	 */
    public int getApSignalLevel(String mSSID){
       scanNetworks();
	      for (ScanResult r : mResults) {
         if(r.SSID.equals(mSSID)){
    	     return r.level;
    	     }
    	   }
    	 
    	return 0;
 } 
    /**
     * Method to Get Security Mode By Network SSID
     * 
     * @param SSID Name of HotSpot
     * @return OPEN PSK EAP OR WEP
     */
   public  String getSecurityModeBySSID(String SSID){
	   
	   List<ScanResult> scanResultList=mWifiManager.getScanResults();
       
       if(mWifiManager.isWifiEnabled()){
       		
       for (ScanResult result : scanResultList) {
       	
           if (result.SSID.equals(SSID)) {
        	   return getSecurityMode(result);
        	  
           }
       }
       
       }
	  return null; 
   } 
   /**
    * Method to Get Network Security Mode
    * 
    * @param scanResult 
    * @return OPEN PSK EAP OR WEP
    */
   public String getSecurityMode(ScanResult scanResult) {
       final String cap = scanResult.capabilities;
       final String[] modes = {"WPA", "EAP","WEP" };
        for (int i = modes.length - 1; i >= 0; i--) {
           if (cap.contains(modes[i])) {
               return modes[i];
           }
       }
      return "OPEN";
   }
   ScanTimer twoSecondTimer;
   public void startScan(long interval, long duration){
	   twoSecondTimer = new ScanTimerSimple(interval, duration,mContext);
		//Start the timer.
		twoSecondTimer.start();
   }
   public void stopScan(){
	   twoSecondTimer.cancel();
   }
   
 
}
