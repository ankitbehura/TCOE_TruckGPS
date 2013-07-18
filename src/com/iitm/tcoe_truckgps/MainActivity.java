package com.iitm.tcoe_truckgps;

import java.util.Timer;
import java.util.TimerTask;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;

public class MainActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		Timer timer_begin= new Timer();
		timer_begin.schedule(new TimerTask() {
			
			@Override
			public void run() {
				// TODO Auto-generated method stub
				Intent i=new Intent(getApplicationContext(), HomeActivity.class);
				startActivity(i);
				finish();
			}
		},3*1000);
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}
