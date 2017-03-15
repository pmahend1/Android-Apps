package com.example.praneeth.itunestoppaidapps;
/*
        a. Assignment : Homework 6
        b. File Name. : AsyncAppsFeed.java
        c. Group Name : Group 06
        d. Students in  group : Prateek Mahendrakar , Siva Ram Praneeth Vemulapalli
 */

import android.os.AsyncTask;
import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.ArrayList;


public class AsyncAppsFeed extends AsyncTask<String, Void, ArrayList<AppEntry>> {

    private static final String LOG_TEXT = "AsyncAppsFeed";
    MainActivity activity;

    public AsyncAppsFeed(MainActivity activity) {
        this.activity = activity;
    }

    @Override
    protected void onPostExecute(ArrayList<AppEntry> apps) {
        super.onPostExecute(apps);
        Log.d(LOG_TEXT, "onPostExecute: " + apps);

        activity.printListItems(apps);
    }

    @Override
    protected ArrayList<AppEntry> doInBackground(String... params) {
        try {
            URL url = null;
            try {
                url = new URL(params[0]);
                HttpURLConnection con = (HttpURLConnection) url.openConnection();
                con.setRequestMethod("GET");
                con.connect();
                int status = con.getResponseCode();
                Log.d(LOG_TEXT, con.getResponseCode() + " -- " + HttpURLConnection.HTTP_OK);
                if (status == HttpURLConnection.HTTP_OK) {
                    BufferedReader reader = new BufferedReader(new InputStreamReader(con.getInputStream()));
                    String line = "";
                    StringBuilder sb = new StringBuilder();
                    while ((line = reader.readLine()) != null) {
                        sb.append(line);
                    }
                    Log.d(LOG_TEXT, sb.toString() + LOG_TEXT);
                    return JsonParser.parseData(sb.toString());
                }
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (ProtocolException e) {
                e.printStackTrace();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public interface LinkData {
        void printListItems(ArrayList<AppEntry> appFeeds);
    }
}
