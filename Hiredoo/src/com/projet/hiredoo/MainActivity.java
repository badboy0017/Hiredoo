package com.projet.hiredoo;

import java.io.File;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity implements OnClickListener {
	
	private ImageView presentation_close, presentation_go;
	
	private ImageView login_ok;
	private TextView login_recruter, login_candidate;
	private EditText email, password;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		// Test du 1er lancement de l'application
		File ini = new File(getFileStreamPath(Constante.file_ini).toString());
		if(!ini.exists()) {
			setContentView(R.layout.presentation_view);
			
			presentation_close = (ImageView)findViewById(R.id.presentation_btnClose);
			presentation_go    = (ImageView)findViewById(R.id.presentation_btnGoNow);
			
			presentation_close.setOnClickListener(this);
			presentation_go.setOnClickListener(this);
			return;
		}
		
		// Test si le user n'est pas connecté
		if(Constante.getINIvalue(this, Constante.ini_remember).equals("false")) {
			setContentView(R.layout.login_view);
			
			login_ok = (ImageView)findViewById(R.id.login_btnOk);
			login_recruter  = (TextView)findViewById(R.id.login_register_recruter);
			login_candidate = (TextView)findViewById(R.id.login_register_candidate);
			email = (EditText)findViewById(R.id.login_username);
			password = (EditText)findViewById(R.id.login_password);
			
			login_ok.setOnClickListener(this);
			login_recruter.setOnClickListener(this);
			login_candidate.setOnClickListener(this);
			return;
		}
		
		// Sinon: user connecté
		Intent listjob_intent = new Intent(this, Listjob_activity.class);
		try {
			startActivity(listjob_intent);
			finish(); // Pour arreter cette activité quand on affiche la liste des jobs
		}
		catch(ActivityNotFoundException ex) {
			Toast.makeText(this, "Activity introuvable.\n" + ex.getMessage(), Toast.LENGTH_LONG).show();
		}
	}
	
	// Just for test => a enlever apres!!
	@Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if(keyCode == KeyEvent.KEYCODE_MENU ) {
        	Constante.deleteINIFile(this);
        	Toast.makeText(this, "File deleted", Toast.LENGTH_SHORT).show();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

	@Override
	public void onClick(View v) {
		switch(v.getId()) {
		// Presentation View
		case R.id.presentation_btnClose:
			finish();
			break;
			
		case R.id.presentation_btnGoNow:
			Constante.createINIFile(this);
			recreate();
			break;
			
		// Login View
		case R.id.login_btnOk:
			// Test si les champs sont vides
			if(this.email.getText().toString().isEmpty() || this.password.getText().toString().isEmpty()) {
				AlertDialog.Builder builder = new AlertDialog.Builder(this);
				builder.setTitle("Warning");
				builder.setMessage("Please fill all the fields");
				builder.create().show();
				return;
			}
			
			// Verification des espaces
			if(this.email.getText().toString().contains(" ") || this.password.getText().toString().contains(" ")) {
				AlertDialog.Builder builder = new AlertDialog.Builder(this);
				builder.setTitle("Warning");
				builder.setMessage("Caracter space is not allowed");
				builder.create().show();
				return;
			}
			
			// Préparation de l'objet JSON
			JSONObject obj = new JSONObject();
			try {
	            obj.put("email", this.email.getText().toString());
	            obj.put("password", this.password.getText().toString());
			}
			catch (JSONException ex) {
				Toast.makeText(this, "Erreur JSON\n" + ex.getMessage(), Toast.LENGTH_LONG).show();
			}
            
			// Appel du web service POST
			Async_post ap = new Async_post(this, obj, null);
			ap.execute(new String[] { Constante.url + Constante.user_login, Constante.url + Constante.enterprise_login });
			break;
			
		case R.id.login_register_recruter:
			Intent recruter_intent = new Intent(this, RecruiterRegisterActivity.class);
			try {
				startActivity(recruter_intent);
			}
			catch(ActivityNotFoundException ex) {
				Toast.makeText(this, "Activity introuvable.\n" + ex.getMessage(), Toast.LENGTH_LONG).show();
			}
			break;
			
		case R.id.login_register_candidate:
			Intent candidate_intent = new Intent(this, JobSeeckerRegisterActivity.class);
			try {
				startActivity(candidate_intent);
			}
			catch(ActivityNotFoundException ex) {
				Toast.makeText(this, "Activity introuvable.\n" + ex.getMessage(), Toast.LENGTH_LONG).show();
			}
			break;
		}
	}

}
