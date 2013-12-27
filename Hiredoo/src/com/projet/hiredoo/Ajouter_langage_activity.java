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

public class Ajouter_langage_activity extends Activity implements OnClickListener {
	
	private EditText language_name;
	private Button btn_add;
		
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.ajouter_langage_view);
		
		// Récuperation des views
		language_name = (EditText)findViewById(R.id.ajouterlangage_langage);
		
		btn_add = (Button)findViewById(R.id.ajouterlangage_btnajouter);
		btn_add.setOnClickListener(this);
	}
		
	public void onBackPressed() {
		finish();
	}

	@Override
	public void onClick(View v) {
		// Verification des champs
		if(this.language_name.getText().toString().isEmpty()) {
			AlertDialog.Builder builder = new AlertDialog.Builder(this);
			builder.setTitle("Warning");
			builder.setMessage("Please fill the language field");
			builder.create().show();
			return;
		}

		// Préparation de l'objet JSON
		JSONObject obj = new JSONObject();
		try {
			obj.put("idUser", Constante.getINIvalue(this, Constante.ini_id));
	        obj.put("language", this.language_name.getText().toString());
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
		ap.execute(new String[] { Constante.url + Constante.language });
	}

}
