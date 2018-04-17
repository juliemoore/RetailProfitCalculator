package com.example.julieannmoore.retailprofitcalculator;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.julieannmoore.retailprofitcalculator.mAdapter.SummaryAdapter;
import com.example.julieannmoore.retailprofitcalculator.mData.Formula;
import com.example.julieannmoore.retailprofitcalculator.mData.FormulaCollection;
import com.example.julieannmoore.retailprofitcalculator.mData.Product;
import com.example.julieannmoore.retailprofitcalculator.mData.Store;
import com.example.julieannmoore.retailprofitcalculator.mData.StoreProductProfits;
import com.example.julieannmoore.retailprofitcalculator.mData.Summary;
import com.example.julieannmoore.retailprofitcalculator.mDatabase.AppDatabase;
import com.example.julieannmoore.retailprofitcalculator.mDialogs.CustomProductDialog;

import java.lang.ref.WeakReference;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import static com.example.julieannmoore.retailprofitcalculator.mData.FormulaCollection.getFormulas;

public class SummaryActivity extends AppCompatActivity {

    private TextView storeNameTextView, storeNumberTextView, productTextView;
    private Button mUpdateButton, mSaveButton, mShareButton;
    private ListView mListView;
    private SummaryAdapter mAdapter;
    private Resources mResources;
    private AppDatabase mDatabase;
    private Product mProduct;
    private Store mStore;
    private Summary mSummary;
    private String[] mFormulaNames, mFormulaAmounts;
    private Boolean isUpdate, isSummaryView;
    private int storeId, productId;
    private String storeName, storeNumber, productName;
    private double cost_of_goods, selling_price, mark_up_dollars, mark_up_percent, gm_dollars,
            gm_percent, inventory_turnover, weeks_supply_of_inventory, gmroi,
            sales_per_feet, gm_linear_feet, annual_units_sold, ave_weekly_inventory,
            linear_ft;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_summary);

        // Set action bar with logo
        ActionBar actionBar = getSupportActionBar();
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        actionBar.setLogo(R.mipmap.ic_launcher_round);
        actionBar.setDisplayUseLogoEnabled(true);
        actionBar.setTitle(R.string.profitability_summary);

        isUpdate = false;
        isSummaryView = false;

        // Get data from database
        mDatabase = AppDatabase.getInstance(this);
        mSummary = new Summary();
        initializeView();
        getData();
        mSummary = calculate(mSummary);
        getFormulaAmounts();

        mAdapter = new SummaryAdapter(this, mFormulaNames, mFormulaAmounts);
        mListView.setAdapter(mAdapter);

        mSaveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toastMessage(getString(R.string.save_product_successful));
                Intent intent = new Intent(SummaryActivity.this, MainActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);    // Clears stack
                startActivity(intent);
            }
        });
        mUpdateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SummaryActivity.this, AddProductActivity.class);
                intent.putExtra("UpdateProduct", mProduct);
                startActivity(intent);
            }
        });

    }

    public void initializeView() {
        storeNameTextView = findViewById(R.id.storeNameTextView3);
        storeNumberTextView = findViewById(R.id.storeNumberTextView3);
        productTextView = findViewById(R.id.productSummaryTextView);
        mListView = findViewById(R.id.list_view);
        mSaveButton = findViewById(R.id.saveButton);
        mUpdateButton = findViewById(R.id.updateButton);
        mShareButton = findViewById(R.id.shareButton);
    }

    private void getData() {
        if( ((mProduct = (Product) getIntent().getSerializableExtra("Calculate")) != null)) {
            storeId = mProduct.getStoreId();
            mStore = mDatabase.getStoreDao().findByStoreId(storeId);
            storeName = mStore.getStoreName();
            storeNumber = mStore.getStoreNumber();
            productId = mProduct.getProductId();
            productName = mProduct.getProductName();
            cost_of_goods = mProduct.getCostOfGoods();
            selling_price = mProduct.getSellingPrice();
            annual_units_sold = mProduct.getAnnualUnitsSold();
            ave_weekly_inventory = mProduct.getAveWeeklyInventory();
            linear_ft = mProduct.getLinearFeet();

            storeNameTextView.setText(storeName);
            storeNumberTextView.setText(storeNumber);
            productTextView.setText(productName);
        }
    }

    private Summary calculate(Summary summary) {
        mark_up_dollars = markUpDollars();
        mark_up_percent = markUpPercentage(mark_up_dollars);
        gm_dollars = grossMarginDollars();
        gm_percent = grossMarginPercentage(gm_dollars);
        inventory_turnover = inventoryTurnover();
        weeks_supply_of_inventory = weeksSupplyOfInventory(inventory_turnover);
        gmroi = gmroi();
        sales_per_feet = salesPerFeet();
        gm_linear_feet = gmDollarsPerFeet(gm_percent, sales_per_feet);

        summary = new Summary(storeId, productId, mark_up_dollars, mark_up_percent, gm_dollars,
                            gm_percent, inventory_turnover, weeks_supply_of_inventory, gmroi,
                            sales_per_feet, gm_linear_feet);
        return summary;
    }

    private void getFormulaAmounts() {
        Resources res = getResources();
        mFormulaNames = res.getStringArray(R.array.formulaNames);
        DecimalFormat dFormat = new DecimalFormat("####,###,###.00");
        mFormulaAmounts = new String[14];
        mFormulaAmounts[0] = String.valueOf("$" + cost_of_goods);
        mFormulaAmounts[1] = String.valueOf("$" + selling_price);
        mFormulaAmounts[2] = String.valueOf(Math.round(annual_units_sold));
        mFormulaAmounts[3] = String.valueOf(Math.round(ave_weekly_inventory));
        mFormulaAmounts[4] = String.valueOf(linear_ft);
        mFormulaAmounts[5] = String.format("$" + dFormat.format(mark_up_dollars));
        mFormulaAmounts[6] = String.valueOf(Math.round(mark_up_percent * 100)) + "%";
        mFormulaAmounts[7] = String.format("$" + dFormat.format(gm_dollars));
        mFormulaAmounts[8] = String.valueOf(Math.round(gm_percent * 100)) + "%";
        mFormulaAmounts[9] = String.format(dFormat.format(inventory_turnover));
        mFormulaAmounts[10] = String.format(dFormat.format(weeks_supply_of_inventory));
        mFormulaAmounts[11] = String.format("$" + dFormat.format(gmroi));
        mFormulaAmounts[12] = String.format("$" + dFormat.format(sales_per_feet));
        mFormulaAmounts[13] = String.format("$" + dFormat.format(gm_linear_feet));
    }

    /* Method to calculate mark-up dollars */
    public double markUpDollars() {
        double markUpDollars = 0;
        markUpDollars = selling_price - cost_of_goods;
        return markUpDollars;
    }

    /* Method to calculate mark-up percentage */
    public double markUpPercentage(double markUpDollars) {
        double markUpPercentage = 0;
        markUpPercentage = markUpDollars / cost_of_goods;
        return markUpPercentage;
    }

    /* Method to calculate the gross margin dollars of a retail item */
    public double grossMarginDollars() {
        double grossMarginDollars = 0;
        grossMarginDollars = selling_price - cost_of_goods;
        return grossMarginDollars;
    }

    /* Method to calculate mark-up percentage */
    public double grossMarginPercentage(double grossMarginDollars) {
        double grossMarginPercentage = 0;
        grossMarginPercentage = grossMarginDollars / selling_price;
        return grossMarginPercentage;
    }

    /* Method to calculate inventory turnover */
    public double inventoryTurnover() {
        double inventoryTurnover = 0;
        inventoryTurnover = annual_units_sold / ave_weekly_inventory;
        return inventoryTurnover;
    }

    /* Method to calculate weeks supply of inventory */
    public double weeksSupplyOfInventory(double inventoryTurnover) {
        double weeksSupplyOfInventory = 0;
        int weeksInYr = 52;
        weeksSupplyOfInventory = weeksInYr / inventoryTurnover;
        return weeksSupplyOfInventory;
    }

    /* Method to calculate GMROI (GMROI = annual gross profits / inventory $'s invested */
    public double gmroi() {
        double annualGrossProfits = 0;
        double inventoryDollarsInvested = 0;
        double gmroi = 0;
        int weeksInYr = 52;
        annualGrossProfits = (selling_price - cost_of_goods) * annual_units_sold;
        inventoryDollarsInvested = (ave_weekly_inventory * cost_of_goods) * weeksInYr;
        gmroi = annualGrossProfits / inventoryDollarsInvested;
        return gmroi;
    }

    /* Method to calculate sales per linear foot */
    public double salesPerFeet() {
        double weeklySales = 0;
        int weeksInYr = 52;
        double salesPerFeet = 0;
        weeklySales = annual_units_sold / weeksInYr;
        salesPerFeet = weeklySales / linear_ft;
        return salesPerFeet;
    }

    /* Method to calculate gross margin dollars per linear foot */
    public double gmDollarsPerFeet(double grossMarginPercentage, double salesPerFeet) {
        double gmDollarsPerFeet = 0;
        gmDollarsPerFeet = grossMarginPercentage * salesPerFeet;
        return  gmDollarsPerFeet;
    }

    private void toastMessage(String message){
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    public void sendEmail(View view) {
        Intent emailIntent = new Intent(Intent.ACTION_SENDTO,
                Uri.fromParts("mailto", "", null));
        // The intent does not have a URI, so declare the "text/plain" MIME type
        String subject = getString(R.string.profitability_summary);
        String info = "Store name: " + storeName + "\nStore number: "  + storeNumber +
                "\nProduct: " + productName + "\n";
                for (int i = 0; i < mFormulaNames.length; i++) {
                    info += mFormulaNames[i] + ":  " + mFormulaAmounts[i].toString() + "\n";
                }
        emailIntent.putExtra(Intent.EXTRA_SUBJECT, subject);
        emailIntent.putExtra(Intent.EXTRA_TEXT, info);
        emailIntent.putExtra(Intent.EXTRA_STREAM, Uri.parse("content://path/to/email/attachment"));

        /* Verify it resolves
        PackageManager packageManager = getPackageManager();
        List<ResolveInfo> activities = packageManager.queryIntentActivities(emailIntent, 0);
        boolean isIntentSafe = activities.size() > 0;

        // Start an activity if it's safe
        if (!isIntentSafe) {
            Toast.makeText(MainActivity.this,"There is no activity to handle this " +
                    "request on your device.", Toast.LENGTH_LONG).show();
        } else {
            startActivity(emailIntent);
        }
        */
        // Or let user choose app
        String title = getResources().getString(R.string.select_app);
        // Create intent to show chooser
        Intent chooser = Intent.createChooser(emailIntent, title);

        // Verify the intent will resolve to at least one activity
        if (emailIntent.resolveActivity(getPackageManager()) != null) {
            startActivity(chooser);
        }

    }
}
