package com.projet.hiredoo;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;

public class Listcandidate_activity extends Activity implements OnItemClickListener {
	
	//private ListView candidate_listview;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.listcandidate_view);
		
	}
	
	@Override
    public void onBackPressed() {
        finish();
    }
	
	@Override
	public void onItemClick(AdapterView<?> adapter, View v, int position, long id) {
		
	}
	
}
