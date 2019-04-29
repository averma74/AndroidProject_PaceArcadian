package com.example.pacearcadian;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;

public class ProfileInventory extends Activity {

    ArrayList<Items> mItem = new ArrayList<>();
    private InventoryRecyclerViewAdapter mAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.profile_inventory);

        RecyclerView recyclerView = findViewById(R.id.inventoryFeed);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        mAdapter = new InventoryRecyclerViewAdapter(this, mItem);
        recyclerView.setAdapter(mAdapter);
    }
}
