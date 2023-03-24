package com.tatvic.ecommercetraining;

import static com.tatvic.ecommercetraining.ProductListing.itemsInCartList;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.tatvic.ecommercetraining.adapters.PLPListAdapter;
import com.tatvic.ecommercetraining.model.CategoryModel;
import com.tatvic.ecommercetraining.model.ProductModel;

import java.text.NumberFormat;
import java.text.ParseException;
import java.util.Formatter;
import java.util.List;
import java.util.Locale;

public class ProductDetail extends AppCompatActivity {

    private static final String screen_name = "Product Details Screen";
    private ImageView backImage, imageView4, item_image;
    private TextView pdp_item_name, pdp_item_price;
    private Button add_to_cart, buy_now;
    private List<ProductModel> menuList = null;
    private PLPListAdapter.MenuListClickListener clickListener;
    private int totalItemInCart = 0;
    CategoryModel categoryModel;
    private FirebaseAnalytics mFirebaseAnalytics;
    private ProductModel productModel;
    Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getSupportActionBar().hide();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pdp);
        findViewById();
        mFirebaseAnalytics = FirebaseAnalytics.getInstance(this);

//        productModel = new ProductModel();

        intent = getIntent();

//        Log.d("PRICE", price.toString());
        pdp_item_name.setText(intent.getStringExtra("item_name"));
        pdp_item_price.setText(String.valueOf(NumberFormat.getCurrencyInstance(new Locale("en", "IN")).format(intent.getFloatExtra("item_price", 0))));
        Glide.with(item_image)
                .load(intent.getStringExtra("item_img_url"))
                .into(item_image);

        clickListeners();
    }

    private void findViewById() {
        backImage =  findViewById(R.id.imageView2);
        add_to_cart =  findViewById(R.id.add_to_cart);
        buy_now =  findViewById(R.id.buy_now);
        imageView4 =  findViewById(R.id.imageView4);
        item_image =  findViewById(R.id.pdp_item_image);
        pdp_item_name =  findViewById(R.id.pdp_item_name);
        pdp_item_price =  findViewById(R.id.pdp_item_price);
    }

    private void clickListeners() {
        backImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        add_to_cart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                Float price = intent.getFloatExtra("item_price", 0);
                productModel = new ProductModel(intent.getStringExtra("item_name"), price,
                        1, intent.getStringExtra("item_img_url"),
                        intent.getStringExtra("item_id"),
                        intent.getStringExtra("item_brand"),
                        intent.getStringExtra("item_variant"),
                        intent.getStringExtra("item_category"));

                itemsInCartList.add(productModel);

                Intent i =new Intent(ProductDetail.this, Cart.class);
                startActivity(i);


              /*  categoryModel = getIntent().getParcelableExtra("RestaurantModel");
                menuList = categoryModel.getMenus();
                ProductModel menu  = menuList.get(0);*/
                if(itemsInCartList == null) {
//            itemsInCartList = new ArrayList<>();
                }
          /*      itemsInCartList.add(menu);*/
                totalItemInCart = 0;

                for(ProductModel m : itemsInCartList) {
                    totalItemInCart = totalItemInCart + m.getTotalInCart();
                }

           /*     menu.setTotalInCart(1);
                clickListener.onAddToCartClick(menu);*/

            /*    categoryModel.setMenus(itemsInCartList);*/
              /*  Intent i = new Intent(ProductDetail.this, Cart.class);
//                i.putExtra("RestaurantModel", categoryModel);
                startActivityForResult(i, 1000);;*/
//<<<<<<< Code_With_Error_Analytics


//>>>>>>> codee_with_error_analytics
                Bundle product = new Bundle();
                product.putString(FirebaseAnalytics.Param.ITEM_ID, intent.getStringExtra("item_id"));
                product.putString(FirebaseAnalytics.Param.ITEM_NAME, intent.getStringExtra("item_name"));
                product.putString(FirebaseAnalytics.Param.ITEM_CATEGORY, intent.getStringExtra("item_category"));
                product.putString(FirebaseAnalytics.Param.ITEM_VARIANT, intent.getStringExtra("item_variant"));
                product.putString(FirebaseAnalytics.Param.ITEM_BRAND, intent.getStringExtra("item_brand"));
                product.putDouble(FirebaseAnalytics.Param.PRICE, price);

                Bundle itemJeggingsWishlist = new Bundle(product);
                itemJeggingsWishlist.putLong(FirebaseAnalytics.Param.QUANTITY, 1);

                Bundle addToWishlistParams = new Bundle();
                addToWishlistParams.putString(FirebaseAnalytics.Param.CURRENCY, "INR");
                addToWishlistParams.putDouble(FirebaseAnalytics.Param.VALUE, price);
//<<<<<<< Code_With_Error_Analytics//                
            addToWishlistParams.putParcelableArray(FirebaseAnalytics.Param.ITEMS,
                        new Parcelable[]{itemJeggingsWishlist});
                addToWishlistParams.putParcelableArray(FirebaseAnalytics.Param.ITEMS,
                        new Parcelable[]{itemJeggingsWishlist});
//>>>>>>> codee_with_error_analytics

                mFirebaseAnalytics.logEvent(FirebaseAnalytics.Event.ADD_TO_CART, addToWishlistParams);
            }
        });

        buy_now.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(ProductDetail.this, ShippingAddress.class);
                intent.putExtra("from_detail", "yes");
                startActivityForResult(intent, 1000);

                Bundle params = new Bundle();
//                params.putString(FirebaseAnalytics.Param.ITEM_ID, intent.getStringExtra("item_id"));
//                params.putString(FirebaseAnalytics.Param.ITEM_NAME, intent.getStringExtra("item_name"));
//                params.putDouble(FirebaseAnalytics.Param.PRICE, intent.getFloatExtra("item_price", 0));

//                Incorrect values in buy_now_click click event
                params.putString(FirebaseAnalytics.Param.ITEM_ID, "Iphone");
                params.putString(FirebaseAnalytics.Param.ITEM_NAME, "Iphone 14 pro");
                params.putDouble(FirebaseAnalytics.Param.PRICE, 999);

                params.putString("screen_name",screen_name);
                mFirebaseAnalytics.logEvent("buy_now_click", params);
            }
        });

        imageView4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(ProductDetail.this, Cart.class));

                Bundle custom_event1 = new Bundle();
                custom_event1.putString("menu_name", "Cart");
                custom_event1.putString("screen_name", screen_name);
                mFirebaseAnalytics.logEvent("top_action_bar_click", custom_event1);
            }
        });
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

}