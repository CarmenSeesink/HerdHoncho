package com.example.herdhoncho;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.palette.graphics.Palette;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.speech.tts.TextToSpeech;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.ml.vision.FirebaseVision;
import com.google.firebase.ml.vision.common.FirebaseVisionImage;
import com.google.firebase.ml.vision.text.FirebaseVisionText;
import com.google.firebase.ml.vision.text.FirebaseVisionTextRecognizer;
import com.squareup.picasso.Picasso;

import java.io.InputStream;
import java.util.HashMap;
import java.util.Locale;

public class ScanActivity extends AppCompatActivity {

    public static final int REQUEST_CODE = 101;
    public static final int CAMERA_REQUEST_CODE = 102;

    ImageView scanIV;
    EditText tagNumberScanned, yearScanned;
    Button detectBtn;

    TextToSpeech textToSpeech;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scan);

        // Assign variables
        scanIV = findViewById(R.id.scan_IV);
        tagNumberScanned = findViewById(R.id.tagNumber_scan);
        detectBtn = findViewById(R.id.detect_btn);
//        colorExtract = findViewById(R.id.mainColor);
        yearScanned = findViewById(R.id.breed);

        // Palette API

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
//                    int titleTextColor = swatch.getTitleTextColor();
                    String rgbText = Integer.toHexString(rgb);

                    yearScanned.setBackgroundColor(rgb);
//                    yearScanned.setTextColor(titleTextColor);
                    yearScanned.setText(rgbText);

                }
            }

        });

    }

//    public static int getDominantColor(Bitmap bitmap) {
//        Bitmap newBitmap = Bitmap.createScaledBitmap(bitmap, 1, 1, true);
//        final int color = newBitmap.getPixel(0, 0);
//        newBitmap.recycle();
//        return color;
//    }

//    // Generate palette synchronously and return it
//    public Palette createPaletteSync(Bitmap bitmap) {
//        Palette palette = Palette.from(bitmap).generate();
//        return palette;
//    }
//
//    // Generate palette
//    public void paletteGenerator() {
//        // Use generated instance
//        BitmapDrawable drawable = (BitmapDrawable) scanIV.getDrawable();
//        Bitmap bitmapColor = drawable.getBitmap();
//
//        Palette palette = createPaletteSync(bitmapColor);
//        Palette.Swatch dominantSwatch = palette.getDominantSwatch();
//        Palette.Swatch vibrantSwatch = palette.getVibrantSwatch();
//        if(dominantSwatch != null)
//        {
//            int color = dominantSwatch.getRgb();
//            colorExtract.setBackgroundColor(color);
//        }
//        else if (vibrantSwatch != null)
//        {
//            int color = dominantSwatch.getRgb();
//            colorExtract.setBackgroundColor(color);
//        }
//        else
//        {
//            colorExtract.setBackgroundColor(Color.YELLOW);
//        }
//    }


}