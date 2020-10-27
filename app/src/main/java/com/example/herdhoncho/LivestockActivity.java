package com.example.herdhoncho;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Adapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class LivestockActivity extends AppCompatActivity {

    private RecyclerView recyclerView;

    ArrayList<Animal> animalArrayList = new ArrayList<>();

    AnimalsAdapter animalsAdapter;
    Context mContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_livestock);

        // Init navigation
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);

        // Set current selected
        bottomNavigationView.setSelectedItemId(R.id.livestock);

        // Perform
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId()){
                    case R.id.scan:
                        startActivity(new Intent(getApplicationContext(), HomeActivity.class));
                        overridePendingTransition(0,0);
                        return true;
                    case R.id.livestock:
                        return true;
                    case R.id.profile:
                        startActivity(new Intent(getApplicationContext(), UserAccountActivity.class));
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

        mContext = this;

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);

        readAnimalsFromFirebase();

    }

    // Get animals from Firebase
    private void readAnimalsFromFirebase(){
        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();

        DatabaseReference animalsReference = firebaseDatabase.getReference().child("Farms").child(currentUser.getUid()).child("Animals");

        animalsReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                animalArrayList.clear();
                Animal animal;
                for(DataSnapshot animalSnapShot: dataSnapshot.getChildren())
                {
                   animal = animalSnapShot.getValue(Animal.class);

                   animal.setAnimalID(animalSnapShot.getKey());

                   Toast.makeText(LivestockActivity.this, "animal tagNumber" +animal.getTagNumber(), Toast.LENGTH_SHORT).show();

                   // Add animal to ArrayList
                    animalArrayList.add(animal);
                }
                // Layout
                animalsAdapter = new AnimalsAdapter(animalArrayList, mContext);
                recyclerView.setAdapter(animalsAdapter);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(LivestockActivity.this, "Some error occurred.", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void deleteAnimalFromFirebase(String animalID){
        // Delete

        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();

        DatabaseReference animalReference = firebaseDatabase.getReference().child("Farms").child(currentUser.getUid()).child("Animals");

        DatabaseReference specificAnimalReference = animalReference.child(animalID);

        specificAnimalReference.removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {

                if(task.isSuccessful())
                {
                    Toast.makeText(mContext, "Animal is deleted, successfully!", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    Toast.makeText(mContext, "Error occurred!", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}