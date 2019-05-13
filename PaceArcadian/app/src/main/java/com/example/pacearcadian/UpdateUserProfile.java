package com.example.pacearcadian;

import android.content.Intent;
import android.database.Cursor;
import android.os.Handler;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.pacearcadian.AccountActivity.LoginActivity;
import com.example.pacearcadian.AccountActivity.SignUpActivity;
import com.example.pacearcadian.AccountActivity.UploadImage;
import com.example.pacearcadian.AccountActivity.UserInformation;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Locale;
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

import static com.squareup.picasso.Picasso.*;

public class UpdateUserProfile extends AppCompatActivity {

    private DatabaseReference mDatabase;
    private FirebaseUser mFirebaseUser;
    private EditText mFirstName, mLastName, mGraduationYear;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private FirebaseAuth mAuth;
    private Uri imageUri;
    private static final int PICK_IMAGE_REQUEST = 1;
    private ImageView mImageView;
    private StorageReference mStorageReference;
    Map<String, UserInformation> mUser = new HashMap<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_user_profile);

        mAuth = FirebaseAuth.getInstance();
        mFirebaseUser = mAuth.getCurrentUser();
        mStorageReference = FirebaseStorage.getInstance().getReference("uploads/");
        mDatabase = FirebaseDatabase.getInstance().getReference();

        Button btnSaveProfileInfo = findViewById(R.id.save_profile_button);
        mFirstName = findViewById(R.id.edit_user_first_name);
        mLastName = findViewById(R.id.edit_user_last_name);
        mGraduationYear = findViewById(R.id.edit_user_graduation_year);
        Button mEditButton = findViewById(R.id.user_photo_button);
        mImageView = findViewById(R.id.user_photo);
        mAuthListener = firebaseAuth -> {
            if (mFirebaseUser == null) {
                startActivity(new Intent(UpdateUserProfile.this, LoginActivity.class));
                finish();
            }
        };

        mEditButton.setOnClickListener(view->{
            openFileChooser();
        });

        btnSaveProfileInfo.setOnClickListener(view -> {

            String inputGraduationYear = mGraduationYear.getText().toString().trim();

            if (mFirstName.getText().toString().isEmpty() || mLastName.getText().toString().isEmpty() ||
                    mGraduationYear.getText().toString().isEmpty()) {

                Toast.makeText(getApplicationContext(), getString(R.string.fields_required), Toast.LENGTH_SHORT).show();

            } else if(validateGraduationYear(inputGraduationYear) && (mUser != null)){
                updateProfileInfo();
            }
        });
    }

    private void openFileChooser(){
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent,PICK_IMAGE_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==PICK_IMAGE_REQUEST && resultCode==RESULT_OK && data!=null && data.getData()!=null){
            imageUri=data.getData();
            Picasso.get().load(imageUri).into(mImageView);

        }
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
    //Code to get the file extension
    private String getFileExtension(Uri uri){
        ContentResolver cr = getContentResolver();
        MimeTypeMap mime = MimeTypeMap.getSingleton();
        return mime.getExtensionFromMimeType(cr.getType(uri));
    }

    public void updateProfileInfo(){
//        StorageReference fileReference = mStorageReference.child(System.currentTimeMillis()+"."+getFileExtension(imageUri));
        mDatabase = mDatabase.child("/user-data/" + "/");

        mUser.put(mFirebaseUser.getUid(), new UserInformation(mFirstName.getText().toString().trim(), mLastName.getText().toString().trim(), mGraduationYear.getText().toString().trim(), mFirebaseUser.getEmail().trim()));
    /*    fileReference.putFile(imageUri)
                .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        UploadImage uploadImage = new UploadImage(taskSnapshot.getStorage().getDownloadUrl().toString());
                        String uploadId= mDatabase.push().getKey();
                        mDatabase.child(uploadId).setValue(uploadImage);
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {

                    }
                });*/
        mDatabase.setValue(mUser);

        Toast.makeText(getApplicationContext(), getString(R.string.profile_updated), Toast.LENGTH_SHORT).show();
        startActivity(new Intent(UpdateUserProfile.this, MainActivity.class));

    }
}
