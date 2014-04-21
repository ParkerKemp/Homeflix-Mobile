/*Homeflix-Mobile: SocketHandle
 * 
 * Homeflix project for WKU CS496
 * Richie Davidson, Parker Kemp, Colin Page
 * Spring Semester 2014
 * 
 * This is simply a wrapper class for Socket. ClientConnect has to continuously re-assign the Socket
 * reference as it attempts to reconnect. SocketHandle allows it to do this without overwriting the
 * reference passed from the main thread.
 */

package com.thundercats.homeflix_mobile;

import java.io.BufferedReader;
import java.io.PrintWriter;
import java.net.Socket;

public class SocketHandle {	
	public Socket sock;
	public String ip;
	public BufferedReader bufferIn;
	public PrintWriter bufferOut;
}
