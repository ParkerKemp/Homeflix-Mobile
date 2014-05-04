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
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.VideoView;

public class HomeflixMobile extends Application{
	
	public MainActivity mainActivity = null;
	//public volatile ClientConnect connectHandle;
	public SocketHandle sockHandle = new SocketHandle();
	
	public static VideoView videoView;
	
	public static String[] fileNames;//names of playable files, sent from Base
	public static int fileCount;//number of fileNames expected from Base
	public static ArrayAdapter<String> adapter;
	public static int j; //loop counter
	
	public static ListView myVidList;//ListView container for user's available video files. Will be populated with
	//info from Base.
	
	@Override
	public void onCreate(){
		//Called when the application first starts
		
		super.onCreate();
		sockHandle.sock = new Socket();
	}
	
	public void sendRequest(String requestType, String data){
		//Send a request (possibly with data) to Base

		//If there is no connection, then return
		if(sockHandle.sock == null)
			return;
		if(!sockHandle.sock.isConnected())
			return;
		
		//Send the request as a single string
		sockHandle.bufferOut.println(requestType + " " + data);
	}
	
	public void openStream(String filename){
		//Start a new VideoStream activity and connect to the given stream via RTSP
		
		//Spaces are annoying; use underscores instead (the same is done on Base side)
		filename = filename.replace(' ', '_');
    	String mediaURL = "rtsp://" + sockHandle.ip + ":2464/" + filename;
		
    	//Initialize the intent, attach the URL string to it
		Intent intent = new Intent(mainActivity, VideoStream.class);
		intent.putExtra("com.thundercats.homeflix_mobile.streamurl", mediaURL);
		intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		startActivity(intent);
	}
	
	public void connectToIP(String ip){
		//Start a new thread trying to connect to the given IP address
		
		sockHandle.ip = ip;
		new Thread(new ClientConnect(this, sockHandle)).start();
	}
	
	public void receiveData(String s){
		//Receive the next piece of a list of filenames
		
		//the first value should be an integer (only first value)
		if (isInteger(s)){
			//This is the number of file names to be received
			fileCount = Integer.parseInt(s);
			fileNames = new String[fileCount];
			//fileTimes = new String[fileCount];
			j = 0;
		}
		else{
			fileNames[j] = s;
			//then iterate
			j++;
		}
		
		//Once file names are all received
		if (j == fileCount){
			//Convert String[] to suitable format to feed to ArrayAdapter
		    ArrayList<String> list = new ArrayList<String>();
		    for (int i = 0; i < fileNames.length; ++i) {
		      list.add(fileNames[i]);
		    }
		    
		    //then set adapter
		    adapter = new ArrayAdapter<String> (mainActivity, R.layout.list_layout, R.id.listTextView, list);
		    
		    //and pass to UI
		    myVidList.setAdapter(adapter);
		}
	}
	
	public static boolean isInteger(String s) {
		//Determine if the string can be interpreted as an int
		
	    try { 
	        Integer.parseInt(s); 
	    } catch(NumberFormatException e) { 
	        return false; 
	    }
	    // only got here if we didn't return false
	    return true;
	}
}