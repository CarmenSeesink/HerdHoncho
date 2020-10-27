package com.example.herdhoncho;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class TasksActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tasks);

        // Init navigation
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);

        // Set current selected
        bottomNavigationView.setSelectedItemId(R.id.task);

        // Perform
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId()){
                    case R.id.livestock:
                        startActivity(new Intent(getApplicationContext(), LivestockActivity.class));
                        overridePendingTransition(0,0);
                        return true;
                    case R.id.task:
                        return true;
                    case R.id.profile:
                        startActivity(new Intent(getApplicationContext(), UserAccountActivity.class));
                        overridePendingTransition(0,0);
                        return true;
                    case R.id.scan:
                        startActivity(new Intent(getApplicationContext(), HomeActivity.class));
                        overridePendingTransition(0,0);
                        return true;
                }
                return false;
            }
        });

    }
}