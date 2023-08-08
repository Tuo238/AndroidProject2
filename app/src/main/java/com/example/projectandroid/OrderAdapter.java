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

public class OrderAdapter extends ArrayAdapter<OrderClass> {

    // constructor for our list view adapter.
    public OrderAdapter(@NonNull Context context, ArrayList<OrderClass> orderClassArrayList) {
        super(context, 0, orderClassArrayList);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        // below line is use to inflate the
        // layout for our item of list view.
        View listitemView = convertView;
        if (listitemView == null) {
            listitemView = LayoutInflater.from(getContext()).inflate(R.layout.order_history_layout, parent, false);
        }

        // after inflating an item of listview item
        // we are getting data from array list inside
        // our modal class.
        OrderClass orderClass = getItem(position);

        // initializing our UI components of list view item.
        TextView tvOrderItem = listitemView.findViewById(R.id.tvOrderItem);
        TextView tvOrderPrice = listitemView.findViewById(R.id.tvOrderPrice);

        // after initializing our items we are
        // setting data to our view.
        // below line is use to set data to our text view.
        tvOrderItem.setText(orderClass.getLstFood());
        tvOrderPrice.setText(orderClass.getTotal());



        // below line is use to add item click listener
        // for our item of list view.
        listitemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // on the item click on our list view.
                // we are displaying a toast message.
                Toast.makeText(getContext(), "Item clicked is : " + orderClass.getLstFood(), Toast.LENGTH_SHORT).show();
            }
        });
        return listitemView;
    }
}