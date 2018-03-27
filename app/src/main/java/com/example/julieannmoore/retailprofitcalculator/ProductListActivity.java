package com.example.julieannmoore.retailprofitcalculator;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.julieannmoore.retailprofitcalculator.mData.Product;
import com.example.julieannmoore.retailprofitcalculator.mDatabase.AppDatabase;

import java.util.List;

public class ProductListActivity extends AppCompatActivity {

    AppDatabase mDatabase;
    List<Product> products;
    Button mButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_list);
        mDatabase = AppDatabase.getInstance(this);
        products = mDatabase.getProductDao().getProducts();


        mButton = findViewById(R.id.bn_add_product);
        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ProductListActivity.this, AddProductActivity.class);
                startActivity(intent);
            }
        });

    }
}
