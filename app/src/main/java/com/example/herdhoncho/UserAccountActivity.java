package com.example.herdhoncho;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class UserAccountActivity extends AppCompatActivity implements View.OnClickListener {

    private TextView welcomeMessageTV;
    private Button logoutBtn;
    private Button updatePasswordBtn;
    private Button updateEmailBtn;
    private Button addEmployeeDetails;

    FirebaseAuth firebaseAuth;
    FirebaseUser user;

    Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_account);

        // Init navigation
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);

        // Set current selected
        bottomNavigationView.setSelectedItemId(R.id.profile);

        // Perform
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId()){
                    case R.id.livestock:
                        startActivity(new Intent(getApplicationContext(), LivestockActivity.class));
                        overridePendingTransition(0,0);
                        return true;
                    case R.id.profile:
                        return true;
                    case R.id.scan:
                        startActivity(new Intent(getApplicationContext(), HomeActivity.class));
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

        welcomeMessageTV = findViewById(R.id.welcomeMessage_TV);
        logoutBtn = findViewById(R.id.logout_btn);
        updatePasswordBtn = findViewById(R.id.updatePassword_btn);
        updateEmailBtn = findViewById(R.id.updateEmail_btn);
        addEmployeeDetails = findViewById(R.id.addEmployee_btn);

        firebaseAuth = FirebaseAuth.getInstance();

        user = firebaseAuth.getCurrentUser();

        welcomeMessageTV.setText("Hi, " +user.getEmail()+"!");

        logoutBtn.setOnClickListener(this);
        updatePasswordBtn.setOnClickListener(this);
        updateEmailBtn.setOnClickListener(this);

        context = this;

        // Open employee
        addEmployeeDetails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addEmployee();
            }
        });

    }

    private void addEmployee(){
        Intent employeeIntent = new Intent(UserAccountActivity.this, EmployeeActivity.class);
        startActivity(employeeIntent);
    }

    @Override
    public void onClick(View view){
        int id = view.getId();

        switch (id)
        {
            case R.id.logout_btn:
                // Logout
                showLogoutDialog();
                break;

            case R.id.updatePassword_btn:
                showUpdatePasswordActivity();
                break;

            case R.id.updateEmail_btn:
                showUpdateEmailActivity();
                break;
        }
    }

    private void showUpdatePasswordActivity() {
        Toast.makeText(context, "Update password", Toast.LENGTH_SHORT).show();

        // Show update password
        Intent updatePasswordActivity = new Intent(UserAccountActivity.this, UpdatePasswordActivity.class);
        startActivity(updatePasswordActivity);

    }

    private void showUpdateEmailActivity() {
        Toast.makeText(context, "Update email", Toast.LENGTH_SHORT).show();

        // Show update password
        Intent updateEmailActivity = new Intent(UserAccountActivity.this, UpdateEmailActivity.class);
        startActivity(updateEmailActivity);

    }

    private void showLogoutDialog(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        builder.setMessage("Are you sure you want to logout?").setPositiveButton("Yes logout", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                firebaseAuth.signOut();
                ((Activity)context).finish();

                Intent loginActivity = new Intent(UserAccountActivity.this, MainActivity.class);
                startActivity(loginActivity);
            }
        }).setNegativeButton("No stay logged in", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        });

        AlertDialog alertDialog = builder.create();

        alertDialog.show();
    }
}