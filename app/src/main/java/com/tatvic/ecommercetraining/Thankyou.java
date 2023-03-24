package com.tatvic.ecommercetraining;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.firebase.analytics.FirebaseAnalytics;

import java.util.ArrayList;

public class Thankyou extends AppCompatActivity {
    private FirebaseAnalytics mFirebaseAnalytics;
    private static final String screen_name = "Thank You Screen";

    private Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_thankyou);
        mFirebaseAnalytics = FirebaseAnalytics.getInstance(this);

        button = findViewById(R.id.refund);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                refundFunction();
            }
        });

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

        //--------GA3 Screen view---------
        Bundle bundle = new Bundle();
        bundle.putString(FirebaseAnalytics.Param.SCREEN_NAME, screen_name);// Pass the Screen name
        mFirebaseAnalytics.logEvent("screenview_manual", bundle);
    }

    private void refundFunction(){
        Bundle refundedProduct = new Bundle();
        refundedProduct.putString( FirebaseAnalytics.Param.TRANSACTION_ID, "T12345" ); // Required
        refundedProduct.putDouble( FirebaseAnalytics.Param.VALUE, 650.17 ); // Revenue
        refundedProduct.putString( FirebaseAnalytics.Param.CURRENCY, "INR");
        refundedProduct.putString(FirebaseAnalytics.Param.ITEM_LIST_ID, "L001"); // item list id//NOT MANDATORY
        refundedProduct.putString(FirebaseAnalytics.Param.ITEM_LIST_NAME, "Related products");//NOT MANDATORY
        ArrayList items = new ArrayList();
        items.add(refundedProduct);
        refundedProduct.putParcelableArrayList( "items", items );

// Log purchase_refund event with ecommerce bundle
        mFirebaseAnalytics.logEvent( FirebaseAnalytics.Event.REFUND, refundedProduct );
    }
}