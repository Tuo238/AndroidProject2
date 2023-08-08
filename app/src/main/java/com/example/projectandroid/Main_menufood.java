package com.example.projectandroid;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.Spinner;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class Main_menufood extends AppCompatActivity {

    Button btnorder;
    ListView list;
    Spinner spinner_danhmuc;

    SearchView svMenu;
    FoodAdapter foodAdapter;
    ArrayList<Food> filteredList;
    FirebaseFirestore db = FirebaseFirestore.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menufood);
        initCategory();

        filteredList = new ArrayList<>();
        SearchView svMenu = findViewById(R.id.svMenu);
        svMenu.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                if (foodAdapter != null) {
                    foodAdapter.getFilter().filter(newText);
                }
                return true;            }
        });
    }

//    private void filter(String searchText) {
//        filteredList.clear();
//        for (Food food : filteredList) {
//            if (food.getName().toLowerCase().contains(searchText.toLowerCase())) {
//                filteredList.add(food);
//            }
//        }
//        // Update the ListView with the filtered list.
//        foodAdapter = new FoodAdapter(Main_menufood.this, R.layout.layout_row, filteredList);
//        list.setAdapter(foodAdapter);
//    }

    public void changeActivity(String id,String path)
    {
        Intent intent = new Intent(Main_menufood.this,Main_menutopping.class);
        intent.putExtra("idFood",id);
        intent.putExtra("pathFood",path);
        startActivity(intent);
    }

    public void loadData(String param1,String param2){

        FirebaseFirestore db = FirebaseFirestore.getInstance();
        CollectionReference usersRef = db.collection("/Category/"+param1+"/"+param2);
        Query query = usersRef;
        query.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    ArrayList<Food> arrayList = new ArrayList<>();
                    List<DocumentSnapshot> documents = task.getResult().getDocuments();
                    for (DocumentSnapshot document : documents) {
                        arrayList.add(new Food(document.get("img").toString(),document.get("name").toString(),document.get("mota").toString(),document.get("price").toString(),"/Category/"+param1+"/"+param2));
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
                loadData(data.get(position),"lst"+data.get(position));
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        Intent intent = getIntent();
        String categoryname = intent.getStringExtra("category");
        int selectedPosition = data.indexOf(categoryname);
        spinner_danhmuc.setSelection(selectedPosition);

    }

    public void initCategory(){
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        CollectionReference usersRef = db.collection("/Category");
        Query query = usersRef;
        query.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    ArrayList<String> data=new ArrayList<>();
                    List<DocumentSnapshot> documents = task.getResult().getDocuments();
                    for (DocumentSnapshot document : documents) {
                        if(!(document.getId().equals("Topping")))
                        {
                            data.add(document.getId());
                        }
                    }
                    loadCategory(data);
                } else {
                    // Xử lý lỗi
                }
            }
        });
    }
}