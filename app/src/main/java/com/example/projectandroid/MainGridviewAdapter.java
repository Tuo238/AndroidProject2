package com.example.projectandroid;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class MainGridviewAdapter extends ArrayAdapter<MainGridviewClass> {

    // constructor for our list view adapter.
    public MainGridviewAdapter(@NonNull Context context, ArrayList<MainGridviewClass> mainGridviewClassArrayList) {
        super(context, 0, mainGridviewClassArrayList);
    }

    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent){
        // below line is use to inflate the
        // layout for our item of list view.
        View listitemView = convertView;
        if (listitemView == null) {
            listitemView = LayoutInflater.from(getContext()).inflate(R.layout.main_category_layout, parent, false);
        }

        // after inflating an item of listview item
        // we are getting data from array list inside
        // our modal class.
        MainGridviewClass mainGridviewClass = getItem(position);

        // initializing our UI components of list view item.
        TextView tvCategory = listitemView.findViewById(R.id.tvCategory);
        ImageView imgCategory = listitemView.findViewById(R.id.imgCategory);

        // after initializing our items we are
        // setting data to our view.
        // below line is use to set data to our text view.
        tvCategory.setText(mainGridviewClass.getName());

        // in below line we are using Picasso to load image
        // from URL in our Image VIew.
        Picasso.get().load(mainGridviewClass.getImgUrl()).into(imgCategory);

        // below line is use to add item
        // click listener for our item of list view.
//        listitemView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                // on the item click on our list view.
//                // we are displaying a toast message.
//                Toast.makeText(getContext(), "Item clicked is : " + mainGridviewClass.getName(), Toast.LENGTH_SHORT).show();
//            }
//        });
        return listitemView;
    }}