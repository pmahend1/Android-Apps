package com.example.praneeth.triviaapp;
/*  a. Assignment #. Homework 4
    b. File Name. : Stats.java
    c. Full name of all students in your group. : Prateek , Praneeth
    */
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.MultiAutoCompleteTextView;
import android.widget.ProgressBar;
import android.widget.Space;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;

public class Stats extends AppCompatActivity {
    HashMap<Integer, String> answerList = new HashMap<Integer, String>();
    ArrayList<Question> triviaList = new ArrayList<Question>();

    ArrayList<Integer> correctAnswerArray = new ArrayList<Integer>();
    ArrayList<Integer> wrongAnswerArray = new ArrayList<Integer>();

    final static String logText = "Stats";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stats);
        int correctAnsCount = 0;
        int performance = 0;

        Button btn1 = (Button) findViewById(R.id.button_Finish);
        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent startActivity = new Intent(Stats.this, MainActivity.class);
                startActivity(startActivity);
            }
        });


        //ArrayList<Question> getTriviaInfo =
        LinearLayout ll = (LinearLayout) findViewById(R.id.linearLayout_inScrollviewStats);

        answerList = (HashMap<Integer, String>) getIntent().getSerializableExtra("ANSWERS");


        triviaList = (ArrayList<Question>) getIntent().getSerializableExtra("QUESTIONS");

        if (answerList != null) {
            Space space = new Space(Stats.this);


            LinearLayout.LayoutParams param = new LinearLayout.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    75);
            space.setMinimumHeight(40);
            space.setLayoutParams(param);

            for (int i = 0; i < triviaList.size(); i++) {
                if (answerList.get(i) != null) {

                    String answered = answerList.get(i).toString();
                    Log.d(logText, "" + answerList.get(i).toString());
                    int y = Integer.parseInt(triviaList.get(i).getAnswer());
                    if (answered.equals(triviaList.get(i).getChoices()[y - 1])) {
                        correctAnswerArray.add(i);
                        correctAnsCount++;
                        Log.d(logText, "right answer");
                    } else if (answered.equals("Not Answered")) {
                        wrongAnswerArray.add(i);
                    } else {
                        Log.d(logText, "wrong answer");
                        wrongAnswerArray.add(i);
                        //answerList.put(i,"Not Answered");
                    }
                } else {
                    wrongAnswerArray.add(i);
                    answerList.put(i, "Timed out");
                    Log.d(logText, "Timed out question added");
                }
            }


            if (wrongAnswerArray != null) {
                for (int j = 0; j < wrongAnswerArray.size(); j++) {
                    int k = wrongAnswerArray.get(j);
                    Question correctAns = triviaList.get(k);
                    TextView tvQuestion = new TextView(Stats.this);
                    TextView tvYourAns = new TextView(Stats.this);
                    TextView tvCorrectAns = new TextView(Stats.this);

                    int y = Integer.parseInt(correctAns.getAnswer());
                    tvQuestion.setText("Q" + (k + 1) + ": " + correctAns.getText().toString());
                    tvYourAns.setText("Your Ans: " + answerList.get(k).toString());
                    tvCorrectAns.setText("Correct Ans: " + correctAns.getChoices()[y - 1]);
                    tvYourAns.setBackgroundColor(Color.RED);
                    try {
                        ll.addView(tvQuestion);
                        ll.addView(tvYourAns);
                        ll.addView(tvCorrectAns);
                        ll.addView(space);
                    } catch (Exception e) {
                        Log.d(logText, "Line 99" + e.getMessage());
                    }

                }

            }


            //Log.d(logText, "Line 103"+e.getMessage());


            /*if (correctAnswerArray != null) {
                for (int j = 0; j < correctAnswerArray.size(); j++) {
                    int k = correctAnswerArray.get(j);
                    Question correctAns = triviaList.get(k);
                    TextView tvQuestion = new TextView(Stats.this);
                    TextView tvYourAns = new TextView(Stats.this);
                    TextView tvCorrectAns = new TextView(Stats.this);

                    int y = Integer.parseInt(correctAns.getAnswer());
                    tvQuestion.setText("Q" + (k + 1) + ": " + correctAns.getText().toString());
                    tvYourAns.setText("Your Ans: " + answerList.get(k).toString());
                    tvCorrectAns.setText("Correct Ans: " + correctAns.getChoices()[y - 1]);
                    tvYourAns.setBackgroundColor(Color.GREEN);
                    try {
                        ll.addView(tvQuestion);
                        ll.addView(tvYourAns);
                        ll.addView(tvCorrectAns);
                        ll.addView(space);
                    } catch (Exception e) {
                        Log.d(logText, "Line 129" + e.getMessage());
                    }

                }
            }*/

            performance = ((correctAnsCount * 100) / triviaList.size());

            ProgressBar pb = (ProgressBar) findViewById(R.id.progressBar_CorrectAnswers);
            pb.setMax(100);
            pb.setProgress(performance);

            TextView tvPerformance = (TextView) findViewById(R.id.textView_performancePercentage);
            tvPerformance.setText("" + performance + "%");


        }


    }
}

