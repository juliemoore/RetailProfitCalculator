package com.example.julieannmoore.retailprofitcalculator.mData;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;

import java.io.Serializable;

/**
 * Created by Julie Moore on 3/25/2018.
 */

public class Formula implements Serializable {

    private int mFormulaId;
    private String mFormulaName;
    private int mImage;

    public Formula(String formulaName, int image) {
        mFormulaName = formulaName;
        mImage = image;
    }

    public Formula() {}

    public int getFormulaId() {
        return mFormulaId;
    }

    public void setFormulaId(int mFormulaId) {
        this.mFormulaId = mFormulaId;
    }

    public String getFormulaName() {
        return mFormulaName;
    }

    public void setFormulaName(String mFormulaName) {
        this.mFormulaName = mFormulaName;
    }

    public int getImage() { return mImage; }

    public void setImage(int mImage) { this.mImage = mImage; }

}
