package com.tatvic.ecommercetraining;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import com.google.firebase.analytics.FirebaseAnalytics;

public class Thankyou extends AppCompatActivity {
    private FirebaseAnalytics mFirebaseAnalytics;
    private static final String screen_name = "Thank You Screen";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_thankyou);
        mFirebaseAnalytics = FirebaseAnalytics.getInstance(this);

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(Thankyou.this, MainActivity.class));
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