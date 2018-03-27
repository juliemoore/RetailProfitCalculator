package com.example.julieannmoore.retailprofitcalculator;


import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.julieannmoore.retailprofitcalculator.mData.Store;
import com.example.julieannmoore.retailprofitcalculator.mDatabase.AppDatabase;

import java.io.Serializable;
import java.lang.ref.WeakReference;

public class AddStoreActivity extends AppCompatActivity {
    EditText mStoreName, mStoreNumber;
    String storeName, storeNumber;
    Store store;
    AppDatabase mDatabase;

    Button mButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_store);

        mDatabase = AppDatabase.getInstance(this);
        mStoreName = findViewById(R.id.editTextStoreName);
        mStoreNumber = findViewById(R.id.editTextStoreNumber);
        mButton = findViewById(R.id.bn_add);
        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addStore();

                Intent intent = new Intent(AddStoreActivity.this, AddProductActivity.class);
                startActivity(intent);
            }
        });
    }

    public void addStore() {
        // Fetch data and create store object
        store = new Store(0, mStoreName.getText().toString(),
                mStoreNumber.getText().toString());

        if (mStoreName.length() != 0 && mStoreNumber.length() != 0) {
            mDatabase.getStoreDao().insertStore(store);
            toastMessage("Store added successfully.");
            mStoreName.setText("");
            mStoreNumber.setText("");
        } else {
            toastMessage("Store name and number are required.");
        }
    }

    private void toastMessage(String message){
        Toast.makeText(this,message, Toast.LENGTH_SHORT).show();


    }
}
