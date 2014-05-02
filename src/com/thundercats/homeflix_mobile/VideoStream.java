package com.thundercats.homeflix_mobile;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.widget.VideoView;
import android.widget.MediaController;

public class VideoStream extends Activity{
	VideoView videoView;
	
	@Override
	protected void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		SharedPreferences prefs = getPreferences(MODE_PRIVATE);
		setContentView(R.layout.activity_video);
		String url = getIntent().getStringExtra("com.thundercats.homeflix_mobile.streamurl");
		System.out.println("New activity got: " + url);
		int timestamp = prefs.getInt("timestamp", 0);
		System.out.println(timestamp);
		
		videoView = (VideoView) this.findViewById(R.id.videoView);
		videoView.setVideoURI(Uri.parse(url));
		videoView.setMediaController(new MediaController(this));
		videoView.requestFocus();
		videoView.seekTo(timestamp);
		videoView.start();
	}
	
	@Override
	public void onPause(){
		super.onPause();
		SharedPreferences prefs = getPreferences(MODE_PRIVATE);
		SharedPreferences.Editor editor = prefs.edit();
		editor.putInt("timestamp", videoView.getCurrentPosition());
		editor.commit();
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