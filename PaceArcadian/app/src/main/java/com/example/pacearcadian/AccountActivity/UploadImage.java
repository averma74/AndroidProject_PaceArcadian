package com.example.pacearcadian.AccountActivity;

public class UploadImage {

    private String mImageUrl;

    public UploadImage(){

    }
    public UploadImage(String imageUrl){
        mImageUrl = imageUrl;
    }
    public String getImageUrl(){
        return mImageUrl;
    }
    public void setmImageUrl(String imageUrl){
        mImageUrl = imageUrl;
    }
}
