package com.example.julieannmoore.retailprofitcalculator.mData;

import android.content.Context;

import com.example.julieannmoore.retailprofitcalculator.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by msjul on 3/25/2018.
 */

public class FormulaCollection {

    private static Context context;

    public FormulaCollection(Context current){
        this.context = current;
    }


    public static List<Formula> getFormulas()
    {

        List<Formula> formulas = new ArrayList<>();
        Formula formula = null;

        // Get strings
        String mark_up_dollars = context.getResources().getString(R.string.mark_up_dollars);
        String mark_up_percent = context.getResources().getString(R.string.mark_up_percent);
        String gm_dollars = context.getResources().getString(R.string.gm_dollars);
        String gm_percent = context.getResources().getString(R.string.gm_percent);
        String inventory_turnover = context.getResources().getString(R.string.inventory_turnover);
        String weeks_supply_of_inventory = context.getResources().getString(R.string.weeks_supply_of_inventory);
        String gmroi = context.getResources().getString(R.string.gmroi);
        String sales_per_feet = context.getResources().getString(R.string.sales_per_feet);
        String gm_linear_feet = context.getResources().getString(R.string.gm_linear_feet);
        String annual_units_sold = context.getResources().getString(R.string.annual_units_sold);
        String ave_weekly_inventory = context.getResources().getString(R.string.ave_weekly_inventory);
        String linear_ft_sales_floor = context.getResources().getString(R.string.linear_ft_sales_floor);

        // Add Data
        formula = new Formula();
        formula.setFormulaId(1);
        formula.setFormulaName(mark_up_dollars);
        formula.setImage(R.drawable.mark_up_dollars);
        formulas.add(formula);

        formula = new Formula();
        formula.setFormulaId(2);
        formula.setFormulaName(mark_up_percent);
        formula.setImage(R.drawable.mark_up_percentage);
        formulas.add(formula);

        formula = new Formula();
        formula.setFormulaId(3);
        formula.setFormulaName(gm_dollars);
        formula.setImage(R.drawable.gm_dollars);
        formulas.add(formula);

        formula = new Formula();
        formula.setFormulaId(4);
        formula.setFormulaName(gm_percent);
        formula.setImage(R.drawable.gm_percentage);
        formulas.add(formula);

        formula = new Formula();
        formula.setFormulaId(5);
        formula.setFormulaName(inventory_turnover);
        formula.setImage(R.drawable.inventory_turn);
        formulas.add(formula);

        formula = new Formula();
        formula.setFormulaId(6);
        formula.setFormulaName(weeks_supply_of_inventory);
        formula.setImage(R.drawable.weeks_supply);
        formulas.add(formula);

        formula = new Formula();
        formula.setFormulaId(7);
        formula.setFormulaName(gmroi);
        formula.setImage(R.drawable.gmroi);
        formulas.add(formula);

        formula = new Formula();
        formula.setFormulaId(8);
        formula.setFormulaName(sales_per_feet);
        formula.setImage(R.drawable.sales_per_feet);
        formulas.add(formula);

        formula = new Formula();
        formula.setFormulaId(9);
        formula.setFormulaName(gm_linear_feet);
        formula.setImage(R.drawable.gm_per_linear_ft);
        formulas.add(formula);

        formula = new Formula();
        formula.setFormulaId(10);
        formula.setFormulaName(annual_units_sold);
        formula.setImage(R.drawable.formulasquare);
        formulas.add(formula);

        formula = new Formula();
        formula.setFormulaId(11);
        formula.setFormulaName(ave_weekly_inventory);
        formula.setImage(R.drawable.formulasquare);
        formulas.add(formula);

        formula = new Formula();
        formula.setFormulaId(12);
        formula.setFormulaName(linear_ft_sales_floor);
        formula.setImage(R.drawable.formulasquare);
        formulas.add(formula);

        return formulas;
    }
}


