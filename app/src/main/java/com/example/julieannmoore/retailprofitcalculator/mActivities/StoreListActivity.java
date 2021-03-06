package com.example.julieannmoore.retailprofitcalculator.mActivities;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Filter;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.TextView;

import com.example.julieannmoore.retailprofitcalculator.R;
import com.example.julieannmoore.retailprofitcalculator.mAdapter.StoreAdapter;
import com.example.julieannmoore.retailprofitcalculator.mDialogs.CustomStoreDialog;
import com.example.julieannmoore.retailprofitcalculator.mUtilities.StoreListEventCallbacks;
import com.example.julieannmoore.retailprofitcalculator.mData.Store;
import com.example.julieannmoore.retailprofitcalculator.mDatabase.AppDatabase;

import java.lang.ref.WeakReference;
import java.util.List;

public class StoreListActivity extends AppCompatActivity implements StoreListEventCallbacks, SearchView.OnQueryTextListener {

    private Filter mFilter;
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

        // Set action bar with logo
        ActionBar actionBar = getSupportActionBar();
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        actionBar.setLogo(R.mipmap.ic_launcher_round);
        actionBar.setDisplayUseLogoEnabled(true);

        mTitle = findViewById(R.id.storeList_title);
        // Get data from database
        mDatabase = AppDatabase.getInstance(this);
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
                if(mListCallbacks == null) { return; }
                listItem = mAdapter.getStoreItem(position);
                mItemId = position;
                mListCallbacks.Select(listItem, mAdapter, position);
            }
        });

        mFab = findViewById(R.id.add_store_fab);
        mFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(StoreListActivity.this, AddStoreActivity.class);
                startActivity(intent);
            }
        });

        if(StoreListEventCallbacks.class.isInstance(this)){
            mListCallbacks = this;
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater mMenuInflater = getMenuInflater();
        mMenuInflater.inflate(R.menu.menu_search, menu);
        MenuItem mMenuItem = menu.findItem(R.id.menuSearch);
        SearchView mSearchView = (SearchView) mMenuItem.getActionView();
        mSearchView.setOnQueryTextListener(this);
        mSearchView.setQueryHint(getString(R.string.search));
        mListView.setTextFilterEnabled(true);
        mFilter = mAdapter.getFilter();

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        if(TextUtils.isEmpty(newText)) {
            mFilter.filter(null);
        } else {
            mFilter.filter(newText);
        }
        return true;
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
    }

}
