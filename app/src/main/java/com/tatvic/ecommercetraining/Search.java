package com.tatvic.ecommercetraining;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.MenuItemCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
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

public class Search extends AppCompatActivity implements SearchAdapter.RestaurantListClickListener {

    private static final String screen_name = "Search Screen";
    private SearchAdapter searchAdapter;
    private List<CategoryModel> menuList;
    private FirebaseAnalytics mFirebaseAnalytics;
    Bundle iphone_bundle, tv_bundle, audio_bundle, fridge_bundle, ac_bundle;

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
        analytics();
    }

    private void analytics() {
        iphone_bundle = new Bundle();
        iphone_bundle.putString(FirebaseAnalytics.Param.PROMOTION_ID, "IPHONE_CATEGORY");
        iphone_bundle.putString(FirebaseAnalytics.Param.PROMOTION_NAME, "Mobiles");
        iphone_bundle.putString(FirebaseAnalytics.Param.CREATIVE_NAME, "Phone_qhq1wa.png");
        iphone_bundle.putString(FirebaseAnalytics.Param.CREATIVE_SLOT, "category_1");
        iphone_bundle.putString(FirebaseAnalytics.Param.LOCATION_ID, "IPHONE_PROMO");

        // Promotion displayed
        mFirebaseAnalytics.logEvent(FirebaseAnalytics.Event.VIEW_PROMOTION, iphone_bundle);

        tv_bundle = new Bundle();
        tv_bundle.putString(FirebaseAnalytics.Param.PROMOTION_ID, "TV_CATEGORY");
        tv_bundle.putString(FirebaseAnalytics.Param.PROMOTION_NAME, "Televisions");
        tv_bundle.putString(FirebaseAnalytics.Param.CREATIVE_NAME, "TV_by2xka.png");
        tv_bundle.putString(FirebaseAnalytics.Param.CREATIVE_SLOT, "category_2");
        tv_bundle.putString(FirebaseAnalytics.Param.LOCATION_ID, "TV_PROMO");

        // Promotion displayed
        mFirebaseAnalytics.logEvent(FirebaseAnalytics.Event.VIEW_PROMOTION, tv_bundle);

        audio_bundle = new Bundle();
        audio_bundle.putString(FirebaseAnalytics.Param.PROMOTION_ID, "AUDIO_CATEGORY");
        audio_bundle.putString(FirebaseAnalytics.Param.PROMOTION_NAME, "Audios");
        audio_bundle.putString(FirebaseAnalytics.Param.CREATIVE_NAME, "Audio_rd8pkk.png");
        audio_bundle.putString(FirebaseAnalytics.Param.CREATIVE_SLOT, "category_3");
        audio_bundle.putString(FirebaseAnalytics.Param.LOCATION_ID, "AUDIO_PROMO");

        // Promotion displayed
        mFirebaseAnalytics.logEvent(FirebaseAnalytics.Event.VIEW_PROMOTION, audio_bundle);

        fridge_bundle = new Bundle();
        fridge_bundle.putString(FirebaseAnalytics.Param.PROMOTION_ID, "FRIDGE_CATEGORY");
        fridge_bundle.putString(FirebaseAnalytics.Param.PROMOTION_NAME, "Refrigerators");
        fridge_bundle.putString(FirebaseAnalytics.Param.CREATIVE_NAME, "Ref_jmphdw.png");
        fridge_bundle.putString(FirebaseAnalytics.Param.CREATIVE_SLOT, "category_4");
        fridge_bundle.putString(FirebaseAnalytics.Param.LOCATION_ID, "FRIDGE_PROMO");

        // Promotion displayed
        mFirebaseAnalytics.logEvent(FirebaseAnalytics.Event.VIEW_PROMOTION, fridge_bundle);

        ac_bundle = new Bundle();
        ac_bundle.putString(FirebaseAnalytics.Param.PROMOTION_ID, "AC_CATEGORY");
        ac_bundle.putString(FirebaseAnalytics.Param.PROMOTION_NAME, "Air Conditioners");
        ac_bundle.putString(FirebaseAnalytics.Param.CREATIVE_NAME, "AC_gw4ktn.png");
        ac_bundle.putString(FirebaseAnalytics.Param.CREATIVE_SLOT, "category_5");
        ac_bundle.putString(FirebaseAnalytics.Param.LOCATION_ID, "AC_PROMO");

        // Promotion displayed
        mFirebaseAnalytics.logEvent(FirebaseAnalytics.Event.VIEW_PROMOTION, ac_bundle);
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
        searchAdapter = new SearchAdapter(productList,this);
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

        //--------GA3 Screen view---------
        Bundle bundle = new Bundle();
        bundle.putString(FirebaseAnalytics.Param.SCREEN_NAME, screen_name);// Pass the Screen name
        mFirebaseAnalytics.logEvent("screenview_manual", bundle);

    }

    @Override
    public void onItemClick(CategoryModel categoryModel) {
        switch (categoryModel.getName()){
            case "Mobiles":
                // Promotion selected
                mFirebaseAnalytics.logEvent(FirebaseAnalytics.Event.SELECT_PROMOTION, iphone_bundle);
                break;
            case "Televisions":
                // Promotion selected
                mFirebaseAnalytics.logEvent(FirebaseAnalytics.Event.SELECT_PROMOTION, tv_bundle);
                break;
            case "Audios":
                // Promotion selected
                mFirebaseAnalytics.logEvent(FirebaseAnalytics.Event.SELECT_PROMOTION, audio_bundle);
                break;
            case "Refrigerators":
                // Promotion selected
                mFirebaseAnalytics.logEvent(FirebaseAnalytics.Event.SELECT_PROMOTION, fridge_bundle);
                break;
            case "Air Conditioners":
                // Promotion selected
                mFirebaseAnalytics.logEvent(FirebaseAnalytics.Event.SELECT_PROMOTION, ac_bundle);
                break;
        }
        Intent intent = new Intent(Search.this, ProductListing.class);
        intent.putExtra("RestaurantModel", categoryModel);
        startActivity(intent);
    }
}