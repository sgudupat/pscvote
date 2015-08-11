package com.psc.vote.app;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.*;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import java.util.ArrayList;

public class UserProfileActivity extends Activity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.i("UserProfileActivity", "inside user profile page");
        setContentView(R.layout.profile);
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        String username = preferences.getString("username", "");
        String mobile = preferences.getString("mobile", "");
        String age = preferences.getString("age", "");
        String pushNotification = preferences.getString("pushNotification", "");
        String gender = preferences.getString("gender", "");

        TextView usernameField = (TextView) findViewById(R.id.userNameText);
        TextView mobileField = (TextView) findViewById(R.id.mobileText);
        Spinner ageView = (Spinner) findViewById(R.id.profile_age);
        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.age_array, android.R.layout.simple_spinner_item);
        // Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
        ageView.setAdapter(adapter);
        ageView.setSelection(adapter.getPosition(age));
        usernameField.setText(username);
        mobileField.setText(mobile);

        RadioButton radio1 = (RadioButton) findViewById(R.id.gen1);
        RadioButton radio2 = (RadioButton) findViewById(R.id.gen2);
        RadioButton radio3 = (RadioButton) findViewById(R.id.gen3);
        if (gender.contains("M")) {
            radio1.setChecked(true);
        } else if (gender.contains("F")) {
            radio2.setChecked(true);
        } else if (gender.contains("O")) {
            radio3.setChecked(true);
        }

        RadioButton push1 = (RadioButton) findViewById(R.id.push1);
        RadioButton push2 = (RadioButton) findViewById(R.id.push2);
        if (pushNotification.contains("Y")) {
            push1.setChecked(true);
        } else {
            push2.setChecked(true);
        }
    }

    public void showSearch(View view) {
        submitChanges();
        Intent intent = new Intent(this, SearchActivity.class);
        startActivity(intent);
    }

    private void submitChanges() {
        TextView usernameField = (TextView) findViewById(R.id.userNameText);
        TextView mobileField = (TextView) findViewById(R.id.mobileText);
        RadioGroup gen = (RadioGroup) findViewById(R.id.myRadioGroup2);
        RadioButton genderButton = (RadioButton) findViewById(gen.getCheckedRadioButtonId());
        String genderValue = (String) genderButton.getText();
        RadioGroup push = (RadioGroup) findViewById(R.id.myradiogroup3);
        RadioButton pushButton = (RadioButton) findViewById(gen.getCheckedRadioButtonId());
        String pushValue = (String) pushButton.getText();
        Log.i("username", usernameField.getText().toString());
        Log.i("mobile", mobileField.getText().toString());
        Log.i("gender", genderValue);
        Log.i("pushnotification", pushValue);
        final ArrayList<NameValuePair> postParameters = new ArrayList<NameValuePair>();
        postParameters.add(new BasicNameValuePair("username", usernameField.getText().toString()));
        postParameters.add(new BasicNameValuePair("gender", genderValue));
        postParameters.add(new BasicNameValuePair("age", "20_30"));
        postParameters.add(new BasicNameValuePair("pushNotification", pushValue));
        try {
            String response = SimpleHttpClient.executeHttpPost("/updateUser", postParameters);
            Log.i("Response:", response);
        } catch (Exception e) {
            Log.i("Response 2:Error:", e.getMessage());
        }
    }
}