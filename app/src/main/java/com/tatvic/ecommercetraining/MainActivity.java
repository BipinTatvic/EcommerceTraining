package com.tatvic.ecommercetraining;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.denzcoskun.imageslider.ImageSlider;
import com.denzcoskun.imageslider.constants.ScaleTypes;
import com.denzcoskun.imageslider.interfaces.ItemClickListener;
import com.denzcoskun.imageslider.models.SlideModel;
import com.google.gson.Gson;
import com.tatvic.ecommercetraining.adapters.MenuListAdapter;
import com.tatvic.ecommercetraining.adapters.ProductAdapter;
import com.tatvic.ecommercetraining.adapters.RestaurantListAdapter;
import com.tatvic.ecommercetraining.model.ItemModel;
import com.tatvic.ecommercetraining.model.Product;
import com.tatvic.ecommercetraining.model.RestaurantModel;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.StringWriter;
import java.io.Writer;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MainActivity extends AppCompatActivity implements RestaurantListAdapter.RestaurantListClickListener{

    private RecyclerView recyclerView;
    private ArrayList<ItemModel> item_list;
    private ImageSlider imageSlider;
    private CardView cardView;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = findViewById(R.id.recycler_view);
        imageSlider = findViewById(R.id.image_slider);
        cardView = findViewById(R.id.card_promotion);

        List<RestaurantModel> restaurantModelList =  getRestaurantData();
        initRecyclerView(restaurantModelList);

        cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, ProductListing.class);
                startActivity(intent);
            }
        });

//        item_list = new ArrayList<>();
//        item_list.add(new ItemModel("Mobile", "$ 299", R.drawable.iphone));
//        item_list.add(new ItemModel("TV", "$ 1099", R.drawable.tv));
//        item_list.add(new ItemModel("AC", "$ 539", R.drawable.ac));
//        item_list.add(new ItemModel("Fridge", "$ 4089", R.drawable.fridge));
//        item_list.add(new ItemModel("Washing Machine", "$ 2249", R.drawable.kindpng_2085518));

//        ProductAdapter adapter = new ProductAdapter(this, item_list);
//        grid_view.setAdapter(adapter);
//
//        grid_view.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
//                startActivity(new Intent(MainActivity.this, ProductDetail.class));
//            }
//        });

        ArrayList<SlideModel> arrayList = new ArrayList<>();
        arrayList.add(new SlideModel(R.drawable.promotion, ScaleTypes.CENTER_INSIDE));
        arrayList.add(new SlideModel(R.drawable.promo2, ScaleTypes.CENTER_INSIDE));
        arrayList.add(new SlideModel(R.drawable.promo3, ScaleTypes.CENTER_INSIDE));
        imageSlider.setImageList(arrayList);

        imageSlider.setItemClickListener(new ItemClickListener() {
            @Override
            public void onItemSelected(int i) {
                startActivity(new Intent(MainActivity.this, ProductListing.class));
            }
        });
    }

    private void initRecyclerView(List<RestaurantModel> restaurantModelList ) {
        RecyclerView recyclerView =  findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 2));
        RestaurantListAdapter adapter = new RestaurantListAdapter(restaurantModelList, this);
        recyclerView.setAdapter(adapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.search:
                startActivity(new Intent(MainActivity.this, Search.class));
                return (true);
            case R.id.cart:
                startActivity(new Intent(MainActivity.this, Cart.class));
                return (true);
            case R.id.profile:
                Toast.makeText(this, "Profile selected", Toast.LENGTH_SHORT).show();
                return (true);
            case R.id.about_us:
                Toast.makeText(this, "Contact Us selected", Toast.LENGTH_SHORT).show();
                return (true);
        }
        return (super.onOptionsItemSelected(item));
    }


    private List<RestaurantModel> getRestaurantData() {
        InputStream is = getResources().openRawResource(R.raw.restaurent);
        Writer writer = new StringWriter();
        char[] buffer = new char[1024];
        try{
            Reader reader = new BufferedReader(new InputStreamReader(is, "UTF-8"));
            int n;
            while(( n = reader.read(buffer)) != -1) {
                writer.write(buffer, 0,n);
            }
        }catch (Exception e) {

        }

        String jsonStr = writer.toString();
        Gson gson = new Gson();
        RestaurantModel[] restaurantModels =  gson.fromJson(jsonStr, RestaurantModel[].class);
        List<RestaurantModel> restList = Arrays.asList(restaurantModels);

        return  restList;

    }

    @Override
    public void onItemClick(RestaurantModel restaurantModel) {
        Intent intent = new Intent(MainActivity.this, ProductListing.class);
        intent.putExtra("RestaurantModel", restaurantModel);
        startActivity(intent);

    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        setResult(Activity.RESULT_CANCELED);
        finish();
    }

}