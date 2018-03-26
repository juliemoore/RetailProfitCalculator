package com.example.julieannmoore.retailprofitcalculator.mDatabase;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import com.example.julieannmoore.retailprofitcalculator.mData.Product;

import java.util.List;

/**
 * Created by Julie Moore on 3/25/2018.
 */

@Dao
public interface ProductDao {

    @Query("SELECT * FROM Constants.TABLE_NAME_PRODUCT")
    public List<Product> getProducts();

    @Query("SELECT * FROM Constants.TABLE_NAME_PRODUCT WHERE product_name LIKE :name LIMIT 1")
    Product findByName(String name);

    @Insert
    public void insertProduct(Product product);

    @Update
    public void updateProduct(Product product);

    @Delete
    public void deleteProduct(Product product);

    @Delete
    public void deleteProduct(Product... product); // Deletes a List


}