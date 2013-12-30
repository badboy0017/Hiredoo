package com.projet.hiredoo;

import java.io.File;
import java.io.IOException;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.widget.Toast;

import com.utility.hiredoo.ProgressiveEntity;

public class Async_upload extends AsyncTask<String, Void, String> {
	
	private Context context;
	private ProgressDialog pd;
	private Boolean exception = false;
	private String iduser, file_type;
	private String res;
	
	public Async_upload(Context context, String iduser, String file_type) {
		
		this.context = context;
		this.iduser = iduser;
		this.file_type = file_type;
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
			httppost   = new HttpPost(str[0]);
		}
		catch(Exception ex) {
			this.res = "";
			this.res += "Exception HTTP: ";
			this.res += ex.getMessage() + "\n" + ex.toString();
        	this.exception = true;
        	return this.res;
		}
        
        try {
			File file = new File(str[1]);
		    FileBody body = new FileBody(file);
		    
		    MultipartEntityBuilder mbuilder = MultipartEntityBuilder.create();
		    mbuilder.addTextBody("iduser", this.iduser);
		    mbuilder.addPart("file", body);
		    if(this.file_type != null) {
		    	mbuilder.addTextBody("type", this.file_type);
		    }
		    
		    HttpEntity yourEntity = mbuilder.build();
		    ProgressiveEntity myEntity = new ProgressiveEntity(yourEntity);
		    
		    httppost.setEntity(myEntity);
		    HttpResponse response = httpclient.execute(httppost);
		    this.res = EntityUtils.toString(response.getEntity());
		    httpclient.getConnectionManager().shutdown();
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
			
			if(result.equals("ok")) {
				Toast.makeText(this.context, "Upload succeeded", Toast.LENGTH_SHORT).show();
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
	
	// Show the Progress Dialog
	private void showDialog() {
		this.pd.setCancelable(false);
		this.pd.setMessage("Uploading file... Please Wait");
		this.pd.setProgressStyle(ProgressDialog.STYLE_SPINNER);
		this.pd.show();
	}
	
	// Dissmis the Progress Dialog
	private void stopDialog() {
		this.pd.dismiss();
	}

}
