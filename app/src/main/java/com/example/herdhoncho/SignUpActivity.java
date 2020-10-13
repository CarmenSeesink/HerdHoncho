package com.example.herdhoncho;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class SignUpActivity extends AppCompatActivity {

    EditText emailET, passwordET, passwordAgainET;

    private String email;
    private String password;
    private String passwordAgain;

    Button signUpBtn;

    ValidateInput validateInput;

    FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        // START Sign up

        emailET = findViewById(R.id.email_ET);
        passwordET = findViewById(R.id.password_ET);
        passwordAgainET = findViewById(R.id.password_Again);
        signUpBtn = findViewById(R.id.signUp_btn);

        validateInput = new ValidateInput(this);

        signUpBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                // Handle SignUp
                handleSignUpBtnClick();
            }
        });

        mAuth = FirebaseAuth.getInstance();

        // END Sign up
    }

    private void handleSignUpBtnClick(){
        // Fetching string values
        email = emailET.getText().toString();
        password = passwordET.getText().toString();
        passwordAgain = passwordAgainET.getText().toString();

        if(validateInput.checkIfEmailIsValid(email) && validateInput.checkIfPasswordIsValid(password))
        {
            if(password.equals(passwordAgain)) // Check if passwords match
            {
                mAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        // This is triggered when user is created
                        if(task.isSuccessful())
                        {
                            // Firebase user email, password and other profile info
                            FirebaseUser user = mAuth.getCurrentUser(); // Logged in user
                            Toast.makeText(SignUpActivity.this, "Sign up was successful for: " +user.getEmail(), Toast.LENGTH_SHORT).show();
                        }
                        else
                        {
                            Toast.makeText(SignUpActivity.this, "Error occured" +task.getException(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });

            }
            else
            {
                Toast.makeText(this, "Passwords don't match, try again", Toast.LENGTH_SHORT).show();
            }
        }



    }
}