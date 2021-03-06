package com.example.julieannmoore.retailprofitcalculator.mDialogs;

import android.app.Activity;
import android.app.Dialog;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.julieannmoore.retailprofitcalculator.R;
import com.example.julieannmoore.retailprofitcalculator.mAdapter.FormulaAdapter;
import com.example.julieannmoore.retailprofitcalculator.mData.Formula;
import com.example.julieannmoore.retailprofitcalculator.mUtilities.FormulaListEventCallbacks;

public class CustomFormulaDialog extends Dialog implements View.OnClickListener {

    public Activity mActivity;
    private Formula mItem;
    private FormulaAdapter mAdapter;
    private int mItemId;
    private TextView mFormulaName;
    private ImageView mFormulaImage;
    private FloatingActionButton mFab;
    private FormulaListEventCallbacks mListCallbacks;

    public void InitializeFormulaData(Formula item, FormulaAdapter adapter, int itemId) {
        mItem = item;
        mAdapter = adapter;
        mItemId = itemId;
    }

    // constructor
    public CustomFormulaDialog(Activity c) {
        super(c);
        this.mActivity = c;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.custom_formula_dialog);


        if(FormulaListEventCallbacks.class.isInstance(this)){
            mListCallbacks = (FormulaListEventCallbacks) this;
        }

        mFormulaImage = findViewById(R.id.formula_image);
        mFormulaImage.setImageResource(mItem.getImage());
        mFab = findViewById(R.id.close_fab);

        mFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });

    }

    @Override
    public void onClick(View v) {
        return;
    }
}
