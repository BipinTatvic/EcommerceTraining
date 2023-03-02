package com.tatvic.ecommercetraining;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.MenuItemCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.inputmethod.EditorInfo;
//import android.widget.SearchView;
import androidx.appcompat.widget.SearchView;


import com.google.firebase.analytics.FirebaseAnalytics;
import com.google.gson.Gson;
import com.tatvic.ecommercetraining.adapters.SearchAdapter;
import com.tatvic.ecommercetraining.model.CategoryModel;
import com.tatvic.ecommercetraining.model.ProductModel;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.StringWriter;
import java.io.Writer;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Search extends AppCompatActivity {

    private static final String screen_name = "Search Screen";
    private SearchAdapter searchAdapter;
    private List<CategoryModel> menuList;
    private FirebaseAnalytics mFirebaseAnalytics;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        mFirebaseAnalytics = FirebaseAnalytics.getInstance(this);

//        CategoryModel categoryModel = getIntent().getParcelableExtra("RestaurantModel");
//        menuList = categoryModel.getMenus();
        menuList = getProductData();
        fillExampleList();
        initRecyclerView(menuList);

    }

    private void fillExampleList() {
    }

    private List<CategoryModel> getProductData() {
        InputStream is = getResources().openRawResource(R.raw.ecommerce_data);
        Writer writer = new StringWriter();
        char[] buffer = new char[1024];
        try {
            Reader reader = new BufferedReader(new InputStreamReader(is, "UTF-8"));
            int n;
            while ((n = reader.read(buffer)) != -1) {
                writer.write(buffer, 0, n);
            }
        } catch (Exception e) {

        }

        String jsonStr = writer.toString();
        Gson gson = new Gson();
        CategoryModel[] productModels = gson.fromJson(jsonStr, CategoryModel[].class);
        List<CategoryModel> restList = Arrays.asList(productModels);
        return restList;
    }


    private void initRecyclerView(List<CategoryModel> productList) {
        RecyclerView recyclerView = findViewById(R.id.search_recyclerView);
        recyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        searchAdapter = new SearchAdapter(this, productList);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(searchAdapter);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.search_menu, menu);
        SearchView searchItem = (SearchView) menu.findItem(R.id.pdp_search).getActionView();
//        SearchView searchView = (SearchView) searchItem.getActionView();
//        searchView.setImeOptions(EditorInfo.IME_ACTION_DONE);
        searchItem.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                searchAdapter.getFilter().filter(newText);
                return false;
            }
        });
        return true;
    }
    @Override
    protected void onResume() {
        super.onResume();
        Bundle screen_view = new Bundle();
        screen_view.putString(FirebaseAnalytics.Param.SCREEN_NAME, screen_name); //e.g. Screen Name
        screen_view.putString(FirebaseAnalytics.Param.SCREEN_CLASS, this.getLocalClassName()); // You can pass the value as specific activity name over here and if not then you can ignore this line and it will take the value automtically
        mFirebaseAnalytics.logEvent(FirebaseAnalytics.Event.SCREEN_VIEW, screen_view);
    }
}