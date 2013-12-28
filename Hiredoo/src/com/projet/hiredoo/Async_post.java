package com.projet.hiredoo;

import java.io.IOException;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.widget.Toast;

public class Async_post extends AsyncTask<String, Void, String> {
	
	private Context context;
	private Object next_activity;
	private JSONObject jobj; //Object en entrée
	private JSONObject json; //Objet renvoyé par le serveur
	private ProgressDialog pd;
	private Boolean exception = false;
	private String res;
	private String user_type = null;
	
	public Async_post(Context context, JSONObject jobj, Object next_activity) {
		
		this.context = context;
		this.next_activity = next_activity;
		this.jobj = jobj;
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
		HttpPost httppost = null;
		
		try {
			httpclient = new DefaultHttpClient();
			httppost = new HttpPost(str[0]);
		}
		catch(Exception ex) {
			this.res = "";
			this.res += "Exception HTTP: ";
			this.res += ex.getMessage() + "\n" + ex.toString();
        	this.exception = true;
        	return this.res;
		}
        
        try {
            if(this.jobj != null) {
            	httppost.setEntity(new StringEntity(this.jobj.toString(), "UTF-8"));
            }
            
            httppost.setHeader("content-type", "application/json; charset=UTF-8");
            HttpResponse response = httpclient.execute(httppost);
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
        
        // Test de la classe appelante
        if(this.context.getClass().equals(MainActivity.class)) {
        	this.traitement_login(str[1]);
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
			
			// Appel de l'activité suivante
			if(this.context.getClass().equals(MainActivity.class)) {
				// Test du resultat envoyé par le serveur
				try {
					this.json = new JSONObject(result);
				}
				catch(JSONException ex) {
					AlertDialog.Builder builder = new AlertDialog.Builder(this.context);
					builder.setTitle("JSON Exception");
					builder.setMessage(ex.getCause() + "\n\n" + ex.getMessage());
					builder.create().show();
					return;
				}
				
				try {
					Constante.saveINIFile(this.context, this.json.getString("email"), this.json.getString("password"), true, this.user_type, this.json.getInt("id"));
				}
				catch (JSONException ex) {
					AlertDialog.Builder builder = new AlertDialog.Builder(this.context);
					builder.setTitle("JSON getting property Exception");
					builder.setMessage(ex.getCause() + "\n\n" + ex.getMessage());
					builder.create().show();
					return;
				}
				
				// Appel de l'activité suivante
				Intent job_intent = new Intent(this.context, Listjob_activity.class);
				try {
					this.context.startActivity(job_intent);
				}
				catch(ActivityNotFoundException anfe) {
					Toast.makeText(this.context, "Activity introuvable.\n" + anfe.getMessage(), Toast.LENGTH_LONG).show();
					return;
				}
			}
			else if(this.next_activity == null) { //Activité suivante null dans le cas d'inscription, on va nulle part
				
				if(result.equals("ok")) {
					Toast.makeText(this.context, "Operation succeeded", Toast.LENGTH_SHORT).show();
				}
				else {
					AlertDialog.Builder builder = new AlertDialog.Builder(this.context);
					builder.setTitle("Server Exception");
					builder.setMessage(result);
					builder.create().show();
					return;
				}
			}
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
	
	// Fonction de traitement spécifique à l'operation de login
	private void traitement_login(String url) {
		// Formatage du resultat
		try {
			new JSONObject(this.res);
			this.user_type = Constante.ini_type_jobseeker;
			return;
		}
		catch (JSONException je) {
			HttpClient httpclient = null;
			HttpPost httppost = null;
			
			try {
				httpclient = new DefaultHttpClient();
				httppost = new HttpPost(url);
			}
			catch(Exception ex) {
				this.res = "";
				this.res += "Exception HTTP: ";
				this.res += ex.getMessage() + "\n" + ex.toString();
	        	this.exception = true;
	        	return;
			}
	        
	        try {
	            if(this.jobj != null) {
	            	httppost.setEntity(new StringEntity(this.jobj.toString(), "UTF-8"));
	            }
	            
	            httppost.setHeader("content-type", "application/json; charset=UTF-8");
	            HttpResponse response = httpclient.execute(httppost);
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
	        
	        this.user_type = Constante.ini_type_recruiter;
	        return;
		}
	}

}
