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

public class UpdateEmailActivity extends AppCompatActivity {

    private EditText oldEmailET, newEmailET;
    private Button updateEmailBtn;
    private ValidateInput validateInput;

    FirebaseAuth firebaseAuth;
    FirebaseUser firebaseUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_email);

        oldEmailET = findViewById(R.id.oldEmail_ET);
        newEmailET = findViewById(R.id.newEmail_ET);

        updateEmailBtn = findViewById(R.id.updateEmail_btn);
        updateEmailBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                updateEmailInFirebase();
            }
        });

        validateInput = new ValidateInput(this);

        firebaseAuth = FirebaseAuth.getInstance();
        firebaseUser = firebaseAuth.getCurrentUser();
    }

    private void updateEmailInFirebase(){
        // Update email
        String oldEmail = oldEmailET.getText().toString();
        final String newEmail = newEmailET.getText().toString();

        if(validateInput.checkIfEmailIsValid(newEmail))
        {
            if(oldEmail.equals(newEmail))
            {
                Toast.makeText(this, "New email can't be the same as old one", Toast.LENGTH_SHORT).show();

            }
            else
            {
                firebaseUser.updateEmail(newEmail).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if(task.isSuccessful())
                        {
                            Toast.makeText(UpdateEmailActivity.this, "Email updated successfully: " +newEmail, Toast.LENGTH_SHORT).show();
                        }
                        else
                        {
                            Toast.makeText(UpdateEmailActivity.this, "Error occurred: " +task.getException(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });

            }
        }

    }
}