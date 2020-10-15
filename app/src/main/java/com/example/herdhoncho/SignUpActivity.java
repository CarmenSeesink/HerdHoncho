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
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class SignUpActivity extends AppCompatActivity {

    EditText emailET, passwordET, passwordAgainET, nameET;

    private String email;
    private String password;
    private String passwordAgain;
    private String name;

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
        nameET = findViewById(R.id.name_ET);

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
        name = nameET.getText().toString();

        if(!name.isEmpty()){
            // Name is not empty sign up
            if(validateInput.checkIfEmailIsValid(email) && validateInput.checkIfPasswordIsValid(password)) {
                if (password.equals(passwordAgain)) // Check if passwords match
                {
                    mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            // This is triggered when user is created
                            if (task.isSuccessful()) {
                                // Firebase user email, password and other profile info
                                FirebaseUser user = mAuth.getCurrentUser(); // Logged in user
                                Toast.makeText(SignUpActivity.this, "Sign up was successful for: " + user.getEmail(), Toast.LENGTH_SHORT).show();

                                saveNameInFirebaseRealtimeDatabase(user);
                            } else {
                                Toast.makeText(SignUpActivity.this, "Error occured" + task.getException(), Toast.LENGTH_SHORT).show();
                            }
                        }
                    });

                } else {
                    Toast.makeText(this, "Passwords don't match, try again", Toast.LENGTH_SHORT).show();
                }
            }
        }
        else
        {
            Toast.makeText(this, "Please enter farm name", Toast.LENGTH_SHORT).show();
        }
    }

    private void saveNameInFirebaseRealtimeDatabase(FirebaseUser user){
        // Firebase method
        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();

        DatabaseReference rootReference = firebaseDatabase.getReference();
        DatabaseReference nameReference = rootReference.child("Farms").child(user.getUid()).child("name");

        nameReference.setValue(name);
    }

}