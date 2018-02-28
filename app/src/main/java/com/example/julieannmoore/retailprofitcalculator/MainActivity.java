package com.example.julieannmoore.retailprofitcalculator;

import android.support.annotation.IdRes;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.List;

public class MainActivity extends AppCompatActivity implements ListAdapterWithRecycleView.StoreModifier {

    private RecyclerView recyclerView;
    AppUtility appUtility;

    ListAdapterWithRecycleView listAdapterWithRecycleView;

    private EditText editTextStoreName, editTextStoreNumber;
    private Button buttonAdd;

    List<Store> stores;
    int modificationIndex=-1;

    String storeName, storeNumber;

    LinearLayoutManager linearLayoutManager;
    GridLayoutManager gridLayoutManager;
    StaggeredGridLayoutManager staggeredGridLayoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        appUtility=AppUtility.getAppUtility(getApplicationContext());
        initStoreInputForm();
        recyclerView =(RecyclerView) findViewById(R.id.recycleListView);

        stores = appUtility.getStores();

        listAdapterWithRecycleView=new ListAdapterWithRecycleView(this,stores);
        listAdapterWithRecycleView.setStoreModifier(this);

        linearLayoutManager=new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false);
        gridLayoutManager = new GridLayoutManager(this,2,GridLayoutManager.VERTICAL,false);

        staggeredGridLayoutManager = new StaggeredGridLayoutManager(3,StaggeredGridLayoutManager.HORIZONTAL);

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(listAdapterWithRecycleView);
    }

    private void initStoreInputForm(){
        editTextStoreName = (EditText)findViewById(R.id.editTextStoreName);
        editTextStoreNumber = (EditText)findViewById(R.id.editTextStoreNumber);

        buttonAdd = (Button)findViewById(R.id.buttonAdd);
        buttonAdd.setTag("Add");


        buttonAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                storeName = editTextStoreName.getText().toString();
                storeNumber = editTextStoreNumber.getText().toString();
                Store store = null;

                if(isInputDataValid()) {
                    store = new Store(storeName, storeNumber);
                }else{
                    Toast.makeText(MainActivity.this,"Input Invalid",Toast.LENGTH_LONG).show();
                }

                String behaviour = (String)buttonAdd.getTag();
                if(behaviour.equalsIgnoreCase("Add")){
                    if(store!=null){
                        stores.add(store);
                        listAdapterWithRecycleView.notifyDataSetChanged();
                        recyclerView.scrollToPosition(stores.size()-1);
                        clearInputForm();
                    }
                }else if(behaviour.equalsIgnoreCase("modify")){
                    if(store!=null){
                        try{
                            stores.get(modificationIndex).setStoreName(store.getStoreName());
                            stores.get(modificationIndex).setStoreNumber(store.getStoreNumber());

                            listAdapterWithRecycleView.notifyItemChanged(modificationIndex);
                            clearInputForm();
                            buttonAdd.setTag("Add");
                            buttonAdd.setText("Add");
                        }catch (IndexOutOfBoundsException exception){
                            Toast.makeText(MainActivity.this,"Can't modify, item moved",Toast.LENGTH_LONG ).show();
                            listAdapterWithRecycleView.notifyDataSetChanged();
                            clearInputForm();
                            buttonAdd.setTag("Add");
                            buttonAdd.setText("Add");
                        }
                    }
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

    @Override
    public void onStoreSelected(int position) {
        modificationIndex = position;
        Store store = stores.get(position);
        buttonAdd.setTag("Modify");
        buttonAdd.setText("Modify");

        editTextStoreName.setText(store.getStoreName());
        editTextStoreNumber.setText(store.getStoreNumber());
    }

    @Override
    public void onStoreDeleted(int position) {
        buttonAdd.setTag("Add");
        buttonAdd.setText("Add");
        clearInputForm();
    }

}
