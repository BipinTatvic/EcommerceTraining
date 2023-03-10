package com.tatvic.ecommercetraining;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;


public class Thankyou extends AppCompatActivity {
    private static final String screen_name = "Thank You Screen";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_thankyou);

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(Thankyou.this, MainActivity.class));
    }

    @Override
    protected void onResume() {
        super.onResume();

    }
}