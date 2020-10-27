package com.example.herdhoncho;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class HomeActivity extends AppCompatActivity {
    private TextView homeMessageTV;
    private ImageButton startBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        // Init navigation
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);

        // Set current selected
        bottomNavigationView.setSelectedItemId(R.id.scan);

        // Test
        startBtn = findViewById(R.id.start_btn);

        // Perform
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId()){
                    case R.id.livestock:
                        startActivity(new Intent(getApplicationContext(), LivestockActivity.class));
                        overridePendingTransition(0,0);
                        return true;
                    case R.id.scan:
                        return true;
                    case R.id.profile:
                        startActivity(new Intent(getApplicationContext(), UserAccountActivity.class));
                        overridePendingTransition(0,0);
                        return true;
                    case R.id.task:
                        startActivity(new Intent(getApplicationContext(), TasksActivity.class));
                        overridePendingTransition(0,0);
                        return true;
                }
                return false;
            }
        });

        // Get name of farm
        homeMessageTV = findViewById(R.id.homeMessage_TV);

        // Get name from Firebase
        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();

        DatabaseReference rootReference = firebaseDatabase.getReference();
        DatabaseReference nameReference = rootReference.child("Farms").child(currentUser.getUid()).child("name");

        nameReference.addListenerForSingleValueEvent(new ValueEventListener() {
            // Triggered at least once
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                // DataSnapshot will have = {name="xx"}
                homeMessageTV.setText("Farm " +dataSnapshot.getValue().toString());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


        // Start scan
        startBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(HomeActivity.this, "Camera btn is clicked", Toast.LENGTH_SHORT).show();
                startScan();
            }
        });
    }

    private void startScan(){
        Intent scanIntent = new Intent(HomeActivity.this, ScanActivity.class);
        startActivity(scanIntent);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.app_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId())
        {
            case R.id.useraccount:
                Intent userAccountIntent = new Intent(HomeActivity.this,UserAccountActivity.class);
                startActivity(userAccountIntent);
                break;

            case R.id.logout:
                FirebaseAuth.getInstance().signOut();
                this.finish();
                Intent loginIntent = new Intent(HomeActivity.this,MainActivity.class);
                startActivity(loginIntent);
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}