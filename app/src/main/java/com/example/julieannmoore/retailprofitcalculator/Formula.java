package com.example.julieannmoore.retailprofitcalculator;

/**
 * Created by msjul on 3/13/2018.
 */

public class Formula {

    private String formulaName, formulaAmount;

    public Formula() {
    }

    public Formula(String formulaName, String formulaAmount) {
        this.formulaName = formulaName;
        this.formulaAmount = formulaAmount;
    }

    public String getFormulaAmount() {
        return formulaAmount;
    }

    public void setFormulaAmount(String formulaAmount) {
        this.formulaAmount = formulaAmount;
    }

    public String getFormulaName() {
        return formulaName;
    }

    public void setFormulaName(String formulaName) {
        this.formulaName = formulaName;
    }


}
