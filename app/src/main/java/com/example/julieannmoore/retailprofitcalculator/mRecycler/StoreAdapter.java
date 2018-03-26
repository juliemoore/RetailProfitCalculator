package com.example.julieannmoore.retailprofitcalculator.mRecycler;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.julieannmoore.retailprofitcalculator.R;
import com.example.julieannmoore.retailprofitcalculator.mData.Store;

import java.util.List;

/**
 * Created by Julie Moore on 3/25/2018.
 */

public class StoreAdapter extends RecyclerView.Adapter<StoreAdapter.BeanHolder> {

    private List<Store> list;
    private Context context;
    private LayoutInflater layoutInflater;
    private OnStoreItemClick onStoreItemClick;

    public StoreAdapter(List<Store> list,Context context) {
        layoutInflater = LayoutInflater.from(context);
        this.list = list;
        this.context = context;
        this.onStoreItemClick = (OnStoreItemClick) context;
    }


    @Override
    public BeanHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = layoutInflater.inflate(R.layout.layout_list_item, parent,false);
        return new BeanHolder(view);
    }

    @Override
    public void onBindViewHolder(BeanHolder holder, int position) {
        Log.e("bind", "onBindViewHolder: "+ list.get(position));
        holder.textViewStoreName.setText(list.get(position).getStoreName());
        holder.textViewStoreNumber.setText(list.get(position).getStoreNumber());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class BeanHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView textViewStoreName, textViewStoreNumber;
        public BeanHolder(View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
            textViewStoreName = itemView.findViewById(R.id.textView1);
            textViewStoreNumber = itemView.findViewById(R.id.textView2);
        }

        @Override
        public void onClick(View view) {
            onStoreItemClick.onStoreClick(getAdapterPosition());
        }
    }

    public interface OnStoreItemClick{
        void onStoreClick(int pos);
    }
}
