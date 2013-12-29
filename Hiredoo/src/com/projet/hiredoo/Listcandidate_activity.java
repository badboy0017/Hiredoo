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

public class Listcandidate_activity extends Activity implements OnItemClickListener {
	
	private ListView candidate_listview;
	private String json;
	private JSONArray ja;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.listcandidate_view);
		
		// Récuperation des views
		candidate_listview = (ListView)findViewById(R.id.list_candidate);
		candidate_listview.setOnItemClickListener(this);
		
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
        	Toast.makeText(this, "No result found", Toast.LENGTH_LONG).show();
        	this.finish();
        }
		
		// Affichage des elements dans la liste
		ArrayList<HashMap<String, String>> candidateItem = new ArrayList<HashMap<String, String>>();
        HashMap<String, String> candidate_hashmap;
        
        try {
        	for(int i=0 ; i<this.ja.length() ; i++) {
	        	candidate_hashmap = new HashMap<String, String>();
	        	candidate_hashmap.put("text1", this.ja.getJSONObject(i).getString("name") + " " +this.ja.getJSONObject(i).getString("lastname"));
	        	candidate_hashmap.put("text2", this.ja.getJSONObject(i).getString("city"));
		        candidateItem.add(candidate_hashmap);
	        }
        }
        catch(JSONException ex) {
        	AlertDialog.Builder builder = new AlertDialog.Builder(this);
			builder.setTitle("JSONException");
			builder.setMessage("Cause: " + ex.getCause() + "\n\nMessage: " + ex.getMessage());
			builder.create().show();
			return;
        }
		
        ListAdapter adapter = new SimpleAdapter(this, candidateItem, android.R.layout.simple_list_item_2, new String[] {"text1", "text2"}, new int[] {android.R.id.text1, android.R.id.text2 });
        this.candidate_listview.setAdapter(adapter);
	}
	
	@Override
    public void onBackPressed() {
        finish();
    }
	
	@Override
	public void onItemClick(AdapterView<?> adapter, View v, int position, long id) {
		// Appel du web service
		try {
			Async_get ag = new Async_get(this, Profilcandidate_activity.class);
			ag.execute(new String[] { Constante.url + Constante.user_getUserProfile + this.ja.getJSONObject(position).getString("id") });
		}
		catch(JSONException ex) {
			Toast.makeText(this, "JSONException.\n" + ex.getMessage(), Toast.LENGTH_LONG).show();
		}
	}
	
}
