package com.vote.app;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONObject;

import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.Random;

public class ForgotPasswordActivity extends Activity {

	String generatedOTP;
	String mobileNumber;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.forgot_password);
	}

	public void generateOtp(View view) {
		// Get User information
		EditText username = (EditText) findViewById(R.id.forgotPassword_username);
		try {
			final ArrayList<NameValuePair> postParameters = new ArrayList<NameValuePair>();
			postParameters.add(new BasicNameValuePair("username", username
					.getText().toString()));
			try {

				String response = SimpleHttpClient.executeHttpPost("/getUser",
						postParameters);
				JSONObject jsonobject = new JSONObject(response);
				mobileNumber = (String) jsonobject.get("mobile");
			} catch (Exception e) {
				Log.e("LoginPageActivity", e.getMessage() + "");
				Toast.makeText(getApplicationContext(),
						"Login Failed, Please Retry !!!", Toast.LENGTH_LONG)
						.show();
			}

		} catch (Exception e) {
		}

		// Generate OTP value
		generatedOTP = generateOTPValue();

		// Send SMS the OTP that is generated
		sendSMS(mobileNumber, generatedOTP);
	}

	public void submit(View view) {
		// get user input and set it to result edit text
		EditText otpValue = (EditText) findViewById(R.id.forgotPassword_otp);
		EditText username = (EditText) findViewById(R.id.forgotPassword_username);
		String aotp = otpValue.getText().toString();
		if (aotp.equals(generatedOTP)) {
			try {
				String dynamicPassword = generateDynamicPassword();
				sendSMS(mobileNumber, dynamicPassword);
				final ArrayList<NameValuePair> postParameters = new ArrayList<NameValuePair>();
				postParameters.add(new BasicNameValuePair("username", username
						.getText().toString()));
				postParameters.add(new BasicNameValuePair("password",
						dynamicPassword));
				String response = SimpleHttpClient.executeHttpPost(
						"/updatePassword", postParameters);
				if (response.contains("success")) {
					Intent intent = new Intent(this, SignInPageActivity.class);
					startActivity(intent);
				} else {
					Toast.makeText(getApplicationContext(),
							"Reset Password Failed, Please Retry !!!",
							Toast.LENGTH_LONG).show();
				}
			} catch (Exception e) {
				Log.e("register", e.getMessage() + "");
				Toast.makeText(getApplicationContext(),
						"Reset Password Failed, Please Retry !!!",
						Toast.LENGTH_LONG).show();
			}
		}
	}

	private String generateOTPValue() {
		String chars = "0123456789";
		final int PW_LENGTH = 5;
		Random rnd = new SecureRandom();
		StringBuilder pass = new StringBuilder();
		for (int i = 0; i < PW_LENGTH; i++) {
			pass.append(chars.charAt(rnd.nextInt(chars.length())));
		}
		return pass.toString();
	}

	private String generateDynamicPassword() {
		String chars = "abcdefghijklmnopqrstuvwxyz0123456789";
		final int PW_LENGTH = 8;
		Random rnd = new SecureRandom();
		StringBuilder pass = new StringBuilder();
		for (int i = 0; i < PW_LENGTH; i++) {
			pass.append(chars.charAt(rnd.nextInt(chars.length())));
		}
		return pass.toString();
	}

	private void sendSMS(String mobileNumber, String smsValue) {
		SmsManager smsManager = SmsManager.getDefault();
		smsManager.sendTextMessage(mobileNumber, null, smsValue, null, null);
		Toast.makeText(getApplicationContext(), "SMS Sent!", Toast.LENGTH_LONG)
				.show();
	}
}