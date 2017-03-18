/*
a. Assignment #. Homework 2
b. File Name : ViewListByYear.java
c. Group : 6
c. Name of students: Prateek Mahendrakar , Siva Ram Praneeth Vemulapalli

 */
package com.uncc.prateek.favouritemovies;

import android.content.Intent;
import android.media.Image;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class ViewListByYear extends AppCompatActivity implements  View.OnClickListener {
    String logLevel = "FavMovies/ViewByYear";
    static int i=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_view_list_by_year);

        Log.d(logLevel,"------Inside----");
        TextView tvName= (TextView) findViewById(R.id.textView_viewYearTitle);
        TextView tvDescription = (TextView) findViewById(R.id.textView_ViewYearDescription);
        TextView tvGenre= (TextView) findViewById(R.id.textView_ViewYearGenre);
        TextView tvRating = (TextView) findViewById(R.id.textView_ViewYearRating);
        TextView tvYear = (TextView) findViewById(R.id.textView_ViewYearYear);
        TextView tvImdb = (TextView) findViewById(R.id.textView_ViewYearIMDB);

        final List<Movie> movieList = (List<Movie>) getIntent().getSerializableExtra(MainActivity.MOVIE_LIST_BY_YEAR);

        int i=0;
        Button buttonFinish = (Button) findViewById(R.id.button_finishByYear);
        ImageButton buttonFirst = (ImageButton) findViewById(R.id.button_firstMovieByYear);
        ImageButton buttonNext = (ImageButton) findViewById(R.id.button_nextMovieByYear);
        ImageButton buttonPrevious = (ImageButton) findViewById(R.id.button_previousMovieByYear);
        ImageButton buttonLast = (ImageButton) findViewById(R.id.button_lastMovieByYear);


        buttonFinish.setOnClickListener(this);
        buttonFirst.setOnClickListener(this);
        buttonNext.setOnClickListener(this);
        buttonPrevious.setOnClickListener(this);
        buttonLast.setOnClickListener(this);

        Movie movie = movieList.get(i);
        tvName.setText(movie.getName().toString());
        tvDescription.setText(movie.getDescription().toString());
        tvGenre.setText(movie.getGenre().toString());
        tvRating.setText(""+movie.getRating());
        tvYear.setText(""+movie.getYear());
        tvImdb.setText(movie.getIMDB().toString());


    }

    @Override
    public void onClick(View view) {

        TextView tvName= (TextView) findViewById(R.id.textView_viewYearTitle);
        TextView tvDescription = (TextView) findViewById(R.id.textView_ViewYearDescription);
        TextView tvGenre= (TextView) findViewById(R.id.textView_ViewYearGenre);
        TextView tvRating = (TextView) findViewById(R.id.textView_ViewYearRating);
        TextView tvYear = (TextView) findViewById(R.id.textView_ViewYearYear);
        TextView tvImdb = (TextView) findViewById(R.id.textView_ViewYearIMDB);

        Log.d(logLevel,"onClick inside ViewListByYear");

        final List<Movie> movieList = (List<Movie>) getIntent().getSerializableExtra(MainActivity.MOVIE_LIST_BY_YEAR);

        switch (view.getId()){
            case R.id.button_finishByYear:{
                Intent intent = new Intent();
                setResult(RESULT_OK);
                finish();
                break;
            }
            case R.id.button_firstMovieByYear:{
                i=0;
                Movie movie = movieList.get(i);
                tvName.setText(movie.getName().toString());
                tvDescription.setText(movie.getDescription().toString());
                tvGenre.setText(movie.getGenre().toString());
                tvRating.setText(""+movie.getRating());
                tvYear.setText(""+movie.getYear());
                tvImdb.setText(movie.getIMDB().toString());
                break;
            }
            case R.id.button_lastMovieByYear:{
                Movie movie = movieList.get(movieList.size()-1);
                tvName.setText(movie.getName().toString());
                tvDescription.setText(movie.getDescription().toString());
                tvGenre.setText(movie.getGenre().toString());
                tvRating.setText(""+movie.getRating());
                tvYear.setText(""+movie.getYear());
                tvImdb.setText(movie.getIMDB().toString());
                break;
            }
            case R.id.button_previousMovieByYear:{
                if(i>0){
                    i=i-1;
                    Movie movie = movieList.get(i);
                    tvName.setText(movie.getName().toString());
                    tvDescription.setText(movie.getDescription().toString());
                    tvGenre.setText(movie.getGenre().toString());
                    tvRating.setText(""+movie.getRating());
                    tvYear.setText(""+movie.getYear());
                    tvImdb.setText(movie.getIMDB().toString());
                    break;

                }else{
                    Toast.makeText(this,"At first element already",Toast.LENGTH_SHORT).show();
                    break;
                }

            }
            case R.id.button_nextMovieByYear:{
                if(i<movieList.size()-1){
                    i=i+1;
                    Movie movie = movieList.get(i);
                    tvName.setText(movie.getName().toString());
                    tvDescription.setText(movie.getDescription().toString());
                    tvGenre.setText(movie.getGenre().toString());
                    tvRating.setText(""+movie.getRating());
                    tvYear.setText(""+movie.getYear());
                    tvImdb.setText(movie.getIMDB().toString());
                    break;

                }else{
                    Toast.makeText(this,"At Last element already",Toast.LENGTH_SHORT).show();
                    break;
                }
            }

        }
    }
}
