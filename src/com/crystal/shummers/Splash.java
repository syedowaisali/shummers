package com.crystal.shummers;

import android.os.Bundle;
import android.os.Handler;

public class Splash extends BaseActivity {
	
	public void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		setContentView(R.layout.splash);
		
		// set handler
		new Handler().postDelayed(new Runnable() {
			
			@Override
			public void run() {
				// TODO Auto-generated method stub
				finish();
				gotoActivity(MainActivity.class);
			}
		}, Setting.SPLASH_TIMEOUT);
	}

	@Override
	public void onBackPressed() {
		// TODO Auto-generated method stub
		super.onBackPressed();
		overridePendingTransition (R.anim.open_main, R.anim.close_next);
	}
	
}
