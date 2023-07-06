package com.example.projectandroid;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.denzcoskun.imageslider.ImageSlider;
import com.denzcoskun.imageslider.constants.ScaleTypes;
import com.denzcoskun.imageslider.models.SlideModel;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

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
    }
}