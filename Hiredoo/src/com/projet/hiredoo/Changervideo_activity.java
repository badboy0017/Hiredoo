package com.projet.hiredoo;

import org.json.JSONArray;
import org.json.JSONException;

import android.app.Activity;
import android.app.AlertDialog;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

public class Changervideo_activity extends Activity implements OnClickListener {
	
	private Spinner video;
	private Button btn_modifier;
	private JSONArray ja;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.changervideo_view);
		
		// Récuperation des views
		video = (Spinner)findViewById(R.id.changervideo_video);
		
		btn_modifier = (Button)findViewById(R.id.changervideo_btnmodifier);
		btn_modifier.setOnClickListener(this);
		
		// Récuperation des données
		String data = getIntent().getExtras().getString("data");
		
		// Formatage du resultat
		try {
			this.ja = new JSONArray(data);
		}
		catch (JSONException je) {
			Toast.makeText(this, "Impossible de formater les données", Toast.LENGTH_LONG).show();
			return;
		}
		
		// Test de la présence des données
		if(this.ja.length() == 0) {
			AlertDialog.Builder builder = new AlertDialog.Builder(this);
			builder.setTitle("Warning");
			builder.setMessage("You have no Video.\nPlease add a video before you choose one");
			builder.create().show();
			
			// Disabling views
			this.video.setEnabled(false);
			this.btn_modifier.setEnabled(false);
			return;
		}
		
		// Ajout des données au spinners
		try {
			// Ajout des données au Video
			String[] str_video = new String[this.ja.length()];
			for(int i=0 ; i<this.ja.length() ; i++) {
				str_video[i] = this.ja.getJSONObject(i).getString("name");
			}
			
			ArrayAdapter<String> videoArrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, str_video);
		    videoArrayAdapter.setDropDownViewResource( android.R.layout.simple_spinner_dropdown_item );
		    this.video.setAdapter(videoArrayAdapter);
		}
		catch(JSONException ex) {
			AlertDialog.Builder builder = new AlertDialog.Builder(this);
			builder.setTitle("JSONException");
			builder.setMessage("Cause: " + ex.getCause() + "\n\nMessage: " + ex.getMessage());
			builder.create().show();
			return;
		}
	}
	
	public void onBackPressed() {
		finish();
	}

	@Override
	public void onClick(View v) {
		String id_video = null;
		
		// Recuperation des ID
		try {
			id_video = this.ja.getJSONObject((int) this.video.getSelectedItemId()).get("id").toString();
		}
		catch (JSONException ex) {
			AlertDialog.Builder builder = new AlertDialog.Builder(this);
			builder.setTitle("JSON Exception2");
			builder.setMessage("Cause:\n" + ex.getCause() + "\n\nMessage:\n" + ex.getMessage());
			builder.create().show();
		}
		
		// Appel du web service GET
		Async_get ag = new Async_get(this, null);
		ag.execute(new String[] { Constante.url + Constante.user_chooseVideo + Constante.getINIvalue(this, Constante.ini_id) + "/" + id_video });
	}

}
