package com.example.pacearcadian;

import android.os.Parcel;
import android.os.Parcelable;

public class TradeItems implements Parcelable {
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

    protected TradeItems(Parcel in) {
        title = in.readString();
        description = in.readString();
        category = in.readString();
        userId = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(title);
        dest.writeString(description);
        dest.writeString(category);
        dest.writeString(userId);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<TradeItems> CREATOR = new Creator<TradeItems>() {
        @Override
        public TradeItems createFromParcel(Parcel in) {
            return new TradeItems(in);
        }

        @Override
        public TradeItems[] newArray(int size) {
            return new TradeItems[size];
        }
    };

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
