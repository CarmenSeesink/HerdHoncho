package com.example.herdhoncho;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class EditAnimalActivity extends AppCompatActivity {

    TextView tagNumberET, breedET, weightET, yearET;
    Button editBtn;
    String tagNumber, breed, weight, year, animalID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_animal);

        tagNumberET = findViewById(R.id.tagNumber_ET);
        breedET = findViewById(R.id.breed_ET);
        weightET = findViewById(R.id.weight_ET);
        yearET = findViewById(R.id.year_ET);
        editBtn = findViewById(R.id.edit_btn);

        if(getIntent().getExtras() != null)
        {
            tagNumber = getIntent().getStringExtra("tagNumber");
            breed = getIntent().getStringExtra("breed");
            weight = getIntent().getStringExtra("weight");
            year = getIntent().getStringExtra("year");
            animalID = getIntent().getStringExtra("animalID");

            tagNumberET.setText(tagNumber);
            breedET.setText(breed);
            weightET.setText(weight);
            yearET.setText(year);
        }

        editBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editAnimalInFirebase();
            }
        });
    }

    private void editAnimalInFirebase() {
        // Edit method according to ID
        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();

        DatabaseReference rootReference = firebaseDatabase.getReference();

        DatabaseReference animalReference = rootReference.child("Farms").child(currentUser.getUid()).child("Animals");

        DatabaseReference specificAnimalReference = animalReference.child(animalID);

        specificAnimalReference.child("tagNumber").setValue(tagNumberET.getText().toString());
        specificAnimalReference.child("breed").setValue(breedET.getText().toString());
        specificAnimalReference.child("weight").setValue(weightET.getText().toString());
        specificAnimalReference.child("year").setValue(yearET.getText().toString()).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {

                if(task.isSuccessful())
                {
                    //note updates successfully.
                    Toast.makeText(EditAnimalActivity.this, "Animal updated successfully!", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    Toast.makeText(EditAnimalActivity.this, "Some error occurred!", Toast.LENGTH_SHORT).show();
                }


            }
        });

        // Open LivestockActivity
        Intent editAnimalIntent = new Intent(EditAnimalActivity.this, LivestockActivity.class);
        startActivity(editAnimalIntent);

    }
}