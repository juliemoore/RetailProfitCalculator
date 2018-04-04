package com.example.julieannmoore.retailprofitcalculator;

import android.content.Context;
import android.content.Intent;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.julieannmoore.retailprofitcalculator.mAdapter.ProductAdapter;
import com.example.julieannmoore.retailprofitcalculator.mData.Product;
import com.example.julieannmoore.retailprofitcalculator.mData.Store;
import com.example.julieannmoore.retailprofitcalculator.mDatabase.AppDatabase;

import java.util.List;

public class UpdateProductActivity extends AppCompatActivity {

    private static final String TAG = AddProductActivity.class.getSimpleName();

    private TextView mStoreName, mStoreNumber;
    private TextInputEditText mProductName, mCostOfGoods, mSellingPrice, mAnnualUnitsSold,
            mAveWeeklyInventory, mLinearFt;
    private TextInputLayout mProductNameWrapper, mCostOfGoodsWrapper, mSellingPriceWrapper,
            mAnnualUnitsSoldWrapper, mAveWeeklyInventoryWrapper, mLinearFtWrapper;
    private Button mButton;
    private AppDatabase mDatabase;
    private ProductAdapter mAdapter;
    private List<Product> mProducts;
    private Product mProduct;
    private Store mStore;
    private int storeId, productId;
    private String storeName, storeNumber, productName;
    private double costOfGoods, sellingPrice, annualUnitsSold,
            aveWeeklyInventory, linearFt = 0;
    private Boolean isValid = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_product);

        mDatabase = AppDatabase.getInstance(this);
        mProduct = new Product();
        initializeView();
        getProductData();

        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateProduct();
                Intent intent = new Intent(UpdateProductActivity.this, SummaryActivity.class);
                intent.putExtra("UpdatedProduct", mProduct);
                startActivity(intent);
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

    private void getProductData() {
        // Get Store Data and display in textviews
        if ((mProduct = (Product) getIntent().getSerializableExtra("UpdateProduct")) != null) {
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

    public void updateProduct() {

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
        }else {
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

        mProduct = new Product(productId, productName, storeId, costOfGoods, sellingPrice,
                annualUnitsSold, aveWeeklyInventory, linearFt);
        Log.i(TAG, mProduct.toString());

        if (isValid == true) {
            hideKeyboard();
            mDatabase.getProductDao().updateProduct(mProduct);
            toastMessage("Product updated successfully.");
            clear();

        } else {
            toastMessage("Invalid data. Try again.");
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
}
