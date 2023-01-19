package com.tatvic.ecommercetraining;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;

public class ProductDetail extends AppCompatActivity {

    ImageView backImage, imageView4;
    Button add_to_cart, buy_now;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getSupportActionBar().hide();
//        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
//                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pdp);

        backImage =  findViewById(R.id.imageView2);
        add_to_cart =  findViewById(R.id.add_to_cart);
        buy_now =  findViewById(R.id.buy_now);
        imageView4 =  findViewById(R.id.imageView4);

        clickListeners();
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