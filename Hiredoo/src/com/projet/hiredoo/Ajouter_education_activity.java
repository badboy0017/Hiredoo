package com.projet.hiredoo;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

public class Ajouter_education_activity extends Activity implements OnClickListener {
	
	private EditText educ_title, educ_diploma, educ_university, educ_location;
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
		
		educ_btnadd = (Button)findViewById(R.id.ajoutereducation_btnajouter);
		educ_btnadd.setOnClickListener(this);
		
	}
		
	public void onBackPressed() {
		finish();
	}

	@Override
	public void onClick(View arg0) {
		// TODO Auto-generated method stub
		
	}

}
