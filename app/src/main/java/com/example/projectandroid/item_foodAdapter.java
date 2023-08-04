package com.example.projectandroid;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;

public class item_foodAdapter extends ArrayAdapter {
    Context context;
    ArrayList<item_food> arrayList;
    int layout;


    public item_foodAdapter(@NonNull Context context, int resource, @NonNull ArrayList<item_food> objects) {
        super(context, resource, objects);
        this.context = context;
        this.arrayList = objects;
        this.layout = resource;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        item_food itemFood = arrayList.get(position);
        if(convertView == null){
            convertView = LayoutInflater.from(context).inflate(layout,null);
        }
        ImageView imageView = convertView.findViewById(R.id.imageView);
        getImg(itemFood.getImg_food(),imageView);
        TextView tv_name = convertView.findViewById(R.id.tv_name);
        tv_name.setText(itemFood.getName_food());
        TextView tv_money = convertView.findViewById(R.id.tv_money);
        tv_money.setText(String.valueOf(itemFood.getMoney()));

        Button btn_tang = convertView.findViewById(R.id.btn_tang);
        Button btn_giam = convertView.findViewById(R.id.btn_giam);
        TextView tv_soluong = convertView.findViewById(R.id.tv_soluong);
        btn_tang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int newValue = Integer.parseInt(tv_soluong.getText().toString())+1;
                if(newValue >0)
                {
                    tv_soluong.setText(String.valueOf(newValue));
                    int newPrice = itemFood.getMoney()*Integer.parseInt(tv_soluong.getText().toString());
                    tv_money.setText(String.valueOf(newPrice));
                    Main_paymet main_paymet = (Main_paymet) context;
                    main_paymet.loadPayment(itemFood.getMoney(),1);
                }
            }
        });
        btn_giam.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int newValue = Integer.parseInt(tv_soluong.getText().toString())-1;
                if(newValue >0)
                {
                    tv_soluong.setText(String.valueOf(newValue));
                    int newPrice = itemFood.getMoney()*Integer.parseInt(tv_soluong.getText().toString());
                    tv_money.setText(String.valueOf(newPrice));
                    Main_paymet main_paymet = (Main_paymet) context;
                    main_paymet.loadPayment(itemFood.getMoney(),0);
                }
            }
        });
        return convertView;
    }

    public void getImg(String url,ImageView imgTest)
    {
        FirebaseStorage storage = FirebaseStorage.getInstance();
        StorageReference storageRef = storage.getReference();
        StorageReference imageRef = storageRef.child(url);
        final long MAX_SIZE = 1024 * 1024; // maximum file size in bytes
        imageRef.getBytes(MAX_SIZE).addOnSuccessListener(new OnSuccessListener<byte[]>() {
            @Override
            public void onSuccess(byte[] bytes) {
                Bitmap bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
                imgTest.setImageBitmap(bitmap);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
            }
        });
    }
}
