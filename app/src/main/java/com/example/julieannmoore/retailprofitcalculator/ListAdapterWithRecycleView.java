package com.example.julieannmoore.retailprofitcalculator;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

/**
 * Created by Julie Moore 02/23/2018
 */

public class ListAdapterWithRecycleView extends RecyclerView.Adapter<ListAdapterWithRecycleView.StoreViewHolder> {

    private static final String TAG = ListAdapterWithRecycleView.class.getSimpleName();

    public interface StoreModifier{
        public void onStoreSelected(int position);
        public void onStoreDeleted(int position);
    }

    private List<Store> storeList;
    private Context context;

    private StoreModifier storeModifier;

    public ListAdapterWithRecycleView(Context context, List<Store> storeList){
        this.storeList = storeList;
        this.context=context;
    }

    public void setStoreModifier(StoreModifier storeModifier){
        this.storeModifier = storeModifier;
    }

    @Override
    public StoreViewHolder onCreateViewHolder(final ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.layout_store_list,parent,false);
        final StoreViewHolder storeViewHolder = new StoreViewHolder(view);
        view.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                    int position = storeViewHolder.getAdapterPosition();
                    Toast.makeText(context,"Item at position "+position+" deleted",Toast.LENGTH_SHORT).show();
                    storeList.remove(position);
                    notifyDataSetChanged();
                    if(storeModifier!=null){storeModifier.onStoreDeleted(position);}
                    return true;
                }
        });
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // This toggles the background color and text of the store list recyclerviews when clicked.
                int colorName = -1;
                int textColor = 1979711488;
                int color = storeViewHolder.textViewStoreName.getCurrentTextColor();

                if(color != colorName) {
                    view.setBackgroundColor(Color.parseColor("#C43C00"));
                    storeViewHolder.textViewStoreName.setTextColor(Color.parseColor("#FFFFFF"));
                    storeViewHolder.textViewStoreNumber.setTextColor(Color.parseColor("#FFFFFF"));
                } else {
                    view.setBackgroundColor(Color.parseColor("#FFFFFF"));
                    storeViewHolder.textViewStoreName.setTextColor(textColor);
                    storeViewHolder.textViewStoreNumber.setTextColor(textColor);
                }

            }
        });
        /* view.setOnSwipe(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                int position = storeViewHolder.getAdapterPosition();
                Toast.makeText(context,"Item at position "+position+" deleted",Toast.LENGTH_SHORT).show();
                storeList.remove(position);
                notifyDataSetChanged();
                if(storeModifier!=null){storeModifier.onStoreDeleted(position);}
                return true;
            }
        });
        */
        Log.i(TAG,"onCreateViewHolder invoked");
        return storeViewHolder;
    }

    @Override
    public void onBindViewHolder(final StoreViewHolder holder, int position) {
        final Store store = storeList.get(position);
        holder.textViewStoreName.setText(store.getStoreName());
        holder.textViewStoreNumber.setText(store.getStoreNumber());

        Log.i(TAG,"onBindViewHolder invoked: "+position);
    }

    @Override
    public int getItemCount() {
        return storeList.size();
    }

    class StoreViewHolder extends RecyclerView.ViewHolder{
        public TextView textViewStoreName;
        public TextView textViewStoreNumber;

        public StoreViewHolder(View view){
            super(view);
            textViewStoreName = (TextView)view.findViewById(R.id.textViewStoreName);
            textViewStoreNumber=(TextView)view.findViewById(R.id.textViewStoreNumber);
        }
    }
}
