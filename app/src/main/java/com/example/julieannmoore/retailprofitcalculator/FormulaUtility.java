package com.example.julieannmoore.retailprofitcalculator;

import android.content.Context;

import java.text.Normalizer;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Julie Moore on 3/13/2018.
 */

public class FormulaUtility {

    private String [] formulaNames;
    private String[] formulaAmounts;

    private static final String EMPTY_STRING = "";

    private Context context;

    private List<Formula> formulas;

    private static FormulaUtility formulaUtility;

    private FormulaUtility(Context context){
        this.context=context;
        formulaNames = context.getResources().getStringArray(R.array.formulaNames);
        //formulaAmounts = context.getResources().getStringArray(R.array.formulaAmounts);

        formulas = new ArrayList<Formula>();
        for(int i=0;i<formulaNames.length;i++){
            Formula formula = new Formula(formulaNames[i], formulaAmounts[i]);
            formulas.add(formula);
        }
    }

    public static FormulaUtility getFormulaUtility(Context context){
        if(formulaUtility==null){
            formulaUtility =new FormulaUtility(context);
        }
        return formulaUtility;
    }

    public List<Formula> getFormulas(){
        return this.formulas;
    }

    public String[] getFormulaNames() {
        return formulaNames;
    }

    public String[] getFormulaAmounts() {
        return formulaAmounts;
    }

    public static boolean isStringEmpty(String string){
        boolean isStringEmpty=false;
        if (string==null){
            isStringEmpty=true;
        }else{
            if(string.length()==0 || string.equals(EMPTY_STRING)){
                isStringEmpty=true;
            }
        }
        return isStringEmpty;
    }
}

