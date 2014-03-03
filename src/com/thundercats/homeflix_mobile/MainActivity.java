package com.thundercats.homeflix_mobile;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.widget.TextView;

import java.net.*;
import java.io.*;

public class MainActivity extends Activity {
	Socket sock;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		//Called at the very beginning
		
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		sock = new Socket();
		
		new Thread(new ClientConnect(sock)).start();

		new Thread(new WatchHamster(this, sock)).start();
	}
	
	@Override
	protected void onStart(){
		//Called after onCreate, and also after onRestart if the user is returning to it after onStop
		super.onStart();
	}
	
	@Override
	protected void onResume(){
		//Called after onStart, and also after onPause when the user returns to it
		super.onResume();
	}
	
	public void output(String s){
		TextView text = (TextView) findViewById(R.id.textView2);
		text.setText(s);
	}
	
	
	
	@Override
	protected void onPause(){
		//Called when another activity comes into the foreground, but this one is still partially visible
		super.onPause();
	}
	
	@Override
	protected void onStop(){
		//Called when the activity is no longer visible to the user
		super.onStop();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	protected void onDestroy(){
		//Exit app
		try {
			sock.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		super.onDestroy();
	}
}
