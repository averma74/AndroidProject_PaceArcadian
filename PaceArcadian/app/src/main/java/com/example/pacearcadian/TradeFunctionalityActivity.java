package com.example.pacearcadian;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

public class TradeFunctionalityActivity extends AppCompatActivity {

    TextView mTextView;
    Button mRequestButton, mCancelButton;
    Spinner mDropdown;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.trading_activity);

        mTextView = findViewById(R.id.select_item_to_trade);
        mRequestButton = findViewById(R.id.request_button);
        mCancelButton = findViewById(R.id.cancel_button);
        mDropdown = findViewById(R.id.items_list_dropdown);
        addValuesToDropdown();
    }

    private void addValuesToDropdown() {

    }
}