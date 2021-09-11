package com.example.betterlife;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;

public class splashscreen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,1024);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splashscreen);
        Animation ttb = AnimationUtils.loadAnimation(this, R.anim.bottom_to_top);
        TextView tvTitle = findViewById(R.id.tv_ss_title);
        tvTitle.setAnimation(ttb);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
                finish();
            }
        }, 3200);
    }
}