package com.example.julieannmoore.retailprofitcalculator.mAdapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.julieannmoore.retailprofitcalculator.R;
import com.example.julieannmoore.retailprofitcalculator.mData.Formula;
import com.example.julieannmoore.retailprofitcalculator.mData.Store;

import java.util.List;

public class FormulaAdapter extends BaseAdapter {

    private static final String TAG = FormulaAdapter.class.getSimpleName();
    private Context mContext;
    private TextView mTextView;
    private ImageView mImageView;
    private List<Formula> mFormulaList;

    public FormulaAdapter(Context context, List<Formula> formulaList) {
        mContext = context;
        mFormulaList = formulaList;
    }

    @Override
    public int getCount() {
        return mFormulaList.size();
    }

    @Override
    public Object getItem(int position) {
        return mFormulaList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public Formula getFormulaItem(int position) {
        if (mFormulaList.size() <= 0) {
            Toast.makeText(mContext, "There are no formulas in the list", Toast.LENGTH_SHORT).show();
        }
        if (position > mFormulaList.size() -1) {
            Toast.makeText(mContext, "Selection out of bounds", Toast.LENGTH_SHORT).show();
        }
        return mFormulaList.get(position);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = View.inflate(mContext, R.layout.layout_formula_item, null);
        mTextView = view.findViewById(R.id.formulaTextView);
        mImageView = view.findViewById(R.id.formulaImageView);
        // Set text for TextView
        mTextView.setText(mFormulaList.get(position).getFormulaName());
        mImageView.setImageResource(mFormulaList.get(position).getImage());

        // Save storeId to tag
        view.setTag(mFormulaList.get(position).getFormulaId());
        Log.i(TAG, "Index: " + position + " : " + view);

        return view;
    }

}
