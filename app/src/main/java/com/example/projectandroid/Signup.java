package com.example.projectandroid;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class Signup extends AppCompatActivity {
    FirebaseAuth auth;
    EditText siginup_email, siginup_password1, siginup_password2;
    Button signup_button;
    TextView LoginText;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        auth=FirebaseAuth.getInstance();
         addEvents();
         addControls();
    }

    private void addEvents(){
        siginup_email= (EditText) findViewById(R.id.siginup_email);
        siginup_password1=(EditText) findViewById(R.id.siginup_password1);
        siginup_password2= (EditText) findViewById(R.id.siginup_password2);
        signup_button=(Button) findViewById(R.id.signup_button);
        LoginText=(TextView) findViewById(R.id.LoginText);
    }

    private void addControls(){
        signup_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String user = siginup_email.getText().toString().trim();
                String pass = siginup_password1.getText().toString().trim();
                String pass2 = siginup_password2.getText().toString().trim();
                if (user.isEmpty()){
                    siginup_email.setError("Email cannot be empty");
                    return;
                }

                if (pass.isEmpty()){
                    siginup_password1.setError("Password cannot be empty");
                    return;
                }

                if (pass2.isEmpty()){
                    siginup_password2.setError("Please confirm your password");
                    return;
                }

                if (!pass.equals(pass2)) {
                    siginup_password2.setError("Passwords do not match");
                    return;
                }


                if (pass.length() < 8 || pass.length() > 16) {
                    siginup_password1.setError("Password must be between 8 and 16 characters");
                    return;
                }

                if (!pass.matches("^(?=.*[A-Z])(?=.*\\d)(?=.*[@#$%^&+.=]).*$")) {
                    siginup_password1.setError("Password must contain at least one uppercase letter, one digit, and one special character.");
                    return;
                }

                else {
                    auth.createUserWithEmailAndPassword(user,pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()){
                                Toast.makeText(Signup.this,"SignUp Successful",Toast.LENGTH_SHORT).show();
                                startActivity(new Intent(Signup.this,Login.class));
                            } else {
                                Toast.makeText(Signup.this,"SignUp Failed "+task.getException().getMessage(),Toast.LENGTH_SHORT).show();

                            }
                        }
                    });
                }
            }
        });

        LoginText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Signup.this, Login.class));
            }
        });
    }

}