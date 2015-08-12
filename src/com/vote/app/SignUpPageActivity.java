package com.vote.app;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.provider.Settings.Secure;
import android.telephony.SmsManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.*;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONObject;

import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.Random;

public class SignUpPageActivity extends Activity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.i("SignUpPageActivity", "inside user registration page");
        setContentView(R.layout.sign_up);
        Spinner spinner = (Spinner) findViewById(R.id.spinner);
        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.age_array, android.R.layout.simple_spinner_item);
        // Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
        spinner.setAdapter(adapter);
    }

    public void onRadioButtonClicked(View view) {
        // Is the button now checked?
        boolean checked = ((RadioButton) view).isChecked();
        // Check which radio button was clicked
        switch (view.getId()) {
            case R.id.radio_he:
                if (checked)
                    // Pirates are the best
                    break;
            case R.id.radio_she:
                if (checked)
                    // Ninjas rule
                    break;
            case R.id.radio_others:
                if (checked)
                    // Ninjas rule
                    break;
        }
    }

    public void register(View view) {
        EditText username = (EditText) findViewById(R.id.fld_username);
        EditText mobile = (EditText) findViewById(R.id.fld_mbl);
        EditText password = (EditText) findViewById(R.id.signUpPwd);
        RadioGroup gender = (RadioGroup) findViewById(R.id.myRadioGroup);
        RadioButton genderButton = (RadioButton) findViewById(gender.getCheckedRadioButtonId());
        String genderValue = (String) genderButton.getText();

        Spinner age = (Spinner) findViewById(R.id.spinner);
        String ageValue = age.getSelectedItem().toString();

        String deviceId = Secure.getString(getContentResolver(), Secure.ANDROID_ID);
        Log.i("register:", "register");
        final ArrayList<NameValuePair> postParameters = new ArrayList<NameValuePair>();
        postParameters.add(new BasicNameValuePair("username", username.getText().toString()));
        postParameters.add(new BasicNameValuePair("password", password.getText().toString()));
        postParameters.add(new BasicNameValuePair("mobile", mobile.getText().toString()));
        postParameters.add(new BasicNameValuePair("gender", genderValue));
        postParameters.add(new BasicNameValuePair("age", ageValue));
        postParameters.add(new BasicNameValuePair("deviceId", deviceId));
        final Context context = this;
        String chars = "0123456789";
        final int PW_LENGTH = 5;
        Random rnd = new SecureRandom();
        StringBuilder pass = new StringBuilder();
        for (int i = 0; i < PW_LENGTH; i++) {
            pass.append(chars.charAt(rnd.nextInt(chars.length())));
        }
        Log.i("otp password", pass.toString());
        final String otp = pass.toString();
        try {
            String phoneNo = mobile.getText().toString();
            SmsManager smsManager = SmsManager.getDefault();
            smsManager.sendTextMessage(phoneNo, null, otp, null, null);
            Toast.makeText(getApplicationContext(), "SMS Sent!", Toast.LENGTH_LONG).show();
        } catch (Exception e) {
            Toast.makeText(getApplicationContext(), "SMS failed, please try again later!", Toast.LENGTH_LONG).show();
        }

        // get prompts.xml view
        LayoutInflater li = LayoutInflater.from(context);
        View promptsView = li.inflate(R.layout.prompts, null);
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);
        // set prompts.xml to alert dialog builder
        alertDialogBuilder.setView(promptsView);
        final EditText userInput = (EditText) promptsView.findViewById(R.id.editTextDialogUserInput);
        // set dialog message
        alertDialogBuilder.setCancelable(false).setPositiveButton("OK",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // get user input and set it to result edit text
                        String aotp = userInput.getText().toString();
                        Log.i("alert dialog otp", aotp);
                        if (aotp.equals(otp)) {
                            try {
                                String response = SimpleHttpClient.executeHttpPost("/register", postParameters);
                                Log.i("Response:", response);
                                JSONObject jsonobject = new JSONObject(response);
                                SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
                                SharedPreferences.Editor editor = prefs.edit();
                                editor.putString("username", (String) jsonobject.get("user_name"));
                                editor.putString("pushNotification", (String) jsonobject.get("push_notification"));
                                editor.putString("age", (String) jsonobject.get("age"));
                                editor.putString("gender", (String) jsonobject.get("gender"));
                                editor.putString("mobile", (String) jsonobject.get("mobile"));
                                editor.commit();
                                Intent intent = new Intent(context, SearchActivity.class);
                                startActivity(intent);
                                Log.i("inside otp if loop", "search activity started");
                            } catch (Exception e) {
                                Log.e("register", e.getMessage() + "");
                                Toast.makeText(getApplicationContext(), "Login Failed, Please Retry !!!", Toast.LENGTH_LONG).show();
                            }
                        }
                    }
                }).setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                dialog.cancel();
            }
        });
        // create alert dialog
        AlertDialog alertDialog = alertDialogBuilder.create();
        // show it
        alertDialog.show();
    }
}