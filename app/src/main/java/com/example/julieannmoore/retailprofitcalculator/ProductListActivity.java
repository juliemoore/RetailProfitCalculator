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
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.julieannmoore.retailprofitcalculator.mAdapter.ProductAdapter;
import com.example.julieannmoore.retailprofitcalculator.mAdapter.StoreAdapter;
import com.example.julieannmoore.retailprofitcalculator.mData.Product;
import com.example.julieannmoore.retailprofitcalculator.mData.Store;
import com.example.julieannmoore.retailprofitcalculator.mDatabase.AppDatabase;
import com.example.julieannmoore.retailprofitcalculator.mDialogs.CustomProductDialog;
import com.example.julieannmoore.retailprofitcalculator.mDialogs.CustomStoreDialog;
import com.example.julieannmoore.retailprofitcalculator.mUtilities.ProductListEventCallbacks;
import com.example.julieannmoore.retailprofitcalculator.mUtilities.StoreListEventCallbacks;

import java.lang.ref.WeakReference;
import java.util.List;

public class ProductListActivity extends AppCompatActivity implements ProductListEventCallbacks{

    private TextView mTitle, mStoreName, mStoreNumber;
    private Button mButton;
    private FloatingActionButton mFab;
    private ListView mListView;
    private ProductAdapter mAdapter;
    private AppDatabase mDatabase;
    private List<Product> mProductsList;
    private ProductListEventCallbacks mListCallbacks;
    private Product listItem;
    private Store mStore;
    private int mStoreId, mItemId;
    private String storeName, storeNumber;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_list);

        mTitle = findViewById(R.id.productList_title);
        mStoreName = findViewById(R.id.storeNameTextView);
        mStoreNumber = findViewById(R.id.storeNumberTextView);

        // Get Store Data and display in textviews
        if( (mStore = (Store) getIntent().getSerializableExtra("Store")) != null) {
            mStoreId = mStore.getStoreId();
            storeName = mStore.getStoreName();
            storeNumber = mStore.getStoreNumber();
            mStoreName.setText(storeName);
            mStoreNumber.setText(storeNumber);
        }
        // Get data from database
        mDatabase = AppDatabase.getInstance(this);
        mProductsList = mDatabase.getProductDao().findByStoreId(mStoreId);
        if (mProductsList.size() == 0) {
            mTitle.setText(getString(R.string.empty_product_list));
        }
        mListView = findViewById(R.id.list_view);

        //Initiate adapter
        mAdapter = new ProductAdapter(getApplicationContext(), mProductsList);
        // Update product list if product updated
        if( (listItem = (Product) getIntent().getSerializableExtra("Update")) != null) {
            mAdapter.EditItem(mItemId, listItem);
            mAdapter.notifyDataSetChanged();
        }

        mListView.setAdapter(mAdapter);
        mListCallbacks = (ProductListEventCallbacks)this;
        listItem = new Product();

        new RetrieveProductTask(this).execute();

        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // Display dialog box
                Toast.makeText(getApplicationContext(), "Clicked product id = " + view.getTag(), Toast.LENGTH_SHORT).show();
                // Get store object and pass to custom dialog
                if(mListCallbacks == null) { return; }
                listItem = mAdapter.getProductItem(position);
                mItemId = position;
                mListCallbacks.Select(listItem, mAdapter, position);
            }
        });

        mButton = findViewById(R.id.bn_add_product);
        mButton.setOnClickListener(new View.OnClickListener() {
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


    private static class RetrieveProductTask extends AsyncTask<Void,Void,List<Product>> {

        private WeakReference<ProductListActivity> activityReference;

        // only retain a weak reference to the activity
        RetrieveProductTask(ProductListActivity context) {
            activityReference = new WeakReference<>(context);
        }

        @Override
        protected List<Product> doInBackground(Void... voids) {
            if (activityReference.get()!=null)
                return activityReference.get().mDatabase.getProductDao().getProducts();
            else
                return null;
        }

        @Override
        protected void onPostExecute(List<Product> products) {
            if (products!=null && products.size()>0 ){
                activityReference.get().mProductsList.clear();
                activityReference.get().mProductsList.addAll(products);
                // hides empty text view
                activityReference.get().mAdapter.notifyDataSetChanged();
            }
        }
    }

    @Override
    public void Select(Product item, ProductAdapter adapter, int itemId) {
        // Create custom dialog
        CustomProductDialog mCustomDialog = new CustomProductDialog(ProductListActivity.this);
        mCustomDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        mCustomDialog.InitializeProductData(item, adapter, itemId);
        mCustomDialog.setCancelable(true);
        mCustomDialog.show();

        Toast.makeText(this, "Selected store is " + item.toString(),
                Toast.LENGTH_LONG).show();
    }

}
