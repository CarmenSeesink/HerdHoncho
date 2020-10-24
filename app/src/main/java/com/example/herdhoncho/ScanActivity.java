package com.example.herdhoncho;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.palette.graphics.Palette;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.speech.tts.TextToSpeech;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.herdhoncho.Objects.ColorDetector;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.ml.vision.FirebaseVision;
import com.google.firebase.ml.vision.common.FirebaseVisionImage;
import com.google.firebase.ml.vision.text.FirebaseVisionText;
import com.google.firebase.ml.vision.text.FirebaseVisionTextRecognizer;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

public class ScanActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    public static final int REQUEST_CODE = 101;
    public static final int CAMERA_REQUEST_CODE = 102;

    ImageView scanIV;
    EditText tagNumberScanned, yearScanned, weightOutput;
    Button detectBtn;
    SeekBar weightInput;
    Spinner breed;
//    TextView title;

    TextToSpeech textToSpeech;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scan);

        // Assign variables
        scanIV = findViewById(R.id.scan_IV);
        tagNumberScanned = findViewById(R.id.tagNumber_scan);
        detectBtn = findViewById(R.id.detect_btn);
        yearScanned = findViewById(R.id.year);
        weightInput = findViewById(R.id.weightInput);
        weightOutput = findViewById(R.id.weightOutput);
        breed = findViewById(R.id.breed);
//        title = findViewById(R.id.title);

        // Spinner
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.breeds, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        breed.setAdapter(adapter);
        breed.setOnItemSelectedListener(this);


        // Weight seek bar
        weightOutput.setText(weightInput.getProgress() + " kg");
        weightInput.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                weightOutput.setText(progress + " kg");
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                Toast.makeText(ScanActivity.this, "Start", Toast.LENGTH_SHORT ).show();
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                Toast.makeText(ScanActivity.this, "Stop", Toast.LENGTH_SHORT ).show();
            }
        });

        // Init navigation
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);

        // Set current selected
        bottomNavigationView.setSelectedItemId(R.id.scan);

        // Perform
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId()){
                    case R.id.livestock:
                        startActivity(new Intent(getApplicationContext(), LivestockActivity.class));
                        overridePendingTransition(0,0);
                        return true;
                    case R.id.scan:
                        return true;
                    case R.id.profile:
                        startActivity(new Intent(getApplicationContext(), UserAccountActivity.class));
                        overridePendingTransition(0,0);
                        return true;
                    case R.id.add:
                        startActivity(new Intent(getApplicationContext(), AddAnimalActivity.class));
                        overridePendingTransition(0,0);
                        return true;
                }
                return false;
            }
        });

        // Text to speech
        textToSpeech = new TextToSpeech(getApplicationContext(), new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int i) {
                if (i == TextToSpeech.SUCCESS){
                    // Select language
                    int lang = textToSpeech.setLanguage(Locale.ENGLISH);
                }
            }
        });

        detectBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                // Get tag number value
                String s = tagNumberScanned.getText().toString();

                // Text to speech
                int speech = textToSpeech.speak(s, TextToSpeech.QUEUE_FLUSH, null);

            }
        });

        if (checkSelfPermission(Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED){
            // Grant the permission
            requestPermissions(new String[]{Manifest.permission.CAMERA}, 101);
        }
    }

    public void scanBtnStart(View view){
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(intent, 101);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Bundle bundle = data.getExtras();
        //from bundle, extract the image
        Bitmap bitmap = (Bitmap) bundle.get("data");
        //set image in imageview
        scanIV.setImageBitmap(bitmap);
        //process the image
        //1. create a FirebaseVisionImage object from a Bitmap object
        FirebaseVisionImage firebaseVisionImage = FirebaseVisionImage.fromBitmap(bitmap);
        //2. Get an instance of FirebaseVision
        FirebaseVision firebaseVision = FirebaseVision.getInstance();
        //3. Create an instance of FirebaseVisionTextRecognizer
        FirebaseVisionTextRecognizer firebaseVisionTextRecognizer = firebaseVision.getOnDeviceTextRecognizer();
        //4. Create a task to process the image
        Task<FirebaseVisionText> task = firebaseVisionTextRecognizer.processImage(firebaseVisionImage);
        //5. if task is success
        task.addOnSuccessListener(new OnSuccessListener<FirebaseVisionText>() {
            @Override
            public void onSuccess(FirebaseVisionText firebaseVisionText) {
                String s = firebaseVisionText.getText();
                tagNumberScanned.setText(s);
            }
        });
        //6. if task is failure
        task.addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_LONG).show();
            }
        });

        Palette.from(bitmap).generate(new Palette.PaletteAsyncListener() {
            @Override
            public void onGenerated(@NonNull Palette palette) {

                Palette.Swatch swatch = palette.getDominantSwatch();

                if (swatch != null) {

                    int rgb = swatch.getRgb();
                    String rgbText = Integer.toHexString(rgb);

                    Class<?> cls = this.getClass();
                    ColorDetector detector = null;
                    try {
                        detector = new ColorDetector(getAssets().open("colorconfig.json"));
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                    List<com.example.herdhoncho.Objects.Color> returned =  detector.findColor(rgbText.substring(2));

                    if(returned.isEmpty())
                        Log.e("Error", String.format("could not find a value for detected color %s with the integer value %s", rgbText.substring(2), Integer.parseInt(rgbText.substring(2),16)));
//                        System.out.println(String.format("could not find a value for detected color %s with the integer value %s", rgbText, Integer.parseInt(rgbText,16)));
                    else
                        yearScanned.setText(String.valueOf(returned.get(0).getYear()));
                }
            }

        });

    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        String text = parent.getItemAtPosition(position).toString();
        Toast.makeText(parent.getContext(), text, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}