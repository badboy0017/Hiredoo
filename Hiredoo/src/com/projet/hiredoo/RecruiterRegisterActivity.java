package com.projet.hiredoo;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.AlertDialog;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.ImageView;

public class RecruiterRegisterActivity extends Activity implements OnClickListener {
	
	private EditText enterprise_name, activity_sector, email, password, repassword;
	private ImageView btn_ok;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.recruiterregister_view);
		
		enterprise_name = (EditText)findViewById(R.id.recruiter_register_enterprise_name);
		activity_sector = (EditText)findViewById(R.id.recruiter_register_activity_sector);
		email           = (EditText)findViewById(R.id.recruiter_register_email);
		password        = (EditText)findViewById(R.id.recruiter_register_password);
		repassword      = (EditText)findViewById(R.id.recruiter_register_retypepassword);
		
		btn_ok = (ImageView)findViewById(R.id.recruiter_register_btnOk);
		btn_ok.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		// Test si les champs sont vides
		if(this.enterprise_name.getText().toString().isEmpty() || this.activity_sector.getText().toString().isEmpty() || this.email.getText().toString().isEmpty() || this.password.getText().toString().isEmpty() || this.repassword.getText().toString().isEmpty()) {
			AlertDialog.Builder builder = new AlertDialog.Builder(this);
			builder.setTitle("Warning");
			builder.setMessage("Please fill all the fields");
			builder.create().show();
			return;
		}
				
		// Verification des espaces
		if(this.enterprise_name.getText().toString().contains(" ") || this.activity_sector.getText().toString().contains(" ") || this.email.getText().toString().contains(" ") || this.password.getText().toString().contains(" ") || this.repassword.getText().toString().contains(" ")) {
			AlertDialog.Builder builder = new AlertDialog.Builder(this);
			builder.setTitle("Warning");
			builder.setMessage("Caracter space is not allowed");
			builder.create().show();
			return;
		}
				
		// Verification de l'email
		if(!this.email.getText().toString().matches("[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+")) {
			AlertDialog.Builder builder = new AlertDialog.Builder(this);
			builder.setTitle("Warning");
			builder.setMessage("Invlid email address");
			builder.create().show();
			return;
		}
		
		// Verification de la longueur du mor de passe
		if(this.password.getText().toString().length() < 4) {
			AlertDialog.Builder builder = new AlertDialog.Builder(this);
			builder.setTitle("Warning");
			builder.setMessage("Password must have at least 4 characters");
			builder.create().show();
			
			this.password.setText("");
			this.repassword.setText("");
			return;
		}
				
		// Verification du mot de passe
		if(!this.password.getText().toString().equals(this.repassword.getText().toString())) {
			AlertDialog.Builder builder = new AlertDialog.Builder(this);
			builder.setTitle("Warning");
			builder.setMessage("Incorrect password.\nPlease Retype");
			builder.create().show();
			
			this.password.setText("");
			this.repassword.setText("");
			return;
		}
				
		// Préparation de l'objet JSON
		JSONObject obj = new JSONObject();
		try {
			obj.put("name", this.enterprise_name.getText().toString());
			obj.put("activity", this.activity_sector.getText().toString());
	        obj.put("email", this.email.getText().toString());
	        obj.put("password", this.password.getText().toString());
		}
		catch (JSONException ex) {
			AlertDialog.Builder builder = new AlertDialog.Builder(this);
			builder.setTitle("JSON Exception");
			builder.setMessage("Cause:\n" + ex.getCause() + "\n\nMessage:\n" + ex.getMessage());
			builder.create().show();
			return;
		}
				            
		// Appel du web service POST
		Async_post ap = new Async_post(this, obj, null);
		ap.execute(new String[] { Constante.url + Constante.enterprise });
	}

}
