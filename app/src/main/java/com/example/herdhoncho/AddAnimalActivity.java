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

public class AddAnimalActivity extends AppCompatActivity {

    private EditText tagNumber, breed, age, weight, relation;
    private Button addAnimalBtn;
    private AddAnimalActivity mContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_animal);

        tagNumber = findViewById(R.id.tagnumber_ET);
        breed = findViewById(R.id.breed_ET);
        age = findViewById(R.id.age_ET);
        weight = findViewById(R.id.weight_ET);
        relation = findViewById(R.id.relation_ET);

        addAnimalBtn = findViewById(R.id.addAnimal_btn);

        addAnimalBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addAnimalInFirebase();
            }
        });

        mContext = AddAnimalActivity.this;
    }

    private void addAnimalInFirebase() {
        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();

        DatabaseReference rootReference = firebaseDatabase.getReference();
        DatabaseReference animalReference = rootReference.child("Farms").child(currentUser.getUid()).child("Animals");

        DatabaseReference newAnimalReference = animalReference.push();

        // Animal object
        Animal newAnimal = new Animal(tagNumber.getText().toString(), breed.getText().toString(), age.getText().toString(), weight.getText().toString(), relation.getText().toString());

        newAnimalReference.setValue(newAnimal).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful())
                {
                    Toast.makeText(AddAnimalActivity.this, "Animal added to Firebase", Toast.LENGTH_SHORT).show();
                    mContext.finish(); // Finish Activity
                }
                else
                {
                    Toast.makeText(AddAnimalActivity.this, "Error occurred: " +task.getException(), Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}