package com.example.julieannmoore.retailprofitcalculator.mUtilities;

import com.example.julieannmoore.retailprofitcalculator.mAdapter.FormulaAdapter;
import com.example.julieannmoore.retailprofitcalculator.mData.Formula;

public interface FormulaListEventCallbacks {
    void Select(Formula formula, FormulaAdapter adapter, int itemId);
}
