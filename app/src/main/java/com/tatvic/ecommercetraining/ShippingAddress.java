package com.tatvic.ecommercetraining;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.tatvic.ecommercetraining.model.CategoryModel;

public class ShippingAddress extends AppCompatActivity {

    private static final String screen_name = "Shipping Address Screen";
    private Button buttonPlaceYourOrder;
    Context context;
    private EditText inputName, inputAddress, inputCity, inputState, inputZip;
    Intent i;

    String isCart;
    CategoryModel categoryModel;
    Float cartValue;
    private FirebaseAnalytics mFirebaseAnalytics;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shipping_address);
        findViewById();
        mFirebaseAnalytics = FirebaseAnalytics.getInstance(this);

        i = getIntent();
        isCart = i.getStringExtra("from_cart");
        categoryModel = i.getParcelableExtra("RestaurantModel");
        cartValue = i.getFloatExtra("Total",0);
        Log.d("JBKADF", "onCreate: "+isCart);

        buttonPlaceYourOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onPlaceOrderButtonClick(view);
            }
        });
    }


    private void cartItemsRecyclerView() {
//        item_list = new ArrayList<>();
//        item_list.add(new ItemModel("Mobile", "$ 299", R.drawable.iphone));
//        item_list.add(new ItemModel("TV", "$ 1099", R.drawable.tv));
//        item_list.add(new ItemModel("AC", "$ 299", R.drawable.ac));
//
//        searchAdapter = new SearchAdapter(this, item_list);
//        cartItemsRecyclerView.setHasFixedSize(true);
//        cartItemsRecyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext(),
//                LinearLayoutManager.VERTICAL, false));
//        cartItemsRecyclerView.setAdapter(searchAdapter);

    }
//    private void calculateTotalAmount(RestaurantModel restaurantModel) {
//        float subTotalAmount = 0f;
//
//        for(Product m : restaurantModel.getMenus()) {
//            subTotalAmount += m.getPrice() * m.getTotalInCart();
//        }
//
//        tvSubtotalAmount.setText("$"+String.format("%.2f", subTotalAmount));
//        tvDeliveryChargeAmount.setText("$"+String.format("%.2f", restaurantModel.getDelivery_charge()));
//        subTotalAmount += restaurantModel.getDelivery_charge();
//
//        tvTotalAmount.setText("$"+String.format("%.2f", subTotalAmount));
//    }
//
//    private void initRecyclerView(RestaurantModel restaurantModel) {
//        cartItemsRecyclerView.setLayoutManager(new LinearLayoutManager(this));
//        placeYourOrderAdapter = new PlaceYourOrderAdapter(restaurantModel.getMenus());
//        cartItemsRecyclerView.setAdapter(placeYourOrderAdapter);
//    }

    private void onPlaceOrderButtonClick(View view) {
        if(TextUtils.isEmpty(inputName.getText().toString())) {
            Snackbar.make(view, "Please enter name", Snackbar.LENGTH_LONG)
                    .setAction("OK", new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                        }
                    }).show();
            return;
        } else if(TextUtils.isEmpty(inputAddress.getText().toString())) {
            Snackbar.make(view, "Please enter address", Snackbar.LENGTH_LONG)
                    .setAction("OK", new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                        }
                    }).show();
            return;
        }else if(TextUtils.isEmpty(inputCity.getText().toString())) {
            Snackbar.make(view, "Please enter city", Snackbar.LENGTH_LONG)
                    .setAction("OK", new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                        }
                    }).show();
            return;
        }else if(TextUtils.isEmpty(inputState.getText().toString())) {
            Snackbar.make(view, "Please enter state", Snackbar.LENGTH_LONG)
                    .setAction("OK", new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                        }
                    }).show();
            return;
        }else if(TextUtils.isEmpty(inputZip.getText().toString())) {
            Snackbar.make(view, "Please enter zip", Snackbar.LENGTH_LONG)
                    .setAction("OK", new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                        }
                    }).show();
            return;
        }
        Intent intent = new Intent(ShippingAddress.this, PaymentMethod.class);

        intent.putExtra("name", inputName.getText().toString());
        intent.putExtra("address", inputAddress.getText().toString());
        intent.putExtra("city", inputCity.getText().toString());
        intent.putExtra("state", inputState.getText().toString());
        intent.putExtra("zip", inputZip.getText().toString());
        //intent.putExtra("Total", i.getStringExtra("Total"));
        startActivity(intent);

        return;
    }

    private void findViewById() {
        buttonPlaceYourOrder = findViewById(R.id.buttonPlaceYourOrder);
        inputName = findViewById(R.id.inputName);
        inputAddress = findViewById(R.id.inputAddress);
        inputCity = findViewById(R.id.inputCity);
        inputState = findViewById(R.id.inputState);
        inputZip = findViewById(R.id.inputZip);
//        cartItemsRecyclerView = findViewById(R.id.cartItemsRecyclerView);
//        tvSubtotalAmount = findViewById(R.id.tvSubtotalAmount);
//        tvDeliveryChargeAmount = findViewById(R.id.tvDeliveryChargeAmount);
//        tvTotalAmount = findViewById(R.id.tvTotalAmount);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();

        if(isCart.equals("yes")){
//            startActivity(new Intent(this, Cart.class));
            Intent i = new Intent(this, Cart.class);
            i.putExtra("RestaurantModel", categoryModel);
            i.putExtra("Total", cartValue);
            i.putExtra("from_cart","yes");
            startActivity(i);
        }

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