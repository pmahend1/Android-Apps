package com.example.praneeth.myapplication;

import java.io.Serializable;

/**
 * Created by Prateek on 29-04-2017.
 */

public class Place implements Serializable {
    String name;
    double lattitude;
    double longitude;

    public Place(double lattitude, double longitude) {
        this.lattitude = lattitude;
        this.longitude = longitude;
    }

    public Place() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return this.name;
    }
}
