package com.example.julieannmoore.retailprofitcalculator.mUtilities;

import com.example.julieannmoore.retailprofitcalculator.mAdapter.StoreAdapter;
import com.example.julieannmoore.retailprofitcalculator.mData.Store;

/**
 * Created by Julie Moore on 3/26/2018.
 */

public interface StoreListEventCallbacks {
    void Select(Store store, StoreAdapter adapter, int itemId);
}
