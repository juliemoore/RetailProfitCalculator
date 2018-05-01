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
import android.widget.Button;
import android.widget.Filter;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.julieannmoore.retailprofitcalculator.R;
import com.example.julieannmoore.retailprofitcalculator.mAdapter.ProductAdapter;
import com.example.julieannmoore.retailprofitcalculator.mData.Product;
import com.example.julieannmoore.retailprofitcalculator.mData.Store;
import com.example.julieannmoore.retailprofitcalculator.mDatabase.AppDatabase;
import com.example.julieannmoore.retailprofitcalculator.mDialogs.CustomProductDialog;
import com.example.julieannmoore.retailprofitcalculator.mUtilities.ProductListEventCallbacks;
import com.example.julieannmoore.retailprofitcalculator.mUtilities.StoreListEventCallbacks;

import java.lang.ref.WeakReference;
import java.util.List;

public class ProductListActivity extends AppCompatActivity implements ProductListEventCallbacks, SearchView.OnQueryTextListener {

    private ActionBar mActionBar;
    private Filter mFilter;
    private TextView mTitle, mStoreName, mStoreNumber;
    private Button mButton;
    private FloatingActionButton mFab;
    private ListView mListView;
    private ProductAdapter mAdapter;
    private AppDatabase mDatabase;
    private List<Product> mProductsList;
    private ProductListEventCallbacks mListCallbacks;
    private CustomProductDialog mCustomDialog;
    private View mView;
    private Product listItem, product;
    private Store mStore;
    private int mStoreId, mItemId;
    private String storeName, storeNumber;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_list);

        // Set action bar with logo
        mActionBar = getSupportActionBar();
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        mActionBar.setLogo(R.mipmap.ic_launcher_round);
        mActionBar.setDisplayUseLogoEnabled(true);

        mView = findViewById(android.R.id.content);
        mTitle = findViewById(R.id.productList_title);
        mStoreName = findViewById(R.id.storeNameTextView);
        mStoreNumber = findViewById(R.id.storeNumberTextView);
        mListView = findViewById(R.id.list_view);
        mFab = findViewById(R.id.add_product_fab);
        product = new Product();
        listItem = new Product();


        // Get data from database
        mDatabase = AppDatabase.getInstance(this);

        // Get Store Data and display in textviews
        if( (mStore = (Store) getIntent().getSerializableExtra("Store")) != null) {
            mStoreId = mStore.getStoreId();
            storeName = mStore.getStoreName();
            storeNumber = mStore.getStoreNumber();
            mStoreName.setText(storeName);
            mStoreNumber.setText("#" + storeNumber);
            mProductsList = mDatabase.getProductDao().findByStoreId(mStoreId);
        } else if( (product = (Product) getIntent().getSerializableExtra("Updated Product")) != null) {
            int storeId = product.getStoreId();
            mStore = mDatabase.getStoreDao().findByStoreId(storeId);
            storeName = mStore.getStoreName();
            storeNumber = mStore.getStoreNumber();
            mStoreName.setText(storeName);
            mStoreNumber.setText("#" + storeNumber);
            mStoreId = product.getStoreId();
            mProductsList = mDatabase.getProductDao().findByStoreId(mStoreId);
        }
        mProductsList = mDatabase.getProductDao().findByStoreId(mStoreId);
        if (mProductsList.size() == 0) {
            mTitle.setText(getString(R.string.empty_product_list));
        }

        //Initiate adapter
        mAdapter = new ProductAdapter(getApplicationContext(), mProductsList);
        mListView.setAdapter(mAdapter);

        mListCallbacks = (ProductListEventCallbacks)this;

        new RetrieveProductTask(this).execute();

        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // Display dialog box
                // Get store object and pass to custom dialog
                if(mListCallbacks == null) { return; }
                listItem = mAdapter.getProductItem(position);
                mItemId = position;
                mListCallbacks.Select(listItem, mAdapter, position);
            }
        });

        //mButton = findViewById(R.id.bn_add_product);
        mFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ProductListActivity.this, AddProductActivity.class);
                intent.putExtra("Store", mStore);
                startActivity(intent);
            }
        });

        if(StoreListEventCallbacks.class.isInstance(this)){
            mListCallbacks = (ProductListEventCallbacks) this;
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

    // Get Products AsyncTask
    private class RetrieveProductTask extends AsyncTask<Object, Object, List<Product>> {
        AppDatabase mDatabase;
        private WeakReference<ProductListActivity> activityReference;

        // only retain a weak reference to the activity
        RetrieveProductTask(ProductListActivity context) {
            activityReference = new WeakReference<>(context);
        }

        @Override
        protected List<Product> doInBackground(Object... params) {
            // Open the database
            mDatabase = AppDatabase.getInstance(ProductListActivity.this);

            return mDatabase.getProductDao().findByStoreId(mStoreId);
        }

        protected void onPostExecute(List<Product> products) {
            if (products!=null && products.size()>0 ){
                activityReference.get().mProductsList.clear();
                activityReference.get().mProductsList.addAll(products);
                // hides empty text view
                activityReference.get().mAdapter.notifyDataSetChanged();
            }
        }
    }

    public void getData() {
        AsyncTask.execute(new Runnable() {
            @Override
            public void run() {
                AsyncTask.execute(new Runnable() {
                    @Override
                    public void run() {

                    }
                });
            }
        });
    }

    @Override
    public void Select(Product item, ProductAdapter adapter, int itemId) {
        // Create custom dialog
        mCustomDialog = new CustomProductDialog(ProductListActivity.this);
        mCustomDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        mCustomDialog.InitializeProductData(item, adapter, itemId);
        mCustomDialog.setCancelable(true);
        mCustomDialog.show();
    }

    @Override
    protected void onPause() {
        super.onPause();
        this.mView.setVisibility(View.GONE);
    }

    @Override
    public void onDestroy(){
        super.onDestroy();
        if ( mCustomDialog!=null && mCustomDialog.isShowing() ){
            mCustomDialog.cancel();
        }
    }

    private void toastMessage(String message){
        Toast.makeText(this,message, Toast.LENGTH_SHORT).show();
    }

}