package com.example.julieannmoore.retailprofitcalculator.mActivities;

import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.julieannmoore.retailprofitcalculator.R;

public class MainActivity extends AppCompatActivity {

    Button mAddStore, mViewStore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Set action bar with logo
        ActionBar actionBar = getSupportActionBar();
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        actionBar.setLogo(R.mipmap.ic_launcher_round);
        actionBar.setDisplayUseLogoEnabled(true);

        mAddStore = findViewById(R.id.bn_add_store);
        mViewStore = findViewById(R.id.bn_view_store);

        mAddStore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent addStoreIntent = new Intent(MainActivity.this, AddStoreActivity.class);
                startActivity(addStoreIntent);
            }
        });

        mViewStore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent viewStoreIntent = new Intent(MainActivity.this, StoreListActivity.class);
                startActivity(viewStoreIntent);
            }
        });
    }

    public void viewFormulas(View view) {
        Intent viewFormulasIntent = new Intent(this, FormulaListActivity.class);
        startActivity(viewFormulasIntent);
    }
}
