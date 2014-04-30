package com.thundercats.homeflix_mobile;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.widget.VideoView;
import android.widget.MediaController;

public class VideoStream extends Activity{

	@Override
	protected void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_video);
		String url = getIntent().getStringExtra("com.thundercats.homeflix_mobile.streamurl");
		System.out.println("New activity got: " + url);
		VideoView videoView = (VideoView) this.findViewById(R.id.videoView);
		videoView.setVideoURI(Uri.parse(url));        
		videoView.setMediaController(new MediaController(this));
		videoView.seekTo(2000);
		videoView.requestFocus();
		videoView.start();
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