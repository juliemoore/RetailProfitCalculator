package com.example.julieannmoore.retailprofitcalculator;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.julieannmoore.retailprofitcalculator.mData.Store;
import com.example.julieannmoore.retailprofitcalculator.mDatabase.AppDatabase;

public class UpdateStoreActivity extends AppCompatActivity {

    private EditText mStoreName, mStoreNumber;
    private Store mStore;
    private AppDatabase mDatabase;
    private Button mButton;
    int mStoreId;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_store);

        mDatabase = AppDatabase.getInstance(this);
        mStoreName = findViewById(R.id.editTextStoreName);
        mStoreNumber = findViewById(R.id.editTextStoreNumber);
        mButton = findViewById(R.id.bn_add);

        mButton = findViewById(R.id.bn_add);
        if( (mStore = (Store) getIntent().getSerializableExtra("Update")) != null) {
            mButton.setText(getString(R.string.update));
            mStoreId = mStore.getStoreId();
            mStoreName.setText(mStore.getStoreName());
            mStoreNumber.setText(mStore.getStoreNumber());
        }

        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateStore();
                Intent intent = new Intent(UpdateStoreActivity.this, StoreListActivity.class);
                intent.putExtra("Update", mStore);
                startActivity(intent);
            }
        });
    }

    private void updateStore() {
        mStore = new Store(mStoreId, mStoreName.getText().toString(),
                mStoreNumber.getText().toString());

        if (mStoreName.length() != 0 && mStoreNumber.length() != 0) {
            mDatabase.getStoreDao().updateStore(mStore);
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
