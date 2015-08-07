package com.psc.vote.app;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

public class UserHomePageActivity extends Activity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.i("UserHomePageActivity", "inside user home landing page");
        setContentView(R.layout.main);
    }

    public void signInLandingPage(View view) {
        Intent intent = new Intent(this, SignInPageActivity.class);
        startActivity(intent);
    }

    public void signUpLandingPage(View view) {
        Intent intent = new Intent(this, SignUpPageActivity.class);
        startActivity(intent);
    }

    public void searchPage(View view) {
        Intent intent = new Intent(this, SearchActivity.class);
        startActivity(intent);
    }

    public void campaignView(View view) {
        Intent intent = new Intent(this, AnchorActivity.class);
        startActivity(intent);
    }
}