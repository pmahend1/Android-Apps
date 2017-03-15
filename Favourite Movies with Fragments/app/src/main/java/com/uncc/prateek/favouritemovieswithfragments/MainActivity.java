package com.uncc.prateek.favouritemovieswithfragments;
/*
a. Assignment #. In Class 08
b. File Name : MainActivity.java
c. Group : 6
c. Name of students: Prateek Mahendrakar , Siva Ram Praneeth Vemulapalli

 */
import android.content.DialogInterface;
import android.net.Uri;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class MainActivity extends AppCompatActivity  implements  AddMovieFragment.OnFragmentInteractionListener
,MyFavoriteMovies.OnFragmentInteractionListener
,EditMovieFragment.OnFragmentInteractionListener
,ViewListByRatingFragment.OnFragmentInteractionListener
,ViewListByYearFragment.OnFragmentInteractionListener{



    private String logLevel= "FavMovies/Main ";

    int editIndex = -1 ;


    public ArrayList<Movie> movieList = new ArrayList<Movie>();
    Button addMovie,edit,delete,listByYear,listByrating;
    AlertDialog alertDialog;
    private int deleteIndex= -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

       // MyFavoriteMovies myFavMovFra = new MyFavoriteMovies();
        //myFavMovFra.getMovies(movieList);

        getFragmentManager().beginTransaction().add(R.id.container,new MyFavoriteMovies(),"tag_my_fav_movies").commit();

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
    public void addMovie(Movie movie) {
        movieList.add(movie);
        MyFavoriteMovies fragment = (MyFavoriteMovies) getFragmentManager().findFragmentByTag("tag_my_fav_movies");
        fragment.getMovies(movieList);
        Toast.makeText(this,"Movie "+movie.getName()+" added to favourites",Toast.LENGTH_LONG).show();
    }

    @Override
    public void goToNextFragment() {
        getFragmentManager().beginTransaction().replace(R.id.container, new AddMovieFragment(),"tag_add_movie").addToBackStack(null).commit();
    }

    @Override
    public void setDeleteIndex(int index) {
        this.deleteIndex = index;
        if(movieList.size()>0){
            movieList.remove(deleteIndex);
            MyFavoriteMovies fragment = (MyFavoriteMovies) getFragmentManager().findFragmentByTag("tag_my_fav_movies");
            fragment.getMovies(movieList);
        }

    }


    @Override
    public void editMovie(Movie movie) {
        if(editIndex!=-1){
            Log.d("main","editMovie overrite");
            Log.d("main",editIndex+" : editIndex");
            movieList.set(editIndex,movie);
            Toast.makeText(this, "Movie edited", Toast.LENGTH_SHORT).show();
            getFragmentManager().popBackStack();
            MyFavoriteMovies fragment = (MyFavoriteMovies) getFragmentManager().findFragmentByTag("tag_my_fav_movies");
            fragment.getMovies(movieList);
        }else{
            Log.d("main","editMovie editIndex is -1");
        }
    }

    @Override
    public void setEditIndex(int index) {
        this.editIndex = index;
    }


    @Override
    public void insideByRating(String txt) {

    }

    @Override
    public void insideByYear(String txt) {

    }
}
