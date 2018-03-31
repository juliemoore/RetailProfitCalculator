package com.example.julieannmoore.retailprofitcalculator.mDatabase;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import com.example.julieannmoore.retailprofitcalculator.mData.Store;

import java.util.List;

/**
 * Created by Julie Moore on 3/25/2018.
 */

@Dao
public interface StoreDao {

    @Query("SELECT * FROM stores")
    public List<Store> getStores();

    @Query("SELECT * FROM stores WHERE store_name LIKE :name AND "
            + "store_number LIKE :number LIMIT 1")
    Store findByNameAndNumber(String name, String number);

    @Query("SELECT * FROM stores WHERE storeId LIKE :storeId LIMIT 1")
    Store findByStoreId(int storeId);

    @Insert
    public void insertStore(Store store);

    @Update
    public void updateStore(Store store);

    @Delete
    public void deleteStore(Store store);

    @Delete
    public void deleteStoreList(Store... store); // Deletes a List
}
