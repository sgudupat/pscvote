package com.psc.vote.app;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import org.apache.http.Header;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;
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
        EditText edit = (EditText) findViewById(R.id.emailAddress);
        EditText pwd = (EditText) findViewById(R.id.password);
        try {
            RequestParams params = new RequestParams();
            params.put("username", edit.getText().toString());
            params.put("password", pwd.getText().toString());
            params.put("ausername", "murali");
            params.put("apassword", "mkyongsd1A@asad");
            triggerLoginPostRequest(edit.getText().toString(), pwd.getText().toString());
            Log.i("After process:", "Done");
        } catch (Exception e) {
        }
    }

    public void triggerLogin(String userName, String password) {
        Log.i("triggerLogin:", "triggerLogin");

        final ArrayList<NameValuePair> postParameters = new ArrayList<NameValuePair>();
        postParameters.add(new BasicNameValuePair("username", userName));
        postParameters.add(new BasicNameValuePair("password", password));
        postParameters.add(new BasicNameValuePair("ausername", "murali"));
        postParameters.add(new BasicNameValuePair("apassword", "mkyongsd1A@asad"));
        final String response = null;
        try {
            Log.i("LoginPageActivity", "try");
            new Thread(new Runnable() {
                public void run() {
                    Log.i("Response 2:", "In New Thread");
                    try {
                        String response2 = SimpleHttpClient.executeHttpPost2();
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
    }

    public void triggerLoginPostRequest(String userName, String password) {
        Log.i("triggerLogin:", "triggerLogin");

        final ArrayList<NameValuePair> postParameters = new ArrayList<NameValuePair>();
        postParameters.add(new BasicNameValuePair("username", userName));
        postParameters.add(new BasicNameValuePair("password", password));
        final String response = null;
        try {
            Log.i("LoginPageActivity", "try");
            new Thread(new Runnable() {
                public void run() {
                    Log.i("Response 2:", "In New Thread");
                    try {
                        String response2 = SimpleHttpClient.executeHttpPost(postParameters);
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
    }
}