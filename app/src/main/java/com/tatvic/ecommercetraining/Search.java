package com.tatvic.ecommercetraining;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.widget.SearchView;



import java.util.ArrayList;
import java.util.List;

public class Search extends AppCompatActivity {



//    private SearchAdapter searchAdapter;
//    private List<ItemModel> item_list;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

//        fillExampleList();
//        setUpRecyclerView();

    }
//
//    private void fillExampleList() {
//        item_list = new ArrayList<>();
//
//        //item_list.add(new ItemModel("Mobile", "$ 299", R.drawable.iphone));
//        item_list.add(new ItemModel("TV", "$ 1099", R.drawable.tv));
//        item_list.add(new ItemModel("AC", "$ 539", R.drawable.ac));
//        item_list.add(new ItemModel("Fridge", "$ 4089", R.drawable.fridge));
//        item_list.add(new ItemModel("Washing Machine", "$ 2249", R.drawable.kindpng_2085518));
//
//       // searchAdapter = new SearchAdapter(this, item_list);
//    }
//
//    private void setUpRecyclerView() {
//        RecyclerView recyclerView = findViewById(R.id.search_recyclerView);
//        recyclerView.setHasFixedSize(true);
//        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
//        searchAdapter = new SearchAdapter(this, item_list);
//        recyclerView.setLayoutManager(layoutManager);
//        recyclerView.setAdapter(searchAdapter);
//    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.seach, menu);
        SearchView searchItem = (SearchView) menu.findItem(R.id.actionSearch).getActionView();
     //   SearchView searchView = (SearchView) searchItem.getActionView();
     //   searchView.setImeOptions(EditorInfo.IME_ACTION_DONE);
        searchItem.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }
            @Override
            public boolean onQueryTextChange(String newText) {
//               searchAdapter.getFilter().filter(newText);
                return false;
            }
        });
        return true;
    }
  /*  @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.search_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.pdp_search:
                Toast.makeText(this, "Search selected", Toast.LENGTH_SHORT).show();
                return (true);

        }
        return (super.onOptionsItemSelected(item));
    }*/

//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        MenuInflater inflater = getMenuInflater();
//        inflater.inflate(R.menu.search_menu, menu);
//        SearchView searchItem = (SearchView) menu.findItem(R.id.pdp_search).getActionView();
////        SearchView searchView = (SearchView) searchItem.getActionView();
////        searchView.setImeOptions(EditorInfo.IME_ACTION_DONE);
//        searchItem.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
//            @Override
//            public boolean onQueryTextSubmit(String query) {
//                return false;
//            }
//            @Override
//            public boolean onQueryTextChange(String newText) {
//                plpAdapter.getFilter().filter(newText);
//                return false;
//            }
//        });
//        return true;
//    }
}