package com.example.julieannmoore.retailprofitcalculator.mData;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;

/**
 * Created by Julie Moore on 3/25/2018.
 */

@Entity(tableName = "store__product_profits",
        primaryKeys = {"storeId","productId"})
public class StoreProductProfits {

    @ColumnInfo(name = "storeId")
    int mStoreId;

    @ColumnInfo (name = "productId")
    int mProductId;

    @ColumnInfo (name = "mark_up_dollars")
    double mMarkUpDollars = 0;

    @ColumnInfo (name = "mark_up_percent")
    double mMarkUpPercent = 0;

    @ColumnInfo (name = "gm_dollars")
    double mGMDollars = 0;

    @ColumnInfo (name = "gm_percent")
    double mGMPercent = 0;

    @ColumnInfo (name = "inventory_turnover")
    double mInventoryTurnover = 0;

    @ColumnInfo (name = "weeks_supply_of_inventory")
    double mWeeksSupplyOfInventory = 0;

    @ColumnInfo (name = "GMROI")
    double mGMROI = 0;

    @ColumnInfo (name = "sales_per_feet")
    double mSalesPerFeet = 0;

    @ColumnInfo (name = "gm_dollars_per_feet")
    double mGMDollarsPerFeet = 0;

    public int getStoreId() {
        return mStoreId;
    }

    public void setStoreId(int mStoreId) {
        this.mStoreId = mStoreId;
    }

    public int getProductId() {
        return mProductId;
    }

    public void setProductId(int mProductId) {
        this.mProductId = mProductId;
    }


    public double getMarkUpDollars() {
        return mMarkUpDollars;
    }

    public void setMarkUpDollars(double mMarkUpDollars) {
        this.mMarkUpDollars = mMarkUpDollars;
    }

    public double getMarkUpPercent() {
        return mMarkUpPercent;
    }

    public void setMarkUpPercent(double mMarkUpPercent) {
        this.mMarkUpPercent = mMarkUpPercent;
    }

    public double getGMDollars() {
        return mGMDollars;
    }

    public void setGMDollars(double mGMDollars) {
        this.mGMDollars = mGMDollars;
    }

    public double getGMPercent() {
        return mGMPercent;
    }

    public void setGMPercent(double mGMPercent) {
        this.mGMPercent = mGMPercent;
    }

    public double getInventoryTurnover() {
        return mInventoryTurnover;
    }

    public void setInventoryTurnover(double mInventoryTurnover) {
        this.mInventoryTurnover = mInventoryTurnover;
    }

    public double getWeeksSupplyOfInventory() {
        return mWeeksSupplyOfInventory;
    }

    public void setWeeksSupplyOfInventory(double mWeeksSupplyOfInventory) {
        this.mWeeksSupplyOfInventory = mWeeksSupplyOfInventory;
    }

    public double getGMROI() {
        return mGMROI;
    }

    public void setmGMROI(double mGMROI) {
        this.mGMROI = mGMROI;
    }

    public double getmSalesPerFeet() {
        return mSalesPerFeet;
    }

    public void setmSalesPerFeet(double mSalesPerFeet) {
        this.mSalesPerFeet = mSalesPerFeet;
    }

    public double getmGMDollarsPerFeet() {
        return mGMDollarsPerFeet;
    }

    public void setmGMDollarsPerFeet(double mGMDollarsPerFeet) {
        this.mGMDollarsPerFeet = mGMDollarsPerFeet;
    }

    public StoreProductProfits(int productId, int storeId) {
        mProductId = productId;
        mStoreId = storeId;
    }

}