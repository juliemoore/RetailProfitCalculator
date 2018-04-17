package com.example.julieannmoore.retailprofitcalculator.mData;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;

import java.io.Serializable;

public class Summary implements Serializable {

    public int mStoreId;

    public int mProductId;

    private double mMarkUpDollars = 0;

    private double mMarkUpPercent = 0;

    private double mGMDollars = 0;

    private double mGMPercent = 0;

    private double mInventoryTurnover = 0;

    private double mWeeksSupplyOfInventory = 0;

    private double mGMROI = 0;

    private double mSalesPerFeet = 0;

    private double mGMDollarsPerFeet = 0;

    // Parameterized constructor
    @Ignore
    public Summary(int mStoreId, int mProductId, double mMarkUpDollars,
                               double mMarkUpPercent, double mGMDollars, double mGMPercent,
                               double mInventoryTurnover, double mWeeksSupplyOfInventory,
                               double mGMROI, double mSalesPerFeet, double mGMDollarsPerFeet) {
        this.mStoreId = mStoreId;
        this.mProductId = mProductId;
        this.mMarkUpDollars = mMarkUpDollars;
        this.mMarkUpPercent = mMarkUpPercent;
        this.mGMDollars = mGMDollars;
        this.mGMPercent = mGMPercent;
        this.mInventoryTurnover = mInventoryTurnover;
        this.mWeeksSupplyOfInventory = mWeeksSupplyOfInventory;
        this.mGMROI = mGMROI;
        this.mSalesPerFeet = mSalesPerFeet;
        this.mGMDollarsPerFeet = mGMDollarsPerFeet;
    }

    public Summary() {}

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

    public void setGMROI(double mGMROI) {
        this.mGMROI = mGMROI;
    }

    public double getSalesPerFeet() {
        return mSalesPerFeet;
    }

    public void setSalesPerFeet(double mSalesPerFeet) {
        this.mSalesPerFeet = mSalesPerFeet;
    }

    public double getGMDollarsPerFeet() {
        return mGMDollarsPerFeet;
    }

    public void setGMDollarsPerFeet(double mGMDollarsPerFeet) {
        this.mGMDollarsPerFeet = mGMDollarsPerFeet;
    }

    @Ignore
    public Summary(int productId, int storeId) {
        mProductId = productId;
        mStoreId = storeId;
    }
}

