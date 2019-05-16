package com.example.pacearcadian;

public class TradeRequestedItems {
    private String title;
    private String userID;
    private String status;
    private String category;
    public TradeRequestedItems(){

    }
    TradeRequestedItems(String mtitle, String muserId, String mstatus, String mCategory) {
        title = mtitle;
        userID = muserId;
        status = mstatus;
        category = mCategory;
    }

    String getTitle() {
        return title;
    }
    String getStatus(){
        return status;
    }
    String getCategory() {
        return category;
    }

    String getmUserId() {
        return userID;
    }

}
