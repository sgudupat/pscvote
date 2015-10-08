package com.vote.app;

import java.io.IOException;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.Random;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONObject;


import com.google.android.gms.gcm.GoogleCloudMessaging;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager.NameNotFoundException;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.provider.Settings.Secure;
import android.telephony.SmsManager;
import android.text.InputType;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

public class SignUpPageActivity extends Activity {
	GoogleCloudMessaging gcm;
	Context context;
	String regId;
	public static final String REG_ID = "regId";
	private static final String APP_VERSION = "appVersion";

	static final String TAG = "Register Activity";

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);        
		setContentView(R.layout.sign_up);
		//EditText password = (EditText) findViewById(R.id.signUpPwd);
		//GCMregister
		if (TextUtils.isEmpty(regId)) {
			regId = registerGCM();
			Log.d("RegisterActivity", "GCM RegId: " + regId);
		} else {
			Toast.makeText(getApplicationContext(),
					"Already Registered with GCM Server!",
					Toast.LENGTH_LONG).show();
			Log.i("device registration id",regId);
		}
		if (TextUtils.isEmpty(regId)) {
			Toast.makeText(getApplicationContext(), "RegId is empty!",
					Toast.LENGTH_LONG).show();
		} else {
			Intent i = new Intent(getApplicationContext(),
					ShareActivity.class);
			i.putExtra("regId", regId);
			Log.d("Rrgister id", "GCM RegId: " + regId);
			Log.d("RegisterActivity",
					"onClick of Share: Before starting main activity.");
			startActivity(i);
			finish();
			Log.d("RegisterActivity", "onClick of Share: After finish.");
		
		}
		
		
		Log.i("device registration id",regId);

		// ((InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE)).showSoftInput(password, InputMethodManager.SHOW_FORCED);            
		Spinner spinner = (Spinner) findViewById(R.id.spinner);
		// Create an ArrayAdapter using the string array and a default spinner layout
		ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.age_array, android.R.layout.simple_spinner_item);
		// Specify the layout to use when the list of choices appears
		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		// Apply the adapter to the spinner
		spinner.setAdapter(adapter);
	}
	
	//GSM REGISTER
	public String registerGCM() {

		gcm = GoogleCloudMessaging.getInstance(this);
		regId = getRegistrationId(context);

		if (TextUtils.isEmpty(regId)) {

			registerInBackground();

			Log.d("RegisterActivity",
					"registerGCM - successfully registered with GCM server - regId: "
							+ regId);
		} else {
			Toast.makeText(getApplicationContext(),
					"RegId already available. RegId: " + regId,
					Toast.LENGTH_LONG).show();
	
			
			Log.i("registration id",""+regId);
		}
		return regId;
	}
	private String getRegistrationId(Context context) {
		final SharedPreferences prefs = getSharedPreferences(
				ShareActivity.class.getSimpleName(), Context.MODE_PRIVATE);
		String registrationId = prefs.getString(REG_ID, "");
		if (registrationId.isEmpty()) {
			Log.i(TAG, "Registration not found.");
			return "";
		}
		int registeredVersion = prefs.getInt(APP_VERSION, Integer.MIN_VALUE);
		int currentVersion = getAppVersion(context);
		if (registeredVersion != currentVersion) {
			Log.i(TAG, "App version changed.");
			return "";
		}
		return registrationId;
	}

	private static int getAppVersion(Context context) {
		try {
			PackageInfo packageInfo = context.getPackageManager()
					.getPackageInfo(context.getPackageName(), 0);
			return packageInfo.versionCode;
		} catch (NameNotFoundException e) {
			Log.d("RegisterActivity",
					"I never expected this! Going down, going down!" + e);
			throw new RuntimeException(e);
		}
	}

	private void registerInBackground() {
		new AsyncTask<Void, Void, String> (){

			protected String doInBackground(Void... params) {
				String msg = "";
				try {
					if (gcm == null) {
						gcm = GoogleCloudMessaging.getInstance(context);
					}
					regId = gcm.register(Config.GOOGLE_PROJECT_ID);
					Log.d("RegisterActivity", "registerInBackground - regId: "
							+ regId);
					msg = "Device registered, registration ID=" + regId;

					//storeRegistrationId(context, regId);
				} catch (IOException ex) {
					msg = "Error :" + ex.getMessage();
					Log.d("RegisterActivity", "Error: " + msg);
				}
				Log.d("RegisterActivity", "AsyncTask completed: " + msg);
				return msg;
			}

			protected void onPostExecute(String msg) {
				Toast.makeText(getApplicationContext(),
						"Registered with GCM Server." + msg, Toast.LENGTH_LONG)
						.show();
			}

			
		}.execute(null, null, null);
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
		password.setInputType(InputType.TYPE_TEXT_VARIATION_EMAIL_ADDRESS);
		RadioGroup gender = (RadioGroup) findViewById(R.id.myRadioGroup);
		RadioButton genderButton = (RadioButton) findViewById(gender.getCheckedRadioButtonId());
		String genderValue = (String) genderButton.getText();

		Spinner age = (Spinner) findViewById(R.id.spinner);
		String ageValue = age.getSelectedItem().toString();

		String deviceId = Secure.getString(getContentResolver(), Secure.ANDROID_ID);       
		final ArrayList<NameValuePair> postParameters = new ArrayList<NameValuePair>();
		postParameters.add(new BasicNameValuePair("username", username.getText().toString()));
		postParameters.add(new BasicNameValuePair("password", password.getText().toString()));
		postParameters.add(new BasicNameValuePair("mobile", mobile.getText().toString()));
		postParameters.add(new BasicNameValuePair("gender", genderValue));
		postParameters.add(new BasicNameValuePair("age", ageValue));
		postParameters.add(new BasicNameValuePair("deviceId", deviceId));
		postParameters.add(new BasicNameValuePair("registrationId", regId));
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
				if (aotp.equals(otp)) {
					try {
						String response = SimpleHttpClient.executeHttpPost("/register", postParameters);

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