package com.projet.hiredoo;

import android.app.Activity;
import android.app.AlertDialog;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

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
	}

}
