package com.example.pacearcadian.AccountActivity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.pacearcadian.R;

public class AddItem extends AppCompatActivity {

    String mTitle, mDescription;
    EditText mEnterTitleField, mEnterDescriptionField;
    public static final String EXTRA_MESSAGE_TITLE = "string_title";
    public static final String EXTRA_MESSAGE_DESCRIPTION = "string_desc";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_item);
        mEnterTitleField = findViewById(R.id.new_title);
        mEnterDescriptionField = findViewById(R.id.new_description);
    }

    public void onSaveButtonClicked(View view) {
        Intent replyIntent = new Intent();
        if (mEnterDescriptionField.getText().toString().isEmpty()) {
            setResult(RESULT_CANCELED);
            Toast.makeText(this, R.string.enter_value_for_title, Toast.LENGTH_LONG).show();
        }
        else if (mEnterTitleField.getText().toString().isEmpty()) {
            setResult(RESULT_CANCELED);
            Toast.makeText(this, R.string.enter_value_for_description, Toast.LENGTH_LONG).show();
        }
        else {
            mTitle = mEnterTitleField.getText().toString().trim();
            mDescription = mEnterDescriptionField.getText().toString().trim();
            replyIntent.putExtra(EXTRA_MESSAGE_TITLE, mTitle);
            replyIntent.putExtra(EXTRA_MESSAGE_DESCRIPTION, mDescription);
            setResult(RESULT_OK, replyIntent);
            finish();
        }
    }
}
