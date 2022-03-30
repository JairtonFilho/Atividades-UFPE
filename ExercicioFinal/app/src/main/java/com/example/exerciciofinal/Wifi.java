package com.example.exerciciofinal;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.widget.TextView;

public class Wifi extends AppCompatActivity {

    private MyBroadcastReceiver receiver;
    private static TextView wifi;

    @SuppressLint("StaticFieldLeak")

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wifi);

        wifi = findViewById(R.id.textView2);

        Intent intent = new Intent(this,MyIntentService.class);
        startService(intent);
    }

    @Override
    protected void onStart() {
        super.onStart();
        receiver = new MyBroadcastReceiver();
        IntentFilter filter = new IntentFilter(WifiManager.WIFI_STATE_CHANGED_ACTION);
        registerReceiver(receiver,filter);
    }

    @Override
    protected void onStop() {
        super.onStop();
        receiver = new MyBroadcastReceiver();
        IntentFilter filter = new IntentFilter(WifiManager.WIFI_STATE_CHANGED_ACTION);
        registerReceiver(receiver, filter);
    }

    class MyBroadcastReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (wifi()) {
                wifi.setText("Habilitado");
            } else {
                wifi.setText("Desabilitado");
            }

        }
    }

    public boolean wifi() {
        WifiManager wifiManager = (WifiManager) getApplicationContext().getSystemService(Context.WIFI_SERVICE);
        if (wifiManager.isWifiEnabled()) {
            return true;
        } else {
            return false;
        }
    }
}