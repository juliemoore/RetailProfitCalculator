package com.example.julieannmoore.retailprofitcalculator;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

/**
 * Created by Julie Moore on 2/23/2018.
 */

public class ListAdapterStores extends BaseAdapter {

    private static final String TAG = ListAdapterStores.class.getSimpleName();

    List<Store> stores;
    Context context;
    LayoutInflater layoutInflater;

    public ListAdapterStores(Context context,List<Store> stores){
        this.context = context;
        this.stores = stores;
    }

    @Override
    public int getCount() {
        return stores.size();
    }

    @Override
    public Object getItem(int i) {
        return stores.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        StoreViewHolder storeViewHolder;

        if(view==null){
            layoutInflater = LayoutInflater.from(this.context);

            view = layoutInflater.inflate(R.layout.layout_store_list,null);
            storeViewHolder = new StoreViewHolder();

            storeViewHolder.textViewStoreName = view.findViewById(R.id.textViewStoreName);
            storeViewHolder.textViewStoreNumber = view.findViewById(R.id.textViewStoreNumber);

            view.setTag(storeViewHolder);
        }else{
            storeViewHolder = (StoreViewHolder)view.getTag();
        }

        final Store store = stores.get(i);

        storeViewHolder.textViewStoreName.setText(store.getStoreName());
        storeViewHolder.textViewStoreNumber.setText(store.getStoreNumber());
        Log.i(TAG,"Index: "+i+" : "+view);

        return view;
    }

    private static class StoreViewHolder{
        public TextView textViewStoreName;
        public TextView textViewStoreNumber;
    }
}

