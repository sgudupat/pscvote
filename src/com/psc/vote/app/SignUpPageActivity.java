package com.psc.vote.app;

import android.app.Activity;
import android.os.Bundle;
import android.provider.Settings.Secure;
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

        String deviceId  = Secure.getString(getContentResolver(), Secure.ANDROID_ID);
        Log.i("register:", "register");
        final ArrayList<NameValuePair> postParameters = new ArrayList<NameValuePair>();
        postParameters.add(new BasicNameValuePair("username", username.getText().toString()));
        postParameters.add(new BasicNameValuePair("password", password.getText().toString()));
        postParameters.add(new BasicNameValuePair("mobile", mobile.getText().toString()));
        postParameters.add(new BasicNameValuePair("gender", genderValue));
        postParameters.add(new BasicNameValuePair("age", ageValue));
        postParameters.add(new BasicNameValuePair("deviceId", deviceId));
        final String response = null;
       
    
    final Context context = this;
    String res;
	   EditText mobilep = (EditText) findViewById(R.id.fld_mbl);
	 String chars = "0123456789";
	 final int PW_LENGTH = 5;
  Random rnd = new SecureRandom();
  StringBuilder pass = new StringBuilder();
  for (int i = 0; i < PW_LENGTH; i++)
      pass.append(chars.charAt(rnd.nextInt(chars.length())));
  
  Log.i("otp password",pass.toString());
  final String otp=pass.toString();
  String sms = otp;
  try {
 	 String phoneNo=mobilep.toString();
			SmsManager smsManager = SmsManager.getDefault();
			smsManager.sendTextMessage(phoneNo, null, sms, null, null);
			Toast.makeText(getApplicationContext(), "SMS Sent!",
						Toast.LENGTH_LONG).show();
		  } catch (Exception e) {
			Toast.makeText(getApplicationContext(),
				"SMS faild, please try again later!",
				Toast.LENGTH_LONG).show();
			e.printStackTrace();
		  }

	// get prompts.xml view
	LayoutInflater li = LayoutInflater.from(context);
	View promptsView = li.inflate(R.layout.prompts, null);

	AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
			context);

	// set prompts.xml to alertdialog builder
	alertDialogBuilder.setView(promptsView);

	final EditText userInput = (EditText) promptsView
			.findViewById(R.id.editTextDialogUserInput);

	// set dialog message
	alertDialogBuilder
		.setCancelable(false)
		.setPositiveButton("OK",
		  new DialogInterface.OnClickListener() {
		    public void onClick(DialogInterface dialog,int id) {
			// get user input and set it to result
			// edit text
			//result.setText(userInput.getText());
		    
		    	String aotp=userInput.getText().toString();
		   	 Log.i("alert dailog otp password",aotp);
		    	if(aotp.equals(otp)){
		    		 try {
		    	          //  Log.i("register", "try");
		    	            new Thread(new Runnable() {
		    	                public void run() {
		    	                   // Log.i("Response 2:", "In New Thread");
		    	                    try {
		    	                        String response2 = SimpleHttpClient.executeHttpPost("/register", postParameters);
		    	                        Log.i("Response from db:", response2);
		    	                        String res="success";
		    	                        Log.v("boolean result1",""+TextUtils.equals(response2.toString(), res.toString()));
		    	                        Log.v("boolean result2",""+TextUtils.equals(response2, "success"));
		    	                        Log.v("boolean result3",""+res.equals(response2.toString()));
		    	                        if(TextUtils.equals(response2.toString(), "success")){
		    		    		    		Intent intent = new Intent(context,SearchActivity.class) ;
		    		    		            startActivity(intent);
		    		    		            Log.i("inside otp if loop","search activity started");
		    		    		    			}
		    	                        Log.i("Response 2:", response2);
		    	                    } catch (Exception e) {
		    	                        Log.i("Response 2:Error:", e.getMessage());
		    	                    }
		    	                }
		    	            }).start();
		    	          
		    	        	
		    	        } catch (Exception e) {
		    	            Log.e("register", e.getMessage()+"");
		    	        }
		    		
		    		
		    	}
		    }
		  })
		.setNegativeButton("Cancel",
		  new DialogInterface.OnClickListener() {
		    public void onClick(DialogInterface dialog,int id) {
			dialog.cancel();
		    }
		  });

	// create alert dialog
	AlertDialog alertDialog = alertDialogBuilder.create();

	// show it
	alertDialog.show();
    
    }
}