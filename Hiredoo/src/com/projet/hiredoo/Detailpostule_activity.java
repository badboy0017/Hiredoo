package com.projet.hiredoo;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.AlertDialog;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;
import android.widget.Toast;

public class Detailpostule_activity extends Activity implements OnClickListener {
        
	private TextView postule_user, postule_date, postule_cv, postule_lm, postule_video;
	private String json;
	private JSONObject jo;
	private String cv_path, lm_path, video_path, user_id;
        
    @Override
    protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.detailpostule_view);
            
            // Récupération des views
            postule_user = (TextView)findViewById(R.id.detailpostule_user);
            postule_date = (TextView)findViewById(R.id.detailpostule_date);
            postule_cv   = (TextView)findViewById(R.id.detailpostule_cv);
            postule_lm   = (TextView)findViewById(R.id.detailpostule_lm);
            postule_video = (TextView)findViewById(R.id.detailpostule_video);
            
            postule_user.setOnClickListener(this);
            postule_cv.setOnClickListener(this);
            postule_lm.setOnClickListener(this);
            postule_video.setOnClickListener(this);
            
    		// Recuperation des donnees
    		this.json = getIntent().getExtras().getString("data");
    		
    		// Formatage du resultat
    		try {
    			this.jo = new JSONObject(this.json);
    		}
    		catch (JSONException je) {
    			Toast.makeText(this, "Impossible de formater les données", Toast.LENGTH_LONG).show();
    			return;
    		}
    		
    		// Remplissage de la page
    		remplirPage();
    }
        
    public void onBackPressed() {
            finish();
    }

    @Override
    public void onClick(View v) {
    	switch(v.getId()) {
    	case R.id.detailpostule_user:
			// Appel du web service
    		Async_get ag = new Async_get(this, Profilcandidate_activity.class);
			ag.execute(new String[] { Constante.url + Constante.user_getUserProfile + this.user_id });
    		break;
    		
    	case R.id.detailpostule_cv:
    		Toast.makeText(this, "CV path: " + this.cv_path, Toast.LENGTH_LONG).show();
    		
    		// Appel du web service
    		//Async_get ag = new Async_get(this, Listpostule_activity.class);
    		//ag.execute(new String[] { Constante.url + Constante.postule_getUsersByPostule + getIntent().getExtras().getString("id") });
    		break;
    		
    	case R.id.detailpostule_lm:
    		Toast.makeText(this, "LM path: " + this.lm_path, Toast.LENGTH_LONG).show();
    		break;
    		
    	case R.id.detailpostule_video:
    		Toast.makeText(this, "Video path: " + this.video_path, Toast.LENGTH_LONG).show();
    		break;
    		
		default:
			AlertDialog.Builder error = new AlertDialog.Builder(this);
            error.setTitle("Internal error");
            error.setMessage("Listener caller unkwonen");
            error.create().show();
    	}
    }
    
    // Fonction de remplissage de la page
    private void remplirPage() {
    	// Recuperation des objets
    	JSONObject user, cv, lm, vid;
    	String date;
    	
    	try {
    		user = this.jo.getJSONObject("user");
        	cv   = this.jo.getJSONObject("cv");
        	lm   = this.jo.getJSONObject("lm");
        	vid  = this.jo.getJSONObject("video");
        	date = this.jo.get("postuleDate").toString();
		}
		catch (JSONException ex) {
			AlertDialog.Builder builder = new AlertDialog.Builder(this);
			builder.setTitle("JSONException1");
			builder.setMessage("Cause: " + ex.getCause() + "\n\nMessage: " + ex.getMessage());
			builder.create().show();
			return;
		}
    	
    	// Affichage des données
    	try {
    		this.user_id = user.get("id").toString();
    		this.postule_user.setText(((user.has("name")) ? user.get("name").toString() : "") + " " + ((user.has("lastname")) ? user.get("lastname").toString() : ""));
    		this.postule_date.setText(Constante.transformDate(date));
    		this.cv_path = cv.get("path").toString();
    		this.lm_path = lm.get("path").toString();
    		this.video_path = vid.get("path").toString();
		}
		catch (JSONException ex) {
			AlertDialog.Builder builder = new AlertDialog.Builder(this);
			builder.setTitle("JSONException2");
			builder.setMessage("Cause: " + ex.getCause() + "\n\nMessage: " + ex.getMessage());
			builder.create().show();
			return;
		}
    	
    }
                
}
