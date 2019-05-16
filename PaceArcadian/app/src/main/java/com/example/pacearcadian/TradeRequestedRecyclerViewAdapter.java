package com.example.pacearcadian;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class TradeRequestedRecyclerViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private Context mContext;
    private ArrayList<TradeRequest> mItemsList;

    TradeRequestedRecyclerViewAdapter(Context context, ArrayList<TradeRequest> items) {
        mContext = context;
        mItemsList = items;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = View.inflate(mContext, R.layout.trade_requested_item, null);
        return new RecyclerViewHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
        TradeRequest requestedItems = mItemsList.get(i);
        Log.e("id",requestedItems.getRequestingUserId()+"");
        RecyclerViewHolder recyclerViewHolder = (RecyclerViewHolder) viewHolder;
        recyclerViewHolder.mTitle.setText(requestedItems.getRequestingItemTitle());
        recyclerViewHolder.mStatus.setText(requestedItems.getStatus());
        recyclerViewHolder.mUserID.setText(requestedItems.getRequestingUserId());
       /* recyclerViewHolder.mImageView.setImageResource(R.drawable.other);
        switch (requestedItems.getCategory()){
            case "APPARELS":
                recyclerViewHolder.mImageView.setImageResource(R.drawable.apparels);
                break;
            case "BOOKS":
                recyclerViewHolder.mImageView.setImageResource(R.drawable.books);
                break;
            case "EATABLES":
                recyclerViewHolder.mImageView.setImageResource(R.drawable.eatables);
                break;
            case "ELECTRONICS":
                recyclerViewHolder.mImageView.setImageResource(R.drawable.electronics);
                break;
            case "FASHION ACCESSORIES":
                recyclerViewHolder.mImageView.setImageResource(R.drawable.fashion_accessories);
                break;
            case "FURNITURE":
                recyclerViewHolder.mImageView.setImageResource(R.drawable.furniture);
                break;
            case "MEDIA":
                recyclerViewHolder.mImageView.setImageResource(R.drawable.media);
                break;
            case "SHOES":
                recyclerViewHolder.mImageView.setImageResource(R.drawable.shoes);
                break;
            case "SPORTS":
                recyclerViewHolder.mImageView.setImageResource(R.drawable.sports);
                break;
            case "TICKETS":
                recyclerViewHolder.mImageView.setImageResource(R.drawable.tickets);
                break;
            case "OTHER":
                recyclerViewHolder.mImageView.setImageResource(R.drawable.other);
                break;
        }*/
    }

    @Override
    public int getItemCount() {
        return mItemsList.size();
    }


    private class RecyclerViewHolder extends RecyclerView.ViewHolder {

        TextView mTitle;
        TextView mUserID;
        ImageView mImageView;
        TextView mStatus;

        RecyclerViewHolder(@NonNull View itemView) {
            super(itemView);
            mTitle = itemView.findViewById(R.id.title);
            mUserID = itemView.findViewById(R.id.username);
            mImageView = itemView.findViewById(R.id.item_image);
            mStatus = itemView.findViewById(R.id.status);
        }
    }
}
