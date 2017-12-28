package com.example.yunuscobanoglu.chatclient00;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

/**
 * Created by YunusCobanoglu on 28.12.17.
 */

public class ChatActivity extends AppCompatActivity {

    private  String username = MainActivity.getUsername();
    private String msg;
    BroadcastReceiver updateChat;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.acitivty_chat);
        Log.i("ChatActivitiy","ChatActivityStarted");

        final TextView chatfenster =findViewById(R.id.chatView);
        final EditText input=findViewById(R.id.editmessage);
        Button enter=findViewById(R.id.enter);

        enter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                msg=input.getText().toString();
                Intent sentit = new Intent();
                sentit.putExtra("json", username+msg);
                sentit.setAction("sendjson");
                getApplicationContext().sendBroadcast(sentit);
                input.setText("");

            }
        });

        Intent intent = new Intent(ChatActivity.this, WebSocketService.class);
        startService(intent);

        IntentFilter filter = new IntentFilter("message_arrived");
        registerReceiver(this.updateChat, filter);

        this.updateChat = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                //handle the broadcast event here
                String msg = intent.getStringExtra("Message");
                chatfenster.append(msg +"\n");
            }
        };
    }
}
