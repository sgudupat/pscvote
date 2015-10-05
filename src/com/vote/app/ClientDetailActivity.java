package com.vote.app;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

public class ClientDetailActivity extends Activity {

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.client_detail);

		Intent intent = getIntent();
		String anchorName = intent.getStringExtra("anchorName");
		String clientName = intent.getStringExtra("clientName");
		String anchorDate = intent.getStringExtra("anchorCreationDate");
		String websiteURL = intent.getStringExtra("websiteURL");
		String clientInfo = intent.getStringExtra("clientInfo");

		try {
			TextView anchor = (TextView) findViewById(R.id.anchorDescription);
			TextView website = (TextView) findViewById(R.id.websitetext);
			TextView clientDetail = (TextView) findViewById(R.id.clientDetail);
			anchor.setText(anchorName + " created on " + anchorDate + " by "
					+ clientName);
			website.setText(websiteURL);
			clientDetail.setText(clientInfo);
		} catch (Exception e) {
		}
	}

	public void goToSearchPage(View view) {
		Intent intent = new Intent(this, SearchActivity.class);
		startActivity(intent);
	}

}