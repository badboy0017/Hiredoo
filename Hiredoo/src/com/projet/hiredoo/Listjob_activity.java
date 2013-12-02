package com.projet.hiredoo;

import java.util.ArrayList;
import java.util.HashMap;

import android.app.Activity;
import android.content.ActivityNotFoundException;
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

public class Listjob_activity extends Activity implements OnItemClickListener {
	
	private SlidingMenu slidingMenu;
	private ListView job_listview, menu_listview;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.liste_job_view);
		
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
        
        // itemList
        menu_listview = (ListView)findViewById(R.id.slidingmenu_list);
        ArrayList<HashMap<String, String>> listItem = new ArrayList<HashMap<String, String>>();
        HashMap<String, String> hash_map;
        
        // Ajout des elements à la liste
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
	            return true;
	            
	        case R.id.listjobmenu_search:
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
		switch(position) {
		case 0: // mon profil
			Intent profil_intent = new Intent(this, Profil_activity.class);
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
			
		case 2: // rechercher profl
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
				Constante.createINIFile(this); // Pour initialiser le fichier
				startActivity(login_intent);
				finish(); // Pour terminer cette activité
			}
			catch(ActivityNotFoundException ex) {
				Toast.makeText(this, "Activity introuvable.\n" + ex.getMessage(), Toast.LENGTH_LONG).show();
			}
			slidingMenu.toggle();
			break;
		}
	}
	
}
