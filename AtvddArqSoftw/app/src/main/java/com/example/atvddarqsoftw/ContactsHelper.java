package com.example.atvddarqsoftw;

import android.annotation.SuppressLint;
import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.provider.ContactsContract;

import java.util.ArrayList;
import java.util.List;

public class ContactsHelper {
    public static List<MyContact> getContacts(Context context){
        List<MyContact> contacts = new ArrayList<MyContact>();
        ContentResolver contentResolver = context.getContentResolver();
        if(contentResolver != null){
            Cursor cursor = contentResolver.query(
                    ContactsContract.Contacts.CONTENT_URI, null,null,null,null
            );
            if((cursor != null ? cursor.getCount(): 0) > 0){
                while (cursor.moveToNext()){
                    @SuppressLint("Range")
                    String id = cursor.getString(cursor.getColumnIndex(
                            ContactsContract.Contacts._ID
                    ));

                    @SuppressLint("Range")
                    String name = cursor.getString(cursor.getColumnIndex(
                            ContactsContract.Contacts.DISPLAY_NAME
                    ));

                    MyContact contact = new MyContact(Integer.parseInt(id), name);
                    contacts.add(contact);
                }
            }
            if (cursor != null){
                cursor.close();
            }
        }
        return contacts;
    }

}
