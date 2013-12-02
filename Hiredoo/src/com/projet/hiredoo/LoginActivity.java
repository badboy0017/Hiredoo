package com.projet.hiredoo;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;
import android.widget.TextView;

public class LoginActivity extends Activity{

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.login);
		/*
		 * Programmation des bouttons et des liens
		 * 
		 * */
		//Boutton ok
		ImageButton btttnOk = (ImageButton) findViewById(R.id.btnOkAuth);
		btttnOk.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					Intent intent = new Intent(LoginActivity.this, AccueilActivity.class);
					startActivity(intent);
					
					

				}
			});
		

		TextView lienRecruter = (TextView) findViewById(R.id.lienRegisterRecruter);
		lienRecruter.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					Intent intent = new Intent(LoginActivity.this, RegisterRecruiterActivity.class);
					startActivity(intent);

				}
			});
		TextView lienJobSeecker = (TextView) findViewById(R.id.lienRegisterJobS);
		lienJobSeecker.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					Intent intent = new Intent(LoginActivity.this, RegisterJobSeeckerActivity.class);
					startActivity(intent);

				}
			});

		
	}
	

}
