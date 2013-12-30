package com.projet.hiredoo;

import java.io.File;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class Ajoutercv_activity extends Activity implements OnClickListener, android.content.DialogInterface.OnClickListener {
	
	private TextView cv_path;
	private Button cv_browse, cv_ajouter;
	private File current_path;
	private File[] listFiles;
    private String[] files;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.ajoutercv_view);
		
		// Récuperation des views
		cv_path    = (TextView)findViewById(R.id.ajoutercv_path);
		cv_browse  = (Button)findViewById(R.id.ajoutercv_btnbrowse);
		cv_ajouter = (Button)findViewById(R.id.ajoutercv_btnajouter);
		
		cv_browse.setOnClickListener(this);
		cv_ajouter.setOnClickListener(this);
	}
	
	@Override
	public void onSaveInstanceState(Bundle outState) {
	    super.onSaveInstanceState(outState);
	    outState.putString("cv_path", this.cv_path.getText().toString());
	}
	
	@Override
	public void onRestoreInstanceState(Bundle savedInstanceState) {
	    super.onRestoreInstanceState(savedInstanceState);
	    
	    if(savedInstanceState != null && savedInstanceState.containsKey("cv_path"))
	    	this.cv_path.setText(savedInstanceState.getString("cv_path"));
	}

	@Override
	public void onClick(View v) {
		switch(v.getId()) {
		case R.id.ajoutercv_btnbrowse:
			Constante.path = Constante.path_static;
			this.browsing();
			break;
			
			
		case R.id.ajoutercv_btnajouter:
			this.upload();
			break;
			
		default:
			AlertDialog.Builder builder = new AlertDialog.Builder(this);
			builder.setTitle("Intern Error");
			builder.setMessage("Button ID not found");
			builder.create().show();
		}
	}

	// Fonction de click sur un element de la liste de AlertDialog.Builder
	@Override
	public void onClick(DialogInterface dialog, int position) {
		Constante.path = this.listFiles[position].getPath();
		this.browsing();
	}
	
	private void browsing() {
		this.current_path = new File(Constante.path);
		
		// Test si un fichier est selectionné
		if(this.current_path.isFile()) {
			this.cv_path.setText(this.current_path.getPath());
			return;
		}
		
		// Test si on peut lire ce dossier
		if(!this.current_path.canRead()) {
			Toast.makeText(this, "You cannot read this directory.\nNo read access", Toast.LENGTH_SHORT).show();
			return;
		}
		
		this.listFiles = current_path.listFiles();
        this.files = new String[this.listFiles.length];
        
        for (int i=0 ; i<listFiles.length ; i++) {
        	files[i] = listFiles[i].getName();
		}
        
		AlertDialog.Builder browse = new AlertDialog.Builder(this);
		browse.setTitle("Browse file");
		browse.setItems(files, this);
		if(!Constante.path.equals(Constante.path_static)) {
			browse.setNegativeButton("Parent", new android.content.DialogInterface.OnClickListener() {
				
				@Override
				public void onClick(DialogInterface dialog, int which) {
					Constante.path = Ajoutercv_activity.this.current_path.getParent();
					Ajoutercv_activity.this.browsing();
				}
			});
		}
		browse.create().show();
	}
	
	private void upload() {
		// Verification des champs
		if(this.cv_path.getText().toString().isEmpty()) {
			AlertDialog.Builder builder = new AlertDialog.Builder(this);
			builder.setTitle("Warning");
			builder.setMessage("Please choose a CV");
			builder.create().show();
			return;
		}
		
		// Appel du web service POST
		Async_upload au = new Async_upload(this, Constante.getINIvalue(this, Constante.ini_id), Constante.type_cv);
		au.execute(new String[] { Constante.url + Constante.upload, this.cv_path.getText().toString() });
	}
	
}
