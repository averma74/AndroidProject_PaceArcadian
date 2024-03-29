package com.example.pacearcadian;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.pacearcadian.AccountActivity.LoginActivity;
import com.example.pacearcadian.AccountActivity.UserInformation;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.w3c.dom.Text;

import java.util.HashMap;
import java.util.Map;

public class UserProfile extends AppCompatActivity {

    private DatabaseReference mDatabase;
    private FirebaseUser mFirebaseUser;
    private TextView mFirstName, mLastName, mGraduationYear, mEmail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.user_profile);

        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        mFirebaseUser = mAuth.getCurrentUser();
        mDatabase = FirebaseDatabase.getInstance().getReference();

        mFirstName = findViewById(R.id.user_first_name);
        mLastName = findViewById(R.id.user_last_name);
        mGraduationYear = findViewById(R.id.user_graduation_year);
        mEmail = findViewById(R.id.user_email);
        Button editProfile = findViewById(R.id.edit_profile_button);
        Button goToHome = findViewById(R.id.go_back_button);

//        FirebaseAuth.AuthStateListener mAuthListener = firebaseAuth -> {
//            if (mFirebaseUser == null) {
//                startActivity(new Intent(UserProfile.this, LoginActivity.class));
//                finish();
//            }
//        };

        editProfile.setOnClickListener(view -> {
            startActivity(new Intent(UserProfile.this, UpdateUserProfile.class));
            finish();
        });

        goToHome.setOnClickListener(view -> {
            startActivity(new Intent(UserProfile.this, MainActivity.class));
            finish();
        });

    }

    @Override
    protected void onStart() {
        super.onStart();
        getProfileInfo();
    }

    private void getProfileInfo() {
        mDatabase = mDatabase.child("/user-data/" + mFirebaseUser.getUid()  + "/" + mFirebaseUser.getUid() + "/");

        mDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                if (dataSnapshot.exists()){
                    UserInformation fetchedUserData = dataSnapshot.getValue(UserInformation.class);

                    assert fetchedUserData != null;
                    mFirstName.setText(fetchedUserData.mFirstName.trim());
                    mLastName.setText(fetchedUserData.mLastName.trim());
                    mGraduationYear.setText(fetchedUserData.mGraduationYear.trim());
                    mEmail.setText(fetchedUserData.mEmail.trim());
                }
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.d("", getString(databaseError.getCode()));
            }
        });
    }
}
