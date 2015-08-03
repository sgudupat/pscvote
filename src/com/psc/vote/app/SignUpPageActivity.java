package com.psc.vote.app;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.*;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import java.util.ArrayList;

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
            case R.id.radio_pirates:
                if (checked)
                    // Pirates are the best
                    break;
            case R.id.radio_ninjas:
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

        Log.i("register:", "register");
        final ArrayList<NameValuePair> postParameters = new ArrayList<NameValuePair>();
        postParameters.add(new BasicNameValuePair("username", username.getText().toString()));
        postParameters.add(new BasicNameValuePair("password", password.getText().toString()));
        postParameters.add(new BasicNameValuePair("mobile", mobile.getText().toString()));
        postParameters.add(new BasicNameValuePair("gender", genderValue));
        postParameters.add(new BasicNameValuePair("age", ageValue));
        final String response = null;
        try {
            Log.i("register", "try");
            new Thread(new Runnable() {
                public void run() {
                    Log.i("Response 2:", "In New Thread");
                    try {
                        String response2 = SimpleHttpClient.executeHttpPost("/register", postParameters);
                        Log.i("Response 2:", response2);
                    } catch (Exception e) {
                        Log.i("Response 2:Error:", e.getMessage());
                    }
                }
            }).start();
            Log.i("register", "call done");
            String res = response.toString();
            Log.i("register", res);
            String resp = res.replaceAll("\\s+", "");
            Log.i("register", resp);
        } catch (Exception e) {
            Log.e("register", e.getMessage());
        }
    }
}