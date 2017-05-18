package com.uncc.prateek.final800966178;

import java.io.Serializable;



/**
 * Created by Prateek on 08-05-2017.
 */

public class Deal implements Serializable{
    String Place;
    int Cost;
    Location Location;
    String Duration;

    public Deal() {
    }

    public Deal(String place, int cost, com.uncc.prateek.final800966178.Location location, String duration) {
        Place = place;
        Cost = cost;
        Location = location;
        Duration = duration;
    }

    @Override
    public String toString() {
        return "Deal{" +
                "Place='" + Place + '\'' +
                ", Cost=" + Cost +
                ", Location=" + Location +
                ", Duration='" + Duration + '\'' +
                '}';
    }
}
