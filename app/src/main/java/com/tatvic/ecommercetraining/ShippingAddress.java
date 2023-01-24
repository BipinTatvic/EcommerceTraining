package com.tatvic.ecommercetraining;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.util.ArrayList;

public class ShippingAddress extends AppCompatActivity {

    private Button buttonPlaceYourOrder;
    private EditText inputName, inputAddress, inputCity, inputState, inputZip;
    private RecyclerView cartItemsRecyclerView;
    private SearchAdapter searchAdapter;
    private ArrayList<ItemModel> item_list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shipping_address);

        findViewById();
        //cartItemsRecyclerView();
        editText();
    }

    private void cartItemsRecyclerView() {
        item_list = new ArrayList<>();
        item_list.add(new ItemModel("Mobile", "$ 299", R.drawable.iphone));
        item_list.add(new ItemModel("TV", "$ 1099", R.drawable.tv));
        item_list.add(new ItemModel("AC", "$ 299", R.drawable.ac));

        searchAdapter = new SearchAdapter(this, item_list);
        cartItemsRecyclerView.setHasFixedSize(true);
        cartItemsRecyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext(),
                LinearLayoutManager.VERTICAL, false));
        cartItemsRecyclerView.setAdapter(searchAdapter);

    }

    private void editText() {

        buttonPlaceYourOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ShippingAddress.this, PaymentMethod.class);

                intent.putExtra("name", inputName.getText().toString());
                intent.putExtra("address", inputAddress.getText().toString());
                intent.putExtra("city", inputCity.getText().toString());
                intent.putExtra("state", inputState.getText().toString());
                intent.putExtra("zip", inputZip.getText().toString());

                startActivity(intent);
            }
        });
    }

    private void findViewById() {
        buttonPlaceYourOrder = findViewById(R.id.buttonPlaceYourOrder);
        inputName = findViewById(R.id.inputName);
        inputAddress = findViewById(R.id.inputAddress);
        inputCity = findViewById(R.id.inputCity);
        inputState = findViewById(R.id.inputState);
        inputZip = findViewById(R.id.inputZip);
        cartItemsRecyclerView = findViewById(R.id.cartItemsRecyclerView);
    }
}