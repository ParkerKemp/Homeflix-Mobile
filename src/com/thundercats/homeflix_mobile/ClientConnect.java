package com.thundercats.homeflix_mobile;

import java.io.IOException;
import java.net.*;

public class ClientConnect implements Runnable {
	
	private final int Port = 6000;	//Should match the port Homeflix Base is listening on
	
	private final String Host = "192.168.1.102";	//Set this to your computer's local IP
													//(temporary for debugging purposes)
	
	Socket sock;
	
	public ClientConnect(Socket s){
		sock = s;
	}
	
	@Override
	public void run(){
		try{
			SocketAddress serverAddress = new InetSocketAddress(Host, Port);
			
			sock.connect(serverAddress, 6000);
		}
		catch (UnknownHostException e1) {
			e1.printStackTrace();
		} catch (IOException e1) {
			e1.printStackTrace();
		} catch (Exception e1) {
			e1.printStackTrace();
		}
	}
}
