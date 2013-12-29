package com.projet.hiredoo;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

public class Rechercher_activity extends Activity implements OnClickListener {
	
	private EditText rech_text, rech_city;
	private Spinner rech_type;
	private Button rech_btnsearch;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.rechercher_view);
		
		// Récupération des views
		rech_text = (EditText)findViewById(R.id.rechercher_text);
		rech_city = (EditText)findViewById(R.id.rechercher_city);
		rech_type = (Spinner)findViewById(R.id.rechercher_type);
		
		rech_btnsearch = (Button)findViewById(R.id.rechercher_btnsearch);
		rech_btnsearch.setOnClickListener(this);
		
		//Modification des types de recherche
		if(Constante.getINIvalue(this, Constante.ini_type).equals(Constante.ini_type_recruiter)) {
			ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.rechercher_type_rectuiter, android.R.layout.simple_spinner_item);
			adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
			this.rech_type.setAdapter(adapter);
		}
	}
	
	public void onBackPressed() {
		Constante.listjob_type = Constante.listjob_type_all; // Pour dire à la liste des jobs que c'est pas une recherche
		finish();
	}

	@Override
	public void onClick(View v) {
		// Test si les champs sont vides
		if(this.rech_text.getText().toString().isEmpty()) {
			AlertDialog.Builder builder = new AlertDialog.Builder(this);
			builder.setTitle("Warning");
			builder.setMessage("Please fill the text field");
			builder.create().show();
			return;
		}
		
		// Verification du camps city
		String city;
		
		if(this.rech_city.getText().toString().trim().isEmpty()) {
			city = "000"; // juste pour dire que city est empty => Le serveur convertera la valeur en ""
		}
		else {
			city = this.rech_city.getText().toString().trim();
		}
		
		// Actualisation de next_activity
		Object next_activity;
		String classe;
		
		if(Constante.getINIvalue(this, Constante.ini_type).equals(Constante.ini_type_jobseeker)) {
			// Determination du next_activity
			switch(this.rech_type.getSelectedItemPosition()) {
			case 0:
				next_activity = Listjob_activity.class;
				classe = Constante.job_search;
				Constante.listjob_type = Constante.listjob_type_seach; // Pour afficher les jobs recherchés et pas tt les jobs
				
				// Appel de la liste des jobs
				Intent job_intent = new Intent(this, Listjob_activity.class);
				try {
					job_intent.putExtra("text", this.rech_text.getText().toString());
					job_intent.putExtra("city", city);
					startActivity(job_intent);
				}
				catch(ActivityNotFoundException ex1) {
					Toast.makeText(this, "Activity introuvable.\n" + ex1.getMessage(), Toast.LENGTH_LONG).show();
				}
				return; // pour finir et ne pas appeler le web service en bas
				
			case 1:
				next_activity = Listcandidate_activity.class;
				classe = Constante.user_search;
				break;
				
			case 2:
				next_activity = Listenterprise_activity.class;
				classe = Constante.enterprise_search;
				break;
				
			default:
				AlertDialog.Builder builder = new AlertDialog.Builder(this);
	        	builder.setTitle("Internal Exception");
	        	builder.setMessage("Error finding choice type");
	        	builder.create().show();
	        	return;
			}
	    }
	    else if(Constante.getINIvalue(this, Constante.ini_type).equals(Constante.ini_type_recruiter)) {
			// Determination du next_activity
			switch(this.rech_type.getSelectedItemPosition()) {
			case 0:
				next_activity = Listcandidate_activity.class;
				classe = Constante.user_search;
				break;
				
			case 1:
				next_activity = Listenterprise_activity.class;
				classe = Constante.enterprise_search;
				break;
				
			default:
				AlertDialog.Builder builder = new AlertDialog.Builder(this);
	        	builder.setTitle("Internal Exception");
	        	builder.setMessage("Error finding choice type");
	        	builder.create().show();
	        	return;
			}
	    }
	    else {
	    	AlertDialog.Builder builder = new AlertDialog.Builder(this);
        	builder.setTitle("Internal Exception");
        	builder.setMessage("Error finding user type");
        	builder.create().show();
        	return;
	    }
		
		// Appel du web service
		Async_get ag = new Async_get(this, next_activity);
		ag.execute(new String[] { Constante.url + classe + this.rech_text.getText().toString() + "/" + city });
	}

}
