package com.example.herdhoncho;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    EditText emailET, passwordET;
    private Button loginBtn;
    private TextView signUpText;

    ValidateInput validateInput;

    private String email;
    private String password;

    FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // START Login

        validateInput = new ValidateInput(this);

        emailET = findViewById(R.id.email_ET);
        passwordET = findViewById(R.id.password_ET);
        loginBtn = findViewById(R.id.login_btn);
        signUpText = findViewById(R.id.signUp_TV);

        loginBtn.setOnClickListener(this);
        signUpText.setOnClickListener(this);

        mAuth = FirebaseAuth.getInstance();

        // END Login
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();

        switch (id)
        {
            case R.id.login_btn:
                    handleLoginBtnClick();
                break;

            case R.id.signUp_TV:
                    handleSignUpBtnClick();
                break;

        }

    }

    private void handleLoginBtnClick() {
        email = emailET.getText().toString();
        password = passwordET.getText().toString();

        // Login user with email and password
        if(validateInput.checkIfEmailIsValid(email) && validateInput.checkIfPasswordIsValid(password))
        {
            // Use Firebase to login user
            mAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if(task.isSuccessful())
                    {
                        // Login
                        Intent userAccountActivity = new Intent(MainActivity.this, HomeActivity.class);
                        startActivity(userAccountActivity);
                    }
                    else
                    {
                        Log.e("Not working", "onComplete: Failed=" + task.getException().getMessage());
                        Toast.makeText(MainActivity.this, "Error occurred: " +task.getException(), Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
    }

    private void handleSignUpBtnClick() {
        Toast.makeText(this, "Sign up here", Toast.LENGTH_SHORT).show();
        Intent signUpActivity = new Intent(MainActivity.this, SignUpActivity.class);
        startActivity(signUpActivity);
    }
}