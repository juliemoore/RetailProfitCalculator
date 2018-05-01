package com.example.julieannmoore.retailprofitcalculator.mDialogs;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;

import com.example.julieannmoore.retailprofitcalculator.mActivities.AddProductActivity;
import com.example.julieannmoore.retailprofitcalculator.mActivities.ProductListActivity;
import com.example.julieannmoore.retailprofitcalculator.R;
import com.example.julieannmoore.retailprofitcalculator.mActivities.StoreListActivity;
import com.example.julieannmoore.retailprofitcalculator.mActivities.SummaryActivity;
import com.example.julieannmoore.retailprofitcalculator.mAdapter.ProductAdapter;
import com.example.julieannmoore.retailprofitcalculator.mData.Product;
import com.example.julieannmoore.retailprofitcalculator.mDatabase.AppDatabase;
import com.example.julieannmoore.retailprofitcalculator.mUtilities.ProductListEventCallbacks;
import com.example.julieannmoore.retailprofitcalculator.mUtilities.StoreListEventCallbacks;

public class CustomProductDialog extends Dialog implements View.OnClickListener {

    public Activity mActivity;
    public Dialog mDialog;
    private AppDatabase mDatabase;
    private Product mItem;
    private ProductAdapter mAdapter;
    private int mItemId;
    private TextView mProduct, mProductId;
    private Button mUpdateButton, mDeleteButton, mViewButton;
    private ProductListEventCallbacks mListCallbacks;
    public void InitializeProductData(Product item, ProductAdapter adapter, int itemId) {
        mItem = item;
        mAdapter = adapter;
        mItemId = itemId;
    }

    // constructor
    public CustomProductDialog(Activity c) {
        super(c);
        this.mActivity = c;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.custom_product_dialog);

        mDatabase = AppDatabase.getInstance(mActivity);
        mDatabase.getProductDao().getProducts();
        if(StoreListEventCallbacks.class.isInstance(this)){
            mListCallbacks = (ProductListEventCallbacks) this;
        }

        mProduct = findViewById(R.id.textView1);
        mProductId = findViewById(R.id.textView2);
        mProduct.setText(mItem.getProductName());
        mProductId.setText("");

        mUpdateButton = findViewById(R.id.bn_update_product);
        mDeleteButton = findViewById(R.id.bn_delete_product);
        mViewButton = findViewById(R.id.bn_view_product);

        mUpdateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // intent to add store activity
                Intent intent = new Intent(mActivity, AddProductActivity.class);
                intent.putExtra("UpdateProduct", mItem);
                intent.putExtra("ItemId", mItemId);
                mActivity.startActivity(intent);
            }
        });
        mDeleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDatabase.getProductDao().deleteProduct(mItem);
                mAdapter.notifyDataSetChanged();
                Intent intent = new Intent(mActivity, StoreListActivity.class);
                mActivity.startActivity(intent);
            }
        });
        mViewButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mActivity, SummaryActivity.class);
                intent.putExtra("Calculate", mItem);
                mActivity.startActivity(intent);
            }
        });
    }

    @Override
    public void onClick(View v) {
        return;
    }
}
