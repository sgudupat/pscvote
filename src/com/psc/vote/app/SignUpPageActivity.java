package com.psc.vote.app;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;

public class SignUpPageActivity extends Activity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.i("SignUpPageActivity", "inside user registration page");
        setContentView(R.layout.sign_up);
    }
}