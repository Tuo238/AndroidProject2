package com.example.projectandroid;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class NhapKM_update extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nhap_km);
        Intent intent = getIntent();
        EditText edtText = findViewById(R.id.editTextText);
        edtText.setText(intent.getStringExtra("maKM"));
        findViewById(R.id.button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(NhapKM_update.this, "Áp dụng khuyến mãi thành công", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(NhapKM_update.this,khuyenmaiActivity_update.class);
                startActivity(intent);
            }
        });
    }
}