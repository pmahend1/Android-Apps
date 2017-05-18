package com.uncc.prateek.final800966178;

import java.io.Serializable;

/**
 * Created by Prateek on 08-05-2017.
 */

public class Location implements Serializable {
    double Lat;
    double Lon;

    @Override
    public String toString() {
        return "Location{" +
                "Lat=" + Lat +
                ", Lon=" + Lon +
                '}';
    }
}
