package com.example.praneeth.chitchat;
/*a. Assignment #. In Class 09
        b. File Name. ___
        c. Full name of all students in your group. : Prateek , Praneeth*/
import android.os.AsyncTask;
import android.util.Log;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by Praneeth on 3/27/2017.
 */

public class ConnectionAsyncTask extends AsyncTask<String , Void, String> {
    @Override
    protected String doInBackground(String... params) {
        String url = params[0];
        OkHttpClient client = new OkHttpClient();


        Request request = new Request.Builder()
                .url(url)
                .build();

        Response response = null;
        try {
            response = client.newCall(request).execute();
            Log.d("async",response.body().string());
            return response.body().string();
        } catch (Exception e) {
            Log.d("asyn", "ExC" + e.getMessage());
        }
        return null;

    }

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);
    }
}
