package com.example.julieannmoore.retailprofitcalculator;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class SummaryActivity extends Activity {

    Button editButton, saveButton, deleteButton;
    Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_summary);
        context = getApplicationContext();

        editButton = findViewById(R.id.editButton);
        editButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, StoreDataActivity.class);
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
