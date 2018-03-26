package com.example.julieannmoore.retailprofitcalculator.mDatabase;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import com.example.julieannmoore.retailprofitcalculator.mData.StoreProductProfits;

import java.util.List;

/**
 * Created by Julie Moore on 3/25/2018.
 */

@Dao
public interface StoreProductProfitsDao {

    @Query("SELECT * FROM Constants.TABLE_NAME_STOREPRODUCTPROFITS")
    public List<StoreProductProfits> getStoreProductProfits();

    @Insert
    public void insertStoreProductProfit(StoreProductProfits storeProductProfits);

    @Update
    public void updateStoreProductProfit(StoreProductProfits storeProductProfits);

    @Delete
    public void deleteStoreProductProfit(StoreProductProfits storeProductProfits);

    @Delete
    public void deleteStoreProductProfitList(StoreProductProfits... storeProductProfits); // Deletes a List
}
