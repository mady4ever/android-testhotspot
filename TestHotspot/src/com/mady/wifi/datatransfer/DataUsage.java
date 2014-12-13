/* 
 * Copyright (C) 2013-2014 www.Andbrain.com 
 * Faster and more easily to create android apps
 * 
 * */

package com.mady.wifi.datatransfer;

import android.net.TrafficStats;

class DataUsage {
	long tx=0;
	long rx=0;
	String tag=null;
	
	DataUsage() {
		tx=TrafficStats.getTotalTxBytes();
		rx=TrafficStats.getTotalRxBytes();
	}
	
	DataUsage(int uid, String tag) {
		tx=TrafficStats.getUidTxBytes(uid);
		rx=TrafficStats.getUidRxBytes(uid);
		this.tag=tag;
	}
}