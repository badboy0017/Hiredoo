package com.projet.hiredoo;

import android.app.Activity;
import android.app.ProgressDialog;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;
import android.media.MediaPlayer.OnPreparedListener;
import android.net.Uri;
import android.os.Bundle;
import android.widget.MediaController;
import android.widget.VideoView;

public class Video_activity extends Activity implements OnCompletionListener, OnPreparedListener {
	
	private ProgressDialog pd;
	private VideoView vv;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.video_view);
		
		//String SrcPath ="rtsp://197.8.99.239:1935/hiredo/mp4:aaa.mp4";//marche
		//String SrcPath ="http://197.8.99.239:1935/hiredo/mp4:aaa.mp4/playlist.m3u8";//marche pas
		String SrcPath = "rtsp://197.9.1.26/videochat/testing";
		
		// Code Ala
		/*vv = (VideoView)findViewById(R.id.video_player);
		vv.setVideoURI(Uri.parse(SrcPath));
		vv.setMediaController(new MediaController(this));
	    vv.requestFocus();
	    vv.start();*/
		
		this.pd = new ProgressDialog(this);
		
		vv = (VideoView)findViewById(R.id.video_player);
		MediaController mc = new MediaController(this);
		mc.setAnchorView(vv);
		//Uri video = Uri.parse("http://stagemt.orgfree.com/assets/a.mp4");
		Uri video = Uri.parse(SrcPath);
		vv.setVideoURI(video);
		vv.setMediaController(mc);
		vv.requestFocus();
		
		vv.setOnPreparedListener(this);
		vv.setOnCompletionListener(this);

		this.showDialog();
	}
	
	@Override
	public void onPrepared(MediaPlayer mp) {
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
		this.pd.setMessage("Chargement... Attendez SVP");
		this.pd.setProgressStyle(ProgressDialog.STYLE_SPINNER);
		this.pd.show();
	}
	
	// Dissmis the Progress Dialog
	private void stopDialog() {
		this.pd.dismiss();
	}

}
