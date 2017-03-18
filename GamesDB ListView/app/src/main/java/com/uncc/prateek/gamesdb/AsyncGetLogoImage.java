package com.uncc.prateek.gamesdb;
/*
	Assignment : InClass 06
	FullName of Student : Prateek Mahendrakar
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

/**
 * Created by Prateek on 20-02-2017.
 */


class AsyncGetLogoImage extends AsyncTask<String, Void, Bitmap> {
    String logText = "AsyncGetLogoImage";
    ImageView iv;
    public AsyncGetLogoImage(ImageView iv ) {
        this.iv = iv;
    }

    @Override
    protected void onPostExecute(Bitmap s) {
        if (s != null) {
            Log.d(logText, "image is :"+s.toString());
            ImageView iw = iv;
            iw.setImageBitmap(s);
            //progressDialog.dismiss();
        } else {
            ImageView iw = iv;
            iw.setImageBitmap(s);
            Log.d(logText, "NULL");
        }
    }


    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }


    @Override
    protected Bitmap doInBackground(String... params) {

        try {
            URL url = new URL(params[0]);
            Log.d("AsyncGetLogoImage", "" + url);
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("GET");
            Bitmap image = BitmapFactory.decodeStream(con.getInputStream());

            return image;
        } catch (Exception e) {
            Log.d("GetImage", "Exception at diInbackground of GetImage :" + e.getMessage());
        }
        return null;
    }

}


