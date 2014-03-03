package com.thundercats.homeflix_mobile;

import java.net.*;

import android.app.Activity;
import android.widget.TextView;

public class WatchHamster implements Runnable {
	
	private Socket sock;
	private BoolEvent boolSwitch = new BoolEvent();
	private MainActivity mainActivity;
	
	public WatchHamster(MainActivity mainActivity, Socket s){
		sock = s;
		this.mainActivity = mainActivity;
	}
	
	@Override
	public void run(){
		String text = "Not Connected...";
		mainActivity.output(text);
		
		while(true){
			boolSwitch.update(sock.isConnected());
			if(boolSwitch.switchOn())
				text = "Connected to server.";
			else if(boolSwitch.switchOff())
				text = "Not Connected...";
			else
				continue;
			mainActivity.output(text);
		}
	}
}
