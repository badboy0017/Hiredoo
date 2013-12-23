package com.projet.hiredoo;

import android.app.Activity;
import android.os.Bundle;

public class Voiroffres_activity extends Activity {
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.applyjob_view);
		
	}
	
	public void onBackPressed() {
		finish();
	}

}
