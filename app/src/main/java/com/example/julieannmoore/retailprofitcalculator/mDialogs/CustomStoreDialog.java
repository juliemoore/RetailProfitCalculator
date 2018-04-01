package com.example.julieannmoore.retailprofitcalculator.mDialogs;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;

import com.example.julieannmoore.retailprofitcalculator.ProductListActivity;
import com.example.julieannmoore.retailprofitcalculator.R;
import com.example.julieannmoore.retailprofitcalculator.StoreListActivity;
import com.example.julieannmoore.retailprofitcalculator.UpdateStoreActivity;
import com.example.julieannmoore.retailprofitcalculator.mAdapter.StoreAdapter;
import com.example.julieannmoore.retailprofitcalculator.mData.Store;
import com.example.julieannmoore.retailprofitcalculator.mDatabase.AppDatabase;
import com.example.julieannmoore.retailprofitcalculator.mUtilities.StoreListEventCallbacks;

public class CustomStoreDialog extends Dialog implements View.OnClickListener {

        public Activity mActivity;
        public Dialog mDialog;
        private AppDatabase mDatabase;
        private Store mItem;
        private StoreAdapter mAdapter;
        private StoreListEventCallbacks mListCallbacks;
        private int mItemId;
        private TextView mStoreName, mStoreNumber;
        private Button mUpdateButton, mDeleteButton, mViewButton;
        private String mMessage, storeName, storeNumber;

        public void InitializeData(Store item, StoreAdapter adapter, int itemId) {
            mItem = item;
            mAdapter = adapter;
            mItemId = itemId;
        }

        // constructor
        public CustomStoreDialog(Activity c) {
            super(c);
            this.mActivity = c;
        }

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            requestWindowFeature(Window.FEATURE_NO_TITLE);
            setContentView(R.layout.custom_store_dialog);

            mDatabase = AppDatabase.getInstance(mActivity);
            mDatabase.getStoreDao().getStores();
            if(StoreListEventCallbacks.class.isInstance(this)){
                mListCallbacks = (StoreListEventCallbacks) this;
            }

            mStoreName = findViewById(R.id.textView1);
            mStoreNumber = findViewById(R.id.textView2);
            mStoreName.setText(mItem.getStoreName());
            mStoreNumber.setText(mItem.getStoreNumber());

            mUpdateButton = findViewById(R.id.bn_update_store);
            mDeleteButton = findViewById(R.id.bn_delete_store);
            mViewButton = findViewById(R.id.bn_view_products);

            mUpdateButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // intent to add store activity
                    Intent intent = new Intent(mActivity, UpdateStoreActivity.class);
                    intent.putExtra("Update", mItem);
                    mActivity.startActivity(intent);
                }
            });
            mDeleteButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mDatabase.getStoreDao().deleteStore(mItem);
                    mAdapter.notifyDataSetChanged();
                    Intent intent = new Intent(mActivity, StoreListActivity.class);
                    mActivity.startActivity(intent);
                }
            });
            mViewButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(mActivity, ProductListActivity.class);
                    intent.putExtra("Store", mItem);
                    mActivity.startActivity(intent);
                }
            });

        }

        @Override
        public void onClick(View v) {
            return;
        }
    }
