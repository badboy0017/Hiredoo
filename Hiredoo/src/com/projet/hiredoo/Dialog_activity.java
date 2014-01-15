package com.projet.hiredoo;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.os.Bundle;

public class Dialog_activity extends Activity {
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		// Recuperation des données
		String msg = getIntent().getExtras().getString("msg");
		
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		builder.setTitle("Notification Message");
		builder.setMessage(msg);
		builder.setPositiveButton("OK", new OnClickListener() {
			
			@Override
			public void onClick(DialogInterface arg0, int arg1) {
				finish();
			}
		});
		builder.create().show();
	}

}
