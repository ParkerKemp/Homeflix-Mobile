package com.thundercats.homeflix_mobile;

import java.util.ArrayList;
//import java.util.HashMap;
//import java.util.List;


import android.net.Uri;
import android.os.Bundle;
import android.app.Activity;
//import android.content.Context;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.view.KeyEvent;
import android.view.inputmethod.EditorInfo;
import android.view.View.OnClickListener;

public class MainActivity extends Activity {
	SocketHandle sockHandle = new SocketHandle();
	Homeflix app;
	TextView text, text2;
	EditText ipInput, messageInput;
	Button streamButton;
	ListView myVidList;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		app = (Homeflix)getApplication();
		app.mainActivity = this;
		
		//Splash screen partial implementation, incomplete
		/*
		setContentView(R.layout.splashscreen);
        new Handler().postDelayed(new Runnable(){
            @Override
            public void run() {
                Intent mainIntent = new Intent(MainActivity.this,Menu.class);
                MainActivity.this.startActivity(mainIntent);
                MainActivity.this.finish();
            }
        }, 10);
       */
		
		//Connect to vidList in activity_main.xml
		myVidList = (ListView) findViewById(R.id.vidList);
		
		//dummy code to demonstrate scrollable
	    String[] values = new String[] { "My video.mp4", "Your video.mp4", "Bob's video.avi", 
	    		"NSFW vid.flv", "How to do thing.avi", "How not to do thing.mp4",
	    		"glow in the dark dog.mov", "Gameplay.flv", "test.avi",
	    		"My Little Pony Ep1.avi", "My Little Pony Ep2.avi", "My Little Pony Ep6.avi",
	    		"My Little Pony Ep10.avi", "Derpy Hooves.avi", "Parker eats a Taco.mp4",
	    		"Colin Builds a birdhouse.avi", "Richie sleeps for 14 hrs.gif"};

	    ArrayList<String> list = new ArrayList<String>();
	    for (int i = 0; i < values.length; ++i) {
	      list.add(values[i]);
	    }
	    
	    ArrayAdapter<String> adapter = new ArrayAdapter<String> (this, android.R.layout.simple_list_item_1, android.R.id.text1, list);
	    
	    //Connect software list with user interface
	    myVidList.setAdapter(adapter);
		
	    myVidList.setOnItemClickListener(new ListView.OnItemClickListener() {
	        @Override
	        public void onItemClick(AdapterView<?> a, View v, int i, long l) {
	        	String filename = "test";
	        	app.sendData("play " + filename);
	        	String mediaURL = "rtsp://" + app.sockHandle.ip + ":2464/" + filename;
				System.out.println(mediaURL);
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(mediaURL));
                startActivity(intent);
	        }
	    });
	    
		//text = (TextView) findViewById(R.id.textView2);
		//text2 = (TextView) findViewById(R.id.textView4);
		
		//streamButton = (Button)findViewById(R.id.button1);
		
		ipInput = (EditText) findViewById(R.id.editText1);
		TextView.OnEditorActionListener ipListener = new TextView.OnEditorActionListener(){
			@Override
			public boolean onEditorAction(TextView exampleView, int actionId, KeyEvent event) {
				   if (actionId == EditorInfo.IME_ACTION_SEND){// && event.getAction() == KeyEvent.ACTION_DOWN) { 
					   app.connectToIP(exampleView.getText().toString());
					   //System.out.println(exampleView.getText());
				   }
				   return true;
				}
		};
		ipInput.setOnEditorActionListener(ipListener);
		
		/*messageInput = (EditText) findViewById(R.id.editText2);
		TextView.OnEditorActionListener messageListener = new TextView.OnEditorActionListener(){
			@Override
			public boolean onEditorAction(TextView exampleView, int actionId, KeyEvent event) {
				   if (actionId == EditorInfo.IME_ACTION_SEND){// && event.getAction() == KeyEvent.ACTION_DOWN) { 
					   String str = exampleView.getText().toString();
					   app.sendData(str);
					   exampleView.setText("");
					   
					   //System.out.println(str);
				   }
				   return true;
				}
		};
		messageInput.setOnEditorActionListener(messageListener);*/
		
		/*streamButton.setOnClickListener(new OnClickListener(){
			public void onClick(View v){
				//String mediaURL = "rtsp://184.72.239.149/vod/mp4:BigBuckBunny_115k.mov";
				String mediaURL = "rtsp://" + app.sockHandle.ip + ":2464/demo";
				System.out.println(mediaURL);
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(mediaURL));
                startActivity(intent);
            	
			}
		});*/
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
		text.setText(s);
	}
	
	public void output2(String s){
		text2.setText(s);
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
		
		super.onDestroy();
	}
	
	//test code for implementing listview
	/*
	private class StableArrayAdapter extends ArrayAdapter<String> {
		HashMap<String, Integer> mIdMap = new HashMap<String, Integer>();
		public StableArrayAdapter(Context context, int textViewResourceId, List<String> objects) {
			super(context, textViewResourceId, objects);
			for (int i = 0; i < objects.size(); ++i) {
				mIdMap.put(objects.get(i), i);
			}
	    }
	}
	*/
}
