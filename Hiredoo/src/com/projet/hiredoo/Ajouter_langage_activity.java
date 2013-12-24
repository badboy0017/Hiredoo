package com.projet.hiredoo;

import android.app.Activity;
import android.os.Bundle;

public class Ajouter_langage_activity extends Activity {
		
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.ajouter_langage_view);
		
	}
		
	public void onBackPressed() {
		finish();
	}

}
