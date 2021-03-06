package com.projet.hiredoo;

import java.util.ArrayList;
import java.util.HashMap;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.KeyEvent;
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
	
	private TextView profil_name, profil_aboutme, profil_experience, profil_education, profil_langage, profil_address, profil_web, profil_mail, profil_tel, profil_videocall;
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
		profil_aboutme    = (TextView)findViewById(R.id.profil_aboutme);
		profil_experience = (TextView)findViewById(R.id.profil_experience);
		profil_education  = (TextView)findViewById(R.id.profil_education);
		profil_langage    = (TextView)findViewById(R.id.profil_langue);
		profil_address    = (TextView)findViewById(R.id.profil_address);
		profil_web        = (TextView)findViewById(R.id.profil_web);
		profil_mail       = (TextView)findViewById(R.id.profil_email);
		profil_tel        = (TextView)findViewById(R.id.profil_tel);
		profil_videocall  = (TextView)findViewById(R.id.profil_videocall);
		
		profil_address.setOnClickListener(this);
		profil_web.setOnClickListener(this);
		profil_mail.setOnClickListener(this);
		profil_tel.setOnClickListener(this);
		
		video_link = (ImageView)findViewById(R.id.profil_videolink);
		video_link.setOnClickListener(this);
		
		// Recuperation des donnees
		this.json = getIntent().getExtras().getString("data");
		
		// Formatage du resultat
		try {
			this.jo = new JSONObject(this.json);
		}
		catch (JSONException je) {
			Toast.makeText(this, "Impossible de formater les donn�es", Toast.LENGTH_LONG).show();
			return;
		}
		
		// Affichage des donn�es
		this.remplirProfil();
		
		// Instantiation du sliding menu
		slidingMenu = new SlidingMenu(this);
		
		// Test qui consulte le profil
		String id;
		try {
			id = this.jo.getJSONObject("user").getString("id");
		}
		catch(JSONException ex) {
			AlertDialog.Builder builder = new AlertDialog.Builder(this);
			builder.setTitle("OnCreate JSONException");
			builder.setMessage("Cause: " + ex.getCause() + "\n\nMessage: " + ex.getMessage());
			builder.create().show();
			return;
		}
		
		// Affichage / masquage de video call
		if(Constante.getINIvalue(this, Constante.ini_id).equals(id)){
			this.profil_videocall.setVisibility(View.GONE);
		}
		else {
			this.profil_videocall.setOnClickListener(this);
			return; // Pour ne pas afficher le sliding menu pour un consulteur de profil
		}
		
		// Ajout du Sliding Menu
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
        
        // Ajout des elements � la liste
    	hash_map = new HashMap<String, String>();
    	hash_map.put("titre", "Add CV");
    	hash_map.put("description", "Click to Add a CV");
    	hash_map.put("img", String.valueOf(R.drawable.cv));
        listItem.add(hash_map);
        
        hash_map = new HashMap<String, String>();
    	hash_map.put("titre", "Add LM");
    	hash_map.put("description", "Click to add a cover letter");
    	hash_map.put("img", String.valueOf(R.drawable.cover_letter));
        listItem.add(hash_map);
        
        hash_map = new HashMap<String, String>();
    	hash_map.put("titre", "Add video");
    	hash_map.put("description", "Click to add video");
    	hash_map.put("img", String.valueOf(R.drawable.video));
        listItem.add(hash_map);
        
        hash_map = new HashMap<String, String>();
    	hash_map.put("titre", "Profile video");
    	hash_map.put("description", "Click to choose video");
    	hash_map.put("img", String.valueOf(R.drawable.video));
        listItem.add(hash_map);
        
        hash_map = new HashMap<String, String>();
    	hash_map.put("titre", "My experiences");
    	hash_map.put("description", "Click to add experience");
    	hash_map.put("img", String.valueOf(R.drawable.experience));
        listItem.add(hash_map);
        
        hash_map = new HashMap<String, String>();
    	hash_map.put("titre", "My education");
    	hash_map.put("description", "Click to add education");
    	hash_map.put("img", String.valueOf(R.drawable.education));
        listItem.add(hash_map);
        
        hash_map = new HashMap<String, String>();
    	hash_map.put("titre", "My languages");
    	hash_map.put("description", "Click to add a language");
    	hash_map.put("img", String.valueOf(R.drawable.language));
        listItem.add(hash_map);
        
        hash_map = new HashMap<String, String>();
    	hash_map.put("titre", "Edit profile");
    	hash_map.put("description", "Click to edit profile");
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
    public boolean onKeyDown(int keyCode, KeyEvent event) {
		//Quand on clique sur le bouton physique MENU, on ouvre le menu
        if(keyCode == KeyEvent.KEYCODE_MENU ) {
            this.slidingMenu.toggle();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

	@Override
	public void onItemClick(AdapterView<?> adapter, View v, int position, long id) {
		switch(position) {
		case 0: // Add CV
			// Appel de l'activit�
			Intent cv_intent = new Intent(this, Ajoutercv_activity.class);
			try {
				startActivity(cv_intent);
			}
			catch(ActivityNotFoundException ex) {
				Toast.makeText(this, "Activity introuvable.\n" + ex.getMessage(), Toast.LENGTH_LONG).show();
			}
			
			slidingMenu.toggle();
			break;
			
		case 1: // Add LM
			// Appel de l'activit�
			Intent lm_intent = new Intent(this, Ajouterlm_activity.class);
			try {
				startActivity(lm_intent);
			}
			catch(ActivityNotFoundException ex) {
				Toast.makeText(this, "Activity introuvable.\n" + ex.getMessage(), Toast.LENGTH_LONG).show();
			}
			
			slidingMenu.toggle();
			break;
			
		case 2: // Add video
			// Appel de l'activit�
			Intent video_intent = new Intent(this, Ajoutervideo_activity.class);
			try {
				startActivity(video_intent);
			}
			catch(ActivityNotFoundException ex) {
				Toast.makeText(this, "Activity introuvable.\n" + ex.getMessage(), Toast.LENGTH_LONG).show();
			}
			
			slidingMenu.toggle();
			break;
			
		case 3: // Choose video
			// Appel du web service GET
			Async_get ag = new Async_get(this, Changervideo_activity.class);
			ag.execute(new String[] { Constante.url + Constante.video_getMyVideos + Constante.getINIvalue(this, Constante.ini_id) });
			
			slidingMenu.toggle();
			break;
			
		case 4: // My experiences
			// Appel de l'activit�
			Intent exp_intent = new Intent(this, Ajouter_experience_activity.class);
			try {
				startActivity(exp_intent);
				finish();
			}
			catch(ActivityNotFoundException ex) {
				Toast.makeText(this, "Activity introuvable.\n" + ex.getMessage(), Toast.LENGTH_LONG).show();
			}
			
			slidingMenu.toggle();
			break;
			
		case 5: // My education
			// Appel de l'activit�
			Intent edu_intent = new Intent(this, Ajouter_education_activity.class);
			try {
				startActivity(edu_intent);
			}
			catch(ActivityNotFoundException ex) {
				Toast.makeText(this, "Activity introuvable.\n" + ex.getMessage(), Toast.LENGTH_LONG).show();
			}
			
			slidingMenu.toggle();
			break;
			
		case 6: // My languages
			// Appel de l'activit�
			Intent lan_intent = new Intent(this, Ajouter_langage_activity.class);
			try {
				startActivity(lan_intent);
				finish();
			}
			catch(ActivityNotFoundException ex) {
				Toast.makeText(this, "Activity introuvable.\n" + ex.getMessage(), Toast.LENGTH_LONG).show();
			}
			
			slidingMenu.toggle();
			break;
			
		case 7: // Edit profile
			// Appel de l'activit�
			Intent con_intent = new Intent(this, Modifier_jobseeker_activity.class);
			try {
				startActivity(con_intent);
				finish();
			}
			catch(ActivityNotFoundException ex) {
				Toast.makeText(this, "Activity introuvable.\n" + ex.getMessage(), Toast.LENGTH_LONG).show();
			}
			
			slidingMenu.toggle();
			break;
			
		case 8: // Disconnect
			Intent login_intent = new Intent(this, MainActivity.class);
			try {
				slidingMenu.toggle();
				Constante.saveINIFile(this, "null", "null", false, "null", 0); // Pour mettre remember � false
				startActivity(login_intent);
				finish(); // Pour terminer cette activit�
			}
			catch(ActivityNotFoundException ex) {
				Toast.makeText(this, "Activity introuvable.\n" + ex.getMessage(), Toast.LENGTH_LONG).show();
			}
			
			slidingMenu.toggle();
			break;
		}
	}

	@Override
	public void onClick(View v) {
		switch(v.getId()) {
		
		case R.id.profil_videolink:
			// Test de la connexion internet
			if(!Constante.isInternetAvailable(this)) {
				Toast.makeText(this, "Internet connection not available", Toast.LENGTH_LONG).show();
				return;
			}
			
			String video_name = this.getVideoPrincipaleName();
			if(video_name == null) return;
			
			Intent video_intent = new Intent(this, Video_activity.class);
			try {
				video_intent.putExtra("type", Constante.video_streaming);
				video_intent.putExtra("name", video_name);
				startActivity(video_intent);
			}
			catch(ActivityNotFoundException ex) {
				Toast.makeText(this, "Activity introuvable.\n" + ex.getMessage(), Toast.LENGTH_LONG).show();
			}
			break;
			
		case R.id.profil_address:
			Intent map_intent = new Intent(this, Map_activity.class);
			try {
				map_intent.putExtra("address", this.jo.getJSONObject("user").has("address") ? this.jo.getJSONObject("user").getString("address") : "");
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
			
		case R.id.profil_email:
			Intent email_intent = new Intent(Intent.ACTION_SENDTO, Uri.parse("mailto:" + this.profil_mail.getText().toString()));
			try {
				startActivity(email_intent);
			}
			catch (ActivityNotFoundException ex) {
				Toast.makeText(this, ex.getMessage(), Toast.LENGTH_LONG).show();
			}
			break;
			
		case R.id.profil_web:
			Intent web_intent = new Intent(Intent.ACTION_VIEW, Uri.parse(this.profil_web.getText().toString()));
			try {
				startActivity(web_intent);
			}
			catch(ActivityNotFoundException ex) {
				//Toast.makeText(this, ex.getMessage(), Toast.LENGTH_LONG).show();
				Toast.makeText(this, "Incorrect web address", Toast.LENGTH_LONG).show();
			}
			break;
			
		case R.id.profil_tel:
			Intent tel_intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + this.profil_tel.getText().toString()));
			try {
				startActivity(tel_intent);
			}
			catch(ActivityNotFoundException ex) {
				Toast.makeText(this, ex.getMessage(), Toast.LENGTH_LONG).show();
			}
			break;
			
		case R.id.profil_videocall:
			// Test de la connexion internet
			if(!Constante.isInternetAvailable(this)) {
				Toast.makeText(this, "Internet connection not available", Toast.LENGTH_LONG).show();
				return;
			}
			
			Intent live_intent = new Intent(this, Video_activity.class);
			try {
				live_intent.putExtra("type", Constante.video_live);
				live_intent.putExtra("name", "");
				startActivity(live_intent);
			}
			catch(ActivityNotFoundException ex) {
				Toast.makeText(this, "Activity introuvable.\n" + ex.getMessage(), Toast.LENGTH_LONG).show();
			}
			break;
			
		default:
			Toast.makeText(this, "View not found", Toast.LENGTH_LONG).show();
			return;
		}
	}
	
	private void remplirProfil() {
		String name, aboutme, experience, education, language, address, mail, tel ,web;;
		
		try {
			// Name
			name = this.jo.getJSONObject("user").getString("name") + " " + this.jo.getJSONObject("user").getString("lastname") + "\n" + (this.jo.getJSONObject("user").has("titleprofile") ? this.jo.getJSONObject("user").getString("titleprofile") : "");

			// About me
			aboutme = "";
			aboutme += (this.jo.getJSONObject("user").has("resume") ? this.jo.getJSONObject("user").getString("resume") : "No resume");
			aboutme += (this.jo.getJSONObject("user").has("socialsituation") ? "\n\nSocial Situation: " + this.jo.getJSONObject("user").getString("socialsituation") : "");
			
			// Experience
			if(this.jo.getJSONArray("experience").length() != 0) {
				JSONArray ja = this.jo.getJSONArray("experience");
				experience = "";
				for(int i=0 ; i<ja.length() ; i++) {
					experience += (i == 0) ? "" : "\n\n";
					experience += ja.getJSONObject(i).getString("title") + "\n";
					experience += "Enterprise: " + ja.getJSONObject(i).getString("entreprise") + " at " + ja.getJSONObject(i).getString("location") + "\n";
					experience += "Date from: " + Constante.transformDate(ja.getJSONObject(i).getString("dateFrom")) + "\n";
					experience += "Date to: " + Constante.transformDate(ja.getJSONObject(i).getString("dateTo")) + "\n";
					experience += (ja.getJSONObject(i).has("link") ? "Link: " + ja.getJSONObject(i).getString("link") : "") + "\n";
					experience += ja.getJSONObject(i).getString("description");
				}
			}
			else {
				experience = "No experience";
			}
			
			// Education
			if(this.jo.getJSONArray("education").length() != 0) {
				JSONArray ja = this.jo.getJSONArray("education");
				education = "";
				for(int i=0 ; i<ja.length() ; i++) {
					education += (i == 0) ? "" : "\n\n";
					education += ja.getJSONObject(i).getString("title") + "\n";
					education += "University: " + ja.getJSONObject(i).getString("university") + " at " + ja.getJSONObject(i).getString("location") + "\n";
					education += "Date from: " + Constante.transformDate(ja.getJSONObject(i).getString("dateFrom")) + "\n";
					education += "Date to: " + Constante.transformDate(ja.getJSONObject(i).getString("dateTo")) + "\n";
					education += ja.getJSONObject(i).getString("deploma");
				}
			}
			else {
				education = "No education";
			}
			
			// Language
			if(this.jo.getJSONArray("language").length() != 0) {
				JSONArray ja = this.jo.getJSONArray("language");
				language = "";
				for(int i=0 ; i<ja.length() ; i++) {
					language += (i == 0) ? "" : ", ";
					language += ja.getJSONObject(i).getString("language");
				}
			}
			else {
				language = "No languages";
			}
			
			// Contact
			address = "";
			address += (this.jo.getJSONObject("user").has("address") ? this.jo.getJSONObject("user").getString("address") : "No Address");
			address += " - ";
			address += (this.jo.getJSONObject("user").has("city") ? this.jo.getJSONObject("user").getString("city") : "No City");
			
			mail = "";
			mail += (this.jo.getJSONObject("user").has("email") ? this.jo.getJSONObject("user").getString("email") : "No Email");
			
			web = "";
			web += (this.jo.getJSONObject("user").has("website") ? this.jo.getJSONObject("user").getString("website") : "No Web site");
			
			tel = "";
			tel += (this.jo.getJSONObject("user").has("tel") ? this.jo.getJSONObject("user").getString("tel") : "No phone number");
		}
		catch(JSONException ex) {
			AlertDialog.Builder builder = new AlertDialog.Builder(this);
			builder.setTitle("JSONException");
			builder.setMessage("Cause: " + ex.getCause() + "\n\nMessage: " + ex.getMessage());
			builder.create().show();
			return;
		}
		
		// Remplir le profil
		this.profil_name.setText(name);
		this.profil_aboutme.setText(aboutme);
		this.profil_experience.setText(experience);
		this.profil_education.setText(education);
		this.profil_langage.setText(language);
		this.profil_address.setText(address);
		this.profil_mail.setText(mail);
		this.profil_tel.setText(tel);
		this.profil_web.setText(web);
	}
	
	private String getVideoPrincipaleName() {
		// R�cuperation du nom de la video
		String video_principale_id;
		JSONArray video_list;
		try {
			video_principale_id = this.jo.getJSONObject("user").has("videoPrincipalId") ? this.jo.getJSONObject("user").getString("videoPrincipalId") : "0";
			video_list = this.jo.getJSONArray("video");
			
			if("0".equals(video_principale_id)) {
				Toast.makeText(this, "This candidate doesn't specify a video yet", Toast.LENGTH_LONG).show();
				return null;
			}
			
			for(int i=0 ; i<video_list.length() ; i++) {
				if(video_list.getJSONObject(i).get("id").toString().equals(video_principale_id)) {
					return video_list.getJSONObject(i).get("name").toString();
				}
			}
			
			Toast.makeText(this, "Video not found", Toast.LENGTH_LONG).show();
			return null;
		}
		catch(JSONException ex) {
			AlertDialog.Builder builder = new AlertDialog.Builder(this);
			builder.setTitle("JSONException");
			builder.setMessage("Cause: " + ex.getCause() + "\n\nMessage: " + ex.getMessage());
			builder.create().show();
			return null;
		}
	}

}
