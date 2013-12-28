package com.projet.hiredoo;

import java.util.ArrayList;
import java.util.HashMap;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.AlertDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListAdapter;
import android.widget.SimpleAdapter;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

public class Listcandidate_activity extends Activity implements OnItemClickListener {
	
	private ListView candidate_listview;
	private String json;
	private JSONObject jo;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.listcandidate_view);
		
		// Récuperation des views
		candidate_listview = (ListView)findViewById(R.id.list_candidate);
		candidate_listview.setOnItemClickListener(this);
		
		// Recuperation des donnees
		this.json = getIntent().getExtras().getString("data");
		
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		builder.setTitle("Data");
		builder.setMessage(this.json);
		builder.create().show();
		
		/*
		// Formatage du resultat
		try {
			this.jo = new JSONObject(this.json);
		}
		catch (JSONException je) {
			Toast.makeText(this, "Impossible de formater les données", Toast.LENGTH_LONG).show();
			return;
		}
		
		// Affichage des elements dans la liste
		ArrayList<HashMap<String, String>> candidateItem = new ArrayList<HashMap<String, String>>();
        HashMap<String, String> candidate_hashmap;
        
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
		*/
	}
	
	@Override
    public void onBackPressed() {
        finish();
    }
	
	@Override
	public void onItemClick(AdapterView<?> adapter, View v, int position, long id) {
		
	}
	
}
