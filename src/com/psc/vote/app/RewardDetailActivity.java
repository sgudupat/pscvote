package com.psc.vote.app;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

public class RewardDetailActivity extends Activity {
	final Context context = this;
	 ArrayList<Reward> reward= new ArrayList<Reward>();
	
     
	
	  @Override
	    public void onCreate(Bundle savedInstanceState) {
	        super.onCreate(savedInstanceState);
	        Log.i("rewarddetail Activity", "inside rewardetail page");
	        setContentView(R.layout.reward_detail);
	        Intent intent = getIntent();

	        // fetch value from key-value pair and make it visible on TextView.
	        String rewardDetail = intent.getStringExtra("rewarddesc");
	        String rid = intent.getStringExtra("rid");
	       String sdate=intent.getStringExtra("rewardsdate");
	       String  edate=intent.getStringExtra("rewardedate");
	        Log.i("reward detail",rewardDetail);
	        Log.i("reward  id",rid);
	        Log.i("reward  sdate",sdate);
	        Log.i("reward  edate",edate);
	     
			 TextView offer = (TextView)findViewById(R.id.offertext); 
			 TextView validity = (TextView)findViewById(R.id.validitytext); 
			 offer.setText(rewardDetail);
			 String validityText="from "+sdate+" to "+edate;
			 validity.setText(validityText);
	        
	    }

	  public void showReward(View view){
		  
		  Intent intent = new Intent(this, RewardActivity.class);
	        startActivity(intent);
		  
	  }
	  
	  
}
