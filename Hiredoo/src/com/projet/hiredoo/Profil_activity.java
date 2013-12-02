package com.projet.hiredoo;

import java.util.ArrayList;
import java.util.HashMap;

import android.app.Activity;
import android.app.AlertDialog;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;

public class Profil_activity extends Activity implements OnItemClickListener {
	
	private SlidingMenu slidingMenu;
	private ListView menu_listview;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.profilcandidate_view);
		
		/*VideoView vv = (VideoView)findViewById(R.id.profil_video);
		MediaController mc = new MediaController(this);
		mc.setAnchorView(vv);
		Uri video = Uri.parse("http://stagemt.orgfree.com/assets/a.mp4");
		vv.setVideoURI(video);
		vv.setMediaController(mc);
		vv.requestFocus();
		vv.start();*/
		
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
        
        // Ajout des elements � la liste
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
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
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

}
