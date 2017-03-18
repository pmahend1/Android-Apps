package com.uncc.prateek.cnnnewsapp;
/*
    a) Assignment #. : InClass05
    b) Full name of the student. Prateek Mahendrakar
*/
/**
 * Created by Prateek on 13-02-2017.
 */

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ImageView;
import android.widget.Toast;

import org.json.JSONException;

import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

class AsyncGetImage extends AsyncTask<String,Void,Bitmap> {
    Activity activity;
    //IActivity iAct;
    public AsyncGetImage(Activity act) {
        this.activity=act;
        //this.iAct = (IActivity) act;
    }
    ProgressDialog progressDialog;

    @Override
    protected void onPostExecute(Bitmap s) {
        if(s != null ){
            Log.d("onPostExecute",s.toString());
            ImageView iw = (ImageView) activity.findViewById(R.id.imageView_newsImage);
            iw.setImageBitmap(s);
            //progressDialog.dismiss();
        }else{
            ImageView iw = (ImageView) activity.findViewById(R.id.imageView_newsImage);
            iw.setImageBitmap(s);
           // progressDialog.dismiss();
            Log.d("onPostExecute","NULL");
        }
    }


    @Override
    protected void onPreExecute() {
        super.onPreExecute();

//        progressDialog= ProgressDialog.show(activity.getApplicationContext(), "Loading image","", false);

    }


    @Override
    protected void onProgressUpdate(Void... values) {
        super.onProgressUpdate(values);
    }

    @Override
    protected Bitmap doInBackground(String... params) {

        try {
            URL url = new URL(params[0]);
            Log.d("AsyncGetImage",""+url);
            HttpURLConnection con =  (HttpURLConnection) url.openConnection();
            con.setRequestMethod("GET");
            Bitmap image = BitmapFactory.decodeStream(con.getInputStream());

            return image;
        }
        catch(Exception e){
            Log.d("GetImage","Exception at diInbackground of GetImage :" + e.getMessage());
        }
        return null;
    }

    static public interface IActivity {
        public Context getContext();
    }
}


