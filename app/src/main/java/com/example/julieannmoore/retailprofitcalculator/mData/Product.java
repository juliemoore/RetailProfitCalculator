package com.example.julieannmoore.retailprofitcalculator.mData;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;

import java.io.Serializable;

/**
 * Created by Julie Moore on 3/25/2018.
 */

@Entity(tableName = "products",
        foreignKeys = @ForeignKey(entity = Store.class,
                parentColumns = "storeId",
                childColumns = "store_id"))
public class Product implements Serializable {

    @PrimaryKey(autoGenerate = true)
    private int mProductId;

    @ColumnInfo(name = "product_name")
    private String mProductName;

    @ColumnInfo(name = "store_id")
    private int mStoreId;

    @ColumnInfo(name = "cost_of_goods")
    private double mCostOfGoods;

    @ColumnInfo(name = "selling_price")
    private double mSellingPrice;

    @ColumnInfo(name = "annual_units_sold")
    private double mAnnualUnitsSold;

    @ColumnInfo(name = "ave_weekly_inventory")
    private double mAveWeeklyInventory;

    @ColumnInfo(name = "linear_feet")
    private double mLinearFeet;

    // Default constructor
    @Ignore
    public Product() {
        mProductId = 0;
        mProductName = "";
        mStoreId = 0;
        mCostOfGoods = 0;
        mSellingPrice = 0;
        mAnnualUnitsSold = 0;
        mAveWeeklyInventory = 0;
        mLinearFeet = 0;
    }
    // Parameterized constructor
    public Product(String productName, int storeId, double costOfGoods, double sellingPrice,
                   double annualUnitsSold, double aveWeeklyInventory, double linearFeet) {
        mProductName = productName;
        mCostOfGoods = costOfGoods;
        mStoreId = storeId;
        mSellingPrice = sellingPrice;
        mAnnualUnitsSold = annualUnitsSold;
        mAveWeeklyInventory = aveWeeklyInventory;
        mLinearFeet = linearFeet;
    }

    // Getter/setter methods
    public int getProductId() {
        return mStoreId;
    }

    public String getProductName() {
        return mProductName;
    }

    public int getStoreId() { return  mStoreId; }

    public double getCostOfGoods() {
        return mCostOfGoods;
    }

    public double getSellingPrice() {
        return mSellingPrice;
    }

    public double getAnnualUnitsSold() {
        return mAnnualUnitsSold;
    }

    public double getAveWeeklyInventory() {
        return mAveWeeklyInventory;
    }

    public double getLinearFeet() { return mLinearFeet; }

    public void setProductId(int productId) {
        mProductId = productId;
    }

    public void setProductName(String productName) {
        mProductName = productName;
    }

    public void setStoreId(int storeId) { mStoreId = storeId; }

    public void setCostOfGoods(double costOfGoods) {
        mCostOfGoods = costOfGoods;
    }

    public void setSellingPrice(double sellingPrice) {
        mSellingPrice = sellingPrice;
    }

    public void setAnnualUnitsSold(double annualUnitsSold) { mAnnualUnitsSold = annualUnitsSold; }

    public void setAveWeeklyInventory(double aveWeeklyInventory) { mAveWeeklyInventory = aveWeeklyInventory; }

    public void setLinearFeet(double linearFeet) {
        mLinearFeet = linearFeet;
    }

    public boolean equals(Product obj) {
        if(this == obj) { return true; }
        if(!(obj instanceof Product)) { return false; }

        Product castProduct = (Product) obj;
        if(mProductId != castProduct.mProductId) { return false; }

        // obj mContent = Not Null | mContent = Null // Definately Not Equal
        // obj.mContent = Null | mContent = Not Null // Definately Not Equal
        if(castProduct.mProductName != null && mProductName == null) { return false; }
        if(castProduct.mProductName == null && mProductName != null) { return  false; }

        // obj.mContent = Null | mContent = Null // Could Be Equal
        // obj.mContent = Not Null | mContent = Not Null // Could Be Equal
        if(castProduct.mProductName != null && !castProduct.mProductName.equals(mProductName)) { return false; }

        return true;
    }

    @Override
    public String toString() {
        return "String(StoreName = " + mStoreId + "\nProductId" + mProductId + "\nProductName = " + mProductName + ")";
    }

}