package com.example.julieannmoore.retailprofitcalculator;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;

import com.example.julieannmoore.retailprofitcalculator.mData.Store;
import com.example.julieannmoore.retailprofitcalculator.mDatabase.AppDatabase;

/**
 * Created by Julie Moore on 3/27/2018.
 */

public class CustomDialog extends Dialog implements View.OnClickListener {

    public Activity mActivity;
    public Dialog mDialog;
    private AppDatabase mDatabase;
    private Store store;
    private TextView mStoreName, mStoreNumber;
    private Button mUpdateButton, mDeleteButton, mViewButton;
    private String mMessage;

    // constructor
    CustomDialog(Activity c) {
        super(c);
        this.mActivity = c;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.layout_custom);

        mDatabase = AppDatabase.getInstance(mActivity);
        mDatabase.getStoreDao().getStores();
        mStoreName = findViewById(R.id.textView1);
        mStoreNumber = findViewById(R.id.textView2);
        //mStoreName.setText(listItem.getStoreName());
        //mStoreNumber.setText(listItem.getStoreNumber());
        mUpdateButton = findViewById(R.id.bn_update_store);
        mDeleteButton = findViewById(R.id.bn_delete_store);
        mViewButton = findViewById(R.id.bn_view_products);

        mUpdateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // intent to add store activity
                mMessage = mActivity.getString(R.string.update);
                Intent intent = new Intent(mActivity, AddStoreActivity.class);
                intent.putExtra("Update", mMessage);
                mActivity.startActivity(intent);
            }
        });
        mDeleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mActivity, StoreListActivity.class);
                mActivity.startActivity(intent);
            }
        });
        mViewButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mActivity, ProductListActivity.class);
                mActivity.startActivity(intent);
            }
        });

    }

    @Override
    public void onClick(View v) {
        return;
    }
}
