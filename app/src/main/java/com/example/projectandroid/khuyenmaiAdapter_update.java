package com.example.projectandroid;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;

public class khuyenmaiAdapter_update extends ArrayAdapter {

    Context context;
    ArrayList<khuyenmai_update> arrayList;
    int layout;

    public khuyenmaiAdapter_update(@NonNull Context context, int resource, @NonNull ArrayList<khuyenmai_update> objects) {
        super(context, resource, objects);
        this.context = context;
        this.arrayList = objects;
        this.layout = resource;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        khuyenmai_update km = arrayList.get(position);
        if(convertView == null){
            convertView = LayoutInflater.from(context).inflate(layout,null);
        }
        ImageButton imgBtnKM = convertView.findViewById(R.id.imgBtnKM);
        //imgBtnKM.setImageResource(R.drawable.ic_launcher_background);
        getImg(km.getImgKhuyenMai(),imgBtnKM);
        Button btn_Nhap_1_KM = convertView.findViewById(R.id.btn_Nhap_1_KM);
        //btn_Nhap_1_KM.setText(km.getMaKhuyenMai());

        imgBtnKM.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                khuyenmaiActivity khuyenmaiActivity = (khuyenmaiActivity) context;
                khuyenmaiActivity.changeIntent(km.getMaKhuyenMai());
            }
        });

        btn_Nhap_1_KM.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                khuyenmaiActivity khuyenmaiActivity = (khuyenmaiActivity) context;
                khuyenmaiActivity.changeIntent(km.getMaKhuyenMai());
            }
        });
        return convertView;
    }

    public void getImg(String url,ImageButton imgBtn)
    {
        FirebaseStorage storage = FirebaseStorage.getInstance();
        StorageReference storageRef = storage.getReference();
        StorageReference imageRef = storageRef.child(url);
        final long MAX_SIZE = 1024 * 1024; // maximum file size in bytes
        imageRef.getBytes(MAX_SIZE).addOnSuccessListener(new OnSuccessListener<byte[]>() {
            @Override
            public void onSuccess(byte[] bytes) {
                Bitmap bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
                imgBtn.setImageBitmap(bitmap);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
            }
        });
    }
}
