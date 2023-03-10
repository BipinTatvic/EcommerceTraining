package com.tatvic.ecommercetraining;

import static com.tatvic.ecommercetraining.ProductListing.itemsInCartList;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.material.snackbar.Snackbar;
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
    private ProductModel productModel;
    Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getSupportActionBar().hide();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pdp);
        findViewById();

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
                        intent.getStringExtra("item_variant"));

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
            }
        });

        buy_now.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                startActivity(new Intent(ProductDetail.this, ShippingAddress.class));

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

    }

}