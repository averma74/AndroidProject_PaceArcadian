package com.example.pacearcadian;

class Items {

    private String title;
    private String description;
    private String category;
    private String userId;

    public Items(){

    }

    Items(String title, String description, String category, String userId) {
        this.description = description;
        this.title = title;
        this.category = category;
        this.userId = userId;
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

    String getUserId() {
        return userId;
    }
}
