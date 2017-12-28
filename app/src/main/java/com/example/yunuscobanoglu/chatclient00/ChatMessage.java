package com.example.yunuscobanoglu.chatclient00;

import android.os.Parcelable;
import android.os.Parcel;


import org.json.simple.JSONObject;
import org.json.simple.parser.ParseException;

/**
 * Created by YunusCobanoglu on 28.12.17.
 */
//Analog wie MusikJukeBox jedoch wirds nirgendswo benutzt .... ka

public class ChatMessage implements Parcelable{
    String txtmessage;

    public ChatMessage(String txtmessage){
        // this.username=username;
        this.txtmessage=txtmessage;
    }
    public ChatMessage(JSONObject chatjson) throws ParseException {
        // this.username=(String) chatjson.get("username");
        this.txtmessage=(String) chatjson.get("txtmessage");


    }
    protected ChatMessage(Parcel in) {
        txtmessage=in.readString();

    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(txtmessage);

    }
    // public String getUsername(){return username;}

    public String getTxtmessage(){ return txtmessage;}

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<ChatMessage> CREATOR = new Creator <ChatMessage>() {
        @Override
        public ChatMessage createFromParcel(Parcel in) {
            return new ChatMessage(in);
        }
        @Override
        public ChatMessage[] newArray(int size) {
            return new ChatMessage[size];
        }
    };



}

