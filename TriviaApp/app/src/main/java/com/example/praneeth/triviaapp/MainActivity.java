package com.example.praneeth.triviaapp;
/*  a. Assignment #. Homework 4
    b. File Name. : MainActivity.java
    c. Full name of all students in your group. : Prateek , Praneeth
    */
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements AsyncGetQuestions.IQuestion, View.OnClickListener {
    ArrayList<Question> questionsArrayList = new ArrayList<>();
    final static String logText = "MainActivity";
    final static String webURL = "http://dev.theappsdr.com/apis/trivia_json/index.php";


    @Override
    public Context getContext() {
        return this;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findViewById(R.id.button_Exit).setOnClickListener(this);

        new AsyncGetQuestions(MainActivity.this).execute(webURL);

    }

    @Override
    public void display(ArrayList<Question> result) {
        Log.d(logText, result.toString());

        if (result != null) {
            Log.d(logText, "loadData here");
            questionsArrayList = result;
            Log.d(logText, "step xyz" + questionsArrayList.toString());

            ImageView imTriviaReadyImage = (ImageView) findViewById(R.id.imageView_triviaReady);

            imTriviaReadyImage.setImageResource(R.drawable.trivia);

            Button startButton = (Button) findViewById(R.id.Button_StartTrivia);
            startButton.setClickable(true);
            startButton.setOnClickListener(MainActivity.this);
            TextView tvTriviaReadyMsg = (TextView) findViewById(R.id.textView_triviaReadymsg);
            tvTriviaReadyMsg.setText("Trivia Ready");

        } else {
            Log.d(logText, "loadData on NULL");
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.button_Exit: {
                finish();
                break;
            }
            case R.id.Button_StartTrivia: {
                Intent triviaIntent = new Intent(MainActivity.this, Trivia.class);
                if (questionsArrayList != null) {
                    triviaIntent.putExtra("QUESTIONS_LIST", questionsArrayList);
                }
                startActivity(triviaIntent);
                break;


            }
        }
    }

    public ArrayList<Question> getTriviaInfo() {
        return questionsArrayList;
    }
}
