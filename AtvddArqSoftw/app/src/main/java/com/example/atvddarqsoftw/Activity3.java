package com.example.atvddarqsoftw;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class Activity3 extends AppCompatActivity {

    private TextView msg;
    private static final String[] CONTACTS_PERMISSIONS = {
            Manifest.permission.READ_CONTACTS
    };
    private static final int CONTACTS_REQUEST = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity3);

        msg = findViewById(R.id.textView6);

        if(withPermission()){
            List<MyContact> contacts = ContactsHelper.getContacts(this);
            ArrayList<String> nomes = new ArrayList<>();
            if(contacts.size() >=3){
                for (MyContact contact : contacts) {
                    nomes.add(contact.getName());
                }
                access(nomes.get(2));
            } else {
                msg.setText("Não há três contatos salvos");
            }
        } else {
            requestPermissions(CONTACTS_PERMISSIONS, CONTACTS_REQUEST);
            noPermission();
        }
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
                if(contacts.size() >=3){
                    for (MyContact contact : contacts) {
                        nomes.add(contact.getName());
                    }
                    access(nomes.get(2));
                } else {
                    msg.setText("Não tem três contatos salvos");
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
        Intent intent = new Intent(this,MyIntentService.class);
        intent.putExtra("TELA","tela 3");
        startService(intent);
    }
}