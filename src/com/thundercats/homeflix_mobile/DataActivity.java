/*Homeflix-Mobile: DataActivity
 * 
 * Homeflix project for WKU CS496
 * Richie Davidson, Parker Kemp, Colin Page
 * Spring Semester 2014
 * 
 * Display metadata etc to user
 */

package com.thundercats.homeflix_mobile;

import java.util.ArrayList;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

public class DataActivity extends Activity{
	ListView vidInfo;
	String[] myVidInfo = new String[3];
	ArrayAdapter<String> adapter;
	Button playButton;
	String filename;
	Homeflix app;
	
	//@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_data);
		
		app = (Homeflix)getApplication();
		
		TextView myTextView = (TextView)findViewById(R.id.FileText);
		playButton = (Button)findViewById(R.id.playButton);
		vidInfo = (ListView) findViewById(R.id.infoList);
		
		String fileInfo = getIntent().getStringExtra("fileInfo");
		
		ArrayList<String> list = new ArrayList<String>();
		//Video information is split by semicolons
		String[] tokens = fileInfo.split(";");
	    
		filename = tokens[0];
		myTextView.setText("Info for " + filename);
		
	    myVidInfo[0] = "Play Time: " + tokens[1];
	    myVidInfo[1] = "Video Codec: " + tokens[2];
	    myVidInfo[2] = "Audio Codec: " + tokens[3];
	    
	    //construct array into list
	    for (int i = 0; i < myVidInfo.length; ++i) {
		      list.add(myVidInfo[i]);
		}
		
		//then set adapter
	    adapter = new ArrayAdapter<String> (this, R.layout.list_layout, R.id.listTextView, list);
	    
	    //and pass to UI
	    vidInfo.setAdapter(adapter);
	    
	    playButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				app.sendRequest("play", filename);
	        }
		});
	}
}