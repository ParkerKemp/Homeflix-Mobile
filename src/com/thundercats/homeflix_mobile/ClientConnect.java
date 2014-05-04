/*Homeflix-Mobile: ClientConnect
 * 
 * Homeflix project for WKU CS496
 * Richie Davidson, Parker Kemp, Colin Page
 * Spring Semester 2014
 * 
 * Establish connection between client (this mobile device) and server (User's home machine
 * running Homeflix-Base).
 */

package com.thundercats.homeflix_mobile;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.*;

public class ClientConnect implements Runnable {
	
	private final int Port = 2463;	//Should match the port Homeflix Base is listening on
	
	HomeflixMobile app;
	SocketHandle sockHandle;
	long markTime, currentTime, timeout = 5000;
	
	public ClientConnect(HomeflixMobile app, SocketHandle s){
		sockHandle = s;
		this.app = app;
	}
	
	@Override
	public void run(){
		markTime = System.currentTimeMillis();
		
		//Repeatedly try to connect, stopping upon success or when 5 seconds have elapsed
		while(!establishConnection() && currentTime - markTime < 5000)
			currentTime = System.currentTimeMillis();
		
		//Request Base for a list of files
		app.sendRequest("RequestFileList", null);
	}
	
	public boolean establishConnection(){
		//Attempt to establish a connection to the server, return true on success
		
		try{
			sockHandle.sock = new Socket(InetAddress.getByName(sockHandle.ip),Port);
			
			//If connection was successful, create new buffers for reading and writing to the socket
			if(sockHandle.sock.isConnected()){
				sockHandle.bufferIn = new BufferedReader(new InputStreamReader(sockHandle.sock.getInputStream()));
				sockHandle.bufferOut = new PrintWriter(sockHandle.sock.getOutputStream(), true);
				
				//Put WatchHamster in his wheel
				new Thread(new WatchHamster(app, sockHandle)).start();
			}
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
