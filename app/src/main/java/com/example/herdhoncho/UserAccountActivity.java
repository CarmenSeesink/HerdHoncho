package com.example.herdhoncho;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class UserAccountActivity extends AppCompatActivity implements View.OnClickListener {

    private TextView welcomeMessageTV;
    private Button logoutBtn;
    private Button updatePasswordBtn;
    private Button updateEmailBtn;

    FirebaseAuth firebaseAuth;
    FirebaseUser user;

    Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_account);

        welcomeMessageTV = findViewById(R.id.welcomeMessage_TV);
        logoutBtn = findViewById(R.id.logout_btn);
        updatePasswordBtn = findViewById(R.id.updatePassword_btn);
        updateEmailBtn = findViewById(R.id.updateEmail_btn);

        FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();

        FirebaseUser user = firebaseAuth.getCurrentUser();

        welcomeMessageTV.setText("Hi, " +user.getEmail()+"!");

        logoutBtn.setOnClickListener(this);
        updatePasswordBtn.setOnClickListener(this);
        updateEmailBtn.setOnClickListener(this);

        context = this;

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
        AlertDialog.Builder builder = new AlertDialog.Builder(context);

        builder.setMessage("Are you sure you want to logout?").setPositiveButton("Yes logout", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                firebaseAuth.signOut();
                ((Activity)context).finish();
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