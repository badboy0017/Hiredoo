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

public class Ajoutervideo_activity extends Activity implements OnClickListener, android.content.DialogInterface.OnClickListener {
	
	private EditText video_name;
	private TextView video_path;
	private Button video_browse, video_ajouter;
	private File current_path;
	private File[] listFiles;
    private String[] files;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.ajoutervideo_view);
		
		// Récuperation des views
		video_name    = (EditText)findViewById(R.id.ajoutervideo_name);
		video_path    = (TextView)findViewById(R.id.ajoutervideo_path);
		video_browse  = (Button)findViewById(R.id.ajoutervideo_btnbrowse);
		video_ajouter = (Button)findViewById(R.id.ajoutervideo_btnajouter);
		
		video_browse.setOnClickListener(this);
		video_ajouter.setOnClickListener(this);
	}
	
	@Override
	public void onSaveInstanceState(Bundle outState) {
	    super.onSaveInstanceState(outState);
	    outState.putString("video_path", this.video_path.getText().toString());
	}
	
	@Override
	public void onRestoreInstanceState(Bundle savedInstanceState) {
	    super.onRestoreInstanceState(savedInstanceState);
	    
	    if(savedInstanceState != null && savedInstanceState.containsKey("video_path"))
	    	this.video_path.setText(savedInstanceState.getString("video_path"));
	}

	@Override
	public void onClick(View v) {
		switch(v.getId()) {
		case R.id.ajoutervideo_btnbrowse:
			Constante.path = Constante.path_static;
			this.browsing();
			break;
			
			
		case R.id.ajoutervideo_btnajouter:
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
			this.video_path.setText(this.current_path.getPath());
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
					Constante.path = Ajoutervideo_activity.this.current_path.getParent();
					Ajoutervideo_activity.this.browsing();
				}
			});
		}
		browse.create().show();
	}
	
	private void upload() {
		Toast.makeText(this, "En cours...", Toast.LENGTH_SHORT).show();
	}

}
