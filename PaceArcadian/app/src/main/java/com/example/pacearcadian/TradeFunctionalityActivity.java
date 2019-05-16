package com.example.pacearcadian;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class TradeFunctionalityActivity extends AppCompatActivity {

    TextView mTextView;
    Button mRequestButton, mCancelButton;
    Spinner mDropdown;
    FirebaseUser user;
    DatabaseReference mDatabaseReference;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.trading_activity);

        mDatabaseReference = FirebaseDatabase.getInstance().getReference();
        //initialize views
        mTextView = findViewById(R.id.select_item_to_trade);
        mRequestButton = findViewById(R.id.request_button);
        mCancelButton = findViewById(R.id.cancel_button);
        mDropdown = findViewById(R.id.items_list_dropdown);

        addValuesToDropdown();
        clickListeners();
    }

    private void clickListeners() {
        //cancel button
        mCancelButton.setOnClickListener(v -> {
            setResult(RESULT_CANCELED);
            finish();
        });

        //request button
        mRequestButton.setOnClickListener(v -> {
            Toast.makeText(this, R.string.requested, Toast.LENGTH_LONG).show();
            TradeRequest request = new TradeRequest("ID-1", "Tickets", "Endgame tickets", "ID-2", "Book", "Cracking the coding interview hardcopy");
            mDatabaseReference.child("request").push().setValue(request);
            finish();
        });
    }


    private void addValuesToDropdown() {
        user = FirebaseAuth.getInstance().getCurrentUser();
        String ID = user.getUid();
        mDatabaseReference.child("inventory").child(ID).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                List<String> listOfItems = new ArrayList<>();
                try {
                    if (dataSnapshot.getValue() != null) {
                        for (DataSnapshot ds: dataSnapshot.getChildren()) {
                            //adding values to the dropdown
                            String values = ds.child("title").getValue(String.class);
                            listOfItems.add(values);
                        }
                        ArrayAdapter<String> adapter = new ArrayAdapter<>(TradeFunctionalityActivity.this, android.R.layout.simple_spinner_dropdown_item, listOfItems);
                        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        mDropdown.setAdapter(adapter);
                    }
                    else
                        Log.i("debug","probably null");
                    //data snapshot is null
                }
                catch (Exception e) {
                    e.printStackTrace();

                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
}