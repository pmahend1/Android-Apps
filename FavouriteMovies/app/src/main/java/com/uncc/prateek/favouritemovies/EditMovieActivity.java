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
import android.support.v7.widget.ScrollingTabContainerView;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class EditMovieActivity extends AppCompatActivity {

    private String logLevel="EditMovieActivity ";


    private int ratingValue=0;
    int i = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_edit_movie);

        TextView movieName = (TextView) findViewById(R.id.editText_movieNameValueEdit);
        TextView description = (TextView) findViewById(R.id.textView_description_valueEdit);
        Spinner genreSpinner = (Spinner) findViewById(R.id.spinner_genreEdit);
        SeekBar rating = (SeekBar) findViewById(R.id.seekBar_ratingEdit);
        EditText year= (EditText) findViewById(R.id.editText_yearValueEdit);
        EditText imdb = (EditText) findViewById(R.id.editText_imdbLinkEdit);
        Button saveChanges = (Button) findViewById(R.id.button_saveChanges);

        ratingValue= rating.getProgress();
        TextView tvRatingVal = (TextView) findViewById(R.id.textView_ratingValueEdit);
        tvRatingVal.setText(Integer.toString(ratingValue));

        //EditText description = (EditText) findViewById(R.id.textView_description_valueEdit);
        description.setMovementMethod(new ScrollingMovementMethod());
        //Log.d(logLevel,descriptionStr);

        rating.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener(){

            @Override
            public void onProgressChanged(SeekBar seekBar, int progress,
                                          boolean fromUser) {
                // TODO Auto-generated method stub
                TextView tvRatingVal = (TextView) findViewById(R.id.textView_ratingValueEdit);
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
        Movie movie = (Movie) getIntent().getSerializableExtra(MainActivity.EDIT_MOVIE_KEY);
        i = getIntent().getExtras().getInt(MainActivity.INDEX_KEY);

        Log.d(logLevel,"Movie fetched at EditMovAct"+movie.toString());
        Log.d(logLevel,"Index "+i);


        Log.d(logLevel,"Movie fetched");

        if(movie!=null){

            //setting field values
            movieName.setText(movie.getName().toString());
            Log.d(logLevel,"movie nname set");
            description.setText(movie.getDescription().toString());
            Log.d(logLevel,"descrition set");
            String compareValue = movie.getGenre().toString();
            ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.spinner_genreValues, android.R.layout.simple_spinner_item);
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            genreSpinner.setAdapter(adapter);
            if (!compareValue.equals(null)) {
                int spinnerPosition = adapter.getPosition(compareValue);
                genreSpinner.setSelection(spinnerPosition);
            }

            rating.setProgress(movie.getRating());
            Log.d(logLevel,"Rating set");

            year.setText(Integer.toString(movie.getYear()));
            Log.d(logLevel,"Year set");
            imdb.setText(movie.getIMDB().toString());
            Log.d(logLevel,"IMDB link set");

            Log.d(logLevel,"on click of button checking and performing edits");

            saveChanges.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    //declare variables
                    String movieNameStr = "";
                    String descriptionStr = "";
                    String genreStr = "";
                    int ratingStr = 0;
                    int yearStr= 0;
                    String imdbLinkStr = "";
                    String logLevel = "EditMovieOnclick";
                    Log.d(logLevel,"variable set");

                    EditText movieNameET = (EditText) findViewById(R.id.editText_movieNameValueEdit);
                    movieNameStr = movieNameET.getText().toString();
                    Log.d(logLevel,"Movie name "+movieNameStr);

                    EditText description = (EditText) findViewById(R.id.textView_description_valueEdit);
                    descriptionStr = description.getText().toString();
                    Log.d(logLevel,descriptionStr);

                    Spinner genreSpinner = (Spinner) findViewById(R.id.spinner_genreEdit);
                    genreStr = genreSpinner.getSelectedItem().toString();

                    SeekBar seek = (SeekBar) findViewById(R.id.seekBar_ratingEdit);

                    ratingStr= seek.getProgress();

                    //logStep="Get year value";
                    EditText year = (EditText) findViewById(R.id.editText_yearValueEdit);
                    try{
                        yearStr= Integer.parseInt(year.getText().toString());
                    }catch (Exception e){
                        Log.d(logLevel ,e.getMessage());
                        Toast.makeText(EditMovieActivity.this,"Error in year",Toast.LENGTH_LONG);
                    }


                    EditText imdb = (EditText) findViewById(R.id.editText_imdbLinkEdit);
                    imdbLinkStr= imdb.getText().toString();

                    Movie editMovie = new Movie();
                    editMovie.setName(movieNameStr);
                    editMovie.setDescription(descriptionStr);
                    editMovie.setGenre(genreStr);
                    editMovie.setIMDB(imdbLinkStr);
                    editMovie.setRating(ratingStr);
                    editMovie.setYear(yearStr);
                    //logStep = "Print movie obj ";


                    String msg = Validator.validate(editMovie);
                    if(msg==null){
                        msg="";
                    }
                    if(msg.isEmpty()){
                        Log.d(logLevel,editMovie.toString());

                        Intent intent =  new Intent();
                        intent.putExtra(MainActivity.EDIT_MOVIE_KEY,editMovie);
                        intent.putExtra(MainActivity.INDEX_KEY,i);
                        Log.d(logLevel,"Sending back Edit mov obj");
                        setResult(RESULT_OK,intent);

                        finish();
                    }
                    else{
                        Toast.makeText(EditMovieActivity.this,msg,Toast.LENGTH_LONG).show();
                    }


                }
            });

        }


    }

}
