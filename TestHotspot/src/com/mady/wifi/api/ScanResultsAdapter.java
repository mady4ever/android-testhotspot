/* 
 * Copyright (C) 2013-2014 www.Andbrain.com 
 * Faster and more easily to create android apps
 * 
 * */
package com.mady.wifi.api;


import java.util.List;



import com.example.testhotspot.R;
import android.annotation.SuppressLint;
import android.content.Context;
import android.net.wifi.ScanResult;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class ScanResultsAdapter extends BaseAdapter {

	private final Context mContext;
	private final List<ScanResult> mResults;
	public ScanResultsAdapter(Context context, List<ScanResult> results) {
		this.mContext = context;
		this.mResults = results;
	}

	@Override
	public int getCount() {
		return mResults.size();
	}
 
	@Override
	public Object getItem(int position) {
		return mResults.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@SuppressLint("InflateParams")
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ScanResult result = mResults.get(position);

		if(convertView==null) {
			LayoutInflater inflater = (LayoutInflater)mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			convertView = inflater.inflate(R.layout.net_list_row, null);
		}

		// Get textview fields
		TextView txtSSID = (TextView)convertView.findViewById(R.id.txtSSID);
		TextView txtBSSID = (TextView)convertView.findViewById(R.id.txtBSSID);
		TextView txtCapabilities = (TextView)convertView.findViewById(R.id.txtCapabilities);
		TextView txtFrecuency = (TextView)convertView.findViewById(R.id.txtFrecuency);
		TextView txtLevel = (TextView)convertView.findViewById(R.id.txtLevel);

		// Set fields values
		txtSSID.setText("SSID::"+result.SSID);
		txtBSSID.setText("BSSID::"+result.BSSID);
		txtCapabilities.setText("capabilities::"+result.capabilities);
		txtFrecuency.setText("frecuency::"+result.frequency);
		txtLevel.setText("signal_level::"+result.level);

		return convertView;
	}

}
