package com.thundercats.homeflix_mobile;

import java.io.IOException;
import java.net.*;

public class ClientConnect implements Runnable {
	
	private final int Port = 6000;	//Should match the port Homeflix Base is listening on
	
	private final String Host = "192.168.1.102";	//Set this to your computer's local IP
													//(temporary for debugging purposes)
	
	SocketHandle sockHandle;
	
	public ClientConnect(SocketHandle s){
		sockHandle = s;
	}
	
	@Override
	public void run(){
		while(!establishConnection())
			;	//Block until establishConnection() returns true
	}
	
	public boolean establishConnection(){
		//Attempt to establish a connection to the server, return true on success
		try{
			sockHandle.sock = new Socket(InetAddress.getByName(Host),Port);
		}
		catch (UnknownHostException e1) {
			e1.printStackTrace();
		} catch (IOException e1) {
			e1.printStackTrace();
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		return sockHandle.sock.isConnected();
	}
}
