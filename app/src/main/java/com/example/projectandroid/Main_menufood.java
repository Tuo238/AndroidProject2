package com.example.projectandroid;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class Main_menufood extends AppCompatActivity {

BottomNavigationView bottomNavigationView;
    ListView list;
    Spinner spinner_danhmuc;
    FoodAdapter foodAdapter;
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menufood);
//        setContentView(R.layout.activity_nav_giohang);
//        BottomNavigationView bottomNavigationView = findViewById(R.id.nav_giohang);
//
//        Intent intent = getIntent() ;

        initCategory();
    }


    public void loadData(String type){
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        CollectionReference usersRef = db.collection("/menu/");
        Query query = usersRef;
        query.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    ArrayList<Food> arrayList = new ArrayList<>();
                    List<DocumentSnapshot> documents = task.getResult().getDocuments();
                    if(type.isEmpty())
                    {
                        for (DocumentSnapshot document : documents) {
                            arrayList.add(new Food(document.get("img").toString(),document.get("name").toString(),document.get("mota").toString(),document.get("price").toString(),document.getId()));
                        }
                    }
                    else{
                        for (DocumentSnapshot document : documents) {
                            if(document.getString("type").toString().equals(type))
                            {
                                arrayList.add(new Food(document.get("img").toString(),document.get("name").toString(),document.get("mota").toString(),document.get("price").toString(),document.getId()));
                            }
                        }
                    }
                    list = findViewById(R.id.lstFood);
                    foodAdapter = new FoodAdapter(Main_menufood.this, R.layout.layout_row, arrayList);
                    list.setAdapter(foodAdapter);
                } else {
                    // Xử lý lỗi
                }

            }
        });
    }

    public void loadCategory(ArrayList<String> data){
        spinner_danhmuc=(Spinner) findViewById(R.id.spinner_danhmuc);
        ArrayAdapter<String> adapter=new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item,data);
        spinner_danhmuc.setAdapter(adapter);
        spinner_danhmuc.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if(position == 0)
                {
                    loadData("");
                }
                else{
                    loadData(data.get(position));
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    public void initCategory(){
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        CollectionReference usersRef = db.collection("/menu");
        Query query = usersRef;
        query.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    ArrayList<String> data=new ArrayList<>();
                    List<DocumentSnapshot> documents = task.getResult().getDocuments();
                    data.add("Tất cả thực phẩm");
                    for (DocumentSnapshot document : documents) {
                        if(!data.contains(document.getString("type")))
                        {
                            data.add(document.getString("type"));
                        }
                    }
                    loadCategory(data);
                } else {
                    // Xử lý lỗi
                }
            }
        });
    }
    public void changeIntent(String idFood)
    {
        Intent intent = new Intent(Main_menufood.this,Main_menutopping.class);
        intent.putExtra("idFood",idFood);
        startActivity(intent);
    }

}