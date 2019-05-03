package com.example.pacearcadian;

import android.content.Intent;
import android.database.Cursor;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
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
import android.os.Build;
import android.support.v4.content.ContextCompat;
import android.Manifest;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.support.v7.app.AlertDialog;
import android.content.*;
import android.app.Activity;
import android.provider.MediaStore;
public class UpdateUserProfile extends AppCompatActivity {

    private DatabaseReference mDatabase;
    private FirebaseUser mFirebaseUser;
    private EditText mFirstName, mLastName, mGraduationYear;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private FirebaseAuth mAuth;
    private ImageButton mProfilePhoto;
    static final int SELECT_PICTURE=1000;
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
        mProfilePhoto = findViewById(R.id.user_photo_button);
        handlePermission();
        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                if (mFirebaseUser == null) {
                    startActivity(new Intent(UpdateUserProfile.this, LoginActivity.class));
                    finish();
                }
            }
        };

        mProfilePhoto.setOnClickListener(view->{
            openImageChooser();
        });

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

    //Code added by Rohan K for selecting the photo from External Storage;
    private void handlePermission() {

        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
            return;
        }

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            //ask for permission
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                    SELECT_PICTURE);
        }
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case SELECT_PICTURE:
                for (int i = 0; i < permissions.length; i++) {
                    String permission = permissions[i];
                    if (grantResults[i] == PackageManager.PERMISSION_DENIED) {
                        boolean showRationale = ActivityCompat.shouldShowRequestPermissionRationale(this, permission);
                        if (showRationale) {
                            //  Show your own message here
                        } else {
                            showSettingsAlert();
                        }
                    }
                }
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    /* Choose an image from Gallery */
    void openImageChooser() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), SELECT_PICTURE);
    }

    public void onActivityResult(final int requestCode, final int resultCode, final Intent data) {

        new Thread(new Runnable() {
            @Override
            public void run() {
                if (resultCode == RESULT_OK) {
                    if (requestCode == SELECT_PICTURE) {
                        // Get the url from data
                        final Uri selectedImageUri = data.getData();
                        if (null != selectedImageUri) {
                            // Get the path from the Uri
                            String path = getPathFromURI(selectedImageUri);
                          // Log.i(TAG, "Image Path : " + path);
                            // Set the image in ImageView
                            findViewById(R.id.user_photo_button).post(new Runnable() {
                                @Override
                                public void run() {
                                    ((ImageView) findViewById(R.id.user_photo_button)).setImageURI(selectedImageUri);
                                }
                            });

                        }
                    }
                }
            }
        }).start();

    }
    public String getPathFromURI(Uri contentUri) {
        String res = null;
        String[] proj = {MediaStore.Images.Media.DATA};
        Cursor cursor = getContentResolver().query(contentUri, proj, null, null, null);
        if (cursor.moveToFirst()) {
            int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
            res = cursor.getString(column_index);
        }
        cursor.close();
        return res;
    }

    private void showSettingsAlert() {

        AlertDialog alertDialog = new AlertDialog.Builder(this).create();
        alertDialog.setTitle("Alert");
        alertDialog.setMessage("App needs to access the Camera.");
        alertDialog.setButton(AlertDialog.BUTTON_NEGATIVE, "DONT ALLOW",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        //finish();
                    }
                });
        alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, "SETTINGS",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        openAppSettings(UpdateUserProfile.this);
                    }
                });
        alertDialog.show();

    }
    public static void openAppSettings(final Activity context) {
        if (context == null) {
            return;
        }
        final Intent i = new Intent();
        i.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
        i.addCategory(Intent.CATEGORY_DEFAULT);
        i.setData(Uri.parse("package:" + context.getPackageName()));
        i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        i.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
        i.addFlags(Intent.FLAG_ACTIVITY_EXCLUDE_FROM_RECENTS);
        context.startActivity(i);
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
