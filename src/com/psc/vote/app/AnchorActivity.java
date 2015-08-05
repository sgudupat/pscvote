package com.psc.vote.app;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import java.util.ArrayList;

public class AnchorActivity extends Activity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.i("SignInPageActivity", "inside user anchor page");
        setContentView(R.layout.anchor);
        Intent intent = getIntent();

        // fetch value from key-value pair and make it visible on TextView.
        String item = intent.getStringExtra("campaignid");
        displayCampaign(item);

    }

    public void showResult(View view) {

        Intent intent = new Intent(AnchorActivity.this, CampaignActivity.class);
        //  intent.putExtra("selected-item", text);
        startActivity(intent);


    }

    public void displayCampaign(String campaignId) {
        Log.i("displayCampaign:", "displayCampaign");
        final ArrayList<NameValuePair> postParameters = new ArrayList<NameValuePair>();
        postParameters.add(new BasicNameValuePair("campaignId", campaignId));
        try {
            new Thread(new Runnable() {
                public void run() {
                    try {
                        String response = SimpleHttpClient.executeHttpPost("/getCampaign", postParameters);
                        Log.i("Response:", response);
                    } catch (Exception e) {
                        Log.i("Response 2:Error:", e.getMessage());
                    }
                }
            }).start();
        } catch (Exception e) {
            Log.e("register", e.getMessage() + "");
        }
    }


}