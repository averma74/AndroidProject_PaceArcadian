package com.example.pacearcadian;

public class TradeItems {
    private String mTitle;
    private String mDescription;
    private String mCategory;
    private String mUserId;

    TradeItems(String title, String description, String category, String userId) {
        mDescription = description;
        mTitle = title;
        mCategory = category;
        mUserId = userId;
    }

    String getTitle() {
        return mTitle;
    }

    String getDescription() {
        return mDescription;
    }

    String getCategory() {
        return mCategory;
    }

    String getmUserId() {
        return mUserId;
    }
}
