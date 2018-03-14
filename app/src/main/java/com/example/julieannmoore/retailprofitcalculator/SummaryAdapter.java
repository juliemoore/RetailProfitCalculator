package com.example.julieannmoore.retailprofitcalculator;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by msjul on 2/25/2018.
 */

public class SummaryAdapter extends BaseAdapter {

    LayoutInflater mInflater;
    String[] formulaNames;
    String[] formulaAmounts;

    public SummaryAdapter(Context c, String[] formulaNames, String[] formulaAmounts) {
        this.formulaNames = formulaNames;
        this.formulaAmounts = formulaAmounts;
        mInflater = (LayoutInflater) c.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return formulaNames.length;
    }

    @Override
    public Object getItem(int i) {
        return formulaNames[i];
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {

        View v = mInflater.inflate(R.layout.layout_formula_list, null);
        TextView formulaNameTextView = v.findViewById(R.id.formulaNameTextView);
        TextView formulaTextView = v.findViewById(R.id.formulaTextView);

        String formulaName = formulaNames[i];
        String formulaAmount = formulaAmounts[i];


        formulaNameTextView.setText(formulaName);
        formulaTextView.setText(formulaAmount);

        return v;
    }
}