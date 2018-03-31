package com.example.julieannmoore.retailprofitcalculator;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.julieannmoore.retailprofitcalculator.mAdapter.StoreAdapter;
import com.example.julieannmoore.retailprofitcalculator.mUtilities.InititializeStoreData;
import com.example.julieannmoore.retailprofitcalculator.mUtilities.StoreListEventCallbacks;
import com.example.julieannmoore.retailprofitcalculator.mData.Store;
import com.example.julieannmoore.retailprofitcalculator.mDatabase.AppDatabase;
import java.util.List;

public class StoreListActivity extends AppCompatActivity implements StoreListEventCallbacks{

    private TextView mTitle, mStoreName, mStoreNumber;
    private FloatingActionButton mFab;
    private Button mEdit, mDelete, mView;
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

        mListCallbacks = (StoreListEventCallbacks)this;
        listItem = new Store();

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

    @Override
    public void Select(Store item, StoreAdapter adapter, int itemId) {
        // Create custom dialog
        CustomDialog mCustomDialog = new CustomDialog(StoreListActivity.this);
        mCustomDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        mCustomDialog.setCancelable(true);
        mCustomDialog.show();

        Toast.makeText(this, "Selected store is " + item.toString(),
                Toast.LENGTH_LONG).show();
        /*mEdit = findViewById(R.id.bn_update_store);
        mEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListCallbacks.Select(listItem, mAdapter, mItemId);

                String message = getString(R.string.update);
                Intent intent = new Intent(StoreListActivity.this, AddStoreActivity.class);
                intent.putExtra("Update", message);
                startActivity(intent);
            }
        });
        */
    }

}
