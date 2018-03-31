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
import android.widget.Toast;

import com.example.julieannmoore.retailprofitcalculator.mAdapter.ProductAdapter;
import com.example.julieannmoore.retailprofitcalculator.mAdapter.StoreAdapter;
import com.example.julieannmoore.retailprofitcalculator.mData.Product;
import com.example.julieannmoore.retailprofitcalculator.mData.Store;
import com.example.julieannmoore.retailprofitcalculator.mDatabase.AppDatabase;
import com.example.julieannmoore.retailprofitcalculator.mUtilities.ProductListEventCallbacks;
import com.example.julieannmoore.retailprofitcalculator.mUtilities.StoreListEventCallbacks;

import java.util.List;

public class ProductListActivity extends AppCompatActivity implements ProductListEventCallbacks{

    private List<Product> mProductsList;
    private Button mButton;
    private FloatingActionButton mFab;
    private ListView mListView;
    private StoreAdapter mStoreAdapter;
    private ProductAdapter mAdapter;
    private AppDatabase mDatabase;
    private ProductListEventCallbacks mListCallbacks;
    private Store store;
    private Product listItem;
    private int mStoreId;
    private String mStoreName, mStoreNumber;

    // Method to set store information to display store info in next
    // activities. StoreId is also the foreign key in the list of products
    public void InitializeStoreData(Store store, StoreAdapter adapter) {
        this.store = store;
        mStoreAdapter = adapter;
        mStoreId = store.getStoreId();
        mStoreName = store.getStoreName();
        mStoreNumber = store.getStoreNumber();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_list);
        mDatabase = AppDatabase.getInstance(this);
        mProductsList = mDatabase.getProductDao().getProducts();


        mButton = findViewById(R.id.bn_add_product);
        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ProductListActivity.this, AddProductActivity.class);
                startActivity(intent);
            }
        });
        if (mProductsList.size() == 0) {
            String message = getString(R.string.empty_product_list).toString();
            Toast.makeText(ProductListActivity.this, message, Toast.LENGTH_SHORT).show();
        }

        mListView = findViewById(R.id.list_view);

        //Initiate adapter
        mAdapter = new ProductAdapter(getApplicationContext(), mProductsList);
        mListView.setAdapter(mAdapter);

        mListCallbacks = (ProductListEventCallbacks)this;
        listItem = new Product();

        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // Display dialog box
                Toast.makeText(getApplicationContext(), "Clicked product id = " + view.getTag(), Toast.LENGTH_SHORT).show();

                // Get store object and pass to custom dialog
                if(mListCallbacks == null) { return; }
                listItem = mAdapter.getProductItem(position);
                mListCallbacks.Select(listItem, mAdapter, position);
           }
        });

        mButton = findViewById(R.id.bn_add_product);
        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(ProductListActivity.this, AddProductActivity.class);
                startActivity(intent);
            }
        });

        if(StoreListEventCallbacks.class.isInstance(this)){
            mListCallbacks = (ProductListEventCallbacks) this;
        }

    }

    @Override
    public void Select(Product item, ProductAdapter adapter, int itemId) {
        // Create custom dialog
        CustomDialog mCustomDialog = new CustomDialog(ProductListActivity.this);
        mCustomDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        mCustomDialog.setCancelable(true);
        mCustomDialog.show();
        Toast.makeText(this, "Selected store is " + item.toString(),
                Toast.LENGTH_LONG).show();
    }
}
