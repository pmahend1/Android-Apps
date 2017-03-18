package com.uncc.prateek.cnnnewsapp;

import android.text.Html;

import java.io.Serializable;
import java.util.ArrayList;
/*
    a) Assignment #. : InClass05
    b) Full name of the student. Prateek Mahendrakar
*/
/**
 * Created by Prateek on 13-02-2017.
 */

public class News implements Serializable {

    private String link;
    private String title;
    private String description;
    private String pubdate;
    private String image;

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPubdate() {
        return pubdate;
    }

    public void setPubdate(String pubdate) {
        this.pubdate = pubdate;
    }

    @Override
    public String toString() {
        return "News{" +
                "link='" + link + '\'' +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", pubdate='" + pubdate + '\'' +
                ", image='" + image + '\'' +
                '}';
    }

    public ArrayList<String> toArrayList(){
        ArrayList<String> arr = new ArrayList<String>();
        arr.add("News Title: "+this.title);
        arr.add("Published at: "+this.pubdate);
        arr.add("Description : \n "+Html.fromHtml(String.valueOf(this.description)));


        //arr.add("Image"+this.image);
        return arr;
    }
}
