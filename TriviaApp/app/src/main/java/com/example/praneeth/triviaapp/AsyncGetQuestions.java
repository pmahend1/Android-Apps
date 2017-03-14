package com.example.praneeth.triviaapp;
/*  a. Assignment #. Homework 4
    b. File Name. : AsyncGetQuestions.java
    c. Full name of all students in your group. : Prateek , Praneeth
    */
import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

public class AsyncGetQuestions extends AsyncTask<String, Void, ArrayList<Question>> {

    IQuestion activity;

    String logText = "AsyncGetQuestions";

    ProgressDialog progressDialog ;

    public AsyncGetQuestions(IQuestion act) {
        this.activity = act;
    }

    @Override
    protected ArrayList<Question> doInBackground(String... strings) {


        String urlString = strings[0];
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
                Log.d("logText", "sb.toString()"+sb.toString());
                progressDialog.setMax(10000);
                for(int i=0;i<100;i++){
                    for(int j=0;j<10000;j++){
                        progressDialog.setProgress(j);
                    }

                }

                ArrayList<Question> questions =  QuestionUtil.QuestionJSONParser.parseQuestions(sb.toString());
                Log.d("logText", "back from QuestionUtil.QuestionJSONParser.parseQuestions : value :"+sb.toString());
                return questions;
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
            Log.d(logText,e.getStackTrace().toString());
        } catch (IOException e) {
            e.printStackTrace();
            Log.d(logText,e.getStackTrace().toString());
        }
        catch (JSONException e) {
            e.printStackTrace();
            Log.d(logText,e.getStackTrace().toString());
        }
        return null;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        progressDialog = ProgressDialog.show(activity.getContext(),"Loading Trivia","");
        progressDialog.setCancelable(false);

    }

    @Override
    protected void onPostExecute(ArrayList<Question> result) {
        super.onPostExecute(result);
        if (result != null) {
            try {
                activity.display(result);
                progressDialog.dismiss();
            } catch (JSONException e) {
                e.printStackTrace();
                Log.d(logText, "Exception" + e.getMessage());
            }
        } else {
            Log.d(logText, "null result");
            progressDialog.dismiss();
        }

    }

    static public interface IQuestion {
        public void display(ArrayList<Question> result) throws JSONException;

        public Context getContext();
    }


}
