package com.example.meow.exampleproject;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;
import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import de.hdodenhof.circleimageview.CircleImageView;

import java.io.ByteArrayOutputStream;

public class RegisterActivity extends AppCompatActivity {

    private static final int RESULT_LOAD_IMAGE = 75;

    private Bitmap profilePicture;

    private FirebaseAuth auth;

    private DatabaseReference db;
    private DatabaseReference users;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        auth = FirebaseAuth.getInstance();

        db = FirebaseDatabase.getInstance().getReference();
        users = db.child("users");
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case RESULT_LOAD_IMAGE: {
                    Uri selectedImage = data.getData();
                    String[] filePathColumn = { MediaStore.Images.Media.DATA };

                    Cursor cursor = getContentResolver().query(selectedImage, filePathColumn, null, null, null, null);
                    cursor.moveToFirst();

                    int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                    String picturePath = cursor.getString(columnIndex);
                    cursor.close();

                    profilePicture = BitmapFactory.decodeFile(picturePath);

                    CircleImageView image = findViewById(R.id.image);

                    Glide.with(this)
                            .asBitmap()
                            .load(profilePicture)
                            .into(image);
                }
            }
        }
    }

    public void selectImage(View view) {
        Intent i = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(i, RESULT_LOAD_IMAGE);
    }

    public void registerUser(View view) {
        EditText e = findViewById(R.id.email);
        EditText p = findViewById(R.id.password);

        String email = e.getText().toString();
        String password = p.getText().toString();

        auth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    FirebaseUser u = auth.getCurrentUser();

                    ByteArrayOutputStream os = new ByteArrayOutputStream();
                    profilePicture.compress(Bitmap.CompressFormat.PNG, 100, os);

                    users.child(u.getUid()).child("picture").setValue(Base64.encodeToString(os.toByteArray(), Base64.DEFAULT));

                    finish();
                } else {
                    Toast.makeText(RegisterActivity.this, "Unable to create user", Toast.LENGTH_SHORT).show();
                    Log.d("task:Not Successful", task.getException().getMessage());
                }
            }
        });
    }
}
