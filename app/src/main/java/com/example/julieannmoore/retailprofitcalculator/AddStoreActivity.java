package com.example.julieannmoore.retailprofitcalculator;


import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.julieannmoore.retailprofitcalculator.mAdapter.StoreAdapter;
import com.example.julieannmoore.retailprofitcalculator.mData.Store;
import com.example.julieannmoore.retailprofitcalculator.mDatabase.AppDatabase;
import com.example.julieannmoore.retailprofitcalculator.mUtilities.InititializeStoreData;
import com.example.julieannmoore.retailprofitcalculator.mUtilities.StoreListEventCallbacks;

import java.io.Serializable;
import java.lang.ref.WeakReference;

public class AddStoreActivity extends AppCompatActivity {

    private EditText mStoreName, mStoreNumber;
    private TextView mTitle;
    private int mStoreId;
    private Boolean isUpdate = false;
    private Store mStore;
    private AppDatabase mDatabase;
    private Button mButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_store);

        mDatabase = AppDatabase.getInstance(this);
        mStoreName = findViewById(R.id.editTextStoreName);
        mStoreNumber = findViewById(R.id.editTextStoreNumber);
        mTitle = findViewById(R.id.add_store_title);
        mButton = findViewById(R.id.bn_add);

        if( (mStore = (Store) getIntent().getSerializableExtra("Update")) != null) {
            isUpdate = true;
            mTitle.setText(getString(R.string.update_store));
            mButton.setText(getString(R.string.update));
            mStoreId = mStore.getStoreId();
            String storeName = mStore.getStoreName();
            String storeNumber = mStore.getStoreNumber();
            mStoreName.setText(storeName);
            mStoreNumber.setText(storeNumber);
        }

        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Fetch data and create store object
                String storeName = mStoreName.getText().toString();
                String storeNumber = mStoreNumber.getText().toString();

                if (mStoreName.length() != 0 && mStoreNumber.length() != 0) {
                    if (isUpdate == true) {
                        mStore = new Store(mStoreId, storeName, storeNumber);
                        mDatabase.getStoreDao().updateStore(mStore);
                        toastMessage(getString(R.string.update_store_successful));
                        Intent intent = new Intent(AddStoreActivity.this,
                                StoreListActivity.class);
                        intent.putExtra("Update", mStore);
                        startActivity(intent);
                    } else {
                        mStore = new Store(0, storeName, storeNumber);
                        new InsertStoreTask(AddStoreActivity.this, mStore).execute();
                        toastMessage(getString(R.string.save_store_successful));
                    }
                    mStoreName.setText("");
                    mStoreNumber.setText("");
                    hideKeyboard();
                } else {
                    toastMessage("Store name and number are required.");
                }
            }
        });
    }

    private void hideKeyboard() {
        View view = getCurrentFocus();
        if (view != null) {
            ((InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE)).
                    hideSoftInputFromWindow(view.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
        }
    }

    private void toastMessage(String message){
        Toast.makeText(this,message, Toast.LENGTH_SHORT).show();
    }

    private class InsertStoreTask extends AsyncTask<Void, Void, Boolean> {
        private WeakReference<AddStoreActivity> activityReference;
        private Store store;

        // only retain a weak reference to the activity
        InsertStoreTask(AddStoreActivity context, Store store) {
            activityReference = new WeakReference<>(context);
            this.store = store;
        }

        // doInBackground methods runs on a worker thread
        @Override
        protected Boolean doInBackground(Void... objs) {
            activityReference.get().mDatabase.getStoreDao().insertStore(mStore);
            return true;
        }

        // onPostExecut runs on main thread
        @Override
        protected void onPostExecute(Boolean bool) {
            if (bool) {
                Intent intent = new Intent(AddStoreActivity.this, StoreListActivity.class);
                intent.putExtra("Store", mStore);
                startActivity(intent);
            }
        }
    }

}