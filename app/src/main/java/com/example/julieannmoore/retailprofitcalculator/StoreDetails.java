package com.example.julieannmoore.retailprofitcalculator;

/**
 * Created by Julie Moore on 2/25/2018.
 */

public class StoreDetails extends Store {
    private static String storeName;
    private static String storeNumber;
    private double costOfGoods;
    private double sellingPrice;
    private int annualUnitsSold;
    private double aveWeeklyInventory;
    private double linearFeet;

    // Default constructor
    public StoreDetails() {
        super(storeName, storeNumber);
        costOfGoods = 0;
        sellingPrice = 0;
        annualUnitsSold = 0;
        aveWeeklyInventory = 0;
        linearFeet = 0;
    }
    // Parameterized constructor
    public StoreDetails(String storeName, String storeNumber, double costOfGoods, double sellingPrice,
                        int annualUnitsSold, double aveWeeklyInventory, double linearFeet) {
        super(storeName, storeNumber);
        this.costOfGoods = costOfGoods;
        this.sellingPrice = sellingPrice;
        this.annualUnitsSold = annualUnitsSold;
        this.aveWeeklyInventory = aveWeeklyInventory;
        this.linearFeet = linearFeet;
    }

    // Getter/setter methods
    public double getCostOfGoods() {
        return costOfGoods;
    }

    public double getSellingPrice() {
        return sellingPrice;
    }

    public int getAnnualUnitsSold() {
        return annualUnitsSold;
    }

    public double getAveWeeklyInventory() {
        return aveWeeklyInventory;
    }

    public double getLinearFeet() {
        return linearFeet;
    }

    public void setCostOfGoods(double costOfGoods) {
        this.costOfGoods = costOfGoods;
    }

    public void setSellingPrice(double sellingPrice) {
        this.sellingPrice = sellingPrice;
    }

    public void setAnnualUnitsSold(int annualUnitsSold) {
        this.annualUnitsSold = annualUnitsSold;
    }

    public void setAveWeeklyInventory(int aveWeeklyInventory) {
        this.aveWeeklyInventory = aveWeeklyInventory;
    }

    public void setLinearFeet(double linearFeet) {
        this.linearFeet = linearFeet;
    }

    /* Method to calculate mark-up dollars */
    public Double markUpDollars() {
        double markUpDollars = 0;
        markUpDollars = this.sellingPrice - this.costOfGoods;
        return markUpDollars;
    }

    /* Method to calculate mark-up percentage */
    public Double markUpPercentage(double markUpDollars) {
        double markUpPercentage = 0;
        markUpPercentage = markUpDollars - this.costOfGoods;
        return markUpPercentage;
    }

    /* Method to calculate the gross margin dollars of a retail item */
    public Double grossMarginDollars() {
        double grossMarginDollars = 0;
        grossMarginDollars = this.sellingPrice - costOfGoods;
        return grossMarginDollars;
    }

    /* Method to calculate mark-up percentage */
    public Double grossMarginPercentage(double grossMarginDollars) {
        double grossMarginPercentage = 0;
        grossMarginPercentage = grossMarginDollars - this.costOfGoods;
        return grossMarginPercentage;
    }

    /* Method to calculate the selling price of a retail item */
    public Double sellingPrice(double costOfGoods, double markUpDollars) {
        double sellingPrice = 0;
        sellingPrice = this.costOfGoods + markUpDollars;
        return sellingPrice;
    }

    /* Method to calculate inventory turnover */
    public Double inventoryTurnover(double annualUnitsSold, double aveWeeklyInventory) {
        double inventoryTurnover = 0;
        inventoryTurnover = this.annualUnitsSold / this.aveWeeklyInventory;
        return inventoryTurnover;
    }

    /* Method to calculate weeks supply of inventory */
    public Double weeksSupplyOfInventory(double inventoryTurnover) {
        double weeksSupplyOfInventory = 0;
        int weeksInYr = 52;
        weeksSupplyOfInventory = weeksInYr / inventoryTurnover;
        return weeksSupplyOfInventory;
    }

    /* Method to calculate GMROI (GMROI = annual gross profits / inventory $'s invested */
    public Double gmroi() {
        double annualGrossProfits = 0;
        double inventoryDollarsInvested = 0;
        double gmroi = 0;
        int weeksInYr = 52;
        annualGrossProfits = (this.sellingPrice - this.costOfGoods) * this.annualUnitsSold;
        inventoryDollarsInvested = (this.aveWeeklyInventory * this.costOfGoods) * weeksInYr;
        gmroi = annualGrossProfits / inventoryDollarsInvested;
        return gmroi;
    }

    /* Method to calculate sales per linear foot */
    public Double salesPerFeet() {
        double weeklySales = 0;
        int weeksInYr = 52;
        double salesPerFeet = 0;
        weeklySales = this.annualUnitsSold * weeksInYr;
        salesPerFeet = weeklySales / this.linearFeet;
        return salesPerFeet;
    }

    /* Method to calculate gross margin dollars per linear foot */
    public Double gmDollarsPerFeet(double grossMarginPercentage, double salesPerFeet) {
        double gmDollarsPerFeet = 0;
        gmDollarsPerFeet = grossMarginPercentage * salesPerFeet;
        return  gmDollarsPerFeet;
    }

} // end StoreDetails class


