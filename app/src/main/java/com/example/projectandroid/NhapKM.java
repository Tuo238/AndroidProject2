package com.example.projectandroid;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

public class NhapKM extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nhap_km);
        Intent intent = getIntent();
        EditText edtText = findViewById(R.id.editTextText);
        edtText.setText(intent.getStringExtra("maKM"));
        edtText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(NhapKM.this,khuyenmaiActivity.class);
                startActivity(intent);
            }
        });
    }
}