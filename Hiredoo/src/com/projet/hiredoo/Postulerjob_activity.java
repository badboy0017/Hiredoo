package com.projet.hiredoo;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.AlertDialog;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

public class Postulerjob_activity extends Activity implements OnClickListener {
	
	private Spinner cv, lm, video;
	private Button btn_apply;
	private JSONObject jo;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.postulerjob_view);
		
		// Récuperation des views
		cv    = (Spinner)findViewById(R.id.postulerjob_cv);
		lm    = (Spinner)findViewById(R.id.postulerjob_lm);
		video = (Spinner)findViewById(R.id.postulerjob_video);
		
		btn_apply = (Button)findViewById(R.id.postulerjob_btnpostuler);
		btn_apply.setOnClickListener(this);
		
		// Récuperation des données
		String data = getIntent().getExtras().getString("data");
		
		// Formatage du resultat
		try {
			this.jo = new JSONObject(data);
		}
		catch (JSONException je) {
			Toast.makeText(this, "Impossible de formater les données", Toast.LENGTH_LONG).show();
			return;
		}
		
		// Test de la présence des données
		try {
			if(this.jo.getJSONArray("cv").length() == 0) {
				AlertDialog.Builder builder = new AlertDialog.Builder(this);
				builder.setTitle("Warning");
				builder.setMessage("You have no CV.\nPlease add a CV then apply");
				builder.create().show();
				
				// Disabling views
				this.cv.setEnabled(false);
				this.lm.setEnabled(false);
				this.video.setEnabled(false);
				this.btn_apply.setEnabled(false);
				return;
			}
			
			if(this.jo.getJSONArray("resume").length() == 0) {
				AlertDialog.Builder builder = new AlertDialog.Builder(this);
				builder.setTitle("Warning");
				builder.setMessage("You have no Cover letter.\nPlease add a letter then apply");
				builder.create().show();
				
				// Disabling views
				this.lm.setEnabled(false);
				this.video.setEnabled(false);
				this.btn_apply.setEnabled(false);
				return;
			}
			
			if(this.jo.getJSONArray("video").length() == 0) {
				AlertDialog.Builder builder = new AlertDialog.Builder(this);
				builder.setTitle("Warning");
				builder.setMessage("You have no Video.\nPlease add a video then apply");
				builder.create().show();
				
				// Disabling views
				this.video.setEnabled(false);
				this.btn_apply.setEnabled(false);
				return;
			}
		}
		catch(JSONException ex) {
			AlertDialog.Builder builder = new AlertDialog.Builder(this);
			builder.setTitle("JSONException1");
			builder.setMessage("Cause: " + ex.getCause() + "\n\nMessage: " + ex.getMessage());
			builder.create().show();
			return;
		}
		
		// Ajout des données au spinners
		try {
			// Ajout des données au CV
			JSONArray cvs = this.jo.getJSONArray("cv");
			String[] str_cv = new String[cvs.length()];
			for(int i=0 ; i<cvs.length() ; i++) {
				str_cv[i] = cvs.getJSONObject(i).getString("name");
			}
			
			ArrayAdapter<String> cvArrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, str_cv);
		    cvArrayAdapter.setDropDownViewResource( android.R.layout.simple_spinner_dropdown_item );
		    this.cv.setAdapter(cvArrayAdapter);
			
			// Ajout des données au LM
			JSONArray lms = this.jo.getJSONArray("resume");
			String[] str_lm = new String[lms.length()];
			for(int i=0 ; i<lms.length() ; i++) {
				str_lm[i] = lms.getJSONObject(i).getString("name");
			}
			
			ArrayAdapter<String> lmArrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, str_lm);
		    lmArrayAdapter.setDropDownViewResource( android.R.layout.simple_spinner_dropdown_item );
		    this.lm.setAdapter(lmArrayAdapter);
			
			// Ajout des données au Video
			JSONArray videos = this.jo.getJSONArray("video");
			String[] str_video = new String[videos.length()];
			for(int i=0 ; i<videos.length() ; i++) {
				str_video[i] = videos.getJSONObject(i).getString("name");
			}
			
			ArrayAdapter<String> videoArrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, str_video);
		    videoArrayAdapter.setDropDownViewResource( android.R.layout.simple_spinner_dropdown_item );
		    this.video.setAdapter(videoArrayAdapter);
		}
		catch(JSONException ex) {
			AlertDialog.Builder builder = new AlertDialog.Builder(this);
			builder.setTitle("JSONException2");
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
		String id_cv = null, id_lm = null, id_video = null;
		
		// Recuperation des ID
		try {
			id_cv = this.jo.getJSONArray("cv").getJSONObject((int) this.cv.getSelectedItemId()).get("id").toString();
			id_lm = this.jo.getJSONArray("resume").getJSONObject((int) this.lm.getSelectedItemId()).get("id").toString();
			id_video = this.jo.getJSONArray("video").getJSONObject((int) this.video.getSelectedItemId()).get("id").toString();
		}
		catch (JSONException ex) {
			AlertDialog.Builder builder = new AlertDialog.Builder(this);
			builder.setTitle("JSON Exception3");
			builder.setMessage("Cause:\n" + ex.getCause() + "\n\nMessage:\n" + ex.getMessage());
			builder.create().show();
		}
		
		
		// Préparation de l'objet JSON
		JSONObject obj = new JSONObject();
		try {
			obj.put("idJob", Constante.job_id);
			obj.put("idUser", Constante.getINIvalue(this, Constante.ini_id));
	        obj.put("resume", id_lm);
	        obj.put("cv", id_cv);
	        obj.put("datePostule", Constante.getCurrentDate());
	        obj.put("video", id_video);
		}
		catch (JSONException ex) {
			AlertDialog.Builder builder = new AlertDialog.Builder(this);
			builder.setTitle("JSON Exception4");
			builder.setMessage("Cause:\n" + ex.getCause() + "\n\nMessage:\n" + ex.getMessage());
			builder.create().show();
			return;
		}
		
		// Appel du web service POST
		Async_post ap = new Async_post(this, obj, null);
		ap.execute(new String[] { Constante.url + Constante.postule });
	}

}
