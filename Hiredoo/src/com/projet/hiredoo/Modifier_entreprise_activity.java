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

public class Modifier_entreprise_activity extends Activity implements OnClickListener {
	
	private EditText mod_name, mod_activity, mod_nbre, mod_address, mod_city, mod_website, mod_tel, mod_fax, mod_resume;
	private Button mod_btnrech;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.modifier_profil_entreprise_view);
		
		// Récuperation des views
		mod_name     = (EditText)findViewById(R.id.modifierEntreprise_name);
		mod_activity = (EditText)findViewById(R.id.modifierEntreprise_activitySector);
		mod_nbre     = (EditText)findViewById(R.id.modifierEntreprise_nbreemplyee);
		mod_address  = (EditText)findViewById(R.id.modifierEntreprise_address);
		mod_city     = (EditText)findViewById(R.id.modifierEntreprise_city);
		mod_website  = (EditText)findViewById(R.id.modifierEntreprise_website);
		mod_tel      = (EditText)findViewById(R.id.modifierEntreprise_telephone);
		mod_fax      = (EditText)findViewById(R.id.modifierEntreprise_fax);
		mod_resume   = (EditText)findViewById(R.id.modifierEntreprise_resume);
		
		mod_btnrech = (Button)findViewById(R.id.modifierEntreprise_btnmodifier);
		mod_btnrech.setOnClickListener(this);
		
	}
	
	public void onBackPressed() {
		finish();
	}

	@Override
	public void onClick(View v) {
		// Verification des champs
		if(this.mod_name.getText().toString().isEmpty() ||
				this.mod_activity.getText().toString().isEmpty() ||
				this.mod_nbre.getText().toString().isEmpty() ||
				this.mod_address.getText().toString().isEmpty() || 
				this.mod_city.getText().toString().isEmpty() || 
				this.mod_website.getText().toString().isEmpty() ||
				this.mod_tel.getText().toString().isEmpty() ||
				this.mod_fax.getText().toString().isEmpty() ||
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
			obj.put("email", Constante.getINIvalue(this, Constante.ini_email));
			obj.put("name", this.mod_name.getText().toString());
			obj.put("tel", this.mod_tel.getText().toString());
			obj.put("fax", this.mod_fax.getText().toString());
			obj.put("address", this.mod_address.getText().toString());
			obj.put("city", this.mod_city.getText().toString());
			obj.put("activity", this.mod_activity.getText().toString());
			obj.put("resume", this.mod_resume.getText().toString());
			obj.put("nbemplyee", this.mod_nbre.getText().toString());
	        obj.put("website", this.mod_website.getText().toString());
		}
		catch (JSONException ex) {
			AlertDialog.Builder builder = new AlertDialog.Builder(this);
			builder.setTitle("JSON Exception");
			builder.setMessage("Cause:\n" + ex.getCause() + "\n\nMessage:\n" + ex.getMessage());
			builder.create().show();
			return;
		}
		
		// Appel du web service PUT
		Async_put aput = new Async_put(this, obj, null);
		aput.execute(new String[] { Constante.url + Constante.enterprise });
	}

}
