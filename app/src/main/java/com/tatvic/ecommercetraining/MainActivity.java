package com.tatvic.ecommercetraining;

import static androidx.constraintlayout.helper.widget.MotionEffect.TAG;
import static com.tatvic.ecommercetraining.ProductListing.itemsInCartList;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.denzcoskun.imageslider.ImageSlider;
import com.denzcoskun.imageslider.constants.ScaleTypes;
import com.denzcoskun.imageslider.interfaces.ItemClickListener;
import com.denzcoskun.imageslider.models.SlideModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.tatvic.ecommercetraining.adapters.HomeCategoryListAdapter;
import com.tatvic.ecommercetraining.model.CategoryModel;
import com.tatvic.ecommercetraining.model.ProductModel;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.os.Parcelable;
import android.preference.PreferenceManager;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.StringWriter;
import java.io.Writer;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MainActivity extends AppCompatActivity implements HomeCategoryListAdapter.RestaurantListClickListener {

    private static final String screen_name = "Home Screen";
    private static final String STORE_FILE_NAME = "CartItem";
    private static final String PRODUCT_TAG = "CartList";
    private RecyclerView recyclerView;
    Bundle promoParams;
    private ImageView ac_banner_list_promo;
    private ImageSlider imageSlider;
    List<ProductModel> productFromShared;
    CategoryModel categoryModel;
    private CardView cardView;
    private FirebaseAnalytics mFirebaseAnalytics;
    static
    {
        itemsInCartList = new ArrayList<>();
    }

    @SuppressLint({"MissingInflatedId", "MissingPermission"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        categoryModel = getIntent().getParcelableExtra("RestaurantModel");
        mFirebaseAnalytics = FirebaseAnalytics.getInstance(this);
        recyclerView = findViewById(R.id.recycler_view);
        imageSlider = findViewById(R.id.image_slider);
        cardView = findViewById(R.id.card_promotion);
        ac_banner_list_promo = findViewById(R.id.ac_banner_list_promo);

        List<CategoryModel> categoryModelList = getRestaurantData();
        initRecyclerView(categoryModelList);

        Analytics();
        cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, ProductListing.class);
                intent.putExtra("RestaurantModel", categoryModelList.get(4));
                startActivity(intent);
                // Promotion selected
                mFirebaseAnalytics.logEvent(FirebaseAnalytics.Event.SELECT_PROMOTION, promoParams);
            }
        });


        ArrayList<SlideModel> arrayList = new ArrayList<>();
        arrayList.add(new SlideModel(R.drawable.promotion, ScaleTypes.CENTER_INSIDE));
        arrayList.add(new SlideModel(R.drawable.promo2, ScaleTypes.CENTER_INSIDE));
        arrayList.add(new SlideModel(R.drawable.promo3, ScaleTypes.CENTER_INSIDE));
        arrayList.add(new SlideModel(R.drawable.banner5, ScaleTypes.CENTER_INSIDE));
        imageSlider.setImageList(arrayList);

        imageSlider.setItemClickListener(new ItemClickListener() {
            @Override
            public void onItemSelected(int i) {
                Intent intent = new Intent(MainActivity.this, ProductListing.class);
                intent.putExtra("RestaurantModel", categoryModelList.get(i));
                startActivity(intent);

            }
        });
    }

    private void Analytics() {
        String platformName = Build.VERSION.RELEASE;
        String manufacturer = Build.MANUFACTURER;
        String model = Build.MODEL;
        String deviceName = manufacturer + " " + model;

        promoParams = new Bundle();
        promoParams.putString(FirebaseAnalytics.Param.PROMOTION_ID, "MEGA_SALE_50%");
        promoParams.putString(FirebaseAnalytics.Param.PROMOTION_NAME, "Mega Sale Up to 50% OFF");
        promoParams.putString(FirebaseAnalytics.Param.CREATIVE_NAME, "banner2.jpg");
        promoParams.putString(FirebaseAnalytics.Param.CREATIVE_SLOT, "featured_promo_1");
        promoParams.putString(FirebaseAnalytics.Param.LOCATION_ID, "AIR_CONDITIONER_LIST_BANNER");

        // Promotion displayed
        mFirebaseAnalytics.logEvent(FirebaseAnalytics.Event.VIEW_PROMOTION, promoParams);


        FirebaseAnalytics.getInstance(this).getAppInstanceId().addOnCompleteListener(new OnCompleteListener<String>() {
            @Override
            public void onComplete(@NonNull Task<String> task) {
                if (task.isSuccessful()) {
                    String app_instance_id = task.getResult();
                    mFirebaseAnalytics.setUserProperty("app_instance_id", app_instance_id);
                    mFirebaseAnalytics.setUserProperty("os_version", "Android "+ platformName);
                    mFirebaseAnalytics.setUserProperty("device_name", deviceName);
                }
            }
        });
    }


    private void initRecyclerView(List<CategoryModel> categoryModelList) {
        RecyclerView recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 2));
        HomeCategoryListAdapter adapter = new HomeCategoryListAdapter(categoryModelList, this);
        recyclerView.setAdapter(adapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.home_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        String itemName = (String) item.getTitle();

        switch (item.getItemId()) {
            case R.id.search:
                Bundle custom_event = new Bundle();
                custom_event.putString("menu_name", itemName);
                custom_event.putString("screen_name", screen_name);
                mFirebaseAnalytics.logEvent("top_action_bar_click", custom_event);
                startActivity(new Intent(MainActivity.this, Search.class));
                return (true);
            case R.id.cart:
                Snackbar.make(findViewById(android.R.id.content), "Please add some items in cart", Snackbar.LENGTH_LONG)
                        .setAction("OK", new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                            }
                        }).show();
                Bundle custom_event1 = new Bundle();
                custom_event1.putString("menu_name", itemName);
                custom_event1.putString("screen_name", screen_name);
                mFirebaseAnalytics.logEvent("top_action_bar_click", custom_event1);
                return (true);
        }
        return (super.onOptionsItemSelected(item));
    }


    private List<CategoryModel> getRestaurantData() {
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
        CategoryModel[] categoryModels = gson.fromJson(jsonStr, CategoryModel[].class);
        List<CategoryModel> restList = Arrays.asList(categoryModels);

        return restList;

    }

    @Override
    public void onItemClick(CategoryModel categoryModel) {
        Intent intent = new Intent(MainActivity.this, ProductListing.class);
        intent.putExtra("RestaurantModel", categoryModel);
        startActivity(intent);

    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
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