package com.example.testhotspot;


import java.util.List;

import com.mady.wifi.api.*;


import android.support.v7.app.ActionBarActivity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends ActionBarActivity {

	private Button invite;
	private Button join;
	wifiHotSpots hotutil;
	WifiStatus wifiStatus;
	BroadcastReceiver receiver;
	private static int result_lavel=0;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		hotutil=new wifiHotSpots(getApplicationContext());
		wifiStatus = new WifiStatus(getApplicationContext());
		invite = (Button)findViewById(R.id.Invite);
		join   = (Button)findViewById(R.id.Join);
		invite.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				/*if(hotutil.setHotSpot("SSID",""))
				{
					hotutil.startHotSpot(true);
				}
				*/
				inviteFriend(hotutil);
			}
		});
		join.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				joinFriend(wifiStatus, hotutil);				
			}
		});
	}
	public void inviteFriend(wifiHotSpots hotutil)
	{
		/*
		if(hotutil.setHotSpot("SSID",""))
		{
			hotutil.startHotSpot(true);
		}
		*/
		hotutil.setAndStartHotSpot(true, "MAHENDRA");
	}
	public void joinFriend(final WifiStatus wifiStatus, final wifiHotSpots hotutil)
	{
		if(wifiStatus.checkWifi(wifiStatus.IS_WIFI_ON))
		{
			hotutil.scanNetworks();
			List<ScanResult> results = hotutil.getHotspotsList();			      
	        for (ScanResult result : results) {
	            //Toast.makeText(getApplicationContext(), result.SSID + " " + result.level,
	            //        Toast.LENGTH_SHORT).show();
	            if(result.SSID.equalsIgnoreCase("SSID"))
	            {
	            	
	            	Toast.makeText(getApplicationContext(), result.SSID + " Found SSID" + result.level,
		                    Toast.LENGTH_SHORT).show();
	            	hotutil.connectToHotspot("SSID","");
	            	try
	            	{
	            		unregisterReceiver(receiver);
	            		break;
	            	}catch(Exception e)
	            	{
	            		//error as trying to do unregistering twice?
	            	}
	            	//hotutil.stopScan();
	            }
	         }
			
		}
		else
		{
			if(hotutil.isWifiApEnabled())
				hotutil.startHotSpot(false);
			//start wifi.
			wifiStatus.checkWifi(wifiStatus.WIFI_ON);
			
			 receiver = new BroadcastReceiver() {

				@Override
				public void onReceive(Context context, Intent intent) {
					// TODO Auto-generated method stub
					final String action = intent.getAction();						 
				    if(action.equals(WifiManager.SCAN_RESULTS_AVAILABLE_ACTION))
				    {
				    	List<ScanResult> results = hotutil.getHotspotsList();			      
				        for (ScanResult result : results) {
				            //Toast.makeText(getApplicationContext(), result.SSID + " " + result.level,
				            //        Toast.LENGTH_SHORT).show();
				            if(result.SSID.equalsIgnoreCase("SSID"))
				            {	
				            	Toast.makeText(getApplicationContext(),"Found SSID",Toast.LENGTH_SHORT).show();
				            	if(!hotutil.isConnectToHotSpotRunning)				            		
				            		hotutil.connectToHotspot("SSID","");				            	
				            	try{
				            		unregisterReceiver(receiver);
				            		break;
				            	}catch(Exception e)
				            	{
				            		//trying to unregister twice? need vary careful about this.
				            	}
				    
				            }
				            }
				    }
				}
				
			};
			IntentFilter mIntentFilter = new IntentFilter();			
			mIntentFilter.addAction(WifiManager.SCAN_RESULTS_AVAILABLE_ACTION);
			registerReceiver(receiver,  mIntentFilter);					
		}

	}
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
}
