package com.example.pacearcadian;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.pacearcadian.AccountActivity.AddItem;

import java.util.ArrayList;

public class ProfileInventory extends Activity {

    ArrayList<Items> mItem = new ArrayList<>();
    private InventoryRecyclerViewAdapter mAdapter;
    private final int ACTIVITY_REQUEST_CODE = 1;

    ImageView mProfileImage;
    TextView mUsername;
    TextView mRatingTitle, mRating, mFollowerTitle, mFollowerCount, mFollowingTitle, mFollowingCount;
    FloatingActionButton mFloatingButton;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.profile_inventory);

        initializeViews();
        implementClickListener();

        RecyclerView recyclerView = findViewById(R.id.inventoryFeed);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        mAdapter = new InventoryRecyclerViewAdapter(this, mItem);
        recyclerView.setAdapter(mAdapter);

        addData();
    }

    private void addData() {
        mItem.add(new Items("Movie Ticket - Endgame", "Endgame tickets available for 5/3/2019"));
        mItem.add(new Items("Book", "Cracking the coding interview"));
        mItem.add(new Items("Shoes", "Red shoes"));
        mItem.add(new Items("Notes for Android", "Kachi's class notes"));
        mAdapter.notifyDataSetChanged();
    }

    // click listener for the floating action button
    private void implementClickListener() {
        mFloatingButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ProfileInventory.this, AddItem.class);
                startActivityForResult(intent, ACTIVITY_REQUEST_CODE);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        //get data from second activity
        String title = data.getStringExtra(AddItem.EXTRA_MESSAGE_TITLE);
        String desc = data.getStringExtra(AddItem.EXTRA_MESSAGE_DESCRIPTION);
        if (resultCode == RESULT_OK) {
            //add it to recycler view
            mItem.add(new Items(title, desc));
            mAdapter.notifyDataSetChanged();
        }
    }

    // initialize all views
    private void initializeViews() {
        mProfileImage = findViewById(R.id.profile_image);
        mUsername = findViewById(R.id.username);
        mRatingTitle = findViewById(R.id.rating_tab);
        mRating = findViewById(R.id.rating);
        mFollowerTitle = findViewById(R.id.follower_tab);
        mFollowerCount = findViewById(R.id.follower_count);
        mFollowingTitle = findViewById(R.id.following_tab);
        mFollowingCount = findViewById(R.id.following_count);
        mFloatingButton = findViewById(R.id.fab);
    }
}
