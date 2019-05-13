package com.example.pacearcadian;

public class TradeItems {
    private String mTitle;
    private String mUsername;

    TradeItems(String title, String Username) {
        mUsername = Username;
        mTitle = title;
    }

    String getTitle() {
        return mTitle;
    }

    String getDescription() {
        return mUsername;
    }
}
