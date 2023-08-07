package com.example.projectandroid;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.OutputStream;
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
                String idFood = intent.getStringExtra("idFood");
                // đọc dữ liệu trong cart
//                String oldData = "";
//                try {
//                    InputStream in=openFileInput("dataCart.txt",Main_menutopping.MODE_PRIVATE);
//                    int size=in.available();
//                    byte[]buffer=new byte[size];
//                    in.read(buffer);
//                    in.close();
//                    oldData = new String(buffer);
//                } catch (FileNotFoundException e) {
//                    throw new RuntimeException(e);
//                } catch (IOException e) {
//                    throw new RuntimeException(e);
//                }
                 //ghi thêm dữ liệu trong cart
                String newData = "";
                try {
                    OutputStream op=openFileOutput("dataCart.txt",MODE_APPEND);
                    if(priceTopping == 0)
                    {
                        newData=idFood+"\n";
                    }
                    else{
                        newData=idFood+"-"+String.join(",",lstnameTopping)+"-"+String.valueOf(priceTopping)+"\n";
                    }
                    op.write(newData.getBytes());
                    op.close();
                    Intent changeIntent = new Intent(Main_menutopping.this,Main_menufood.class);
                    startActivity(changeIntent);
                } catch (FileNotFoundException e) {
                    throw new RuntimeException(e);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
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