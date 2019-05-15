package com.example.pacearcadian;

class Items {

    private String mTitle;
    private String mDescription;
    private String mCategory;
    private String mUserId;

    Items(String title, String description, String category, String userId) {
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
