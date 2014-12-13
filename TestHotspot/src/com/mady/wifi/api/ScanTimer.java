/* 
 * Copyright (C) 2013-2014 www.Andbrain.com 
 * Faster and more easily to create android apps
 * 
 * */
package com.mady.wifi.api;

import java.util.Timer;
import java.util.TimerTask;
import android.app.Activity;
import android.content.Context;


/**
 * A abstract Scan timer utility class .
 */
public abstract class ScanTimer {
	public static final int DURATION_INFINITY = -1;
	private volatile boolean isRunning = false;
	private long interval;
	private long duration;
	Context mContext;
	Timer timer;
	MyTimerTask myTimerTask;

	
	public ScanTimer(Context c) {
		this(1000, -1,c);
		mContext=c;
	}

	
	public ScanTimer(long interval, long duration,Context c) {
		this.interval = interval;
		this.duration = duration;
		mContext=c;
	}

	/**
	 * Starts the timer. If the timer was already running, this call is ignored.
	 */
	
	public void start() {
		if (isRunning)
			return;

		isRunning = true;
		if(timer != null){
				timer.cancel();
			}
		   timer = new Timer();
		   myTimerTask = new MyTimerTask();
		  timer.schedule(myTimerTask, interval, duration);
	}

	/**
	 * Paused the timer. If the timer is not running, this call is ignored.
	 */
	public void pause() {
		if(!isRunning) return;
		timer.cancel();
		isRunning = false;
	}

	
	/**
	 * Resumes the timer if it was paused, else starts the timer.
	 */
	public void resume() {
		this.start();
	}

	
	/**
	 *	This method is called periodically with the interval set as the delay between subsequent calls. 
	 */
	protected abstract void onTick();
	
	
	/**
	 * This method is called once the timer has run for the specified duration. If the duration was set as infinity, then this method is never called.
	 */
	protected abstract void onFinish();

	/**
	 * Stops the timer. If the timer is not running, then this call does nothing.
	 */
	public void cancel() {
		pause();
		}

	
	/**
	 * @return true if the timer is currently running, and false otherwise.
	 */
	public boolean isRunning() {
		return isRunning;
	}
	
	 class MyTimerTask extends TimerTask {

			@Override
			public void run() {
				((Activity)mContext).runOnUiThread(new Runnable(){
				@Override
					public void run() {
					    
					onTick();
					}});
			
			}
			
		}
}
