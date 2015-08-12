package com.vote.app;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

public class RewardDetailActivity extends Activity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.i("rewarddetail Activity", "inside rewardetail page");
        setContentView(R.layout.reward_detail);
        Intent intent = getIntent();

        // fetch value from key-value pair and make it visible on TextView.
        String rewardDetail = intent.getStringExtra("rewarddesc");
        String rid = intent.getStringExtra("rid");
        String sdate = intent.getStringExtra("rewardsdate");
        String edate = intent.getStringExtra("rewardedate");
        Log.i("reward detail", rewardDetail);
        Log.i("reward  id", rid);
        Log.i("reward  sdate", sdate);
        Log.i("reward  edate", edate);

        TextView offer = (TextView) findViewById(R.id.offertext);
        TextView validity = (TextView) findViewById(R.id.validitytext);
        offer.setText(rewardDetail);
        String validityText = "from " + sdate + " to " + edate;
        validity.setText(validityText);

    }

    public void showReward(View view) {
        Intent intent = new Intent(this, RewardActivity.class);
        startActivity(intent);
    }
}
