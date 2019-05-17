package com.example.pacearcadian;

import android.content.ClipData;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TradeFunctionalityActivity extends AppCompatActivity {

    TextView mTextView;
    Button mRequestButton, mCancelButton;
    Spinner mDropdown;
    FirebaseUser user;
    DatabaseReference mDatabaseReference;
    TradeItems mItem;
    String UID1, UID2;
    String title1, title2;
    String description1, description2;
    String category1, category2;
    List<String> listOfTitles = new ArrayList<>();
    List<Items> listOfItems = new ArrayList<Items>();

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
        itemSelected();
        clickListeners();

        Bundle extras = getIntent().getExtras();
        mItem = extras.getParcelable("item");
        UID2 = mItem.getUserId();
        title2 = mItem.getTitle();
        description2 = mItem.getDescription();
        category2 = mItem.getCategory();
    }

    private void itemSelected() {
        mDropdown.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Items temp_obj = listOfItems.get(position);
                Log.i("debug", temp_obj.toString());
                UID1 = temp_obj.getUserId();
                title1 = temp_obj.getTitle();
                description1 = temp_obj.getDescription();
                category1 = temp_obj.getCategory();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
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
            TradeRequest request = new TradeRequest(UID1, title1, description1,category1, UID2, title2, description2, category2);
            //String key = UID2;
            String key = mDatabaseReference.push().getKey();
            Map<String, Object> childUpdates = new HashMap<>();
            childUpdates.put(key, request);
            mDatabaseReference = mDatabaseReference.child("trade-request/" + UID2);
            mDatabaseReference.updateChildren(childUpdates);
            //mDatabaseReference.child("request").push().setValue(request);

            mDatabaseReference = FirebaseDatabase.getInstance().getReference();
            mDatabaseReference = mDatabaseReference.child("trade-requested/" + UID1);
            mDatabaseReference.updateChildren(childUpdates);
            finish();
        });


    }


    private void addValuesToDropdown() {
        user = FirebaseAuth.getInstance().getCurrentUser();
        String ID = user.getUid();
        mDatabaseReference.child("inventory").child(ID).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                try {
                    if (dataSnapshot.getValue() != null) {
                        mRequestButton.setEnabled(true);
                        for (DataSnapshot ds: dataSnapshot.getChildren()) {
                            //adding values to the dropdown
                            listOfItems.add(ds.getValue(Items.class));
                            String values = ds.child("title").getValue(String.class);
                            listOfTitles.add(values);
                        }
                        ArrayAdapter<String> adapter = new ArrayAdapter<>(TradeFunctionalityActivity.this, android.R.layout.simple_spinner_dropdown_item, listOfTitles);
                        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        mDropdown.setAdapter(adapter);
                    }
                    else{
                        mRequestButton.setEnabled(false);
                    }

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