package com.example.pacearcadian;

class Items {

    private String mTitle;
    private String mDescription;
    private String mCategory;

    Items(String title, String description, String category) {
        mDescription = description;
        mTitle = title;
        mCategory = category;
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
}
