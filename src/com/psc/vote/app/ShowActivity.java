package com.psc.vote.app;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

public class ShowActivity extends Activity {
	
	TextView textView;

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Log.i("ShowActivity", "inside showactivity  page");
		setContentView(R.layout.show_item);
		 textView = (TextView) findViewById(R.id.textView);
		 // get the intent from which this activity is called.
        Intent intent = getIntent();

        // fetch value from key-value pair and make it visible on TextView.
        String item = intent.getStringExtra("selected-item");
        textView.setText("you selected "+item);
	}
}