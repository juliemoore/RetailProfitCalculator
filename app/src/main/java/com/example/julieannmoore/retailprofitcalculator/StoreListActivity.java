package com.example.julieannmoore.retailprofitcalculator;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.julieannmoore.retailprofitcalculator.mAdapter.StoreAdapter;
import com.example.julieannmoore.retailprofitcalculator.mDialogs.CustomStoreDialog;
import com.example.julieannmoore.retailprofitcalculator.mUtilities.StoreListEventCallbacks;
import com.example.julieannmoore.retailprofitcalculator.mData.Store;
import com.example.julieannmoore.retailprofitcalculator.mDatabase.AppDatabase;

import java.lang.ref.WeakReference;
import java.util.List;

public class StoreListActivity extends AppCompatActivity implements StoreListEventCallbacks{

    private TextView mTitle;
    private FloatingActionButton mFab;
    private ListView mListView;
    private StoreAdapter mAdapter;
    private AppDatabase mDatabase;
    private List<Store> mStoreList;
    private StoreListEventCallbacks mListCallbacks;
    private Store listItem;
    private int mItemId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_store_list);

        mTitle = findViewById(R.id.storeList_title);
        // Get data from database
        mDatabase = AppDatabase.getInstance(this);
        mDatabase.cleanUp();
        mStoreList = mDatabase.getStoreDao().getStores();
        if (mStoreList.size() == 0) {
            mTitle.setText(getString(R.string.empty_store_list));
        }
        mListView = findViewById(R.id.list_view);

        //Initiate adapter
        mAdapter = new StoreAdapter(getApplicationContext(), mStoreList);
        if( (listItem = (Store) getIntent().getSerializableExtra("Update")) != null) {
            mAdapter.EditItem(mItemId, listItem);
            mAdapter.notifyDataSetChanged();
        }
        mListView.setAdapter(mAdapter);
        mListCallbacks = (StoreListEventCallbacks)this;
        listItem = new Store();

        new RetrieveTask(this).execute();

        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // Display dialog box
                Toast.makeText(getApplicationContext(), "Clicked store id = " + view.getTag(), Toast.LENGTH_SHORT).show();
                // Get store object and pass to custom dialog
                if(mListCallbacks == null) { return; }
                listItem = mAdapter.getStoreItem(position);
                mItemId = position;
                mListCallbacks.Select(listItem, mAdapter, position);
            }
        });

        mFab = (FloatingActionButton) findViewById(R.id.add_store_fab);
        mFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(StoreListActivity.this, AddStoreActivity.class);
                startActivity(intent);
            }
        });

        if(StoreListEventCallbacks.class.isInstance(this)){
            mListCallbacks = (StoreListEventCallbacks) this;
        }

    }


    private static class RetrieveTask extends AsyncTask<Void,Void,List<Store>> {

        private WeakReference<StoreListActivity> activityReference;

        // only retain a weak reference to the activity
        RetrieveTask(StoreListActivity context) {
            activityReference = new WeakReference<>(context);
        }

        @Override
        protected List<Store> doInBackground(Void... voids) {
            if (activityReference.get()!=null)
                return activityReference.get().mDatabase.getStoreDao().getStores();
            else
                return null;
        }

        @Override
        protected void onPostExecute(List<Store> stores) {
            if (stores!=null && stores.size()>0 ){
                activityReference.get().mStoreList.clear();
                activityReference.get().mStoreList.addAll(stores);
                // hides empty text view
                activityReference.get().mAdapter.notifyDataSetChanged();
            }
        }
    }

    @Override
    public void Select(Store item, StoreAdapter adapter, int itemId) {
        // Create custom dialog
        CustomStoreDialog mCustomDialog = new CustomStoreDialog(StoreListActivity.this);
        mCustomDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        mCustomDialog.InitializeData(item, adapter, itemId);
        mCustomDialog.setCancelable(true);
        mCustomDialog.show();

        Toast.makeText(this, "Selected store is " + item.toString(),
                Toast.LENGTH_LONG).show();
    }

}
