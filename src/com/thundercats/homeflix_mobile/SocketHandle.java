package com.thundercats.homeflix_mobile;

import java.net.Socket;

public class SocketHandle {
	//This is simply a wrapper class for Socket. ClientConnect has to continuously re-assign the Socket
	//reference as it attempts to reconnect. SocketHandle allows it to do this without overwriting the
	//reference passed from the main thread.
	
	public Socket sock;
}
