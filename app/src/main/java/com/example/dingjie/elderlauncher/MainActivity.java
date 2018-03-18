package com.example.dingjie.elderlauncher;

import android.content.Intent;
import android.content.res.Resources;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import java.net.Socket;


public class MainActivity extends AppCompatActivity {

    final Client client= new Client("192.168.0.112", 1234);
    Button button;
    ImageView more ;
    ImageView contact ;
    @Override
    protected void onCreate(Bundle savedIntanceState){
        super.onCreate(savedIntanceState);
        setContentView(R.layout.activity_main);
        button = findViewById(R.id.button);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
        initMore();
        initContacts();
        initServer();

    }
    public static int getScreenWidth() {
        return Resources.getSystem().getDisplayMetrics().widthPixels;
    }

    public static int getScreenHeight() {
        return Resources.getSystem().getDisplayMetrics().heightPixels;
    }
    public void initContacts(){
        contact = findViewById(R.id.contacts);
        contact.getLayoutParams().height = getScreenHeight()/4;
        contact.getLayoutParams().width = getScreenWidth()/4;
        contact.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setClass(MainActivity.this, ContactActivity.class);
                startActivity(intent);
            }
        });
        contact.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                return false;
            }
        });
    }
    public void initMore(){
        more = findViewById(R.id.more);
        more.getLayoutParams().height = getScreenHeight()/4;
        more.getLayoutParams().width = getScreenWidth()/4;
        more.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setClass(MainActivity.this, AppList.class);
                startActivity(intent);
            }
        });
        more.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                return false;
            }
        });
    }
    public void initServer(){
        client.setClientCallback(new Client.ClientCallback () {
            @Override
            public void onMessage(String message) {
            }

            @Override
            public void onConnect(Socket socket) {

                client.send("Hello World!\n");
                client.send("0910832632");
                //client.disconnect();
            }

            @Override
            public void onDisconnect(Socket socket, String message) {
            }

            @Override
            public void onConnectError(Socket socket, String message) {
            }
        });

        client.connect();
    }
}
