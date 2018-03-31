package com.example.julieannmoore.retailprofitcalculator;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.julieannmoore.retailprofitcalculator.mData.Product;
import com.example.julieannmoore.retailprofitcalculator.mDatabase.AppDatabase;

public class AddProductActivity extends AppCompatActivity {

    private static final String TAG = AddProductActivity.class.getSimpleName();

    private TextView mStoreName, mStoreNumber;
    private EditText mProduct, mCostOfGoods, mSellingPrice, mAnnualUnitsSold,
            mAveWeeklyInventory, mLinearFt;
    private Button mButton;
    private AppDatabase mDatabase;
    private Product product;
    private int storeId, productId;
    private String storeName, storeNumber, productName;
    private double costOfGoods, sellingPrice, annualUnitsSold,
            aveWeeklyInventory, linearFt = 0;
    String info = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_product);

        mDatabase = AppDatabase.getInstance(this);
        mButton = findViewById(R.id.calculateButton);
        initializeView();

        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addProduct();
                Intent intent = new Intent(AddProductActivity.this, SummaryActivity.class);
                startActivity(intent);
            }
        });
    }

    public void initializeView() {
        mStoreName = findViewById(R.id.textView1);
        mStoreNumber = findViewById(R.id.textView1);
        mProduct = findViewById(R.id.productEditText);
        mCostOfGoods = findViewById(R.id.costOfGoodsEditText);
        mSellingPrice = findViewById(R.id.sellingPriceEditText);
        mAnnualUnitsSold = findViewById(R.id.annualUnitsSoldEditText);
        mAveWeeklyInventory = findViewById(R.id.aveWeeklyInventoryEditText);
        mLinearFt = findViewById(R.id.linearFtEditText);
    }

    public void addProduct() {
        Product product= new Product();
        // Set store id to unique number
        product.setProductId(productId);
        product.setStoreId(1);
        if (mProduct.length() != 0) {
            productName = mProduct.getText().toString();
            product.setProductName(productName);
        } else {
            toastMessage("Product name is required.");
            mProduct.requestFocus();
        }
        if (mCostOfGoods.length() != 0) {
            try {
                costOfGoods = Double.parseDouble(mCostOfGoods.getText().toString());
            } catch (NumberFormatException ex) {
                toastMessage("Cost of goods is not a number");
                mCostOfGoods.requestFocus();
            }
            product.setCostOfGoods(costOfGoods);
        } else {
            toastMessage("Cost of goods is required.");
            mCostOfGoods.requestFocus();
        }
        if (mSellingPrice.length() != 0) {
            try {
                sellingPrice = Double.parseDouble(mSellingPrice.getText().toString());
            } catch (NumberFormatException ex) {
                toastMessage("Selling price is not a number");
            }
            product.setSellingPrice(sellingPrice);
        } else {
            toastMessage("Selling price is required.");
            mSellingPrice.requestFocus();
        }
        if (mAnnualUnitsSold.length() != 0) {
            try {
                annualUnitsSold = Double.parseDouble(mAnnualUnitsSold.getText().toString());
            } catch (NumberFormatException ex) {
                toastMessage("Annual units sold is not a number");
                mAveWeeklyInventory.requestFocus();
            }
            product.setAnnualUnitsSold(annualUnitsSold);
        } else {
            annualUnitsSold = 0;
        }
        if (mAveWeeklyInventory.length() != 0) {
            try {
                aveWeeklyInventory = Double.parseDouble(mAveWeeklyInventory.getText().toString());
            } catch (NumberFormatException ex) {
                toastMessage("Average weekly inventory is not a number");
                mAveWeeklyInventory.requestFocus();
            }
            product.setAnnualUnitsSold(aveWeeklyInventory);
        } else {
            aveWeeklyInventory = 0;
        }
        if (mLinearFt.length() != 0) {
            try {
                linearFt = Double.parseDouble(mLinearFt.getText().toString());
            } catch (NumberFormatException ex) {
                toastMessage("Linear feet is not a number");
                mLinearFt.requestFocus();
            }
            product.setLinearFeet(linearFt);
        } else {
            linearFt = .001; // if set to 0, will return divide by zero exception
            product.setLinearFeet(linearFt);
        }

        info +=  "ProductId: " + productId +
                "\nProduct name: " + productName +
                "\ncost: " + costOfGoods +
                "\nprice: " + sellingPrice +
                "\n\n";
        Log.i(TAG, info);

        // Fetch data and create product object
        mDatabase.getProductDao().insertProduct(product);
        toastMessage("Product added successfully.");
        // clear text boxes
        mStoreName.setText("");
        mStoreNumber.setText("");
        mCostOfGoods.setText("");
        mSellingPrice.setText("");
        mAveWeeklyInventory.setText("");
        mLinearFt.setText("");
    }

    private void toastMessage(String message){
        Toast.makeText(this,message, Toast.LENGTH_SHORT).show();
    }
}
