/*
a. Assignment #. Homework 2
b. File Name : MainActivity.java
c. Group : 6
c. Name of students: Prateek Mahendrakar , Siva Ram Praneeth Vemulapalli

 */

package com.uncc.prateek.favouritemovies;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.support.annotation.IdRes;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private String logLevel= "FavMovies/Main ";
    final static String ADD_MOVIE_KEY="movieList";
    final static String EDIT_MOVIE_KEY="editMovie";
    final static String INDEX_KEY="index";
    final static String DELETE_MOVIE_KEY="deleteMovie";
    final static String MOVIE_LIST_BY_YEAR="MovieListByYear";
    final static String MOVIE_LIST_BY_RATING="MovieListByRating";




    public static final int REQUEST_CODE_ADD=10;
    public static final int REQUEST_CODE_EDIT=20;
    public static final int REQUEST_CODE_DELETE=30;
    public static final int REQUEST_CODE_VIEW_BY_YEAR=40;
    public static final int REQUEST_CODE_VIEW_BY_RATING=50;

    public ArrayList<Movie> movieList = new ArrayList<Movie>();
    Button addMovie,edit,delete,listByYear,listByrating;
    AlertDialog alertDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        addMovie= (Button) findViewById(R.id.button_addMovie);
        edit= (Button) findViewById(R.id.button_editMovie);
        delete= (Button) findViewById(R.id.button_deleteMovie);
        listByYear= (Button) findViewById(R.id.button_showListByYear);
        listByrating= (Button) findViewById(R.id.button_showListByRating);

        addMovie.setOnClickListener(this);
        edit.setOnClickListener(this);
        delete.setOnClickListener(this);
        listByYear.setOnClickListener(this);
        listByrating.setOnClickListener(this);


        Log.d(logLevel,"Step 1");

    }

    @Override
    protected void onResume() {
        super.onResume();
        if(getIntent().getExtras()!=null){
          Log.d(logLevel,"Inside onResume");
        }
    }

    @Override
    public void onClick(View view) {
        switch(view.getId()){

            case R.id.button_addMovie:{
                Intent addMovieIntent=new Intent(MainActivity.this,AddMovieActivity.class);
                //addMovieIntent.putParcelableArrayListExtra(ADD_MOVIE_KEY,movieList);
                Log.d(logLevel,"button_addMovie starting activity for result");
                startActivityForResult(addMovieIntent,REQUEST_CODE_ADD);
                break;
            }

            case R.id.button_editMovie: {

                if(movieList.size()>0){
                    List<CharSequence> moviesList = new ArrayList<CharSequence>();
                    //new String[100];
                    Log.d(logLevel,"button_editMovie");

                    for (int i = 0; i < movieList.size(); i++) {

                        moviesList.add(movieList.get(i).getName().toString());
                        Log.d(logLevel,"button_editMovie for loop");
                        Log.d(logLevel+"Movies i",""+moviesList.get(i).toString());
                        Log.d(logLevel+"get i",""+movieList.get(i).getName().toString()
                        );
                    }

                    CharSequence[] movies = moviesList.toArray(new CharSequence[moviesList.size()]);

                    String title = "Choose a Movie";
                    AlertDialog.Builder alertDialogBuilder =
                            new AlertDialog.Builder(MainActivity.this);

                    alertDialogBuilder.setTitle("Pick Movie")
                            .setItems(movies, new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    Log.d(logLevel,"onClik of alert /dialog intf");

                                    Intent intent = new Intent(MainActivity.this,EditMovieActivity.class);
                                    intent.putExtra(EDIT_MOVIE_KEY,movieList.get(i));
                                    intent.putExtra(INDEX_KEY,i);
                                    startActivityForResult(intent,REQUEST_CODE_EDIT);
                                }
                            });

                    alertDialog = alertDialogBuilder.create();
                    if(!alertDialog.isShowing()){
                        alertDialog.show();
                    }
                    //getAlertDialog(movies, title);
                    Log.d(logLevel,"button_editMovie show alert");
                    break;
                }else{
                    Toast.makeText(this,"No favourite movies.Nothing to be edited.",Toast.LENGTH_LONG).show();
                    break;
                }

            }
            case R.id.button_deleteMovie:{
                if(movieList.size()>0){
                    final List<CharSequence> moviesList = new ArrayList<CharSequence>();
                    Log.d(logLevel,"button_deleteMovie");

                    for (int i = 0; i < movieList.size(); i++) {
                        moviesList.add(movieList.get(i).getName().toString());
                        Log.d(logLevel,"button_deleteMovie for loop");
                        Log.d(logLevel+"Movies i",""+moviesList.get(i).toString());
                        Log.d(logLevel+"getI",""+movieList.get(i).getName().toString()
                        );
                    }

                    CharSequence[] movies = moviesList.toArray(new CharSequence[moviesList.size()]);

                    String title = "Pick a Movie";
                    AlertDialog.Builder alertDialogBuilder =
                            new AlertDialog.Builder(MainActivity.this);

                    alertDialogBuilder.setTitle(title)
                            .setItems(movies, new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    Log.d(logLevel,"onClik of Delete alert /dialog intf");
                                    String name= (String) moviesList.get(i);
                                    movieList.remove(i);
                                    Toast.makeText(getApplicationContext(),"Movie "+ name +" deleted",Toast.LENGTH_LONG).show();
                                }
                            });

                    alertDialog = alertDialogBuilder.create();
                    if(!alertDialog.isShowing()){
                        alertDialog.show();
                    }
                    //getAlertDialog(movies, title);
                    Log.d(logLevel,"button_deleteMovie show alert");
                    break;
                }else{
                    Toast.makeText(this,"No favourite movies.Nothing to be deleted.",Toast.LENGTH_LONG).show();
                    break;
                }

            }
            case R.id.button_showListByYear:{

            if(movieList.size()>0){
                Comparator<Movie> comp = new Comparator<Movie>() {
                    @Override
                    public int compare(Movie movie, Movie t1) {
                        if(movie.getYear()<t1.getYear()){
                            return -1;
                        }else{
                            return 1;
                        }


                    }
                };
                Collections.sort(movieList,comp);
                Intent intent = new Intent(MainActivity.this,ViewListByYear.class);
                intent.putExtra(MOVIE_LIST_BY_YEAR,movieList);
                startActivityForResult(intent,REQUEST_CODE_VIEW_BY_YEAR);

                break;
            }else{
                Toast.makeText(this,"No favourite movies exist! Add and retry.",Toast.LENGTH_LONG).show();
                break;
            }

            }
            case R.id.button_showListByRating:{
                if(movieList.size()>0){
                    Comparator<Movie> comp = new Comparator<Movie>() {

                        @Override
                        public int compare(Movie movie, Movie t1) {

                            //Descending
                            if(movie.getRating()<t1.getRating()){
                                return 1;
                            }else{
                                return -1;
                            }
                        }
                    };
                    Collections.sort(movieList,comp);
                    Intent intent = new Intent(MainActivity.this,ViewListByRating.class);
                    intent.putExtra(MOVIE_LIST_BY_RATING,movieList);
                    startActivityForResult(intent,REQUEST_CODE_VIEW_BY_RATING);

                    break;
                }else{
                    Toast.makeText(this,"No favourite movies exist! Add and retry.",Toast.LENGTH_LONG).show();
                    break;
                }


            }
        }
    }




    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        Log.d(logLevel,"onActivityResult");
        switch (requestCode) {
            case REQUEST_CODE_ADD: {
                if (resultCode == RESULT_OK) {
                    Movie movie = (Movie) data.getExtras().getSerializable(ADD_MOVIE_KEY);
                    Log.d(logLevel, "onActivityResult");
                    if (movie != null) {
                        Log.d(logLevel, movie.getName());
                        movieList.add(movie);
                        Toast.makeText(this,"Movie "+movie.getName()+" added to favourites",Toast.LENGTH_LONG).show();
                        break;
                    }
                    break;
                }
            }
            case REQUEST_CODE_EDIT: {
                if (resultCode == RESULT_OK) {
                    Log.d(logLevel, "Inside onActivty result of Edit req cde");
                    Movie editMovie = (Movie) data.getExtras().getSerializable(EDIT_MOVIE_KEY);
                    int i = data.getExtras().getInt(INDEX_KEY);
                    Log.d(logLevel, "I " + i);
                    //Log.d(logLevel,"Movie obj i Mainacct fr edit "+editMovie.toString());
                    if (editMovie != null) {
                        movieList.set(i, editMovie);
                        Log.d(logLevel, "Movie edited");
                        Toast.makeText(this,"Movie "+editMovie.getName()+" edited",Toast.LENGTH_LONG).show();
                        break;
                    }
                    break;

                }

            }
            case REQUEST_CODE_VIEW_BY_YEAR:{
                if (resultCode == RESULT_OK) {
                    Log.d(logLevel, "Inside onActivty result of View List By year");
                    break;

                }
            }
            case REQUEST_CODE_VIEW_BY_RATING:{
                if (resultCode == RESULT_OK) {
                    Log.d(logLevel, "Inside onActivty result of View List By rating");
                    break;

                }
            }
        }
    }
}


