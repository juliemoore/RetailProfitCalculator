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

    @Query("SELECT * FROM products")
    public List<Product> getProducts();

    @Query("SELECT * FROM products WHERE product_name LIKE :name AND "
            + "store_id LIKE :storeId LIMIT 1")
    public Product findByNameAndStoreId(String name, int storeId);

    @Query("SELECT * FROM products WHERE store_id LIKE :storeId")
    public List<Product> findByStoreId(int storeId);

    @Query("SELECT * FROM products WHERE productId LIKE :productId LIMIT 1")
    Product findByProductId(int productId);

    @Query("UPDATE products SET product_name = :productName AND cost_of_goods = :costOfGoods AND "
            + "selling_price = :sellingPrice AND annual_units_sold = :annualUnitsSold AND "
            + "ave_weekly_inventory = :aveWeeklyInventory AND linear_feet = :linearFeet WHERE "
            + " productId = :productId AND store_id = :storeId")
    public void updateProduct(String productName, double costOfGoods, double sellingPrice,
                double annualUnitsSold, double aveWeeklyInventory, double linearFeet, int productId,
                int storeId);

    @Insert
    public void insertProduct(Product product);

    @Update
    public void updateProduct(Product product);

    @Delete
    public void deleteProduct(Product product);

    @Delete
    public void deleteProduct(Product... product); // Deletes a List


}