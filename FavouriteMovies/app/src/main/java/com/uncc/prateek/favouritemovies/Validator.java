/*
a. Assignment #. Homework 2
b. File Name : MainActivity.java
c. Group : 6
c. Name of students: Prateek Mahendrakar , Siva Ram Praneeth Vemulapalli

 */
package com.uncc.prateek.favouritemovies;


import android.icu.util.Calendar;
import android.icu.util.GregorianCalendar;

import java.util.Date;

/**
 * Created by Prateek on 1/29/2017.
 */

public class Validator {

private static boolean valid=true;
    private  static String message ="";

    public static String validate(Movie movie){
        message="";
        if(movie.getName().length()>50 ){
            valid=false;
            message+=" Movie name should be < 50 characters.\n";
        }

        if(movie.getName().length()==0){
            valid=false;
            message+=" Movie name cant be empty. \n";
        }

        if(movie.getDescription().length()>1000){
            valid=false;
            message+=" Description should be < 1000 characters.\n";
        }

        if(movie.getYear()<1910 || movie.getYear()>2017){
            valid=false;
            message+=" Year should be in (1911-2017).\n";
        }

        if( (!movie.getIMDB().contains("http://imdb.com/")) && (!movie.getIMDB().contains("www.imdb.com/"))){
            valid=false;
            message+=" IMDB link pattern should be"+
            ". www.imdb.com/ or  http://imdb.com/ \n";
        }
        if(valid==false){
            return message;
        }else{
            return null;
        }


    }
}
