package com.projet.hiredoo;

import java.io.IOException;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.AlertDialog;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.widget.Toast;

import com.projet.hiredoo.R.drawable;

public class Async_notif extends AsyncTask<String, Void, String> {
	
	private Context context;
	private ProgressDialog pd;
	private Boolean exception = false;
	private String res;
	private String type;
	private JSONObject jo;
	
	public Async_notif(Context context, String type) {
		
		this.context = context;
		this.type = type;
		this.res = "";
		this.jo = null;
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
        	this.res += "IOException:\n";
        	this.res += ex.getMessage() + "\n" + ex.toString();
        	this.exception = true;
        }
        catch (Exception ex) {
        	this.res = "";
        	this.res += "Exception 2:\n";
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
			
			/*AlertDialog.Builder builder = new AlertDialog.Builder(this.context);
			builder.setTitle("Data");
			builder.setMessage(this.res);
			builder.create().show();*/
			
			
			// Formatage du resultat
			try {
				jo = new JSONObject(this.res);
			}
			catch (JSONException je) {
				AlertDialog.Builder builder = new AlertDialog.Builder(this.context);
				builder.setTitle("Server Exception");
				builder.setMessage("Impossible de formater les données.\n\n" + this.res);
				builder.create().show();
				return;
			}
			
			// Traitement
			if(jo.length() != 0) {
				if(this.type.equals(Constante.ini_type_jobseeker)) {
					this.traitement_jobseeker();
				}
				else if(this.type.equals(Constante.ini_type_recruiter)) {
					this.traitement_recruteur();
				}
				else {
					Toast.makeText(this.context, "User type is incorrect", Toast.LENGTH_SHORT).show();
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
	
	// Fonction de traitement du jobseeker
	@SuppressWarnings("deprecation")
	private void traitement_jobseeker() {
		// Preparation des données
		String msg;
		try {
			msg  = this.jo.getJSONObject("user").get("code").toString();
		}
		catch(JSONException ex) {
			AlertDialog.Builder builder = new AlertDialog.Builder(this.context);
			builder.setTitle("JSON Exception");
			builder.setMessage("Data received does not contains 'user' object");
			builder.create().show();
			return;
		}
		
		Intent async_intent = new Intent(this.context, Dialog_activity.class);
		async_intent.putExtra("msg", msg);
				
		// Notification
		NotificationManager not = (NotificationManager)this.context.getSystemService(Context.NOTIFICATION_SERVICE);
		Notification notif = new Notification();
		notif.icon = drawable.ic_launcher;
		notif.tickerText = "You have a notification";
		notif.number = 1;
		notif.flags = Notification.FLAG_AUTO_CANCEL;
		notif.setLatestEventInfo(this.context, msg, "Click to open", PendingIntent.getActivity(this.context, 0, async_intent, 0));
		not.notify(1, notif);
	}
	
	// Fonction de traitement du recruteur
	@SuppressWarnings("deprecation")
	private void traitement_recruteur() {
		// Preparation des données
		String data, msg;
		try {
			data = this.jo.getJSONArray("postules").toString();
			msg  = this.jo.getJSONObject("entreprise").get("code").toString();
		}
		catch(JSONException ex) {
			AlertDialog.Builder builder = new AlertDialog.Builder(this.context);
			builder.setTitle("JSON Exception");
			builder.setMessage("Data received does not contains 'postules' array");
			builder.create().show();
			return;
		}
		
		Intent async_intent = new Intent(this.context, Listpostule_activity.class);
		async_intent.putExtra("data", data);
		
		// Notification
		NotificationManager not = (NotificationManager)this.context.getSystemService(Context.NOTIFICATION_SERVICE);
		Notification notif = new Notification();
		notif.icon = drawable.ic_launcher;
		notif.tickerText = "You have a notification";
		notif.number = 1;
		notif.flags = Notification.FLAG_AUTO_CANCEL;
		notif.setLatestEventInfo(this.context, msg, "Click to open", PendingIntent.getActivity(this.context, 0, async_intent, 0));
		not.notify(1, notif);
	}

}
