package com.uncc.prateek.newsapp;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
/*a. Assignment #. InClass 04
b. File Name. News.java
c. Full name of all students in your group. Prateek Mahendrakar , Siva Ram Praneeth Vemulapalli*/
/**
 * Created by Prateek on 06-02-2017.
 */

public class News {
    private String urlToImage;
    private String title;
    private String author;
    private String description;
    private String publishedAt;

    public News(String urlToImage, String title, String author, String description, String publishedAt) {
        this.urlToImage = urlToImage;
        this.title = title;
        this.author = author;
        this.description = description;
        this.publishedAt = publishedAt;
    }

    public News(JSONObject newsJSONObject) throws JSONException {
        this.urlToImage = newsJSONObject.getString("urlToImage");
        this.title = newsJSONObject.getString("title");
        this.author = newsJSONObject.getString("author");
        this.description = newsJSONObject.getString("description");
        this.publishedAt = newsJSONObject.getString("publishedAt");
    }


    public String getUrlToImage() {
        return urlToImage;
    }

    public void setUrlToImage(String urlToImage) {
        this.urlToImage = urlToImage;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPublishedAt() {
        return publishedAt;
    }

    public void setPublishedAt(String publishedAt) {
        this.publishedAt = publishedAt;
    }


    @Override
    public String toString() {
        return super.toString();
    }

    public ArrayList<String> toArrayList(){
        ArrayList<String> arr = new ArrayList<String>();
        arr.add("News Title:"+this.title);
        arr.add("Author:"+this.author);
        arr.add("Description :"+this.description);
        //arr.add();

        arr.add("Published at:"+this.publishedAt);

        return arr;
    }
}
