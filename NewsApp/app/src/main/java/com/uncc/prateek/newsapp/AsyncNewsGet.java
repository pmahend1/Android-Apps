package com.uncc.prateek.newsapp;

/**
 * Created by Prateek on 06-02-2017.
 */

/*a. Assignment #. InClass 04
b. File Name. AsyncNewsGet.java
c. Full name of all students in your group. Prateek Mahendrakar , Siva Ram Praneeth Vemulapalli*/

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Array;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.LinkedList;

import org.json.JSONException;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.util.Log;

public class AsyncNewsGet extends AsyncTask<String, Void, ArrayList<News>> {
    INewsData activity;
    //MainActivity activity;

    String logText = "AsyncNewsGet";
    public AsyncNewsGet(INewsData data) {
        this.activity=data;
    }

    @Override
    protected ArrayList<News> doInBackground(String... params) {
        String urlString = params[0];
        try {
            URL url = new URL(urlString);
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("GET");
            con.connect();
            int statusCode = con.getResponseCode();
            if (statusCode == HttpURLConnection.HTTP_OK) {
                BufferedReader reader = new BufferedReader(
                        new InputStreamReader(con.getInputStream()));
                StringBuilder sb = new StringBuilder();
                String line = reader.readLine();
                while (line != null) {
                    sb.append(line);
                    line = reader.readLine();
                }
                 Log.d("logText", sb.toString());
                ArrayList<News> newses =  NewsUtil.NewsJSONParser.parseNews(sb.toString());
                return newses;
            }
        } catch (MalformedURLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            Log.d(logText,e.getStackTrace().toString());
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            Log.d(logText,e.getStackTrace().toString());
        } // catch (JSONException e) {
        catch (JSONException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            Log.d(logText,e.getStackTrace().toString());
        }
        return null;
    }

    @Override
    protected void onPostExecute(ArrayList<News> result) {
        if (result != null) {
            Log.d(logText, result.toString());
            super.onPostExecute(result);
            try{
                activity.loadData(result);
                //super.onPostExecute(result);
            }catch (Exception e){
                Log.d(logText,"Exception"+e.getMessage());
            }

        } else {
            Log.d(logText, "null result");
        }

    }

   static public interface INewsData{
       public void loadData(ArrayList<News> result);
       public Context getContext();
   }
}