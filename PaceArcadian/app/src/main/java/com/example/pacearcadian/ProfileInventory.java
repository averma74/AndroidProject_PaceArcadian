package com.example.pacearcadian;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class ProfileInventory extends Activity {

    ArrayList<Items> mItem = new ArrayList<>();
    private InventoryRecyclerViewAdapter mAdapter;

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
    }

    // click listener for the floating action button
    private void implementClickListener() {
        mFloatingButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showPopupFragment();
            }
        });
    }

    // opens a popup dialog that asks for title, description, image
    private void showPopupFragment() {
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
