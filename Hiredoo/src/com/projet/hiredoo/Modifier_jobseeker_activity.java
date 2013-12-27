package com.projet.hiredoo;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.AlertDialog;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

public class Modifier_jobseeker_activity extends Activity implements OnClickListener {
	
	private EditText mod_name, mod_lastname, mod_profiletitle, mod_address, mod_city, mod_tel, mod_website, mod_resume;
	private Spinner mod_socialsituation;
	private Button mod_editbtn;
		
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.modifier_profil_jobseeker_view);
		
		// Récuperation des views
		mod_name         = (EditText)findViewById(R.id.modifierprofil_jobseeker_name);
		mod_lastname     = (EditText)findViewById(R.id.modifierprofil_jobseeker_lastname);
		mod_profiletitle = (EditText)findViewById(R.id.modifierprofil_jobseeker_profiltitle);
		mod_address      = (EditText)findViewById(R.id.modifierprofil_jobseeker_address);
		mod_city         = (EditText)findViewById(R.id.modifierprofil_jobseeker_city);
		mod_tel          = (EditText)findViewById(R.id.modifierprofil_jobseeker_telephone);
		mod_website      = (EditText)findViewById(R.id.modifierprofil_jobseeker_website);
		mod_resume       = (EditText)findViewById(R.id.modifierprofil_jobseeker_resume);
		
		mod_socialsituation = (Spinner)findViewById(R.id.modifierprofil_jobseeker_socialsituation);
		mod_editbtn = (Button)findViewById(R.id.modifierprofil_jobseeker_btnmodifier);
		mod_editbtn.setOnClickListener(this);
	}
		
	public void onBackPressed() {
		finish();
	}

	@Override
	public void onClick(View v) {
		// Verification des champs
		if(this.mod_name.getText().toString().isEmpty() ||
				this.mod_lastname.getText().toString().isEmpty() ||
				this.mod_profiletitle.getText().toString().isEmpty() ||
				this.mod_address.getText().toString().isEmpty() || 
				this.mod_city.getText().toString().isEmpty() || 
				this.mod_tel.getText().toString().isEmpty() || 
				this.mod_website.getText().toString().isEmpty() ||
				this.mod_resume.getText().toString().isEmpty()) {
			
			AlertDialog.Builder builder = new AlertDialog.Builder(this);
			builder.setTitle("Warning");
			builder.setMessage("Please fill all the fields");
			builder.create().show();
			return;
		}

		// Préparation de l'objet JSON
		JSONObject obj = new JSONObject();
		try {
			obj.put("id", Constante.getINIvalue(this, Constante.ini_id));
			obj.put("password", Constante.getINIvalue(this, Constante.ini_password));
			obj.put("name", this.mod_name.getText().toString());
			obj.put("lastname", this.mod_lastname.getText().toString());
			obj.put("email", Constante.getINIvalue(this, Constante.ini_email));
			obj.put("tel", this.mod_tel.getText().toString());
			obj.put("address", this.mod_address.getText().toString());
			obj.put("city", this.mod_city.getText().toString());
			obj.put("socialsituation", this.mod_socialsituation.getSelectedItem().toString());
			obj.put("resume", this.mod_resume.getText().toString());
			obj.put("titleprofile", this.mod_profiletitle.getText().toString());
	        obj.put("website", this.mod_website.getText().toString());
		}
		catch (JSONException ex) {
			AlertDialog.Builder builder = new AlertDialog.Builder(this);
			builder.setTitle("JSON Exception");
			builder.setMessage("Cause:\n" + ex.getCause() + "\n\nMessage:\n" + ex.getMessage());
			builder.create().show();
			return;
		}
		
		// Appel du web service POST
		Async_put aput = new Async_put(this, obj, null);
		aput.execute(new String[] { Constante.url + Constante.user });
	}

}
