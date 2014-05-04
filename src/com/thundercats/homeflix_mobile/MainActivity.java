/*Homeflix-Mobile: MainActivity
 * 
 * Homeflix project for WKU CS496
 * Richie Davidson, Parker Kemp, Colin Page
 * Spring Semester 2014
 * 
 * Main Activity class for Homeflix-Mobile
 */

package com.thundercats.homeflix_mobile;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.VideoView;
import android.view.KeyEvent;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;

public class MainActivity extends Activity {
	SocketHandle sockHandle = new SocketHandle();
	HomeflixMobile app;
	TextView text;
	TextView text2;
	VideoView videoView;
	
	EditText ipInput;//Entry field for user to manually enter IP. 

	Button refreshButton;//User can press this to refresh their list of files
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		
		super.onCreate(savedInstanceState);

		setContentView(R.layout.activity_main);
		app = (HomeflixMobile)getApplication();
		app.mainActivity = this;
		
		//Connect to vidList in activity_main.xml
		HomeflixMobile.myVidList = (ListView) findViewById(R.id.vidInfo);

				
		//Homeflix.refreshRotate();
		app.sendRequest("RequestFileList", null);//jerry-rigged fix for file list consistency

		SharedPreferences sharedPref = getSharedPreferences("prefs", MODE_PRIVATE);
		String previousInput = sharedPref.getString("ipAddress", "");
		
	    //Create interface effect: When file is tapped, play is initiated or resumed
	    HomeflixMobile.myVidList.setOnItemClickListener(new ListView.OnItemClickListener() {
	        @Override
	        public void onItemClick(AdapterView<?> a, View v, int position, long rowID) {
	        	String filename = HomeflixMobile.fileNames[position];//Identify the file name selected
	        	
	        	//Send a request for Base to start the stream
	        	app.sendRequest("info", filename);
	        }
	    });
	    
		refreshButton = (Button)findViewById(R.id.refreshButton);
		
		//Current method of connection has user input IP of Base
		ipInput = (EditText) findViewById(R.id.editText1);
		ipInput.setText(previousInput);
		
		//Define behavior of the EditText (IP address input)
		TextView.OnEditorActionListener ipListener = new TextView.OnEditorActionListener(){
			@Override
			public boolean onEditorAction(TextView exampleView, int actionId, KeyEvent event) {
				//User presses enter
				if (actionId == EditorInfo.IME_ACTION_SEND){ 
					   
					//Hide the keyboard
					InputMethodManager mgr = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
					mgr.hideSoftInputFromWindow(ipInput.getWindowToken(), 0);
					
					//Try to connect to the IP address
					app.connectToIP(exampleView.getText().toString());
					
					//Store IP address for later
					SharedPreferences sharedPref = getSharedPreferences("prefs", MODE_PRIVATE);
					SharedPreferences.Editor editor = sharedPref.edit();
					editor.putString("ipAddress", exampleView.getText().toString());
					editor.commit();
				}
				return true;
			}
			
		};
		
		ipInput.setOnEditorActionListener(ipListener);
		
		refreshButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				//Request an up-to-date file list
				app.sendRequest("RequestFileList", null);
	        }
		});
	}
	
	public void parseResponse(String s){
		//Parse a response received from the server
		
		String[] tokens = s.split(" ");
		if (tokens[0].equals("FILE")){
			//Messages preceded with FILE are related an incoming file list
			app.receiveData(s.substring(5));
			return;
		}
		if(tokens[0].equals("READY")){
			//A READY message indicates that a requested stream is ready on Base side
			String filename = s.substring(6);
			app.openStream(filename);
		}
		if (tokens[0].equals("INFO")){
			Intent dataIntent = new Intent(app.mainActivity, DataActivity.class);
        	dataIntent.putExtra("fileInfo", s.substring(5));
        	dataIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
    		startActivity(dataIntent);
		}
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
}
