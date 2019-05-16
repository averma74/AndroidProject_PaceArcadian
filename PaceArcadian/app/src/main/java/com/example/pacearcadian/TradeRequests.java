package com.example.pacearcadian;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import java.util.ArrayList;

public class TradeRequests extends AppCompatActivity {

    private TradeRequestRecyclerViewAdapter mAdapter;
    private ArrayList<TradeRequestItem> mFetchedItems = new ArrayList<>();
    private TextView mEmptyTradeRequests;
    FloatingActionButton mHomeFloatingButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trade_requests);

        mEmptyTradeRequests = findViewById(R.id.no_trade_request);
        mHomeFloatingButton = findViewById(R.id.home);

        RecyclerView recyclerView = findViewById(R.id.tradeRequestFeed);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        mFetchedItems.add(new TradeRequestItem("blah","blah","OTHER",
                "test", "blah","blah","OTHER", "test"));

        if(mFetchedItems.size() == 0){
            mEmptyTradeRequests.setVisibility(View.VISIBLE);
        }
        mAdapter = new TradeRequestRecyclerViewAdapter(TradeRequests.this, mFetchedItems);
        recyclerView.setAdapter(mAdapter);

        buttonClickListeners();
    }

    private void buttonClickListeners() {
        mHomeFloatingButton.setOnClickListener(v ->
                startActivity(new Intent(TradeRequests.this, MainActivity.class)));

    }
}
