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

public class JobSeeckerRegisterActivity extends Activity implements OnClickListener {
	
	private EditText name, firstname, email, password, repassword;
	private ImageView btn_ok;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.jobseekerregister_view);
		
		// Recupération des views
		name       = (EditText)findViewById(R.id.jobseekerregister_name);
		firstname  = (EditText)findViewById(R.id.jobseekerregister_firstname);
		email      = (EditText)findViewById(R.id.jobseekerregister_email);
		password   = (EditText)findViewById(R.id.jobseekerregister_password);
		repassword = (EditText)findViewById(R.id.jobseekerregister_repassword);
		
		btn_ok = (ImageView)findViewById(R.id.jobseekerregister_btnOk);
		btn_ok.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		// Test si les champs sont vides
		if(this.name.getText().toString().isEmpty() || this.firstname.getText().toString().isEmpty() || this.email.getText().toString().isEmpty() || this.password.getText().toString().isEmpty() || this.repassword.getText().toString().isEmpty()) {
			AlertDialog.Builder builder = new AlertDialog.Builder(this);
			builder.setTitle("Warning");
			builder.setMessage("Please fill all the fields");
			builder.create().show();
			return;
		}
		
		// Verification des espaces
		if(this.name.getText().toString().contains(" ") || this.firstname.getText().toString().contains(" ") || this.email.getText().toString().contains(" ") || this.password.getText().toString().contains(" ") || this.repassword.getText().toString().contains(" ")) {
			AlertDialog.Builder builder = new AlertDialog.Builder(this);
			builder.setTitle("Warning");
			builder.setMessage("Character space is not allowed");
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
			obj.put("name", this.name.getText().toString());
			obj.put("lastname", this.firstname.getText().toString());
	        obj.put("email", this.email.getText().toString());
	        obj.put("password", this.password.getText().toString());
		}
		catch (JSONException ex) {
			AlertDialog.Builder builder = new AlertDialog.Builder(this);
			builder.setTitle("JSON Exception");
			builder.setMessage("Cause:\n" + ex.getCause() + "\n\nMessage:\n" + ex.getMessage());
			builder.create().show();
		}
		            
		// Appel du web service POST
		Async_post ap = new Async_post(this, obj, null);
		ap.execute(new String[] { Constante.url + Constante.user });
	}

}
