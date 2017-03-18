package com.uncc.prateek.gamesdb;
/*
	Assignment : InClass 06
	FullName of Student : Prateek Mahendrakar
*/
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class SimilarGamesActivity extends AppCompatActivity implements AsyncGetSimilarDetails.IGameData {
    private static String URLBase = "http://thegamesdb.net/api/GetGame.php?id=";//http://wiki.thegamesdb.net/index.php/GetGamesList";
    private static String URL = "";
    String gameName = "";
    private ArrayList<Game> similarGames = new ArrayList<Game>();
    private String searchKey = "";
    private String logText = "SimilarGamesActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_activity_similar_games);

        similarGames = (ArrayList<Game>) getIntent().getSerializableExtra("SIMILAR_GAMES_LIST");
        gameName = (String) getIntent().getSerializableExtra("GAME_NAME");
        Log.d(logText, similarGames.toString());
        if (similarGames != null) {
            String[] values = new String[3];
            for (int i = 0; i < similarGames.size(); i++) {
                Game game = similarGames.get(i);
                if (game != null) {
                    try {
                        values[i] = game.getId().toString();
                        Log.d(logText, "values[" + i + "] = " + values[i]);
                    } catch (Exception e) {
                        Log.d(logText, "Exception at values[i] " + e.getMessage());
                    }

                } else {
                    Log.d(logText, "game is NULL");
                }

            }
            new AsyncGetSimilarDetails(SimilarGamesActivity.this).execute(values);
        }

        findViewById(R.id.button_Finish2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }


    @Override
    public void loadData(ArrayList<Game> result) {
        if (result != null) {
            Log.d(logText, "loadData");
            TextView tvTitle = (TextView) findViewById(R.id.textView_SimilarGamesTo);
            tvTitle.setText("Similar games to " + gameName);
/*            ArrayList<String> temp = new ArrayList<String>();
            for (Game g : result) {
                temp.add(g.toString());
            }*/
            List<Game> temp1 = result;
            ListView liView = (ListView) findViewById(R.id.listView_similarGamesList);
            //String[] values = temp.toArray(new String[temp.size()]);
            //ArrayAdapter<Game> adapter = new ArrayAdapter<Game>(this, R.layout.layout_listview, result);
            CustomAdapter custAdapter =  new CustomAdapter(this,R.layout.layout_listview,temp1);
            liView.setAdapter(custAdapter);
            /*ListView liView = (ListView) findViewById(R.id.listView_similarGamesList);
            //String[] values= (String[]) temp.toArray();
            String[] values = temp.toArray(new String[temp.size()]);
            ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, values);
            liView.setAdapter(adapter);*/

            liView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    Log.d(logText,"Item clicked was "+position + " id is "+ id );
                    Game selectedGame  = similarGames.get(position);
                    Log.d(logText,"game to string " +selectedGame.toString());
                    Log.d(logText,"game to short string " +selectedGame.toShortString());


                    String gameId = selectedGame.getId();
                    Intent intent = new Intent(SimilarGamesActivity.this, GameDetailsActivity.class);
                    intent.putExtra("gameId", gameId);
                    intent.putExtra("fromDetails", true);
                    startActivity(intent);
                }
            });
        } else {
            Log.d(logText, "Nothing here");
        }


    }

    @Override
    public Context getContext() {
        return this;
    }
}
