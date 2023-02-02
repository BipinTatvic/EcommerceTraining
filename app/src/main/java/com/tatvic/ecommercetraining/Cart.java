package com.tatvic.ecommercetraining;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.tatvic.ecommercetraining.adapters.CartListAdapter;
import com.tatvic.ecommercetraining.model.ProductModel;
import com.tatvic.ecommercetraining.model.CategoryModel;

import java.text.NumberFormat;
import java.util.Locale;

public class Cart extends AppCompatActivity {
    private RecyclerView recyclerView_cart;
    private CartListAdapter cartListAdapter;
    private TextView tvSubtotalAmount, tvDeliveryChargeAmount, tvTotalAmount;
    private Button begin_checkout;
    float subTotalAmount = 0f;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);

        CategoryModel categoryModel = getIntent().getParcelableExtra("RestaurantModel");
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

        initRecyclerView(categoryModel);
        calculateTotalAmount(categoryModel);

        begin_checkout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Cart.this, ShippingAddress.class);
                i.putExtra("RestaurantModel", categoryModel);
                i.putExtra("Total", subTotalAmount);
                startActivityForResult(i, 1000);
            }
        });

    }

    private void initRecyclerView(CategoryModel categoryModel) {
        recyclerView_cart.setLayoutManager(new LinearLayoutManager(this));
        cartListAdapter = new CartListAdapter(categoryModel.getMenus());
        recyclerView_cart.setAdapter(cartListAdapter);
    }

    private void calculateTotalAmount(CategoryModel categoryModel) {


        for (ProductModel m : categoryModel.getMenus()) {
            subTotalAmount += m.getPrice() * m.getTotalInCart();
        }

        tvSubtotalAmount.setText(String.valueOf(NumberFormat.getCurrencyInstance(new Locale("en", "IN")).format(subTotalAmount)));
        tvDeliveryChargeAmount.setText("₹" + String.format("%.2f", categoryModel.getDelivery_charge()));
        subTotalAmount += categoryModel.getDelivery_charge();
        tvTotalAmount.setText(String.valueOf(NumberFormat.getCurrencyInstance(new Locale("en", "IN")).format(subTotalAmount)));
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