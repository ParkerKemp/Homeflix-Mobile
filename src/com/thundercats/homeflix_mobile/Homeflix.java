/*Homeflix-Mobile: Homeflix
 * 
 * Homeflix project for WKU CS496
 * Richie Davidson, Parker Kemp, Colin Page
 * Spring Semester 2014
 * 
 * 
 */

package com.thundercats.homeflix_mobile;

import java.net.Socket;
import java.util.ArrayList;

import android.app.Application;
import android.content.Intent;
import android.net.Uri;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

public class Homeflix extends Application{
	
	public static MainActivity mainActivity = null;
	//public volatile ClientConnect connectHandle;
	public SocketHandle sockHandle = new SocketHandle();
	
	
	public static String[] fileNames;//names of playable files, sent from Base
	public static String[] fileTimes;//play durations of files, from Base
	public static int fileCount;//number of fileNames expected from Base
	public static ArrayAdapter<String> adapter;
	public static int j; //loop counter
	
	public static ListView myVidList;//ListView container for user's available video files. Will be populated with
	//info from Base.
	
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
	
	public void sendRequest(String requestType, String data){
		//Write code to send a string across the socket
		//try{
		if(sockHandle.sock == null)
			return;
		if(!sockHandle.sock.isConnected())
			return;
		
		sockHandle.bufferOut.println(requestType + " " + data);
		//parseRequest(s);
		//} catch(IOException e){
		
		//}
	}
	
	public void openStream(String filename){
		//String filename = tokens[1];
		filename = filename.replace(' ', '_');
    	String mediaURL = "rtsp://" + sockHandle.ip + ":2464/" + filename;
		System.out.println(mediaURL);
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(mediaURL));
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
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
	
	public static void receiveData(String s){
		//the first value should be an integer (only first value)
		if (isInteger(s)){
			//This is the number of file names to be received
			fileCount = Integer.parseInt(s);
			fileNames = new String[fileCount];
			fileTimes = new String[fileCount];
			j = 0;
		}
		else
		{
			//use tokens to split file name and play duration
			String[] tokens = s.split(";");
			//store each file name in the string
			fileNames[j] = tokens[0];
			fileTimes[j] = tokens[1];
			//then iterate
			j++;
		}
		
		//Once file names are all received
		if (j == fileCount){
			//Convert String[] to suitable format to feed to ArrayAdapter
		    ArrayList<String> list = new ArrayList<String>();
		    for (int i = 0; i < fileNames.length; ++i) {
		      list.add(fileNames[i] + " - " + fileTimes[i]);
		    }
		    
		    //then set adapter
		    adapter = new ArrayAdapter<String> (mainActivity, android.R.layout.simple_list_item_1, android.R.id.text1, list);
		    
		    //and pass to UI
		    myVidList.setAdapter(adapter);
		}
	}
	
	/*
	public static void refreshRotate(){
		if (adapter != null){
		    myVidList.setAdapter(adapter);
			//System.out.println("Test2");
		}
		//System.out.println("Test1");
	}
	*/
	
	public static boolean isInteger(String s) {
	    try { 
	        Integer.parseInt(s); 
	    } catch(NumberFormatException e) { 
	        return false; 
	    }
	    // only got here if we didn't return false
	    return true;
	}
}