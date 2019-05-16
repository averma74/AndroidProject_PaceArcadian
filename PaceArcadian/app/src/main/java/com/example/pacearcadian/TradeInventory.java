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
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class TradeInventory extends Activity {
    ArrayList<TradeItems> mItem = new ArrayList<>();
    private TradeFeedRecyclerViewAdapter mAdapter;
    FloatingActionButton mHomeFloatingButton;
    DatabaseReference mDatabaseReference;
    private FirebaseUser mFirebaseUser;
    RecyclerView recyclerView;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.trade_feed);

        mHomeFloatingButton = findViewById(R.id.home);
        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        mFirebaseUser = mAuth.getCurrentUser();
        recyclerView= findViewById(R.id.tradeFeeds);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        mDatabaseReference = FirebaseDatabase.getInstance().getReference().child("/inventory/" );

        mDatabaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                if(dataSnapshot.exists()){
                    for(DataSnapshot dataSnapshot1: dataSnapshot.getChildren() ){
                        for(DataSnapshot snap:dataSnapshot1.getChildren()){
                            TradeItems inventoryItems = snap.getValue(TradeItems.class);
                            if(inventoryItems.getmUserId()!=mFirebaseUser.getUid()){
                                mItem.add(inventoryItems);
                            }

                        }

                    }
                    mAdapter = new TradeFeedRecyclerViewAdapter(TradeInventory.this, mItem,v->{
                            int position = (int) v.getTag();

                    });
                    recyclerView.setAdapter(mAdapter);
                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        //recyclerView.setAdapter(mAdapter);

       // addData();
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
