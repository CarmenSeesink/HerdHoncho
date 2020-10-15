package com.example.herdhoncho;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.os.Bundle;
import android.widget.Adapter;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

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
    private TextView farmNameTV;

    ArrayList<Animal> animalArrayList = new ArrayList<>();

    AnimalsAdapter animalsAdapter;
    Context mContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_livestock);

        mContext = this;

        // Get name of farm
        farmNameTV = findViewById(R.id.farm_name);

        // Get name from Firebase
        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();

        DatabaseReference rootReference = firebaseDatabase.getReference();
        DatabaseReference nameReference = rootReference.child("Farms").child(currentUser.getUid()).child("name");

        nameReference.addListenerForSingleValueEvent(new ValueEventListener() {
            // Triggered at least once
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                // DataSnapshot will have = {name="xx"}
                farmNameTV.setText("Livestock of " +dataSnapshot.getValue().toString());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);


        readAnimalsFromFirebase();
    }

    // Get notes from Firebase

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
}