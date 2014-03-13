package com.thundercats.homeflix_mobile;

import java.io.BufferedReader;
import java.io.PrintWriter;
import java.net.Socket;

public class SocketHandle {
	//This is simply a wrapper class for Socket. ClientConnect has to continuously re-assign the Socket
	//reference as it attempts to reconnect. SocketHandle allows it to do this without overwriting the
	//reference passed from the main thread.
	
	public Socket sock;
	public String ip;
	public BufferedReader bufferIn;
	public PrintWriter bufferOut;
}
