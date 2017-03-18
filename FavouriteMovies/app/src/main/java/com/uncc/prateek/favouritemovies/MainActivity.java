/*
a. Assignment #. Homework 2
b. File Name : MainActivity.java
c. Group : 6
c. Name of students: Prateek Mahendrakar , Siva Ram Praneeth Vemulapalli

 */

package com.uncc.prateek.favouritemovies;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private String logLevel= "FavMovies/Main ";

    final static String ADD_MOVIE_KEY="AddMovie";

    public static final int REQUEST_CODE_ADD=1;

    public List<Movie> movieList = new ArrayList<Movie>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Call addMovieActivity for On click of Add Movie Button
        findViewById(R.id.button_addMovie).setOnClickListener(new View.OnClickListener() {
            //Log.d("FavMovies/Main","button_addMovie listener");
            @Override
            public void onClick(View v) {
                Log.d("FavMovies/Main","Add button clicked");
                Intent addMovieIntent= new Intent();
               // Context context = MainActivity.this;
                addMovieIntent.setClass(MainActivity.this,AddMovieActivity.class);

                //REQUEST_CODE = 1 for add

                startActivityForResult(addMovieIntent,REQUEST_CODE_ADD);

            }
        });


        }



    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == REQUEST_CODE_ADD){
            //add Movie operation
            if(resultCode==RESULT_OK){
                    Movie movie = (Movie) data.getSerializableExtra(ADD_MOVIE_KEY);
                movieList.add(movie);
                    Log.d("onActivityResult",movie.toString());
                for (Movie mov:movieList
                        ) {
                    Log.d(logLevel+"for loop",mov.toString());

                }
            }else{
                Log.d("aad","result failed");
                for (Movie mov:movieList
                     ) {
                    Log.d(logLevel,mov.toString());

                }
            }

        }
    }


}

