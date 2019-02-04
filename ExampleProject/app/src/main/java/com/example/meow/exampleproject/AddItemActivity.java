package com.example.meow.exampleproject;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.UUID;

public class AddItemActivity extends AppCompatActivity {

    private FirebaseAuth auth;

    private DatabaseReference db;
    private DatabaseReference entries;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_item);

        auth = FirebaseAuth.getInstance();

        db = FirebaseDatabase.getInstance().getReference();
        entries = db.child("entries");
    }

    void onAddClicked(View view) {
        EditText from = findViewById(R.id.fromCity);
        EditText to = findViewById(R.id.toCity);
        EditText phone = findViewById(R.id.phoneNumber);
        EditText info = findViewById(R.id.info);
        EditText cost = findViewById(R.id.cost);

        if (!(from.getText().toString().isEmpty() &&
                to.getText().toString().isEmpty() &&
                phone.getText().toString().isEmpty() &&
                info.getText().toString().isEmpty() &&
                cost.getText().toString().isEmpty())) {
            entries.child(UUID.randomUUID().toString())
                    .setValue(
                            new Entry(
                                    from.getText().toString(),
                                    to.getText().toString(),
                                    info.getText().toString(),
                                    phone.getText().toString(),
                                    Float.parseFloat(cost.getText().toString())
                            )
                    );
            finish();
        }
    }
}
