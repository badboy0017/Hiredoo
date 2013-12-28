package com.projet.hiredoo;

import java.util.ArrayList;
import java.util.HashMap;

import org.json.JSONArray;
import org.json.JSONException;

import android.app.Activity;
import android.app.AlertDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

public class Listpostule_activity extends Activity implements OnItemClickListener {
	
	private ListView postule_list;
	private String json;
	private JSONArray ja;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.listpostule_view);
		
		// Récuperation des views
		postule_list = (ListView)findViewById(R.id.list_postule);
		postule_list.setOnItemClickListener(this);
		
		// Recuperation des donnees
		this.json = getIntent().getExtras().getString("data");
		
		// Formatage du resultat
		try {
			this.ja = new JSONArray(this.json);
		}
		catch (JSONException je) {
			Toast.makeText(this, "Impossible de formater les données", Toast.LENGTH_LONG).show();
			return;
		}
		
        // Message d'avertissement si la liste est vide
        if(this.ja.length() == 0) {
        	Toast.makeText(this, "No body has applied on this job yet", Toast.LENGTH_LONG).show();
        	return;
        }
		
		// Affichage des elements dans la liste
		ArrayList<HashMap<String, String>> postuleItem = new ArrayList<HashMap<String, String>>();
        HashMap<String, String> postule_hashmap;
        
        try {
        	for(int i=0 ; i<this.ja.length() ; i++) {
	        	postule_hashmap = new HashMap<String, String>();
	        	postule_hashmap.put("text1", Constante.transformDate(this.ja.getJSONObject(i).getString("datePostule")));
	        	postule_hashmap.put("text2", "");
		        postuleItem.add(postule_hashmap);
	        }
        }
        catch(JSONException ex) {
        	AlertDialog.Builder builder = new AlertDialog.Builder(this);
			builder.setTitle("JSONException");
			builder.setMessage("Cause: " + ex.getCause() + "\n\nMessage: " + ex.getMessage());
			builder.create().show();
			return;
        }
		
        ListAdapter adapter = new SimpleAdapter(this, postuleItem, android.R.layout.simple_list_item_2, new String[] {"text1", "text2"}, new int[] {android.R.id.text1, android.R.id.text2 });
        this.postule_list.setAdapter(adapter);
	}
	
	@Override
    public void onBackPressed() {
        finish();
    }
	
	@Override
	public void onItemClick(AdapterView<?> adapter, View v, int position, long id) {
		// Recuperation de l'ID
		String id_pos;
		try {
        	id_pos = this.ja.getJSONObject(position).getString("id");
        }
        catch(JSONException ex) {
        	AlertDialog.Builder builder = new AlertDialog.Builder(this);
			builder.setTitle("JSONException");
			builder.setMessage("Cause: " + ex.getCause() + "\n\nMessage: " + ex.getMessage());
			builder.create().show();
			return;
        }
		
		// Appel du web service
		Async_get ag = new Async_get(this, Detailpostule_activity.class);
		ag.execute(new String[] { Constante.url + Constante.postule_getPostuleDetail + id_pos });
	}
	
}
