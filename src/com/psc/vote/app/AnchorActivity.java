package com.psc.vote.app;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

public class AnchorActivity extends Activity {
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Log.i("SignInPageActivity", "inside user anchor page");
		setContentView(R.layout.anchor);
		
	}

	 public void showResult(View view) {
		 
		  Intent intent = new Intent(AnchorActivity.this, CampaignActivity.class);
 	      //  intent.putExtra("selected-item", text);
 	        startActivity(intent);
		 
	 
	 }

}
