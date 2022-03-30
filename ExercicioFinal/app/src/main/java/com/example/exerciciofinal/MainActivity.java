package com.example.exerciciofinal;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final Button btnAplications = findViewById(R.id.button2);
        final Button btnBattery = findViewById(R.id.button3);
        final Button btnWifi = findViewById(R.id.button4);


        btnAplications.setOnClickListener(
                v -> startActivity(new Intent(this, Aplications.class))
        );

        btnBattery.setOnClickListener(
                v -> startActivity(new Intent(this, Battery.class))
        );

        btnWifi.setOnClickListener(
                v -> startActivity(new Intent(this, Wifi.class))
        );
    }

    @Override
    protected void onStart() {
        super.onStart();
        Intent intent = new Intent(this,MyIntentService.class);
        startService(intent);
    }
}