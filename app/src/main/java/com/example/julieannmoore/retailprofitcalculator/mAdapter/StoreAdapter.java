package com.example.julieannmoore.retailprofitcalculator.mAdapter;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.example.julieannmoore.retailprofitcalculator.R;
import com.example.julieannmoore.retailprofitcalculator.mData.Store;

import java.util.List;

/**
 * Created by Julie Moore on 3/26/2018.
 */

public class StoreAdapter extends BaseAdapter {

    private static final String TAG = StoreAdapter.class.getSimpleName();
    List<Store> mStoreList;
    Context mContext;

    //Constructor
    public StoreAdapter(Context context, List<Store> stores) {
        mContext = context;
        mStoreList = stores;
    }

    public void AddItem(Store store) {
        mStoreList.add(store);
        notifyDataSetChanged();
    }

    public void EditItem(int index, Store store) {
        mStoreList.set(index, store);
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return mStoreList.size();
    }

    @Override
    public Object getItem(int position) { return mStoreList.get(position); }

    public Store getStoreItem(int position) {
        if (mStoreList.size() <= 0) {
            Toast.makeText(mContext, "There are no stores in the list", Toast.LENGTH_SHORT).show();
        }
        if (position > mStoreList.size() -1) {
            Toast.makeText(mContext, "Selection out of bounds", Toast.LENGTH_SHORT).show();
        }
        return mStoreList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = View.inflate(mContext, R.layout.layout_list_item, null);
        TextView textViewStoreName = view.findViewById(R.id.textView1);
        TextView textViewStoreNumber = view.findViewById(R.id.textView2);
        // Set text for TextView
        textViewStoreName.setText(mStoreList.get(position).getStoreName());
        textViewStoreNumber.setText(mStoreList.get(position).getStoreNumber());

        // Save storeId to tag
        view.setTag(mStoreList.get(position).getStoreId());
        Log.i(TAG, "Index: " + position + " : " + view);

        return view;
    }
}