package com.example.herdhoncho;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();

        // Current user object
        final FirebaseUser currentUser = firebaseAuth.getCurrentUser();

        Handler handler = new Handler();

        handler.postDelayed(new Runnable() {
            @Override
            public void run() {

                if(currentUser != null)
                {
                    // User is logged in
                    Intent homeIntent = new Intent(SplashActivity.this, HomeActivity.class);
                    startActivity(homeIntent);
                }
                else
                {
                    // User is not logged in
                    Intent loginIntent = new Intent(SplashActivity.this, MainActivity.class);
                    startActivity(loginIntent);
                }
            }
        }, 1500);

    }
}