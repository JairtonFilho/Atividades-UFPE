package com.example.exerciciofinal;

import androidx.appcompat.app.AppCompatActivity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.BatteryManager;
import android.os.Build;
import android.os.Bundle;
import android.widget.TextView;

public class Battery extends AppCompatActivity {

    private BatteryReceiver receiver;
    private TextView charge;
    private TextView time;
    private TextView batteryHealth;

    public long timeRemaning;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_battery);

        charge = findViewById(R.id.textView9);
        time = findViewById(R.id.textView11);
        batteryHealth = findViewById(R.id.textView10);

        IntentFilter filter = new IntentFilter(Intent.ACTION_BATTERY_CHANGED);
        registerReceiver(receiver, filter);

        Intent intent = new Intent(this,MyIntentService.class);
        startService(intent);

    }

    @Override
    protected void onStart() {
        super.onStart();
        receiver = new BatteryReceiver();
        IntentFilter filter = new IntentFilter(Intent.ACTION_BATTERY_CHANGED);
        registerReceiver(receiver,filter);
    }

    @Override
    protected void onStop() {
        super.onStop();
        receiver = new BatteryReceiver();

    }

    class BatteryReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            int level = intent.getIntExtra(BatteryManager.EXTRA_LEVEL, -1);
            int scale = intent.getIntExtra(BatteryManager.EXTRA_SCALE, -1);
            int health = intent.getIntExtra(BatteryManager.EXTRA_HEALTH, 0);
            int plugged = intent.getIntExtra(BatteryManager.EXTRA_PLUGGED, 0);

            BatteryManager bateryManager = (BatteryManager) getApplicationContext().getSystemService(Context.BATTERY_SERVICE);

            float batteryPercent = level * 100 / (float)scale;
            charge.setText(batteryPercent + "%");

            if (plugged == 0) {
                time.setText("Dispositivo não está carregando" );
            } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P){
                timeRemaning = bateryManager.computeChargeTimeRemaining()/60000;
                time.setText("Faltam " + timeRemaning + " minutos" );
            }

            if (health == BatteryManager.BATTERY_HEALTH_COLD) {
                batteryHealth.setText("COLD");
            }
            if (health == BatteryManager.BATTERY_HEALTH_DEAD) {
                batteryHealth.setText("DEAD");
            }
            if (health == BatteryManager.BATTERY_HEALTH_GOOD) {
                batteryHealth.setText("GOOD");
            }
            if (health == BatteryManager.BATTERY_HEALTH_OVERHEAT) {
                batteryHealth.setText("OVERHEAT");
            }
            if (health == BatteryManager.BATTERY_HEALTH_OVER_VOLTAGE) {
                batteryHealth.setText("OVERVOLTAGE");
            }
        }
    };

}

