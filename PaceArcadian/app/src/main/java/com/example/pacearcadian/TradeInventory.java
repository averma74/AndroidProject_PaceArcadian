package com.example.pacearcadian;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

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

        addData();
        buttonClickListeners();
    }

    private void buttonClickListeners() {
        mHomeFloatingButton.setOnClickListener(v ->
                startActivity(new Intent(TradeInventory.this, MainActivity.class)));

    }

    private void addData() {

        //get data from db put into mItem
        mItem.add(new TradeItems("Movie Ticket - Endgame", "Endgame tickets available for 5/3/2019", "APPARELS", "test"));
        mItem.add(new TradeItems("Book", "Cracking the coding interview", "BOOKS", "test"));
        mItem.add(new TradeItems("Shoes", "Red shoes", "EATABLES", "test"));


        mAdapter.notifyDataSetChanged();
    }

}
