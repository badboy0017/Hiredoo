package com.projet.hiredoo;

import java.util.ArrayList;
import java.util.HashMap;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;

public class Profilcandidate_activity extends Activity implements OnClickListener, OnItemClickListener {
	
	private TextView profil_name, profil_experience, profil_education, profil_langage, profil_contact;
	private SlidingMenu slidingMenu;
	private ListView menu_listview;
	private ImageView video_link;
	private String json;
	private JSONObject jo;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.profilcandidate_view);
		
		// Recuperation des views
		profil_name       = (TextView)findViewById(R.id.profil_name);
		profil_experience = (TextView)findViewById(R.id.profil_experience);
		profil_education  = (TextView)findViewById(R.id.profil_education);
		profil_langage    = (TextView)findViewById(R.id.profil_langue);
		profil_contact    = (TextView)findViewById(R.id.profil_contact);
		
		video_link = (ImageView)findViewById(R.id.profil_videolink);
		video_link.setOnClickListener(this);
		
		// Recuperation des donnees
		this.json = getIntent().getExtras().getString("data");
		
		// Formatage du resultat
		try {
			this.jo = new JSONObject(this.json);
		}
		catch (JSONException je) {
			Toast.makeText(this, "Impossible de formater les données", Toast.LENGTH_LONG).show();
			return;
		}
		
		// Affichage des données
		try {
			profil_name.setText(this.jo.getJSONObject("user").getString("name") + "\n" + this.jo.getJSONObject("user").getString("titleprofile"));
			profil_experience.setText(this.jo.getJSONArray("experience").getJSONObject(0).getString("title"));
		}
		catch (JSONException ex) {
			AlertDialog.Builder builder = new AlertDialog.Builder(this);
			builder.setTitle("JSONException");
			builder.setMessage("Cause: " + ex.getCause() + "\n\nMessage: " + ex.getMessage());
			builder.create().show();
		}
		
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
        
        // itemList du Sliding Menu
        menu_listview = (ListView)findViewById(R.id.slidingmenu_list);
        ArrayList<HashMap<String, String>> listItem = new ArrayList<HashMap<String, String>>();
        HashMap<String, String> hash_map;
        
        // Ajout des elements à la liste
    	hash_map = new HashMap<String, String>();
    	hash_map.put("titre", "Add CV");
    	hash_map.put("description", "Click to Add a CV");
    	hash_map.put("img", String.valueOf(R.drawable.ic_launcher));
        listItem.add(hash_map);
        
        hash_map = new HashMap<String, String>();
    	hash_map.put("titre", "Add LM");
    	hash_map.put("description", "Click to add a motivation letter");
    	hash_map.put("img", String.valueOf(R.drawable.ic_launcher));
        listItem.add(hash_map);
        
        hash_map = new HashMap<String, String>();
    	hash_map.put("titre", "Add video");
    	hash_map.put("description", "Click to add video");
    	hash_map.put("img", String.valueOf(R.drawable.ic_launcher));
        listItem.add(hash_map);
        
        hash_map = new HashMap<String, String>();
    	hash_map.put("titre", "My experiences");
    	hash_map.put("description", "Click to add experience");
    	hash_map.put("img", String.valueOf(R.drawable.ic_launcher));
        listItem.add(hash_map);
        
        hash_map = new HashMap<String, String>();
    	hash_map.put("titre", "My education");
    	hash_map.put("description", "Click to add education");
    	hash_map.put("img", String.valueOf(R.drawable.ic_launcher));
        listItem.add(hash_map);
        
        hash_map = new HashMap<String, String>();
    	hash_map.put("titre", "My languages");
    	hash_map.put("description", "Click to add a language");
    	hash_map.put("img", String.valueOf(R.drawable.ic_launcher));
        listItem.add(hash_map);
        
        hash_map = new HashMap<String, String>();
    	hash_map.put("titre", "My contacts");
    	hash_map.put("description", "Click to add contact");
    	hash_map.put("img", String.valueOf(R.drawable.ic_launcher));
        listItem.add(hash_map);
        
        hash_map = new HashMap<String, String>();
    	hash_map.put("titre", "My password");
    	hash_map.put("description", "Click to change password");
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
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		builder.setMessage("En cours de dev.");
		builder.create().show();
		slidingMenu.toggle();
	}

	@Override
	public void onClick(View v) {
		// Test de la connexion internet
		if(!Constante.isInternetAvailable(this)) {
			Toast.makeText(this, "Pas de connexion Internet", Toast.LENGTH_LONG).show();
			return;
		}
		
		Intent video_intent = new Intent(this, Video_activity.class);
		try {
			startActivity(video_intent);
		}
		catch(ActivityNotFoundException ex) {
			Toast.makeText(this, "Activity introuvable.\n" + ex.getMessage(), Toast.LENGTH_LONG).show();
		}
	}

}
