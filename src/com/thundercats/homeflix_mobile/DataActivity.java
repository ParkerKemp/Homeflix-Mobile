/*Homeflix-Mobile: DataActivity
 * 
 * Homeflix project for WKU CS496
 * Richie Davidson, Parker Kemp, Colin Page
 * Spring Semester 2014
 * 
 * Display metadata etc to user
 */

package com.thundercats.homeflix_mobile;

import android.os.Bundle;

public class DataActivity {
	String filename = getExtras();
	
	//@Override
	protected void onCreate(Bundle savedInstanceState) {
		
		//super.onCreate(savedInstanceState);

		setContentView(R.layout.activity_data);
		app = (Homeflix)getApplication();
		app.mainActivity = this;
	}
}