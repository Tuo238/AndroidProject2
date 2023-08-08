package com.example.projectandroid;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Main_paymet_update extends AppCompatActivity {
    Button btnchangesaddress;
    ListView list;
    item_foodAdapter_update foodAdapter;
    ImageButton btn_paymethod;
    ImageView imgPayment;
    TextView result, tvkq, tvGiaTien, tvGiaShip, tvGiaKhac, tvTongThanhToan;
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    private String myData;
    final ArrayList<item_food_update> arrayList = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_paymet);

        addControls();
        addEvents();
        loadData();

    }
    public void addControls()
    {
        btnchangesaddress = findViewById(R.id.btn_change_address);
        tvGiaTien =findViewById(R.id.tvGiaTien);
        tvGiaShip = findViewById(R.id.tvGiaShip);
        tvGiaKhac=findViewById(R.id.tvGiaKhac);
        result=findViewById(R.id.result);
        tvkq=findViewById(R.id.tvkq);
        tvTongThanhToan=findViewById(R.id.tvTongtien);
        list = findViewById(R.id.order_payment);
    }

    public void addEvents()
    {
        imgPayment=findViewById(R.id.imgPayment);
        btn_paymethod = findViewById(R.id.img_btn_paymenthod);

        btn_paymethod.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialog();
            }
        });

        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(Main_paymet_update.this, "ga", Toast.LENGTH_LONG).show();
                //Toast.makeText(MainActivity.this, String.valueOf(position)+String.valueOf(id), Toast.LENGTH_SHORT).show();
            }
        });
        btnchangesaddress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent myintent = new Intent(Main_paymet_update.this, ggmap_update.class );
                startActivity(myintent);
            }
        });

        findViewById(R.id.btn_dathang).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ArrayList<String> lstnamefood = new ArrayList<>();
                TextView tvTongtien = findViewById(R.id.tvTongtien);
                int totalPrice = Integer.parseInt(tvTongtien.getText().toString());
                for(int i=0;i<list.getCount();i++)
                {
                    item_food_update itemfood = (item_food_update) list.getItemAtPosition(i);
                    lstnamefood.add(itemfood.getName_food());
                }
                // ghi lên database
                Map<String, Object> bill = new HashMap<>();
                bill.put("lstFood", String.join(",",lstnamefood));
                bill.put("total", String.valueOf(totalPrice));
                db.collection("order")
                        .add(bill)
                        .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                            @Override
                            public void onSuccess(DocumentReference documentReference) {
                                Toast.makeText(Main_paymet_update.this, "dat mon thanh cong", Toast.LENGTH_SHORT).show();
                                try {
                                    OutputStream op=openFileOutput("dataCart.txt",MODE_PRIVATE);
                                    op.write("".getBytes());
                                    op.close();
                                } catch (FileNotFoundException e) {
                                    throw new RuntimeException(e);
                                } catch (IOException e) {
                                    throw new RuntimeException(e);
                                }
                                Intent changeIntent = new Intent(Main_paymet_update.this,MainActivity.class);
                                startActivity(changeIntent);
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(Main_paymet_update.this, "dat mon that bai", Toast.LENGTH_SHORT).show();
                            }
                        });
            }
        });
    }

//    public void loadData()
//    {
//        CollectionReference usersRef = db.collection("/Category/MilkTea/lstMilkTea");
//        Query query = usersRef;
//        query.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
//            @Override
//            public void onComplete(@NonNull Task<QuerySnapshot> task) {
//                if (task.isSuccessful()) {
//                    int giatien = 0;
//                    List<DocumentSnapshot> documents = task.getResult().getDocuments();
//                    for (DocumentSnapshot document : documents) {
//                        arrayList.add(new item_food(Math.toIntExact(document.getLong("price")),document.get("name").toString(),document.getString("img")));
//                        giatien = giatien + Math.toIntExact(document.getLong("price"));
//                    }
//                    tvGiaTien.setText(String.valueOf(giatien));
//                    tvTongThanhToan.setText(String.valueOf(giatien+16000+3000));
//                    foodAdapter = new item_foodAdapter(Main_paymet_update.this, R.layout.layout_item_drink, arrayList);
//                    list.setAdapter(foodAdapter);
//                } else {
//                    // Xử lý lỗi
//                }
//            }
//        });
//    }

    public void loadData()
    {
        CollectionReference usersRef = db.collection("/menu");
        Query query = usersRef;
        query.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    ArrayList<String> lstIDFood = new ArrayList<>();
                    try {
                        InputStream in=openFileInput("dataCart.txt");
                        int size=in.available();
                        byte[]buffer=new byte[size];
                        in.read(buffer);
                        in.close();
                        String[] st = new String(buffer).split("\n");
                        for(int i=0;i<st.length;i++)
                        {
                            String idFood = st[i].split("-")[0];
                            lstIDFood.add(idFood);
                        }
                    } catch (FileNotFoundException e) {
                        throw new RuntimeException(e);
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                    int giatien = 0;
                    List<DocumentSnapshot> documents = task.getResult().getDocuments();
                    for (DocumentSnapshot document : documents) {
                        if(lstIDFood.contains(document.getId()))
                        {
                            arrayList.add(new item_food_update(Math.toIntExact(document.getLong("price")),document.get("name").toString(),document.getString("img")));
                            giatien = giatien + Math.toIntExact(document.getLong("price"));
                        }
                    }
                    tvGiaTien.setText(String.valueOf(giatien));
                    tvTongThanhToan.setText(String.valueOf(giatien+16000+3000));
                    foodAdapter = new item_foodAdapter_update(Main_paymet_update.this, R.layout.layout_item_drink, arrayList);
                    list.setAdapter(foodAdapter);
                } else {
                    // Xử lý lỗi
                }
            }
        });
    }

    public void loadPayment(int giatien,int check)
    {
        if(check==1) // nghĩa là tăng
        {
            int newPrice = Integer.parseInt(tvGiaTien.getText().toString()) + giatien;
            tvGiaTien.setText(String.valueOf(newPrice));
            tvTongThanhToan.setText(String.valueOf(newPrice+16000+3000));

        }
        else{ // nghĩa là giảm
            int newPrice = Integer.parseInt(tvGiaTien.getText().toString()) - giatien;
            tvGiaTien.setText(String.valueOf(newPrice));
            tvTongThanhToan.setText(String.valueOf(newPrice+16000+3000));
        }

    }


    private void showDialog (){
        final Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.layout_paymethod);

        // bottom sheet
        LinearLayout payment_momo =dialog.findViewById(R.id.layout_payment_momo);
        LinearLayout payment_different_momo = dialog.findViewById(R.id.layout_paymenthod_money);
        LinearLayout payment_note = dialog.findViewById(R.id.note_paymenthod);
        //check box

        payment_momo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imgPayment.setImageResource(R.drawable.momo);
                tvkq.setText("Ví momo");
                result.setText("");
                dialog.cancel();
            }
        });
        payment_different_momo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                result.setText("");
                tvkq.setText("Tiền mặt");
                imgPayment.setImageResource(R.drawable.money_paymenthod2);
                dialog.cancel();
            }
        });
        payment_note.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        dialog.show();
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.WRAP_CONTENT);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.getWindow().getAttributes().windowAnimations= R.style.DialoAnimation;
        dialog.getWindow().setGravity(Gravity.BOTTOM);
    }
}