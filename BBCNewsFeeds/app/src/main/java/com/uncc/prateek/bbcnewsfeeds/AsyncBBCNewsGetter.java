package com.uncc.prateek.bbcnewsfeeds;

/**
 * Created by Prateek on 19-03-2017.
 */


import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;


public class AsyncBBCNewsGetter extends AsyncTask<String, Void, ArrayList<BBCNews>> {
    Idata data;
    Context context;
    ProgressDialog progressDialog;

    public AsyncBBCNewsGetter(Idata data) {
        this.data = data;
    }

    @Override
    protected ArrayList<BBCNews> doInBackground(String... params) {
        String urlString = params[0];
        try {
            URL url = new URL(urlString);
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("GET");
            Log.d("statusCode","here");
            con.connect();
            int statusCode = con.getResponseCode();

            Log.d("statusCode",statusCode+"");
            if (statusCode == HttpURLConnection.HTTP_OK) {
                Log.d("Async","HTTP_OK");
                ArrayList<BBCNews> radioList = BBCNewsXMLUtility.ObjectSAXParser.parseObject(con.getInputStream());
                return radioList;
            }
        } catch (MalformedURLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return null;
    }

    @Override
    protected void onPostExecute(ArrayList<BBCNews> result) {
        if (progressDialog.isShowing()) {
            progressDialog.dismiss();
        }
        if (result != null) {
            Log.d("demo", result.toString());
            data.loadData(result);

        } else {
            Toast.makeText(data.getContext(), "No data", Toast.LENGTH_SHORT).show();
        }


    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        progressDialog = new ProgressDialog(data.getContext());
        progressDialog.setTitle("Loading News");
        progressDialog.setCancelable(false);
    }

    static public interface Idata {
        public void loadData(ArrayList<BBCNews> result);

        public Context getContext();
    }
}

