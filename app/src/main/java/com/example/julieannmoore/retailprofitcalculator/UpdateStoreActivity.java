package com.example.julieannmoore.retailprofitcalculator;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.julieannmoore.retailprofitcalculator.mData.Store;
import com.example.julieannmoore.retailprofitcalculator.mDatabase.AppDatabase;

import java.io.Serializable;
import java.lang.ref.WeakReference;

public class UpdateStoreActivity extends AppCompatActivity {


    private TextInputEditText mStoreName, mStoreNumber;
    private AppDatabase mDatabase;
    private Store store;
    private boolean update;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_store);


        mStoreName = findViewById(R.id.et_store_name);
        mStoreNumber = findViewById(R.id.et_store_number);
        mDatabase = AppDatabase.getInstance(UpdateStoreActivity.this);
        final Button mButton = findViewById(R.id.bn_add);




    }

    private void setResult(Store store, int flag){
        setResult(flag, new Intent().putExtra("store", (Serializable) store));
        finish();
    }

    private static class InsertTask extends AsyncTask<Void,Void,Boolean> {

        private WeakReference<UpdateStoreActivity> activityReference;
        private Store store;

        // only retain a weak reference to the activity
        InsertTask(UpdateStoreActivity context, Store store) {
            activityReference = new WeakReference<UpdateStoreActivity>(context);
            this.store = store;
        }

        @Override
        protected Boolean doInBackground(Void... voids) {
            activityReference.get().mDatabase.getStoreDao().updateStore(store);
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