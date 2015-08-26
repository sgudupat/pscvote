package com.psc.vote.app;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

public class AnchorActivity extends Activity {

    String username;
    String campaignId;
    String anchorName;
    String clientName;
    String readOnly;
    String status;
    String userVoted;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.anchor);
        Log.i("SignInPageActivity", "inside user anchor page");
        // fetch value from key-value pair and make it visible on TextView.
        Intent intent = getIntent();
        anchorName = intent.getStringExtra("anchorName");
        clientName = intent.getStringExtra("clientName");
        campaignId = intent.getStringExtra("campaignId");
        readOnly = intent.getStringExtra("readOnly");
        status = intent.getStringExtra("status");
        Log.i("campaignId:", campaignId);

        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        username = preferences.getString("username", "");
        String response = fetchCampaignInfo(campaignId);
        Log.i("inside displayCampaign", "displayCampaign");
        try {
            TextView anchor = (TextView) findViewById(R.id.campaignPage_AnchorName);
            TextView client = (TextView) findViewById(R.id.campaignPage_ClientName);
            TextView question = (TextView) findViewById(R.id.campaignPage_Question);
            JSONObject campaignJSON = new JSONObject(response);
            anchor.setText(anchorName);
            client.setText(clientName);
            question.setText((String) campaignJSON.get("question"));
            Log.i("userVoted", userVoted);
            if (TextUtils.equals(userVoted, "Y")) {
                setContentView(R.layout.update_campaign);
                Log.i("SignInPageActivity", "inside user anchor page");
            }

            JSONArray options = new JSONArray((String) campaignJSON.get("options"));
            for (int i = 0; i < options.length(); i++) {
                JSONObject option = options.getJSONObject(i);
                Log.i("option value:", String.valueOf(i));
                Log.i("option", option.toString());
                if (i == 0) {
                    RadioButton option1 = (RadioButton) findViewById(R.id.campaign_opt1);
                    option1.setVisibility(View.VISIBLE);
                    option1.setText((String) option.get("option_value"));
                    option1.setHint((String) option.get("option_id"));
                } else if (i == 1) {
                    RadioButton option2 = (RadioButton) findViewById(R.id.campaign_opt2);
                    option2.setVisibility(View.VISIBLE);
                    option2.setText((String) option.get("option_value"));
                    option2.setHint((String) option.get("option_id"));
                } else if (i == 2) {
                    RadioButton option3 = (RadioButton) findViewById(R.id.campaign_opt3);
                    option3.setVisibility(View.VISIBLE);
                    option3.setText((String) option.get("option_value"));
                    option3.setHint((String) option.get("option_id"));
                } else if (i == 3) {
                    RadioButton option4 = (RadioButton) findViewById(R.id.campaign_opt4);
                    option4.setVisibility(View.VISIBLE);
                    option4.setText((String) option.get("option_value"));
                    option4.setHint((String) option.get("option_id"));
                }
            }
            //If Read Only then Done button will not be displayed
            if (!TextUtils.isEmpty(readOnly) && (TextUtils.equals(readOnly, "Y") || (TextUtils.equals(status, "STOPPED")))) {
                Button doneButton = (Button) findViewById(R.id.doneButton);
                doneButton.setVisibility(View.INVISIBLE);
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
        intent.putExtra("anchorName", anchorName);
        intent.putExtra("clientName", clientName);
        intent.putExtra("campaignId", campaignId);
        intent.putExtra("readOnly", "N");
        startActivity(intent);
    }

    private String fetchCampaignInfo(String campaignId) {
        Log.i("fetchCampaignInfo:", "fetchCampaignInfo");
        final ArrayList<NameValuePair> postParameters = new ArrayList<NameValuePair>();
        postParameters.add(new BasicNameValuePair("campaignId", campaignId));
        postParameters.add(new BasicNameValuePair("userName", username));
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
        RadioGroup options = (RadioGroup) findViewById(R.id.campaign_options);
        RadioButton option = (RadioButton) findViewById(options.getCheckedRadioButtonId());
        String hint = (String) option.getHint();
        //Toast.makeText(getApplicationContext(), "Value:" + hint, Toast.LENGTH_SHORT).show();
    }

    public void showStats(View view) {
        Intent intent = new Intent(this, CampaignActivity.class);
        intent.putExtra("anchorName", anchorName);
        intent.putExtra("clientName", clientName);
        intent.putExtra("campaignId", campaignId);
        intent.putExtra("readOnly", readOnly);
        startActivity(intent);
    }
}