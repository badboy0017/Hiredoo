package com.projet.hiredoo;

import android.app.Activity;
import android.os.Bundle;

public class Modifierentreprise_activity extends Activity {
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.modifier_profil_entreprise_view);
		
	}
	
	public void onBackPressed() {
		finish();
	}

}
