package com.example.pacearcadian;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;

public class TradeInventory extends Activity {
    ArrayList<TradeItems> mItem = new ArrayList<>();
    private TradeFeedRecyclerViewAdapter mAdapter;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.trade_feed);
        RecyclerView recyclerView = findViewById(R.id.tradeFeeds);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        mAdapter = new TradeFeedRecyclerViewAdapter(this, mItem);
        recyclerView.setAdapter(mAdapter);
        addData();
    }
    private void addData() {
        mItem.add(new TradeItems("Movie Ticket - Endgame","kulkarni.rohan619@gmail.com"));
        mItem.add(new TradeItems("Book", "sanchita@gmail.com"));
        mItem.add(new TradeItems("Shoes", "aditee@gmail.com"));

        mAdapter.notifyDataSetChanged();
    }
}
