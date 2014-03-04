package com.thundercats.homeflix_mobile;

public class WatchHamster implements Runnable {
	
	private SocketHandle sockHandle;
	private MainActivity mainActivity;
	
	public WatchHamster(MainActivity mainActivity, SocketHandle s){
		sockHandle = s;
		this.mainActivity = mainActivity;
	}
	
	@Override
	public void run(){
		while(true){
			checkConnection();
		}
	}
	
	public void checkConnection(){
		if(sockHandle.sock.isConnected())
			output("Connected to server! Downloading virus...");
		else
			output("Connecting...");
	}
	
	public void output(final String s){
		
		//All changes to the UI must be run on the thread that created it (the main thread, I assume)
		mainActivity.runOnUiThread(new Runnable() {
		     @Override
		     public void run() {
		    	 mainActivity.output(s);
		    }
		});
	}
}
