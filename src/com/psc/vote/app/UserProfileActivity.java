package com.psc.vote.app;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.widget.TextView;

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

        TextView usernameField  = (TextView) findViewById(R.id.userNameText);
        TextView mobileField  = (TextView) findViewById(R.id.mobileText);
        usernameField.setText(username);
        mobileField.setText(mobile);
    }
}