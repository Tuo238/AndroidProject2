package com.example.projectandroid;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.SignInButton;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import java.util.regex.Pattern;

public class Login extends AppCompatActivity {
    FirebaseAuth auth;
    EditText login_email, login_password;
    Button login_button;
    TextView SiginUpText;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        auth=FirebaseAuth.getInstance();
        addEvents();
        addControls();
    }
    private void addEvents(){
        login_email= (EditText) findViewById(R.id.login_email);
        login_password=(EditText) findViewById(R.id.login_password);
        login_button=(Button) findViewById(R.id.login_button);
        SiginUpText=(TextView) findViewById(R.id.SiginUpText);
    }

    private void addControls(){
        login_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = login_email.getText().toString();
                String pass =  login_password.getText().toString();
                if (!email.isEmpty()&& Patterns.EMAIL_ADDRESS.matcher(email).matches()){
                    if (!pass.isEmpty()){
                    auth.signInWithEmailAndPassword(email,pass).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                        @Override
                        public void onSuccess(AuthResult authResult) {
                            Toast.makeText(Login.this,"Login Successful",Toast.LENGTH_SHORT).show();
                            startActivity( new Intent(Login.this,MainActivity.class));
                            finish();
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(Login.this,"Login Failed",Toast.LENGTH_SHORT).show();
                        }
                    });
                } else {
                        login_password.setError("Password cannot be emptu");
                    }
                } else if (email.isEmpty()){
                    login_email.setError("Email cannot be emptu");
                } else {
                    login_email.setError("Email cannot be emptu");
                }

        }
        });
        SiginUpText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Login.this,Signup.class));
            }
        });
    }
}