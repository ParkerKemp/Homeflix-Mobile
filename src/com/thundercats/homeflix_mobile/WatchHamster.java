package com.thundercats.homeflix_mobile;

import android.app.Application;

public class WatchHamster implements Runnable {
	
	private SocketHandle sockHandle;
	private Homeflix app;
	
	public WatchHamster(Homeflix app,SocketHandle s){
		sockHandle = s;
		this.app = app;
	}
	
	@Override
	public void run(){
		while(true){
			checkConnection();
			readFromSocket();
		}
	}
	
	public void checkConnection(){
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if(sockHandle.sock.isConnected())
			output("Connected to server! Downloading virus...");
		else
			output("Connecting...");
	}
	
	public void readFromSocket(){
		if(!sockHandle.sock.isConnected())
			return;
		
		
	}
	
	public void output(final String s){
		if(app.mainActivity == null)
			return;
		//All changes to the UI must be run on the thread that created it (the main thread, I assume)
		app.mainActivity.runOnUiThread(new Runnable() {
		     @Override
		     public void run() {
		    	 app.mainActivity.output(s);
		    }
		});
	}
}
