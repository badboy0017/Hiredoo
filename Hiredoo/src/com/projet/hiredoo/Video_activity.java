package com.projet.hiredoo;

import android.app.Activity;
import android.app.ProgressDialog;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;
import android.media.MediaPlayer.OnPreparedListener;
import android.net.Uri;
import android.os.Bundle;
import android.widget.MediaController;
import android.widget.Toast;
import android.widget.VideoView;

public class Video_activity extends Activity implements OnCompletionListener, OnPreparedListener {
	
	private ProgressDialog pd;
	private VideoView vv;
	private String srcPath;;
	private String type, video_name;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.video_view);
		
		// Recuperation des donnees
		try {
			this.type = getIntent().getExtras().getString("type");
			this.video_name = getIntent().getExtras().getString("name");
		}
		catch(Exception ex) {
			Toast.makeText(this, "Error finding data", Toast.LENGTH_LONG).show();
			finish();
		}
		
		//String SrcPath ="rtsp://197.8.192.247:1935/hiredo/mp4:aaa.mp4";//marche
		//String SrcPath ="http://197.8.99.239:1935/hiredo/mp4:aaa.mp4/playlist.m3u8";//marche pas
		//String SrcPath ="rtsp://197.8.119.26:1935/live/work";
		
		if(this.type.equals(Constante.video_streaming)) {
			//srcPath = Constante.php_prertsp + Constante.url_php + Constante.php_streaming + "driftvideo.mp4";
			this.srcPath = Constante.php_prertsp + Constante.url_php + Constante.php_streaming + this.video_name;
		}
		else if(this.type.equals(Constante.video_live)) {
			this.srcPath = Constante.php_prertsp + Constante.url_php + Constante.php_live;
		}
		else {
			Toast.makeText(this, "Error in video type", Toast.LENGTH_LONG).show();
			finish();
		}
		
		this.pd = new ProgressDialog(this);
		
		vv = (VideoView)findViewById(R.id.video_player);
		MediaController mc = new MediaController(this);
		mc.setAnchorView(vv);
		Uri video = Uri.parse(this.srcPath);
		vv.setVideoURI(video);
		vv.setMediaController(mc);
		vv.requestFocus();
		
		vv.setOnPreparedListener(this);
		vv.setOnCompletionListener(this);

		this.showDialog();
	}
	
	@Override
	public void onPrepared(MediaPlayer player) {
		this.stopDialog();
		vv.start();
	}

	@Override
	public void onCompletion(MediaPlayer player) {
		finish();
	}
	
	// Show the Progress Dialog
	private void showDialog() {
		
		this.pd.setCancelable(false);
		this.pd.setMessage("Loading... Please wait");
		this.pd.setProgressStyle(ProgressDialog.STYLE_SPINNER);
		this.pd.show();
	}
	
	// Dissmis the Progress Dialog
	private void stopDialog() {
		this.pd.dismiss();
	}

}
