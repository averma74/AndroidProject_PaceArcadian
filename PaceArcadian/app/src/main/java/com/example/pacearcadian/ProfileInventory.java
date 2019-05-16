package com.example.pacearcadian;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.example.pacearcadian.AccountActivity.UserInformation;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class ProfileInventory extends Activity {

    ArrayList<Items> mItem = new ArrayList<>();
    private InventoryRecyclerViewAdapter mAdapter;
    private final int ACTIVITY_REQUEST_CODE = 1;
    private static String ID = "";
    DatabaseReference mDatabaseReference;
    ArrayList<Items> mFetchedItems;
    //ImageView mProfileImage;
    //TextView mUsername;
    //TextView mRatingTitle, mRating, mFollowerTitle, mFollowerCount, mFollowingTitle, mFollowingCount;
    FloatingActionButton mFloatingButton;
    FloatingActionButton mHomeFloatingButton;
    private FirebaseUser mFirebaseUser;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.profile_inventory);

        initializeViews();
        implementClickListener();

        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        mFirebaseUser = mAuth.getCurrentUser();

        RecyclerView recyclerView = findViewById(R.id.inventoryFeed);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        mDatabaseReference = FirebaseDatabase.getInstance().getReference().child("/inventory/" + mFirebaseUser.getUid()  + "/");

        mDatabaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                mFetchedItems = new ArrayList<Items>();
                if(dataSnapshot.exists()){
                    for(DataSnapshot dataSnapshot1: dataSnapshot.getChildren() ){
                           Items inventoryItems = dataSnapshot1.getValue(Items.class);
                            mFetchedItems.add(inventoryItems);
                    }
                    mAdapter = new InventoryRecyclerViewAdapter(ProfileInventory.this, mFetchedItems);
                    recyclerView.setAdapter(mAdapter);
                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }

    // click listener for the floating action button
    private void implementClickListener() {
        mFloatingButton.setOnClickListener(v -> {
            Intent intent = new Intent(ProfileInventory.this, AddItem.class);
            startActivityForResult(intent, ACTIVITY_REQUEST_CODE);
        });
        mHomeFloatingButton.setOnClickListener(v ->
                startActivity(new Intent(ProfileInventory.this, MainActivity.class)));
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        //get data from second activity

        if (resultCode == RESULT_OK) {
            String title = data.getStringExtra(AddItem.EXTRA_MESSAGE_TITLE);
            String desc = data.getStringExtra(AddItem.EXTRA_MESSAGE_DESCRIPTION);
            String category = data.getStringExtra(AddItem.EXTRA_MESSAGE_CATEGORY);
            //add it to recycler view
            //mItem.add(new Items(title, desc, category, mFirebaseUser.getUid()));

            String key = mDatabaseReference.child("inventory").push().getKey();
            Items inventoryItem = new Items(title, desc, category, mFirebaseUser.getUid());
            Map<String, Object> childUpdates = new HashMap<>();

            childUpdates.put(key, inventoryItem);
            mDatabaseReference.updateChildren(childUpdates);

            //mAdapter.notifyDataSetChanged();
        }
    }

    // initialize all views
    private void initializeViews() {
//        mProfileImage = findViewById(R.id.profile_image);
//        mUsername = findViewById(R.id.username);
//        mRatingTitle = findViewById(R.id.rating_tab);
//        mRating = findViewById(R.id.rating);
//        mFollowerTitle = findViewById(R.id.follower_tab);
//        mFollowerCount = findViewById(R.id.follower_count);
//        mFollowingTitle = findViewById(R.id.following_tab);
//        mFollowingCount = findViewById(R.id.following_count);
        mFloatingButton = findViewById(R.id.fab);
        mHomeFloatingButton = findViewById(R.id.home);
    }
}
