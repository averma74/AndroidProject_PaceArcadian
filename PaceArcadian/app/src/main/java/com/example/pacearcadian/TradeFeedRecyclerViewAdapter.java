package com.example.pacearcadian;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

public class TradeFeedRecyclerViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private Context mContext;
    private ArrayList<TradeItems> mItemsList;
    TradeFeedRecyclerViewAdapter(Context context, ArrayList<TradeItems> items) {
        mContext = context;
        mItemsList = items;
    }
    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = View.inflate(mContext,R.layout.feed_item,null);
        return new RecyclerViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
        TradeItems inventoryItem = mItemsList.get(i);
       RecyclerViewHolder recyclerViewHolder = (RecyclerViewHolder) viewHolder;
       recyclerViewHolder.mTitle.setText(inventoryItem.getTitle());
       recyclerViewHolder.mDescription.setText(inventoryItem.getDescription());
    }

    @Override
    public int getItemCount() {
        return mItemsList.size();
    }
    private class RecyclerViewHolder extends RecyclerView.ViewHolder {

        TextView mTitle;
        TextView mDescription;

        RecyclerViewHolder(@NonNull View itemView) {
            super(itemView);
            mTitle = itemView.findViewById(R.id.title);
            mDescription = itemView.findViewById(R.id.username);
            //mImageView = itemView.findViewById(R.id.item_image);
        }
    }
}