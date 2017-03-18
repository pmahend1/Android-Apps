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
import android.view.ViewGroup;
import android.view.ViewParent;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;

public class GameDetailsActivity extends AppCompatActivity implements View.OnClickListener, AsyncGetGameDetails.IGameData , AsyncGetImage.IActivity{

    private static String URLBase = "http://thegamesdb.net/api/GetGame.php?id=";//http://wiki.thegamesdb.net/index.php/GetGamesList";
    private static String URL = "";
    private String searchKey = "";
    private String logText = "main";
    private String youTubeURL;
    private ArrayList<Game> similarGames = new ArrayList<Game>();
    private String gameName="";
    boolean fromDetails=false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_activity_game_details);

        String  gameId = (String) getIntent().getSerializableExtra("gameId");
        fromDetails = (boolean) getIntent().getSerializableExtra("fromDetails");

        Log.d(logText, "Game id selected was : " + gameId);
        searchKey = gameId; //String.valueOf(gameId);
        if (searchKey != null) {
            URL = URLBase + searchKey;
            Log.d(logText, "URL is :" + URL);
            new AsyncGetGameDetails(this).execute(URL);

        }

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            /*case R.id.button_PlayTrailer:{
                if(youTubeURL!=null){
                    Intent webViewIntent = new Intent(this,ViewTrailerActivity.class);
                    webViewIntent.putExtra("YOUTUBE_URL",youTubeURL);
                    webViewIntent.putExtra("GAME_NAME",gameName);
                    startActivity(webViewIntent);
                    break;
                }else{
                    Toast.makeText(this, "No trailer found!", Toast.LENGTH_SHORT).show();
                    break;
                }

            }*/
            case R.id.button_SimilarGames:{
                if(similarGames!=null){
                    Intent similarGamesIntent  = new Intent(this, SimilarGamesActivity.class);
                    similarGamesIntent.putExtra("SIMILAR_GAMES_LIST",similarGames);
                    similarGamesIntent.putExtra("GAME_NAME",gameName);
                    startActivity(similarGamesIntent);
                }else{
                    Toast.makeText(this, "No similar games exist!", Toast.LENGTH_SHORT).show();
                }
                break;
            }
            case R.id.button_Finish:{
                finish();
                break;
            }
        }

    }

    @Override
    public void loadData(Game result) {
        Log.d(logText,"loadData");
        if(result!=null){
            Log.d(logText,result.toString());
            similarGames = result.getSimilar();
            if(similarGames!=null){
                Log.d(logText,"similar games are :"+similarGames.toString());

            }
            youTubeURL=result.getYouTube();
            gameName = result.getGameTitle();

            ImageView image = (ImageView) findViewById(R.id.imageView_gameImage);
            try{
                new AsyncGetImage(this).execute(result.getImages()[0]);
            }catch (Exception e){
                Log.d(logText,"No image");
                ((ImageView) findViewById(R.id.imageView_gameImage)).setImageResource(R.drawable.no_image);
            }

            TextView tvGameName = (TextView) findViewById(R.id.textView_GameName);

            tvGameName.setText(result.getGameTitle());

            TextView tvOverView = (TextView) findViewById(R.id.textView_Overview);

            tvOverView.setText(result.getOverView());

            TextView tvGenre = (TextView) findViewById(R.id.textView_ViewGenre);

            tvGenre.setText(Arrays.toString(result.getGenres()).replace("[","").replace("]",""));

            TextView tvPublisher
                    = (TextView) findViewById(R.id.textView_ViewPublisher);

            tvPublisher.setText(result.getPublisher());

            //findViewById(R.id.button_PlayTrailer).setOnClickListener(this);
            findViewById(R.id.button_SimilarGames).setOnClickListener(this);
            findViewById(R.id.button_Finish).setOnClickListener(this);
        }
        if(fromDetails){
            Button buttonSimilar = (Button) findViewById(R.id.button_SimilarGames);

            //View namebar = view.findViewById(R.id.button_SimilarGames);
            ((ViewGroup) buttonSimilar.getParent()).removeView(buttonSimilar);


        }


    }

    @Override
    public Context getContext() {
        return this;
    }

    @Override
    public Context getContextForImage() {
        return this;
    }
}
