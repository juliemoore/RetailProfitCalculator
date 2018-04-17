package com.example.julieannmoore.retailprofitcalculator;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.support.annotation.IdRes;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    Button mAddStore, mViewStore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Set action bar with logo
        ActionBar actionBar = getSupportActionBar();
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        actionBar.setLogo(R.mipmap.ic_launcher_round);
        actionBar.setDisplayUseLogoEnabled(true);

        mAddStore = findViewById(R.id.bn_add_store);
        mViewStore = findViewById(R.id.bn_view_store);

        mAddStore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent addStoreIntent = new Intent(MainActivity.this, AddStoreActivity.class);
                startActivity(addStoreIntent);
            }
        });

        mViewStore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent viewStoreIntent = new Intent(MainActivity.this, StoreListActivity.class);
                startActivity(viewStoreIntent);
            }
        });
    }

    public void viewFormulas(View view) {
        Intent viewFormulasIntent = new Intent(this, FormulaListActivity.class);
        startActivity(viewFormulasIntent);
    }
}
