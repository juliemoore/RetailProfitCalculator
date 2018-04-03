package com.example.julieannmoore.retailprofitcalculator.mData;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;

import java.io.Serializable;

/**
 * Created by Julie Moore on 3/25/2018.
 */

@Entity(tableName = "formulas")
public class Formula implements Serializable {

    @PrimaryKey(autoGenerate = false)
    @ColumnInfo(name = "formulaId")
    private int mFormulaId;

    @ColumnInfo (name = "formula_name")
    private String mFormulaName;

    @ColumnInfo (name = "image")
    private int mImage;

    @Ignore
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

    @Override
    public String toString() {
        return "Formula{ FormulaId=" + mFormulaId + ", Content = " + mFormulaName + ")";
    }
}
