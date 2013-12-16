package com.projet.hiredoo;

import java.io.IOException;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.widget.Toast;

public class Async_get extends AsyncTask<String, Void, String> {
	
	private Context context;
	private ProgressDialog pd;
	private Boolean exception = false;
	private String res;
	
	public Async_get(Context context) {
		
		this.context = context;
		this.res = "";
		this.pd = new ProgressDialog(this.context);
	}
	
	@Override
	protected void onPreExecute() {
		// Test de la connexion internet
		if(!Constante.isInternetAvailable(this.context)) {
			Toast.makeText(this.context, "Internet connection not available", Toast.LENGTH_LONG).show();
			cancel(true); // Pour arreter le asyncTask et ne pas executer doInBackground
			return; // Pour arreter la methode et ne pas executer ce qui suit
		}
		
		this.showDialog();
	}
	
	@Override
	protected String doInBackground(String... str) {
		// Envoie de la demande du web service
		HttpClient httpclient = null;
		HttpGet httpget = null;
		
		try {
			httpclient = new DefaultHttpClient();
			httpget = new HttpGet(str[0]);
		}
		catch(Exception ex) {
			this.res = "";
			this.res += "Exception HTTP: ";
			this.res += ex.getMessage() + "\n" + ex.toString();
        	this.exception = true;
        	return this.res;
		}
        
        try {
           HttpResponse response = httpclient.execute(httpget);
           this.res = EntityUtils.toString(response.getEntity());
        }
        catch (IOException ex) {
        	this.res = "";
        	this.res += "IOException: ";
        	this.res += ex.getMessage() + "\n" + ex.toString();
        	this.exception = true;
        }
        catch (Exception ex) {
        	this.res = "";
        	this.res += "Exception 2";
        	this.res += ex.getMessage() + "\n" + ex.toString();
        	this.exception = true;
        }
        
        return this.res;
	}
	
	@Override
	protected void onPostExecute(String result) {
		// Verification et affichage de l'exception
		if(this.exception) {
			// Stop the dialog
			this.stopDialog();
						
			AlertDialog.Builder builder = new AlertDialog.Builder(this.context);
			builder.setTitle("Echec Operation");
			builder.setMessage(result);
			builder.create().show();
			return;
		}
		else {
			// Stop the dialog
			this.stopDialog();
		}
	}
	
	// Show the Progress Dialog
	private void showDialog() {
		this.pd.setCancelable(false);
		this.pd.setMessage("Loading... Please Wait");
		this.pd.setProgressStyle(ProgressDialog.STYLE_SPINNER);
		this.pd.show();
	}
	
	// Dissmis the Progress Dialog
	private void stopDialog() {
		this.pd.dismiss();
	}

}
