package com.example.pacearcadian;

class Items {

    private String mTitle;
    private String mDescription;

    Items(String title, String description) {
        mDescription = description;
        mTitle = title;
    }

    String getTitle() {
        return mTitle;
    }

    String getDescription() {
        return mDescription;
    }
}
