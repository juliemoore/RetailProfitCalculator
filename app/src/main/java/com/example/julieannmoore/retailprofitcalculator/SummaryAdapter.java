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
    LayoutInflater mInflator;
    List<Formula> formulas;
    List<String> formulaName;
    List<String> formulaAmount;
    Context context;

    public SummaryAdapter(Context c, List<String> formulaName, List<String> formulaAmount) {
        mInflator = (LayoutInflater) c.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        formulaName = new ArrayList();
        formulaAmount = new ArrayList();
        this.context = c;
    }

    @Override
    public int getCount() {
        return formulas.size();
    }

    @Override
    public Object getItem(int position) {
        return formulaName.get(position);
    }

    public Object getFormula(int position) {return formulaAmount.get(position);}

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if(convertView==null){
            mInflator = LayoutInflater.from(this.context);
            convertView = mInflator.inflate(R.layout.layout_formula_list,null);
        }

        View view = mInflator.inflate(R.layout.layout_summary, null);
        TextView formulaNameTextView = view.findViewById(R.id.formulaNameTextView);
        TextView formulaTextView= view.findViewById(R.id.formulaTextView);


        formulaNameTextView.setText(formulaName.get(position));
        formulaTextView.setText(formulaAmount.get(position).toString());

        return view;
    }

} // End SummaryAdapter class