package com.uncc.prateek.favouritemovieswithfragments;

import java.io.Serializable;

/**
 * Created by Prateek on 27-01-2017.
 */
/*
a. Assignment #. Homework 2
b. File Name : MainActivity.java
c. Group : 6
c. Name of students: Prateek Mahendrakar , Siva Ram Praneeth Vemulapalli

 */

public class Movie implements Serializable{
    private String name;
    private String description;
    private String genre;
    private int rating;
    private int year;
    private String IMDB;

    public Movie() {
    }

    public Movie(String name, String description, String genre, int rating, int year, String IMDB) {
        this.name = name;
        this.description = description;
        this.genre = genre;
        this.rating = rating;
        this.year = year;
        this.IMDB = IMDB;
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return "Movie{" +
                "name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", genre='" + genre + '\'' +
                ", rating=" + rating +
                ", year=" + year +
                ", IMDB='" + IMDB + '\'' +
                '}';
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public String getIMDB() {
        return IMDB;
    }

    public void setIMDB(String IMDB) {
        this.IMDB = IMDB;
    }
}
