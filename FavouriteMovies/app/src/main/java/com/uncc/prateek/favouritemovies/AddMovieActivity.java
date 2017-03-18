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
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import java.util.ArrayList;
import java.util.List;

public class AddMovieActivity extends AppCompatActivity {
    String movieName = "";
    String description = "";
    String genre = "";
    int rating = 0;
    int year= 0;
    String imdbLink = "";
    private String logLevel = "Add Movie";
    public static final String MovieLIST="MOVIELIST";
    private String logStep = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_add_movie);

        final Spinner spinnerGenre  = (Spinner) findViewById(R.id.spinner_genre);
        final Context context = AddMovieActivity.this;

        final String[] values=getResources().getStringArray(R.array.spinner_genreValues);
        ArrayAdapter<String> dropDownAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item,values);
        dropDownAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spinnerGenre.setAdapter(dropDownAdapter);
        try{
            Log.d(logLevel,""+spinnerGenre.getSelectedItem().toString());
        }catch (Exception e){
            Log.d(logLevel,e.getMessage());
        }
        SeekBar seek = (SeekBar) findViewById(R.id.seekBar_rating);

        rating= seek.getProgress();
        TextView tvRatingVal = (TextView) findViewById(R.id.textView_ratingValueAdd);
        tvRatingVal.setText(Integer.toString(rating));

        EditText editTextDesc = (EditText) findViewById(R.id.textView_description_value);
        description = editTextDesc.getText().toString();
        editTextDesc.setMovementMethod(new ScrollingMovementMethod());

        seek.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener(){

            @Override
            public void onProgressChanged(SeekBar seekBar, int progress,
                                          boolean fromUser) {
                // TODO Auto-generated method stub
                TextView tvRatingVal = (TextView) findViewById(R.id.textView_ratingValueAdd);
                tvRatingVal.setText(String.valueOf(progress));
                Log.d(logLevel,"Seekbar value changed");
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                // TODO Auto-generated method stub
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                // TODO Auto-generated method stub
            }
        });
        findViewById(R.id.button_addMovieOk).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText editText_movieNameVal = (EditText) findViewById(R.id.editText_movieNameValue);
                movieName = editText_movieNameVal.getText().toString();
                Log.d(logLevel,"Movie name "+movieName);

                EditText editTextDesc = (EditText) findViewById(R.id.textView_description_value);
                description = editTextDesc.getText().toString();
                Log.d(logLevel,description);

                genre = spinnerGenre.getSelectedItem().toString();

                SeekBar seek = (SeekBar) findViewById(R.id.seekBar_rating);
                rating= seek.getProgress();
/*                TextView tvRatingVal = (TextView) findViewById(R.id.textView_ratingValueAdd);
                tvRatingVal.setText(Integer.toString(rating));*/
                logStep="Get year value";
                EditText editTextYear = (EditText) findViewById(R.id.editText_yearValue);
                try{
                    year= Integer.parseInt(editTextYear.getText().toString());
                }catch (Exception e){
                    Log.d(logLevel ,logStep+e.getMessage());
                    Toast.makeText(context,"Error in year",Toast.LENGTH_LONG);
                }


                EditText editTextImdbLink = (EditText) findViewById(R.id.editText_imdbLink);
                imdbLink= editTextImdbLink.getText().toString();

                Movie addMovie = new Movie();
                addMovie.setName(movieName);
                addMovie.setDescription(description);
                addMovie.setGenre(genre);
                addMovie.setIMDB(imdbLink);
                addMovie.setRating(rating);
                addMovie.setYear(year);
                logStep = "Print movie obj ";

                String msg = Validator.validate(addMovie);
                Log.d(logLevel,"|"+msg+"|");
                if(msg==null){
                    msg="";
                }
                if(msg.isEmpty()){
                    Log.d(logLevel,addMovie.toString());
                    Intent intent =  new Intent();
                    intent.putExtra(MainActivity.ADD_MOVIE_KEY,addMovie);
                    setResult(RESULT_OK,intent);
                    finish();
                }
                else{
                    Toast.makeText(context,msg,Toast.LENGTH_LONG).show();
                }


            }
        });




    }

}
