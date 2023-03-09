package com.tatvic.ecommercetraining;

import static androidx.constraintlayout.helper.widget.MotionEffect.TAG;
import static java.lang.reflect.Array.set;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Parcelable;
import android.preference.PreferenceManager;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.google.gson.Gson;
import com.tatvic.ecommercetraining.adapters.PLPListAdapter;
import com.tatvic.ecommercetraining.model.ProductModel;
import com.tatvic.ecommercetraining.model.CategoryModel;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class ProductListing extends AppCompatActivity implements PLPListAdapter.MenuListClickListener,
        com.tatvic.ecommercetraining.adapters.PLPListAdapter.ProductClickListener,
        com.tatvic.ecommercetraining.adapters.PLPListAdapter.AddToCartListener,
        com.tatvic.ecommercetraining.adapters.PLPListAdapter.RemoveFromCartListener {

    private static final String screen_name = "Product Listing Screen";
    private RecyclerView rv_PLP;
    private Button GoToCart;
    private List<ProductModel> menuList = null;
    private PLPListAdapter PLPListAdapter;
    static List<ProductModel> itemsInCartList;
    private int totalItemInCart = 0;
    private FirebaseAnalytics mFirebaseAnalytics;
    CategoryModel categoryModel;
    Bundle product;

    static
    {
        itemsInCartList = new ArrayList<>();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_listing);
        categoryModel = getIntent().getParcelableExtra("RestaurantModel");
        rv_PLP = findViewById(R.id.rv_PLP);
        mFirebaseAnalytics = FirebaseAnalytics.getInstance(this);
        product = new Bundle();
        menuList = categoryModel.getMenus();

        initRecyclerView();
        GoToCart = findViewById(R.id.GoToCart);
        GoToCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(itemsInCartList == null && itemsInCartList.size() <= 0) {
                    Snackbar.make(v, "Please add some items in cart", Snackbar.LENGTH_LONG)
                            .setAction("OK", new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                }
                            }).show();
                    return;
                }
                categoryModel.setMenus(itemsInCartList);
                Intent i = new Intent(ProductListing.this, Cart.class);
                i.putExtra("RestaurantModel", categoryModel);
                startActivityForResult(i, 1000);

                Bundle params = new Bundle();
                params.putInt("total_items_in_cart", totalItemInCart);
                params.putString("screen_name", screen_name);
                mFirebaseAnalytics.logEvent("go_to_cart_click", params);
            }
        });


    }

    private void initRecyclerView() {
        RecyclerView recyclerView =  findViewById(R.id.rv_PLP);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        MainActivity mainActivity = new MainActivity();
        PLPListAdapter = new PLPListAdapter(this, menuList,
                this, this, this, this);
        recyclerView.setAdapter(PLPListAdapter);
    }

//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        // Inflate the menu; this adds items to the action bar if it is present.
//        getMenuInflater().inflate(R.menu.plp_menu, menu);
//        return true;
//    }



    @Override
    public void onAddToCartClick(ProductModel productModel) {
        if(itemsInCartList == null) {
//            itemsInCartList = new ArrayList<>();
        }
        itemsInCartList.add(productModel);
        totalItemInCart = 0;

        for(ProductModel m : itemsInCartList) {
            totalItemInCart = totalItemInCart + m.getTotalInCart();
        }
        //buttonCheckout.setText("Checkout (" +totalItemInCart +") items");
    }

    @Override
    public void onUpdateCartClick(ProductModel productModel) {
        if(itemsInCartList.contains(productModel)) {
            int index = itemsInCartList.indexOf(productModel);
            itemsInCartList.remove(index);
            itemsInCartList.add(index, productModel);

            totalItemInCart = 0;

            for(ProductModel m : itemsInCartList) {
                totalItemInCart = totalItemInCart + m.getTotalInCart();
            }
            //buttonCheckout.setText("Checkout (" +totalItemInCart +") items");
        }
    }

    @Override
    public void onRemoveFromCartClick(ProductModel productModel) {
        if(itemsInCartList.contains(productModel)) {
            itemsInCartList.remove(productModel);
            totalItemInCart = 0;

            for(ProductModel m : itemsInCartList) {
                totalItemInCart = totalItemInCart + m.getTotalInCart();
            }
            //buttonCheckout.setText("Checkout (" +totalItemInCart +") items");
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == 1000 && resultCode == Activity.RESULT_OK) {
            //
            finish();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        Bundle screen_view = new Bundle();
        screen_view.putString(FirebaseAnalytics.Param.SCREEN_NAME, screen_name); //e.g. Screen Name
        screen_view.putString(FirebaseAnalytics.Param.SCREEN_CLASS, this.getLocalClassName()); // You can pass the value as specific activity name over here and if not then you can ignore this line and it will take the value automtically
        mFirebaseAnalytics.logEvent(FirebaseAnalytics.Event.SCREEN_VIEW, screen_view);
    }

    @Override
    public void onProClick(Integer position) {

        product.putString(FirebaseAnalytics.Param.ITEM_ID, menuList.get(position).getItem_id());
        product.putString(FirebaseAnalytics.Param.ITEM_NAME, menuList.get(position).getName());
        product.putString(FirebaseAnalytics.Param.ITEM_CATEGORY, menuList.get(position).getItem_category());
        product.putString(FirebaseAnalytics.Param.ITEM_VARIANT, menuList.get(position).getVariant());
        product.putString(FirebaseAnalytics.Param.ITEM_BRAND, menuList.get(position).getBrand());
        product.putDouble(FirebaseAnalytics.Param.PRICE, menuList.get(position).getPrice());

        Bundle selectItemParams = new Bundle();
        selectItemParams.putString(FirebaseAnalytics.Param.ITEM_LIST_ID, categoryModel.getAddress());
        selectItemParams.putString(FirebaseAnalytics.Param.ITEM_LIST_NAME, categoryModel.getName());
        selectItemParams.putParcelableArray(FirebaseAnalytics.Param.ITEMS,
                new Parcelable[]{product});
        mFirebaseAnalytics.logEvent(FirebaseAnalytics.Event.SELECT_ITEM, selectItemParams);

        Bundle viewItemParams = new Bundle();
        viewItemParams.putString(FirebaseAnalytics.Param.CURRENCY, "INR");
        viewItemParams.putDouble(FirebaseAnalytics.Param.VALUE, menuList.get(position).getPrice());
        viewItemParams.putParcelableArray(FirebaseAnalytics.Param.ITEMS,
                new Parcelable[]{product});
        mFirebaseAnalytics.logEvent(FirebaseAnalytics.Event.VIEW_ITEM, viewItemParams);
    }

    @Override
    public void onAddToCartProduct(Integer position) {
        product.putString(FirebaseAnalytics.Param.ITEM_ID, menuList.get(position).getItem_id());
        product.putString(FirebaseAnalytics.Param.ITEM_NAME, menuList.get(position).getName());
        product.putString(FirebaseAnalytics.Param.ITEM_CATEGORY, menuList.get(position).getItem_category());
        product.putString(FirebaseAnalytics.Param.ITEM_VARIANT, menuList.get(position).getVariant());
        product.putString(FirebaseAnalytics.Param.ITEM_BRAND, menuList.get(position).getBrand());
        product.putDouble(FirebaseAnalytics.Param.PRICE, menuList.get(position).getPrice());

        Bundle itemJeggingsWishlist = new Bundle(product);
        itemJeggingsWishlist.putLong(FirebaseAnalytics.Param.QUANTITY, 1);

        Bundle addToWishlistParams = new Bundle();
        addToWishlistParams.putString(FirebaseAnalytics.Param.CURRENCY, "INR");
        addToWishlistParams.putDouble(FirebaseAnalytics.Param.VALUE, menuList.get(position).getPrice());
        addToWishlistParams.putParcelableArray(FirebaseAnalytics.Param.ITEMS,
                new Parcelable[]{itemJeggingsWishlist});

        mFirebaseAnalytics.logEvent(FirebaseAnalytics.Event.ADD_TO_CART, addToWishlistParams);
    }

    @Override
    public void onRemoveFromProduct(Integer position) {
        product.putString(FirebaseAnalytics.Param.ITEM_ID, menuList.get(position).getItem_id());
        product.putString(FirebaseAnalytics.Param.ITEM_NAME, menuList.get(position).getName());
        product.putString(FirebaseAnalytics.Param.ITEM_CATEGORY, menuList.get(position).getItem_category());
        product.putString(FirebaseAnalytics.Param.ITEM_VARIANT, menuList.get(position).getVariant());
        product.putString(FirebaseAnalytics.Param.ITEM_BRAND, menuList.get(position).getBrand());
        product.putDouble(FirebaseAnalytics.Param.PRICE, menuList.get(position).getPrice());

        Bundle removeCartParams = new Bundle();
        removeCartParams.putString(FirebaseAnalytics.Param.CURRENCY, "INR");
        removeCartParams.putDouble(FirebaseAnalytics.Param.VALUE, menuList.get(position).getPrice());
        removeCartParams.putParcelableArray(FirebaseAnalytics.Param.ITEMS,
                new Parcelable[]{product});

        mFirebaseAnalytics.logEvent(FirebaseAnalytics.Event.REMOVE_FROM_CART, removeCartParams);
    }
}