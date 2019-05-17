package com.example.pacearcadian;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class TradeRequestRecyclerViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context mContext;
    private ArrayList<TradeRequest> mItemsList;

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

        RecyclerViewHolder(@NonNull View itemView) {
            super(itemView);
            mMyTitle = itemView.findViewById(R.id.my_title);
            mMyDescription = itemView.findViewById(R.id.my_description);
            mMyImageView = itemView.findViewById(R.id.my_item_image);
            mOfferedTitle = itemView.findViewById(R.id.offered_title);
            mOfferedDescription = itemView.findViewById(R.id.offered_description);
            mOfferedImageView = itemView.findViewById(R.id.offered_item_image);
        }
    }
}
