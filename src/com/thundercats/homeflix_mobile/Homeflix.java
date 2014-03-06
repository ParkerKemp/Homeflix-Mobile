package com.thundercats.homeflix_mobile;

import java.net.Socket;

import android.app.Application;

public class Homeflix extends Application{
	
	public MainActivity mainActivity = null;
	SocketHandle sockHandle = new SocketHandle();
	@Override
	public void onCreate(){
		super.onCreate();
		sockHandle.sock = new Socket();
		
		
		//Start thread that connects to server
		new Thread(new ClientConnect(sockHandle)).start();
		
		//Start thread that handles general loop-ey stuff.
		//If Homeflix were a game, this is the game loop
		new Thread(new WatchHamster(this, sockHandle)).start();
	}
	
	public void sendData(String s){
		//Write code to send a string across the socket
	}
}