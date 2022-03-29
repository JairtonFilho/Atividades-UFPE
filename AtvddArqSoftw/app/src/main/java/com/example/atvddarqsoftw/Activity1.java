package com.example.atvddarqsoftw;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.net.wifi.WifiManager;
import android.util.Log;
import android.widget.Button;

import android.os.Bundle;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class Activity1 extends AppCompatActivity {

    private MyBroadcastReceiver receiver;

    private  Button bt1;
    private  TextView msg;
    private static final String[] CONTACTS_PERMISSIONS = {
            Manifest.permission.READ_CONTACTS
    };
    private static final int CONTACTS_REQUEST = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity1);

        bt1 = findViewById(R.id.button);
        msg = findViewById(R.id.textView4);

        if(withPermission()){
            List<MyContact> contacts = ContactsHelper.getContacts(this);
            ArrayList<String> nomes = new ArrayList<>();
            if(contacts.size() >=1){
                for (MyContact contact : contacts) {
                    nomes.add(contact.getName());
                }
                access(nomes.get(0));
            } else {
                msg.setText("Não há contatos salvos.");
            }
        } else {
            requestPermissions(CONTACTS_PERMISSIONS, CONTACTS_REQUEST);
            noPermission();
        }

        bt1.setOnClickListener(v->{
            Intent in = new Intent(this, Activity2.class);
            startActivity(in);
        });
    }

    @Override
    public void onRequestPermissionsResult(
            int requestCode,
            @NonNull String[] permissions,
            @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if(requestCode == CONTACTS_REQUEST){
            if(grantResults[0] == PackageManager.PERMISSION_GRANTED){
                List<MyContact> contacts = ContactsHelper.getContacts(this);
                ArrayList<String> nomes = new ArrayList<>();
                if(contacts.size() >=1){
                    for (MyContact contact : contacts) {
                        nomes.add(contact.getName());
                    }
                    access(nomes.get(0));
                } else {
                    msg.setText("Não há contatos salvos.");
                }
            } else {
                msg.setText("Sem permissão para contatos.");
            }
        }
    }

    private boolean withPermission(){
        return checkSelfPermission(Manifest.permission.READ_CONTACTS
        ) == PackageManager.PERMISSION_GRANTED;
    }

    private void noPermission(){
        msg.setText("Não é possível completar a operação.");
    }

    private void access(String resultadoID){

        msg.setText(resultadoID);
    }

    @Override
    protected void onStart() {
        super.onStart();
        receiver = new MyBroadcastReceiver();
        IntentFilter filter = new IntentFilter(WifiManager.WIFI_STATE_CHANGED_ACTION);
        registerReceiver(receiver,filter);

        Intent intent = new Intent(this,MyIntentService.class);
        intent.putExtra("TELA","tela 3");
        startService(intent);
    }

    @Override
    protected void onStop() {
        super.onStop();
        if(receiver != null){
            unregisterReceiver(receiver);
            receiver = null ;
        }
    }

    class MyBroadcastReceiver extends BroadcastReceiver{
        @Override
        public void onReceive(Context context, Intent intent) {
            Log.d("SSF", "Status do Wi-Fi Mudou.");
        }
    }
}