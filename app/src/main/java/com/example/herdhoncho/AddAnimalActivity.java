package com.example.herdhoncho;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.StorageTask;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

public class AddAnimalActivity extends AppCompatActivity {
    private static final int PICK_IMAGE_REQUEST = 1;

    private EditText tagNumber, breed, age, weight, relation;
    private Button addAnimalBtn;
    private AddAnimalActivity mContext;
    private Button uploadImageBtn;
    private ImageView uploadIV;
    private ProgressBar progressBar;

    private Uri ImageUri;

    private StorageTask uploadTask;
    private StorageReference storageReference;
    private DatabaseReference databaseReference;
    private FirebaseUser currentUser;
    private FirebaseDatabase firebaseDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_animal);

        // Set variables
        tagNumber = findViewById(R.id.tagnumber_ET);
        breed = findViewById(R.id.breed_ET);
        age = findViewById(R.id.age_ET);
        weight = findViewById(R.id.weight_ET);
        relation = findViewById(R.id.relation_ET);

        addAnimalBtn = findViewById(R.id.addAnimal_btn);

        uploadImageBtn = findViewById(R.id.imageUpload_btn);
        uploadIV = findViewById(R.id.imageUpload_IV);
        progressBar = findViewById(R.id.progress_bar);

        // Get Firebase instance
        currentUser = FirebaseAuth.getInstance().getCurrentUser();
        databaseReference = FirebaseDatabase.getInstance().getReference().child("Farms").child(currentUser.getUid()).child("Animals");
        storageReference = FirebaseStorage.getInstance().getReference("Uploads");

        // Upload image
        uploadImageBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openFileChooser();
            }
        });

        // Add to Firebase
        addAnimalBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (uploadTask != null && uploadTask.isInProgress()) {
                    Toast.makeText(AddAnimalActivity.this, "Upload in progress", Toast.LENGTH_SHORT).show();
                } else {
                    uploadFile();
                }
            }
        });

        mContext = AddAnimalActivity.this;
    }

    // Upload image
    private void openFileChooser(){
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent, PICK_IMAGE_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {
            ImageUri = data.getData();

            Picasso.with(this).load(ImageUri).into(uploadIV);
        }
    }

    // Uri extension
    private String getFileExtension(Uri uri) {
        ContentResolver cR = getContentResolver();
        MimeTypeMap mime = MimeTypeMap.getSingleton();
        return mime.getExtensionFromMimeType(cR.getType(uri));
    }

    // OLD CODE
//    private void addAnimalInFirebase() {
//        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
//        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
//
//        DatabaseReference rootReference = firebaseDatabase.getReference();
//        DatabaseReference animalReference = rootReference.child("Farms").child(currentUser.getUid()).child("Animals");
//        DatabaseReference newAnimalReference = animalReference.push();
//
//        // Animal object
////        Animal newAnimal = new Animal(tagNumber.getText().toString(), breed.getText().toString(), age.getText().toString(), weight.getText().toString(), relation.getText().toString());
//
//        newAnimalReference.setValue(newAnimal).addOnCompleteListener(new OnCompleteListener<Void>() {
//            @Override
//            public void onComplete(@NonNull Task<Void> task) {
//                if(task.isSuccessful())
//                {
//                    Toast.makeText(AddAnimalActivity.this, "Animal added to Firebase", Toast.LENGTH_SHORT).show();
//                    mContext.finish(); // Finish Activity
//                }
//                else
//                {
//                    Toast.makeText(AddAnimalActivity.this, "Error occurred: " +task.getException(), Toast.LENGTH_SHORT).show();
//                }
//            }
//        });
//    }

    // Upload file to Firebase
        private void uploadFile() {
            if (ImageUri != null) {
                storageReference.putFile(ImageUri).continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
                    @Override
                    public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception {
                        if (!task.isSuccessful()) {
                            throw task.getException();
                        }
                        return storageReference.getDownloadUrl();
                    }
                }).addOnCompleteListener(new OnCompleteListener<Uri>() {
                    @Override
                    public void onComplete(@NonNull Task<Uri> task) {
                        if (task.isSuccessful()) {
                            Uri downloadUri = task.getResult();
                            Animal newAnimal = new Animal(tagNumber.getText().toString(), breed.getText().toString(), age.getText().toString(),
                                        weight.getText().toString(), downloadUri.toString());
                            String animalId = databaseReference.push().getKey();
                                databaseReference.child(animalId).setValue(newAnimal);
                                openLivestockActivity();
                        } else {
                            Toast.makeText(AddAnimalActivity.this, "upload failed: " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            } else {
                Toast.makeText(this, "No file selected", Toast.LENGTH_SHORT).show();
            }
        }

    // Navigate to livestock
    private void openLivestockActivity() {
        Intent intent = new Intent(this, LivestockActivity.class);
        startActivity(intent);
    }
}