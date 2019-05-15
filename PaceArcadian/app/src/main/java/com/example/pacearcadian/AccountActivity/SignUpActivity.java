package com.example.pacearcadian.AccountActivity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.pacearcadian.MainActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import com.example.pacearcadian.R;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class SignUpActivity extends AppCompatActivity {

    private EditText inputEmail, inputPassword;     //hit option + enter if you on mac , for windows hit ctrl + enter
    private ProgressBar progressBar;
    private FirebaseAuth auth;
    private EditText mFirstName, mLastName, mGraduationYear;
    Map<String, UserInformation> mUser = new HashMap<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        //Get Firebase auth instance
        auth = FirebaseAuth.getInstance();

        Button btnSignIn = findViewById(R.id.sign_in_button);
        Button btnSignUp = findViewById(R.id.sign_up_button);
        inputEmail = findViewById(R.id.email);
        inputPassword = findViewById(R.id.password);
        progressBar = findViewById(R.id.progressBar);
        Button btnResetPassword = findViewById(R.id.btn_reset_password);
        mFirstName = findViewById(R.id.edit_user_first_name);
        mLastName = findViewById(R.id.edit_user_last_name);
        mGraduationYear = findViewById(R.id.edit_user_graduation_year);

        btnResetPassword.setOnClickListener(v ->
                startActivity(new Intent(SignUpActivity.this, ResetPasswordActivity.class)));

        btnSignIn.setOnClickListener(v -> finish());

        btnSignUp.setOnClickListener(v -> {

            String email = inputEmail.getText().toString().trim();
            String password = inputPassword.getText().toString().trim();


            if (TextUtils.isEmpty(email)) {
                Toast.makeText(getApplicationContext(), "Enter email address!", Toast.LENGTH_SHORT).show();
                return;
            }

            if (TextUtils.isEmpty(password)) {
                Toast.makeText(getApplicationContext(), "Enter password!", Toast.LENGTH_SHORT).show();
                return;
            }

            if (password.length() < 6) {
                Toast.makeText(getApplicationContext(), "Password too short, enter minimum 6 characters!", Toast.LENGTH_SHORT).show();
                return;
            }

            String inputGraduationYear = mGraduationYear.getText().toString().trim();

            if (mFirstName.getText().toString().isEmpty() || mLastName.getText().toString().isEmpty() ||
                    mGraduationYear.getText().toString().isEmpty()) {
                Toast.makeText(getApplicationContext(), getString(R.string.fields_required), Toast.LENGTH_SHORT).show();
                return;
            }

            progressBar.setVisibility(View.VISIBLE);
            //create user
            auth.createUserWithEmailAndPassword(email, password)
                    .addOnCompleteListener(SignUpActivity.this, task -> {
                        Toast.makeText(SignUpActivity.this, "createUserWithEmail:onComplete:" + task.isSuccessful(), Toast.LENGTH_SHORT).show();
                        progressBar.setVisibility(View.GONE);
                        // If sign in fails, display a message to the user. If sign in succeeds
                        // the auth state listener will be notified and logic to handle the
                        // signed in user can be handled in the listener.
                        if (!task.isSuccessful()) {
                            Toast.makeText(SignUpActivity.this, "Authentication failed." + task.getException(),
                                    Toast.LENGTH_SHORT).show();
                        } else if(validateGraduationYear(inputGraduationYear)){
                            updateProfileInfo();
                            startActivity(new Intent(SignUpActivity.this, MainActivity.class));
                            finish();
                        }
                    });
        });
    }

    private void updateProfileInfo() {
        FirebaseAuth auth = FirebaseAuth.getInstance();
        FirebaseUser firebaseUser = auth.getCurrentUser();
        DatabaseReference myRef = FirebaseDatabase.getInstance().getReference("user-data");
        mUser.put(firebaseUser.getUid(), new UserInformation(mFirstName.getText().toString().trim(),
                mLastName.getText().toString().trim(), mGraduationYear.getText().toString().trim(),
                firebaseUser.getEmail()));
        myRef.child(firebaseUser.getUid()).setValue(mUser);
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

    @Override
    protected void onResume() {
        super.onResume();
        progressBar.setVisibility(View.GONE);
    }
}

