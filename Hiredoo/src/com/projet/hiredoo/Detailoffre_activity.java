package com.projet.hiredoo;

import android.app.Activity;
import android.app.AlertDialog;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

public class Detailoffre_activity extends Activity implements OnClickListener {
	
	private TextView offer_title, offer_date, offer_type, offer_domaine, offer_city, offer_description, offer_postule;
	private Button btn_supp;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.detailoffre_view);
		
		// Récupération des views
		offer_title = (TextView)findViewById(R.id.detailoffre_name);
		offer_date = (TextView)findViewById(R.id.detailoffre_date);
		offer_type = (TextView)findViewById(R.id.detailoffre_type);
		offer_domaine = (TextView)findViewById(R.id.detailoffre_domaine);
		offer_city = (TextView)findViewById(R.id.detailoffer_city);
		offer_description = (TextView)findViewById(R.id.detailoffre_description);
		offer_postule = (TextView)findViewById(R.id.detailoffre_postule);
		
		btn_supp = (Button)findViewById(R.id.detailoffre_btnsupp);
		btn_supp.setOnClickListener(this);
		
		// Recuperation et affichage des donnees
		this.offer_title.setText(getIntent().getExtras().getString("title"));
		this.offer_date.setText(this.offer_date.getText() + " " + getIntent().getExtras().getString("datecreation"));
		this.offer_domaine.setText(this.offer_domaine.getText() + " " + getIntent().getExtras().getString("domaine"));
		this.offer_city.setText(this.offer_city.getText() + " " + getIntent().getExtras().getString("city"));
		this.offer_type.setText(this.offer_type.getText() + " " + getIntent().getExtras().getString("type"));
		this.offer_description.setText(getIntent().getExtras().getString("description"));
		this.offer_postule.setText("13 candidates have postulate to this offer");
	}
	
	public void onBackPressed() {
		finish();
	}

	@Override
	public void onClick(View v) {
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
    	builder.setTitle("!!");
    	builder.setMessage("Fonction pas encore implémenter");
    	builder.create().show();
	}

}
