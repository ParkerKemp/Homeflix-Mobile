package com.thundercats.homeflix_mobile;

public class WatchHamster implements Runnable {
	
	private SocketHandle sockHandle;
	private BoolEvent boolSwitch = new BoolEvent();
	private MainActivity mainActivity;
	
	public WatchHamster(MainActivity mainActivity, SocketHandle s){
		sockHandle = s;
		this.mainActivity = mainActivity;
	}
	
	@Override
	public void run(){
		String text = "Not Connected...";
		mainActivity.output(text);
		
		while(true){
			boolSwitch.update(sockHandle.sock.isConnected());
			if(boolSwitch.switchOn())
				text = "Connected to server.";
			else if(boolSwitch.switchOff())
				text = "Not Connected...";
			else
				continue;
			output(text);
		}
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
