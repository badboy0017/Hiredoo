package com.projet.hiredoo;

import java.util.ArrayList;
import java.util.HashMap;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.ActivityNotFoundException;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;

public class Listjob_activity extends Activity implements OnItemClickListener, OnClickListener {
	
	private SlidingMenu slidingMenu;
	private ListView job_listview, menu_listview;
	private Object next_activity;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.listjob_view);
		
		// Test de la connexion internet
		if(!Constante.isInternetAvailable(this)) {
			AlertDialog.Builder buider = new AlertDialog.Builder(this);
			buider.setTitle("Warning");
			buider.setMessage("Internet connection not available");
			buider.setCancelable(false);
			buider.setPositiveButton("Reload", this);
			buider.setNegativeButton("Exit", this);
			buider.create().show();
			return;
		}
		
		// Liste des jobs
		job_listview = (ListView)findViewById(R.id.list_job);
	    job_listview.setOnItemClickListener(this);
		
		// Appel du web service
		Async_jobs aj = new Async_jobs(this, Constante.http_get, this.job_listview, null);
		aj.execute(new String[] { Constante.url + Constante.getAllJobs });
		
		// Ajout du Sliding Menu
        slidingMenu = new SlidingMenu(this);
        slidingMenu.setMode(SlidingMenu.LEFT);
        slidingMenu.setTouchModeAbove(SlidingMenu.TOUCHMODE_FULLSCREEN);
        slidingMenu.setShadowWidthRes(R.dimen.slidingmenu_shadow_width);
        slidingMenu.setShadowDrawable(R.drawable.slidingmenu_shadow);
        slidingMenu.setBehindOffsetRes(R.dimen.slidingmenu_offset);
        slidingMenu.setFadeDegree(0.5f);
        slidingMenu.attachToActivity(this, SlidingMenu.SLIDING_CONTENT);
        slidingMenu.setMenu(R.layout.slidingmenu);
        
        // itemList du sliding menu
        menu_listview = (ListView)findViewById(R.id.slidingmenu_list);
        ArrayList<HashMap<String, String>> listItem = new ArrayList<HashMap<String, String>>();
        HashMap<String, String> hash_map;
        
        // Ajout des elements à la liste de sliding menu
    	hash_map = new HashMap<String, String>();
    	hash_map.put("titre", "My Profile");
    	hash_map.put("description", "Click to edit profile");
    	hash_map.put("img", String.valueOf(R.drawable.ic_launcher));
        listItem.add(hash_map);
        
        hash_map = new HashMap<String, String>();
    	hash_map.put("titre", "Search jobs");
    	hash_map.put("description", "Click search jobs");
    	hash_map.put("img", String.valueOf(R.drawable.ic_launcher));
        listItem.add(hash_map);
        
        hash_map = new HashMap<String, String>();
    	hash_map.put("titre", "Search Profile");
    	hash_map.put("description", "Search profile or enterprise");
    	hash_map.put("img", String.valueOf(R.drawable.ic_launcher));
        listItem.add(hash_map);
        
        hash_map = new HashMap<String, String>();
    	hash_map.put("titre", "Disconnect");
    	hash_map.put("description", "Click to disconnect");
    	hash_map.put("img", String.valueOf(R.drawable.ic_launcher));
        listItem.add(hash_map);
        
        SimpleAdapter mSchedule = new SimpleAdapter (this.getBaseContext(), listItem, R.layout.affichageitem,
                new String[] {"img", "titre", "description"}, new int[] {R.id.img, R.id.titre, R.id.description});
        
        menu_listview.setAdapter(mSchedule);
        menu_listview.setOnItemClickListener(this);
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.listjob_menu, menu);
		return true;
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
	    
	    switch (item.getItemId()) {
	        case R.id.listjobmenu_type:
	        	AlertDialog.Builder builder = new AlertDialog.Builder(this);
	        	builder.setTitle("Select a job type");
	        	builder.setItems(Constante.job_domaine, new DialogInterface.OnClickListener() {
	        	    public void onClick(DialogInterface dialog, int item) {
	        	    	// Preparation de l'objet JSON
	        	    	JSONObject obj = new JSONObject();
	        			try {
	        	            obj.put("domaine", Constante.job_domaine[item]);
	        			}
	        			catch (JSONException ex) {
	        				Toast.makeText(getApplicationContext(), "Erreur JSON\n" + ex.getMessage(), Toast.LENGTH_LONG).show();
	        			}
	        			
	        			// Appel du web service
	        			Async_jobs aj = new Async_jobs(Listjob_activity.this, Constante.http_post, job_listview, obj);
	        			aj.execute(new String[] { Constante.url + Constante.getJobsByDomaine });
	        	    }
	        	});
	        	builder.create().show();
	            return true;
	            
	        case R.id.listjobmenu_search:
	        	Intent rechercher_intent = new Intent(this, Rechercher_activity.class);
	        	try {
	        		startActivity(rechercher_intent);
	        	}
	        	catch(ActivityNotFoundException ex) {
	        		Toast.makeText(this, "Activity introuvable.\n" + ex.getMessage(), Toast.LENGTH_LONG).show();
	        	}
	        	return true;
	        	
	        case R.id.listjobmenu_showAll:
	        	this.recreate();
	        	return true;
	        	
	        default:
	            return super.onOptionsItemSelected(item);
	    }
	}
	
	@Override
    public void onBackPressed() {
        if(slidingMenu.isMenuShowing()) {
            slidingMenu.toggle();
        }
        else {
            finish();
        }
    }
	
	@Override
	public void onItemClick(AdapterView<?> adapter, View v, int position, long id) {
		
		switch(adapter.getId()) {
		case R.id.list_job:
			this.traitement_joblist(position);
			break;
			
		case R.id.slidingmenu_list:
			this.traitement_slidingmenu_list(position);
			break;
			
		default:
			AlertDialog.Builder builder = new AlertDialog.Builder(this);
        	builder.setTitle("Internal Exception");
        	builder.setMessage("Error finding the list");
        	builder.create().show();
			break;
		}
	}
	
	// Fonction de traitement des clicks sur la liste des jobs
	private void traitement_joblist(int position) {
		Intent detail_intent = new Intent(this, Applyjob_activity.class);
		try {
			detail_intent.putExtra("id", Constante.ja.getJSONObject(position).getString("id"));
			detail_intent.putExtra("idEntreprise", Constante.ja.getJSONObject(position).getString("idEntreprise"));
			detail_intent.putExtra("title", Constante.ja.getJSONObject(position).getString("title"));
			detail_intent.putExtra("type", Constante.ja.getJSONObject(position).getString("type"));
			detail_intent.putExtra("description", Constante.ja.getJSONObject(position).getString("description"));
			detail_intent.putExtra("city", Constante.ja.getJSONObject(position).getString("city"));
			detail_intent.putExtra("datecreation", Constante.ja.getJSONObject(position).getString("datecreation"));
			detail_intent.putExtra("domaine", Constante.ja.getJSONObject(position).getString("domaine"));
			
			startActivity(detail_intent);
		}
		catch(JSONException ex) {
			Toast.makeText(this, "JSONException\nCannot convert data: " + ex.getMessage(), Toast.LENGTH_LONG).show();
		}
		catch(ActivityNotFoundException ex) {
			Toast.makeText(this, "Activity introuvable.\n" + ex.getMessage(), Toast.LENGTH_LONG).show();
		}
	}
	
	// Fonction de traitement des clicks sur la liste du slide menu
	private void traitement_slidingmenu_list(int position) {
		switch(position) {
		case 0: // mon profil
			// Actualisation de this.next_activity
			this.next_activity = Profilcandidate_activity.class;
			
			// Appel du web service
			Async_get ag = new Async_get(this, this.next_activity);
			ag.execute(new String[] { Constante.url + Constante.getUserProfile + Constante.getINIvalue(this, Constante.ini_id) });
			
			slidingMenu.toggle();
			break;
			
		case 1: // rechercher job
			Intent job_intent = new Intent(this, Rechercher_activity.class);
			try {
				startActivity(job_intent);
			}
			catch(ActivityNotFoundException ex) {
				Toast.makeText(this, "Activity introuvable.\n" + ex.getMessage(), Toast.LENGTH_LONG).show();
			}
			slidingMenu.toggle();
			break;
			
		case 2: // rechercher profil
			Intent rech_intent = new Intent(this, Rechercher_activity.class);
			try {
				startActivity(rech_intent);
			}
			catch(ActivityNotFoundException ex) {
				Toast.makeText(this, "Activity introuvable.\n" + ex.getMessage(), Toast.LENGTH_LONG).show();
			}
			slidingMenu.toggle();
			break;
			
		case 3: // Deconnexion
			Intent login_intent = new Intent(this, MainActivity.class);
			try {
				slidingMenu.toggle();
				Constante.saveINIFile(this, "null", "null", false, "null", 0); // Pour mettre remember à false
				startActivity(login_intent);
				finish(); // Pour terminer cette activité
			}
			catch(ActivityNotFoundException ex) {
				Toast.makeText(this, "Activity introuvable.\n" + ex.getMessage(), Toast.LENGTH_LONG).show();
			}
			break;
		}
	}

	@Override
	public void onClick(DialogInterface arg0, int arg1) {
		if (arg1 == AlertDialog.BUTTON_POSITIVE) {
			recreate();
        }
        else if (arg1 == AlertDialog.BUTTON_NEGATIVE) {
        	finish();
        }
	}
	
}
