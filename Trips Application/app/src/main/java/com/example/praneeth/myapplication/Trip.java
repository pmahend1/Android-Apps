package com.example.praneeth.myapplication;

import android.os.Parcel;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


public class Trip implements Serializable
//        implements Parcelable
{
    /*public static final Creator<Trip> CREATOR = new Creator<Trip>() {
        @Override
        public Trip createFromParcel(Parcel in) {
            return new Trip(in);
        }

        @Override
        public Trip[] newArray(int size) {
            return new Trip[size];
        }
    };*/
    String tripID;
    String title;
    String location;
    String imageURL;
    ArrayList<String> users = new ArrayList<String>();
    boolean isActive = true;
    String createrId;
    ArrayList<Place> places=new ArrayList<Place>();

    public Trip(String tripID, String title, String location, String imageURL, String createrId) {
        this.tripID = tripID;
        this.title = title;
        this.location = location;
        this.imageURL = imageURL;
        this.createrId = createrId;
    }

    public Trip() {
    }

    public Trip(String tripID, String title, String location, String imageURL) {
        this.tripID = tripID;
        this.title = title;
        this.location = location;
        this.imageURL = imageURL;
    }

    protected Trip(Parcel in) {
        tripID = in.readString();
        title = in.readString();
        location = in.readString();
        imageURL = in.readString();
    }

    public ArrayList<String> getUsers() {
        return users;
    }

    /*public static Creator<Trip> getCREATOR() {
        return CREATOR;
    }*/

    public void setUsers(ArrayList<String> users) {
        this.users = users;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }

    public String getCreaterId() {
        return createrId;
    }

    public void setCreaterId(String createrId) {
        this.createrId = createrId;
    }

    @Override
    public String toString() {
        return "Trip{" +
                "tripID='" + tripID + '\'' +
                ", title='" + title + '\'' +
                ", location='" + location + '\'' +
                ", imageURL='" + imageURL + '\'' +
                '}';
    }

    public String getTripID() {
        return tripID;
    }

    public void setTripID(String tripID) {
        this.tripID = tripID;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getImageURL() {
        return imageURL;
    }

    public void setImageURL(String imageURL) {
        this.imageURL = imageURL;
    }

    public Map<String, Object> toBasicMap() {
        HashMap<String, Object> result = new HashMap<>();
        result.put("tripID", tripID);
        result.put("title", title);
        result.put("location", location);
        result.put("imageURL", imageURL);
        result.put("isActive", true);
        result.put("createrId", createrId);
        result.put("users", users);

        return result;
    }

    public Map<String, Object> toAddMemberMap() {
        HashMap<String, Object> result = new HashMap<>();
        result.put("tripID", tripID);
        result.put("title", title);
        result.put("location", location);
        result.put("imageURL", imageURL);
        return result;
    }

    public Map<String, Object> toAddChatMap() {
        HashMap<String, Object> result = new HashMap<>();
        result.put("tripID", tripID);
        result.put("title", title);
        result.put("location", location);
        result.put("imageURL", imageURL);
        return result;
    }

    /*@Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(tripID);
        dest.writeString(title);
        dest.writeString(location);
        dest.writeString(imageURL);
    }*/
}
