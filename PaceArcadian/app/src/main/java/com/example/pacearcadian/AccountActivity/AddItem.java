package com.example.pacearcadian.AccountActivity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.pacearcadian.R;

public class AddItem extends AppCompatActivity {

    String mTitle, mDescription;
    String mCategory;
    EditText mEnterTitleField, mEnterDescriptionField;
    Spinner mCategorySpinner;
    public static final String EXTRA_MESSAGE_TITLE = "string_title";
    public static final String EXTRA_MESSAGE_DESCRIPTION = "string_desc";
    public static final String EXTRA_MESSAGE_CATEGORY = "string_category";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_item);
        mEnterTitleField = findViewById(R.id.new_title);
        mEnterDescriptionField = findViewById(R.id.new_description);

        mCategorySpinner = findViewById(R.id.category_spinner);
        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.category_array, android.R.layout.simple_spinner_item);
        // Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
        mCategorySpinner.setAdapter(adapter);

        mCategorySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                mCategory = parent.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                mCategory = "OTHER";
            }
        });

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
            replyIntent.putExtra(EXTRA_MESSAGE_CATEGORY, mCategory);
            setResult(RESULT_OK, replyIntent);
            finish();
        }
    }

    public void onCancelButtonClicked(View view) {
        setResult(RESULT_CANCELED);
        finish();
    }
}
