package com.example.pacearcadian;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.pacearcadian.AccountActivity.LoginActivity;
import com.example.pacearcadian.AccountActivity.SignUpActivity;
import com.example.pacearcadian.AccountActivity.UserInformation;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class UpdateUserProfile extends AppCompatActivity {

    private DatabaseReference mDatabase;
    private FirebaseUser mFirebaseUser;
    private EditText mFirstName, mLastName, mGraduationYear;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private FirebaseAuth mAuth;
    Map<String, UserInformation> mUser = new HashMap<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_user_profile);

        mAuth = FirebaseAuth.getInstance();
        mFirebaseUser = mAuth.getCurrentUser();
        mDatabase = FirebaseDatabase.getInstance().getReference();

        Button btnSaveProfileInfo = findViewById(R.id.save_profile_button);
        mFirstName = findViewById(R.id.edit_user_first_name);
        mLastName = findViewById(R.id.edit_user_last_name);
        mGraduationYear = findViewById(R.id.edit_user_graduation_year);

        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                if (mFirebaseUser == null) {
                    startActivity(new Intent(UpdateUserProfile.this, LoginActivity.class));
                    finish();
                }
            }
        };

        btnSaveProfileInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String inputGraduationYear = mGraduationYear.getText().toString().trim();

                if (mFirstName.getText().toString().isEmpty() || mLastName.getText().toString().isEmpty() ||
                        mGraduationYear.getText().toString().isEmpty()) {

                    Toast.makeText(getApplicationContext(), getString(R.string.fields_required), Toast.LENGTH_SHORT).show();

                } else if(validateGraduationYear(inputGraduationYear) && (mUser != null)){
                    updateProfileInfo();
                }
            }
        });
    }

    @Override
    public void onStart() {
        super.onStart();
        mAuth.addAuthStateListener(mAuthListener);
    }

    @Override
    public void onStop() {
        super.onStop();
        if (mAuthListener != null) {
            mAuth.removeAuthStateListener(mAuthListener);
        }
    }

    public boolean validateGraduationYear(String graduationYear){

        SimpleDateFormat format = new SimpleDateFormat("yyyy");

        try {
            format.parse(graduationYear);
        } catch (ParseException e) {
            return false;
        }
        if(Integer.parseInt(graduationYear) >= Calendar.getInstance().get(Calendar.YEAR))
            return true;

        Toast.makeText(getApplicationContext(), getString(R.string.invalid_graduation_year), Toast.LENGTH_SHORT).show();

        return false;
    }

    public void updateProfileInfo(){

        mDatabase = mDatabase.child("/user-data/" + "/");

        mUser.put(mFirebaseUser.getUid(), new UserInformation(mFirstName.getText().toString().trim(), mLastName.getText().toString().trim(), mGraduationYear.getText().toString().trim(), mFirebaseUser.getEmail().trim()));
        mDatabase.setValue(mUser);

        Toast.makeText(getApplicationContext(), getString(R.string.profile_updated), Toast.LENGTH_SHORT).show();
        startActivity(new Intent(UpdateUserProfile.this, MainActivity.class));

    }
}
