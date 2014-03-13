package com.thundercats.homeflix_mobile;

import java.io.IOException;

public class ServerListener implements Runnable {
	
	private SocketHandle sockHandle;
	private Homeflix app;
	
	public ServerListener(Homeflix app, SocketHandle sockHandle){
		this.sockHandle = sockHandle;
		this.app = app;
	}
	
	@Override
	public void run(){
		
		String line;
		try{
			while((line = sockHandle.bufferIn.readLine()) != null && !line.equals(".")){
				System.out.println(line);
				output(line);
				//reply with the same message, adding some text
				// out.println("Server received: " + line);
			}
		}
		catch (IOException e){
			System.out.println("IOException on socket : " + e);
			e.printStackTrace();
		}
	}
	
	public void output(final String s){
		if(app.mainActivity == null)
			return;
		//All changes to the UI must be run on the thread that created it (the main thread, I assume)
		app.mainActivity.runOnUiThread(new Runnable() {
		     @Override
		     public void run() {
		    	 app.mainActivity.output2(s);
		    }
		});
	}
}
