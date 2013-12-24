package com.projet.hiredoo;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

public class Modifierentreprise_activity extends Activity implements OnClickListener {
	
	private EditText mod_name, mod_activity, mod_resume, mod_address, mod_website, mod_tel, mod_fax;
	private Button mod_btnrech;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.modifier_profil_entreprise_view);
		
		// Récuperation des views
		mod_name = (EditText)findViewById(R.id.modifierEntreprise_name);
		mod_activity = (EditText)findViewById(R.id.modifierEntreprise_activitySector);
		mod_resume = (EditText)findViewById(R.id.modifierEntreprise_resume);
		mod_address = (EditText)findViewById(R.id.modifierEntreprise_address);
		mod_website = (EditText)findViewById(R.id.modifierEntreprise_website);
		mod_tel = (EditText)findViewById(R.id.modifierEntreprise_telephone);
		mod_fax = (EditText)findViewById(R.id.modifierEntreprise_fax);
		
		mod_btnrech = (Button)findViewById(R.id.modifierEntreprise_btnmodifier);
		mod_btnrech.setOnClickListener(this);
		
	}
	
	public void onBackPressed() {
		finish();
	}

	@Override
	public void onClick(View v) {
		
	}

}
