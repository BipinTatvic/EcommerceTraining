package com.tatvic.ecommercetraining;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;



public class Sorry extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sorry);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}