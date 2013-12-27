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

public class Ajouteroffre_activity extends Activity implements OnClickListener {
	
	private EditText offer_title, offer_city, offer_description;
	private Spinner offer_type, offer_domaine;
	private Button btn_ajouter;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.ajouteroffre_view);
		
		// Récuperation des views
		offer_title       = (EditText)findViewById(R.id.ajouteroffre_titre);
		offer_city        = (EditText)findViewById(R.id.ajouteroffre_city);
		offer_description = (EditText)findViewById(R.id.ajouteroffre_description);
		offer_type        = (Spinner)findViewById(R.id.ajouteroffre_type);
		offer_domaine     = (Spinner)findViewById(R.id.ajouteroffre_domaine);
		
		btn_ajouter = (Button)findViewById(R.id.ajouteroffre_btnajouter);
		btn_ajouter.setOnClickListener(this);
	}
	
	public void onBackPressed() {
		finish();
	}

	@Override
	public void onClick(View v) {
		// Verification des champs
		if(this.offer_title.getText().toString().isEmpty() || this.offer_city.getText().toString().isEmpty() || this.offer_description.getText().toString().isEmpty()) {
			AlertDialog.Builder builder = new AlertDialog.Builder(this);
			builder.setTitle("Warning");
			builder.setMessage("Please fill all the fields");
			builder.create().show();
			return;
		}
		
		// Préparation de l'objet JSON
		JSONObject obj = new JSONObject();
		try {
			obj.put("idEntreprise", Constante.getINIvalue(this, Constante.ini_id));
			obj.put("title", this.offer_title.getText().toString());
	        obj.put("type", this.offer_type.getSelectedItem().toString());
	        obj.put("description", this.offer_description.getText().toString());
	        obj.put("city", this.offer_city.getText().toString());
	        obj.put("domaine", this.offer_domaine.getSelectedItem().toString());
	        obj.put("datecreation", Constante.getCurrentDate());
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
		ap.execute(new String[] { Constante.url + Constante.job });
	}

}
