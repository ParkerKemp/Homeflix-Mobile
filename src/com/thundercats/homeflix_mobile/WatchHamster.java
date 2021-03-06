/*Homeflix-Mobile: WatchHamster
 * 
 * Homeflix project for WKU CS496
 * Richie Davidson, Parker Kemp, Colin Page
 * Spring Semester 2014
 * 
 * 
 */

package com.thundercats.homeflix_mobile;

import java.io.IOException;

public class WatchHamster implements Runnable {
	
	private SocketHandle sockHandle;
	private HomeflixMobile app;
	
	public WatchHamster(HomeflixMobile app, SocketHandle sockHandle){
		this.sockHandle = sockHandle;
		this.app = app;
	}
	
	@Override
	public void run(){
		
		String line;
		try{
			while((line = sockHandle.bufferIn.readLine()) != null && !line.equals(".")){
				System.out.println(line);
				//output(line);
				receiveData(line);
				//reply with the same message, adding some text
				// out.println("Server received: " + line);
				
			}
		}
		catch (IOException e){
			System.out.println("IOException on socket : " + e);
			e.printStackTrace();
		}
	}
	
	public void receiveData(final String s){
		if(app.mainActivity == null)
			return;
		app.mainActivity.runOnUiThread(new Runnable() {
			@Override
			public void run() {
				//send incoming data to MainActivity to be interpreted
				app.mainActivity.parseResponse(s);
			}
		});
	}
}
