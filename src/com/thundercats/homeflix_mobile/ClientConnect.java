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
	
	Homeflix app;
	SocketHandle sockHandle;
	long markTime, currentTime, timeout = 5000;
	
	public ClientConnect(Homeflix app, SocketHandle s){
		sockHandle = s;
		this.app = app;
	}
	
	@Override
	public void run(){
		markTime = System.currentTimeMillis();
		while(!establishConnection() && currentTime - markTime < 5000)
			currentTime = System.currentTimeMillis();	//Block until establishConnection() returns true
		System.out.println("ClientConnect thread done.");
		//app.sendData("Major Tom to Ground Control");//confirmation message from Mobile to Base to be echoed
		app.sendRequest("RequestFileList", null);//once connected to Base, ask for user's file list
	}
	
	public boolean establishConnection(){
		//Attempt to establish a connection to the server, return true on success
		try{
			sockHandle.sock = new Socket(InetAddress.getByName(sockHandle.ip),Port);
			if(sockHandle.sock.isConnected()){
				sockHandle.bufferIn = new BufferedReader(new InputStreamReader(sockHandle.sock.getInputStream()));
				sockHandle.bufferOut = new PrintWriter(sockHandle.sock.getOutputStream(), true);
				new Thread(new ServerListener(app, sockHandle)).start();
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
