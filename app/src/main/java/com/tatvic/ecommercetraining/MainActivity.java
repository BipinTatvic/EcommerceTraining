package com.tatvic.ecommercetraining;

import static com.tatvic.ecommercetraining.ProductListing.itemsInCartList;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.denzcoskun.imageslider.ImageSlider;
import com.denzcoskun.imageslider.constants.ScaleTypes;
import com.denzcoskun.imageslider.interfaces.ItemChangeListener;
import com.denzcoskun.imageslider.interfaces.ItemClickListener;
import com.denzcoskun.imageslider.models.SlideModel;
import com.google.android.material.snackbar.Snackbar;
import com.google.gson.Gson;
import com.tatvic.ecommercetraining.adapters.HomeCategoryListAdapter;
import com.tatvic.ecommercetraining.model.CategoryModel;
import com.tatvic.ecommercetraining.model.ProductModel;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.os.Parcelable;
import android.preference.PreferenceManager;
import android.util.Log;
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
    private RecyclerView recyclerView;
    Bundle promoParams, promo0, promo1, promo2, promo3, categories_bundle,
            iphone_bundle, tv_bundle, audio_bundle, fridge_bundle, ac_bundle;
    ArrayList<SlideModel> arrayList;
    private ImageSlider imageSlider;
    static List<CategoryModel> categoryModelList;
    CategoryModel categoryModel;
    private CardView cardView;

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
        recyclerView = findViewById(R.id.recycler_view);
        imageSlider = findViewById(R.id.image_slider);
        cardView = findViewById(R.id.card_promotion);

        categoryModelList = getRestaurantData();
        initRecyclerView(categoryModelList);


        cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Promotion selected

                Intent intent = new Intent(MainActivity.this, ProductListing.class);
                intent.putExtra("RestaurantModel", categoryModelList.get(4));
                startActivity(intent);
            }
        });


        arrayList = new ArrayList<>();
        arrayList.add(new SlideModel(R.drawable.promotion, ScaleTypes.CENTER_INSIDE));
        arrayList.add(new SlideModel(R.drawable.banner, ScaleTypes.CENTER_INSIDE));
        arrayList.add(new SlideModel(R.drawable.promo3, ScaleTypes.CENTER_INSIDE));
        arrayList.add(new SlideModel(R.drawable.banner5, ScaleTypes.CENTER_INSIDE));
        imageSlider.startSliding(3000);
        imageSlider.setImageList(arrayList);


        imageSlider.setItemClickListener(new ItemClickListener() {
            @Override
            public void onItemSelected(int i) {
                switch (i){
                    case 0:
                        // Promotion selected
                        break;
                    case 1:
                        // Promotion selected
                        break;
                    case 2:
                        // Promotion selected
                        break;
                    case 3:
                        // Promotion selected
                        break;
                }

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

                startActivity(new Intent(MainActivity.this, Search.class));
                return (true);
            case R.id.cart:
              /*  Snackbar.make(findViewById(android.R.id.content), "Please add some items in cart", Snackbar.LENGTH_LONG)
                        .setAction("OK", new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                            }
                        }).show();*/

                Intent i1 = new Intent(this,Cart.class);
                startActivity(i1);

                return (true);
        }
        return (super.onOptionsItemSelected(item));
    }


    public List<CategoryModel> getRestaurantData() {
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
        switch (categoryModel.getName()){
            case "Mobiles":
                // Promotion selected
                break;
            case "Televisions":
                // Promotion selected
                break;
            case "Audios":
                // Promotion selected
                break;
            case "Refrigerators":
                // Promotion selected
                break;
            case "Air Conditioners":
                // Promotion selected
                break;
        }
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

        imageSlider.startSliding(3000);


        imageSlider.setItemChangeListener(new ItemChangeListener() {
            @Override
            public void onItemChanged(int i) {
                switch (i){
                    case 0:
                        // Promotion displayed
                        break;
                    case 1:
                        // Promotion displayed
                        break;
                    case 2:
                        // Promotion displayed
                        break;
                    case 3:
                        // Promotion displayed
                        break;
                }
            }
        });
    }

    @Override
    protected void onPause() {
        super.onPause();

        imageSlider.stopSliding();

    }
}