package com.uncc.prateek.gamesdb;
/*
    a. Assignment #. Homework 05
	b. File Name. AsyncGetGameDetails.java
	c. Full name of all students in your group. Prateek Mahendrakar , Siva Ram Praneeth Vemulapalli
*/
/**
 * Created by Prateek on 18-02-2017.
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

public class AsyncGetGameDetails extends AsyncTask<String, Void, Game> {
    String logText = "AsyncGetGameDetls";
    IGameData activity;
    ProgressDialog progressDialog = null;

    public AsyncGetGameDetails(IGameData data) {
        this.activity = data;
    }

    @Override
    protected Game doInBackground(String... params) {
        String urlString = params[0];
        try {
            URL url = new URL(urlString);
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("GET");
            con.connect();
            int statusCode = con.getResponseCode();
            if (statusCode == HttpURLConnection.HTTP_OK) {
                InputStream in = con.getInputStream();
                Game game = GameUtil.GameDetailsSAXParser.parseGameDetailsData(in);
                return game;
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
        progressDialog = ProgressDialog.show(activity.getContext(), "Loading Game Details", "");
    }

    @Override
    protected void onPostExecute(Game result) {
        if (result != null) {
            Log.d(logText, "Game details " + result.toString());
            progressDialog.dismiss();
            activity.loadData(result);
        } else {
            Log.d(logText, "No games returned");
            progressDialog.dismiss();
        }


    }

    static public interface IGameData {
        public void loadData(Game result);

        public Context getContext();
    }
}
