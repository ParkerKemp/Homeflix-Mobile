package com.thundercats.homeflix_mobile;

import java.net.Socket;

import android.app.Application;

public class Homeflix extends Application{
	
	public MainActivity mainActivity = null;
	public volatile ClientConnect connectHandle;
	SocketHandle sockHandle = new SocketHandle();
	
	private final String Host = "192.168.1.102";	//Set this to your computer's local IP
													//(temporary for debugging purposes)
	
	@Override
	public void onCreate(){
		super.onCreate();
		sockHandle.sock = new Socket();
		sockHandle.ip = Host;
		
		//Start thread that connects to server
		new Thread(connectHandle = new ClientConnect(this, sockHandle)).start();
		
		//Start thread that handles general loop-ey stuff.
		//If Homeflix were a game, this is the game loop
		new Thread(new WatchHamster(this, sockHandle)).start();
	}
	
	public void sendData(String s){
		//Write code to send a string across the socket
		//try{
		sockHandle.bufferOut.println(s);
		//} catch(IOException e){
		
		//}
	}
	
	public void connectToIP(String ip){
		sockHandle.ip = ip;
		new Thread(connectHandle = new ClientConnect(this, sockHandle)).start();
	}
}