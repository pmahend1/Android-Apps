package com.example.praneeth.chitchat;
/*a. Assignment #. In Class 09
        b. File Name. ___
        c. Full name of all students in your group. : Prateek , Praneeth*/
import java.io.Serializable;

/**
 * Created by Praneeth on 3/27/2017.
 */

    public class Channel implements Serializable {

    private String name;
    private int id;
    private boolean isSubscribed;

    public boolean isSubscribed() {
        return isSubscribed;
    }

    public void setSubscribed(boolean subscribed) {
        isSubscribed = subscribed;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Channel() {
    }

    public Channel(String name, int id) {
        this.name = name;
        this.id = id;
        this.isSubscribed = false;
    }

    @Override
    public String toString() {
        return "Channel{" +
                "name='" + name + '\'' +
                ", id=" + id +
                ", isSubscribed=" + isSubscribed +
                '}';
    }
}
