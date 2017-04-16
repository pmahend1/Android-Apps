package com.example.praneeth.chitchat;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.util.Log;

import com.google.gson.Gson;

import org.json.JSONObject;

import java.util.Map;

import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * Created by Praneeth on 3/27/2017.
 */

public class AsyncSignUp extends AsyncTask<String , Void, String> {

    IContext context;

    Context con;
    public AsyncSignUp(IContext context) {
        this.context=context;
        //con = context;
    }

    @Override
    protected String doInBackground(String... strings) {
        final OkHttpClient client = new OkHttpClient();
        RequestBody formBody = new FormBody.Builder()
                .add("email", strings[0])
                .add("password", strings[1])
                .add("fname",strings[2])
                .add("lname",strings[3])
                .build();

        Request request = new Request.Builder()
                .url("http://52.90.79.130:8080/Groups/api/signUp")
                .post(formBody)
                .build();


        Response response = null;
        try {
            response = client.newCall(request).execute();
            Log.d("main","response.toString():"+response.toString());
            Log.d("main","response.message():"+response.message());
            //Log.d("main",response);
            if (!response.isSuccessful()){
                throw new Exception("Unexpected code " + response);
            }
            System.out.println("Server: " + response.header("Server"));
            System.out.println("Date: " + response.header("Date"));
            System.out.println("Vary: " + response.headers("Vary"));


            String responseString = response.body().string();
            System.out.println(responseString);

                JSONObject jsonObject = new JSONObject(responseString);
                String token = (String) jsonObject.get("data");

            System.out.println("token"+token);


            return responseString;

        } catch (Exception e) {
            Log.d("main", e.toString());
        }
        return "";
    }

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);
        context.onSignUp(s);
    }

    static interface IContext{
        public void onSignUp(String token);

    }
}
