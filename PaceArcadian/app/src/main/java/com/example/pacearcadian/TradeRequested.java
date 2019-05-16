package com.example.pacearcadian;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.app.Activity;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class TradeRequested extends AppCompatActivity {
    ArrayList<TradeRequest> mItem = new ArrayList<>();
    FloatingActionButton mHomeFloatingButton;

    DatabaseReference mDatabaseReference;
    private FirebaseUser mFirebaseUser;
    RecyclerView recyclerView;
    private TradeRequestedRecyclerViewAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.trade_requested);
        RecyclerView recyclerView = findViewById(R.id.tradeRequested);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        mAdapter = new TradeRequestedRecyclerViewAdapter(TradeRequested.this, mItem);
        recyclerView.setAdapter(mAdapter);

        mDatabaseReference = FirebaseDatabase.getInstance().getReference().child("/request/");
        mDatabaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists()){
                    for(DataSnapshot dataSnapshot1: dataSnapshot.getChildren() ){
                        TradeRequest inventoryItems = dataSnapshot1.getValue(TradeRequest.class);
                        mItem.add(inventoryItems);
                    }
                    mAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        initializeViews();
        implementClickListener();

    }
    private void implementClickListener() {
        mHomeFloatingButton.setOnClickListener(v ->
                startActivity(new Intent(TradeRequested.this, MainActivity.class)));
    }


    private void initializeViews() {
//        mProfileImage = findViewById(R.id.profile_image);
//        mUsername = findViewById(R.id.username);
//        mRatingTitle = findViewById(R.id.rating_tab);
//        mRating = findViewById(R.id.rating);
//        mFollowerTitle = findViewById(R.id.follower_tab);
//        mFollowerCount = findViewById(R.id.follower_count);
//        mFollowingTitle = findViewById(R.id.following_tab);
//        mFollowingCount = findViewById(R.id.following_count);

        mHomeFloatingButton = findViewById(R.id.home);

    }
}
