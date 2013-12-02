package com.projet.hiredoo;

import android.app.Activity;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.widget.MediaController;
import android.widget.VideoView;

public class Profil_activity extends Activity {
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.profilcandidate_view);
		
		VideoView vv = (VideoView)findViewById(R.id.profil_video);
		MediaController mc = new MediaController(this);
		mc.setAnchorView(vv);
		Uri video = Uri.parse("http://stagemt.orgfree.com/assets/a.mp4");
		vv.setVideoURI(video);
		vv.setMediaController(mc);
		vv.requestFocus();
		vv.start();
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}
