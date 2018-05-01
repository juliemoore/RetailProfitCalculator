package com.example.julieannmoore.retailprofitcalculator.mActivities;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.julieannmoore.retailprofitcalculator.R;
import com.example.julieannmoore.retailprofitcalculator.mData.Product;
import com.example.julieannmoore.retailprofitcalculator.mData.Store;
import com.example.julieannmoore.retailprofitcalculator.mDatabase.AppDatabase;

import java.lang.ref.WeakReference;

public class AddProductActivity extends AppCompatActivity {

    private static final String TAG = AddProductActivity.class.getSimpleName();
    private TextView mStoreName, mStoreNumber;
    private TextInputEditText mProductId, mProductName, mCostOfGoods, mSellingPrice, mAnnualUnitsSold,
            mAveWeeklyInventory, mLinearFt;
    private TextInputLayout mProductIdWrapper, mProductNameWrapper, mCostOfGoodsWrapper, mSellingPriceWrapper,
            mAnnualUnitsSoldWrapper, mAveWeeklyInventoryWrapper, mLinearFtWrapper;
    private Button mButton;
    private AppDatabase mDatabase;
    private Product mProduct;
    private Store mStore;
    private int storeId, productId;
    private String storeName, storeNumber, productName;
    private double costOfGoods, sellingPrice, annualUnitsSold,
            aveWeeklyInventory, linearFt;
    private Boolean isValid, isUpdate;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_product);

        // Set action bar with logo
        ActionBar actionBar = getSupportActionBar();
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        actionBar.setLogo(R.mipmap.ic_launcher_round);
        actionBar.setDisplayUseLogoEnabled(true);

        mDatabase = AppDatabase.getInstance(this);
        mProduct = new Product();
        initializeView();
        getStoreData();
        isUpdate = false;
        isValid = false;

        Intent intent = getIntent();
        // Get Product Data and display in textviews
        if ((mProduct = (Product) intent.getSerializableExtra("UpdateProduct")) != null) {
            isUpdate = true;
            setTitle(getString(R.string.update_product));
            mButton.setText(R.string.update);
            storeId = mProduct.getStoreId();
            productId = mProduct.getProductId();
            mStore = mDatabase.getStoreDao().findByStoreId(storeId);
            storeName = mStore.getStoreName();
            storeNumber = mStore.getStoreNumber();
            mStoreName.setText(storeName);
            mStoreNumber.setText(storeNumber);
            mProduct.toString();
            if (isUpdate == true) {
                updateUI();
            }
       }

        mButton.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            getTextInput();
            if (isValid == true) {
                hideKeyboard();
                if (isUpdate == true) {
                    mProduct = new Product(productId, productName, storeId, costOfGoods, sellingPrice,
                            annualUnitsSold, aveWeeklyInventory, linearFt);
                    mDatabase.getProductDao().updateProduct(mProduct);
                    toastMessage(getString(R.string.update_product_successful));
                    Intent intent = new Intent(AddProductActivity.this, SummaryActivity.class);
                    intent.putExtra("Calculate", mProduct);
                    startActivity(intent);
                    clear();
                } else {
                    // Create new product object
                    mProduct = new Product(0, productName, storeId, costOfGoods, sellingPrice,
                            annualUnitsSold, aveWeeklyInventory, linearFt);
                    new InsertProductTask(AddProductActivity.this, mProduct).execute();
                    toastMessage(getString(R.string.save_product_successful));
                    Intent intent = new Intent(AddProductActivity.this, SummaryActivity.class);
                    intent.putExtra("Calculate", mProduct);
                    startActivity(intent);
                    clear();
                }
            } else {
                toastMessage(getString(R.string.invalid_data));
            }
        }
        });
    }

    public void initializeView() {
        mStoreName = findViewById(R.id.textView1);
        mStoreNumber = findViewById(R.id.textView2);
        mProductName = findViewById(R.id.productEditText);
        mCostOfGoods = findViewById(R.id.costOfGoodsEditText);
        mSellingPrice = findViewById(R.id.sellingPriceEditText);
        mAnnualUnitsSold = findViewById(R.id.annualUnitsSoldEditText);
        mAveWeeklyInventory = findViewById(R.id.aveWeeklyInventoryEditText);
        mLinearFt = findViewById(R.id.linearFtEditText);

        mProductNameWrapper = findViewById(R.id.productNameWrapper);
        mCostOfGoodsWrapper = findViewById(R.id.costOfGoodsWrapper);
        mSellingPriceWrapper = findViewById(R.id.sellingPriceWrapper);
        mAnnualUnitsSoldWrapper = findViewById(R.id.annualUnitsSoldWrapper);
        mAveWeeklyInventoryWrapper = findViewById(R.id.aveWeeklyInventoryWrapper);
        mLinearFtWrapper = findViewById(R.id.linearFtWrapper);

        mProductNameWrapper.setHint(getString(R.string.product));
        mCostOfGoodsWrapper.setHint(getString(R.string.cost_of_goods));
        mSellingPriceWrapper.setHint(getString(R.string.selling_price));
        mAnnualUnitsSoldWrapper.setHint(getString(R.string.annual_units_sold));
        mAveWeeklyInventoryWrapper.setHint(getString(R.string.ave_weekly_inventory));
        mLinearFtWrapper.setHint(getString(R.string.linear_ft_sales_floor));

        mButton = findViewById(R.id.calculateButton);
    }

    private void getStoreData() {
        // Get Store Data and display in textviews
        if( (mStore = (Store) getIntent().getSerializableExtra("Store")) != null) {
            storeId = mStore.getStoreId();
            storeName = mStore.getStoreName();
            storeNumber = mStore.getStoreNumber();
            mStoreName.setText(storeName);
            mStoreNumber.setText(storeNumber);
        }
    }

    private void updateUI() {
        if (mProduct.getProductName() != null) {
            productName = mProduct.getProductName();
            mProductName.setText(productName);
        }
        if (mProduct.getCostOfGoods() > 0) {
            Double costOfGoods = mProduct.getCostOfGoods();
            mCostOfGoods.setText(costOfGoods.toString());
        }
        if (mProduct.getSellingPrice() > 0) {
            Double sellingPrice = mProduct.getSellingPrice();
            mSellingPrice.setText(sellingPrice.toString());
        }
        if (mProduct.getAnnualUnitsSold() > 0) {
            Double annualUnitsSold = mProduct.getAnnualUnitsSold();
            mAnnualUnitsSold.setText(annualUnitsSold.toString());
        }
        if (mProduct.getAveWeeklyInventory() > 0) {
            Double aveWeeklyInventory = mProduct.getAveWeeklyInventory();
            mAveWeeklyInventory.setText(aveWeeklyInventory.toString());
        }
        if (mProduct.getLinearFeet() > 0) {
            Double linearFeet = mProduct.getLinearFeet();
            mLinearFt.setText(linearFeet.toString());
        }
    }

    public void getTextInput() {
        if (mProductNameWrapper.getEditText().getText().toString().isEmpty()) {
            mProductNameWrapper.setError(getString(R.string.product_name_required));
            mProductName.requestFocus();
            isValid = false;
        } else {
            productName = mProductName.getText().toString();
            mProductNameWrapper.setErrorEnabled(false);
        }
        if (mCostOfGoodsWrapper.getEditText().toString().isEmpty()) {
            mCostOfGoodsWrapper.setError(getString(R.string.cog_required));
            mCostOfGoods.requestFocus();
            isValid = false;
        } else {
            mCostOfGoodsWrapper.setErrorEnabled(false);
        }
        try {
            costOfGoods = Double.parseDouble(mCostOfGoods.getText().toString());
        } catch (NumberFormatException ex) {
            toastMessage(getString(R.string.cog_nan));
            mCostOfGoods.setText("");
            mCostOfGoods.requestFocus();
            isValid = false;
        }
        if (mSellingPriceWrapper.getEditText().getText().toString().isEmpty()) {
            mSellingPriceWrapper.setError(getString(R.string.sp_required));
            mSellingPrice.requestFocus();
            isValid = false;
        } else {
            mSellingPriceWrapper.setErrorEnabled(false);
        }
        try {
            sellingPrice = Double.parseDouble(mSellingPrice.getText().toString());
            isValid = true;
        } catch (NumberFormatException ex) {
            toastMessage("Selling price is not a number");
            mSellingPrice.setText("");
            mSellingPrice.requestFocus();
            isValid = false;
        }
        try {
            annualUnitsSold = Double.parseDouble(mAnnualUnitsSold.getText().toString());
            isValid = true;
            mAnnualUnitsSoldWrapper.setErrorEnabled(false);
        } catch (NumberFormatException ex) {
            mAnnualUnitsSoldWrapper.setError("Annual units sold inventory is not a number");
            mAnnualUnitsSold.setText("");
            mAveWeeklyInventory.requestFocus();
            isValid = false;
        }
        try {
            aveWeeklyInventory = Double.parseDouble(mAveWeeklyInventory.getText().toString());
            mAveWeeklyInventoryWrapper.setErrorEnabled(false);
            if (aveWeeklyInventory == 0) {
                aveWeeklyInventory = .001; // if set to 0, will return divide by zero exception
            }
        } catch (NumberFormatException ex) {
            mAveWeeklyInventoryWrapper.setError("Average weekly inventory is not a number");
            mAveWeeklyInventory.setText("");
            mAveWeeklyInventory.requestFocus();
            isValid = false;
        } catch (ArithmeticException e) {
            mAveWeeklyInventoryWrapper.setError("Divide by zero exception. \n" +
                    "Average weekly inventory must be greater than 0");
            mAveWeeklyInventory.setText("");
            mAveWeeklyInventory.requestFocus();
            isValid = false;
        }
        try {
            linearFt = Double.parseDouble(mLinearFt.getText().toString());
            mLinearFtWrapper.setErrorEnabled(false);
        } catch (NumberFormatException ex) {
            mLinearFtWrapper.setError("Linear feet is not a number");
            mLinearFt.setText("");
            mLinearFt.requestFocus();
            isValid = false;
        } catch (ArithmeticException e) {
            mLinearFtWrapper.setError("Divide by zero exception. \n" +
                    "Linear feet must be greater than 0");
            mAveWeeklyInventory.requestFocus();
            isValid = false;
        }
    }

    private void clear() {
        // clear text boxes
        mStoreName.setText("");
        mStoreNumber.setText("");
        mProductName.setText("");
        mCostOfGoods.setText("");
        mSellingPrice.setText("");
        mAveWeeklyInventory.setText("");
        mLinearFt.setText("");
    }

    private void hideKeyboard() {
        View view = getCurrentFocus();
        if (view != null) {
            ((InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE)).
                    hideSoftInputFromWindow(view.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
        }
    }

    private void toastMessage(String message){
        Toast.makeText(this,message, Toast.LENGTH_SHORT).show();
    }

    private static class InsertProductTask extends AsyncTask<Void, Void, Boolean> {
        private WeakReference<AddProductActivity> activityReference;
        private Product product;

        // only retain a weak reference to the activity
        InsertProductTask(AddProductActivity context, Product product) {
            activityReference = new WeakReference<>(context);
            this.product = product;
        }

        // doInBackground methods runs on a worker thread
        @Override
        protected Boolean doInBackground(Void... objs) {

            activityReference.get().mDatabase.getProductDao().insertProduct(product);
            return true;
        }
    }
}
