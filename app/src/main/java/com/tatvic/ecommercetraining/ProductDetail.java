package com.tatvic.ecommercetraining;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.Formatter;

public class ProductDetail extends AppCompatActivity {

    private ImageView backImage, imageView4, item_image;
    private TextView pdp_item_name, pdp_item_price;
    private Button add_to_cart, buy_now;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getSupportActionBar().hide();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pdp);
        findViewById();
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
                startActivity(new Intent(ProductDetail.this, Cart.class));
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
}