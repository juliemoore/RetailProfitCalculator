package com.example.julieannmoore.retailprofitcalculator.mAdapter;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;
import android.widget.Toast;

import com.example.julieannmoore.retailprofitcalculator.R;
import com.example.julieannmoore.retailprofitcalculator.mData.Product;
import com.example.julieannmoore.retailprofitcalculator.mData.Store;

import java.util.ArrayList;
import java.util.List;

public class ProductAdapter extends BaseAdapter implements Filterable {

    private static final String TAG = ProductAdapter.class.getSimpleName();
    List<Product> mProductList;
    List<Product> mStringFilterList;
    ValueFilter valueFilter;
    Context mContext;

    //Constructor
    public ProductAdapter(Context context, List<Product> products) {
        mContext = context;
        mProductList = products;
    }

    public void AddItem(Product product) {
        mProductList.add(product);
        notifyDataSetChanged();
    }

    public void EditItem(int index, Product product) {
        mProductList.set(index, product);
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return mProductList.size();
    }

    @Override
    public Object getItem(int position) { return null; }

    public Product getProductItem(int position) {
        if (mProductList.size() <= 0) {
            Toast.makeText(mContext, "There are no products in the list", Toast.LENGTH_SHORT).show();
        }
        if (position > mProductList.size() -1) {
            Toast.makeText(mContext, "Selection out of bounds", Toast.LENGTH_SHORT).show();
        }
        return mProductList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public Filter getFilter() {
        if (valueFilter == null) {
            valueFilter = new ValueFilter();
        }
        return valueFilter;
    }

    private class ValueFilter extends Filter {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            FilterResults results = new FilterResults();

            mStringFilterList = mProductList;
            if (constraint != null && constraint.length() > 0) {
                List<Product> filterList = new ArrayList<>();
                for (int i = 0; i < mStringFilterList.size(); i++) {
                    if ((mStringFilterList.get(i).getProductName().toUpperCase()).contains(constraint.toString().toUpperCase())) {
                        filterList.add(mStringFilterList.get(i));
                    }
                }
                results.count = filterList.size();
                results.values = filterList;
            } else {
                results.count = mStringFilterList.size();
                results.values = mStringFilterList;
            }
            return results;

        }

        @Override
        protected void publishResults(CharSequence constraint,
                                      FilterResults results) {
            mProductList = (List<Product>) results.values;
            notifyDataSetChanged();
        }

    }

    public void notifyDataSetChanged() {
        super.notifyDataSetChanged();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = View.inflate(mContext, R.layout.layout_product_item, null);
        TextView textViewProduct = view.findViewById(R.id.textView1);
        TextView textViewProductId = view.findViewById(R.id.textView2);
        // Set text for TextView
        textViewProduct.setText(mProductList.get(position).getProductName());
        textViewProductId.setText("");

        return view;
    }
}