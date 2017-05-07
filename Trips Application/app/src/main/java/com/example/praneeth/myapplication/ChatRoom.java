package com.example.praneeth.myapplication;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Prateek on 27-04-2017.
 */

public class ChatRoom implements Parcelable {
    protected ChatRoom(Parcel in) {
    }

    public static final Creator<ChatRoom> CREATOR = new Creator<ChatRoom>() {
        @Override
        public ChatRoom createFromParcel(Parcel in) {
            return new ChatRoom(in);
        }

        @Override
        public ChatRoom[] newArray(int size) {
            return new ChatRoom[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
    }
}
