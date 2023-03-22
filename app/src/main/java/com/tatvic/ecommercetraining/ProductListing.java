package com.tatvic.ecommercetraining;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.tatvic.ecommercetraining.adapters.PLPListAdapter;
import com.tatvic.ecommercetraining.model.CategoryModel;
import com.tatvic.ecommercetraining.model.ProductModel;

import java.util.ArrayList;
import java.util.List;

public class ProductListing extends AppCompatActivity implements PLPListAdapter.MenuListClickListener, com.tatvic.ecommercetraining.adapters.PLPListAdapter.ProductClickListener, com.tatvic.ecommercetraining.adapters.PLPListAdapter.AddToCartListener, com.tatvic.ecommercetraining.adapters.PLPListAdapter.RemoveFromCartListener {

    private static final String screen_name = "Product Listing Screen";
    private RecyclerView rv_PLP;
    private Button GoToCart;
    private List<ProductModel> menuList = null;
    private PLPListAdapter PLPListAdapter;
    static List<ProductModel> itemsInCartList;
    private int totalItemInCart = 0;
    private FirebaseAnalytics mFirebaseAnalytics;
    CategoryModel categoryModel;
    Bundle product, itemAddToCart;
    private LinearLayoutManager linearLayoutManager;
    private boolean isFirstTime;
    private boolean indexLastLog = true;
    private int flag = 0;
    private List<Bundle> arrayBundle = new ArrayList<Bundle>();
    ;

    private RecyclerView.OnScrollListener recyclerViewOnScrollListener;

    static {
        itemsInCartList = new ArrayList<>();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_listing);
        categoryModel = getIntent().getParcelableExtra("RestaurantModel");
        rv_PLP = findViewById(R.id.rv_PLP);
        mFirebaseAnalytics = FirebaseAnalytics.getInstance(this);
        product = new Bundle();
        menuList = categoryModel.getMenus();

        isFirstTime = true;


        linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);

        initRecyclerView();


        GoToCart = findViewById(R.id.GoToCart);
        GoToCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (itemsInCartList == null && itemsInCartList.size() <= 0) {
                    Snackbar.make(v, "Please add some items in cart", Snackbar.LENGTH_LONG)
                            .setAction("OK", new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                }
                            }).show();
                    return;
                }
                categoryModel.setMenus(itemsInCartList);
                Intent i = new Intent(ProductListing.this, Cart.class);
                i.putExtra("RestaurantModel", categoryModel);
                startActivityForResult(i, 1000);
            }
        });


    }

    private void initRecyclerView() {
        RecyclerView recyclerView = findViewById(R.id.rv_PLP);
        recyclerView.setLayoutManager(linearLayoutManager);
//        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
//            @Override
//            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
//                super.onScrollStateChanged(recyclerView, newState);
//
//
//            }
//
//            @Override
//            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
//                super.onScrolled(recyclerView, dx, dy);
//
//                int visibleItemCount = linearLayoutManager.getChildCount();
//                int totalItemCount = linearLayoutManager.getItemCount();
//                int firstVisibleItemPosition = linearLayoutManager.findFirstVisibleItemPosition();
//                int lastItem = firstVisibleItemPosition + visibleItemCount - 2;
//                int lastIndexItem = firstVisibleItemPosition + visibleItemCount - 1;
//
//                Log.d("lastIndexItem", "Last Index::: " + lastIndexItem);
//
//                Log.d(
//                        "RecyclerView_Data",
//                        "onScrolled: \n " + "visibleItemCount: " + visibleItemCount + "\n" + "totalItemCount: " + totalItemCount + "\n" + "firstVisibleItemPosition: " + firstVisibleItemPosition + "\n" + "lastItem: " + lastItem
//                );
//
//                if (isFirstTime) {
//                    isFirstTime = false;
//                    flag = lastItem;
//
//                    Log.d("GA_FIRED", "onScrolled:IF");
//
//                    if (lastItem >= 0 && flag >= lastItem) {
//
//                        for (int i = 0; i <= lastItem; i++) {
//                            Bundle bundle = new Bundle();
//                            bundle.putString(FirebaseAnalytics.Param.ITEM_ID, menuList.get(i).getItem_id());
//                            bundle.putString(FirebaseAnalytics.Param.ITEM_NAME, menuList.get(i).getName());
//                            bundle.putString(FirebaseAnalytics.Param.ITEM_CATEGORY, menuList.get(i).getItem_category());
//                            bundle.putString(FirebaseAnalytics.Param.ITEM_VARIANT,  menuList.get(i).getVariant());
//                            bundle.putString(FirebaseAnalytics.Param.ITEM_BRAND, menuList.get(i).getBrand());
//                            bundle.putDouble(FirebaseAnalytics.Param.PRICE, menuList.get(i).getPrice());
//                            bundle.putLong(FirebaseAnalytics.Param.INDEX, i+1);
//                            arrayBundle.add(i, bundle);
//                        }
//
//                        Log.d("CREATED_ARRAY", "array data::" + arrayBundle);
//
//                        Bundle viewItemListBundle = new Bundle();
//
//                        viewItemListBundle.putString(FirebaseAnalytics.Param.ITEM_LIST_ID, categoryModel.getAddress());
//                        viewItemListBundle.putString(FirebaseAnalytics.Param.ITEM_LIST_NAME, categoryModel.getName());
//                        viewItemListBundle.putParcelableArrayList(FirebaseAnalytics.Param.ITEMS, (ArrayList<? extends Parcelable>) arrayBundle);
//
//                        mFirebaseAnalytics.logEvent(FirebaseAnalytics.Event.VIEW_ITEM_LIST, viewItemListBundle);
//
//                        Log.d(
//                                "GA_FIRED_COUNT",
//                                "onScrolled: First Visible " + firstVisibleItemPosition + " lastItem: " + lastItem);
//
//                        Log.d("GA_FIRED", "onScrolled:ELSEEE::: GA REPORTS GET HERE:: FIRST TIME");
//
//                    }
//                }
//                else if (flag < lastItem) {
//                    arrayBundle.clear();
//
//                    Log.d("GA_FIRED", "onScrolled:MAIN ELSEEE::: GA REPORTS GET HERE");
//                    Log.d(
//                            "GA_FIRED_COUNT",
//                            "onScrolled: First Visible " + firstVisibleItemPosition + "${lastItem} " + lastItem
//                    );
//
//
//                    for(int i=flag+1; i<=lastItem; i++){
//
//                        Log.d("LAST_INDEX", "onScrolled: First Index "+flag+" lastIndex "+lastItem);
//
//                        Log.d("I_AM_WATCHING_U", "onScrolled: "+i);
//
//                        Bundle bundle = new Bundle();
//                        bundle.putString(FirebaseAnalytics.Param.ITEM_ID, menuList.get(i).getItem_id());
//                        bundle.putString(FirebaseAnalytics.Param.ITEM_NAME, menuList.get(i).getName());
//                        bundle.putString(FirebaseAnalytics.Param.ITEM_CATEGORY, menuList.get(i).getItem_category());
//                        bundle.putString(FirebaseAnalytics.Param.ITEM_VARIANT,  menuList.get(i).getVariant());
//                        bundle.putString(FirebaseAnalytics.Param.ITEM_BRAND, menuList.get(i).getBrand());
//                        bundle.putDouble(FirebaseAnalytics.Param.PRICE, menuList.get(i).getPrice());
//                        bundle.putLong(FirebaseAnalytics.Param.INDEX, i+1);
//                        arrayBundle.add( bundle);
//
//                    }
//                    Log.d("CREATED_ARRAY", "array data::" + arrayBundle);
//
//                    Bundle viewItemListBundle = new Bundle();
//
//                    viewItemListBundle.putString(FirebaseAnalytics.Param.ITEM_LIST_ID, categoryModel.getAddress());
//                    viewItemListBundle.putString(FirebaseAnalytics.Param.ITEM_LIST_NAME, categoryModel.getName());
//                    viewItemListBundle.putParcelableArrayList(FirebaseAnalytics.Param.ITEMS, (ArrayList<? extends Parcelable>) arrayBundle);
//
//                    mFirebaseAnalytics.logEvent(FirebaseAnalytics.Event.VIEW_ITEM_LIST, viewItemListBundle);
//
//                    Log.d(
//                            "GA_FIRED_COUNT",
//                            "onScrolled: First Visible " + firstVisibleItemPosition + " lastItem: " + lastItem);
//
//                    Log.d("GA_FIRED", "onScrolled:ELSEEE::: GA REPORTS GET HERE:: FIRST TIME");
//
//                    //flag updated here
//                    flag = lastItem;
//
//                    Log.d("CREATED_ARRAY", "last ITEM else if: "+menuList.size());
//                }
//                else if(menuList.size()-1 == lastIndexItem){
//
//                    Log.d("CREATED_ARRAY", "last ITEM: ");
//
//                    if (indexLastLog == true) {
//                        indexLastLog = false;
//                        arrayBundle.clear();
//
//                        Bundle bundle = new Bundle();
//
//                        bundle.putString(FirebaseAnalytics.Param.ITEM_ID, menuList.get(menuList.size()-1).getItem_id());
//                        bundle.putString(FirebaseAnalytics.Param.ITEM_NAME, menuList.get(menuList.size()-1).getName());
//                        bundle.putString(FirebaseAnalytics.Param.ITEM_CATEGORY, menuList.get(menuList.size()-1).getItem_category());
//                        bundle.putString(FirebaseAnalytics.Param.ITEM_VARIANT, menuList.get(menuList.size()-1).getVariant());
//                        bundle.putString(FirebaseAnalytics.Param.ITEM_BRAND, menuList.get(menuList.size()-1).getBrand());
//                        bundle.putString(FirebaseAnalytics.Param.PRICE, String.valueOf(menuList.get(menuList.size()-1).getPrice()));
//                        bundle.putLong(FirebaseAnalytics.Param.INDEX, menuList.size());
//
//                        arrayBundle.add(bundle);
//
//                        Log.d("CREATED_ARRAY", "array data::" + arrayBundle);
//
//
//                        Bundle viewItemListBundle = new Bundle();
//
//                        viewItemListBundle.putString(FirebaseAnalytics.Param.ITEM_LIST_ID, categoryModel.getAddress());
//                        viewItemListBundle.putString(FirebaseAnalytics.Param.ITEM_LIST_NAME, categoryModel.getName());
//                        viewItemListBundle.putParcelableArrayList(FirebaseAnalytics.Param.ITEMS, (ArrayList<? extends Parcelable>) arrayBundle);
//
//                        mFirebaseAnalytics.logEvent(FirebaseAnalytics.Event.VIEW_ITEM_LIST, viewItemListBundle);
//
//                    }
//                }
//            }
//        });

        for (int i = 0; i < menuList.size() ; i++) {

            Bundle bundle = new Bundle();
            bundle.putString(FirebaseAnalytics.Param.ITEM_ID, menuList.get(i).getItem_id());
//            bundle.putString(FirebaseAnalytics.Param.ITEM_NAME, menuList.get(i).getName());
//            bundle.putString(FirebaseAnalytics.Param.ITEM_CATEGORY, menuList.get(i).getItem_category());
            bundle.putString(FirebaseAnalytics.Param.ITEM_CATEGORY, "Refrigerator");
            bundle.putString(FirebaseAnalytics.Param.ITEM_VARIANT, menuList.get(i).getVariant());
            bundle.putString(FirebaseAnalytics.Param.ITEM_BRAND, menuList.get(i).getBrand());
            bundle.putDouble(FirebaseAnalytics.Param.PRICE,(menuList.get(i).getPrice()));
            bundle.putLong(FirebaseAnalytics.Param.INDEX, menuList.size());

            arrayBundle.add(bundle);

        }
        Bundle viewItemListBundle = new Bundle();

        viewItemListBundle.putString(FirebaseAnalytics.Param.ITEM_LIST_ID, categoryModel.getAddress());
        viewItemListBundle.putString(FirebaseAnalytics.Param.ITEM_LIST_NAME, categoryModel.getName());
        viewItemListBundle.putParcelableArrayList(FirebaseAnalytics.Param.ITEMS, (ArrayList<? extends Parcelable>) arrayBundle);

        mFirebaseAnalytics.logEvent(FirebaseAnalytics.Event.VIEW_ITEM_LIST, viewItemListBundle);

        //MainActivity mainActivity = new MainActivity();
        PLPListAdapter = new PLPListAdapter(this, menuList,
                this, this, this, this);
        recyclerView.setAdapter(PLPListAdapter);
    }

//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        // Inflate the menu; this adds items to the action bar if it is present.
//        getMenuInflater().inflate(R.menu.plp_menu, menu);
//        return true;
//    }


    @Override
    public void onAddToCartClick(ProductModel productModel) {
        if (itemsInCartList == null) {
//            itemsInCartList = new ArrayList<>();
        }
        itemsInCartList.add(productModel);
        totalItemInCart = 0;

        for (ProductModel m : itemsInCartList) {
            totalItemInCart = totalItemInCart + m.getTotalInCart();
        }
        //buttonCheckout.setText("Checkout (" +totalItemInCart +") items");
    }

    @Override
    public void onUpdateCartClick(ProductModel productModel) {
        if (itemsInCartList.contains(productModel)) {
            int index = itemsInCartList.indexOf(productModel);
            itemsInCartList.remove(index);
            itemsInCartList.add(index, productModel);

            totalItemInCart = 0;

            for (ProductModel m : itemsInCartList) {
                totalItemInCart = totalItemInCart + m.getTotalInCart();
            }
            //buttonCheckout.setText("Checkout (" +totalItemInCart +") items");
        }
    }

    @Override
    public void onRemoveFromCartClick(ProductModel productModel) {
        if (itemsInCartList.contains(productModel)) {
            itemsInCartList.remove(productModel);
            totalItemInCart = 0;

            for (ProductModel m : itemsInCartList) {
                totalItemInCart = totalItemInCart + m.getTotalInCart();
            }
            //buttonCheckout.setText("Checkout (" +totalItemInCart +") items");
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 1000 && resultCode == Activity.RESULT_OK) {
            //
            finish();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        Bundle screen_view = new Bundle();
//        screen_view.putString(FirebaseAnalytics.Param.SCREEN_NAME, screen_name); //e.g. Screen Name
//        Incorrect screen name of Product Listing Screen
        screen_view.putString(FirebaseAnalytics.Param.SCREEN_NAME, "Product Details Screen"); //e.g. Screen Name
        screen_view.putString(FirebaseAnalytics.Param.SCREEN_CLASS, this.getLocalClassName()); // You can pass the value as specific activity name over here and if not then you can ignore this line and it will take the value automtically
        mFirebaseAnalytics.logEvent(FirebaseAnalytics.Event.SCREEN_VIEW, screen_view);
    }

    @Override
    public void onProClick(Integer position) {

//        Missing ItemID in view_Item event
//        product.putString(FirebaseAnalytics.Param.ITEM_ID, menuList.get(position).getItem_id());
        product.putString(FirebaseAnalytics.Param.ITEM_NAME, menuList.get(position).getName());
        product.putString(FirebaseAnalytics.Param.ITEM_CATEGORY, categoryModel.getName());
        product.putString(FirebaseAnalytics.Param.ITEM_VARIANT, menuList.get(position).getVariant());
        product.putString(FirebaseAnalytics.Param.ITEM_BRAND, menuList.get(position).getBrand());
        product.putDouble(FirebaseAnalytics.Param.PRICE, menuList.get(position).getPrice());

        Bundle selectItemParams = new Bundle();

//        ITEM_LIST_ID and ITEM_LIST_NAME is passing static for all lists in select Item event
//        selectItemParams.putString(FirebaseAnalytics.Param.ITEM_LIST_ID, categoryModel.getAddress());
        selectItemParams.putString(FirebaseAnalytics.Param.ITEM_LIST_ID, categoryModel.getAddress());
        selectItemParams.putString(FirebaseAnalytics.Param.ITEM_LIST_NAME, categoryModel.getName());
//        selectItemParams.putString(FirebaseAnalytics.Param.ITEM_LIST_NAME, categoryModel.getName());
        selectItemParams.putParcelableArray(FirebaseAnalytics.Param.ITEMS,
                new Parcelable[]{product});
        mFirebaseAnalytics.logEvent(FirebaseAnalytics.Event.SELECT_ITEM, selectItemParams);

        Bundle viewItemParams = new Bundle();
        viewItemParams.putString(FirebaseAnalytics.Param.CURRENCY, "INR");
//        Missing Value in view_item in PLP
//        viewItemParams.putDouble(FirebaseAnalytics.Param.VALUE, menuList.get(position).getPrice());
        viewItemParams.putParcelableArray(FirebaseAnalytics.Param.ITEMS,
                new Parcelable[]{product,product});

//        item array is passign twice in VIEW_ITEM event
        mFirebaseAnalytics.logEvent(FirebaseAnalytics.Event.VIEW_ITEM, viewItemParams);
    }

    @Override
    public void onAddToCartProduct(Integer position) {
        product.putString(FirebaseAnalytics.Param.ITEM_ID, menuList.get(position).getItem_id());
        product.putString(FirebaseAnalytics.Param.ITEM_NAME, menuList.get(position).getName());
        product.putString(FirebaseAnalytics.Param.ITEM_CATEGORY, categoryModel.getName());
        product.putString(FirebaseAnalytics.Param.ITEM_VARIANT, menuList.get(position).getVariant());
        product.putString(FirebaseAnalytics.Param.ITEM_BRAND, menuList.get(position).getBrand());
        product.putDouble(FirebaseAnalytics.Param.PRICE, menuList.get(position).getPrice());

        Bundle itemJeggingsWishlist = new Bundle(product);
        itemJeggingsWishlist.putLong(FirebaseAnalytics.Param.QUANTITY, 1);

        Bundle addToWishlistParams = new Bundle();
//        addToWishlistParams.putString(FirebaseAnalytics.Param.CURRENCY, "INR");
//        Incorrect Currency value in add to cart and remove from cart event
        addToWishlistParams.putString(FirebaseAnalytics.Param.CURRENCY, "USD");

//        Missing Value parameter in ADD_TO_CART in PLP
//        addToWishlistParams.putDouble(FirebaseAnalytics.Param.VALUE, menuList.get(position).getPrice());
        addToWishlistParams.putParcelableArray(FirebaseAnalytics.Param.ITEMS,
                new Parcelable[]{itemJeggingsWishlist});

        mFirebaseAnalytics.logEvent(FirebaseAnalytics.Event.ADD_TO_CART, addToWishlistParams);
    }

    @Override
    public void onRemoveFromProduct(Integer position) {
        product.putString(FirebaseAnalytics.Param.ITEM_ID, menuList.get(position).getItem_id());
        product.putString(FirebaseAnalytics.Param.ITEM_NAME, menuList.get(position).getName());
        product.putString(FirebaseAnalytics.Param.ITEM_CATEGORY, categoryModel.getName());
        product.putString(FirebaseAnalytics.Param.ITEM_VARIANT, menuList.get(position).getVariant());
        product.putString(FirebaseAnalytics.Param.ITEM_BRAND, menuList.get(position).getBrand());
        product.putDouble(FirebaseAnalytics.Param.PRICE, menuList.get(position).getPrice());

        Bundle removeCartParams = new Bundle();
//        removeCartParams.putString(FirebaseAnalytics.Param.CURRENCY, "INR");
//        Incorrect Currency value in add to cart and remove from cart event
        removeCartParams.putString(FirebaseAnalytics.Param.CURRENCY, "USD");
//        removeCartParams.putDouble(FirebaseAnalytics.Param.VALUE, menuList.get(position).getPrice());

//        Constant value parameter is passing in remve from cart event in PLP
        removeCartParams.putDouble(FirebaseAnalytics.Param.VALUE, 999);
        removeCartParams.putParcelableArray(FirebaseAnalytics.Param.ITEMS,
                new Parcelable[]{product});

        mFirebaseAnalytics.logEvent(FirebaseAnalytics.Event.REMOVE_FROM_CART, removeCartParams);
    }


}

