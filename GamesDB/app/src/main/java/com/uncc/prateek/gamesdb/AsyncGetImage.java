package com.uncc.prateek.gamesdb;
/*
    a. Assignment #. Homework 05
	b. File Name. AsyncGetImage.java
	c. Full name of all students in your group. Prateek Mahendrakar , Siva Ram Praneeth Vemulapalli
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
import android.util.Log;
import android.widget.ImageView;

import java.net.HttpURLConnection;
import java.net.URL;

class AsyncGetImage extends AsyncTask<String, Void, Bitmap> {
    Activity activity;
    IActivity iAct;
    ProgressDialog progressDialog;

    public AsyncGetImage(Activity act) {
        this.activity = act;
        this.iAct = (IActivity) act;
    }

    @Override
    protected void onPostExecute(Bitmap s) {
        if (s != null) {
            Log.d("onPostExecute", s.toString());
            ImageView iw = (ImageView) activity.findViewById(R.id.imageView_gameImage);
            iw.setImageBitmap(s);
            progressDialog.dismiss();
        } else {
            ImageView iw = (ImageView) activity.findViewById(R.id.imageView_gameImage);
            iw.setImageBitmap(s);
            progressDialog.dismiss();
            Log.d("onPostExecute", "NULL");
        }
    }


    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        progressDialog = ProgressDialog.show(iAct.getContextForImage(), "Loading image", "", false);
    }


    @Override
    protected Bitmap doInBackground(String... params) {

        try {
            URL url = new URL(params[0]);
            Log.d("AsyncGetImage", "" + url);
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("GET");
            Bitmap image = BitmapFactory.decodeStream(con.getInputStream());

            return image;
        } catch (Exception e) {
            Log.d("GetImage", "Exception at diInbackground of GetImage :" + e.getMessage());
        }
        return null;
    }

    static public interface IActivity {
        public Context getContextForImage();
    }
}


