package com.uncc.prateek.gamesdb;
/*
    a. Assignment #. Homework 05
	b. File Name. Game.java
	c. Full name of all students in your group. Prateek Mahendrakar , Siva Ram Praneeth Vemulapalli
*/
import android.util.Log;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

/**
 * Created by Prateek on 18-02-2017.
 */

public class Game implements Serializable {
    private String id;
    private String gameTitle;
    private String releaseDate;
    private String platForm;
    private String platFormId;
    private String overView;
    private String ESRB;
    private String genres[];
    private String players;
    private String coOp;
    private String youTube;
    private String publisher;
    private String developer;
    private String rating;
    private ArrayList<Game> similar;
    private String images[];

    public Game() {
    }

    public Game(String id, String gameTitle, String releaseDate, String platForm) {
        this.id = id;
        this.gameTitle = gameTitle;
        this.releaseDate = releaseDate;
        this.platForm = platForm;
    }

    public Game(String id, String gameTitle, String releaseDate, String platForm, String platFormId, String overView, String ESRB, String[] genres, String players, String coOp, String youTube, String publisher, String developer, String rating, ArrayList<Game> similar, String[] images) {
        this.id = id;
        this.gameTitle = gameTitle;
        this.releaseDate = releaseDate;
        this.platForm = platForm;
        this.platFormId = platFormId;
        this.overView = overView;
        this.ESRB = ESRB;
        this.genres = genres;
        this.players = players;
        this.coOp = coOp;
        this.youTube = youTube;
        this.publisher = publisher;
        this.developer = developer;
        this.rating = rating;
        this.similar = similar;
        this.images = images;
    }

    public String getPlatFormId() {
        return platFormId;
    }

    public void setPlatFormId(String platFormId) {
        this.platFormId = platFormId;
    }

    public String getOverView() {
        return overView;
    }

    public void setOverView(String overView) {
        this.overView = overView;
    }

    public String getESRB() {
        return ESRB;
    }

    public void setESRB(String ESRB) {
        this.ESRB = ESRB;
    }

    public String[] getGenres() {
        return genres;
    }

    public void setGenres(String[] genres) {
        this.genres = genres;
    }

    public String getPlayers() {
        return players;
    }

    public void setPlayers(String players) {
        this.players = players;
    }

    public String getCoOp() {
        return coOp;
    }

    public void setCoOp(String coOp) {
        this.coOp = coOp;
    }

    public String getYouTube() {
        return youTube;
    }

    public void setYouTube(String youTube) {
        this.youTube = youTube;
    }

    public String getPublisher() {
        return publisher;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }

    public String getDeveloper() {
        return developer;
    }

    public void setDeveloper(String developer) {
        this.developer = developer;
    }

    public String getRating() {
        return rating;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }

    public ArrayList<Game> getSimilar() {
        return similar;
    }

    public void setSimilar(ArrayList<Game> similar) {
        this.similar = similar;
    }

    public String[] getImages() {
        return images;
    }

    public void setImages(String[] images) {
        this.images = images;
    }


    public String toShortString() {
        return gameTitle + "." + " Released in " + getYear() + "." + " Platform:" + platForm ;
    }

    @Override
    public String toString() {
        return "Game{" +
                "id='" + id + '\'' +
                ", gameTitle='" + gameTitle + '\'' +
                ", releaseDate='" + releaseDate + '\'' +
                ", platForm='" + platForm + '\'' +
                ", platFormId='" + platFormId + '\'' +
                ", overView='" + overView + '\'' +
                ", ESRB='" + ESRB + '\'' +
                ", genres=" + Arrays.toString(genres) +
                ", players='" + players + '\'' +
                ", coOp='" + coOp + '\'' +
                ", youTube='" + youTube + '\'' +
                ", publisher='" + publisher + '\'' +
                ", developer='" + developer + '\'' +
                ", rating='" + rating + '\'' +
                //", similar=" + similar +
                ", images=" + Arrays.toString(images) +
                '}';
    }

    public int getYear(){
        try{
            SimpleDateFormat format = new SimpleDateFormat("MM/dd/yyyy");

            Date theDate = format.parse(this.releaseDate);

            Calendar myCal = new GregorianCalendar();
            myCal.setTime(theDate);
            return myCal.get(Calendar.YEAR);
        }catch (Exception e){
            Log.d("Game","Exception :" +e.getMessage());
            return 0;
        }

    }

    public static void main(String[] args) {
        Game game = new Game();
        game.setReleaseDate("11/01/2014");
        game.getYear();
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getGameTitle() {
        return gameTitle;
    }

    public void setGameTitle(String gameTitle) {
        this.gameTitle = gameTitle;
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(String releaseDate) {
        this.releaseDate = releaseDate;
    }

    public String getPlatForm() {
        return platForm;
    }

    public void setPlatForm(String platForm) {
        this.platForm = platForm;
    }
}
