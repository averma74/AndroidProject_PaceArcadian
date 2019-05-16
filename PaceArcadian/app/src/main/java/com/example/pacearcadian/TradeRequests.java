package com.example.pacearcadian;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.TextView;

import java.util.ArrayList;

public class TradeRequests extends AppCompatActivity {

    private TradeRequestRecyclerViewAdapter mAdapter;
    ArrayList<TradeRequestItem> mFetchedItems = new ArrayList<>();
    TextView mEmptyTradeRequests;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trade_requests);
        //mEmptyTradeRequests = findViewById(R.id.no_trade_request);

        RecyclerView recyclerView = findViewById(R.id.tradeRequestFeed);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        mFetchedItems.add(new TradeRequestItem("blah","blah","OTHER",
                "test", "blah","blah","OTHER", "test"));

        mAdapter = new TradeRequestRecyclerViewAdapter(TradeRequests.this, mFetchedItems);
        recyclerView.setAdapter(mAdapter);

    }
}
