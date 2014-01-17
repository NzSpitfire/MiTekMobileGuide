package com.example.mitekmobileguide;

import android.app.Activity;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.MotionEvent;
import android.widget.Toast;

public class StartUpActivity extends Activity {

	private boolean touched;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_startup);
		touched = false;
	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		if(!touched){
			Intent i = new Intent(getApplicationContext(), TermsActivity.class);
			startActivity(i);
			touched = true;
			
		}
		else{
			finish();
		}
		return super.onTouchEvent(event);
	}
	
	@Override
	public void onConfigurationChanged(Configuration newConfig) {
	    super.onConfigurationChanged(newConfig);

	    // Checks the orientation of the screen
	    if (newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE) {
	        Toast.makeText(this, "landscape", Toast.LENGTH_SHORT).show();
	    } else if (newConfig.orientation == Configuration.ORIENTATION_PORTRAIT){
	        Toast.makeText(this, "portrait", Toast.LENGTH_SHORT).show();
	    }
	}
}
