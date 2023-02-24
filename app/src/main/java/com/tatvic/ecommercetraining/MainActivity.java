package com.tatvic.ecommercetraining;

import static androidx.constraintlayout.helper.widget.MotionEffect.TAG;
import static com.tatvic.ecommercetraining.ProductListing.itemsInCartList;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.denzcoskun.imageslider.ImageSlider;
import com.denzcoskun.imageslider.constants.ScaleTypes;
import com.denzcoskun.imageslider.interfaces.ItemClickListener;
import com.denzcoskun.imageslider.models.SlideModel;
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
import android.os.Bundle;
import android.os.Parcelable;
import android.preference.PreferenceManager;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
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

    private static final String STORE_FILE_NAME = "CartItem";
    private static final String PRODUCT_TAG = "CartList";
    private RecyclerView recyclerView;
    private ImageSlider imageSlider;
    List<ProductModel> productFromShared;
    CategoryModel categoryModel;
    private CardView cardView;
    static
    {
        itemsInCartList = new ArrayList<>();
    }

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        categoryModel = getIntent().getParcelableExtra("RestaurantModel");

        recyclerView = findViewById(R.id.recycler_view);
        imageSlider = findViewById(R.id.image_slider);
        cardView = findViewById(R.id.card_promotion);

        List<CategoryModel> categoryModelList = getRestaurantData();
        initRecyclerView(categoryModelList);

        cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, ProductListing.class);
                intent.putExtra("RestaurantModel", categoryModelList.get(4));
                startActivity(intent);
            }
        });


        ArrayList<SlideModel> arrayList = new ArrayList<>();
        arrayList.add(new SlideModel(R.drawable.promotion, ScaleTypes.CENTER_INSIDE));
        arrayList.add(new SlideModel(R.drawable.promo2, ScaleTypes.CENTER_INSIDE));
        arrayList.add(new SlideModel(R.drawable.promo3, ScaleTypes.CENTER_INSIDE));
        arrayList.add(new SlideModel(R.drawable.banner2, ScaleTypes.CENTER_INSIDE));
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
        switch (item.getItemId()) {
            case R.id.search:
                startActivity(new Intent(MainActivity.this, Search.class));
                return (true);
            case R.id.cart:
                Intent i = new Intent(MainActivity.this, Cart.class);
                startActivity(i);
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

}