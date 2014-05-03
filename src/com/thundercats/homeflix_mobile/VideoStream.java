package com.thundercats.homeflix_mobile;

import android.app.ActionBar.LayoutParams;
import android.app.Activity;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.net.Uri;
import android.os.Bundle;
import android.widget.RelativeLayout;
import android.widget.VideoView;
import android.widget.MediaController;

public class VideoStream extends Activity{
	public static VideoView videoView;
	Homeflix app;
	
	@Override
	protected void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_video);
		
		app = (Homeflix)getApplication();
		
		String url = getIntent().getStringExtra("com.thundercats.homeflix_mobile.streamurl");
		System.out.println("New activity got: " + url);
		
		videoView = (VideoView) this.findViewById(R.id.videoView);
		videoView.setVideoURI(Uri.parse(url));
		videoView.setMediaController(new MediaController(this));
		videoView.requestFocus();
		videoView.start();
	}
	
	@Override
	public void onConfigurationChanged(Configuration newConfig){
		super.onConfigurationChanged(newConfig);
		//videoView.setLayoutParams(new FrameLayout.LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.FILL_PARENT));
	}
	
	@Override
	public void onStop(){
		super.onStop();
		app.sendRequest("stop", null);
	}
}
/*
super.onCreate(savedInstanceState);
setContentView(R.layout.activity_splash);

new Handler().postDelayed(new Runnable() {

    @Override
    public void run() {
    	//Once logo is displayed for predefined time, launch MainActivity
        Intent i = new Intent(SplashScreen.this, MainActivity.class);
        startActivity(i);
        //And close splash screen activity
        finish();
    }
}, SPLASH_TIME_OUT);
}
*/