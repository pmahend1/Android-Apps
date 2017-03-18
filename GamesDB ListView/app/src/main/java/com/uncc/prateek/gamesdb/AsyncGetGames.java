package com.uncc.prateek.gamesdb;
/*
	Assignment : InClass 06
	FullName of Student : Prateek Mahendrakar
*/


import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

public class AsyncGetGames extends AsyncTask<String, Void, ArrayList<Game>> {
    String logText = "AsyncGetGames";
    IGameData activity;
    ProgressDialog progressDialog = null;

    public AsyncGetGames(IGameData data) {
        this.activity = data;
    }

    @Override
    protected ArrayList<Game> doInBackground(String... params) {
        String urlString = params[0];
        try {
            URL url = new URL(urlString);
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("GET");
            con.connect();
            int statusCode = con.getResponseCode();
            if (statusCode == HttpURLConnection.HTTP_OK) {
                InputStream in = con.getInputStream();
                ArrayList<Game> gameList = GameUtil.GamesSAXParser.getSearchList(in);
                return gameList;
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
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
