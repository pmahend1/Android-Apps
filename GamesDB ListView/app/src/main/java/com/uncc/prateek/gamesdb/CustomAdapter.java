package com.uncc.prateek.gamesdb;
/*
	Assignment : InClass 06
	FullName of Student : Prateek Mahendrakar
*/
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.nio.ByteBuffer;
import java.util.List;

/**
 * Created by Prateek on 20-02-2017.
 */

public class CustomAdapter extends ArrayAdapter<Game> {
    List<Game> data;
    Context context;
    int resource;
    String logText="CustomAdapter";


    public CustomAdapter(Context context, int resource, List<Game> objects) {
        super(context, resource, objects);
        this.data = objects;
        this.context = context;
        this.resource = resource;
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if(convertView==null){
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(resource,parent,false);
        }
        Game game = data.get(position);
        Log.d(logText,"Game inside adapter : "+game.toShortString());
        ImageView ivLogo = (ImageView) convertView.findViewById(R.id.imageView_logo);
        TextView tv = (TextView) convertView.findViewById(R.id.tv);
        tv.setText(game.toString());
        String imageURL=game.getClearLogo();
        new AsyncGetLogoImage(ivLogo).execute(imageURL);
        /*Bitmap image=null;

            try {
                String imageURL=game.getClearLogo();
                if(game.getClearLogo()!=null){
                    imageURL = game.getClearLogo().trim();
                }
                URL url = new URL(imageURL);
                Log.d(logText,"logo URL :"+imageURL);
                Log.d("LogoURL", "" + url);
                HttpURLConnection con = (HttpURLConnection) url.openConnection();
                Log.d(logText, "here1");
                con.setRequestMethod("GET");
                Log.d(logText, "here2");
                image = BitmapFactory.decodeStream(con.getInputStream());
                Log.d(logText, "here3");

            } catch (Exception e) {
                Log.d("GetImage", "Exception at adapter getting imageStream:" + e.toString());
            }

        if (image != null) {
            Log.d("onLogoImage IF", image.toString());
            ivLogo.setImageBitmap(image);
        } else {
            Log.d("onLogoImage ELSE", "NULL");
            ivLogo.setImageBitmap(image);
        }*/







        //  return super.getView(position, convertView, parent);
        return convertView;
    }
}
