package com.tatvic.ecommercetraining;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.tatvic.ecommercetraining.model.CategoryModel;
import com.tatvic.ecommercetraining.model.ProductModel;

import java.util.ArrayList;
import java.util.List;

public class ShippingAddress extends AppCompatActivity {

    private static final String screen_name = "Shipping Address Screen";
    private Button buttonPlaceYourOrder;
    private RadioButton radioButton;
    private RadioGroup radioGroupDelivery;
    Context context;
    private EditText inputName, inputAddress, inputCity, inputState, inputZip;
    Intent i;
    private List<ProductModel> plist;
    String isCart, selectedDeliveryMethod, item_bundle, isDetail;
    CategoryModel categoryModel;
    Float cartValue;
    private FirebaseAnalytics mFirebaseAnalytics;
    private List<Bundle> arrayBundle = new ArrayList<Bundle>();
    Bundle addShippingParams, final_bundle;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shipping_address);
        findViewById();

        mFirebaseAnalytics = FirebaseAnalytics.getInstance(this);
        plist = new ArrayList<ProductModel>();
        i = getIntent();
        isCart = i.getStringExtra("from_cart");
        isDetail = i.getStringExtra("from_detail");
        categoryModel = i.getParcelableExtra("RestaurantModel");
        cartValue = i.getFloatExtra("Total",0);
        Log.d("JBKADF", "onCreate: "+isDetail);

        buttonPlaceYourOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onPlaceOrderButtonClick(view);
            }
        });
    }


    private void onPlaceOrderButtonClick(View view) {

        int selectedId = radioGroupDelivery.getCheckedRadioButtonId();
        if (TextUtils.isEmpty(inputName.getText().toString())) {
            Snackbar.make(view, "Please enter name", Snackbar.LENGTH_LONG)
                    .setAction("OK", new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                        }
                    }).show();
            return;
        } else if (TextUtils.isEmpty(inputAddress.getText().toString())) {
            Snackbar.make(view, "Please enter address", Snackbar.LENGTH_LONG)
                    .setAction("OK", new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                        }
                    }).show();
            return;
        } else if (TextUtils.isEmpty(inputCity.getText().toString())) {
            Snackbar.make(view, "Please enter city", Snackbar.LENGTH_LONG)
                    .setAction("OK", new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                        }
                    }).show();
            return;
        } else if (TextUtils.isEmpty(inputState.getText().toString())) {
            Snackbar.make(view, "Please enter state", Snackbar.LENGTH_LONG)
                    .setAction("OK", new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                        }
                    }).show();
            return;
        } else if (TextUtils.isEmpty(inputZip.getText().toString())) {
            Snackbar.make(view, "Please enter zip", Snackbar.LENGTH_LONG)
                    .setAction("OK", new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                        }
                    }).show();
            return;
        }

//

            if (selectedId != -1) {
                // At least one radio button is selected
                radioButton = findViewById(selectedId);
                selectedDeliveryMethod = radioButton.getText().toString();
                // Use the selected value as needed
            }  // No radio button is selected

        Log.d("selectedDeliveryMethod", selectedDeliveryMethod);
            Intent intent = new Intent(ShippingAddress.this, PaymentMethod.class);

            intent.putExtra("name", inputName.getText().toString());
            intent.putExtra("address", inputAddress.getText().toString());
            intent.putExtra("city", inputCity.getText().toString());
            intent.putExtra("state", inputState.getText().toString());
            intent.putExtra("zip", inputZip.getText().toString());
            intent.putExtra("deliveryMethod", selectedDeliveryMethod);
            //intent.putExtra("Total", i.getStringExtra("Total"));
            startActivity(intent);

        Bundle addShippingParams = new Bundle();
        addShippingParams.putString(FirebaseAnalytics.Param.CURRENCY, "INR");
        addShippingParams.putString("User_Address", inputAddress.getText().toString() + ", "
                + inputCity.getText().toString()+ ", "
                + inputState.getText().toString() +", "
                + inputZip.getText().toString());
        //addShippingParams.putDouble(FirebaseAnalytics.Param.VALUE, cartValue);

        addShippingParams.putDouble(FirebaseAnalytics.Param.VALUE, 0.00);

        addShippingParams.putString(FirebaseAnalytics.Param.COUPON, "SUMMER_FUN");
        addShippingParams.putString(FirebaseAnalytics.Param.COUPON, "SUMMER_FUN");
        addShippingParams.putString(FirebaseAnalytics.Param.COUPON, "SUMMER_FUN");


        addShippingParams.putString(FirebaseAnalytics.Param.SHIPPING_TIER, selectedDeliveryMethod);

        arrayBundle.clear();

        for (int i = 0; i < ProductListing.itemsInCartList.size(); i++) {
            plist.add(ProductListing.itemsInCartList.get(i));
            Bundle item_bundle = new Bundle();

          //  item_bundle.putString(FirebaseAnalytics.Param.ITEM_ID, plist.get(i).getItem_id());

            item_bundle.putString(FirebaseAnalytics.Param.ITEM_ID, plist.get(i).getBrand());

//            item_bundle.putString(FirebaseAnalytics.Param.ITEM_NAME, plist.get(i).getName());
//            item_bundle.putString(FirebaseAnalytics.Param.ITEM_NAME, plist.get(i).getName());
//            item_bundle.putString(FirebaseAnalytics.Param.ITEM_NAME, plist.get(i).getName());

          //  item_bundle.putString(FirebaseAnalytics.Param.ITEM_CATEGORY, plist.get(i).getItem_category());

            item_bundle.putString("category", plist.get(i).getItem_category());

          //  item_bundle.putString(FirebaseAnalytics.Param.ITEM_VARIANT, plist.get(i).getVariant());
          //  item_bundle.putString(FirebaseAnalytics.Param.ITEM_BRAND, plist.get(i).getBrand());
            item_bundle.putDouble(FirebaseAnalytics.Param.PRICE, plist.get(i).getPrice());

            arrayBundle.add(item_bundle);

            addShippingParams.putParcelableArrayList(FirebaseAnalytics.Param.ITEMS,
                    (ArrayList<? extends Parcelable>) arrayBundle);
        }
        mFirebaseAnalytics.logEvent(FirebaseAnalytics.Event.ADD_SHIPPING_INFO, addShippingParams);
    }

    private void findViewById() {
        buttonPlaceYourOrder = findViewById(R.id.buttonPlaceYourOrder);
        inputName = findViewById(R.id.inputName);
        inputAddress = findViewById(R.id.inputAddress);
        inputCity = findViewById(R.id.inputCity);
        inputState = findViewById(R.id.inputState);
        inputZip = findViewById(R.id.inputZip);
        radioGroupDelivery = findViewById(R.id.radioGroupDelivery);
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
            i.putExtra("from_detail","yes");
            startActivity(i);
        }else if(isDetail.equals("yes")){
            Intent i = new Intent(this, ProductDetail.class);
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

        //--------GA3 Screen view---------
        Bundle bundle = new Bundle();
        bundle.putString(FirebaseAnalytics.Param.SCREEN_NAME, screen_name);// Pass the Screen name
        mFirebaseAnalytics.logEvent("screenview_manual", bundle);
    }
}