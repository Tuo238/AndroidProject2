package com.example.projectandroid;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;

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

public class khuyenmaiActivity_update extends AppCompatActivity {

    ListView list;
    ArrayList<khuyenmai_update> arrayList;
    khuyenmaiAdapter_update khuyenmaiAdapter_update;
    ImageButton btn1;
    Button btn2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_khuyenmai_update);
        list = findViewById(R.id.lv_khuyenmai);
        loadData();
    }

    public void loadData()
    {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        CollectionReference usersRef = db.collection("/KhuyenMai");
        Query query = usersRef;
        query.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    List<DocumentSnapshot> documents = task.getResult().getDocuments();
                    ArrayList<khuyenmai_update> arrayListKM = new ArrayList<>();
                    for (DocumentSnapshot document : documents) {
                        String makm = document.getString("maKM");
                        //document.getString("imgKM")
                        arrayListKM.add(new khuyenmai_update(document.getString("imgKM"), makm));
                    }
                    arrayList = arrayListKM;
                    khuyenmaiAdapter_update = new khuyenmaiAdapter_update(khuyenmaiActivity_update.this, R.layout.layout_khuyen_mai_update, arrayList);
                    list.setAdapter(khuyenmaiAdapter_update);
                } else {
                    // Xử lý lỗi
                }
            }
        });
        findViewById(R.id.btn_NhapKM).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(khuyenmaiActivity_update.this,NhapKM_update.class);
                startActivity(intent);
            }
        });
    }

    public void changeIntent(String makhuyenmai)
    {
        Intent intent = new Intent(khuyenmaiActivity_update.this,NhapKM_update.class);
        intent.putExtra("maKM",makhuyenmai);
        startActivity(intent);
    }
}