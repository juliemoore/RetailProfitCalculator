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
    Map<String, String> map;
    List<String> formulaName;
    List<String> formulaAmount;
    Context context;

    public SummaryAdapter(Context c, Map m) {
        mInflator = (LayoutInflater) c.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        map = m;
        formulaName = new ArrayList<String>(map.keySet());
        formulaAmount = new ArrayList<String>(map.values());
        this.context = c;
    }

    @Override
    public int getCount() {
        return map.size();
    }

    @Override
    public Object getItem(int position) {
        return formulaName.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if(convertView==null){
            mInflator = LayoutInflater.from(this.context);
            convertView=mInflator.inflate(R.layout.layout_formula_list,null);
        }

        View v = mInflator.inflate(R.layout.layout_summary, null);
        TextView formulaNameTextView = (TextView) v.findViewById(R.id.textViewFormulaName);
        TextView formulaAmountTextView = (TextView) v.findViewById(R.id.textViewFormula);

        formulaNameTextView.setText(formulaName.get(position));
        formulaAmountTextView.setText(formulaAmount.get(position).toString());

        return v;
    }

} // End ItemAdapter class