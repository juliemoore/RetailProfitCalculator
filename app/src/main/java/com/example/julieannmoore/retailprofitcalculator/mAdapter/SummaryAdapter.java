package com.example.julieannmoore.retailprofitcalculator.mAdapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.julieannmoore.retailprofitcalculator.R;
import com.example.julieannmoore.retailprofitcalculator.mData.Formula;
import com.example.julieannmoore.retailprofitcalculator.mData.FormulaCollection;
import com.example.julieannmoore.retailprofitcalculator.mData.Product;
import com.example.julieannmoore.retailprofitcalculator.mData.Store;
import com.example.julieannmoore.retailprofitcalculator.mData.Summary;

import java.util.List;
import java.util.Map;

/**
 * Created by Julie Moore on 2/25/2018.
 */

public class SummaryAdapter extends BaseAdapter {

    private LayoutInflater mInflater;
    private String[] mFormulaNames, mFormulaAmounts;

    public SummaryAdapter(Context context, String[] formulaNames, String[] formulaAmounts) {
        mFormulaNames = formulaNames;
        mFormulaAmounts = formulaAmounts;
        mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return mFormulaAmounts.length;
    }

    @Override
    public Object getItem(int position) {
        return mFormulaAmounts[position];
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View view, ViewGroup viewGroup) {

        View v = mInflater.inflate(R.layout.layout_list_item, null);
        TextView formulaNameTextView = v.findViewById(R.id.textView1);
        TextView formulaTextView = v.findViewById(R.id.textView2);

        String formulaName = mFormulaNames[position].toString();
        String formulaAmount = mFormulaAmounts[position].toString();

        formulaNameTextView.setText(formulaName);
        formulaTextView.setText(formulaAmount);

        return v;
    }
}