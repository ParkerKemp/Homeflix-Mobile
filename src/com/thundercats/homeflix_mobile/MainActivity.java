/*Homeflix-Mobile: MainActivity
 * 
 * Homeflix project for WKU CS496
 * Richie Davidson, Parker Kemp, Colin Page
 * Spring Semester 2014
 * 
 * Main Activity class for Homeflix-Mobile
 */

package com.thundercats.homeflix_mobile;

//import java.util.HashMap;
//import java.util.List;

import android.os.Bundle;
import android.app.ActionBar;
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
	Homeflix app;
	TextView text;
	TextView text2;
	VideoView videoView;
	
	//String[] fileNames;//names of playable files, sent from Base
	//int fileCount;//number of fileNames expected from Base
	//ArrayAdapter<String> adapter;
	//int j; //loop counter
	
	EditText ipInput;//Entry field for user to manually enter IP. Currently not the goal of the
	//final design and will be altered/removed as project progresses
	
	EditText messageInput;//This EditText was used with the server echo test and has been deprecated
	//Will be safely removed from code once all relevant testing is discontinued
	
	Button streamButton;//This button previously activated the sample stream offered by a public site
	//Will be safely removed from code once all relevant testing is discontinued
	
	Button refreshButton;//User can press this to refresh their list of files
	
	//ListView myVidList;//ListView container for user's available video files. Will be populated with
	//info from Base.
	
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		
		super.onCreate(savedInstanceState);

		setContentView(R.layout.activity_main);
		app = (Homeflix)getApplication();
		app.mainActivity = this;
		
		
		
		//Connect to vidList in activity_main.xml
		Homeflix.myVidList = (ListView) findViewById(R.id.vidInfo);
				
		//Homeflix.refreshRotate();
		app.sendRequest("RequestFileList", null);//jerry-rigged fix for file list consistency

		SharedPreferences sharedPref = getSharedPreferences("prefs", MODE_PRIVATE);
		//SharedPreferences.Editor editor = sharedPref.edit();
		String previousInput = sharedPref.getString("ipAddress", "");
		
	    //Create interface effect: When file is tapped, play is initiated or resumed
	    Homeflix.myVidList.setOnItemClickListener(new ListView.OnItemClickListener() {
	        @Override
	        public void onItemClick(AdapterView<?> a, View v, int position, long rowID) {
	        	//String filename = "Test";//debug: all selections play the same testfile
	        	String filename = Homeflix.fileNames[position];//Identify the file name selected
	        	app.sendRequest("info", filename);
	        	
	        	//old code after this
	        	//app.sendRequest("play", filename);
	        	
	        	//app.openStream(filename);
	        	//String mediaURL = "rtsp://" + app.sockHandle.ip + ":2464/" + filename;//Send command to Base
				//System.out.println(mediaURL);//debug code, confirm correct formatting
               // Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(mediaURL));//invoke native media player on new rtsp URL
               // startActivity(intent);
	        }
	    });
	    
		//text = (TextView) findViewById(R.id.textView2);
		//text2 = (TextView) findViewById(R.id.textView4);
		
		//streamButton = (Button)findViewById(R.id.button1);
	    refreshButton = (Button)findViewById(R.id.refreshButton);
		
		//Current method of connection has user input IP of Base
		ipInput = (EditText) findViewById(R.id.editText1);
		ipInput.setText(previousInput);
		
		TextView.OnEditorActionListener ipListener = new TextView.OnEditorActionListener(){
			@Override
			public boolean onEditorAction(TextView exampleView, int actionId, KeyEvent event) {
				   if (actionId == EditorInfo.IME_ACTION_SEND){// && event.getAction() == KeyEvent.ACTION_DOWN) { 
					   InputMethodManager mgr = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
					   mgr.hideSoftInputFromWindow(ipInput.getWindowToken(), 0);
					   
					   app.connectToIP(exampleView.getText().toString());
					   
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
				app.sendRequest("RequestFileList", null);
	        }
		});
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
	
	//Receive the list of files from Base, send them to the ArrayAdapter
	/*
	public void receiveData(String s){
		//the first value should be an integer (only first value)
		if (isInteger(s)){
			//This is the number of file names to be received
			fileCount = Integer.parseInt(s);
			fileNames = new String[fileCount];
			j = 0;
		}
		else
		{
			//store each file name in the string
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
		    adapter = new ArrayAdapter<String> (this, android.R.layout.simple_list_item_1, android.R.id.text1, list);
		    
		    //and pass to UI
		    myVidList.setAdapter(adapter);
		}
	}
	*/
	
	/*
	public static boolean isInteger(String s) {
	    try { 
	        Integer.parseInt(s); 
	    } catch(NumberFormatException e) { 
	        return false; 
	    }
	    // only got here if we didn't return false
	    return true;
	}
	*/
	
	public void parseResponse(String s){
		String[] tokens = s.split(" ");
		if (tokens[0].equals("FILE")){
			app.receiveData(s.substring(5));
			return;
		}
		if (tokens[0].equals("READY")){
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
