package com.example.julieannmoore.retailprofitcalculator;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.julieannmoore.retailprofitcalculator.mAdapter.ProductAdapter;
import com.example.julieannmoore.retailprofitcalculator.mData.Product;
import com.example.julieannmoore.retailprofitcalculator.mData.Store;
import com.example.julieannmoore.retailprofitcalculator.mDatabase.AppDatabase;

import java.lang.ref.WeakReference;
import java.util.List;

public class AddProductActivity extends AppCompatActivity {

    private static final String TAG = AddProductActivity.class.getSimpleName();
    private TextView mStoreName, mStoreNumber;
    private TextInputEditText mProductName, mCostOfGoods, mSellingPrice, mAnnualUnitsSold,
            mAveWeeklyInventory, mLinearFt;
    private TextInputLayout mProductNameWrapper, mCostOfGoodsWrapper, mSellingPriceWrapper,
            mAnnualUnitsSoldWrapper, mAveWeeklyInventoryWrapper, mLinearFtWrapper;
    private Button mButton;
    private AppDatabase mDatabase;
    private Product mProduct;
    private Store mStore;
    private int storeId, productId, itemId;
    private String storeName, storeNumber, productName;
    private double costOfGoods, sellingPrice, annualUnitsSold,
            aveWeeklyInventory, linearFt = 0;
    private Boolean isValid, isUpdate = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_product);

        mDatabase = AppDatabase.getInstance(this);
        mProduct = new Product();
        initializeView();
        getStoreData();
        getProductData();


        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getTextInput();
                if (isUpdate == true) {
                    mProduct = updateProduct();
                    Intent intent = new Intent(AddProductActivity.this, SummaryActivity.class);
                    toastMessage(getString(R.string.update_product_successful));
                    intent.putExtra("UpdatedProduct", mProduct);
                    startActivity(intent);
                } else {
                    // Create new product object
                    mProduct = new Product(0, productName, storeId, costOfGoods, sellingPrice,
                            annualUnitsSold, aveWeeklyInventory, linearFt);
                    new InsertProductTask(AddProductActivity.this, mProduct).execute();
                    clear();
                    Intent intent = new Intent(AddProductActivity.this, SummaryActivity.class);
                    toastMessage(getString(R.string.save_product_successful));
                    intent.putExtra("Calculate", mProduct);
                    startActivity(intent);
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

    private void getProductData() {
        // Get Product Data and display in textviews
        if ((mProduct = (Product) getIntent().getSerializableExtra("UpdateProduct")) != null) {
            isUpdate = true;
            setTitle(getString(R.string.update_product));
            storeId = mProduct.getStoreId();
            productId = mProduct.getProductId();
            mStore = mDatabase.getStoreDao().findByStoreId(storeId);
            storeName = mStore.getStoreName();
            storeNumber = mStore.getStoreNumber();
            mStoreName.setText(storeName);
            mStoreNumber.setText(storeNumber);
            mProduct.toString();

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
    }

    public void getTextInput() {
        if (mProductNameWrapper.getEditText().getText().toString().isEmpty()) {
            mProductNameWrapper.setError("Product name is required.");
            mProductName.requestFocus();
            isValid = false;
        } else {
            productName = mProductName.getText().toString();
            mProductNameWrapper.setErrorEnabled(false);
        }
        if (mCostOfGoodsWrapper.getEditText().toString().isEmpty()) {
            mCostOfGoodsWrapper.setError("Cost of goods is required.");
        } else {
            mCostOfGoodsWrapper.setErrorEnabled(false);
        }
        try {
            costOfGoods = Double.parseDouble(mCostOfGoods.getText().toString());
        } catch (NumberFormatException ex) {
            toastMessage("Cost of goods is not a number");
            mCostOfGoods.setText("");
            mCostOfGoods.requestFocus();
            isValid = false;
        }
        if (mSellingPriceWrapper.getEditText().getText().toString().isEmpty()) {
            mSellingPriceWrapper.setError("Selling price is required.");
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
            mAnnualUnitsSoldWrapper.setError("Average weekly inventory is not a number");
            mAnnualUnitsSold.setText("");
            mAveWeeklyInventory.requestFocus();
            isValid = false;
        }
        try {
            aveWeeklyInventory = Double.parseDouble(mAveWeeklyInventory.getText().toString());
            isValid = true;
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
            isValid = true;
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

        if (isValid == true) {
            hideKeyboard();
        } else {
            toastMessage("Invalid data. Try again.");
        }
    }

    private Product insertProduct() {
        // Create new product object
        Product newProduct = new Product(0, productName, storeId, costOfGoods, sellingPrice,
                annualUnitsSold, aveWeeklyInventory, linearFt);

        // Insert into database
        mDatabase.getProductDao().insertProduct(newProduct);
        clear();
        return newProduct;
    }

    private Product updateProduct() {
        // Create new product object
        Product updateProduct = new Product(productId, productName, storeId, costOfGoods, sellingPrice,
                annualUnitsSold, aveWeeklyInventory, linearFt);
        // Update product in database
        mDatabase.getProductDao().updateProduct(updateProduct);
        clear();
        return updateProduct;
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

    private void setResult(Product product, int flag) {
        Intent intent = new Intent(AddProductActivity.this, SummaryActivity.class);
        setResult(flag, intent.putExtra("Calculate", product));
        startActivity(intent);
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

        // onPostExecute runs on main thread
        @Override
        protected void onPostExecute(Boolean bool) {
            if (bool) {
                activityReference.get().setResult(product, 1);
            }
        }
    }
}
