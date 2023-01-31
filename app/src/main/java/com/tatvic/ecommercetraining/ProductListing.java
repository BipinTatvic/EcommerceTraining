package com.tatvic.ecommercetraining;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.denzcoskun.imageslider.ImageSlider;
import com.denzcoskun.imageslider.constants.ScaleTypes;
import com.denzcoskun.imageslider.interfaces.ItemClickListener;
import com.denzcoskun.imageslider.models.SlideModel;
import com.tatvic.ecommercetraining.adapters.MenuListAdapter;
import com.tatvic.ecommercetraining.adapters.PLPAdapter;
import com.tatvic.ecommercetraining.model.ItemModel;
import com.tatvic.ecommercetraining.model.Product;
import com.tatvic.ecommercetraining.model.RestaurantModel;

import java.util.ArrayList;
import java.util.List;

public class ProductListing extends AppCompatActivity implements MenuListAdapter.MenuListClickListener{

    private RecyclerView rv_PLP;
    private PLPAdapter plpAdapter;
    private Button buttonCheckout;
    private ArrayList<ItemModel> item_list;
    private List<Product> menuList = null;
    private MenuListAdapter menuListAdapter;
    static List<Product> itemsInCartList;
    private int totalItemInCart = 0;

    static
    {
        itemsInCartList = new ArrayList<>();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_listing);
        RestaurantModel restaurantModel = getIntent().getParcelableExtra("RestaurantModel");
        rv_PLP = findViewById(R.id.rv_PLP);

        menuList = restaurantModel.getMenus();

        initRecyclerView();

        buttonCheckout = findViewById(R.id.buttonCheckout);
        buttonCheckout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(itemsInCartList != null && itemsInCartList.size() <= 0) {
                    Toast.makeText(ProductListing.this, "Please add some items in cart.", Toast.LENGTH_SHORT).show();
                    return;
                }
                restaurantModel.setMenus(itemsInCartList);
                Intent i = new Intent(ProductListing.this, Cart.class);
                i.putExtra("RestaurantModel", restaurantModel);
                startActivityForResult(i, 1000);
            }
        });


//        item_list = new ArrayList<>();
//        item_list.add(new ItemModel("Mobile", "$ 299", R.drawable.iphone));
//        item_list.add(new ItemModel("TV", "$ 1099", R.drawable.tv));
//        item_list.add(new ItemModel("AC", "$ 539", R.drawable.ac));
//        item_list.add(new ItemModel("Fridge", "$ 4089", R.drawable.fridge));
//        item_list.add(new ItemModel("Washing Machine", "$ 2249", R.drawable.kindpng_2085518));

//        plpAdapter = new PLPAdapter(this, item_list);
//        rv_PLP.setHasFixedSize(true);
//        rv_PLP.setLayoutManager(new LinearLayoutManager(getApplicationContext(),
//                LinearLayoutManager.VERTICAL, false));
//        rv_PLP.setAdapter(plpAdapter);
    }

    private void initRecyclerView() {
        RecyclerView recyclerView =  findViewById(R.id.rv_PLP);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        menuListAdapter = new MenuListAdapter(this, menuList, this);
        recyclerView.setAdapter(menuListAdapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.plp_menu, menu);
        return true;
    }


    @Override
    public void onAddToCartClick(Product product) {
        if(itemsInCartList == null) {
//            itemsInCartList = new ArrayList<>();
        }
        itemsInCartList.add(product);
        totalItemInCart = 0;

        for(Product m : itemsInCartList) {
            totalItemInCart = totalItemInCart + m.getTotalInCart();
        }
        buttonCheckout.setText("Checkout (" +totalItemInCart +") items");
    }

    @Override
    public void onUpdateCartClick(Product product) {
        if(itemsInCartList.contains(product)) {
            int index = itemsInCartList.indexOf(product);
            itemsInCartList.remove(index);
            itemsInCartList.add(index, product);

            totalItemInCart = 0;

            for(Product m : itemsInCartList) {
                totalItemInCart = totalItemInCart + m.getTotalInCart();
            }
            buttonCheckout.setText("Checkout (" +totalItemInCart +") items");
        }
    }

    @Override
    public void onRemoveFromCartClick(Product product) {
        if(itemsInCartList.contains(product)) {
            itemsInCartList.remove(product);
            totalItemInCart = 0;

            for(Product m : itemsInCartList) {
                totalItemInCart = totalItemInCart + m.getTotalInCart();
            }
            buttonCheckout.setText("Checkout (" +totalItemInCart +") items");
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == 1000 && resultCode == Activity.RESULT_OK) {
            //
            finish();
        }
    }
}