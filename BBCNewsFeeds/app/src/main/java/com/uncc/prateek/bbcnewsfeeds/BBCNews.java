package com.uncc.prateek.bbcnewsfeeds;

import java.text.SimpleDateFormat;
import java.util.Date;

public class BBCNews {

    private long _id;
    private String title, newsDateStr , imageLink;
    private Date newsDate;

    public Date getNewsDate() {
        return newsDate;
    }

    public void setNewsDate(Date newsDate) {
        this.newsDate = newsDate;
    }

    public BBCNews(long _id, String title, String newsDate, String imageLink) {
        this._id = _id;
        this.title = title;
        this.newsDateStr = newsDate;
        this.imageLink = imageLink;
    }

    public long get_id() {
        return _id;
    }

    public void set_id(long _id) {
        this._id = _id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getNewsDateStr() {
        return newsDateStr;
    }

    public void setNewsDateStr(String newsDateStr) {
        this.newsDateStr = newsDateStr;
        this.convertDate();
    }

    public String convertDate(){
        String strDate=this.newsDateStr;
        SimpleDateFormat dateFormat = new SimpleDateFormat("E, dd MMM yyyy HH:mm:ss Z");
        try {
            Date varDate=dateFormat.parse(strDate);
            dateFormat=new SimpleDateFormat("E ,dd MMM yyyy");
            this.newsDate=varDate;
            return dateFormat.format(varDate);
        }catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public String getImageLink() {
        return imageLink;
    }

    public void setImageLink(String imageLink) {
        this.imageLink = imageLink;
    }


    @Override
    public String toString() {
        return "BBCNews{" +
                "_id=" + _id +
                ", title='" + title + '\'' +
                ", newsDate='" + newsDateStr + '\'' +
                ", imageLink='" + imageLink + '\'' +
                '}';
    }

    public BBCNews() {
    }
}