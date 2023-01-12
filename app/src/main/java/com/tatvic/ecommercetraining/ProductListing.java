package com.tatvic.ecommercetraining;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.Menu;

import com.denzcoskun.imageslider.ImageSlider;
import com.denzcoskun.imageslider.constants.ScaleTypes;
import com.denzcoskun.imageslider.models.SlideModel;

import java.util.ArrayList;

public class ProductListing extends AppCompatActivity {

    private RecyclerView rv_PLP;
    private PLPAdapter plpAdapter;
    private ArrayList<ItemModel> item_list;
    private ImageSlider imageSlider;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_listing);

        rv_PLP = findViewById(R.id.rv_PLP);
        imageSlider = findViewById(R.id.image_PLP_slider);

        ArrayList<SlideModel> arrayList = new ArrayList<>();
        arrayList.add(new SlideModel(R.drawable.promotion, ScaleTypes.CENTER_INSIDE));
        arrayList.add(new SlideModel(R.drawable.promo2, ScaleTypes.CENTER_INSIDE));
        arrayList.add(new SlideModel(R.drawable.promo3, ScaleTypes.CENTER_INSIDE));
        imageSlider.setImageList(arrayList);


        item_list = new ArrayList<>();
        item_list.add(new ItemModel("Mobile", "$ 299", R.drawable.iphone));
        item_list.add(new ItemModel("TV", "$ 1099", R.drawable.tv));
        item_list.add(new ItemModel("AC", "$ 299", R.drawable.ac));
        item_list.add(new ItemModel("Fridge", "$ 299", R.drawable.fridge));
        item_list.add(new ItemModel("Washing Machine", "$ 299", R.drawable.kindpng_2085518));

        plpAdapter = new PLPAdapter(this, item_list);
        rv_PLP.setHasFixedSize(true);
        rv_PLP.setLayoutManager(new LinearLayoutManager(getApplicationContext(),
                LinearLayoutManager.VERTICAL, false));
        rv_PLP.setAdapter(plpAdapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.plp_menu, menu);
        return true;
    }
}