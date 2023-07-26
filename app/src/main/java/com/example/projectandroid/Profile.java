package com.example.projectandroid;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;

public class Profile extends AppCompatActivity {

    TextView Fname,Lname, Email, Logout,changeProfile;

    FirebaseUser user;

    DatabaseReference reference;

    ProgressBar progressBar;

    FirebaseAuth auth;

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
//        welcome = (TextView) findViewById(R.id.welcome);


    }

    public void addControls(){
        if (user == null) {
            startActivity(new Intent(Profile.this,Login.class));
            finish();
//            Toast.makeText(Profile.this, "Somthing went wrong!", Toast.LENGTH_SHORT).show();
        } else {

            Email.setText(user.getEmail());

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



    }



}