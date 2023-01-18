package com.tatvic.ecommercetraining;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.VideoView;


public class Sorry extends AppCompatActivity {

    private VideoView sorry_iv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sorry);

        sorry_iv = findViewById(R.id.sorry_image);

        sorry_iv.setVideoPath("https://cdnl.iconscout.com/lottie/premium/preview-watermark/payment-declined-4567537-3810711.mp4");
        sorry_iv.start();

    }
}