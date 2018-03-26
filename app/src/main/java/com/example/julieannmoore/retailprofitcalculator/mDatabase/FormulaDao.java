package com.example.julieannmoore.retailprofitcalculator.mDatabase;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import com.example.julieannmoore.retailprofitcalculator.mData.Formula;

import java.util.List;

/**
 * Created by msjul on 3/25/2018.
 */

@Dao
public interface FormulaDao {
    @Query("SELECT * FROM Constants.TABLE_NAME_FORMULA")
    public List<Formula> getFormulas();

    @Insert
    public void insertFormula(Formula formula);

    @Update
    public void updateFormula(Formula formula);

    @Delete
    public void deleteFormula(Formula formula);

    @Delete
    public void deleteFormulaList(Formula... formula); // Deletes a List
}
