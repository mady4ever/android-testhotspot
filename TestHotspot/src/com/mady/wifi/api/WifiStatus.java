/* 
 * Copyright (C) 2013-2014 www.Andbrain.com 
 * Faster and more easily to create android apps
 * 
 * 
 *  <uses-permission android:name="android.permission.ACCESS_WIFI_STATE"/>
    <uses-permission android:name="android.permission.ACCESS_NETWORK_STATE" />
    <uses-permission android:name="android.permission.CHANGE_WIFI_STATE"/>
    <uses-permission android:name="android.permission.INTERNET"/> 
 * */
package com.mady.wifi.api;


import java.io.IOException;


import java.lang.reflect.Method;

import java.util.List;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;




public class WifiStatus {
	Context mContext;
	WifiManager mWifiManager;
    WifiInfo  mWifiInfo ;
    List<ScanResult> mResults;
    public static int TYPE_WIFI = 1;
	public static int TYPE_MOBILE = 2;
	public static int TYPE_NOT_CONNECTED = 0;
	public final int IS_WIFI_ON=0;
	public final int WIFI_ON=1;
	public final int WIFI_OFF=2;
	public final int WIFI_TOGGLE=3;
	public final int SUPPORT_WIFI=4;
	public final int SUPPORT_WIFI_DIRECT=5;
	public final int CONECT_HOTSPOT=6;
	public final int CONECT_INTERNET=7;
	public final int DATA_BY_WIFI=8;
	
	
    
	 public WifiStatus(Context c) {
		 mContext=c;
		 mWifiManager=(WifiManager)  mContext.getSystemService(Context.WIFI_SERVICE);
	     mWifiInfo = mWifiManager.getConnectionInfo();
	}	
	   /**
		 * Method to Check if the Device Support  Wifi or not 
		 * 
		 * @return true if Wifi supported or false if Wifi not supported
		 */
	  public boolean isSupportWifi() {
		    PackageManager pm = mContext.getPackageManager();
		    if (pm.hasSystemFeature(PackageManager.FEATURE_WIFI)) {
		        return true;
		    } else {
		        return false;
		    }
		}
	  /**
		 * Method to Check if Wifi Direct is  Supported  or not in this android version
		 * 
		 * @return true if Wifi Direct is supported or false if Wifi Direct not supported
		 */
	  public boolean isSupportWifiDirect() {
		  PackageManager pm = mContext.getPackageManager();
		    if (pm.hasSystemFeature(PackageManager.FEATURE_WIFI_DIRECT)) {
		        return true;
		    } else {
		        return false;
		    }
		}
		/**
		 * Method to Check if wifi is enabled
		 * 
		 * @return true if wifi enabled or false if wifi Disabled 
		 */
	  public boolean istWifiEnabled() {
			if (!mWifiManager.isWifiEnabled() ) {
				  return false;
			 }else{
			      return true;  	   
			  }
	  }
	  /**
	     * Check if The Device Is Connected to Hotspot using wifi
	     * 
	     * @return true if device connect to Hotspot or return false if not
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
		 * Method to Turn wifi ON without Progress dialog
		 */
	  public boolean setWifiEnabled(){
			   if(!istWifiEnabled()){
				   mWifiManager.setWifiEnabled(true);
				   return true;
		        }else{
			       return false;
			     	  }
		        }
		/**
		 * check if wifi disable and Show Progress dialog  to turn on wifi
		 */
	  public void wifiEnableDialog()
	    	{
	    		if(!mWifiManager.isWifiEnabled())
	    		{
	   			final ProgressDialog progDialog = new ProgressDialog(mContext);
	   			progDialog.setMessage("Turning Wifi ON");
	   			progDialog.setTitle("WiFi");
	    			AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
	    			builder.setMessage("Do you want to turn ON Wifi ")
	    					.setCancelable(false)
	    					.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
	    						
	    						public void onClick(DialogInterface dialog, int which) {
	    							mWifiManager.setWifiEnabled(true);
	    							progDialog.show();
	    							new Thread() {
	    								public void run() {
	    									try{
	    										while (!mWifiManager.isWifiEnabled()) {
	    											   
	    											sleep(100);} 
	    										progDialog.dismiss();
	    									} catch (Exception e) {} 
	    									
	    								}
	    							}.start();
	    						}
	    					})
	    					.setNegativeButton("No", new DialogInterface.OnClickListener() {
	    						public void onClick(DialogInterface dialog, int which) {
	    						}
	    					});
	    			AlertDialog dialog = builder.create();
	    			dialog.show();
	    		}
	    	}
	    /**
	     * Method to Turn wifi OFF  without Progress dialog
		 */
	  public boolean setWifiDisabled(){
			   	   if(istWifiEnabled()){
			   		mWifiManager.setWifiEnabled(false);
			   	    	return true;
			       }else{
			    	   return false;
			       }
			     } 
	    /**
	     * check if wifi Enabled  and Show Progress dialog  to turn OFF wifi 
	     */
	  public void wifiDisableDialog()
	    	{
	    		if(mWifiManager.isWifiEnabled())
	    		{
	   			final ProgressDialog progDialog = new ProgressDialog(mContext);
	   			progDialog.setMessage("Turning Wifi OFF");
	   			progDialog.setTitle("WiFi");
	    			AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
	    			builder.setMessage("Do you want to turn OFF Wifi ")
	    					.setCancelable(false)
	    					.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
	    						
	    						public void onClick(DialogInterface dialog, int which) {
	    							mWifiManager.setWifiEnabled(false);
	    							progDialog.show();
	    							new Thread() {
	    								public void run() {
	    									try{
	    										
	    										while (mWifiManager.isWifiEnabled()) {
	    											   
	    											sleep(100);} 
	    										progDialog.dismiss();
	    									} catch (Exception e) {} 
	    									
	    								}
	    							}.start();
	    						}
	    					})
	    					.setNegativeButton("No", new DialogInterface.OnClickListener() {
	    						public void onClick(DialogInterface dialog, int which) {
	    						}
	    					});
	    			AlertDialog dialog = builder.create();
	    			dialog.show();
	    		}
	    	}
	   /**
	  	 * Method to Toggle wifi ON/OFF
	  	 * 
	  	 * @return true if wifi ON and false if wifi OFF
	  	 */
 	  public boolean wifiToggle(){
		   
	   	   if(!istWifiEnabled()){
	   	             	mWifiManager.setWifiEnabled(true);
	   		            return true;
	     	        }else{
	     		        mWifiManager.setWifiEnabled(false);
	     		        return false;
	    	               }
	      }
	    /**
	  	 * Method to Run wifi Settings Activity Using Intent
	  	 */
	   public void runWifiSettings(){
		 mContext.startActivity(new Intent(WifiManager.ACTION_PICK_WIFI_NETWORK));
	       }
	   
	    /**
	     * Check if The Device Is Connected to Internet
	     * 
	     * @return true if device connect to Internet or return false if not
	     */
        public boolean  isConnectedToInternet(){
			ConnectivityManager connectivity = (ConnectivityManager)mContext
			        .getSystemService(Context.CONNECTIVITY_SERVICE);
			if (connectivity != null) {
			    NetworkInfo info = connectivity.getActiveNetworkInfo();
			    if (info != null) {
			        if (info.isConnected()) {
				            return pingCmd("www.google.com") ;
			       }
			    }
			}
			return false;
		}
     
        /**
     	 * Method to Ping  IP Address
     	 * 
     	 * @param addr IP address you want to ping it 
     	 * @return true if the IP address is reachable
     	 */
     	public boolean pingCmd(String addr){
     		try {
    			String ping = "ping  -c 1 -W 3 " + addr;
        		Runtime run = Runtime.getRuntime();
    			Process pro = run.exec(ping);
    			 try {
    				pro.waitFor();
    			} catch (InterruptedException e) {
    				e.printStackTrace();
    			}
    	     	int exit = pro.exitValue();
    	        if (exit == 0) { 
    	        	 return true;
    	         } else {
    	        	 //ip address is not reachable
    	           return false;
    	         }
    		}
    			catch (IOException e) {
    			}
    		return false;
    	}  	
         /**
         * set Mobile Data Enabled with SIM
         *
         * @param enabled true or false
         */
         public void setMobileDataEnabled(boolean enabled) {
            try {
                ConnectivityManager connectivityManager = (ConnectivityManager)mContext.getSystemService(Context.CONNECTIVITY_SERVICE);
                Method method = connectivityManager.getClass().getMethod("setMobileDataEnabled", boolean.class);
                method.invoke(connectivityManager, enabled);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
       
        /**
         * check if  Mobile Data With SIM Enabled
         */
        public boolean isMobileDataEnabled() {
            try {
                ConnectivityManager connectivityManager = (ConnectivityManager)mContext.getSystemService(Context.CONNECTIVITY_SERVICE);
                Method method = connectivityManager.getClass().getMethod("getMobileDataEnabled");
                return (Boolean)method.invoke(connectivityManager);
            } catch (Exception e) {
                e.printStackTrace();
            }

            return false;
        }
        /**
	  	 * Method to Get SSID
	  	 * 
	  	 * @return String contain SSID
	  	 */
        public String getSSID(){
		 if(mWifiManager!=null){
			 return mWifiInfo.getSSID();
			 
		 }
		return null;
	 }
        /**
	  	 * Method to Get BSSID
	  	 * 
	  	 * @return String contain BSSID
	  	 */
        public String getBSSID(){
   		 if(mWifiManager!=null){
   			 return mWifiInfo.getBSSID();
   		 }
   		return null;
   	}
        /**
	  	 * Method to Get Rssi
	  	 * 
	  	 * @return int contain RSSI
	  	 */
        public int getRSSI(){
		 if(mWifiManager!=null){
			 return mWifiInfo.getRssi();
		 }
		return 0;
	 } 
        /**
	  	 * Method to Get Signal Strength
	  	 * 
	  	 * @return String contain Signal Strength
	  	 */
        public String getLinkSpeedUnits(){
		 if(mWifiManager!=null){
			 return WifiInfo.LINK_SPEED_UNITS;
		 }
		return null;
	 } 
        /**
	  	 * Method to Get Link Speed
	  	 * 
	  	 * @return int contain Link Speed
	  	 */
        public int getLinkSpeed(){
		 if(mWifiManager!=null){
			 return mWifiInfo.getLinkSpeed();
		 }
		return 0;
	 } 
        /**
	  	 * Method to Get Signal Strength
	  	 * 
	  	 * @return int contain Signal Strength
	  	 */
        public int getSignalStrength(){
		 if(mWifiManager!=null){
			 return WifiManager.calculateSignalLevel(mWifiInfo.getRssi(), 100);
		 }
		return 0;
	 } 
       /**
        * Method to Get type of connection used by mobile now
        * 
        * @return String contain TYPE_WIFI,TYPE_MOBILE,TYPE_NOT_CONNECTED
        */
        public  String getConnectivityStatus() {
    		ConnectivityManager cm = (ConnectivityManager) mContext
    				.getSystemService(Context.CONNECTIVITY_SERVICE);

    		NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
    		if (null != activeNetwork) {
    			if(activeNetwork.getType() == ConnectivityManager.TYPE_WIFI)
    				return "TYPE_WIFI";
    			
    			if(activeNetwork.getType() == ConnectivityManager.TYPE_MOBILE)
    				return "TYPE_MOBILE";
    		} 
    		return "TYPE_NOT_CONNECTED";
    	}
        /**
         * Method to Get type of connection used by mobile now
         * 
         * 
         */
         public  boolean isDataByWifi() {
     		ConnectivityManager cm = (ConnectivityManager) mContext
     				.getSystemService(Context.CONNECTIVITY_SERVICE);

     		NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
     		if (null != activeNetwork) {
     			if(activeNetwork.getType() == ConnectivityManager.TYPE_WIFI)
     				return true;
      		} 
     		return false;
     	}
    	/**
    	 * Method to Get wifi Connection Status  
    	 * 
    	 * @return String contain DISABLING,DISABLED,ENABLING,ENABLED,UNKNOWN
    	 */
    	public  String getWifiStatus() {
    		String status = null;
    		switch(mWifiManager.getWifiState()) {
    	         case WifiManager.WIFI_STATE_DISABLING:
             		      status ="DISABLING";
	         		      break;
		         case WifiManager.WIFI_STATE_DISABLED:
			              status ="DISABLED";
			              break;
		         case WifiManager.WIFI_STATE_ENABLING:
			              status ="ENABLING";
			              break;
		         case WifiManager.WIFI_STATE_ENABLED:
			              status ="ENABLED";
			              break;
		         case WifiManager.WIFI_STATE_UNKNOWN:
			              status ="UNKNOWN";
			              break;
    	      	}
     		return status;
    	}   
       
/**
 * Method to check if wifi on or off or device support wifi or support wifi direct or device conect to ap or internet 
 * and turn on or off wifi
 *  @param stat String : ISON,ON,OFF, TOGGLE,SUPPORT_WIFI, 
 *	    SUPPORT_WIFI_DIRECT,CONECT_AP,CONECT_INTERNET; 
 * @return TRUE OR FALSE
 */
   public boolean checkWifi(int state){
	   switch (state){
	   case SUPPORT_WIFI:
		   return isSupportWifi();
	   case SUPPORT_WIFI_DIRECT:
		   return isSupportWifiDirect();
	   case IS_WIFI_ON:
		   return istWifiEnabled();
	   case WIFI_ON:
		   return setWifiEnabled();
	   case WIFI_OFF:
		   return setWifiDisabled();
	   case WIFI_TOGGLE:
		   return wifiToggle();
	   case CONECT_HOTSPOT:
		   return isConnectedToAP();
	   case CONECT_INTERNET:
		   return isConnectedToInternet();
	   case DATA_BY_WIFI:
		   return isDataByWifi();	   
	   default:
		   return false;
	   
	   }
	   
	}   
}
