package com.example.projectandroid;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

public class EditProfile extends AppCompatActivity {
    TextView Logout;
    EditText NewEmail, NewPass;
    Button ChEmail, ChPass, ChImag;
    ImageView images, text;
    FirebaseUser user;
    DatabaseReference reference;
    ProgressBar progressBar;
    private static final int PICK_IMAGE_REQUEST = 1;
    FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);
        auth = FirebaseAuth.getInstance();
        user = auth.getCurrentUser();
        reference = FirebaseDatabase.getInstance().getReference().child("users").child(user.getUid());

        addControls();
        addEvents();
    }

    public void addControls() {
        text = (ImageView) findViewById(R.id.text);
        Logout = (TextView) findViewById(R.id.Logout);
        images = (ImageView) findViewById(R.id.images);
        ChImag = (Button) findViewById(R.id.ChImag);
        ChEmail = (Button) findViewById(R.id.ChEmail);
        ChPass = (Button) findViewById(R.id.ChPass);
        NewEmail = (EditText) findViewById(R.id.NewEmail);
        NewPass = (EditText) findViewById(R.id.NewPassword);
    }

    public void addEvents() {

        NewEmail.setText(user.getEmail());
        ChImag.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                chooseAvatarFromGallery();
            }
        });

        ChEmail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = NewEmail.getText().toString().trim();
                FirebaseUser us = FirebaseAuth.getInstance().getCurrentUser();
                if (!email.isEmpty()) {
                    // Thực hiện thay đổi email trong Firebase Authentication
                    us.updateEmail(email).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()) {
                                Toast.makeText(EditProfile.this, "Thay đổi email thành công", Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(EditProfile.this, "Thay đổi email thất bại", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                } else {
                    Toast.makeText(EditProfile.this, "Vui lòng nhập email mới", Toast.LENGTH_SHORT).show();
                }
            }
        });

        ChPass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String newPass = NewPass.getText().toString().trim();
                if (!newPass.isEmpty()) {
                    user.updatePassword(newPass).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()) {
                                Toast.makeText(EditProfile.this, "Thay đổi mật khẩu thành công", Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(EditProfile.this, "Thay đổi mật khẩu thất bại", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                } else {
                    Toast.makeText(EditProfile.this, "Vui lòng nhập mật khẩu mới", Toast.LENGTH_SHORT).show();
                }
            }
        });

        Logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();
                startActivity(new Intent(EditProfile.this,Login.class));
                finish();
            }
        });

        text.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(EditProfile.this,Profile.class));
                finish();
            }
        });
    }

    public void chooseAvatarFromGallery() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Chọn hình ảnh đại diện"), PICK_IMAGE_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {
            Uri imageUri = data.getData();

            // Hiển thị hình ảnh đại diện mới trong ImageView
            images.setImageURI(imageUri);

            // Lưu hình ảnh vào Firebase Storage
            FirebaseStorage storage = FirebaseStorage.getInstance();
            StorageReference storageRef = storage.getReference().child("avatar").child(user.getUid() + ".jpg");

            storageRef.putFile(imageUri).addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {
                    if (task.isSuccessful()) {
                        // Lấy URL của hình ảnh đã lưu trong Firebase Storage
                        storageRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                            @Override
                            public void onSuccess(Uri uri) {
                                String imageUrl = uri.toString();

                                // Lưu URL của hình ảnh vào cơ sở dữ liệu Realtime Database
                                reference.child("avatarUrl").setValue(imageUrl)
                                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                                            @Override
                                            public void onComplete(@NonNull Task<Void> task) {
                                                if (task.isSuccessful()) {
                                                    // Hiển thị hình ảnh đại diện mới từ URL đã lưu trong Database
                                                    Glide.with(EditProfile.this)
                                                            .load(imageUrl)
                                                            .into(images);
                                                    Toast.makeText(EditProfile.this, "Chọn hình ảnh thành công", Toast.LENGTH_SHORT).show();
                                                } else {
                                                    Toast.makeText(EditProfile.this, "Lỗi khi lưu URL hình ảnh vào Database", Toast.LENGTH_SHORT).show();
                                                }
                                            }
                                        });
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(EditProfile.this, "Lỗi khi lấy URL hình ảnh", Toast.LENGTH_SHORT).show();
                            }
                        });
                    } else {
                        Toast.makeText(EditProfile.this, "Lỗi khi lưu hình ảnh", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
    }
}


