package com.example.julieannmoore.retailprofitcalculator;

/**
 * Created by Julie Moore on 2/25/2018.
 */

public class Store  {

    private String storeName, storeNumber;

    // Default constructor
    public Store() {
    }

    // Parameterized constructor
    public Store(String storeName, String storeNumber) {
        this.storeName = storeName;
        this.storeNumber = storeNumber;
    }

    // Class mutators
    public String getStoreName() {
        return storeName;
    }

    public String getStoreNumber() {
        return storeNumber;
    }

    public void setStoreName(String storeName) {
        this.storeName = storeName;
    }

    public void setStoreNumber(String storeNumber) {
        this.storeNumber = storeNumber;
    }
}
