package com.example.julieannmoore.retailprofitcalculator;

import android.content.Context;
import android.content.Intent;
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

import com.example.julieannmoore.retailprofitcalculator.mData.Product;
import com.example.julieannmoore.retailprofitcalculator.mData.Store;
import com.example.julieannmoore.retailprofitcalculator.mDatabase.AppDatabase;

public class AddProductActivity extends AppCompatActivity {

    private static final String TAG = AddProductActivity.class.getSimpleName();

    private TextView mStoreName, mStoreNumber;
    private EditText mProductName, mCostOfGoods, mSellingPrice, mAnnualUnitsSold,
            mAveWeeklyInventory, mLinearFt;
    private TextInputLayout mProductNameWrapper, mCostOfGoodsWrapper, mSellingPriceWrapper,
            mAnnualUnitsSoldWrapper, mAveWeeklyInventoryWrapper, mLinearFtWrapper;
    private Button mButton;
    private AppDatabase mDatabase;
    private Product mProduct;
    private Store mStore;
    private int mStoreId;
    private String storeName, storeNumber, productName;
    private double costOfGoods, sellingPrice, annualUnitsSold,
            aveWeeklyInventory, linearFt = 0;
    private String info = "";
    private Boolean isUpdate, isValid = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_product);

        mDatabase = AppDatabase.getInstance(this);
        initializeView();
        getProductData();

        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addProduct();
                Intent intent = new Intent(AddProductActivity.this, SummaryActivity.class);
                intent.putExtra("Product", mProduct);
                startActivity(intent);
            }
        });
    }

    public void initializeView() {
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
        if( (mProduct = (Product) getIntent().getSerializableExtra("Product")) != null) {
            setTitle(getString(R.string.update_product));
            mStoreId = mProduct.getStoreId();
            mStore = mDatabase.getStoreDao().findByStoreId(mStoreId);
            storeName = mStore.getStoreName();
            storeNumber = mStore.getStoreNumber();
            mStoreName.setText(storeName);
            mStoreNumber.setText(storeNumber);

            if (mProduct.getProductName() != null) {
                mProductName.setText(mProduct.getProductName());
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
            isUpdate = true;
            Toast.makeText(this, mStore.toString(), Toast.LENGTH_LONG).show();
        }

        // Get Store Data and display in textviews
        if( (mStore = (Store) getIntent().getSerializableExtra("Store")) != null) {
            mStoreId = mStore.getStoreId();
            storeName = mStore.getStoreName();
            storeNumber = mStore.getStoreNumber();
            mStoreName.setText(storeName);
            mStoreNumber.setText(storeNumber);
            Toast.makeText(this, mStore.toString(), Toast.LENGTH_LONG).show();
        }
    }

    public void addProduct() {
        mProduct = new Product();
        // Set store id to id passed from product list
        mProduct.setStoreId(mStoreId);
        if (mProductName.length() != 0) {
            productName = mProductNameWrapper.getEditText().toString();
            mProduct.setProductName(productName);
            mProductNameWrapper.setErrorEnabled(false);
        } else {
            mProductNameWrapper.setError("Product name is required.");
            mProductName.requestFocus();
            isValid = false;
        }
        if (mCostOfGoods.length() != 0) {
            try {
                costOfGoods = Double.parseDouble(mCostOfGoodsWrapper.getEditText().toString());
            } catch (NumberFormatException ex) {
                mCostOfGoodsWrapper.setError("Cost of goods is not a number");
                mCostOfGoods.setText("");
                mCostOfGoods.requestFocus();
                isValid = false;
            }
            mProduct.setCostOfGoods(costOfGoods);
            mCostOfGoodsWrapper.setErrorEnabled(false);
        } else {
            mCostOfGoodsWrapper.setError("Cost of goods is required.");
            mCostOfGoods.requestFocus();
            isValid = false;
        }
        if (mSellingPrice.length() != 0) {
            try {
                sellingPrice = Double.parseDouble(mSellingPriceWrapper.getEditText().toString());
            } catch (NumberFormatException ex) {

                mSellingPriceWrapper.setError("Selling price is not a number");
                mSellingPrice.setText("");
                mSellingPrice.requestFocus();
                isValid = false;
            }
            isValid = true;
            mProduct.setSellingPrice(sellingPrice);
            mSellingPriceWrapper.setErrorEnabled(false);
        } else {
            mSellingPriceWrapper.setError("Selling price is required.");
            mSellingPrice.requestFocus();
            isValid = false;
        }
        if (mAnnualUnitsSold.length() != 0) {
            try {
                annualUnitsSold = Double.parseDouble(mAnnualUnitsSoldWrapper.getEditText().toString());
            } catch (NumberFormatException ex) {
                mAnnualUnitsSoldWrapper.setError("Annual units sold is not a number");
                mAnnualUnitsSold.setText("");
                mAveWeeklyInventory.requestFocus();
                isValid = false;
            }
            isValid = true;
            mProduct.setAnnualUnitsSold(annualUnitsSold);
            mAnnualUnitsSoldWrapper.setErrorEnabled(false);
        } else {
            annualUnitsSold = 0;
        }
        if (mAveWeeklyInventory.length() != 0) {
            try {
                aveWeeklyInventory = Double.parseDouble(mAveWeeklyInventoryWrapper.getEditText().toString());
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
            isValid = true;
            mProduct.setAnnualUnitsSold(aveWeeklyInventory);
            mAveWeeklyInventoryWrapper.setErrorEnabled(false);
        } else {
            aveWeeklyInventory = .001; // if set to 0, will return divide by zero exception
        }
        if (mLinearFt.length() != 0) {
            try {
                linearFt = Double.parseDouble(mLinearFtWrapper.getEditText().toString());
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
            isValid = true;
            mProduct.setLinearFeet(linearFt);
            mLinearFtWrapper.setErrorEnabled(false);
        } else {
            linearFt = .001; // if set to 0, will return divide by zero exception
            mProduct.setLinearFeet(linearFt);
        }

        info +=  "\nStore name:" + mStoreName +
                "\nProduct name: " + productName +
                "\nprice: " + sellingPrice;
        Log.i(TAG, info);

        if (isValid == true) {
            hideKeyboard();
            if (isUpdate == true) {
                mDatabase.getProductDao().updateProduct(mProduct);
                toastMessage("Product updated successfully.");
                clear();
            } else if (isUpdate == false) {
                // Fetch data and create product object
                mDatabase.getProductDao().insertProduct(mProduct);
                toastMessage("Product added successfully.");
                clear();
            }
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
