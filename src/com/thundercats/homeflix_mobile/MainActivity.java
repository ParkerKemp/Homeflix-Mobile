package com.thundercats.homeflix_mobile;

import android.net.Uri;
import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
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
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		app = (Homeflix)getApplication();
		app.mainActivity = this;
		
		text = (TextView) findViewById(R.id.textView2);
		text2 = (TextView) findViewById(R.id.textView4);
		
		streamButton = (Button)findViewById(R.id.button1);
		
		ipInput = (EditText) findViewById(R.id.editText1);
		TextView.OnEditorActionListener ipListener = new TextView.OnEditorActionListener(){
			@Override
			public boolean onEditorAction(TextView exampleView, int actionId, KeyEvent event) {
				   if (actionId == EditorInfo.IME_NULL && event.getAction() == KeyEvent.ACTION_DOWN) { 
					   app.connectToIP(exampleView.getText().toString());
					   System.out.println(exampleView.getText());
				   }
				   return true;
				}
		};
		ipInput.setOnEditorActionListener(ipListener);
		
		messageInput = (EditText) findViewById(R.id.editText2);
		TextView.OnEditorActionListener messageListener = new TextView.OnEditorActionListener(){
			@Override
			public boolean onEditorAction(TextView exampleView, int actionId, KeyEvent event) {
				   if (actionId == EditorInfo.IME_NULL && event.getAction() == KeyEvent.ACTION_DOWN) { 
					   String str = exampleView.getText().toString();
					   app.sendData(str);
					   exampleView.setText("");
					   
					   System.out.println(str);
				   }
				   return true;
				}
		};
		messageInput.setOnEditorActionListener(messageListener);
		
		streamButton.setOnClickListener(new OnClickListener(){
			public void onClick(View v){
				String mediaURL = "rtsp://184.72.239.149/vod/mp4:BigBuckBunny_115k.mov";
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(mediaURL));
                startActivity(intent); 
            	
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
}
