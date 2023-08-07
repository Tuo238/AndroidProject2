package com.example.projectandroid;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Profile extends AppCompatActivity {

    TextView Fname,Lname, Email, Logout,changeProfile;

    ImageView images;

    FirebaseUser user;

    DatabaseReference reference;

    ProgressBar progressBar;

    FirebaseAuth auth;

    private boolean isActivityDestroyed = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        auth= FirebaseAuth.getInstance();
        user = auth.getCurrentUser();

        addEvents();
        addControls();
    }

    public void addEvents(){
        Fname = (TextView) findViewById(R.id.Fname);
        Lname = (TextView) findViewById(R.id.Lname);
        Email = (TextView) findViewById(R.id.Email);
        Logout = (TextView) findViewById(R.id.Logout);
        changeProfile = (TextView) findViewById(R.id.changeProfile);
        images = (ImageView) findViewById(R.id.images);


    }

    public void addControls(){
        if (user == null) {
            startActivity(new Intent(Profile.this,Login.class));
            finish();
//            Toast.makeText(Profile.this, "Somthing went wrong!", Toast.LENGTH_SHORT).show();
        } else {

            Email.setText(user.getEmail());
            Fname.setText("Mật khẩu: Đã ẩn vì lý do bảo mật");


//            progressBar.setVisibility(View.VISIBLE);
//            showProfile(user);
        }

        Logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();
                startActivity(new Intent(Profile.this,Login.class));
                finish();
            }
        });

        changeProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Profile.this,EditProfile.class));
                finish();
            }
        });

        DatabaseReference profileRef = FirebaseDatabase.getInstance().getReference().child("users").child(user.getUid()).child("avatarUrl");
        profileRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String avatarUrl = dataSnapshot.getValue(String.class);
                if (!isActivityDestroyed) {
                    if (avatarUrl != null) {
                        // Hiển thị hình ảnh đại diện từ URL đã lưu trong Database
                        Glide.with(Profile.this)
                                .load(avatarUrl)
                                .into(images);
                    } else {
                        // Hiển thị ảnh mặc định nếu không có URL trong Database
                        images.setImageResource(R.drawable.images); // Thay thế R.drawable.images bằng ảnh mặc định của bạn
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // Xử lý lỗi nếu cần
            }
        });

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        isActivityDestroyed = true;

    }
}