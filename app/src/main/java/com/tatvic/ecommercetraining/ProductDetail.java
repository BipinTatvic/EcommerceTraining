package com.tatvic.ecommercetraining;

import static com.tatvic.ecommercetraining.ProductListing.itemsInCartList;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
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

import java.util.Formatter;
import java.util.List;

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


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getSupportActionBar().hide();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pdp);
        findViewById();
        mFirebaseAnalytics = FirebaseAnalytics.getInstance(this);

        Intent intent = getIntent();
        pdp_item_name.setText(intent.getStringExtra("item_name"));
        pdp_item_price.setText(intent.getStringExtra("item_price"));
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
                categoryModel = getIntent().getParcelableExtra("RestaurantModel");
                menuList = categoryModel.getMenus();
                ProductModel menu  = menuList.get(0);
                if(itemsInCartList == null) {
//            itemsInCartList = new ArrayList<>();
                }
                itemsInCartList.add(menu);
                totalItemInCart = 0;

                for(ProductModel m : itemsInCartList) {
                    totalItemInCart = totalItemInCart + m.getTotalInCart();
                }

                menu.setTotalInCart(1);
                clickListener.onAddToCartClick(menu);
                if(itemsInCartList != null && itemsInCartList.size() <= 0) {
                    Snackbar.make(view, "Please add some items in cart", Snackbar.LENGTH_LONG)
                            .setAction("OK", new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                }
                            }).show();
                    return;
                }
                categoryModel.setMenus(itemsInCartList);
                Intent i = new Intent(ProductDetail.this, Cart.class);
                i.putExtra("RestaurantModel", categoryModel);
                startActivityForResult(i, 1000);;
            }
        });

        buy_now.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(ProductDetail.this, Cart.class));
            }
        });

        imageView4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(ProductDetail.this, Cart.class));
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
    }

}