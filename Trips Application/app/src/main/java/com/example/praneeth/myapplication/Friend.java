package com.example.praneeth.myapplication;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Praneeth on 4/21/2017.
 */

public class Friend extends User implements Parcelable {
    String friendAddedDate;

    public Friend(User user, String friendAddedDate) {
        super(user.getEmail(), user.getFirstname(), user.getLastname(), user.getProfilepicUri(), user.getUserKey(), user.getGender(), user.getStatus());
        this.friendAddedDate = friendAddedDate;
    }

    public Friend() {}

    public String getFriendAddedDate() {
        return friendAddedDate;
    }

    public void setFriendAddedDate(String friendAddedDate) {
        this.friendAddedDate = friendAddedDate;
    }

    @Override
    public String toString() {
        return "Friend{" +
                "friendAddedDate='" + friendAddedDate + '\'' +
                '}';
    }
    public Map<String, Object> toMap(){
        HashMap<String, Object> result = new HashMap<>();
        result.put("firstname", firstname);
        result.put("lastname", lastname);
        result.put("email", email);
        result.put("profilepicUri", profilepicUri);
        result.put("userKey", userKey);
        result.put("status", status);
        result.put("gender", gender);
        result.put("friendAddedDate", friendAddedDate);
        return result;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(friendAddedDate);
    }
    protected Friend(Parcel in) {
        friendAddedDate=in.readString();
    }

    public static final Creator<Friend> CREATOR = new Creator<Friend>() {
        @Override
        public Friend createFromParcel(Parcel in) {
            return new Friend(in);
        }

        @Override
        public Friend[] newArray(int size) {
            return new Friend[size];
        }
    };
}
