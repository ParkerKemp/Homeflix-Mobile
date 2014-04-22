/*Homeflix-Mobile: SplashScreen
 * 
 * Homeflix project for WKU CS496
 * Richie Davidson, Parker Kemp, Colin Page
 * Spring Semester 2014
 * 
 * Splash screen with logo pops up briefly before passing to MainActivity
 */

package com.thundercats.homeflix_mobile;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
 
public class SplashScreen extends Activity {
 
    // Splash screen timer, thousandths of seconds
    private static int SPLASH_TIME_OUT = 1500;
 
    @Override
    protected void onCreate(Bundle savedInstanceState) {
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
}