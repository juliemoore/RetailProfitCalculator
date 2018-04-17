package com.example.julieannmoore.retailprofitcalculator;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.julieannmoore.retailprofitcalculator.mAdapter.FormulaAdapter;
import com.example.julieannmoore.retailprofitcalculator.mAdapter.StoreAdapter;
import com.example.julieannmoore.retailprofitcalculator.mData.Formula;
import com.example.julieannmoore.retailprofitcalculator.mData.FormulaCollection;
import com.example.julieannmoore.retailprofitcalculator.mData.Store;
import com.example.julieannmoore.retailprofitcalculator.mDialogs.CustomFormulaDialog;
import com.example.julieannmoore.retailprofitcalculator.mDialogs.CustomStoreDialog;
import com.example.julieannmoore.retailprofitcalculator.mUtilities.FormulaListEventCallbacks;
import com.example.julieannmoore.retailprofitcalculator.mUtilities.StoreListEventCallbacks;

import java.util.List;

public class FormulaListActivity extends AppCompatActivity implements FormulaListEventCallbacks {

    private FormulaListEventCallbacks mListCallbacks;
    private CustomFormulaDialog mCustomDialog;
    private FormulaCollection mFormulaCollection;
    private List<Formula> mFormulaList;
    private Formula listItem;
    private int mItemId;
    private FormulaAdapter mAdapter;
    private ListView mListView;
    private FloatingActionButton mFab;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_formula_list);

        // Set action bar with logo
        ActionBar actionBar = getSupportActionBar();
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        actionBar.setLogo(R.mipmap.ic_launcher_round);
        actionBar.setDisplayUseLogoEnabled(true);

        mFormulaCollection = new FormulaCollection(this);
        mListView = findViewById(R.id.list_view);
        mFormulaList = mFormulaCollection.getFormulas();
        mAdapter = new FormulaAdapter(this, mFormulaList);
        mListView.setAdapter(mAdapter);
        mFab = findViewById(R.id.calculator_fab);

        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if(mListCallbacks == null) { return; }
                listItem = mAdapter.getFormulaItem(position);
                mItemId = position;
                mListCallbacks.Select(listItem, mAdapter, position);
            }
        });


        if(FormulaListEventCallbacks.class.isInstance(this)){
            mListCallbacks = this;
        }

        mFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(FormulaListActivity.this, CalculatorActivity.class);
                startActivity(intent);
            }
        });
    }

    @Override
    public void Select(Formula formula, FormulaAdapter adapter, int itemId) {
        // Create custom dialog
        mCustomDialog = new CustomFormulaDialog(FormulaListActivity.this);
        mCustomDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        mCustomDialog.InitializeFormulaData(formula, adapter, itemId);
        mCustomDialog.setCancelable(true);
        mCustomDialog.show();
    }
}
