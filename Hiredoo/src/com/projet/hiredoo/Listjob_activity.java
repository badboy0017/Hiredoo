package com.projet.hiredoo;

import java.util.ArrayList;
import java.util.HashMap;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;

public class Listjob_activity extends Activity implements OnItemClickListener {
	
	private SlidingMenu slidingMenu;
	private ListView job_listview, menu_listview;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.liste_job_view);
		
		//Liste des jobs
		job_listview = (ListView)findViewById(R.id.list_job);
		ArrayList<HashMap<String, String>> jobItem = new ArrayList<HashMap<String, String>>();
        HashMap<String, String> job_hashmap;
		
        job_hashmap = new HashMap<String, String>();
		job_hashmap.put("text1", "Job Application 1");
        job_hashmap.put("text2", "Click to view");
        jobItem.add(job_hashmap);
        
        job_hashmap = new HashMap<String, String>();
		job_hashmap.put("text1", "Job Application 2");
        job_hashmap.put("text2", "Click to view");
        jobItem.add(job_hashmap);
        
        job_hashmap = new HashMap<String, String>();
		job_hashmap.put("text1", "Job Application 3");
        job_hashmap.put("text2", "Click to view");
        jobItem.add(job_hashmap);
        
        job_hashmap = new HashMap<String, String>();
		job_hashmap.put("text1", "Job Application 4");
        job_hashmap.put("text2", "Click to view");
        jobItem.add(job_hashmap);
        
        job_hashmap = new HashMap<String, String>();
		job_hashmap.put("text1", "Job Application 5");
        job_hashmap.put("text2", "Click to view");
        jobItem.add(job_hashmap);
        
        ListAdapter adapter = new SimpleAdapter(this, jobItem, android.R.layout.simple_list_item_2, new String[] {"text1", "text2"}, new int[] {android.R.id.text1, android.R.id.text2 });
	    job_listview.setAdapter(adapter);
	    job_listview.setOnItemClickListener(this);
		
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
	        	final CharSequence[] items = {"Computer Science", "Mecanique", "Others"};
	        	AlertDialog.Builder builder = new AlertDialog.Builder(this);
	        	builder.setTitle("Select a job type");
	        	builder.setItems(items, null);
	        	/*builder.setItems(items, new DialogInterface.OnClickListener() {
	        	    public void onClick(DialogInterface dialog, int item) {
	        	        Toast.makeText(getApplicationContext(), items[item], Toast.LENGTH_SHORT).show();
	        	    }
	        	});*/
	        	builder.create().show();
	            return true;
	            
	        case R.id.listjobmenu_search:
	        	Intent rechercher_intent = new Intent(this, Rechercherjob_activity.class);
	        	try {
	        		startActivity(rechercher_intent);
	        	}
	        	catch(ActivityNotFoundException ex) {
	        		Toast.makeText(this, "Activity introuvable.\n" + ex.getMessage(), Toast.LENGTH_LONG).show();
	        	}
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
		
		/*AlertDialog.Builder builder = new AlertDialog.Builder(this);
    	builder.setTitle(v.getId() + " // " + R.id.slidingmenu_list);
    	builder.setMessage(adapter.getId() + " // " + R.id.list_job);
    	builder.create().show();*/
    	
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
		Intent detail_intent = new Intent(this, Detailjob_activity.class);
		try {
			//detail_intent.putExtra("id", this.ja.getJSONObject(position).getString("id"));
			startActivity(detail_intent);
		}
		catch(ActivityNotFoundException ex) {
			Toast.makeText(this, "Activity introuvable.\n" + ex.getMessage(), Toast.LENGTH_LONG).show();
		}
	}
	
	// Fonction de traitement des clicks sur la liste du slide menu
	private void traitement_slidingmenu_list(int position) {
		switch(position) {
		case 0: // mon profil
			Intent profil_intent = new Intent(this, Profilcandidate_activity.class);
			try {
				startActivity(profil_intent);
			}
			catch(ActivityNotFoundException ex) {
				Toast.makeText(this, "Activity introuvable.\n" + ex.getMessage(), Toast.LENGTH_LONG).show();
			}
			slidingMenu.toggle();
			break;
			
		case 1: // rechercher job
			Intent job_intent = new Intent(this, Rechercherjob_activity.class);
			try {
				startActivity(job_intent);
			}
			catch(ActivityNotFoundException ex) {
				Toast.makeText(this, "Activity introuvable.\n" + ex.getMessage(), Toast.LENGTH_LONG).show();
			}
			slidingMenu.toggle();
			break;
			
		case 2: // rechercher profil
			Intent rechprofil_intent = new Intent(this, Rechercherprofil_activity.class);
			try {
				startActivity(rechprofil_intent);
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
				Constante.saveINIFile(this, "login", "password"); // Pour mettre remember à false
				startActivity(login_intent);
				finish(); // Pour terminer cette activité
			}
			catch(ActivityNotFoundException ex) {
				Toast.makeText(this, "Activity introuvable.\n" + ex.getMessage(), Toast.LENGTH_LONG).show();
			}
			break;
		}
		
	}
	
}
