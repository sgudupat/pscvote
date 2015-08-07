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

    String username;
    String campaignId;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.anchor);
        Log.i("SignInPageActivity", "inside user anchor page");
        // fetch value from key-value pair and make it visible on TextView.
        Intent intent = getIntent();
        String anchorName = intent.getStringExtra("anchorName");
        String clientName = intent.getStringExtra("clientName");
        campaignId = intent.getStringExtra("campaignid");
        username = intent.getStringExtra("username");
        Log.i("campaignId:", campaignId);
        String response = fetchCampaignInfo(campaignId);
        Log.i("inside displayCampaign", "displayCampaign");
        try {
            Log.i("Data", "Before All");
            TextView anchor = (TextView) findViewById(R.id.campaignPage_AnchorName);
            Log.i("Data", "Before Client");
            TextView client = (TextView) findViewById(R.id.campaignPage_ClientName);
            Log.i("Data", "Before Question");
            TextView question = (TextView) findViewById(R.id.campaignPage_Question);
            Log.i("Data", "Before JSON");
            JSONObject campaignJSON = new JSONObject(response);
            Log.i("Campaign", campaignJSON.toString());
            anchor.setText(anchorName);
            client.setText(clientName);
            question.setText((String) campaignJSON.get("question"));
            JSONArray options = new JSONArray((String) campaignJSON.get("options"));
            for (int i = 0; i < options.length(); i++) {
                JSONObject option = options.getJSONObject(i);
                Log.i("option value:", String.valueOf(i));
                Log.i("option", option.toString());
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

    public void submitVote(View view) {
        //Submit Vote
        Log.i("username:", username);
        RadioGroup options = (RadioGroup) findViewById(R.id.campaign_options);
        RadioButton option = (RadioButton) findViewById(options.getCheckedRadioButtonId());
        String optionId = (String) option.getHint();
        Log.i("optionId:", optionId);
        submitVote(username, optionId);
        //Display Stats
        Intent intent = new Intent(this, CampaignActivity.class);
        intent.putExtra("campaignId", campaignId);
        startActivity(intent);
    }

    private String fetchCampaignInfo(String campaignId) {
        Log.i("fetchCampaignInfo:", "fetchCampaignInfo");
        final ArrayList<NameValuePair> postParameters = new ArrayList<NameValuePair>();
        postParameters.add(new BasicNameValuePair("campaignId", campaignId));
        try {
            String response = SimpleHttpClient.executeHttpPost("/getCampaign", postParameters);
            Log.i("Response:", response);
            return response;
        } catch (Exception e) {
            Log.e("register", e.getMessage() + "");
        }
        return null;
    }

    private String submitVote(String userName, String campaignOptionId) {
        Log.i("submitVote:", "submitVote");
        final ArrayList<NameValuePair> postParameters = new ArrayList<NameValuePair>();
        postParameters.add(new BasicNameValuePair("username", userName));
        postParameters.add(new BasicNameValuePair("optionId", campaignOptionId));
        try {
            String response = SimpleHttpClient.executeHttpPost("/submitVote", postParameters);
            Log.i("Response:", response);
            return response;
        } catch (Exception e) {
            Log.e("register", e.getMessage() + "");
        }
        return null;
    }

    public void onRadioSelected(View view) {
        RadioGroup options = (RadioGroup) view.findViewById(R.id.campaign_options);
        RadioButton option = (RadioButton) view.findViewById(options.getCheckedRadioButtonId());
        String hint = (String) option.getHint();
        Toast.makeText(getApplicationContext(), "Value:" + hint, Toast.LENGTH_SHORT).show();
    }

    public void showStats(View view) {
        Intent intent = new Intent(this, CampaignActivity.class);
        startActivity(intent);
    }
}