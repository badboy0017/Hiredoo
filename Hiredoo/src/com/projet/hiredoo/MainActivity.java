package com.projet.hiredoo;

import android.os.Bundle;
import android.app.Activity;
import android.text.Html;
import android.view.Menu;
import android.widget.TextView;

public class MainActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		String summary = "<html><font color=\"#000000\" style=\"italique\"><p align="+ "\"" +"left" + "\""+ ">" +  "You are Looking for a job ? <br /> You are hiring a condidate ? <br /> Good you are using the best way !! <br /> HireGo is a mobile application that you allow to post a cv , a video that you describe. it is also a way that allow employee <br /> to finds a good condidate. <br /> with HireDo you will'nt use jornal to search a job , Get Ready for the Hiring !!!" +"</p>"+"</font></html>";
		TextView t = (TextView) findViewById(R.id.paragraph);
t.setText(Html.fromHtml(summary));
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}
