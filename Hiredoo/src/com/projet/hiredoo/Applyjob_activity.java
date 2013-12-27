package com.projet.hiredoo;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

public class Applyjob_activity extends Activity implements OnClickListener {
	
	private TextView job_title, job_type, job_description, job_city, job_date, job_domain;
	private Button postuler;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.applyjob_view);
		
		// Recuperation des views
		job_title       = (TextView)findViewById(R.id.applyjob_name);
		job_type        = (TextView)findViewById(R.id.applyjob_type);
		job_description = (TextView)findViewById(R.id.applyjob_description);
		job_city        = (TextView)findViewById(R.id.applyjob_city);
		job_date        = (TextView)findViewById(R.id.applyjob_date);
		job_domain      = (TextView)findViewById(R.id.applyjob_domaine);
		
		postuler = (Button)findViewById(R.id.applyjob_btnpostuler);
		postuler.setOnClickListener(this);
		
		// Recuperation et affichage des donnees
		this.job_title.setText(getIntent().getExtras().getString("title"));
		this.job_type.setText(this.job_type.getText() + " " + getIntent().getExtras().getString("type"));
		this.job_description.setText(getIntent().getExtras().getString("description"));
		this.job_city.setText(this.job_city.getText() + " " + getIntent().getExtras().getString("city"));
		this.job_date.setText(this.job_date.getText() + " " + getIntent().getExtras().getString("datecreation"));
		this.job_domain.setText(this.job_domain.getText() + " " + getIntent().getExtras().getString("domaine"));
	}
	
	public void onBackPressed() {
		finish();
	}

	@Override
	public void onClick(View v) {
		// Sauvegarde de l'ID du job
		Constante.job_id = getIntent().getExtras().getString("id");
		
		// Appel du web service GET
		Async_get ag = new Async_get(this, Postulerjob_activity.class);
		ag.execute(new String[] { Constante.url + Constante.user_getCvLmVideo + Constante.getINIvalue(this, Constante.ini_id) });
	}

}
