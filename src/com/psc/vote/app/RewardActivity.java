package com.psc.vote.app;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;

public class RewardActivity extends Activity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.i("rewardActivity", "inside user sign in page");
        setContentView(R.layout.reward_detail);
    }
}