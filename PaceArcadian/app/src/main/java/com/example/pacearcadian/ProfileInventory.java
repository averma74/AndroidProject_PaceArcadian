package com.example.pacearcadian;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class ProfileInventory extends Activity {

    ArrayList<Items> mItem = new ArrayList<>();
    private InventoryRecyclerViewAdapter mAdapter;
    private final int ACTIVITY_REQUEST_CODE = 1;
    private static String ID = "";
    DatabaseReference mDatabaseReference;

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

        final FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        //Log.i("debug", user.getEmail());
        ID = user.getUid();

        //final FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        //Log.i("debug", user.getEmail());
        //mUsername.setText(user.getDisplayName());
        RecyclerView recyclerView = findViewById(R.id.inventoryFeed);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        mAdapter = new InventoryRecyclerViewAdapter(this, mItem);
        recyclerView.setAdapter(mAdapter);
        addData();
    }

    private void addData() {

        //Retrieve from DB & put in mItem

//
//        mItem.add(new Items("Movie Ticket - Endgame", "Endgame tickets available for 5/3/2019", "APPARELS", "test"));
//        mItem.add(new Items("Book", "Cracking the coding interview", "BOOKS", "test"));
//        mItem.add(new Items("Shoes", "Red shoes", "EATABLES", "test"));
//        mItem.add(new Items("Notes for Android", "Kachi's class notes", "ELECTRONICS", "test"));
//        mItem.add(new Items("Movie Ticket - Endgame", "Endgame tickets available for 5/3/2019", "FASHION ACCESSORIES", "test"));
//        mItem.add(new Items("Book", "Cracking the coding interview", "FURNITURE", "test"));
//        mItem.add(new Items("Shoes", "Red shoes", "MEDIA", "test"));
//        mItem.add(new Items("Notes for Android", "Kachi's class notes", "SHOES", "test"));
//        mItem.add(new Items("Movie Ticket - Endgame", "Endgame tickets available for 5/3/2019", "SPORTS", "test"));
//        mItem.add(new Items("Book", "Cracking the coding interview", "TICKETS", "test"));
//        mItem.add(new Items("Shoes", "Red shoes", "OTHER", "test"));
        mAdapter.notifyDataSetChanged();
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
            mItem.add(new Items(title, desc, category, mFirebaseUser.getUid()));
            mDatabaseReference = FirebaseDatabase.getInstance().getReference().child("inventory");
            mDatabaseReference.child(ID).setValue(mItem);
            //mDatabaseReference.push().child("description").setValue(desc);

            //Add mItem to DB


            mAdapter.notifyDataSetChanged();
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
