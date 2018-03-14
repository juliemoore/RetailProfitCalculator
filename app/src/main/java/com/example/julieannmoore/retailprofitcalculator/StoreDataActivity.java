package com.example.julieannmoore.retailprofitcalculator;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class StoreDataActivity extends Activity {

    public static final String EXTRA_STORE_DATA= "com.example.julieannmoore.STORE_DATA";
    String storeName;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_store_data);

        final Button button = findViewById(R.id.calculateButton);
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(StoreDataActivity.this, SummaryActivity.class);

                intent.putExtra(EXTRA_STORE_DATA, storeName);
                startActivity(intent);
            }
        });
    }

}
