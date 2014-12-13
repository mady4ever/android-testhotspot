/* 
 * Copyright (C) 2013-2014 www.Andbrain.com 
 * Faster and more easily to create android apps
 * 
 * */
package com.mady.wifi.api;

import java.util.List;

import android.content.Context;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiManager;
import android.widget.Toast;




public class ScanTimerSimple extends ScanTimer{
	List<ScanResult> mResults;
	WifiManager mWifiManager;
	public ScanTimerSimple(Context c) {
		super(c);
		mWifiManager=(WifiManager)  c.getSystemService(Context.WIFI_SERVICE);
		 
	}
	
	public ScanTimerSimple(long interval, long duration,Context c){
		super(interval, duration,c);
		mWifiManager=(WifiManager) c.getSystemService(Context.WIFI_SERVICE);
		
	}

	/**
	 *	This method is called periodically with the interval set as the delay between subsequent calls. 
	 */
	 
	@Override
	protected void onTick() {
	scanNetworks();
	if(mResults!=null){
		List<ScanResult> results2 = mResults;
	      
        for (ScanResult result : results2) {
            Toast.makeText(mContext, result.SSID + " "+ result.level,
                    Toast.LENGTH_SHORT).show();
            }
	}
		
	}

	@Override
	protected void onFinish() {
		
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
}
