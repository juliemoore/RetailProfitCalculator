package com.example.julieannmoore.retailprofitcalculator.mData;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.content.Context;

import com.example.julieannmoore.retailprofitcalculator.StoreListActivity;

import java.io.Serializable;

/**
 * Created by Julie Moore on 3/25/2018.
 */

@Entity(tableName = "stores")
public class Store implements Serializable {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "storeId")
    private int mStoreId;

    @ColumnInfo(name = "store_name")    // mStoreName will be store_name in table
    private String mStoreName;

    @ColumnInfo(name = "store_number")  // mStoreNumber will be store_number in table
    private String mStoreNumber;

    public Store() {}

    // Parameterized constructor
    public Store(int storeId, String storeName, String storeNumber) {
        mStoreId = storeId;
        mStoreName = storeName;
        mStoreNumber = storeNumber;
    }

    // Class mutators
    public int getStoreId() { return mStoreId; }

    public String getStoreName() {
        return mStoreName;
    }

    public String getStoreNumber() {
        return mStoreNumber;
    }

    public void setStoreId(int storeId) { mStoreId = storeId; }

    public void setStoreName(String storeName) { mStoreName = storeName; }

    public void setStoreNumber(String storeNumber) { mStoreNumber = storeNumber; }

    @Override
    public boolean equals(Object obj) {
        if(this == obj) { return true; }
        if(!(obj instanceof Store)) { return false; }

        Store castStore = (Store) obj;
        if(mStoreId != castStore.mStoreId) { return false; }

        // obj mContent = Not Null | mContent = Null // Definately Not Equal
        // obj.mContent = Null | mContent = Not Null // Definately Not Equal
        if(castStore.mStoreName != null && mStoreName == null) { return false; }
        if(castStore.mStoreName == null && mStoreName != null) { return  false; }

        // obj.mContent = Null | mContent = Null // Could Be Equal
        // obj.mContent = Not Null | mContent = Not Null // Could Be Equal
        if(castStore.mStoreName != null && !castStore.mStoreName.equals(mStoreName)) { return false; }

        if(castStore.mStoreNumber != null && mStoreNumber == null) { return false; }
        if(castStore.mStoreNumber == null && mStoreNumber != null) { return  false; }
        if(castStore.mStoreNumber != null && !castStore.mStoreNumber.equals(mStoreNumber)) { return false; }

        return true;
    }

    @Override
    public String toString() {
        return "String( StoreId = " + mStoreId + ", StoreName = " + mStoreName + ", StoreNumber = " + mStoreNumber + ")";
    }

}
