package com.projet.hiredoo;

import java.util.ArrayList;
import java.util.HashMap;

import org.json.JSONException;
import org.json.JSONObject;

import android.R.drawable;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.ActivityNotFoundException;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;

public class Profilenterprise_activity extends Activity implements OnClickListener, OnItemClickListener {
	
	private TextView profile_name, profile_about, profile_address, profile_web, profile_tel, profile_mail;
	private SlidingMenu slidingMenu;
	private ListView menu_listview;
	private String json;
	private JSONObject jo;
	private String [] list_tel;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.profilenterprise_view);
		
		// Initialisation
		list_tel = new String [2];
		list_tel[0] = "";
		list_tel[1] = "";
		
		// Recuperation des views
		profile_name    = (TextView)findViewById(R.id.profilenterprise_name);
		profile_about   = (TextView)findViewById(R.id.profilenterprise_about);
		profile_address = (TextView)findViewById(R.id.profilenterprise_address);
		profile_mail    = (TextView)findViewById(R.id.profilenterprise_email);
		profile_tel     = (TextView)findViewById(R.id.profilenterprise_tel);
		profile_web     = (TextView)findViewById(R.id.profilenterprise_web);
		
		profile_address.setOnClickListener(this);
		profile_mail.setOnClickListener(this);
		profile_tel.setOnClickListener(this);
		profile_web.setOnClickListener(this);
		
		// Recuperation des donnees
		this.json = getIntent().getExtras().getString("data");
		
		// Formatage du resultat
		try {
			this.jo = new JSONObject(this.json);
		}
		catch (JSONException je) {
			Toast.makeText(this, "Profilenterprise\nImpossible de formater les données", Toast.LENGTH_LONG).show();
			return;
		}
		
		// Affichage des données
		this.remplirProfil();
		
		// Instantiation du sliding menu
		slidingMenu = new SlidingMenu(this);
		
		// Test qui consulte le profil
		String id;
		try {
			id = this.jo.getString("id");
		}
		catch(JSONException ex) {
			AlertDialog.Builder builder = new AlertDialog.Builder(this);
			builder.setTitle("OnCreate JSONException");
			builder.setMessage("Cause: " + ex.getCause() + "\n\nMessage: " + ex.getMessage());
			builder.create().show();
			return;
		}
		
		if(!Constante.getINIvalue(this, Constante.ini_id).equals(id))
			return; // Pour ne pas afficher le sliding menu pour un consulteur de profil
		
		// Ajout du Sliding Menu
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
        
        // Ajout des elements à la liste
        hash_map = new HashMap<String, String>();
    	hash_map.put("titre", "Add a job");
    	hash_map.put("description", "Click to add a job");
    	hash_map.put("img", String.valueOf(drawable.ic_input_add));
        listItem.add(hash_map);
        
        hash_map = new HashMap<String, String>();
    	hash_map.put("titre", "Search");
    	hash_map.put("description", "Click to search candidate or enterprise");
    	hash_map.put("img", String.valueOf(drawable.ic_search_category_default));
        listItem.add(hash_map);
        
        hash_map = new HashMap<String, String>();
    	hash_map.put("titre", "Modify profile");
    	hash_map.put("description", "Click to modify the profile");
    	hash_map.put("img", String.valueOf(R.drawable.profil));
        listItem.add(hash_map);
        
        hash_map = new HashMap<String, String>();
    	hash_map.put("titre", "Disconnect");
    	hash_map.put("description", "Click to disconnect");
    	hash_map.put("img", String.valueOf(R.drawable.disconnect));
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
		switch(position) {
		case 0: // Add job
			// Appel de l'activité
			Intent ajouter_intent = new Intent(this, Ajouteroffre_activity.class);
			try {
				startActivity(ajouter_intent);
			}
			catch(ActivityNotFoundException ex) {
				Toast.makeText(this, "Activity introuvable.\n" + ex.getMessage(), Toast.LENGTH_LONG).show();
			}
			
			slidingMenu.toggle();
			break;
			
		case 1: // Search
			// Appel de l'activité
			Intent rech_intent = new Intent(this, Rechercher_activity.class);
			try {
				startActivity(rech_intent);
			}
			catch(ActivityNotFoundException ex) {
				Toast.makeText(this, "Activity introuvable.\n" + ex.getMessage(), Toast.LENGTH_LONG).show();
			}
			
			slidingMenu.toggle();
			break;
			
		case 2: // Modifier profil
			// Appel de l'activité
			Intent mod_intent = new Intent(this, Modifier_entreprise_activity.class);
			try {
				startActivity(mod_intent);
				finish();
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
	public void onClick(View v) {
		switch(v.getId()) {
		case R.id.profilenterprise_address:
			Intent map_intent = new Intent(this, Map_activity.class);
			try {
				map_intent.putExtra("address", this.jo.has("address") ? this.jo.getString("address") : "");
				startActivity(map_intent);
			}
			catch(ActivityNotFoundException ex) {
				Toast.makeText(this, ex.getMessage(), Toast.LENGTH_LONG).show();
			}
			catch (JSONException ex) {
				AlertDialog.Builder builder = new AlertDialog.Builder(this);
				builder.setTitle("JSONException - No address found");
				builder.setMessage("Cause: " + ex.getCause() + "\n\nMessage: " + ex.getMessage());
				builder.create().show();
			}
			break;
			
		case R.id.profilenterprise_email:
			Intent email_intent = new Intent(Intent.ACTION_SENDTO, Uri.parse("mailto:" + this.profile_mail.getText().toString()));
			try {
				startActivity(email_intent);
			}
			catch (ActivityNotFoundException ex) {
				Toast.makeText(this, ex.getMessage(), Toast.LENGTH_LONG).show();
			}
			break;
			
		case R.id.profilenterprise_tel:
			AlertDialog.Builder bui = new AlertDialog.Builder(this);
			bui.setTitle("Choisir numéro à appeler");
			bui.setItems(this.list_tel, new DialogInterface.OnClickListener() {
				@Override
				public void onClick(DialogInterface dialog, int which) {
					Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + list_tel[which]));
					try {
						startActivity(intent);
					}
					catch(ActivityNotFoundException ex) {
						Toast.makeText(Profilenterprise_activity.this, ex.getMessage(), Toast.LENGTH_LONG).show();
					}
				}
			});
			bui.create().show();
			break;
			
		case R.id.profilenterprise_web:
			Intent web_intent = new Intent(Intent.ACTION_VIEW, Uri.parse(this.profile_web.getText().toString()));
			try {
				startActivity(web_intent);
			}
			catch(ActivityNotFoundException ex) {
				//Toast.makeText(this, ex.getMessage(), Toast.LENGTH_LONG).show();
				Toast.makeText(this, "Incorrect web address", Toast.LENGTH_LONG).show();
			}
			break;
			
		default:
		}
	}
	
	private void remplirProfil() {
		String name, about, address, mail, tel, web;
		
		try {
			// Name
			name = this.jo.getString("name") + "\n" + (this.jo.has("activity") ? this.jo.getString("activity") : "");
			
			// About me
			about = "";
			about += (this.jo.has("resume") ? this.jo.getString("resume") : "No resume");
			about += (this.jo.has("nbemplyee") ? "\n\nEmplyees number: " + this.jo.getString("nbemplyee") : "");
			
			// Contact
			address = "";
			address += (this.jo.has("address") ? this.jo.getString("address") : "No Address");
			address += " - ";
			address += (this.jo.has("city") ? this.jo.getString("city") : "No City");
			
			mail = "";
			mail += (this.jo.has("email") ? this.jo.getString("email") : "No Email");
			
			web = "";
			web += (this.jo.has("website") ? this.jo.getString("website") : "No Web site");
			
			tel = "";
			tel += (this.jo.has("tel") ? this.jo.getString("tel") : "No phone number");
			tel += "\n";
			tel += (this.jo.has("fax") ? this.jo.getString("fax") : "No fax number");
			
			this.list_tel[0] = (this.jo.has("tel") ? this.jo.getString("tel") : "");
			this.list_tel[1] = (this.jo.has("fax") ? this.jo.getString("fax") : "");
		}
		catch(JSONException ex) {
			AlertDialog.Builder builder = new AlertDialog.Builder(this);
			builder.setTitle("JSONException");
			builder.setMessage("Cause: " + ex.getCause() + "\n\nMessage: " + ex.getMessage());
			builder.create().show();
			return;
		}
		
		// Remplir le profil
		this.profile_name.setText(name);
		this.profile_about.setText(about);
		this.profile_address.setText(address);
		this.profile_mail.setText(mail);
		this.profile_web.setText(web);
		this.profile_tel.setText(tel);
	}
	
}