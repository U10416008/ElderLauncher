package com.example.dingjie.elderlauncher;

import android.Manifest;
import android.app.AlertDialog;
import android.content.ContentResolver;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by dingjie on 2018/3/15.
 */

public class ContactActivity extends AppCompatActivity {
    RecyclerView listView;
    ContactsAdapter adapter;
    ArrayList<Contacts> contacts_list = new ArrayList<>();
    final int MY_READ_CONTACT = 3;
    final int MY_CALL_PHONE = 4;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.contact);
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.READ_CONTACTS) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.READ_CONTACTS},
                    MY_READ_CONTACT);
        }
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.CALL_PHONE},
                    MY_CALL_PHONE);
        }
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.READ_CONTACTS) == PackageManager.PERMISSION_GRANTED&&
                ContextCompat.checkSelfPermission(this,
                        Manifest.permission.CALL_PHONE) == PackageManager.PERMISSION_GRANTED) {
            Log.d("permission","pass");
            createContacts();
        }



    }
    public void createContacts(){
        listView = findViewById(R.id.recycle);
        listView.setHasFixedSize(true);
        GridLayoutManager layoutManager = new GridLayoutManager(this, 2);
        listView.setLayoutManager(layoutManager);
        contact();
        adapter = new ContactsAdapter(contacts_list);
        listView.setAdapter(adapter);
        adapter.setOnRecyclerViewListener(new ContactsAdapter.OnRecyclerViewListener(){
            @Override
            public void onItemClick(View view , int position){
                Contacts callContacts = contacts_list.get(position);
            }

            @Override
            public boolean onItemLongClick(int position) {
                return false;
            }
        });
    }
    public void callAlert(String name,final String number){
        new AlertDialog.Builder(this)
                .setMessage("Call "+name+" ?")
                .setPositiveButton("CALL", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface arg0, int arg1) {
                        //showToast("正在撥給"+number);
                        Intent call = new Intent("android.intent.action.CALL", Uri.parse("tel:" + number));
                        startActivity(call);
                    }

                })
                .setNegativeButton("BACK",null)
                .show();
    }
    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {
            case MY_READ_CONTACT:{
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    if (ContextCompat.checkSelfPermission(this,
                            Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                        ActivityCompat.requestPermissions(this,
                                new String[]{Manifest.permission.CALL_PHONE},
                                MY_CALL_PHONE);
                    }
                    // permission was granted, yay! Do the
                    // contacts-related task you need to do.

                }
                return;
            }
            case MY_CALL_PHONE:{
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    if (ContextCompat.checkSelfPermission(this,
                            Manifest.permission.READ_CONTACTS) == PackageManager.PERMISSION_GRANTED&&
                            ContextCompat.checkSelfPermission(this,
                                    Manifest.permission.CALL_PHONE) == PackageManager.PERMISSION_GRANTED) {
                        createContacts();
                    }

                    // permission was granted, yay! Do the
                    // contacts-related task you need to do.

                }
                return;
            }
        }
    }
    public void contact() {


        ContentResolver resolver = getContentResolver();
        Cursor cursor = resolver.query(
                ContactsContract.Contacts.CONTENT_URI,
                null,
                null,
                null,
                null );
        while(cursor.moveToNext()) {
            String[] phoneProjection = new String[] {ContactsContract.CommonDataKinds.Phone.NUMBER};

            int id = cursor.getInt(cursor.getColumnIndex(ContactsContract.Contacts._ID));
            Cursor phonesCusor = this.getContentResolver().query(
                    ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                    phoneProjection,
                    ContactsContract.CommonDataKinds.Phone.CONTACT_ID + "=" + id,
                    null,
                    null);
            String number = "";
            int i = 1;
            if (phonesCusor != null && phonesCusor.moveToFirst()) {
                do {
                    String num = phonesCusor.getString(0);
                    number = number + String.valueOf(i++) + "." + num + "   ";

                }while (phonesCusor.moveToNext());
            }


            String name = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME));
            contacts_list.add(new Contacts(name , number));
            //String number = cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
            //Log.d("RECORD", id + "/" + name + "/" + number);
        }
        for(int i = 0 ; i < contacts_list.size() ;i++){
            Log.d("RECORD", i + "/" + contacts_list.get(i).getName() + "/" + contacts_list.get(i).getNumber());
        }
    }
}
