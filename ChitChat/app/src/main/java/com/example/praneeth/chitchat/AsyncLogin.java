package com.example.praneeth.chitchat;

import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONObject;

import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * Created by Praneeth on 3/27/2017.
 */

public class AsyncLogin extends AsyncTask<String , Void, String> {
    IContextLogin context;

    String TOKEN="";

    public AsyncLogin(IContextLogin context) {
        this.context=context;
    }
    @Override
    protected String doInBackground(String... strings) {
        final OkHttpClient client = new OkHttpClient();
        RequestBody formBody = new FormBody.Builder()
                .add("email", strings[0])
                .add("password", strings[1])
                .build();

        Request request = new Request.Builder()
                .url("http://52.90.79.130:8080/Groups/api/login")
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
            String responseString = response.body().string();
            System.out.println("Server: " + response.header("Server"));
            System.out.println("Date: " + response.header("Date"));
            System.out.println("Vary: " + response.headers("Vary"));
            System.out.println(responseString);

            //String responseString = response.body().string();
            System.out.println(responseString);

            JSONObject jsonObject = new JSONObject(responseString);
            String token = (String) jsonObject.get("data");

            System.out.println("token"+token);

            TOKEN=token;
            return token;

      /*      Intent intent = new Intent(this,SubscribedChannels.class);
            startActivity(intent);*/
        } catch (Exception e) {
            Log.d("main", e.toString());
        }
        return "";
    }

    @Override
    protected void onPostExecute(String s) {
         //super.onPostExecute(s);
         context.onLogin(TOKEN);
    }

    static interface IContextLogin{
        public void onLogin(String token);

    }
}
