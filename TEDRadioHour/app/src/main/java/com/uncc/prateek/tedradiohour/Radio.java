/*
a. Assignment #. Homework 7 
b. File Name. 
c. Full name of all students in your group. : Prateek Mahendrakar , Praneeth

*/
package com.uncc.prateek.tedradiohour;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Prateek on 10-03-2017.
 */

public class Radio implements Serializable {
  private String title;
    private String pubDate;
    private String summary;
    private String description;
    private String imageLink;
    private int duration;
    private String podcastLink;
    private Date pubDateDate;

    public Date getPubDateDate() {
        return pubDateDate;
    }

    public void setPubDateDate(Date pubDateDate) {
        this.pubDateDate = pubDateDate;
    }

    public String getPodcastLink() {
        return podcastLink;
    }

    public void setPodcastLink(String podcastLink) {
        this.podcastLink = podcastLink;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPubDate() {
        return pubDate;
    }

    public void setPubDate(String pubDate) {
        this.pubDate = pubDate;
        convertDate();
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImageLink() {
        return imageLink;
    }

    public void setImageLink(String imageLink) {
        this.imageLink = imageLink;
    }

    @Override
    public String toString() {
        return "Radio{" +
                "title='" + title + '\'' +
                ", pubDate='" + pubDate + '\'' +
                ", summary='" + summary + '\'' +
                ", description='" + description + '\'' +
                ", imageLink='" + imageLink + '\'' +
                ", duration=" + duration +
                ", podcastLink='" + podcastLink + '\'' +
                '}';
    }

    public String convertDate(){
        //Fri, 03 Mar 2017 00:01:14 -0500
        //String strDate=pubDate;
        String strDate=this.pubDate;
        SimpleDateFormat dateFormat = new SimpleDateFormat("E, dd MMM yyyy HH:mm:ss Z");
        try {
            Date varDate=dateFormat.parse(strDate);
            dateFormat=new SimpleDateFormat("E ,dd MMM yyyy");
            this.pubDateDate=varDate;
            return dateFormat.format(varDate);
        }catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
