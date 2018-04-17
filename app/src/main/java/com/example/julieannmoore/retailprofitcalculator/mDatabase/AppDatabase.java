package com.example.julieannmoore.retailprofitcalculator.mDatabase;

import android.arch.persistence.db.SupportSQLiteDatabase;
import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;

import com.example.julieannmoore.retailprofitcalculator.mData.Formula;
import com.example.julieannmoore.retailprofitcalculator.mData.Product;
import com.example.julieannmoore.retailprofitcalculator.mData.Store;
import com.example.julieannmoore.retailprofitcalculator.mData.StoreProductProfits;
import com.example.julieannmoore.retailprofitcalculator.mData.Summary;

import android.arch.persistence.room.migration.Migration;
import android.content.Context;


/**
 * Created by Julie Moore on 3/25/2018.
 */

@Database(entities = {Store.class, Product.class}, version = 1, exportSchema = false)
public abstract class AppDatabase extends RoomDatabase {

    public abstract StoreDao getStoreDao();
    public abstract ProductDao getProductDao();

    // Applies Singleton method
    private static AppDatabase mInstance;
    public static AppDatabase getInstance(Context context) {
        if (null == mInstance) {
            mInstance = createInstance(context);
        }
        return mInstance;
    }

/*
   static final Migration MIGRATION_1_2 = new Migration(1, 2) {
        @Override
        public void migrate(SupportSQLiteDatabase database) {

        }
    };

*/
    // Create database with Room Database Builder
    public static AppDatabase createInstance(final Context context) {
        AppDatabase newDatabase = Room.databaseBuilder(context, AppDatabase.class, "storesdb")
                .fallbackToDestructiveMigration()
                .allowMainThreadQueries()
                .build();

        return newDatabase;
    }

    public void cleanUp(){
        mInstance = null; }
}
