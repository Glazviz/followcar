package com.example.meow.exampleproject;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.*;

import java.util.ArrayList;
import java.util.Locale;

public class ItemActivity extends AppCompatActivity {

    private FirebaseAuth auth;

    private DatabaseReference db;
    private DatabaseReference reserves;

    ArrayList<String> res = new ArrayList<>();

    String entryUid;
    String fromCity;
    String toCity;
    String phoneNumber;
    String additionalInfo;
    float cost;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item);

        Intent i = getIntent();
        entryUid = i.getStringExtra("uid");
        fromCity = i.getStringExtra("from");
        toCity = i.getStringExtra("to");
        additionalInfo = i.getStringExtra("info");
        phoneNumber = i.getStringExtra("phone");
        cost = i.getFloatExtra("cost", 0);

        auth = FirebaseAuth.getInstance();

        db = FirebaseDatabase.getInstance().getReference();
        reserves = db.child("reserves");

        reserves.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                res.clear();
                DataSnapshot ds = dataSnapshot.child(entryUid);
                for (DataSnapshot v : ds.child("users").getChildren()) {
                    String userUid = v.getValue(String.class);
                    res.add(userUid);
                }
                updateData();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });

        updateData();
    }

    void onReserveClicked(View view) {
        if (!(res.contains(auth.getCurrentUser().getUid())))
            res.add(auth.getCurrentUser().getUid());
        reserves.child(entryUid).child("users").setValue(res);
    }

    private void updateData() {
        TextView from = findViewById(R.id.fromCity);
        TextView to = findViewById(R.id.toCity);
        TextView phone = findViewById(R.id.phone);
        TextView info = findViewById(R.id.info);
        TextView costView = findViewById(R.id.cost);

        float c = cost;

        if (res.size() != 0) {
            c = c / res.size();
        }

        from.setText(fromCity);
        to.setText(toCity);
        phone.setText(phoneNumber);
        info.setText(additionalInfo);
        costView.setText(String.format(Locale.US, "%f per person", c));
    }
}
