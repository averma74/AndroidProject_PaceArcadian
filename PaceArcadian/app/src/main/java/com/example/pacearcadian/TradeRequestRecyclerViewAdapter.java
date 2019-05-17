package com.example.pacearcadian;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class TradeRequestRecyclerViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private FirebaseUser mFirebaseUser;
    DatabaseReference mDatabase;
    private Context mContext;
    private ArrayList<TradeRequest> mItemsList;
    DatabaseReference refRemove;

    TradeRequestRecyclerViewAdapter(Context context, ArrayList<TradeRequest> items) {
        mContext = context;
        mItemsList = items;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = View.inflate(mContext, R.layout.trade_request_item, null);
        return new RecyclerViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
        TradeRequest inventoryItem = mItemsList.get(i);
        RecyclerViewHolder recyclerViewHolder = (RecyclerViewHolder) viewHolder;
        recyclerViewHolder.mMyTitle.setText(inventoryItem.getCurrentItemTitle());
        recyclerViewHolder.mMyDescription.setText(inventoryItem.getCurrentItemDescription());
        recyclerViewHolder.mOfferedTitle.setText(inventoryItem.getRequestingItemTitle());
        recyclerViewHolder.mOfferedDescription.setText(inventoryItem.getRequestingItemDescription());
        recyclerViewHolder.mAcceptRequest.setTag(i);
        recyclerViewHolder.mDeclineRequest.setTag(i);
        recyclerViewHolder.itemView.setTag(i);

//        recyclerViewHolder.mAcceptRequest.setOnClickListener(v ->{
//
//            FirebaseAuth mAuth = FirebaseAuth.getInstance();
//            mFirebaseUser = mAuth.getCurrentUser();
//            mDatabase = FirebaseDatabase.getInstance().getReference().child("/inventory/" + mFirebaseUser.getUid()  + "/");
//            mDatabase.addValueEventListener(new ValueEventListener() {
//                @Override
//                public void onDataChange(DataSnapshot dataSnapshot) {
//                    for(DataSnapshot ds:dataSnapshot.getChildren()){
//                        Items Items = ds.getValue(Items.class);
//                        if (Items.getTitle().equals(inventoryItem.getRequestingItemTitle())){
//                            refRemove = ds.getRef();
//                            refRemove.removeValue();
//                            break;
//                        }
//                    }
//                }
//
//                @Override
//                public void onCancelled(DatabaseError databaseError) {
//
//                }
//            });
//            mDatabase = FirebaseDatabase.getInstance().getReference().child("/inventory/" + inventoryItem.getCurrentUserId()  + "/");
//            Log.d("ADITEE", "onBindViewHolder: " + inventoryItem.getCurrentUserId());
//            mDatabase.addValueEventListener(new ValueEventListener() {
//                @Override
//                public void onDataChange(DataSnapshot dataSnapshot) {
//                    for(DataSnapshot ds:dataSnapshot.getChildren()){
//                        Items tradeItems = ds.getValue(Items.class);
//                        Log.d("ADITEE", "onBindViewHolder2: " + tradeItems.getTitle());
//                        Log.d("ADITEE", "onBindViewHolder2: " + inventoryItem.getCurrentItemTitle());
//                        if (tradeItems.getTitle().equals(inventoryItem.getCurrentItemTitle())){
//                            refRemove = ds.getRef();
//                            refRemove.removeValue();
//                        }
//                    }
//                }
//
//                @Override
//                public void onCancelled(DatabaseError databaseError) {
//
//                }
//            });
//
//
////            Intent intent = new Intent(mContext, TradeRequests.class);
////            mContext.startActivity(intent);
//            recyclerViewHolder.mAcceptRequest.setEnabled(false);
//            recyclerViewHolder.mDeclineRequest.setEnabled(false);
//
//        });



        //if category is different
        recyclerViewHolder.mMyImageView.setImageResource(R.drawable.other);
        switch (inventoryItem.getCurrentItemCategory()) {
            case "APPARELS":
                recyclerViewHolder.mMyImageView.setImageResource(R.drawable.apparels);
                break;
            case "BOOKS":
                recyclerViewHolder.mMyImageView.setImageResource(R.drawable.books);
                break;
            case "EATABLES":
                recyclerViewHolder.mMyImageView.setImageResource(R.drawable.eatables);
                break;
            case "ELECTRONICS":
                recyclerViewHolder.mMyImageView.setImageResource(R.drawable.electronics);
                break;
            case "FASHION ACCESSORIES":
                recyclerViewHolder.mMyImageView.setImageResource(R.drawable.fashion_accessories);
                break;
            case "FURNITURE":
                recyclerViewHolder.mMyImageView.setImageResource(R.drawable.furniture);
                break;
            case "MEDIA":
                recyclerViewHolder.mMyImageView.setImageResource(R.drawable.media);
                break;
            case "SHOES":
                recyclerViewHolder.mMyImageView.setImageResource(R.drawable.shoes);
                break;
            case "SPORTS":
                recyclerViewHolder.mMyImageView.setImageResource(R.drawable.sports);
                break;
            case "TICKETS":
                recyclerViewHolder.mMyImageView.setImageResource(R.drawable.tickets);
                break;
            case "OTHER":
                recyclerViewHolder.mMyImageView.setImageResource(R.drawable.other);
                break;
        }

            //if category is different

            recyclerViewHolder.mOfferedImageView.setImageResource(R.drawable.other);
            switch (inventoryItem.getRequestingItemCategory()) {
                case "APPARELS":
                    recyclerViewHolder.mOfferedImageView.setImageResource(R.drawable.apparels);
                    break;
                case "BOOKS":
                    recyclerViewHolder.mOfferedImageView.setImageResource(R.drawable.books);
                    break;
                case "EATABLES":
                    recyclerViewHolder.mOfferedImageView.setImageResource(R.drawable.eatables);
                    break;
                case "ELECTRONICS":
                    recyclerViewHolder.mOfferedImageView.setImageResource(R.drawable.electronics);
                    break;
                case "FASHION ACCESSORIES":
                    recyclerViewHolder.mOfferedImageView.setImageResource(R.drawable.fashion_accessories);
                    break;
                case "FURNITURE":
                    recyclerViewHolder.mOfferedImageView.setImageResource(R.drawable.furniture);
                    break;
                case "MEDIA":
                    recyclerViewHolder.mOfferedImageView.setImageResource(R.drawable.media);
                    break;
                case "SHOES":
                    recyclerViewHolder.mOfferedImageView.setImageResource(R.drawable.shoes);
                    break;
                case "SPORTS":
                    recyclerViewHolder.mOfferedImageView.setImageResource(R.drawable.sports);
                    break;
                case "TICKETS":
                    recyclerViewHolder.mOfferedImageView.setImageResource(R.drawable.tickets);
                    break;
                case "OTHER":
                    recyclerViewHolder.mOfferedImageView.setImageResource(R.drawable.other);
                    break;
            }

    }



    @Override
    public int getItemCount() {
        return mItemsList.size();
    }

    private class RecyclerViewHolder extends RecyclerView.ViewHolder {

        TextView mMyTitle;
        TextView mMyDescription;
        ImageView mMyImageView;
        TextView mOfferedTitle;
        TextView mOfferedDescription;
        ImageView mOfferedImageView;
        Button mAcceptRequest;
        Button mDeclineRequest;

        RecyclerViewHolder(@NonNull View itemView) {
            super(itemView);
            mMyTitle = itemView.findViewById(R.id.my_title);
            mMyDescription = itemView.findViewById(R.id.my_description);
            mMyImageView = itemView.findViewById(R.id.my_item_image);
            mOfferedTitle = itemView.findViewById(R.id.offered_title);
            mOfferedDescription = itemView.findViewById(R.id.offered_description);
            mOfferedImageView = itemView.findViewById(R.id.offered_item_image);
            mAcceptRequest = itemView.findViewById(R.id.accept_trade);
            mDeclineRequest = itemView.findViewById(R.id.decline_trade);
        }
    }
}
