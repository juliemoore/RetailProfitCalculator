package com.example.julieannmoore.retailprofitcalculator.mUtilities;

import com.example.julieannmoore.retailprofitcalculator.mAdapter.ProductAdapter;
import com.example.julieannmoore.retailprofitcalculator.mData.Product;

public interface ProductListEventCallbacks {
    void Select(Product product, ProductAdapter adapter, int itemId);
}
