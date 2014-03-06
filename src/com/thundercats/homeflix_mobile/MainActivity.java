package com.thundercats.homeflix_mobile;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.widget.EditText;
import android.widget.TextView;
import android.view.KeyEvent;
import android.view.inputmethod.EditorInfo;

public class MainActivity extends Activity {
	SocketHandle sockHandle = new SocketHandle();
	Homeflix app;
	TextView text;
	EditText editText;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		app = (Homeflix)getApplication();
		app.mainActivity = this;
		
		text = (TextView) findViewById(R.id.textView2);
		editText = (EditText) findViewById(R.id.editText1);
		TextView.OnEditorActionListener exampleListener = new TextView.OnEditorActionListener(){
			@Override
			public boolean onEditorAction(TextView exampleView, int actionId, KeyEvent event) {
				   if (actionId == EditorInfo.IME_NULL && event.getAction() == KeyEvent.ACTION_DOWN) { 
					   System.out.println(exampleView.getText());
				   }
				   return true;
				}
		};
		editText.setOnEditorActionListener(exampleListener);
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
