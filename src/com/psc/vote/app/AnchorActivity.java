package com.psc.vote.app;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

public class AnchorActivity extends Activity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.i("SignInPageActivity", "inside user anchor page");
        Intent intent = getIntent();
        // fetch value from key-value pair and make it visible on TextView.
        String item = intent.getStringExtra("campaignid");
        Log.i("campaignId:", item);
        fetchCampaignInfo(item);
        setContentView(R.layout.anchor);
    }

    public void showResult(View view) {
        Intent intent = new Intent(AnchorActivity.this, CampaignActivity.class);
        startActivity(intent);
    }

    public void fetchCampaignInfo(String campaignId) {
        Log.i("fetchCampaignInfo:", "fetchCampaignInfo");
        final ArrayList<NameValuePair> postParameters = new ArrayList<NameValuePair>();
        postParameters.add(new BasicNameValuePair("campaignId", campaignId));
        try {
            String response = SimpleHttpClient.executeHttpPost("/getCampaign", postParameters);
            displayCampaign(response);
            Log.i("Response:", response);
        } catch (Exception e) {
            Log.e("register", e.getMessage() + "");
        }
    }

    private void displayCampaign(String response) {
        Log.i("inside displayCampaign", "displayCampaign");
        try {
            JSONObject campaignJSON = new JSONObject(response);
            TextView anchor = (TextView) findViewById(R.id.campaignPage_AnchorName);
            TextView client = (TextView) findViewById(R.id.campaignPage_ClientName);
            TextView question = (TextView) findViewById(R.id.campaignPage_Question);

            anchor.setText("&loreal_anchor_tmp");
            client.setText("Loreal India");
            //anchor.setText((String) campaignJSON.get(""));
            //client.setText((String) campaignJSON.get(""));
            question.setText((String) campaignJSON.get("question"));
            JSONArray options = new JSONArray((String) campaignJSON.get(""));
            for (int i = 0; i < options.length(); i++) {
                JSONObject option = options.getJSONObject(i);
                if (i == 0) {
                    RadioButton option1 = (RadioButton) findViewById(R.id.campaign_opt1);
                    option1.setText((String) option.get("option_value"));
                    option1.setHint((String) option.get("option_id"));
                } else if (i == 1) {
                    RadioButton option2 = (RadioButton) findViewById(R.id.campaign_opt2);
                    option2.setText((String) option.get("option_value"));
                    option2.setHint((String) option.get("option_id"));
                } else if (i == 2) {
                    RadioButton option3 = (RadioButton) findViewById(R.id.campaign_opt3);
                    option3.setText((String) option.get("option_value"));
                    option3.setHint((String) option.get("option_id"));
                } else if (i == 3) {
                    RadioButton option4 = (RadioButton) findViewById(R.id.campaign_opt4);
                    option4.setText((String) option.get("option_value"));
                    option4.setHint((String) option.get("option_id"));
                }
            }
        } catch (Exception e) {
        }
    }

    public void onRadioSelected(View view) {
        RadioGroup options = (RadioGroup) findViewById(R.id.campaign_options);
        RadioButton option = (RadioButton) findViewById(options.getCheckedRadioButtonId());
        String hint = (String) option.getHint();
        Toast.makeText(getApplicationContext(), "Value:" + hint, Toast.LENGTH_SHORT).show();
    }

    public void showStats(View view) {
        Intent intent = new Intent(this, CampaignActivity.class);
        startActivity(intent);
    }
}