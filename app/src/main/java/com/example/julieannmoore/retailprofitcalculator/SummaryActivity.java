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
import android.widget.Toast;

public class SummaryActivity extends Activity {

    Button editButton, saveButton, deleteButton;
    Context context;
    ListView summaryListView;
    String[] formulaNames;
    String[] formulaAmounts;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_summary);
        context = getApplicationContext();

        Resources res = getResources();
        summaryListView = findViewById(R.id.summaryListView);
        formulaNames = res.getStringArray(R.array.formulaNames);
        formulaAmounts = res.getStringArray(R.array.formulaAmounts);
        SummaryAdapter itemAdapter = new SummaryAdapter(this, formulaNames, formulaAmounts);
        summaryListView.setAdapter(itemAdapter);

        summaryListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent showFormulaActivity =
                        new Intent(getApplicationContext(), FormulaActivity.class);
                showFormulaActivity.putExtra("com.example.julieannmoore.ITEM_INDEX", i);
                startActivity(showFormulaActivity);
            }
        });

        editButton = findViewById(R.id.editButton);
        editButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, AddProductActivity.class);
                startActivity(intent);
            }
        });

        saveButton = findViewById(R.id.saveButton);
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context, "Store data saved", Toast.LENGTH_LONG).show();
            }
        });

        deleteButton = findViewById(R.id.deleteButton);
        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context, "Store data deleted", Toast.LENGTH_LONG).show();
            }
        });

    }
}
