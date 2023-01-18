package com.tatvic.ecommercetraining;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.VideoView;

public class Thankyou extends AppCompatActivity {

    private VideoView iv;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_thankyou);

        iv = findViewById(R.id.thank_you_image);

        iv.setVideoPath("https://cdnl.iconscout.com/lottie/premium/preview-watermark/payment-success-5463788-4573683.mp4");
        iv.start();
    }
}