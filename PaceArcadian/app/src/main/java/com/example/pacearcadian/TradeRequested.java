package com.example.pacearcadian;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class TradeRequested extends AppCompatActivity {

    DatabaseReference mDatabase;
    String ID = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trade_requests);
        mDatabase = FirebaseDatabase.getInstance().getReference();
        ID = FirebaseAuth.getInstance().getUid();
        checkForRequests();
    }

    private void checkForRequests() {
        mDatabase.child("request").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot ds: dataSnapshot.getChildren()) {
                    String currID = ds.child("currentUserID").getValue(String.class);
                    if (currID == ID) {

                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
}
