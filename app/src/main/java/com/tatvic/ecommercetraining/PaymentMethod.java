package com.tatvic.ecommercetraining;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Parcelable;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.tatvic.ecommercetraining.model.CategoryModel;
import com.tatvic.ecommercetraining.model.ProductModel;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Random;

public class PaymentMethod extends AppCompatActivity {

    private static final String screen_name = "Payment Method Screen";
    private static final String screen_name_popup = "Payment Creds Popup";
    private RadioGroup radioGroup;
    private RadioButton radioButton;
    private LinearLayout consentLL;
    private Button btn_pay;
    private CheckBox checkBox;
    private ProgressBar progressBar;
    SharedPreferences sharedPreferences;
    private TextView shipping_address, tvCartItems, test_creds;
    private AlertDialog.Builder dialog;
    private String cred_email, cred_password, user_entered_email, user_entered_pass, selectedPaymentMethod, order_details;
    private FirebaseAnalytics mFirebaseAnalytics;
    private List<Bundle> arrayBundle = new ArrayList<Bundle>();
    private List<ProductModel> plist;
    Float value;


    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment_method);

        CategoryModel categoryModel = getIntent().getParcelableExtra("RestaurantModel");
        mFirebaseAnalytics = FirebaseAnalytics.getInstance(this);
        plist = new ArrayList<ProductModel>();

        radioGroup = findViewById(R.id.radioGroup);
        consentLL = findViewById(R.id.consentLL);
        btn_pay = findViewById(R.id.btn_pay);
        checkBox = findViewById(R.id.checkbox);
        shipping_address = findViewById(R.id.shipping_address);
        tvCartItems = findViewById(R.id.tvCartItems);
//        progressBar = findViewById(R.id.progressBar);
//        test_creds = findViewById(R.id.test_creds);

        //sharedPreferences = getSharedPreferences("TotalPrice", MODE_PRIVATE);
        sharedPreferences = getSharedPreferences("MySharedPref", MODE_PRIVATE);
        Intent intent = getIntent();

        String name = intent.getStringExtra("name");
        String address = intent.getStringExtra("address");
        String city = intent.getStringExtra("city");
        String state = intent.getStringExtra("state");
        String zip = intent.getStringExtra("zip");
        value = sharedPreferences.getFloat("Value", 0);

//        List<Product> list = new ArrayList<>();
//        list = restaurantModel.getMenus();

        String final_address = name + "\n" + address + "\n" + city +", " + state + ", " + zip;
//        String final_items = restaurantModel.getName() + "\n" + restaurantModel.getAddress() + "\n" + restaurantModel.getName();
        shipping_address.setText(final_address);
        //tvCartItems.setText(final_items);

        btn_pay.setText("Pay " + String.valueOf(NumberFormat.getCurrencyInstance(new Locale("en", "IN")).format(value)));
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                consentLL.setVisibility(View.VISIBLE);
            }
        });

        paymentGateway();
    }


//    @Override
//    protected void onPause() {
//        super.onPause();
//        dialog.show().dismiss();
//    }

    private void paymentGateway() {

        btn_pay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if((radioGroup.getCheckedRadioButtonId() == -1)){
                    Snackbar.make(view, "Please select any payment method", Snackbar.LENGTH_LONG)
                            .setAction("OK", new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                }
                            }).show();
                }

                if (checkBox.isChecked()) {
                    int selectedId = radioGroup.getCheckedRadioButtonId();
                    if (selectedId != -1) {
                        // At least one radio button is selected
                        radioButton = findViewById(selectedId);
                        selectedPaymentMethod = radioButton.getText().toString();
                        // Use the selected value as needed
                    }  // N


                    Bundle addPaymentParams = new Bundle();
                    // addPaymentParams.putString(FirebaseAnalytics.Param.CURRENCY, "INR");
                    addPaymentParams.putString(FirebaseAnalytics.Param.CURRENCY, "INRR");
                    addPaymentParams.putDouble(FirebaseAnalytics.Param.VALUE, value);
                    addPaymentParams.putDouble(FirebaseAnalytics.Param.VALUE, value);
                    //addPaymentParams.putString(FirebaseAnalytics.Param.COUPON, "SUMMER_FUN");
                    addPaymentParams.putString(FirebaseAnalytics.Param.PAYMENT_TYPE, selectedPaymentMethod);

                    arrayBundle.clear();

                    for (int i = 0; i < ProductListing.itemsInCartList.size(); i++) {
                        plist.add(ProductListing.itemsInCartList.get(i));
                        Bundle item_bundle = new Bundle();

                        item_bundle.putString(FirebaseAnalytics.Param.ITEM_ID, plist.get(i).getItem_id());
                        item_bundle.putString(FirebaseAnalytics.Param.ITEM_ID, plist.get(i).getBrand());

                        item_bundle.putString(FirebaseAnalytics.Param.ITEM_NAME, plist.get(i).getName());
                        item_bundle.putString(FirebaseAnalytics.Param.ITEM_CATEGORY, plist.get(i).getItem_category());
                        item_bundle.putString(FirebaseAnalytics.Param.ITEM_CATEGORY3, plist.get(i).getItem_category());
                        item_bundle.putString(FirebaseAnalytics.Param.ITEM_VARIANT, plist.get(i).getVariant());
                        item_bundle.putString(FirebaseAnalytics.Param.ITEM_BRAND, plist.get(i).getBrand());
                        item_bundle.putDouble(FirebaseAnalytics.Param.PRICE, 0.00);


                        arrayBundle.add(item_bundle);

                        order_details  = i + " ) " + plist.get(i).getName();

                        addPaymentParams.putParcelableArrayList(FirebaseAnalytics.Param.ITEMS,
                                (ArrayList<? extends Parcelable>) arrayBundle);

                        Log.d("OrderDetails", order_details);
                    }

                    //tvCartItems.setText(order_details);
                    mFirebaseAnalytics.logEvent(FirebaseAnalytics.Event.ADD_PAYMENT_INFO, addPaymentParams);

                    cred_email = "test@tatvic.com";
                    cred_password = "Tatvic@12345";

                    dialog = new AlertDialog.Builder(PaymentMethod.this);
                    View mView = PaymentMethod.this.getLayoutInflater().inflate(R.layout.payment_custom_dialog, null);

                    test_creds = mView.findViewById(R.id.test_creds);
                    progressBar = mView.findViewById(R.id.progressBar);

                    EditText cred_et_email = (EditText) mView.findViewById(R.id.cred_email);
                    EditText cred_et_pass = (EditText) mView.findViewById(R.id.cred_pass);

                    test_creds.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {


                            cred_et_email.setText(cred_email);
                            cred_et_pass.setText(cred_password);
                        }
                    });
                    dialog.setView(mView);


                    Button btn_pay_now = (Button) mView.findViewById(R.id.btn_pay_now);


                    final AlertDialog alertDialog = dialog.create();
                    alertDialog.setCanceledOnTouchOutside(false);

                  /* test_creds.setOnClickListener(new View.OnClickListener() {
                       @Override
                       public void onClick(View view) {
                           cred_et_email.setText(cred_email);
                           cred_et_pass.setText(cred_password);
                       }
                   });*/

                    btn_pay_now.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {

                            user_entered_email = cred_et_email.getText().toString();
                            user_entered_pass = cred_et_pass.getText().toString();
//                            Toast.makeText(PaymentMethod.this, "Text is" + user_entered_email, Toast.LENGTH_SHORT).show();
                            if (cred_email.equals(user_entered_email) && cred_password.equals(user_entered_pass)) {
                                progressBar.setVisibility(View.VISIBLE);
                                progressBar.setIndeterminate(true);

                                new Handler().postDelayed(new Runnable() {
                                    @Override
                                    public void run() {
                                        Intent intent = new Intent(PaymentMethod.this, Thankyou.class);
                                        startActivity(intent);
                                    }
                                }, 1500);


                                Random random = new Random();
                                long randomNumber = 1000000000L + (long)(random.nextDouble() * (9999999999L - 1000000000L));

                                Bundle purchaseParams = new Bundle();
                                purchaseParams.putString(FirebaseAnalytics.Param.TRANSACTION_ID, "T_" + randomNumber);
                                purchaseParams.putString(FirebaseAnalytics.Param.AFFILIATION, "Ecommerce Store");
                                purchaseParams.putString(FirebaseAnalytics.Param.CURRENCY, "INR");
                                purchaseParams.putDouble(FirebaseAnalytics.Param.VALUE, value);
                                purchaseParams.putDouble(FirebaseAnalytics.Param.TAX, 0);
                                purchaseParams.putDouble(FirebaseAnalytics.Param.SHIPPING, 0);
                                purchaseParams.putString(FirebaseAnalytics.Param.COUPON, "SUMMER_FUN");

                                arrayBundle.clear();

                                //After code changes for version 3
                                for (int i = 0; i < ProductListing.itemsInCartList.size(); i++) {
                                    plist.add(ProductListing.itemsInCartList.get(i));
                                    Bundle item_bundle = new Bundle();

                                    item_bundle.putString(FirebaseAnalytics.Param.ITEM_ID, "Laptop");
                                    //item_bundle.putString(FirebaseAnalytics.Param.ITEM_NAME, plist.get(i).getName());
                                    //item_bundle.putString(FirebaseAnalytics.Param.ITEM_CATEGORY, plist.get(i).getItem_category());
                                    item_bundle.putString(FirebaseAnalytics.Param.ITEM_CATEGORY, "Acer I Series 108 cm (43 inch) 4K Ultra HD LED Android TV with Google Assistant (2022 model)Acer I Series 108 cm (43 inch) 4K Ultra HD LED Android TV with Google Assistant (2022 model)");
                                    item_bundle.putString(FirebaseAnalytics.Param.ITEM_CATEGORY2, plist.get(i).getItem_category());
                                    item_bundle.putString(FirebaseAnalytics.Param.ITEM_CATEGORY3, plist.get(i).getItem_id());
                                    item_bundle.putString(FirebaseAnalytics.Param.ITEM_VARIANT, "");
                                    item_bundle.putString(FirebaseAnalytics.Param.ITEM_VARIANT, plist.get(i).getVariant());
                                    item_bundle.putString(FirebaseAnalytics.Param.ITEM_BRAND, plist.get(i).getBrand());
                                    item_bundle.putString(FirebaseAnalytics.Param.ITEM_BRAND, "");
                                    item_bundle.putDouble(FirebaseAnalytics.Param.PRICE, 0.00);

                                    arrayBundle.add(item_bundle);

                                    purchaseParams.putParcelableArrayList(FirebaseAnalytics.Param.ITEMS,
                                            (ArrayList<? extends Parcelable>) arrayBundle);
                                }
                                mFirebaseAnalytics.logEvent(FirebaseAnalytics.Event.PURCHASE, purchaseParams);

                            } else {
                                if (user_entered_email.isEmpty() || user_entered_pass.isEmpty()){
                                    Snackbar.make(view, "Please enter valid credentials", Snackbar.LENGTH_LONG)
                                            .setAction("OK", new View.OnClickListener() {
                                                @Override
                                                public void onClick(View view) {
                                                }
                                            }).show();
                                }else {
                                    progressBar.setVisibility(View.VISIBLE);
                                    progressBar.setIndeterminate(true);

                                    new Handler().postDelayed(new Runnable() {
                                        @Override
                                        public void run() {
                                            Intent intent = new Intent(PaymentMethod.this, Sorry.class);
                                            startActivity(intent);
                                        }
                                    }, 1500);

                                }

                            }
                        }
                    });
                    dialog.show();
                    Bundle screen_view = new Bundle();
                    screen_view.putString(FirebaseAnalytics.Param.SCREEN_NAME, screen_name_popup); //e.g. Screen Name
                    screen_view.putString(FirebaseAnalytics.Param.SCREEN_CLASS, "Payment Popup"); // You can pass the value as specific activity name over here and if not then you can ignore this line and it will take the value automtically
                    mFirebaseAnalytics.logEvent(FirebaseAnalytics.Event.SCREEN_VIEW, screen_view);

                    //--------GA3 Screen view---------
                    Bundle bundle = new Bundle();
                    bundle.putString(FirebaseAnalytics.Param.SCREEN_NAME, screen_name_popup);// Pass the Screen name
                    mFirebaseAnalytics.logEvent("screenview_manual", bundle);
                }
                else if(!(radioGroup.getCheckedRadioButtonId() == -1) && !checkBox.isChecked()){
                    Snackbar.make(view, "Please check the T & C", Snackbar.LENGTH_LONG)
                            .setAction("OK", new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                }
                            }).show();
                }
            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
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


