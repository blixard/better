package com.example.betterlife;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class ModelSelection extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_model_selection);

        Button btnPothole = findViewById(R.id.btn_models_pothole);
        Button btnGarbage = findViewById(R.id.btn_models_garbage);

        btnPothole.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext() , PotholeTest.class);
                startActivity(intent);
            }
        });

        btnGarbage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext() , GarbageTest.class);
                startActivity(intent);
            }
        });
    }
}