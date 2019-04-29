package com.example.pacearcadian.AccountActivity;

import java.util.Date;

public class UserInformation {

    public String mFirstName;
    public String mLastName;
    public String mGraduationYear;
    public String mReputation;
    public String mEmail;

    public UserInformation() {

    }

    public UserInformation(String firstName, String lastName, String graduationYear, String email) {
        this.mFirstName = firstName;
        this.mLastName = lastName;
        this.mGraduationYear = graduationYear;
        this.mEmail = email;
    }
}
