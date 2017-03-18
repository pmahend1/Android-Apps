/*
a. Assignment #. Homework 7 
b. File Name. 
c. Full name of all students in your group. : Prateek Mahendrakar , Praneeth

*/
package com.uncc.prateek.tedradiohour;

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

/**
 * Created by Prateek on 11-03-2017.
 */

public class AsyncRadioHourGetter extends AsyncTask<String, Void, ArrayList<Radio>> {
    Idata data;
    Context context;
    ProgressDialog progressDialog;
    public AsyncRadioHourGetter(Idata data) {
        this.data = data;
    }

    @Override
    protected ArrayList<Radio> doInBackground(String... params) {
        String urlString = params[0];
        try {
            URL url = new URL(urlString);
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("GET");

            con.connect();
            int statusCode = con.getResponseCode();
            if (statusCode == HttpURLConnection.HTTP_OK) {
                ArrayList<Radio> radioList = RadioHourUtility.RadioSAXParser.parseRadio(con.getInputStream());
                return radioList;
            }
        } catch (MalformedURLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return null;
    }

    @Override
    protected void onPostExecute(ArrayList<Radio> result) {
        if(progressDialog.isShowing()){
            progressDialog.dismiss();
        }
        if(result!=null){
            Log.d("demo", result.toString());
            data.loadData(result);

        }else{
            Toast.makeText(data.getContext(), "No data", Toast.LENGTH_SHORT).show();
        }


    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        progressDialog = new ProgressDialog(data.getContext());
        progressDialog.setTitle("Loading Episodes");
        progressDialog.setIndeterminate(true);
        progressDialog.setCancelable(false);
    }

    static public interface Idata{
        public void loadData(ArrayList<Radio> result);
        public Context getContext();
    }
}
