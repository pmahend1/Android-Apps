package com.example.praneeth.chitchat;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.preference.PreferenceManager;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
/*a. Assignment #. In Class 09
        b. File Name. ___
        c. Full name of all students in your group. : Prateek , Praneeth*/
/**
 * Created by Praneeth on 3/27/2017.
 */


public class AsyncGetSubSciptions extends AsyncTask<String, Void, ArrayList<Channel>> {

    private String token;
    IContextSubScription context;
    ArrayList<Channel> channelList = new ArrayList<Channel>();

    public AsyncGetSubSciptions(IContextSubScription context) {
        this.context = context;

    }

    @Override
    protected ArrayList<Channel> doInBackground(String... string) {


        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context.getContextSub());
        token = preferences.getString("token","");

        OkHttpClient client = new OkHttpClient();
        RequestBody formBody = new FormBody.Builder()
                .add("Authorization", token)
                .build();

        Request request = new Request.Builder()
                .url("http://52.90.79.130:8080/Groups/api/get/subscriptions")
                .post(formBody)
                .build();


        Response response = null;
        try {
            response = client.newCall(request).execute();
            if (!response.isSuccessful()) throw new Exception("Unexpected code " + response);
            System.out.println("Server: " + response.header("Server"));
            System.out.println("Date: " + response.header("Date"));
            System.out.println("Vary: " + response.headers("Vary"));

            String responseString  = response.body().string();
            System.out.println(responseString);

            JSONObject jsonObject = new JSONObject(responseString);
            JSONArray dataArray = jsonObject.getJSONArray("data");

            for (int i = 0; i < dataArray.length(); i++) {
                JSONObject newJSONObject = dataArray.getJSONObject(i);

                int id = newJSONObject.getInt("channel_id");
                String name = newJSONObject.getString("channel_name");

                Channel channel = new Channel(name,id);

                Log.d("JSONUtil",channel.toString());
                channelList.add(channel);
            }
            //String token = (String) jsonObject.get("data");
            return channelList;

        } catch (Exception e) {
            Log.d("main", e.getMessage());
        }
        return null;
    }

    @Override
    protected void onPostExecute(ArrayList<Channel> list) {
        super.onPostExecute(list);
        context.onSubscriptionCall(list);

    }


    static interface IContextSubScription{
        public void onSubscriptionCall(ArrayList<Channel> list);
        public Context getContextSub();

    }
}
