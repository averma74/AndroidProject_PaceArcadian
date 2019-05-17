package com.example.pacearcadian;

public class TradeRequest {

    private String currentUserID, requestingUserID;
    private String currentItemTitle, requestingItemTitle;
    private String currentItemDescription, requestingItemDescription;
    private boolean mAccepted = false;
    private boolean mDeclined = false;
    private boolean mPending = false;

    public TradeRequest() {
        //empty constructor
    }

    TradeRequest(String UID1, String title1, String description1, String UID2, String title2, String description2) {
        currentUserID = UID1;
        currentItemTitle = title1;
        currentItemDescription = description1;
        requestingUserID = UID2;
        requestingItemTitle = title2;
        requestingItemDescription = description2;
    }

    String getStatus(){
        if(mAccepted){
            return "Completed";
        }
        else if(mDeclined){
            return "Trade declined";
        }
        else{
            return "Pending";
        }
    }

    public String getCurrentUserId() {
        return currentUserID;
    }

    public String getRequestingUserId() {
        return requestingUserID;
    }

    String getCurrentItemTitle() {
        return currentItemTitle;
    }

    String getRequestingItemTitle() {
        return requestingItemTitle;
    }

    String getCurrentItemDescription() {
        return currentItemDescription;
    }

    String getRequestingItemDescription() {
        return requestingItemDescription;
    }

    public void setStatus(int flag) {
        if (flag==1)
            mAccepted = true;
        if (flag==0)
            mDeclined = true;
    }
}

