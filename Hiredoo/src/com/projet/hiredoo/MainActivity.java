package com.projet.hiredoo;

import java.io.File;

import android.app.Activity;
import android.os.Bundle;
import android.text.Html;
import android.view.KeyEvent;
import android.view.Menu;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		// Test du 1er lancement de l'application
		File ini = new File(getFileStreamPath(Constante.file_ini).toString());
		if(!ini.exists()) {
			setContentView(R.layout.activity_main);
			Constante.createINIFile(this);
			
			String summary = "<html><font color=\"#000000\" style=\"italique\"><p align="+ "\"" +"left" + "\""+ ">" +  "You are Looking for a job ? <br /> You are hiring a condidate ? <br /> Good you are using the best way !! <br /> HireGo is a mobile application that you allow to post a cv , a video that you describe. it is also a way that allow employee <br /> to finds a good condidate. <br /> with HireDo you will'nt use jornal to search a job , Get Ready for the Hiring !!!" +"</p>"+"</font></html>";
			TextView t = (TextView) findViewById(R.id.paragraph);
			t.setText(Html.fromHtml(summary));
			
			return;
		}
		
		// Test si le user doit entrer son login et son password
		if(Constante.getINIvalue(this, Constante.ini_remember).equals("false")) {
			setContentView(R.layout.login);
			return;
		}
		
		// Sinon
		setContentView(R.layout.main);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	
	@Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if(keyCode == KeyEvent.KEYCODE_MENU ) {
        	Constante.deleteINIFile(this);
        	Toast.makeText(this, "File deleted", Toast.LENGTH_SHORT).show();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

}
