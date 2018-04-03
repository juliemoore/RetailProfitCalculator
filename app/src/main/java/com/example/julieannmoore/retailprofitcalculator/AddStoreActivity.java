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

        mButton = findViewById(R.id.bn_add);
        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mStore = addStore(mStore);
                new InsertStoreTask(AddStoreActivity.this, mStore).execute();
            }
        });
    }

    public Store addStore(Store store) {
        // Fetch data and create store object
        String storeName = mStoreName.getText().toString();
        String storeNumber = mStoreNumber.getText().toString();
        mStore = new Store(0, storeName, storeNumber);

        if (mStoreName.length() != 0 && mStoreNumber.length() != 0) {
            //mDatabase.getStoreDao().insertStore(mStore);
            toastMessage("Store added successfully.");
            mStoreName.setText("");
            mStoreNumber.setText("");
            hideKeyboard();
        } else {
            toastMessage("Store name and number are required.");
        }
        return mStore;
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

    private void setResult(Store store, int flag) {
        Intent intent = new Intent(AddStoreActivity.this, StoreListActivity.class);
        setResult(flag, intent.putExtra("Store", store));
        startActivity(intent);
    }

    private static class InsertStoreTask extends AsyncTask<Void, Void, Boolean> {
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

            activityReference.get().mDatabase.getStoreDao().insertStore(store);
            return true;
        }

        // onPostExecut runs on main thread
        @Override
        protected void onPostExecute(Boolean bool) {
            if (bool) {
                activityReference.get().setResult(store, 1);
            }
        }
    }

}