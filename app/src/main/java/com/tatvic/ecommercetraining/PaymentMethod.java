package com.tatvic.ecommercetraining;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.tatvic.ecommercetraining.model.Product;
import com.tatvic.ecommercetraining.model.RestaurantModel;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

public class PaymentMethod extends AppCompatActivity {

    private RadioGroup radioGroup;
    private LinearLayout consentLL;
    private Button btn_pay;
    private CheckBox checkBox;
    private TextView shipping_address, tvCartItems;
    private AlertDialog.Builder dialog;
    private String cred_email, cred_password, user_entered_email, user_entered_pass;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment_method);

        RestaurantModel restaurantModel = getIntent().getParcelableExtra("RestaurantModel");
        radioGroup = findViewById(R.id.radioGroup);
        consentLL = findViewById(R.id.consentLL);
        btn_pay = findViewById(R.id.btn_pay);
        checkBox = findViewById(R.id.checkbox);
        shipping_address = findViewById(R.id.shipping_address);
        tvCartItems = findViewById(R.id.tvCartItems);

        Intent intent = getIntent();

        String name = intent.getStringExtra("name");
        String address = intent.getStringExtra("address");
        String city = intent.getStringExtra("city");
        String state = intent.getStringExtra("state");
        String zip = intent.getStringExtra("zip");

//        List<Product> list = new ArrayList<>();
//        list = restaurantModel.getMenus();

        String final_address = name + "\n" + address + "\n" + city +", " + state + ", " + zip;
//        String final_items = restaurantModel.getName() + "\n" + restaurantModel.getAddress() + "\n" + restaurantModel.getName();
        shipping_address.setText(final_address);
        //tvCartItems.setText(final_items);
        btn_pay.setText(intent.getStringExtra("Total"));
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
                    Toast.makeText(PaymentMethod.this, "Please select any payment method", Toast.LENGTH_SHORT).show();
                }

                if (checkBox.isChecked()) {

                    cred_email = "test@tatvic.com";
                    cred_password = "Tatvic@12345";

                    dialog = new AlertDialog.Builder(PaymentMethod.this);
                    View mView = PaymentMethod.this.getLayoutInflater().inflate(R.layout.custom_dialog, null);
                    dialog.setView(mView);

                    EditText cred_et_email = (EditText) mView.findViewById(R.id.cred_email);
                    EditText cred_et_pass = (EditText) mView.findViewById(R.id.cred_pass);
                    Button btn_pay_now = (Button) mView.findViewById(R.id.btn_pay_now);


                    final AlertDialog alertDialog = dialog.create();
                    alertDialog.setCanceledOnTouchOutside(false);

                    btn_pay_now.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            user_entered_email = cred_et_email.getText().toString();
                            user_entered_pass = cred_et_pass.getText().toString();
//                            Toast.makeText(PaymentMethod.this, "Text is" + user_entered_email, Toast.LENGTH_SHORT).show();
                            if (cred_email.equals(user_entered_email) && cred_password.equals(user_entered_pass)) {
                                Intent intent = new Intent(PaymentMethod.this, Thankyou.class);
                                startActivity(intent);
                            } else {
                                if (user_entered_email.isEmpty() || user_entered_pass.isEmpty()){
                                    Toast.makeText(PaymentMethod.this, "Please enter valid credentials", Toast.LENGTH_SHORT).show();
                                }else {
                                    startActivity(new Intent(PaymentMethod.this, Sorry.class));
                                }

                            }
                        }
                    });
                    dialog.show();
                }
                else if(!(radioGroup.getCheckedRadioButtonId() == -1) && !checkBox.isChecked()){
                    Toast.makeText(PaymentMethod.this, "Please check the T & C", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}