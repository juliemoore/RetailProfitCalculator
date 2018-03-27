package com.example.julieannmoore.retailprofitcalculator;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.julieannmoore.retailprofitcalculator.mAdapter.SummaryAdapter;
import com.example.julieannmoore.retailprofitcalculator.mData.StoreProductProfits;
import com.example.julieannmoore.retailprofitcalculator.mDatabase.AppDatabase;

public class SummaryActivity extends Activity {

    private TextView storeNameTextView, storeNumberTextView, productTextView;
    private Button mButton;
    private Context context;
    private ListView mListView;
    private SummaryAdapter mAdapter;
    private AppDatabase mDatabase;
    private StoreProductProfits profits;
    private String[] formulaNames;
    private String[] formulaAmounts;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_summary);

        context = getApplicationContext();
        Resources res = getResources();

        profits = new StoreProductProfits();
        storeNameTextView = findViewById(R.id.textView1);
        storeNumberTextView = findViewById(R.id.textView2);
        productTextView = findViewById(R.id.productSummaryTextView);
        mListView = findViewById(R.id.list_view);
        mButton = findViewById(R.id.saveButton);
        mDatabase = AppDatabase.getInstance(this);

        formulaNames = res.getStringArray(R.array.formulaNames);
        formulaAmounts = res.getStringArray(R.array.formulaAmounts);
        mAdapter = new SummaryAdapter(this, formulaNames, formulaAmounts);
        mListView.setAdapter(mAdapter);

        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent showFormulaActivity =
                        new Intent(SummaryActivity.this, FormulaActivity.class);
                showFormulaActivity.putExtra("com.example.julieannmoore.ITEM_INDEX", i);
                startActivity(showFormulaActivity);
            }
        });
        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context, "Store data saved", Toast.LENGTH_LONG).show();
            }
        });

    }
}
