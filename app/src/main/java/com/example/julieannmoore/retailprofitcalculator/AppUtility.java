package com.example.julieannmoore.retailprofitcalculator;

import android.content.Context;

import com.example.julieannmoore.retailprofitcalculator.mData.Store;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by Julie Moore 02/23/2018.
 */

public class AppUtility {
    private String [] storeNames;
    private String[] storeNumbers;

    private static final String EMPTY_STRING = "";

    private Context context;

    private List<Store> stores;

    private static AppUtility appUtility;
    private String storeName, storeNumber;

    private AppUtility(Context context){
        this.context=context;
        storeNames = context.getResources().getStringArray(R.array.stores);
        storeNumbers = context.getResources().getStringArray(R.array.storeNumbers);

        stores = new ArrayList<Store>();
        for(int i=0;i<storeNames.length;i++){
            storeName = storeNames[i];
            storeNumber = storeNumbers[i];
            Store store = new Store(i, storeName, storeNumber);
            stores.add(store);
        }
    }

    public static AppUtility getAppUtility(Context context){
        if(appUtility==null){
            appUtility=new AppUtility(context);
        }
        return appUtility;
    }

    public List<Store> getStores(){
        return this.stores;
    }

    public String[] getStoreNames() {
        return storeNames;
    }

    public String[] getStoreNumbers() {
        return storeNumbers;
    }

    public static boolean isStringEmpty(String string){
        boolean isStringEmpty=false;
        if (string==null){
            isStringEmpty=true;
        }else{
            if(string.length()==0 || string.equals(EMPTY_STRING)){
                isStringEmpty=true;
            }
        }
        return isStringEmpty;
    }
}
