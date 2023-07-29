package com.example.projectandroid;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.GridView;
import android.widget.Toast;

import com.denzcoskun.imageslider.ImageSlider;
import com.denzcoskun.imageslider.constants.ScaleTypes;
import com.denzcoskun.imageslider.models.SlideModel;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class MainActivity extends AppCompatActivity {
    GridView gvMain;
    ArrayList<MainGridviewClass> mainGridviewClassArrayList;
    FirebaseFirestore db;

    //Navigation
    BottomNavigationView bottom_navigation;

    //
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ImageSlider imgSlider = findViewById(R.id.imgSlider);
        ArrayList<SlideModel> slideModels = new ArrayList<>();

        slideModels.add(new SlideModel(R.drawable.american_food, ScaleTypes.FIT));
        slideModels.add(new SlideModel(R.drawable.chinese_food, ScaleTypes.FIT));
        slideModels.add(new SlideModel(R.drawable.thai_food, ScaleTypes.FIT));

        imgSlider.setImageList(slideModels, ScaleTypes.FIT);
        //=========Xử lý GridView=======

        // below line is use to initialize our variables.
        gvMain = findViewById(R.id.gvMain);
        mainGridviewClassArrayList = new ArrayList<>();

        // initializing our variable for firebase
        // firestore and getting its instance.
        db = FirebaseFirestore.getInstance();

        // here we are calling a method
        // to load data in our list view.
        loadDatainGridView();


        //=========================
        // Initialize and assign variable
        bottom_navigation=findViewById(R.id.bottom_navigation);

        // Set Home selected
        bottom_navigation.setSelectedItemId(R.id.home);

        // Perform item selected listener
        bottom_navigation.setOnItemSelectedListener(new BottomNavigationView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int itemId = item.getItemId();
                if (itemId == R.id.test) {
                    startActivity(new Intent(getApplicationContext(), TestActivity.class));
                    overridePendingTransition(0, 0);
                    return true;
                } else if (itemId == R.id.home) {
                    // Your code for handling the "test" item selected
                    return true;
                } else if (itemId == R.id.test2) {
                    startActivity(new Intent(getApplicationContext(), Test2.class));
                    overridePendingTransition(0, 0);
                    return true;
                }
                return false;
            }
        });

        //==========================
    }

    private void loadDatainGridView() {
        // below line is use to get data from Firebase
        // firestore using collection in android.
        db.collection("Category").get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        // after getting the data we are calling on success method
                        // and inside this method we are checking if the received
                        // query snapshot is empty or not.
                        if (!queryDocumentSnapshots.isEmpty()) {
                            // if the snapshot is not empty we are hiding our
                            // progress bar and adding our data in a list.
                            List<DocumentSnapshot> list = queryDocumentSnapshots.getDocuments();
                            for (DocumentSnapshot d : list) {

                                // after getting this list we are passing
                                // that list to our object class.
                                MainGridviewClass mainGridviewClass = d.toObject(MainGridviewClass.class);

                                // after getting data from Firebase
                                // we are storing that data in our array list
                                mainGridviewClassArrayList.add(mainGridviewClass);
                            }
                            // after that we are passing our array list to our adapter class.
                            MainGridviewAdapter adapter = new MainGridviewAdapter(MainActivity.this, mainGridviewClassArrayList);

                            // after passing this array list
                            // to our adapter class we are setting
                            // our adapter to our list view.
                            gvMain.setAdapter(adapter);
                        } else {
                            // if the snapshot is empty we are displaying a toast message.
                            Toast.makeText(MainActivity.this, "No data found in Database", Toast.LENGTH_SHORT).show();
                        }
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        // we are displaying a toast message
                        // when we get any error from Firebase.
                        Toast.makeText(MainActivity.this, "Fail to load data..", Toast.LENGTH_SHORT).show();
                    }
                });
    }
}