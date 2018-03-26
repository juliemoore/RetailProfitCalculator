package com.example.julieannmoore.retailprofitcalculator;


import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.julieannmoore.retailprofitcalculator.mData.Store;
import com.example.julieannmoore.retailprofitcalculator.mDatabase.AppDatabase;

import java.io.Serializable;
import java.lang.ref.WeakReference;

public class AddStoreActivity extends AppCompatActivity {

    private TextInputEditText mStoreName, mStoreNumber;
    private AppDatabase mDatabase;
    private Store store;
    private boolean update;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_store);

        mStoreName = findViewById(R.id.et_store_name);
        mStoreNumber = findViewById(R.id.et_store_number);
        mDatabase = AppDatabase.getInstance(AddStoreActivity.this);
        Button mButton = findViewById(R.id.bn_add);

        if ( (store = (Store) getIntent().getSerializableExtra("store"))!=null ){
            mButton.setText(getString(R.string.update));
            setTitle(getString(R.string.update_store));
            update = true;
            mButton.setText(getString(R.string.update));
            mStoreName.setText(store.getStoreName());
            mStoreNumber.setText(store.getStoreNumber());
            mButton.setOnClickListener(new View.OnClickListener() {
                @Override public void onClick(View view) {
                    store.setStoreName(mStoreName.getText().toString());
                    store.setStoreNumber(mStoreNumber.getText().toString());
                    mDatabase.getStoreDao().updateStore(store);
                    Intent intent = new Intent(AddStoreActivity.this, StoreListActivity.class);
                    startActivity(intent);
                }
            });
        }

        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Fetch data and create store object
                store = new Store(0, mStoreName.getText().toString(),
                        mStoreNumber.getText().toString());

                // create worker thread to insert data into database
                new InsertTask(AddStoreActivity.this, store).execute();
            }
        });
    }

    private void setResult(Store store, int flag){
        setResult(flag, new Intent().putExtra("store", (Serializable) store));
        finish();
    }

    private static class InsertTask extends AsyncTask<Void,Void,Boolean> {

        private WeakReference<AddStoreActivity> activityReference;
        private Store store;

        // only retain a weak reference to the activity
        InsertTask(AddStoreActivity context, Store store) {
            activityReference = new WeakReference<AddStoreActivity>(context);
            this.store = store;
        }

        @Override
        protected Boolean doInBackground(Void... voids) {
            activityReference.get().mDatabase.getStoreDao().insertStore(store);
            return true;
        }

        // onPostExecute runs on main thread
        @Override
        protected void onPostExecute(Boolean bool) {
            if (bool){
                activityReference.get().setResult(store, 1);
            }
        }
    }

}