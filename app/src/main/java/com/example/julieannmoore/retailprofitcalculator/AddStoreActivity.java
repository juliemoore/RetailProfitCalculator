package com.example.julieannmoore.retailprofitcalculator;


import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.provider.ContactsContract;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.ActionBar;
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

    private TextInputLayout mStoreNameWrapper, mStoreNumberWrapper;
    private TextInputEditText mStoreNameEditText, mStoreNumberEditText;
    private TextView mTitle;
    private String mStoreName, mStoreNumber;
    private int mStoreId;
    private Boolean isUpdate, isValid;
    private Store mStore;
    private AppDatabase mDatabase;
    private Button mButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_store);

        // Set action bar with logo
        ActionBar actionBar = getSupportActionBar();
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        actionBar.setLogo(R.mipmap.ic_launcher_round);
        actionBar.setDisplayUseLogoEnabled(true);

        mDatabase = AppDatabase.getInstance(this);
        mStore = new Store();
        initializeView();
        getUpdateStoreData();
        if (mStore == null) {
            isUpdate = false;
        }

        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Fetch data and create store object
                if (mStoreNameWrapper.getEditText().getText().toString().isEmpty()) {
                    mStoreNameWrapper.setError("Store name is required.");
                    mStoreNameEditText.requestFocus();
                    isValid = false;
                } else {
                    mStoreName = mStoreNameEditText.getText().toString();
                    mStoreNameWrapper.setErrorEnabled(false);
                }
                if (mStoreNumberWrapper.getEditText().getText().toString().isEmpty()) {
                    mStoreNumberWrapper.setError("Store number is required.");
                    mStoreNumberEditText.requestFocus();
                    isValid = false;
                } else {
                    mStoreNumber = mStoreNumberEditText.getText().toString();
                    mStoreNumberWrapper.setErrorEnabled(false);
                    isValid = true;
                }
                if (isValid == true) {
                    if (isUpdate == true) {
                        mStore = new Store(mStoreId, mStoreName, mStoreNumber);
                        mDatabase.getStoreDao().updateStore(mStore);
                        toastMessage(getString(R.string.update_store_successful));
                        Intent intent = new Intent(AddStoreActivity.this,
                                StoreListActivity.class);
                        intent.putExtra("Update", mStore);
                        startActivity(intent);
                    } else {
                        mStore = new Store(0, mStoreName, mStoreNumber);
                        new InsertStoreTask(AddStoreActivity.this, mStore).execute();
                        toastMessage(getString(R.string.save_store_successful));
                    }
                    mStoreNameEditText.setText("");
                    mStoreNumberEditText.setText("");
                    hideKeyboard();
                } else {
                    toastMessage("Store name and number are required.");
                }
           }
        });
    }

    private void initializeView() {
        mStoreNameWrapper = findViewById(R.id.storeNameWrapper);
        mStoreNumberWrapper = findViewById(R.id.storeNumberWrapper);
        mStoreNameWrapper.setHint(getString(R.string.store_name));
        mStoreNameWrapper.setHint(getString(R.string.store_number));
        mStoreNameEditText = findViewById(R.id.storeNameEditText);
        mStoreNumberEditText = findViewById(R.id.storeNumberEditText);
        mTitle = findViewById(R.id.add_store_title);
        mButton = findViewById(R.id.bn_add);
    }

    private void getUpdateStoreData(){
        if( (mStore = (Store) getIntent().getSerializableExtra("Update")) != null) {
            isUpdate = true;
            mTitle.setText(getString(R.string.update_store));
            mButton.setText(getString(R.string.update));
            mStoreId = mStore.getStoreId();
            mStoreName = mStore.getStoreName();
            mStoreNumber = mStore.getStoreNumber();

            mStoreNameEditText.setText(mStoreName);
            mStoreNumberEditText.setText(mStoreNumber);
        }
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
}