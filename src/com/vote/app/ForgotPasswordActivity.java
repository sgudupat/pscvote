package com.vote.app;

import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.Random;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.telephony.SmsManager;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class ForgotPasswordActivity extends Activity {
	 @Override
	    public void onCreate(Bundle savedInstanceState) {
	        super.onCreate(savedInstanceState);
	        Log.i("UserHomePageActivity", "inside user home landing page");
	        setContentView(R.layout.forgot_password);
	    }
	 public void generateOtp(View view) {
		 EditText username = (EditText) findViewById(R.id.emailAddress);
		 String mobile="";
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
	            Log.i("triggerLogin:", "triggerLogin");
	            final ArrayList<NameValuePair> postParameters = new ArrayList<NameValuePair>();
	            postParameters.add(new BasicNameValuePair("username", username.getText().toString()));
	          
	            try {
	                Log.i("LoginPageActivity", "try");
	                String response = SimpleHttpClient.executeHttpPost("/getUser", postParameters);
	                Log.i("Response:", response);
	                JSONObject jsonobject = new JSONObject(response);
	                mobile=(String) jsonobject.get("mobile");
	                Log.i("Mobile number", mobile);
	            } catch (Exception e) {
	                Log.e("LoginPageActivity", e.getMessage() + "");
	                Toast.makeText(getApplicationContext(), "Login Failed, Please Retry !!!", Toast.LENGTH_LONG).show();
	            }
	            Log.i("After process:", "Done");
	        } catch (Exception e) {
	        }
	        try {
	            String phoneNo = mobile;
	            SmsManager smsManager = SmsManager.getDefault();
	            smsManager.sendTextMessage(phoneNo, null, otp, null, null);
	            Toast.makeText(getApplicationContext(), "SMS Sent!", Toast.LENGTH_LONG).show();
	        } catch (Exception e) {
	            Toast.makeText(getApplicationContext(), "SMS failed, please try again later!", Toast.LENGTH_LONG).show();
	        }
	        
	    }
	 
	 
	 public void submit(View view) {
		 
		 
		 
		 
	 }

}
