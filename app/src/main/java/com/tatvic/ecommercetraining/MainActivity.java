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
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.analytics.FirebaseAnalytics;
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

        categoryModelList = getRestaurantData();
        initRecyclerView(categoryModelList);

        Analytics();
        cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Promotion selected
                mFirebaseAnalytics.logEvent(FirebaseAnalytics.Event.SELECT_PROMOTION, promoParams);

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
                        mFirebaseAnalytics.logEvent(FirebaseAnalytics.Event.SELECT_PROMOTION, promo0);
                        break;
                    case 1:
                        // Promotion selected
//                            Missing select_promotion for 2nd banner
//                        mFirebaseAnalytics.logEvent(FirebaseAnalytics.Event.SELECT_PROMOTION, promo1);
                        break;
                    case 2:
                        // Promotion selected
//                Missing select_promotion for 3rd banner
//                        mFirebaseAnalytics.logEvent(FirebaseAnalytics.Event.SELECT_PROMOTION, promo2);
                        break;
                    case 3:
                        // Promotion selected
                        mFirebaseAnalytics.logEvent(FirebaseAnalytics.Event.SELECT_PROMOTION, promo3);
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

        promoParams = new Bundle();
        promoParams.putString(FirebaseAnalytics.Param.PROMOTION_ID, "MEGA_SALE_50%");
        promoParams.putString(FirebaseAnalytics.Param.PROMOTION_NAME, "Mega Sale Up to 50% OFF");
//        Missing Creative Name and Creative Slot from main banner
//        promoParams.putString(FirebaseAnalytics.Param.CREATIVE_NAME, "banner2.jpg");
//        promoParams.putString(FirebaseAnalytics.Param.CREATIVE_SLOT, "featured_promo_1");
        promoParams.putString(FirebaseAnalytics.Param.LOCATION_ID, "AIR_CONDITIONER_LIST_BANNER");

        // Promotion displayed
        mFirebaseAnalytics.logEvent(FirebaseAnalytics.Event.VIEW_PROMOTION, promoParams);

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
//        Missing promotion name
//        audio_bundle.putString(FirebaseAnalytics.Param.PROMOTION_NAME, "Audios");
        audio_bundle.putString(FirebaseAnalytics.Param.CREATIVE_NAME, "Audio_rd8pkk.png");
        audio_bundle.putString(FirebaseAnalytics.Param.CREATIVE_SLOT, "category_3");
        audio_bundle.putString(FirebaseAnalytics.Param.LOCATION_ID, "AUDIO_PROMO");

        // Promotion displayed
        mFirebaseAnalytics.logEvent(FirebaseAnalytics.Event.VIEW_PROMOTION, audio_bundle);

        fridge_bundle = new Bundle();
        fridge_bundle.putString(FirebaseAnalytics.Param.PROMOTION_ID, "FRIDGE_CATEGORY");
        //        Missing promotion name
//        fridge_bundle.putString(FirebaseAnalytics.Param.PROMOTION_NAME, "Refrigerators");
        fridge_bundle.putString(FirebaseAnalytics.Param.CREATIVE_NAME, "Ref_jmphdw.png");
        fridge_bundle.putString(FirebaseAnalytics.Param.CREATIVE_SLOT, "category_4");
        fridge_bundle.putString(FirebaseAnalytics.Param.LOCATION_ID, "FRIDGE_PROMO");

        // Promotion displayed
        mFirebaseAnalytics.logEvent(FirebaseAnalytics.Event.VIEW_PROMOTION, fridge_bundle);

        ac_bundle = new Bundle();
        ac_bundle.putString(FirebaseAnalytics.Param.PROMOTION_ID, "AC_CATEGORY");
//        Missing promotion name
//        ac_bundle.putString(FirebaseAnalytics.Param.PROMOTION_NAME, "Air Conditioners");
        ac_bundle.putString(FirebaseAnalytics.Param.CREATIVE_NAME, "AC_gw4ktn.png");
        ac_bundle.putString(FirebaseAnalytics.Param.CREATIVE_SLOT, "category_5");
        ac_bundle.putString(FirebaseAnalytics.Param.LOCATION_ID, "AC_PROMO");

        // Promotion displayed
//        View Promotion for AC is not triggering
//        mFirebaseAnalytics.logEvent(FirebaseAnalytics.Event.VIEW_PROMOTION, ac_bundle);



        FirebaseAnalytics.getInstance(this).getAppInstanceId().addOnCompleteListener(new OnCompleteListener<String>() {
            @Override
            public void onComplete(@NonNull Task<String> task) {
                if (task.isSuccessful()) {
                    String app_instance_id = task.getResult();

//                    STATIC APP INSTANCE ID AND DEVICE NAME

//                    mFirebaseAnalytics.setUserProperty("app_instance_id", app_instance_id);
                    mFirebaseAnalytics.setUserProperty("app_instance_id", "502e6200-b115-11ed-9b25-6b88fdb7e8da");
                    mFirebaseAnalytics.setUserProperty("os_version", "Android "+ platformName);
//                    mFirebaseAnalytics.setUserProperty("device_name", deviceName);
                    mFirebaseAnalytics.setUserProperty("device_name", "Mobile");
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
              /*  Snackbar.make(findViewById(android.R.id.content), "Please add some items in cart", Snackbar.LENGTH_LONG)
                        .setAction("OK", new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                            }
                        }).show();*/

                Intent i1 = new Intent(this,Cart.class);
                startActivity(i1);

                Bundle custom_event1 = new Bundle();
                custom_event1.putString("menu_name", itemName);
                custom_event1.putString("screen_name", screen_name);
                mFirebaseAnalytics.logEvent("top_action_bar_click", custom_event1);
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
                mFirebaseAnalytics.logEvent(FirebaseAnalytics.Event.SELECT_PROMOTION, iphone_bundle);
                break;
            case "Televisions":
                // Promotion selected
//                mFirebaseAnalytics.logEvent(FirebaseAnalytics.Event.SELECT_PROMOTION, tv_bundle);
                mFirebaseAnalytics.logEvent(FirebaseAnalytics.Event.SELECT_PROMOTION, iphone_bundle);
                break;
            case "Audios":
                // Promotion selected
//                Select Promototion for Auido is not working
//                mFirebaseAnalytics.logEvent(FirebaseAnalytics.Event.SELECT_PROMOTION, audio_bundle);
                break;
            case "Refrigerators":
                // Promotion selected
                mFirebaseAnalytics.logEvent(FirebaseAnalytics.Event.SELECT_PROMOTION, fridge_bundle);
                break;
            case "Air Conditioners":
                // Promotion selected
//                Select Promotion for AC is triggering twice
                mFirebaseAnalytics.logEvent(FirebaseAnalytics.Event.SELECT_PROMOTION, ac_bundle);
                mFirebaseAnalytics.logEvent(FirebaseAnalytics.Event.SELECT_PROMOTION, ac_bundle);
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

        Bundle screen_view = new Bundle();
        screen_view.putString(FirebaseAnalytics.Param.SCREEN_NAME, screen_name); //e.g. Screen Name
        screen_view.putString(FirebaseAnalytics.Param.SCREEN_CLASS, this.getLocalClassName()); // You can pass the value as specific activity name over here and if not then you can ignore this line and it will take the value automtically
        mFirebaseAnalytics.logEvent(FirebaseAnalytics.Event.SCREEN_VIEW, screen_view);

        //--------GA3 Screen view---------
        Bundle bundle = new Bundle();
        bundle.putString(FirebaseAnalytics.Param.SCREEN_NAME, screen_name);// Pass the Screen name
        mFirebaseAnalytics.logEvent("screenview_manual", bundle);

        imageSlider.setItemChangeListener(new ItemChangeListener() {
            @Override
            public void onItemChanged(int i) {
                switch (i){
                    case 0:
                        promo0 = new Bundle();
                        promo0.putString(FirebaseAnalytics.Param.PROMOTION_ID, "BEST_OFFER");
                        promo0.putString(FirebaseAnalytics.Param.PROMOTION_NAME, "Big Sale This Weekend 50% OFF");
                        promo0.putString(FirebaseAnalytics.Param.CREATIVE_NAME, "promotion.png");
                        promo0.putString(FirebaseAnalytics.Param.CREATIVE_SLOT, "featured_promo_2");
                        promo0.putString(FirebaseAnalytics.Param.LOCATION_ID, "IPHONE_LIST_BANNER");

                        // Promotion displayed
                        mFirebaseAnalytics.logEvent(FirebaseAnalytics.Event.VIEW_PROMOTION, promo0);
                        break;
                    case 1:
                        promo1 = new Bundle();
                        promo1.putString(FirebaseAnalytics.Param.PROMOTION_ID, "MEGA_SALE");
                        promo1.putString(FirebaseAnalytics.Param.PROMOTION_NAME, "Mega Sale Up to 80% OFF");
//                        Missing Creative name and ID in view_promotion of 2nd banner
//                        promo1.putString(FirebaseAnalytics.Param.CREATIVE_NAME, "banner.jpeg");
//                        promo1.putString(FirebaseAnalytics.Param.CREATIVE_SLOT, "featured_promo_3");
                        promo1.putString(FirebaseAnalytics.Param.LOCATION_ID, "TELEVISION_LIST_BANNER");

                        // Promotion displayed
                        mFirebaseAnalytics.logEvent(FirebaseAnalytics.Event.VIEW_PROMOTION, promo1);
                        break;
                    case 2:
                        promo2 = new Bundle();
                        promo2.putString(FirebaseAnalytics.Param.PROMOTION_ID, "WEEKEND_SALE");
                        promo2.putString(FirebaseAnalytics.Param.PROMOTION_NAME, "Weekend Sale Up to 70% OFF");
                        promo2.putString(FirebaseAnalytics.Param.CREATIVE_NAME, "promo3.jpg");
                        promo2.putString(FirebaseAnalytics.Param.CREATIVE_SLOT, "featured_promo_4");
                        promo2.putString(FirebaseAnalytics.Param.LOCATION_ID, "AUDIO_LIST_BANNER");

                        // Promotion displayed
//                        Missing view promotion for 3rd banner
//                        mFirebaseAnalytics.logEvent(FirebaseAnalytics.Event.VIEW_PROMOTION, promo2);
                        break;
                    case 3:
                        promo3 = new Bundle();
//                        Missing Promotion name and ID in view_promotion of 4th banner
//                        promo3.putString(FirebaseAnalytics.Param.PROMOTION_ID, "SPECIAL_OFFER");
//                        promo3.putString(FirebaseAnalytics.Param.PROMOTION_NAME, "This Weekend Special Offer");
                        promo3.putString(FirebaseAnalytics.Param.CREATIVE_NAME, "banner5.jpg");
                        promo3.putString(FirebaseAnalytics.Param.CREATIVE_SLOT, "featured_promo_5");
                        promo3.putString(FirebaseAnalytics.Param.LOCATION_ID, "REFRIGERATOR_LIST_BANNER");

                        // Promotion displayed
//                        View Promotion of 3rd banner is triggering on viewing 4th banner
                        mFirebaseAnalytics.logEvent(FirebaseAnalytics.Event.VIEW_PROMOTION, promo3);
                        mFirebaseAnalytics.logEvent(FirebaseAnalytics.Event.VIEW_PROMOTION, promo2);
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