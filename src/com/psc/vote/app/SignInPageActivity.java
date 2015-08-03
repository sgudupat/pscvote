package com.psc.vote.app;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import java.util.ArrayList;

public class SignInPageActivity extends Activity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.i("SignInPageActivity", "inside user sign in page");
        setContentView(R.layout.sign_in);
    }

    public void login(View view) {
        EditText edit = (EditText) findViewById(R.id.emailAddress);
        EditText pwd = (EditText) findViewById(R.id.password);
        try {
            Log.i("triggerLogin:", "triggerLogin");
            final ArrayList<NameValuePair> postParameters = new ArrayList<NameValuePair>();
            postParameters.add(new BasicNameValuePair("username", edit.getText().toString()));
            postParameters.add(new BasicNameValuePair("password", pwd.getText().toString()));
            final String response = null;
            try {
                Log.i("LoginPageActivity", "try");
                new Thread(new Runnable() {
                    public void run() {
                        Log.i("Response 2:", "In New Thread");
                        try {
                            String response2 = SimpleHttpClient.executeHttpPost("/login", postParameters);
                            Log.i("Response 2:", response2);
                        } catch (Exception e) {
                            Log.i("Response 2:Error:", e.getMessage());
                        }
                    }
                }).start();
                Log.i("LoginPageActivity", "call done");
                String res = response.toString();
                Log.i("LoginPageActivity", res);
                String resp = res.replaceAll("\\s+", "");
                Log.i("LoginPageActivity", resp);
            } catch (Exception e) {
                e.printStackTrace();
                String errorMsg = e.getMessage();
                Log.e("LoginPageActivity", errorMsg);
            }
            Log.i("After process:", "Done");
        } catch (Exception e) {
        }
    }
}