package com.projet.hiredoo;

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;

public class Detailjob_activity extends Activity implements OnClickListener {
	
	private Button postuler;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.detailjob_view);
		
		postuler = (Button)findViewById(R.id.detailjob_btnpostuler);
		postuler.setOnClickListener(this);
	}
	
	public void onBackPressed() {
		finish();
	}

	@Override
	public void onClick(View v) {
		Intent postuler_intent = new Intent(this, Postulerjob_activity.class);
		try {
			startActivity(postuler_intent);
		}
		catch (ActivityNotFoundException ex) {
			Toast.makeText(this, "Activity introuvable.\n" + ex.getMessage(), Toast.LENGTH_LONG).show();
		}
	}

}
