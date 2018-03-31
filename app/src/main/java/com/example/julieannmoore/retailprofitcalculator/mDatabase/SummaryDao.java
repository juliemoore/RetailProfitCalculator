package com.example.julieannmoore.retailprofitcalculator.mDatabase;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import com.example.julieannmoore.retailprofitcalculator.mData.Store;
import com.example.julieannmoore.retailprofitcalculator.mData.Summary;

import java.util.List;

@Dao
public interface SummaryDao {

    @Query("SELECT * FROM summary")
    public List<Summary> getSummary();

    @Query("SELECT * FROM summary WHERE storeId LIKE :storeId AND "
            + "productId LIKE :productId LIMIT 1")
    Summary findByIds(int storeId, int productId);

    @Insert
    public void insertSummary(Summary summary);

    @Update
    public void updateSummary(Summary summary);

    @Delete
    public void deleteSummary(Summary summary);

    @Delete
    public void deleteSummaryList(Summary... Summary); // Deletes a List
}
