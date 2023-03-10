package com.tatvic.ecommercetraining;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;


public class Sorry extends AppCompatActivity {

    private static final String screen_name = "Payment Failed Screen";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sorry);

    }

    @Override
    protected void onResume() {
        super.onResume();
    }
}