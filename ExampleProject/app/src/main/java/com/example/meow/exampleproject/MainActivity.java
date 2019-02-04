package com.example.meow.exampleproject;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.*;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private FirebaseAuth auth;

    private DatabaseReference db;
    private DatabaseReference entries;

    private RecyclerView rView;

    ArrayList<Entry> e = new ArrayList<>();
    ArrayList<String> uids =  new ArrayList<>();

    RecyclerViewAdapter rAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(MainActivity.this, AddItemActivity.class);
                startActivity(i);
            }
        });

        rView = findViewById(R.id.rView);

        auth = FirebaseAuth.getInstance();

        db = FirebaseDatabase.getInstance().getReference();
        entries = db.child("entries");

        setUpRecycler();

        entries.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                e.clear();
                uids.clear();
                for (DataSnapshot ds: dataSnapshot.getChildren()) {
                    uids.add(ds.getKey());
                    e.add(ds.getValue(Entry.class));
                }
                rAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {}
        });


    }

    private void setUpRecycler() {
        rAdapter = new RecyclerViewAdapter(this, e, uids);
        rView.setAdapter(rAdapter);
        rView.setLayoutManager(new LinearLayoutManager(this));
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }
}
