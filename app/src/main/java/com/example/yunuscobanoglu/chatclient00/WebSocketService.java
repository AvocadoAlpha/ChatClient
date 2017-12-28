package com.example.yunuscobanoglu.chatclient00;


import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.IBinder;

import java.net.URI;

import org.java_websocket.client.WebSocketClient;
import org.java_websocket.drafts.Draft;
import org.java_websocket.drafts.Draft_6455;
import org.java_websocket.handshake.ServerHandshake;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import java.net.URI;

/**
 * Created by YunusCobanoglu on 28.12.17.
 */

public class WebSocketService extends Service {

    WebSocketServiceClient webSocket;
    BroadcastReceiver sendJson;


    @Override
    public void onCreate() {
        super.onCreate();
        try {
            URI uri = new URI("http://192.168.1.45:8080/chat");
            this.webSocket = new WebSocketServiceClient(uri, new Draft_6455(), getApplicationContext());
            this.webSocket.connect();
            this.generateBroadcastReceiver();

            IntentFilter sendJson = new IntentFilter("sendJson");
            registerReceiver(this.sendJson,sendJson );


        } catch (Exception e) {
            System.out.println("Fehler im Aufbau des WebSockets");
        }
    }
    private void generateBroadcastReceiver() {
        this.sendJson = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                //handle the broadcast event here
                String msg = intent.getStringExtra("json");


                JSONObject start = new JSONObject();
                try {
                    start.put("txtmessage",msg);


                    String jsonString = start.toString();
                    webSocket.send(jsonString);
                } catch (Exception e) {
                    System.out.println("Fehler beim Senden");
                }



            }
        };

    }
    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }
}
class WebSocketServiceClient extends WebSocketClient {
    String username;
    String txtmessage;
    Context context;

    public WebSocketServiceClient(URI serverUri, Draft draft, Context context) {
        super(serverUri, draft);
        this.context = context;

    }
    @Override
    public void onOpen(ServerHandshake handshakedata) {
        System.out.println("Verbindung hergestellt.");
    }
    @Override
    public void onClose(int code, String reason, boolean remote) {
        this.close();
    }
    @Override
    public void onError(Exception ex) {
        this.close();
        System.out.println(ex.getMessage());
        System.out.println("Verbindungsfehler");
    }
    @Override
    public void onMessage(String message) {
        System.out.println("Message bekommen");
        try {
            JSONParser jsonParser = new JSONParser();
            Object obj = jsonParser.parse(message);
            JSONObject jsonObject = (JSONObject) obj;
            System.out.println(jsonObject.toJSONString());
            if (jsonObject.get("txtmessage") != null) {
                String chatmsg=(String)jsonObject.get("txtmessage");
                Intent intent =new Intent();
                intent.setAction("message_arrived");
                intent.putExtra("Message",chatmsg);
                context.sendBroadcast(intent);

            } else {
                //@TODO Was tun wir, wenn json kein cmd hat?
            }


        } catch (Exception e) {
            System.out.println("Fehler bei JSON Parsing");
        }
    }
}

