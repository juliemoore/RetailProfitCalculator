package com.example.julieannmoore.retailprofitcalculator;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;
import com.example.julieannmoore.retailprofitcalculator.mAdapter.StoreAdapter;
import com.example.julieannmoore.retailprofitcalculator.mAdapter.StoreListEventCallbacks;
import com.example.julieannmoore.retailprofitcalculator.mData.Store;
import com.example.julieannmoore.retailprofitcalculator.mDatabase.AppDatabase;
import java.util.ArrayList;
import java.util.List;

public class StoreListActivity extends AppCompatActivity implements StoreListEventCallbacks {

    private FloatingActionButton mFab;
    private TextView storeNameTextView, storeNumberTextView, textViewMsg;
    private CardView cardView;
    private ListView mListView;
    private StoreAdapter mAdapter;
    private AppDatabase mDatabase;
    private List<Store> stores;
    private StoreListEventCallbacks mListCallbacks;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_store_list);
        textViewMsg = findViewById(R.id.store_info);
        mDatabase = AppDatabase.getInstance(this);
        stores = mDatabase.getStoreDao().getStores();
        String info = "";
        for (Store store : stores) {
            int id = store.getStoreId();
            String storeName = store.getStoreName();
            String storeNumber = store.getStoreNumber();
            info +=  "StoreId: " + id +
                    "\nStore name: " + storeName +
                    "\n Store number: " + storeNumber +
                    "\n\n";
        }
        textViewMsg.setText(info);
        mFab = (FloatingActionButton) findViewById(R.id.add_store_fab);
        mFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(StoreListActivity.this, ProductListActivity.class);
                startActivity(intent);
            }
        });

    }

    private void initializeViews(){
        storeNameTextView = findViewById(R.id.textView1);
        storeNumberTextView = findViewById(R.id.textView2);
        //textViewMsg = findViewById(R.id.store_list_message);
        cardView = findViewById(R.id.cardView);
        //mFab = (FloatingActionButton) findViewById(R.id.add_store_fab);
        mListView = findViewById(R.id.list_view);
        stores = new ArrayList<>();
        //mAdapter = new StoreAdapter(this, stores);
        mListView.setAdapter(mAdapter);
    }

    @Override
    public void Select(Object item, StoreAdapter adapter, int itemId) {

    }
}
