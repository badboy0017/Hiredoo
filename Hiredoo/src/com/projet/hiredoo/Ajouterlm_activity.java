package com.projet.hiredoo;

import java.io.File;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class Ajouterlm_activity extends Activity implements OnClickListener, android.content.DialogInterface.OnClickListener {
	
	private EditText lm_name;
	private TextView lm_path;
	private Button lm_browse, lm_ajouter;
	private File current_path;
	private File[] listFiles;
    private String[] files;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.ajouterlm_view);
		
		// Récuperation des views
		lm_name    = (EditText)findViewById(R.id.ajouterlm_name);
		lm_path    = (TextView)findViewById(R.id.ajouterlm_path);
		lm_browse  = (Button)findViewById(R.id.ajouterlm_btnbrowse);
		lm_ajouter = (Button)findViewById(R.id.ajouterlm_btnajouter);
		
		lm_browse.setOnClickListener(this);
		lm_ajouter.setOnClickListener(this);
	}
	
	@Override
	public void onSaveInstanceState(Bundle outState) {
	    super.onSaveInstanceState(outState);
	    outState.putString("lm_path", this.lm_path.getText().toString());
	}
	
	@Override
	public void onRestoreInstanceState(Bundle savedInstanceState) {
	    super.onRestoreInstanceState(savedInstanceState);
	    
	    if(savedInstanceState != null && savedInstanceState.containsKey("lm_path"))
	    	this.lm_path.setText(savedInstanceState.getString("lm_path"));
	}

	@Override
	public void onClick(View v) {
		switch(v.getId()) {
		case R.id.ajouterlm_btnbrowse:
			Constante.path = Constante.path_static;
			this.browsing();
			break;
			
			
		case R.id.ajouterlm_btnajouter:
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
			this.lm_path.setText(this.current_path.getPath());
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
					Constante.path = Ajouterlm_activity.this.current_path.getParent();
					Ajouterlm_activity.this.browsing();
				}
			});
		}
		browse.create().show();
	}
	
	private void upload() {
		Toast.makeText(this, "En cours...", Toast.LENGTH_SHORT).show();
	}

}
