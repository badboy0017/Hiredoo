package com.projet.hiredoo;

import android.app.Activity;
import android.os.Bundle;

public class Modifier_contact_jobseeker_activity extends Activity {
		
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.modifier_contact_jobseeker_view);
		
	}
		
	public void onBackPressed() {
		finish();
	}

}
