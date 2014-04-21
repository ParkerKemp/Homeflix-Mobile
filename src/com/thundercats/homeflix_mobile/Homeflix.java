package com.thundercats.homeflix_mobile;

import java.net.Socket;

import android.app.Application;
import android.content.Intent;
import android.net.Uri;

public class Homeflix extends Application{
	
	public MainActivity mainActivity = null;
	//public volatile ClientConnect connectHandle;
	public SocketHandle sockHandle = new SocketHandle();
	
	public String host;// = "192.168.1.102";	//Set this to your computer's local IP
													//(temporary for debugging purposes)
	@Override
	public void onCreate(){
		super.onCreate();
		sockHandle.sock = new Socket();
		//sockHandle.ip = Host;
		
		//Start thread that connects to server
		//new Thread(connectHandle = new ClientConnect(this, sockHandle)).start();
		
		//Start thread that handles general loop-ey stuff.
		//If Homeflix were a game, this is the game loop
		new Thread(new WatchHamster(this, sockHandle)).start();
	}
	
	public void sendData(String s){
		//Write code to send a string across the socket
		//try{
		if(sockHandle.sock == null)
			return;
		if(!sockHandle.sock.isConnected())
			return;
		sockHandle.bufferOut.println(sockHandle.ip + " " + s);
		parseRequest(s);
		//} catch(IOException e){
		
		//}
	}
	
	public boolean parseRequest(String line){
    	//Return true if a command was processed, or false if it's just arbitrary text data
    	
    	String[] tokens = line.split(" ");
    	String command = tokens[0];
    	
    	if(command.equalsIgnoreCase("play") && tokens.length > 1){
    		String filename = tokens[1];
        	String mediaURL = "rtsp://" + sockHandle.ip + ":2464/" + filename;
			System.out.println(mediaURL);
            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(mediaURL));
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
    		
    		return true;
    	}
    	
    	return false;
    }
	
	public void connectToIP(String ip){
		sockHandle.ip = ip;
		new Thread(new ClientConnect(this, sockHandle)).start();
	}
}