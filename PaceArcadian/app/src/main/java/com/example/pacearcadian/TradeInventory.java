package com.example.pacearcadian;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.example.pacearcadian.AccountActivity.AddItem;

import java.util.ArrayList;

public class TradeInventory extends Activity {
    ArrayList<TradeItems> mItem = new ArrayList<>();
    private TradeFeedRecyclerViewAdapter mAdapter;
    FloatingActionButton mHomeFloatingButton;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.trade_feed);

        mHomeFloatingButton = findViewById(R.id.home);

        RecyclerView recyclerView = findViewById(R.id.tradeFeeds);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        mAdapter = new TradeFeedRecyclerViewAdapter(this, mItem);
        recyclerView.setAdapter(mAdapter);

        mHomeFloatingButton.setOnClickListener(v -> {
            startActivity(new Intent(TradeInventory.this, MainActivity.class));
        });

        addData();
    }
    private void addData() {
        mItem.add(new TradeItems("Movie Ticket - Endgame","kulkarni.rohan619@gmail.com"));
        mItem.add(new TradeItems("Book", "sanchita@gmail.com"));
        mItem.add(new TradeItems("Shoes", "aditee@gmail.com"));

        mAdapter.notifyDataSetChanged();
    }

}
