package com.uncc.prateek.midterm800966178;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;

/**
 * Created by Prateek on 20-03-2017.
 */

public class Product implements Serializable {
    String title, imageLink, description, skus;
    double price, discount;

    public String getTitle() {
        return title;
    }


    public void setTitle(String title) {
        this.title = title;
    }

    public String getImageLink() {
        return imageLink;
    }

    public void setImageLink(String imageLink) {
        this.imageLink = imageLink;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getSkus() {
        return skus;
    }

    public void setSkus(String skus) {
        this.skus = skus;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public double getDiscount() {
        return discount;
    }

    public void setDiscount(double discount) {
        this.discount = discount;
    }

    public Product(String title, String imageLink, double price, double discount) {

        this.title = title;
        this.imageLink = imageLink;
        this.price = price;
        this.discount = discount;
    }

    public Product() {
    }

    @Override
    public String toString() {
        return "Product{" +
                "title='" + title + '\'' +
                ", imageLink='" + imageLink + '\'' +
                ", price=" + price +
                ", discount=" + discount +
                '}';
    }

    public Product(JSONObject newsJSONObject) throws JSONException {
        this.title = newsJSONObject.getString("name");
        //this.imageLink = newsJSONObject.getString("image_urls");
        this.description = newsJSONObject.getString("content");
        this.skus = newsJSONObject.getString("skus");
    }

    public void parseImageLink(){

    }
}
