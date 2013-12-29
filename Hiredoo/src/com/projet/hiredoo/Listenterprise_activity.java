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

public class Listenterprise_activity extends Activity implements OnItemClickListener {
	
	private ListView entreprise_list;
	private String json;
	private JSONArray ja;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.listenterprise_view);
		
		// Récuperation des views
		entreprise_list = (ListView)findViewById(R.id.list_enterprise);
		entreprise_list.setOnItemClickListener(this);
		
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
		ArrayList<HashMap<String, String>> entrepriseItem = new ArrayList<HashMap<String, String>>();
        HashMap<String, String> entreprise_hashmap;
        
        try {
        	for(int i=0 ; i<this.ja.length() ; i++) {
        		entreprise_hashmap = new HashMap<String, String>();
        		entreprise_hashmap.put("text1", this.ja.getJSONObject(i).getString("name"));
        		entreprise_hashmap.put("text2", this.ja.getJSONObject(i).getString("city"));
        		entrepriseItem.add(entreprise_hashmap);
	        }
        }
        catch(JSONException ex) {
        	AlertDialog.Builder builder = new AlertDialog.Builder(this);
			builder.setTitle("JSONException");
			builder.setMessage("Cause: " + ex.getCause() + "\n\nMessage: " + ex.getMessage());
			builder.create().show();
			return;
        }
		
        ListAdapter adapter = new SimpleAdapter(this, entrepriseItem, android.R.layout.simple_list_item_2, new String[] {"text1", "text2"}, new int[] {android.R.id.text1, android.R.id.text2 });
        this.entreprise_list.setAdapter(adapter);
	}
	
	@Override
    public void onBackPressed() {
        finish();
    }
	
	@Override
	public void onItemClick(AdapterView<?> adapter, View v, int position, long id) {
		// Appel du web service
		try {
			Async_get ag = new Async_get(this, Profilenterprise_activity.class);
			ag.execute(new String[] { Constante.url + Constante.enterprise + this.ja.getJSONObject(position).getString("id") });
		}
		catch(JSONException ex) {
			Toast.makeText(this, "JSONException.\n" + ex.getMessage(), Toast.LENGTH_LONG).show();
		}
	}
	
}
