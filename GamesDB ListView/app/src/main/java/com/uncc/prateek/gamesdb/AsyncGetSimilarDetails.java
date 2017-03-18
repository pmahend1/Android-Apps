package com.uncc.prateek.gamesdb;
/*
	Assignment : InClass 06
	FullName of Student : Prateek Mahendrakar
*/


import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import java.util.ArrayList;

public class AsyncGetSimilarDetails extends AsyncTask<String, Void, ArrayList<Game>> {
    String logText = "AsyncGetSimilarDetails";
    IGameData activity;
    ProgressDialog progressDialog = null;

    public AsyncGetSimilarDetails(IGameData data) {
        this.activity = data;
    }

    @Override
    protected ArrayList<Game> doInBackground(String... params) {
        String urlString = params[0];
        ArrayList<Game> gameList = GameUtil.GameDetailsSAXParser.parseSimilarGames(params);
        return gameList;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        progressDialog = ProgressDialog.show(activity.getContext(), "Loading", "");
    }

    @Override
    protected void onPostExecute(ArrayList<Game> result) {
        if (result != null) {
            Log.d(logText, "Games are : " + result.toString());
            activity.loadData(result);
        } else {
            Log.d(logText, "No games returned");
        }
        progressDialog.dismiss();

    }

    static public interface IGameData {
        public void loadData(ArrayList<Game> result);

        public Context getContext();
    }
}
