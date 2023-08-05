package com.example.projectandroid;

import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Main_phanquyen extends AppCompatActivity {
ActivityResultLauncher <String[]> mPermissionResultLauncher;
private  boolean isReadPermissionGranted = false;
    private  boolean islocationPermissionGranted = false;
    private  boolean isRecordPerminsionGrated= false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_phanquyen);
        mPermissionResultLauncher = registerForActivityResult(new ActivityResultContracts.RequestMultiplePermissions(), new ActivityResultCallback<Map<String, Boolean>>() {
            @Override
            public void onActivityResult(Map<String, Boolean> result) {

                if (result.get(Manifest.permission.ACCESS_FINE_LOCATION) != null) {
                    islocationPermissionGranted = result.get(Manifest.permission.ACCESS_FINE_LOCATION);
                }

                if (result.get(Manifest.permission.READ_EXTERNAL_STORAGE) != null) {
                    isReadPermissionGranted = result.get(Manifest.permission.READ_EXTERNAL_STORAGE);
                }

                if (result.get(Manifest.permission.RECORD_AUDIO) != null) {
                    isRecordPerminsionGrated = result.get(Manifest.permission.RECORD_AUDIO);
                }


            }
        });
    }
    private  void requestPermission(){
        islocationPermissionGranted = ContextCompat.checkSelfPermission(
                this, Manifest.permission.ACCESS_FINE_LOCATION
        ) == PackageManager.PERMISSION_GRANTED;

        isReadPermissionGranted = ContextCompat.checkSelfPermission(
                this, Manifest.permission.READ_EXTERNAL_STORAGE
        ) == PackageManager.PERMISSION_GRANTED;

        isRecordPerminsionGrated = ContextCompat.checkSelfPermission(
                this, Manifest.permission.RECORD_AUDIO
        ) == PackageManager.PERMISSION_GRANTED;

        List<String> permissionRequest = new ArrayList<>();
        if (!islocationPermissionGranted) {
            permissionRequest.add(Manifest.permission.ACCESS_FINE_LOCATION);
        }
        if (!isReadPermissionGranted) {
            permissionRequest.add(Manifest.permission.READ_EXTERNAL_STORAGE);
        }
        if (!isRecordPerminsionGrated) {
            permissionRequest.add(Manifest.permission.RECORD_AUDIO);
        }
        if (!permissionRequest.isEmpty()) {
            mPermissionResultLauncher.launch(permissionRequest.toArray(new String[0]));
        }

    }
}