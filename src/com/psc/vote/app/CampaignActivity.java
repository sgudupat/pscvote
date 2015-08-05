package com.psc.vote.app;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;

public class CampaignActivity extends Activity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.i("SignInPageActivity", "inside user campaign page");
        setContentView(R.layout.campaign);
    }


}
