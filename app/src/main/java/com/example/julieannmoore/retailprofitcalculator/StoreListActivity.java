package com.example.julieannmoore.retailprofitcalculator;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.provider.ContactsContract;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;
import android.widget.Toolbar;

import com.example.julieannmoore.retailprofitcalculator.mData.Store;
import com.example.julieannmoore.retailprofitcalculator.mDatabase.AppDatabase;
import com.example.julieannmoore.retailprofitcalculator.mRecycler.StoreAdapter;

import java.io.Serializable;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

public class StoreListActivity extends AppCompatActivity implements StoreAdapter.OnStoreItemClick {

    private TextView textViewMsg;
    private CardView cardView;
    private FloatingActionButton fab;
    private RecyclerView recyclerView;
    private AppDatabase mDatabase;
    private List<Store> stores;
    private Store store;
    private StoreAdapter storeAdapter;
    private StoreListCallbacks mListCallbacks;
    private int position;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_store_list);
        initializeViews();
        displayList();

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(StoreListActivity.this, AddStoreActivity.class);
                startActivity(intent);
            }
        });
    }

    private void displayList(){
        mDatabase = AppDatabase.getInstance(StoreListActivity.this);
        new RetrieveTask(this).execute();
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
            if (stores!=null && stores.size() > 0 ){
                activityReference.get().stores = stores;

                // hides empty tyexview
                activityReference.get().textViewMsg.setVisibility(View.GONE);

                // create and set the adapter on RecyclerView instance to display list
                activityReference.get().storeAdapter = new StoreAdapter(stores,activityReference.get());
                activityReference.get().recyclerView.setAdapter(activityReference.get().storeAdapter);
            }
        }
    }

    private void initializeViews(){
        textViewMsg = findViewById(R.id.store_list_message);
        cardView = findViewById(R.id.cardView);
        fab = (FloatingActionButton) findViewById(R.id.add_store_fab);
        fab.setOnClickListener(listener);
        recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(StoreListActivity.this));
        stores = new ArrayList<>();
        storeAdapter = new StoreAdapter(stores,StoreListActivity.this);
        recyclerView.setAdapter(storeAdapter);
    }

    private View.OnClickListener listener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            startActivityForResult(new Intent(StoreListActivity.this,ProductListActivity.class),100);
        }
    };

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 100 && resultCode > 0 ){
            if( resultCode == 1){
                stores.add((Store) data.getSerializableExtra("store"));
            }else if( resultCode == 2){
                stores.set(position,(Store) data.getSerializableExtra("store"));
            }else if( resultCode == 3){
                stores.set(position,(Store) data.getSerializableExtra("store"));
            }
            listVisibility();
        }
    }

    @Override
    public void onStoreClick(final int position) {
        new AlertDialog.Builder(StoreListActivity.this)
                .setTitle("Select Options")
                .setItems(new String[]{"Delete", "Update", "View"}, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        switch (i){
                            case 0:
                                mDatabase.getStoreDao().deleteStore(stores.get(position));
                                stores.remove(position);
                                listVisibility();
                                break;
                            case 1:
                                Intent updateIntent = new Intent(StoreListActivity.this, AddStoreActivity.class);
                                StoreListActivity.this.position = position;
                                store = stores.get(position);
                                Bundle bundle = new Bundle();
                                bundle.putSerializable("store", store);
                                updateIntent.putExtras(bundle);
                                startActivity(updateIntent);
                                break;
                            case 2:
                                Intent intent = new Intent(StoreListActivity.this, AddStoreActivity.class);
                                StoreListActivity.this.position = position;
                                store = stores.get(position);
                                Bundle newBundle = new Bundle();
                                newBundle.putSerializable("store", store);
                                intent.putExtras(newBundle);
                                startActivity(intent);
                        }
                    }
                }).show();
    }

    private void listVisibility(){
        int emptyMsgVisibility = View.GONE;
        int emptyStoreListVisibility = View.VISIBLE;
        if (stores.size() == 0){ // no item to display
            if (textViewMsg.getVisibility() == View.GONE)
                emptyMsgVisibility = View.VISIBLE;
                emptyStoreListVisibility = View.GONE;
                cardView.setVisibility(emptyStoreListVisibility);
        }
        textViewMsg.setVisibility(emptyMsgVisibility);
        cardView.setVisibility(emptyStoreListVisibility);
        storeAdapter.notifyDataSetChanged();
    }

    @Override
    protected void onDestroy() {
        mDatabase.cleanUp();
        super.onDestroy();
    }

    public interface StoreListCallbacks extends Serializable {
        void Select(Object item, StoreAdapter adapter, int itemId);
    }
}
