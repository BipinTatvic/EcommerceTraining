package com.tatvic.ecommercetraining;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.tatvic.ecommercetraining.adapters.CartAdapter;
import com.tatvic.ecommercetraining.adapters.PlaceYourOrderAdapter;
import com.tatvic.ecommercetraining.model.ItemModel;
import com.tatvic.ecommercetraining.model.Product;
import com.tatvic.ecommercetraining.model.RestaurantModel;

import java.util.ArrayList;
import java.util.List;

public class Cart extends AppCompatActivity {
    private RecyclerView recyclerView_cart;
    private PlaceYourOrderAdapter placeYourOrderAdapter;
    private TextView tvSubtotalAmount, tvDeliveryChargeAmount, tvTotalAmount;
    private Button begin_checkout;
    float subTotalAmount = 0f;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);

        RestaurantModel restaurantModel = getIntent().getParcelableExtra("RestaurantModel");
        recyclerView_cart = findViewById(R.id.cartItemsRecyclerView);
        begin_checkout = findViewById(R.id.begin_checkout);
        tvSubtotalAmount = findViewById(R.id.tvSubtotalAmount);
        tvDeliveryChargeAmount = findViewById(R.id.tvDeliveryChargeAmount);
        tvTotalAmount = findViewById(R.id.tvTotalAmount);
//        item_list = new ArrayList<>();
//        item_list.add(new ItemModel("Mobile", "$ 299", R.drawable.iphone));
//        item_list.add(new ItemModel("TV", "$ 1099", R.drawable.tv));
//        item_list.add(new ItemModel("AC", "$ 539", R.drawable.ac));
//        item_list.add(new ItemModel("Fridge", "$ 4089", R.drawable.fridge));
//        item_list.add(new ItemModel("Washing Machine", "$ 2249", R.drawable.kindpng_2085518));


//        cartAdapter = new CartAdapter(this, item_list);
//        recyclerView_cart.setHasFixedSize(true);
//        recyclerView_cart.setLayoutManager(new LinearLayoutManager(getApplicationContext(),
//                LinearLayoutManager.VERTICAL, false));
//        recyclerView_cart.setAdapter(cartAdapter);

        initRecyclerView(restaurantModel);
        calculateTotalAmount(restaurantModel);

        begin_checkout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Cart.this, ShippingAddress.class);
                i.putExtra("RestaurantModel", restaurantModel);
                i.putExtra("Total", subTotalAmount);
                startActivityForResult(i, 1000);
            }
        });

    }

    private void initRecyclerView(RestaurantModel restaurantModel) {
        recyclerView_cart.setLayoutManager(new LinearLayoutManager(this));
        placeYourOrderAdapter = new PlaceYourOrderAdapter(restaurantModel.getMenus());
        recyclerView_cart.setAdapter(placeYourOrderAdapter);
    }

    private void calculateTotalAmount(RestaurantModel restaurantModel) {


        for (Product m : restaurantModel.getMenus()) {
            subTotalAmount += m.getPrice() * m.getTotalInCart();
        }

        tvSubtotalAmount.setText("₹" + String.format("%.2f", subTotalAmount));
        tvDeliveryChargeAmount.setText("₹" + String.format("%.2f", restaurantModel.getDelivery_charge()));
        subTotalAmount += restaurantModel.getDelivery_charge();
        tvTotalAmount.setText("₹" + String.format("%.2f", subTotalAmount));
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {

        if(requestCode == 1000) {
            setResult(Activity.RESULT_OK);
            finish();
        }
        super.onActivityResult(requestCode, resultCode, data);
    }
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
            default:
                //do nothing
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        setResult(Activity.RESULT_CANCELED);
        finish();
    }

}