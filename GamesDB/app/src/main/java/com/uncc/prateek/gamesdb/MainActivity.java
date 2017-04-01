package com.uncc.prateek.gamesdb;
/*
    a. Assignment #. Homework 05
	b. File Name. MainActivity.java
	c. Full name of all students in your group. Prateek Mahendrakar , Siva Ram Praneeth Vemulapalli
*/

import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements View.OnClickListener, AsyncGetGames.IGameData {
    private static String URLBase = "http://thegamesdb.net/api/GetGamesList.php?name=";//http://wiki.thegamesdb.net/index.php/GetGamesList";
    private static String URL = "";
    private String searchKey = "";
    private String logText = "main";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_activity_main);

        Button buttonSearch = (Button) findViewById(R.id.button_search);
        Button buttonGo = (Button) findViewById(R.id.button_Go);
        EditText editText_GameName = (EditText) findViewById(R.id.editText_GameName);
        RadioGroup rgGamesList = (RadioGroup) findViewById(R.id.radioGroup_gamesList);


        buttonSearch.setOnClickListener(this);


    }


    @Override
    public void onClick(View v) {
        int button_id = v.getId();
        switch (button_id) {
            case R.id.button_search: {

                EditText editText_GameName = (EditText) findViewById(R.id.editText_GameName);
                searchKey = editText_GameName.getText().toString();
                URL = URLBase + searchKey;

                Log.d(logText, "URL : " + URL);
                new AsyncGetGames(MainActivity.this).execute(URL);
                break;
            }
            case R.id.button_Go: {
                RadioGroup rgGamesList = (RadioGroup) findViewById(R.id.radioGroup_gamesList);
                int checkedId = rgGamesList.getCheckedRadioButtonId();
                RadioButton rb = (RadioButton) findViewById(checkedId);
                String checkedText = String.valueOf(rb.getText());
                Log.d(logText, "checkedId : " + checkedId);
                Log.d(logText, "checkedText : " + checkedText);
                Intent intent = new Intent(this, GameDetailsActivity.class);
                intent.putExtra("gameId", checkedId);
                startActivity(intent);
                break;
            }
        }

    }

    @TargetApi(17)
    @Override
    public void loadData(ArrayList<Game> result) {
        RadioGroup rgGamesList = (RadioGroup) findViewById(R.id.radioGroup_gamesList);
        if (result != null) {
            for (Game game : result) {
                RadioButton newRadioButton = new RadioButton(this);
                //newRadioButton.setId(newRadioButton.generateViewId());
                int tempId = 0;
                try {
                    tempId = Integer.parseInt(game.getId());
                } catch (Exception e) {
                    tempId = newRadioButton.generateViewId();
                }

                newRadioButton.setId(tempId);
                newRadioButton.setText(game.toShortString());
                if (rgGamesList != null) {
                    rgGamesList.addView(newRadioButton);
                    Log.d(logText, "new radiobutton added");
                } else {
                    Log.d(logText, "rgGamesList is Null");
                }


            }
            findViewById(R.id.button_Go).setOnClickListener(this);
        }


    }

    @Override
    public Context getContext() {
        return this;
    }
}
