package com.vote.app;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.*;
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
    String selectedOption;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.anchor);
        Log.i("AnchorActivity", "inside user anchor page");
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
            String questionValue = campaignJSON.getString("question");
            userVoted = campaignJSON.getString("userVoted");
            Log.i("userVoted", userVoted);
            question.setText(questionValue);
            JSONArray options = new JSONArray(campaignJSON.getString("options"));
            if (TextUtils.equals(userVoted, "Y")) {
                LinearLayout ql = (LinearLayout) findViewById(R.id.otp_value_ro); //Question Layout
                LinearLayout ol = (LinearLayout) findViewById(R.id.option_ro); //Options Layout
                ql.setVisibility(View.VISIBLE);
                ol.setVisibility(View.VISIBLE);
                TextView roQuestion = (TextView) findViewById(R.id.campaignPage_Question_ro);
                roQuestion.setText(questionValue);
                selectedOption = campaignJSON.getString("optionValue");
                Log.i("Selected Value:", selectedOption);
                String optionId, optionValue;
                for (int i = 0; i < options.length(); i++) {
                    JSONObject option = options.getJSONObject(i);
                    Log.i("option value:", String.valueOf(i));
                    Log.i("option", option.toString());
                    if (i == 0) {
                        RadioButton option1 = (RadioButton) findViewById(R.id.campaign_ro_opt1);
                        option1.setVisibility(View.VISIBLE);
                        optionId = option.getString("option_id");
                        optionValue = option.getString("option_value");
                        option1.setHint(optionId);
                        option1.setText(optionValue);
                        if (selectedOption.equals(optionId)) {
                            option1.setChecked(true);
                        }
                        option1.setEnabled(false);
                    } else if (i == 1) {
                        RadioButton option2 = (RadioButton) findViewById(R.id.campaign_ro_opt2);
                        option2.setVisibility(View.VISIBLE);
                        optionId = option.getString("option_id");
                        optionValue = option.getString("option_value");
                        option2.setHint(optionId);
                        option2.setText(optionValue);
                        if (selectedOption.equals(optionId)) {
                            option2.setChecked(true);
                        }
                        option2.setEnabled(false);
                    } else if (i == 2) {
                        RadioButton option3 = (RadioButton) findViewById(R.id.campaign_ro_opt3);
                        option3.setVisibility(View.VISIBLE);
                        optionId = option.getString("option_id");
                        optionValue = option.getString("option_value");
                        option3.setHint(optionId);
                        option3.setText(optionValue);
                        if (selectedOption.equals(optionId)) {
                            option3.setChecked(true);
                        }
                        option3.setEnabled(false);
                    } else if (i == 3) {
                        RadioButton option4 = (RadioButton) findViewById(R.id.campaign_ro_opt4);
                        option4.setVisibility(View.VISIBLE);
                        optionId = option.getString("option_id");
                        optionValue = option.getString("option_value");
                        option4.setHint(optionId);
                        option4.setText(optionValue);
                        if (selectedOption.equals(optionId)) {
                            option4.setChecked(true);
                        }
                        option4.setEnabled(false);
                    } else if (i == 4) {
                        RadioButton option5 = (RadioButton) findViewById(R.id.campaign_ro_opt5);
                        option5.setVisibility(View.VISIBLE);
                        optionId = option.getString("option_id");
                        optionValue = option.getString("option_value");
                        option5.setHint(optionId);
                        option5.setText(optionValue);
                        if (selectedOption.equals(optionId)) {
                            option5.setChecked(true);
                        }
                        option5.setEnabled(false);
                    }
                }
            }

            for (int i = 0; i < options.length(); i++) {
                JSONObject option = options.getJSONObject(i);
                Log.i("option value:", String.valueOf(i));
                Log.i("option", option.toString());
                if (i == 0) {
                    RadioButton option1 = (RadioButton) findViewById(R.id.campaign_opt1);
                    option1.setVisibility(View.VISIBLE);
                    option1.setText(option.getString("option_value"));
                    option1.setHint(option.getString("option_id"));
                } else if (i == 1) {
                    RadioButton option2 = (RadioButton) findViewById(R.id.campaign_opt2);
                    option2.setVisibility(View.VISIBLE);
                    option2.setText(option.getString("option_value"));
                    option2.setHint(option.getString("option_id"));
                } else if (i == 2) {
                    RadioButton option3 = (RadioButton) findViewById(R.id.campaign_opt3);
                    option3.setVisibility(View.VISIBLE);
                    option3.setText(option.getString("option_value"));
                    option3.setHint(option.getString("option_id"));
                } else if (i == 3) {
                    RadioButton option4 = (RadioButton) findViewById(R.id.campaign_opt4);
                    option4.setVisibility(View.VISIBLE);
                    option4.setText(option.getString("option_value"));
                    option4.setHint(option.getString("option_id"));
                } else if (i == 4) {
                    RadioButton option5 = (RadioButton) findViewById(R.id.campaign_opt5);
                    option5.setVisibility(View.VISIBLE);
                    option5.setText(option.getString("option_value"));
                    option5.setHint(option.getString("option_id"));
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
            String response = null;
            if (TextUtils.equals(userVoted, "Y")) {
                postParameters.add(new BasicNameValuePair("oldOptionId", selectedOption));
                response = SimpleHttpClient.executeHttpPost("/resubmitVote", postParameters);
            } else {
                response = SimpleHttpClient.executeHttpPost("/submitVote", postParameters);
            }
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