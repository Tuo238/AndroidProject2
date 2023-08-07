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

public class FoodAdapter_update extends ArrayAdapter{
    Context context;
    ArrayList<Food_update> arrayList;
    int layout;
    public FoodAdapter_update(@NonNull Context context, int resource, @NonNull ArrayList<Food_update> objects) {
        super(context, resource, objects);
        this.context = context;
        this.arrayList = objects;
        this.layout = resource;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        Food_update food = arrayList.get(position);
        if(convertView == null){
            convertView = LayoutInflater.from(context).inflate(layout,null);
        }
        ImageView img = convertView.findViewById(R.id.imageFood);
        getImg(food.getImg(),img);

        TextView txt1 = convertView.findViewById(R.id.nameFood);
        txt1.setText(food.getName());
        TextView txt2 = convertView.findViewById(R.id.motaFood);
        txt2.setText(food.getMota());
        TextView txt3 = convertView.findViewById(R.id.saleFood);
        txt3.setText(food.getSale()+"đ");
        Button btnorther = convertView.findViewById(R.id.btn_order);
        btnorther.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Main_menufood_update Main_menufood_update = (Main_menufood_update) context;
                Main_menufood_update.changeIntent(food.getId().toString());
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
