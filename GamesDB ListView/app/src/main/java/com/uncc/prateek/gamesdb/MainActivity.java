package com.uncc.prateek.gamesdb;
/*
	Assignment : InClass 06
	FullName of Student : Prateek Mahendrakar
*/

import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnClickListener, AsyncGetGames.IGameData , AdapterView.OnItemClickListener {
    private static String URLBase = "http://thegamesdb.net/api/GetGamesList.php?name=";//http://wiki.thegamesdb.net/index.php/GetGamesList";
    private static String URL = "";
    private String searchKey = "";
    private String logText = "main";
    private ArrayList<Game> gamesList = new ArrayList<Game>();
    List<Game> tempGamesList=null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_activity_main);

        Button buttonSearch = (Button) findViewById(R.id.button_search);
        Button buttonGo = (Button) findViewById(R.id.button_Go);
        EditText editText_GameName = (EditText) findViewById(R.id.editText_GameName);
        ListView lv = (ListView) findViewById(R.id.listView_results);
        //RadioGroup rgGamesList = (RadioGroup) findViewById(R.id.radioGroup_gamesList);
        lv.getOnItemClickListener();
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
/*                RadioGroup rgGamesList = (RadioGroup) findViewById(R.id.radioGroup_gamesList);
                int checkedId = rgGamesList.getCheckedRadioButtonId();
                RadioButton rb = (RadioButton) findViewById(checkedId);
                String checkedText = String.valueOf(rb.getText());
                Log.d(logText, "checkedId : " + checkedId);
                Log.d(logText, "checkedText : " + checkedText);
                Intent intent = new Intent(this, GameDetailsActivity.class);
                intent.putExtra("gameId", checkedId);
                startActivity(intent);*/
                break;
            }
        }

    }

    @TargetApi(17)
    @Override
    public void loadData(final ArrayList<Game> result) {
        //RadioGroup rgGamesList = (RadioGroup) findViewById(R.id.radioGroup_gamesList);
        if (result != null) {
           /* for (Game game : result) {
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


            }*/
            gamesList = result;
            //ArrayList<Game> tempGamesList=null;
//            System.arraycopy(result,1,tempGamesList,10,9);
            final List<Game> tempGamesList = result.subList(0,10);

            Log.d(logText,"Subarray"+tempGamesList.toString());

            ListView lv = (ListView) findViewById(R.id.listView_results);
            //String[] values = temp.toArray(new String[temp.size()]);
            //ArrayAdapter<Game> adapter = new ArrayAdapter<Game>(this, R.layout.layout_listview, result);
            CustomAdapter custAdapter =  new CustomAdapter(this,R.layout.layout_listview,tempGamesList);
            lv.setAdapter(custAdapter);

            lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    Log.d(logText,"here Item clicked was "+position + " id is "+ id );
                    Game selectedGame  = tempGamesList.get(position);
                    Log.d(logText,"game to string " +selectedGame.toString());
                    Log.d(logText,"game to short string " +selectedGame.toShortString());


                    String gameId = selectedGame.getId();
                    Intent intent = new Intent(MainActivity.this, GameDetailsActivity.class);
                    intent.putExtra("gameId", gameId);
                    startActivity(intent);
                }
            });



            findViewById(R.id.button_Go).setOnClickListener(this);
        }


    }

    @Override
    public Context getContext() {
        return this;
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Log.d(logText,"Item clicked was "+position + " id is "+ id );
        Intent intent = new Intent(this, GameDetailsActivity.class);
        intent.putExtra("gameId", position);
        startActivity(intent);

    }
}
