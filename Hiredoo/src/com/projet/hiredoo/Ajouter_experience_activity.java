package com.projet.hiredoo;

import android.app.Activity;
import android.os.Bundle;

public class Ajouter_experience_activity extends Activity {
		
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.ajouter_experience_view);
		
	}
		
	public void onBackPressed() {
		finish();
	}

}
