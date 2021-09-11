package com.example.betterlife;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class Report extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report);
        Button btnPothole = findViewById(R.id.bt_report_pothole);
        Button btnGarbage = findViewById(R.id.bt_report_grabage);
        Button btnCorruption = findViewById(R.id.bt_report_corruption);
        Button btnOthers = findViewById(R.id.bt_report_other);

        btnPothole.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), Pothole.class);
                startActivity(intent);
            }
        });
    }
}