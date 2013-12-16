package com.projet.hiredoo;

import java.io.File;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity implements OnClickListener {
	
	ImageView presentation_close;
	ImageView presentation_go;
	
	ImageView login_ok;
	TextView login_recruter;
	TextView login_candidate;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		/*AlertDialog.Builder builder = new AlertDialog.Builder(this);
		builder.setMessage(Constante.readINIFile(this));
		builder.create().show();*/
		
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
			
			login_ok.setOnClickListener(this);
			login_recruter.setOnClickListener(this);
			login_candidate.setOnClickListener(this);
			return;
		}
		
		// Sinon
		// Appel AsyncTask pour avoir les données de listjob
		String type = Constante.getINIvalue(this, Constante.ini_type);
		if(type.equals(Constante.ini_type_jobseeker)) {
			Intent listjob_intent = new Intent(this, Listjob_activity.class);
			try {
				startActivity(listjob_intent);
			}
			catch(ActivityNotFoundException ex) {
				Toast.makeText(this, "Activity introuvable.\n" + ex.getMessage(), Toast.LENGTH_LONG).show();
			}
		}
		else if(type.equals(Constante.ini_type_recruter)) {
			/*Intent profilEnterprise_intent = new Intent(this, *.class);
			try {
				startActivity(profilEnterprise_intent);
			}
			catch(ActivityNotFoundException ex) {
				Toast.makeText(this, "Activity introuvable.\n" + ex.getMessage(), Toast.LENGTH_LONG).show();
			}*/
		}
		else {
			//Toast.makeText(this, "Erreur Type de connexion !!", Toast.LENGTH_LONG).show();
			Intent listjob_intent = new Intent(this, Listjob_activity.class);
			try {
				startActivity(listjob_intent);
			}
			catch(ActivityNotFoundException ex) {
				Toast.makeText(this, "Activity introuvable.\n" + ex.getMessage(), Toast.LENGTH_LONG).show();
			}
		}
		
		finish();
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
			// This will go to the AsyncTask and call WS
			
			
			// Appel du web service
			Async_post ap = new Async_post(this);
			ap.execute(new String[] { "http://10.0.2.2:8080/hello/helloworld", "hamza just test" });
			
			
			/*Intent listjob_intent = new Intent(this, Listjob_activity.class);
			try {
				startActivity(listjob_intent);
			}
			catch(ActivityNotFoundException ex) {
				Toast.makeText(this, "Activity introuvable.\n" + ex.getMessage(), Toast.LENGTH_LONG).show();
			}
			finish();*/
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
