package com.tatvic.ecommercetraining;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import com.denzcoskun.imageslider.ImageSlider;
import com.denzcoskun.imageslider.constants.ScaleTypes;
import com.denzcoskun.imageslider.models.SlideModel;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private GridView grid_view;
    private ArrayList<ItemModel> item_list;
    private ImageSlider imageSlider;
    private CardView cardView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        grid_view = findViewById(R.id.grid_view);
        imageSlider = findViewById(R.id.image_slider);
        cardView = findViewById(R.id.card_promotion);

        cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, ProductListing.class);
                startActivity(intent);
            }
        });

        item_list = new ArrayList<>();
        item_list.add(new ItemModel("Mobile", "$ 299", R.drawable.iphone));
        item_list.add(new ItemModel("TV", "$ 1099", R.drawable.tv));
        item_list.add(new ItemModel("AC", "$ 299", R.drawable.ac));
        item_list.add(new ItemModel("Fridge", "$ 299", R.drawable.fridge));
        item_list.add(new ItemModel("Washing Machine", "$ 299", R.drawable.kindpng_2085518));

        ProductAdapter adapter = new ProductAdapter(this, item_list);
        grid_view.setAdapter(adapter);

        grid_view.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                startActivity(new Intent(MainActivity.this, ProductDetail.class));
            }
        });


        ArrayList<SlideModel> arrayList = new ArrayList<>();
        arrayList.add(new SlideModel(R.drawable.promotion, ScaleTypes.CENTER_INSIDE));
        arrayList.add(new SlideModel(R.drawable.promo2, ScaleTypes.CENTER_INSIDE));
        arrayList.add(new SlideModel(R.drawable.promo3, ScaleTypes.CENTER_INSIDE));
        imageSlider.setImageList(arrayList);
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) { switch(item.getItemId()) {
        case R.id.search:
            startActivity(new Intent(MainActivity.this, Search.class));
            Toast.makeText(this, "Search selected", Toast.LENGTH_SHORT).show();
            return(true);
        case R.id.cart:
            Toast.makeText(this, "Cart selected", Toast.LENGTH_SHORT).show();
            return(true);
        case R.id.profile:
            Toast.makeText(this, "Profile selected", Toast.LENGTH_SHORT).show();
            return(true);
        case R.id.about_us:
            Toast.makeText(this, "Contact Us selected", Toast.LENGTH_SHORT).show();
            return(true);
    }
        return(super.onOptionsItemSelected(item));
    }
}