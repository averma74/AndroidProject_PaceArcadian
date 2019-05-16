package com.example.pacearcadian;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class TradeRequests extends AppCompatActivity {

    private TradeRequestRecyclerViewAdapter mAdapter;
    private ArrayList<TradeRequest> mItems = new ArrayList<>();
    private TextView mEmptyTradeRequests;
    FloatingActionButton mHomeFloatingButton;
    DatabaseReference mDatabase;
    RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trade_requests);

        mEmptyTradeRequests = findViewById(R.id.no_trade_request);
        mHomeFloatingButton = findViewById(R.id.home);

        recyclerView = findViewById(R.id.tradeRequestFeed);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        mDatabase = FirebaseDatabase.getInstance().getReference().child("/request/");
        //mFetchedItems.add(new TradeRequest("blah","blah","OTHER","test", "blah","blah","OTHER", "test"));

        //mItems.add(new TradeRequest("ID-1", "Tickets", "Endgame tickets", "ID-2", "Book", "Cracking the coding interview hardcopy"));

        if(mItems.size() == 0){
            mEmptyTradeRequests.setVisibility(View.VISIBLE);
        }
        mAdapter = new TradeRequestRecyclerViewAdapter(TradeRequests.this, mItems);
        recyclerView.setAdapter(mAdapter);

        buttonClickListeners();
        fetchFromDatabase();
    }

    private void fetchFromDatabase() {
        mDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                //if(dataSnapshot.exists()){
                    //for(DataSnapshot dataSnapshot1: dataSnapshot.getChildren() ){
                        for(DataSnapshot ds:dataSnapshot.getChildren()){
                            TradeRequest tradeItems = ds.getValue(TradeRequest.class);
                            //if(inventoryItems.getmUserId()!=mFirebaseUser.getUid()){
                            mItems.add(tradeItems);
                            //}
                        }
                    //}
                    mAdapter = new TradeRequestRecyclerViewAdapter(TradeRequests.this, mItems);
                    recyclerView.setAdapter(mAdapter);
                //}
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    private void buttonClickListeners() {
        mHomeFloatingButton.setOnClickListener(v ->
                startActivity(new Intent(TradeRequests.this, MainActivity.class)));

    }
}
