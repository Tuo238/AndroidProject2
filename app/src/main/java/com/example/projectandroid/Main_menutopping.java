package com.example.projectandroid;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

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

public class Main_menutopping extends AppCompatActivity {
    ListView lvtopping;
    Button btnupdate, btntang, btngiam;
    TextView tvsoluong;
    ToppingAdapter toppingAdapter;
    int quantity = 1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menutopping);
        addControlls();
        addEvent();
        loadTopping();
    }
    public  void  addControlls(){
        lvtopping = findViewById(R.id.lv_topping);
        btngiam = findViewById(R.id.btn_giamsoluong);
        btntang = findViewById(R.id.btn_tangsoluong);
        btnupdate = findViewById(R.id.btn_capnhap);
        tvsoluong = findViewById(R.id.tv_soluong);
    }
    //nut tang giam so luong
    public void addEvent() {
        btntang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                quantity++;
                tvsoluong.setText(String.valueOf(quantity));
            }
        });
        btngiam.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (quantity > 1) {
                    quantity--;
                    tvsoluong.setText(String.valueOf(quantity));

                }
            };
        });
        btnupdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ArrayList<String> lstnameTopping = new ArrayList<>();
                int priceTopping = 0;
                for (int i = 0; i < lvtopping.getCount(); i++) {
                    View view = lvtopping.getChildAt(i);
                    if (view != null) {
                        CheckBox checkBox = view.findViewById(R.id.cB_topping);
                        if (checkBox.isChecked()) {
                            TextView tvNameTopping = view.findViewById(R.id.tv_name_topping);
                            TextView tvMoenyTopping = view.findViewById(R.id.tv_money);
                            lstnameTopping.add(tvNameTopping.getText().toString());
                            priceTopping = priceTopping + Integer.parseInt(tvMoenyTopping.getText().toString());
                        }
                    }
                }
                Intent intent = getIntent();
                cartData cartItem = new cartData(intent.getStringExtra("idFood"),intent.getStringExtra("pathFood"),String.join(", ",lstnameTopping),String.valueOf(priceTopping));
                //DataHolder.getInstance().setData(cartItem);
                Toast.makeText(Main_menutopping.this, cartItem.getLstnameTopping(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void loadTopping()
    {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        CollectionReference usersRef = db.collection("/Category/Topping/lstTopping");
        Query query = usersRef;
        query.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    ArrayList<Topping> arrayList = new ArrayList<>();
                    List<DocumentSnapshot> documents = task.getResult().getDocuments();
                    for (DocumentSnapshot document : documents) {
                        arrayList.add(new Topping(document.get("name").toString(), Math.toIntExact(document.getLong("price"))));
                    }
                    toppingAdapter = new ToppingAdapter(Main_menutopping.this, R.layout.layout_item_topping, arrayList);
                    lvtopping.setAdapter(toppingAdapter);
                } else {
                    // Xử lý lỗi
                }

            }
        });
    }
}