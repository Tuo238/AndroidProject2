package com.example.projectandroid;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Filter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;

public class FoodAdapter extends ArrayAdapter{
    Context context;
    ArrayList<Food> arrayList;

    private ArrayList<Food> originalData;
    int layout;
    public FoodAdapter(@NonNull Context context, int resource, @NonNull ArrayList<Food> objects) {
        super(context, resource, objects);
        this.context = context;
        this.arrayList = objects;
        this.layout = resource;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        Food food = arrayList.get(position);
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
        Button btnOrder = convertView.findViewById(R.id.btn_order);
        btnOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Main_menufood Main_menufood = (Main_menufood) context;
                Main_menufood.changeActivity(food.getId(),food.getPath());
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

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                FilterResults filterResults = new FilterResults();
                ArrayList<Food> filteredData = new ArrayList<>();

                if (originalData == null) {
                    originalData = new ArrayList<>(arrayList);
                }

                if (constraint == null || constraint.length() == 0) {
                    filteredData.addAll(originalData);
                } else {
                    String filterPattern = constraint.toString().toLowerCase().trim();
                    for (Food food : originalData) {
                        if (food.getName().toLowerCase().contains(filterPattern)) {
                            filteredData.add(food);
                        }
                    }
                }

                filterResults.values = filteredData;
                filterResults.count = filteredData.size();
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                arrayList.clear();
                arrayList.addAll((ArrayList<Food>) results.values);
                notifyDataSetChanged();
            }
        };
    }

}
