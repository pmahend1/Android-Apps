/*
a. Assignment #. Homework 2
b. File Name : MainActivity.java
c. Group : 6
c. Name of students: Prateek Mahendrakar , Siva Ram Praneeth Vemulapalli

 */
package com.uncc.prateek.favouritemovies;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

public class ViewListByRating extends AppCompatActivity implements View.OnClickListener{

    String logLevel = "FavMovies/ViewByRating";
    static int i=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_view_list_by_rating);

        Log.d(logLevel,"------Inside layout_view_list_by_rating----");
        TextView tvName= (TextView) findViewById(R.id.textView_viewRatingTitle);
        TextView tvDescription = (TextView) findViewById(R.id.textView_ViewRatingDescription);
        TextView tvGenre= (TextView) findViewById(R.id.textView_ViewRatingGenre);
        TextView tvRating = (TextView) findViewById(R.id.textView_ViewRatingRatingValue);
        TextView tvYear = (TextView) findViewById(R.id.textView_ViewRatingYear);
        TextView tvImdb = (TextView) findViewById(R.id.textView_ViewRatingIMDBValue);

        final List<Movie> movieList = (List<Movie>) getIntent().getSerializableExtra(MainActivity.MOVIE_LIST_BY_RATING);

        int i=0;
        Button buttonFinish = (Button) findViewById(R.id.button_finishRating);
        ImageButton buttonFirst = (ImageButton) findViewById(R.id.button_firstMovieByRating);
        ImageButton buttonNext = (ImageButton) findViewById(R.id.button_nextMovieByRating);
        ImageButton buttonPrevious = (ImageButton) findViewById(R.id.button_previousMovieByRating);
        ImageButton buttonLast = (ImageButton) findViewById(R.id.button_lastMovieByRating);


        buttonFinish.setOnClickListener(this);
        buttonFirst.setOnClickListener(this);
        buttonNext.setOnClickListener(this);
        buttonPrevious.setOnClickListener(this);
        buttonLast.setOnClickListener(this);
        if(movieList!=null){
            Movie movie = movieList.get(i);
            tvName.setText(movie.getName().toString());
            tvDescription.setText(movie.getDescription().toString());
            tvGenre.setText(movie.getGenre().toString());
            tvRating.setText(""+movie.getRating());
            tvYear.setText(""+movie.getYear());
            tvImdb.setText(movie.getIMDB().toString());
        }else{
            Intent intent = new Intent();
            setResult(RESULT_CANCELED,intent);

        }



    }

    @Override
    public void onClick(View view) {

        TextView tvName= (TextView) findViewById(R.id.textView_viewRatingTitle);
        TextView tvDescription = (TextView) findViewById(R.id.textView_ViewRatingDescription);
        TextView tvGenre= (TextView) findViewById(R.id.textView_ViewRatingGenre);
        TextView tvRating = (TextView) findViewById(R.id.textView_ViewRatingRatingValue);
        TextView tvYear = (TextView) findViewById(R.id.textView_ViewRatingYear);
        TextView tvImdb = (TextView) findViewById(R.id.textView_ViewRatingIMDBValue);


        Log.d(logLevel,"onClick inside ViewListByRating");

        final List<Movie> movieList = (List<Movie>) getIntent().getSerializableExtra(MainActivity.MOVIE_LIST_BY_RATING);

        switch (view.getId()){
            case R.id.button_finishRating:{
                Intent intent = new Intent();
                setResult(RESULT_OK);
                finish();
                break;
            }
            case R.id.button_firstMovieByRating:{
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
            case R.id.button_lastMovieByRating:{
                Movie movie = movieList.get(movieList.size()-1);
                tvName.setText(movie.getName().toString());
                tvDescription.setText(movie.getDescription().toString());
                tvGenre.setText(movie.getGenre().toString());
                tvRating.setText(""+movie.getRating());
                tvYear.setText(""+movie.getYear());
                tvImdb.setText(movie.getIMDB().toString());
                break;
            }
            case R.id.button_previousMovieByRating:{
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
            case R.id.button_nextMovieByRating:{
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
