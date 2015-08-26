package com.vote.app;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONObject;

import java.util.ArrayList;

public class SignInPageActivity extends Activity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.i("SignInPageActivity", "inside user sign in page");
        setContentView(R.layout.sign_in);
    }

    public void login(View view) {
        final EditText edit = (EditText) findViewById(R.id.emailAddress);
        EditText pwd = (EditText) findViewById(R.id.password);
        try {
            Log.i("triggerLogin:", "triggerLogin");
            final ArrayList<NameValuePair> postParameters = new ArrayList<NameValuePair>();
            postParameters.add(new BasicNameValuePair("username", edit.getText().toString()));
            postParameters.add(new BasicNameValuePair("password", pwd.getText().toString()));
            final Context context = this;
            try {
                Log.i("LoginPageActivity", "try");
                String response = SimpleHttpClient.executeHttpPost("/login", postParameters);
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
                Log.e("LoginPageActivity", e.getMessage() + "");
                Toast.makeText(getApplicationContext(), "Login Failed, Please Retry !!!", Toast.LENGTH_LONG).show();
            }
            Log.i("After process:", "Done");
        } catch (Exception e) {
        }
    }

    public void forgotPassword(View view) {

        Log.i("inside forgot", " forgot password function");
        Intent intent = new Intent(this, ForgotPasswordActivity.class);
        startActivity(intent);

    }
}