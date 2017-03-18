package com.uncc.prateek.cnnnewsapp;
/*
    a) Assignment #. : InClass05
    b) Full name of the student. Prateek Mahendrakar
*/
/**
 * Created by Prateek on 13-02-2017.
 */


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

import org.json.JSONException;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

public class AsyncGetNews extends AsyncTask<String, Void, ArrayList<News>>{
    String logText = "AsyncGetNews";
    INewsData activity;
    ProgressDialog progressDialog=null;
    public AsyncGetNews(INewsData data) {
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
                InputStream in = con.getInputStream();
                ArrayList<News> newses = NewsUtil.NewsesSAXParser.parseNews(in);
                return newses;
            }
        } catch (MalformedURLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } /*catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}*/
        catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Runs on the UI thread before {@link #doInBackground}.
     *
     * @see #onPostExecute
     * @see #doInBackground
     */
    @Override
    protected void onPreExecute() {
        super.onPreExecute();
         progressDialog=ProgressDialog.show(activity.getContext(),"Loading","");
    }

    @Override
    protected void onPostExecute(ArrayList<News> result) {
        if(result!=null){
            Log.d(logText, "Newses are : "+result.toString());
            activity.loadData(result);
        }else{
            Log.d(logText, "NUll returned");
        }
        progressDialog.dismiss();

    }

    static public interface INewsData{
        public void loadData(ArrayList<News> result);
        public Context getContext();
    }
}