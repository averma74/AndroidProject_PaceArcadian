package com.example.pacearcadian;

public class TradeItems {
    private String title;
    private String description;
    private String category;
    private String userId;

    public TradeItems() {

    }

    TradeItems(String title, String description, String category, String userId) {
        description = description;
        title = title;
        category = category;
        userId = userId;
    }

    String getTitle() {
        return title;
    }

    String getDescription() {
        return description;
    }

    String getCategory() {
        return category;
    }

    String getmUserId() {
        return userId;
    }
}
