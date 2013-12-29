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
import android.widget.TextView;


public class Ajouter_experience_activity extends Activity implements OnClickListener {
	
	private EditText exp_title, exp_entreprise, exp_location, exp_link, exp_description;
	private TextView exp_datefrom, exp_dateto;
	private Button exp_btnadd;
		
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.ajouter_experience_view);
		
		// Récuperation des views
		exp_title       = (EditText)findViewById(R.id.ajouter_experience_title);
		exp_entreprise  = (EditText)findViewById(R.id.ajouter_experience_entreprise);
		exp_location    = (EditText)findViewById(R.id.ajouter_experience_location);
		exp_link        = (EditText)findViewById(R.id.ajouter_experience_link);
		exp_description = (EditText)findViewById(R.id.ajouter_experience_description);
		
		exp_datefrom = (TextView)findViewById(R.id.ajouter_experience_datefrom);
		exp_dateto   = (TextView)findViewById(R.id.ajouter_experience_dateto);
		
		exp_datefrom.setOnClickListener(this);
		exp_dateto.setOnClickListener(this);
		
		exp_btnadd = (Button)findViewById(R.id.ajouter_experience_btnajouter);
		exp_btnadd.setOnClickListener(this);
	}
		
	public void onBackPressed() {
		finish();
	}

	@Override
	public void onClick(View v) {
		switch(v.getId()) {
		case R.id.ajouter_experience_datefrom:
			// Affichage du selecteur de date
			SelectDate newFragment = new SelectDate();
			newFragment.initialise(exp_datefrom, Constante.date_from_type);
	        newFragment.show(getFragmentManager(), "DatePicker");
			break;
			
		case R.id.ajouter_experience_dateto:
			// Affichage du selecteur de date
			SelectDate newFragment2 = new SelectDate();
			newFragment2.initialise(exp_dateto, Constante.date_to_type);
	        newFragment2.show(getFragmentManager(), "DatePicker");
			break;
			
		case R.id.ajouter_experience_btnajouter:
			this.traitement_ajouter();
			break;
		
		default:
			AlertDialog.Builder buider = new AlertDialog.Builder(this);
			buider.setTitle("Error");
			buider.setMessage("Internal error occured. View unknowen");
			buider.create().show();
		}
	}
	
	private void traitement_ajouter() {
		// Verification des champs
		if(this.exp_title.getText().toString().isEmpty() ||
				this.exp_entreprise.getText().toString().isEmpty() ||
				this.exp_location.getText().toString().isEmpty() ||
				this.exp_description.getText().toString().isEmpty() || 
				!this.exp_datefrom.getText().toString().contains("/") ||
				!this.exp_dateto.getText().toString().contains("/")) {
			
			AlertDialog.Builder builder = new AlertDialog.Builder(this);
			builder.setTitle("Warning");
			builder.setMessage("Please fill all the fields");
			builder.create().show();
			return;
		}
		
		// Préparation de l'objet JSON
		JSONObject obj = new JSONObject();
		try {
			obj.put("idUser", Constante.getINIvalue(this, Constante.ini_id));
			obj.put("title", this.exp_title.getText().toString());
			obj.put("dateFrom", Constante.date_from);
			obj.put("dateTo", Constante.date_to);
			obj.put("description", this.exp_description.getText().toString());
			obj.put("entreprise", this.exp_entreprise.getText().toString());
			obj.put("location", this.exp_location.getText().toString());
	        
	        if(!this.exp_link.getText().toString().isEmpty()) {
	        	obj.put("link", this.exp_link.getText().toString());
	        }
		}
		catch (JSONException ex) {
			AlertDialog.Builder builder = new AlertDialog.Builder(this);
			builder.setTitle("JSON Exception");
			builder.setMessage("Cause:\n" + ex.getCause() + "\n\nMessage:\n" + ex.getMessage());
			builder.create().show();
			return;
		}
		
		// Appel du web service POST
		Async_post ap = new Async_post(this, obj, null);
		ap.execute(new String[] { Constante.url + Constante.experience });
	}

}
