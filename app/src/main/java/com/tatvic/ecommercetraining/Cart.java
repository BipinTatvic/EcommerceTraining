package com.tatvic.ecommercetraining;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.analytics.FirebaseAnalytics;
import com.google.gson.Gson;
import com.tatvic.ecommercetraining.adapters.CartListAdapter;
import com.tatvic.ecommercetraining.model.ProductModel;
import com.tatvic.ecommercetraining.model.CategoryModel;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

public class Cart extends AppCompatActivity {
    private static final String screen_name = "Cart Screen";
    private RecyclerView recyclerView_cart;
    private CartListAdapter cartListAdapter;
    private TextView tvSubtotalAmount, tvDeliveryChargeAmount, tvTotalAmount, tvTotalItems;
    HashMap<String, ProductModel> hashMap = new HashMap<String, ProductModel>();
    private Button begin_checkout;
    float subTotalAmount = 0f;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor myEdit;
    CategoryModel categoryModel;
    Bundle item_bundle;
    private FirebaseAnalytics mFirebaseAnalytics;
    private ProductModel productModel;
    private List<ProductModel> plist;
    private List<Bundle> arrayBundle = new ArrayList<Bundle>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);

        mFirebaseAnalytics = FirebaseAnalytics.getInstance(this);
        plist = new ArrayList<ProductModel>();
        sharedPreferences = getSharedPreferences("MySharedPref", MODE_PRIVATE);
        myEdit = sharedPreferences.edit();
        categoryModel = new CategoryModel();
        Bundle viewCartParams = new Bundle();

        Gson gson = new Gson();
        String json = sharedPreferences.getString("YOUR_MODEL", "");
//        CategoryModel cm= gson.fromJson(json, CategoryModel.class);
//
//        name = cm.getName();
////        price = cm.get
////        totalInCart = cm.get
//        url = cm.getImage();

      /*  ProductModel p = new ProductModel(name,0,1,url);
        plist.add(p);

        categoryModel.setMenus(plist);*/


        //Log.d("HEYFLDJF", "onCreate: "+cm);

//        categoryModel = getIntent().getParcelableExtra("RestaurantModel");
        recyclerView_cart = findViewById(R.id.cartItemsRecyclerView);
        begin_checkout = findViewById(R.id.begin_checkout);
        tvSubtotalAmount = findViewById(R.id.tvSubtotalAmount);
        tvDeliveryChargeAmount = findViewById(R.id.tvDeliveryChargeAmount);
        tvTotalAmount = findViewById(R.id.tvTotalAmount);
        tvTotalItems = findViewById(R.id.tvTotalItems);
//        item_list = new ArrayList<>();
//        item_list.add(new ItemModel("Mobile", "$ 299", R.drawable.iphone));
//        item_list.add(new ItemModel("TV", "$ 1099", R.drawable.tv));
//        item_list.add(new ItemModel("AC", "$ 539", R.drawable.ac));
//        item_list.add(new ItemModel("Fridge", "$ 4089", R.drawable.fridge));
//        item_list.add(new ItemModel("Washing Machine", "$ 2249", R.drawable.kindpng_2085518));


//        cartAdapter = new CartAdapter(this, item_list);
//        recyclerView_cart.setHasFixedSize(true);
//        recyclerView_cart.setLayoutManager(new LinearLayoutManager(getApplicationContext(),
//                LinearLayoutManager.VERTICAL, false));
//        recyclerView_cart.setAdapter(cartAdapter);


//        if (categoryModel == null ) {
//            ProductModel p = new ProductModel(name,0,1,url);
//            plist.add(p);
//
//            categoryModel.setMenus(plist);
//        } else {
//           /* ProductModel p = new ProductModel(name,0,1,url);
//            plist.add(p);
//
//            categoryModel.setMenus(plist);*/
//


        categoryModel.setMenus(ProductListing.itemsInCartList);

        initRecyclerView(categoryModel);
        calculateTotalAmount(categoryModel);
//
//        }

        arrayBundle.clear();
        //Bundle itemJeggingsCart = new Bundle(item_bundle);
        for (int i = 0; i < ProductListing.itemsInCartList.size(); i++) {
            plist.add(ProductListing.itemsInCartList.get(i));
            Bundle item_bundle = new Bundle();

            item_bundle.putString(FirebaseAnalytics.Param.ITEM_ID, plist.get(i).getItem_id());
            item_bundle.putString(FirebaseAnalytics.Param.ITEM_NAME, plist.get(i).getName());
            item_bundle.putString(FirebaseAnalytics.Param.ITEM_CATEGORY, plist.get(i).getItem_category());
            item_bundle.putString(FirebaseAnalytics.Param.ITEM_VARIANT, plist.get(i).getVariant());
            item_bundle.putString(FirebaseAnalytics.Param.ITEM_BRAND, plist.get(i).getBrand());
            item_bundle.putDouble(FirebaseAnalytics.Param.PRICE, plist.get(i).getPrice());

            arrayBundle.add(item_bundle);
            viewCartParams.putLong(FirebaseAnalytics.Param.QUANTITY, plist.get(i).getTotalInCart());

        }
        viewCartParams.putString(FirebaseAnalytics.Param.CURRENCY, "INR");
        viewCartParams.putDouble(FirebaseAnalytics.Param.VALUE, subTotalAmount);
        viewCartParams.putParcelableArrayList(FirebaseAnalytics.Param.ITEMS,
                (ArrayList<? extends Parcelable>) arrayBundle);

        if(ProductListing.itemsInCartList.size() == 0){

        }else {
            mFirebaseAnalytics.logEvent(FirebaseAnalytics.Event.VIEW_CART, viewCartParams);
        }

        begin_checkout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Cart.this, ShippingAddress.class);
                intent.putExtra("RestaurantModel", categoryModel);

//                Gson gson = new Gson();
//                String json = gson.toJson(categoryModel);
//
//                Log.d("okjhbvcfgh", "onClick: " + json);
//
//                myEdit.putString("YOUR_MODEL", json);
//                myEdit.apply();
                intent.putExtra("Total", subTotalAmount);
                intent.putExtra("from_cart", "yes");
                startActivityForResult(intent, 1000);

                Bundle beginCheckoutParams = new Bundle();

                beginCheckoutParams.putString(FirebaseAnalytics.Param.CURRENCY, "INR");
                beginCheckoutParams.putDouble(FirebaseAnalytics.Param.VALUE, subTotalAmount);
                beginCheckoutParams.putString(FirebaseAnalytics.Param.COUPON, "SUMMER_FUN");

                beginCheckoutParams.putParcelableArrayList(FirebaseAnalytics.Param.ITEMS,
                        (ArrayList<? extends Parcelable>) arrayBundle);
                mFirebaseAnalytics.logEvent(FirebaseAnalytics.Event.BEGIN_CHECKOUT, beginCheckoutParams);
            }
        });


    }


    private void initRecyclerView(CategoryModel categoryModel) {
        /*if(categoryModel.getMenus() == null){
            tvTotalItems.setText("Please add some items in cart");
        }*/
        recyclerView_cart.setLayoutManager(new LinearLayoutManager(this));
        cartListAdapter = new CartListAdapter(categoryModel.getMenus());
        recyclerView_cart.setAdapter(cartListAdapter);
    }

    private void calculateTotalAmount(CategoryModel categoryModel) {


        for (ProductModel m : categoryModel.getMenus()) {
            subTotalAmount += m.getPrice() * m.getTotalInCart();
        }

        tvSubtotalAmount.setText(String.valueOf(NumberFormat.getCurrencyInstance(new Locale("en", "IN")).format(subTotalAmount)));
        tvDeliveryChargeAmount.setText("₹" + String.format("%.2f", categoryModel.getDelivery_charge()));
        subTotalAmount += categoryModel.getDelivery_charge();
        tvTotalAmount.setText(String.valueOf(NumberFormat.getCurrencyInstance(new Locale("en", "IN")).format(subTotalAmount)));
        //myEdit.putString("TotalPrice", String.valueOf(NumberFormat.getCurrencyInstance(new Locale("en", "IN")).format(subTotalAmount)));
        myEdit.putFloat("Value", subTotalAmount);
        myEdit.apply();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {

        if (requestCode == 1000) {
            setResult(Activity.RESULT_OK);
            finish();
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

//    @Override
//    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
//
//        switch (item.getItemId()) {
//            case android.R.id.home:
//                finish();
//            default:
//                //do nothing
//        }
//        return super.onOptionsItemSelected(item);
//    }

    @Override
    protected void onResume() {
        super.onResume();
        Bundle screen_view = new Bundle();
        screen_view.putString(FirebaseAnalytics.Param.SCREEN_NAME, screen_name); //e.g. Screen Name
        screen_view.putString(FirebaseAnalytics.Param.SCREEN_CLASS, this.getLocalClassName()); // You can pass the value as specific activity name over here and if not then you can ignore this line and it will take the value automtically
        mFirebaseAnalytics.logEvent(FirebaseAnalytics.Event.SCREEN_VIEW, screen_view);
    }
}