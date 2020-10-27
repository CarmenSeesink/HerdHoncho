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
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;

public class EmployeeActivity extends AppCompatActivity {
    private EditText employeeName, employeeNumber, employeeLocation;
    private Button saveEmployeeBtn;
    private EmployeeActivity mContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_employee);

        employeeName  = findViewById(R.id.employeeName_ET);
        employeeNumber  = findViewById(R.id.employeeNumber_ET);
        employeeLocation = findViewById(R.id.employeeLocation_ET);

        saveEmployeeBtn = findViewById(R.id.saveEmployee_btn);

        saveEmployeeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                putEmployeeInFirebase();
            }
        });

        mContext = EmployeeActivity.this;
    }

    // Employee in Firebase
    private void putEmployeeInFirebase() {

        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();

        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();

        DatabaseReference rootReference= firebaseDatabase.getReference(); //reference to database-root here.

        DatabaseReference employeesReference = rootReference.child("Farms").child(currentUser.getUid()).child("Employees"); // root/Users/{currentUserID}/Notes

        DatabaseReference newEmployeeReference = employeesReference.push();

        Employee newEmployee = new Employee(employeeName.getText().toString(), employeeNumber.getText().toString(), employeeLocation.getText().toString());

        newEmployeeReference.setValue(newEmployee).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful())
                {
                    Toast.makeText(EmployeeActivity.this, "Note submitted in Firebase.", Toast.LENGTH_SHORT).show();
                    mContext.finish(); //finish this activity.
                }
                else
                {
                    Toast.makeText(EmployeeActivity.this, "Some error occurred :  "+task.getException(), Toast.LENGTH_SHORT).show();
                }
            }
        });


    }
}