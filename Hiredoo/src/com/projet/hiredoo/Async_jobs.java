package com.projet.hiredoo;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

public class Async_jobs extends AsyncTask<String, Void, String> {
	
	private Context context;
	private JSONObject jobj; // Objet JSON en entrée
	private JSONArray ja; // Objet JSON retourné par le serveur
	private String type;
	private ProgressDialog pd;
	private Boolean exception = false;
	private String res;
	private ListView joblist;
	
	public Async_jobs(Context context, String type, ListView joblist, JSONObject jobj) {
		
		this.context = context;
		this.type = type;
		this.res = "";
		this.joblist = joblist;
		this.jobj = jobj;
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
		// Test du type de la requete HTTP
		if(this.type.equals(Constante.http_get)) {
			return this.traitement_get(str[0]);
		}
		else if(this.type.equals(Constante.http_post)) {
			return this.traitement_post(str[0]);
		}
		else {
			this.res = "";
        	this.res += "Internal error\nRequest type unknowen";
        	this.exception = true;
        	return this.res;
		}
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
			
			// Formatage du resultat
			try {
				this.ja = new JSONArray(result);
			}
			catch (JSONException je) {
				Toast.makeText(this.context, "Server Exception. No result returned", Toast.LENGTH_LONG).show();
				AlertDialog.Builder builder = new AlertDialog.Builder(this.context);
				builder.setTitle("Echec Operation");
				builder.setMessage(result);
				builder.create().show();
				return;
			}
			
			ArrayList<HashMap<String, String>> jobItem = new ArrayList<HashMap<String, String>>();
	        HashMap<String, String> job_hashmap;
	        
	        try {
	        	for(int i=0 ; i<this.ja.length() ; i++) {
		        	job_hashmap = new HashMap<String, String>();
					job_hashmap.put("text1", this.ja.getJSONObject(i).getString("title"));
			        job_hashmap.put("text2", this.ja.getJSONObject(i).getString("type"));
			        jobItem.add(job_hashmap);
		        }
	        }
	        catch(JSONException ex) {
	        	AlertDialog.Builder builder = new AlertDialog.Builder(this.context);
				builder.setTitle("JSONException");
				builder.setMessage("Cause: " + ex.getCause() + "\n\nMessage: " + ex.getMessage());
				builder.create().show();
				return;
	        }
			
	        ListAdapter adapter = new SimpleAdapter(this.context, jobItem, android.R.layout.simple_list_item_2, new String[] {"text1", "text2"}, new int[] {android.R.id.text1, android.R.id.text2 });
	        this.joblist.setAdapter(adapter);
	        
	        // Message d'avertissement si la liste est vide
	        if(this.ja.length() == 0) {
	        	Toast.makeText(this.context, "There is no job in this section", Toast.LENGTH_LONG).show();
	        }
	        
	        // Passage du resultat (pour le rendre global)
	        Constante.ja = this.ja;
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
	
	private String traitement_get(String url) {
		// Envoie de la demande du web service
		HttpClient httpclient = null;
		HttpGet httpget = null;
		
		try {
			httpclient = new DefaultHttpClient();
			httpget = new HttpGet(url);
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
	
	private String traitement_post(String url) {
		// Envoie de la demande du web service
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
        	return this.res;
		}
		
		try {
            httppost.setHeader("content-type", "application/json; charset=UTF-8");
            httppost.setEntity(new StringEntity(this.jobj.toString(), "UTF-8"));
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
		
		return this.res;
	}
	
}
