package com.example.yunuscobanoglu.chatclient00;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    //Username wird eingegeben und ChatActivity gestartet. Username wird mit getter dann in ChatActivity benutzt

    private static String username;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        final EditText usrname=findViewById(R.id.editText0);
        TextView info=findViewById(R.id.textView2);
        Button start =findViewById(R.id.button);


        start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                username = usrname.getText().toString();

                Intent intent0 = new Intent(MainActivity.this, ChatActivity.class);
                startActivity(intent0);
            }
        });
    }

        public static String getUsername(){ return username;}
}
