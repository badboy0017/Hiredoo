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

public class Ajouter_education_activity extends Activity implements OnClickListener {
	
	private EditText educ_title, educ_diploma, educ_university, educ_location;
	private TextView educ_datefrom, educ_dateto;
	private Button educ_btnadd;
		
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.ajouter_education_view);
		
		// Récuperation des views
		educ_title = (EditText)findViewById(R.id.ajoutereducation_title);
		educ_diploma = (EditText)findViewById(R.id.ajoutereducation_deploma);
		educ_university = (EditText)findViewById(R.id.ajoutereducation_university);
		educ_location = (EditText)findViewById(R.id.ajoutereducation_location);
		
		educ_datefrom = (TextView)findViewById(R.id.ajoutereducation_datefrom);
		educ_dateto   = (TextView)findViewById(R.id.ajoutereducation_dateto);
		
		educ_datefrom.setOnClickListener(this);
		educ_dateto.setOnClickListener(this);
		
		educ_btnadd = (Button)findViewById(R.id.ajoutereducation_btnajouter);
		educ_btnadd.setOnClickListener(this);
		
	}
		
	public void onBackPressed() {
		finish();
	}

	@Override
	public void onClick(View v) {
		switch(v.getId()) {
		case R.id.ajoutereducation_datefrom:
			// Affichage du selecteur de date
			SelectDate newFragment = new SelectDate();
			newFragment.initialise(educ_datefrom, Constante.date_from_type);
	        newFragment.show(getFragmentManager(), "DatePicker");
			break;
			
		case R.id.ajoutereducation_dateto:
			// Affichage du selecteur de date
			SelectDate newFragment2 = new SelectDate();
			newFragment2.initialise(educ_dateto, Constante.date_to_type);
	        newFragment2.show(getFragmentManager(), "DatePicker");
			break;
			
		case R.id.ajoutereducation_btnajouter:
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
		if(this.educ_title.getText().toString().isEmpty() ||
				this.educ_diploma.getText().toString().isEmpty() ||
				this.educ_university.getText().toString().isEmpty() ||
				this.educ_location.getText().toString().isEmpty() ||
				!this.educ_datefrom.getText().toString().contains("/") ||
				!this.educ_dateto.getText().toString().contains("/")) {
			
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
			obj.put("title", this.educ_title.getText().toString());
			obj.put("deploma", this.educ_diploma.getText().toString());
			obj.put("dateFrom", Constante.date_from);
			obj.put("dateTo", Constante.date_to);
			obj.put("university", this.educ_university.getText().toString());
			obj.put("location", this.educ_location.getText().toString());
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
		ap.execute(new String[] { Constante.url + Constante.education });
	}

}
