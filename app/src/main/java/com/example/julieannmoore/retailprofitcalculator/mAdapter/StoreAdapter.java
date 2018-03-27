package com.example.julieannmoore.retailprofitcalculator.mAdapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
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

    List<Store> stores;
    Context context;
    LayoutInflater layoutInflater;

    public StoreAdapter(Context context, List<Store> stores) {
        this.context = context;
        this.stores = stores;
    }

    @Override
    public int getCount() {
        return stores.size();
    }

    @Override
    public Object getItem(int i) {
        if (stores.size() <= 0) {
            Toast.makeText(context, "There are no stores in the list", Toast.LENGTH_SHORT).show();
            return stores;
        }
        return stores.get(i);
    }

    @Override
    public long getItemId(int i) {
        int storeId = 0;
        if (stores.size() <= 0) {
            Toast.makeText(context, "There are no stores in the list", Toast.LENGTH_SHORT).show();
            return i;
        } else {
            Store store = stores.get(i);
            storeId = store.getStoreId();
        }
        return storeId;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        StoreViewHolder storeViewHolder;

        if (view == null) {
            layoutInflater = LayoutInflater.from(this.context);

            view = layoutInflater.inflate(R.layout.layout_list_item, null);
            storeViewHolder = new StoreViewHolder();

            storeViewHolder.textViewStoreName = (TextView) view.findViewById(R.id.textView1);
            storeViewHolder.textViewStoreNumber = (TextView) view.findViewById(R.id.textView2);

            view.setTag(storeViewHolder);
        } else {
            storeViewHolder = (StoreViewHolder) view.getTag();
        }

        final Store store = stores.get(i);

        storeViewHolder.textViewStoreName.setText(store.getStoreName());
        storeViewHolder.textViewStoreNumber.setText(store.getStoreNumber());
        Log.i(TAG, "Index: " + i + " : " + view);

        return view;
    }

    private static class StoreViewHolder {
        public TextView textViewStoreName;
        public TextView textViewStoreNumber;
    }
}