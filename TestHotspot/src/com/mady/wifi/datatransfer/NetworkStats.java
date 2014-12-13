/* 
 * Copyright (C) 2013-2014 www.Andbrain.com 
 * Faster and more easily to create android apps
 * 
 * */
package com.mady.wifi.datatransfer;

import java.util.HashMap;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.net.TrafficStats;

public class NetworkStats {
	/**
	 * Get Volume of Received Data Used by  Wifi 
	 * 
	 * @return int 
	 */
  public int getWifiDataUsageRx(){
	
	int mobileRx = (int) TrafficStats.getMobileRxBytes();
	int wifiRx = (int) (TrafficStats.getTotalRxBytes() - mobileRx);
	
	return wifiRx;
}
  /**
	 * Get Volume of transferred Data  by  Wifi 
	 * 
	 * @return int 
	 */
public int getWifiDataUsageTx(){
	
	int mobileTx = (int) TrafficStats.getMobileTxBytes();
	int wifiTx = (int) (TrafficStats.getTotalTxBytes() - mobileTx);
	
	return wifiTx;
}

@SuppressLint("UseSparseArrays")
public HashMap<Integer, DataUsage > getDataUsageApps(Context c){
	HashMap<Integer,DataUsage > apps=
			new HashMap<Integer,DataUsage >();
			HashMap<Integer, String> appNames=
					new HashMap<Integer, String>();
	
			for (ApplicationInfo app :
						c.getPackageManager().getInstalledApplications(0)) {
				appNames.put(app.uid, app.packageName);
				apps.put(app.uid, new DataUsage(app.uid, appNames.get(app.uid)));
			}
			
			return apps;
		
}
}
