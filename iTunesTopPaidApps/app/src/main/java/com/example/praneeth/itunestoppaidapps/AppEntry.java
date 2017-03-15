package com.example.praneeth.itunestoppaidapps;
/*
        a. Assignment : Homework 6
        b. File Name. : AppEntry.java
        c. Group Name : Group 06
        d. Students in  group : Prateek Mahendrakar , Siva Ram Praneeth Vemulapalli
 */

import java.io.Serializable;

public class AppEntry implements Serializable {
    String appName;
    Double price;
    String imageUrl;
    Boolean isFavourite;
    int id;

    public AppEntry(String appName, Double price, String imageUrl, Boolean isFavourite, int id) {
        this.appName = appName;
        this.price = price;
        this.imageUrl = imageUrl;
        this.isFavourite = isFavourite;
        this.id = id;
    }

    public AppEntry(String appName, Double price, String imageUrl, Boolean isFavourite) {
        this.appName = appName;
        this.price = price;
        this.imageUrl = imageUrl;
        this.isFavourite = isFavourite;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getAppName() {
        return appName;
    }

    public void setAppName(String appName) {
        this.appName = appName;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public Boolean getFavourite() {
        return isFavourite;
    }

    public void setFavourite(Boolean favourite) {
        isFavourite = favourite;
    }

    @Override
    public String toString() {
        return "AppEntry{" +
                "id=" + id +
                ", isFavourite=" + isFavourite +
                ", appName='" + appName + '\'' +
                ", price=" + price +
                ", imageUrl='" + imageUrl + '\'' +
                '}';
    }
}
