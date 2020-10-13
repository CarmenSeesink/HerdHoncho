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
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class UpdatePasswordActivity extends AppCompatActivity {

    private EditText passwordET;
    private EditText passwordAgainET;
    private Button updatePasswordButton;

    ValidateInput validateInput;
    FirebaseAuth firebaseAuth;
    FirebaseUser firebaseUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_password);

        passwordET = findViewById(R.id.password_ET);
        passwordAgainET = findViewById(R.id.password_Again);

        updatePasswordButton = findViewById(R.id.update_btn);

        updatePasswordButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                updatePasswordInFirebase();
            }
        });

        validateInput = new ValidateInput(this);
        firebaseAuth = FirebaseAuth.getInstance();
        firebaseUser = firebaseAuth.getCurrentUser();
    }

    private void updatePasswordInFirebase(){
        String password = passwordET.getText().toString();
        final String passwordAgain = passwordAgainET.getText().toString();

        if(validateInput.checkIfPasswordIsValid(passwordAgain))
        {
            if(password.equals(passwordAgain))
            {
                // Update in Firebase
                firebaseUser.updatePassword(passwordAgain).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if(task.isSuccessful())
                        {
                            Toast.makeText(UpdatePasswordActivity.this, "Password updated to: " +passwordAgain, Toast.LENGTH_SHORT).show();
                        }
                        else
                        {
                            Toast.makeText(UpdatePasswordActivity.this, "Error occurred: " +task.getException(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });


            }
            else
            {
                Toast.makeText(this, "Passwords don't match", Toast.LENGTH_SHORT).show();
            }
        }

    }
}