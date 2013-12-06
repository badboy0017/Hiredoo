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
		
		this.pd = new ProgressDialog(this);
		
		vv = (VideoView)findViewById(R.id.video_player);
		MediaController mc = new MediaController(this);
		mc.setAnchorView(vv);
		Uri video = Uri.parse("http://stagemt.orgfree.com/assets/a.mp4");
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
