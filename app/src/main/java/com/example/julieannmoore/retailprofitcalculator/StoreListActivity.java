package com.example.julieannmoore.retailprofitcalculator;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.julieannmoore.retailprofitcalculator.mAdapter.StoreAdapter;
import com.example.julieannmoore.retailprofitcalculator.mAdapter.StoreListEventCallbacks;
import com.example.julieannmoore.retailprofitcalculator.mData.Store;
import com.example.julieannmoore.retailprofitcalculator.mDatabase.AppDatabase;
import java.util.ArrayList;
import java.util.List;

public class StoreListActivity extends AppCompatActivity implements StoreListEventCallbacks {

    private FloatingActionButton mFab;
    private ListView mListView;
    private StoreAdapter mAdapter;
    private AppDatabase mDatabase;
    private List<Store> mStoreList;
    private StoreListEventCallbacks mListCallbacks;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_store_list);

        // Get data from database
        mDatabase = AppDatabase.getInstance(this);
        mStoreList = mDatabase.getStoreDao().getStores();
        if (mStoreList.size() == 0) {
            String message = getString(R.string.empty_store_list).toString();
            Toast.makeText(StoreListActivity.this, message, Toast.LENGTH_SHORT).show();
        }

        mListView = findViewById(R.id.list_view);

        //Initiate adapter
        mAdapter = new StoreAdapter(getApplicationContext(), mStoreList);
        mListView.setAdapter(mAdapter);

        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // Display dialog box
                Toast.makeText(getApplicationContext(), "Clicked store id = " + view.getTag(), Toast.LENGTH_SHORT).show();
            }
        });

        mFab = (FloatingActionButton) findViewById(R.id.add_store_fab);
        mFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(StoreListActivity.this, ProductListActivity.class);
                startActivity(intent);
            }
        });

    }


    @Override
    public void Select(Object item, StoreAdapter adapter, int itemId) {

    }
}
