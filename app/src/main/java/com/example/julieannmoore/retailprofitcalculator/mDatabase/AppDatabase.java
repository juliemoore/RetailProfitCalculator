package com.example.julieannmoore.retailprofitcalculator.mDatabase;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;

import com.example.julieannmoore.retailprofitcalculator.mData.Formula;
import com.example.julieannmoore.retailprofitcalculator.mData.Product;
import com.example.julieannmoore.retailprofitcalculator.mData.Store;
import com.example.julieannmoore.retailprofitcalculator.mData.StoreProductProfits;
import android.content.Context;


/**
 * Created by Julie Moore on 3/25/2018.
 */

@Database(entities = {Store.class, Product.class, StoreProductProfits.class, Formula.class }, version = 1)
public abstract class AppDatabase extends RoomDatabase {

    public abstract StoreDao getStoreDao();
    public abstract ProductDao getProductDao();
    public abstract StoreProductProfitsDao getProfitsDao();
    public abstract FormulaDao getFormulaDao();

    // Applies Singleton method
    private static AppDatabase mInstance;
    public static AppDatabase getInstance(Context context) {
        if (null == mInstance) {
            mInstance = createInstance(context);
        }
        return mInstance;
    }

    // Create database with Room Database Builder
    public static AppDatabase createInstance(final Context context) {
        AppDatabase newDatabase = Room.databaseBuilder(context, AppDatabase.class, "storesdb")
                .allowMainThreadQueries().build();

        return newDatabase;
    }

    public void cleanUp(){
        mInstance = null; }
}
