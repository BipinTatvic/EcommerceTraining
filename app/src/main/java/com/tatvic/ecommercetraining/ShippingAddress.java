package com.tatvic.ecommercetraining;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

//import com.tatvic.ecommercetraining.adapters.SearchAdapter;

public class ShippingAddress extends AppCompatActivity {

    private Button buttonPlaceYourOrder;
    private EditText inputName, inputAddress, inputCity, inputState, inputZip;
//    private SearchAdapter searchAdapter;
    Intent i = getIntent();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shipping_address);
        findViewById();
        //cartItemsRecyclerView();


        ;
        buttonPlaceYourOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onPlaceOrderButtonClick();
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

    private void onPlaceOrderButtonClick() {
        if(TextUtils.isEmpty(inputName.getText().toString())) {
            inputName.setError("Please enter name ");
            return;
        } else if(TextUtils.isEmpty(inputAddress.getText().toString())) {
            inputAddress.setError("Please enter address ");
            return;
        }else if(TextUtils.isEmpty(inputCity.getText().toString())) {
            inputCity.setError("Please enter city ");
            return;
        }else if(TextUtils.isEmpty(inputState.getText().toString())) {
            inputState.setError("Please enter state ");
            return;
        }else if(TextUtils.isEmpty(inputZip.getText().toString())) {
            inputState.setError("Please enter zip ");
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
}