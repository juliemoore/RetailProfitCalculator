package com.example.julieannmoore.retailprofitcalculator;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.support.annotation.IdRes;
import android.support.design.widget.FloatingActionButton;
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

public class MainActivity extends AppCompatActivity implements ListAdapterWithRecycleView.StoreModifier{

    public static final String EXTRA_STORE = "com.example.julieannmoore.STORE";
    private RecyclerView recyclerView;
    AppUtility appUtility;

    ListAdapterWithRecycleView listAdapterWithRecycleView;
    private FloatingActionButton fab1;

    List<Store> stores;
    private Context context;

    EditText editTextStoreName, editTextStoreNumber;

    LinearLayoutManager linearLayoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        context = getApplicationContext();

        FloatingActionButton fab1 = findViewById(R.id.fab1);
        fab1 = findViewById(R.id.fab1);
        fab1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, AddStoreActivity.class));
            }
        });

        FloatingActionButton fab2 = findViewById(R.id.fab2);
        fab2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, StoreDataActivity.class));
            }
        });
        appUtility=AppUtility.getAppUtility(getApplicationContext());

        recyclerView =findViewById(R.id.recycleListView);

        stores = appUtility.getStores();

        listAdapterWithRecycleView=new ListAdapterWithRecycleView(this,stores);
        listAdapterWithRecycleView.setStoreModifier(this);

        linearLayoutManager=new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(listAdapterWithRecycleView);
    }

   /** Called when the user selects a store */
    @Override
    public void onStoreSelected(int position) {

        Store store = stores.get(position);

    }


    @Override
    public void onStoreDeleted(int position) {

    }

}
