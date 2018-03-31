package com.example.julieannmoore.retailprofitcalculator.mUtilities;

import com.example.julieannmoore.retailprofitcalculator.mAdapter.SummaryAdapter;
import com.example.julieannmoore.retailprofitcalculator.mData.Summary;

public interface SummaryListEventCallbacks {
    void Select(Summary summary, SummaryAdapter adapter, int itemId);
}
