package com.example.praneeth.triviaapp;
/*  a. Assignment #. Homework 4
    b. File Name. : Trivia.java
    c. Full name of all students in your group. : Prateek , Praneeth
    */
import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;


public class Trivia extends AppCompatActivity implements View.OnClickListener, AsyncGetImage.IActivity {
    ArrayList<Question> questionsArrayList = new ArrayList<>();
    static String logText = "Trivia";
    HashMap<Integer, String> answersMap = new HashMap<Integer, String>();
    int i = 0;
    boolean triviaActivityOver = false;

    @TargetApi(17)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_trivia_activity);

        questionsArrayList = (ArrayList<Question>) getIntent().getSerializableExtra("QUESTIONS_LIST");
        final TextView textView_timeLeft = (TextView) findViewById(R.id.textView_timeLeft);

        if (questionsArrayList != null) {
            Log.d(logText, "questionsArrayList!=null");
            Question firstQuestion = questionsArrayList.get(0);
            i = 0;

            int questionID = firstQuestion.getId() + 1;
            TextView tvQuestionNum = (TextView) findViewById(R.id.textView_QNo);
            tvQuestionNum.setText("Q" + questionID);

            TextView tvQuestion = (TextView) findViewById(R.id.textView_Question);
            Log.d(logText, "" + tvQuestion.getText());
            tvQuestion.setText(firstQuestion.getText().toString());

            new AsyncGetImage(Trivia.this).execute(firstQuestion.getImage());
            Log.d(logText, "firstQuestion.getText().toString()" + firstQuestion.getText().toString());

            RadioGroup radioGroup_choices = (RadioGroup) findViewById(R.id.radioGroup_choices);

            for (int i = 0; i < firstQuestion.getChoices().length; i++) {
                if (firstQuestion.getChoices()[i] != null) {
                    RadioButton newChoice = new RadioButton(this);
                    //newChoice.setHeight(50);
                    newChoice.setTextSize(12);
                    int x = newChoice.generateViewId();
                    Log.d(logText, "id generated " + x);
                    newChoice.setId(x);
                    Log.d(logText, "choice :" + firstQuestion.getChoices()[i]);
                    newChoice.setText(firstQuestion.getChoices()[i].toString());
                    radioGroup_choices.addView(newChoice);
                }

            }

            new CountDownTimer(120000, 1000) { // adjust the milli seconds here

                public void onTick(long millisUntilFinished) {

                    textView_timeLeft.setText((millisUntilFinished / 1000) + " sec");
                }

                public void onFinish() {
                    textView_timeLeft.setText("0 sec");
                    if (triviaActivityOver != true) {
                        triviaActivityOver = true;
                        Intent statsIntent = new Intent(Trivia.this, Stats.class);
                        statsIntent.putExtra("ANSWERS", answersMap);
                        statsIntent.putExtra("QUESTIONS", questionsArrayList);
                        startActivity(statsIntent);
                    }

                }
            }.start();

            findViewById(R.id.button_next).setOnClickListener(this);
            findViewById(R.id.button_previous).setOnClickListener(this);

        }


    }

    public String getLastSavedAnswer(int questionID) {
        try {
            String answer = answersMap.get(questionID);

            return answer;

        } catch (Exception e) {
            Log.d(logText,"Exc at getLastSavedAnswer"+e.getMessage());
            return "";
        }


    }

    @TargetApi(17)
    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.button_previous) {
            if (i > 0) {
                final RadioGroup radioGroup_choices = (RadioGroup) findViewById(R.id.radioGroup_choices);
                int checkedID = radioGroup_choices.getCheckedRadioButtonId();

                try {
                    checkedID = radioGroup_choices.getCheckedRadioButtonId();
                    Log.d(logText, "checkedID : " + checkedID);
                    RadioButton rb = (RadioButton) findViewById(checkedID);
                    String selectedValue = rb.getText().toString();
                    answersMap.put(i, selectedValue);
                } catch (Exception ex) {
                    Log.d(logText, "RadiobuttonChecked exception" + ex.getMessage());
                    answersMap.put(i, "NULL");
                }
                i--;
                Question firstQuestion = questionsArrayList.get(i);
                int questionID = firstQuestion.getId() + 1;
                TextView tvQuestionNum = (TextView) findViewById(R.id.textView_QNo);
                tvQuestionNum.setText("Q" + questionID);

                TextView tvQuestion = (TextView) findViewById(R.id.textView_Question);
                Log.d(logText, "" + tvQuestion.getText());
                tvQuestion.setText(firstQuestion.getText().toString());

                findViewById(R.id.button_previous).setClickable(false);
                findViewById(R.id.button_next).setClickable(false);

                Log.d(logText, "firstQuestion.getText().toString()" + firstQuestion.getText().toString());
                new AsyncGetImage(Trivia.this).execute(firstQuestion.getImage());


                //RadioGroup radioGroup_choices = (RadioGroup) findViewById(R.id.radioGroup_choices);
                radioGroup_choices.removeAllViews();

                for (int j = 0; j< firstQuestion.getChoices().length; j++) {
                    if (firstQuestion.getChoices()[j] != null) {
                        RadioButton newChoice = new RadioButton(this);
                        //newChoice.setHeight(50);
                        int x = newChoice.generateViewId();
                        Log.d(logText, "id generated :" + x);
                        newChoice.setId(x);
                        newChoice.setTextSize(12);
                        Log.d(logText, "choice :" + firstQuestion.getChoices()[j]);

                        newChoice.setText(firstQuestion.getChoices()[j].toString());
                        String lastSaveAns = getLastSavedAnswer(i);

                        if (firstQuestion.getChoices()[j].toString().equals(lastSaveAns)) {
                            newChoice.setChecked(true);
                        }
                        radioGroup_choices.addView(newChoice);
                    }

                }
                findViewById(R.id.button_previous).setClickable(true);
                findViewById(R.id.button_next).setClickable(true);

            } else {
                findViewById(R.id.button_previous).setClickable(false);
            }
        } else if (v.getId() == R.id.button_next) {
            if (i < questionsArrayList.size() - 1) {
                final RadioGroup radioGroup_choices = (RadioGroup) findViewById(R.id.radioGroup_choices);
                int checkedID = radioGroup_choices.getCheckedRadioButtonId();

                try {
                    //String selectedValue = ((RadioButton)findViewById(radioGroup_choices.getCheckedRadioButtonId())).getText().toString();
                    checkedID = radioGroup_choices.getCheckedRadioButtonId();
                    Log.d(logText, "checkedID : " + checkedID);
                    RadioButton rb = (RadioButton) findViewById(checkedID);
                    String selectedValue = rb.getText().toString();
                    answersMap.put(i, selectedValue);
                } catch (Exception ex) {
                    Log.d(logText, "RadiobuttonChecked exception" + ex.getMessage());
                    answersMap.put(i, "Not Answered");
                }

                i++;
                Question firstQuestion = questionsArrayList.get(i);
                int questionID = firstQuestion.getId() + 1;
                TextView tvQuestionNum = (TextView) findViewById(R.id.textView_QNo);
                tvQuestionNum.setText("Q" + questionID);

                TextView tvQuestion = (TextView) findViewById(R.id.textView_Question);
                Log.d(logText, "" + tvQuestion.getText());
                tvQuestion.setText(firstQuestion.getText().toString());

                Log.d(logText, "firstQuestion.getText().toString()" + firstQuestion.getText().toString());
                findViewById(R.id.button_previous).setClickable(false);
                findViewById(R.id.button_next).setClickable(false);
                new AsyncGetImage(Trivia.this).execute(firstQuestion.getImage());

                radioGroup_choices.removeAllViews();

                for (int j = 0; j < firstQuestion.getChoices().length; j++) {
                    if (firstQuestion.getChoices()[j] != null) {
                        RadioButton newChoice = new RadioButton(this);
                        //newChoice.setHeight(50);
                        int x = newChoice.generateViewId();
                        Log.d(logText, "id generated :" + x);
                        newChoice.setId(x);
                        newChoice.setTextSize(12);
                        Log.d(logText, "choice :" + firstQuestion.getChoices()[j]);
                        newChoice.setText(firstQuestion.getChoices()[j].toString());
                        String lastSaveAns = getLastSavedAnswer(i);
                        if (firstQuestion.getChoices()[j].toString().equals(lastSaveAns)) {
                            newChoice.setChecked(true);
                        }
                        radioGroup_choices.addView(newChoice);
                    }

                }
                findViewById(R.id.button_previous).setClickable(true);
                findViewById(R.id.button_next).setClickable(true);

            } else {
                Log.d(logText, "inside if of i=" + questionsArrayList.size());
                LinearLayout linearLayout_buttons = (LinearLayout) findViewById(R.id.linearLayout_buttons);
                linearLayout_buttons.removeAllViews();

                try {
                    Button triviaFinishButton = new Button(Trivia.this);

                    triviaFinishButton.setId(R.id.triviaFinish);
                    LinearLayout.LayoutParams param = new LinearLayout.LayoutParams(
                            ViewGroup.LayoutParams.MATCH_PARENT,
                            ViewGroup.LayoutParams.MATCH_PARENT);
                    triviaFinishButton.setLayoutParams(param);
                    triviaFinishButton.setText("Finish");

                    linearLayout_buttons.addView(triviaFinishButton);

                    findViewById(R.id.triviaFinish).setOnClickListener(this);
                } catch (Exception e) {
                    Log.d(logText, "over step" + e.getMessage());
                }

            }
        } else if (v.getId() == R.id.triviaFinish) {
            triviaActivityOver = true;
            Intent statsIntent = new Intent(this, Stats.class);
            statsIntent.putExtra("ANSWERS", answersMap);
            statsIntent.putExtra("QUESTIONS", questionsArrayList);
            startActivity(statsIntent);
        }
    }


    @Override
    public Context getContext() {
        return this;
    }
}
