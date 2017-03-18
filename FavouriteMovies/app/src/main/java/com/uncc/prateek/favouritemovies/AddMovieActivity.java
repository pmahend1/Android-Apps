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
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
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

    private String logStep = "";
    List<Movie> movieList = new ArrayList<Movie>();

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
/*
            spinnerGenre.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                    Log.d(logLevel,spinnerGenre.getSelectedItem().toString());
                }
            });
*/

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
                logStep="Get year value";
                EditText editTextYear = (EditText) findViewById(R.id.editText_yearValue);
                try{
                    year= Integer.parseInt(editTextYear.getText().toString());
                }catch (Exception e){
                    Log.d(logLevel ,logStep+e.getMessage());
                    Toast.makeText(context,"Error in year",10);
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
                Log.d(logLevel,logStep+addMovie.toString());


                Intent intent =  new Intent();

                intent.putExtra(MainActivity.ADD_MOVIE_KEY,addMovie);
                setResult(RESULT_OK,intent);
                Log.d(logLevel,"Finishing activity");
                finish();

            }
        });




    }

}
