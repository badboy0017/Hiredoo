package com.projet.hiredoo;

import android.app.Activity;
import android.os.Bundle;

public class Ajouter_education_activity extends Activity {
		
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.ajouter_education_view);
		
	}
		
	public void onBackPressed() {
		finish();
	}

}
