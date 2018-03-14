package com.example.julieannmoore.retailprofitcalculator;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.List;

public class ModifyStoreActivity extends Activity {

    AppUtility appUtility;

    ListAdapterWithRecycleView listAdapterWithRecycleView;

    private EditText editTextStoreName, editTextStoreNumber;
    private Button buttonModify;

    List<Store> stores;
    int modificationIndex=-1;

    String storeName, storeNumber;

    LinearLayoutManager linearLayoutManager;
    GridLayoutManager gridLayoutManager;
    StaggeredGridLayoutManager staggeredGridLayoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modify_store);

        appUtility=AppUtility.getAppUtility(getApplicationContext());

        stores = appUtility.getStores();

        initStoreInputForm();

    }

    private void initStoreInputForm(){
        editTextStoreName = findViewById(R.id.editTextStoreName);
        editTextStoreNumber = findViewById(R.id.editTextStoreNumber);

        buttonModify = findViewById(R.id.buttonAdd);
        buttonModify.setTag("Modify");


        buttonModify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                storeName = editTextStoreName.getText().toString();
                storeNumber = editTextStoreNumber.getText().toString();
                Store store = null;

                if(isInputDataValid()) {
                    store = new Store(storeName, storeNumber);
                    stores.add(store);
                    Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                    startActivity(intent);
                }else{
                    Toast.makeText(ModifyStoreActivity.this,"Input Invalid",Toast.LENGTH_LONG).show();
                }

            }
        });
    }

    private boolean isInputDataValid(){
        if(AppUtility.isStringEmpty(storeName) || AppUtility.isStringEmpty(storeNumber)){
            return false;
        }else{
            return true;
        }
    }

    private void clearInputForm() {
        editTextStoreName.setText("");
        editTextStoreNumber.setText("");
    }
}
