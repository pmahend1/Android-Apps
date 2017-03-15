package com.example.praneeth.itunestoppaidapps;
/*
        a. Assignment : Homework 6
        b. File Name. : FavouritesAdapter.java
        c. Group Name : Group 06
        d. Students in  group : Prateek Mahendrakar , Siva Ram Praneeth Vemulapalli
 */

import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.ToggleButton;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;


public class FavouritesAdapter extends ArrayAdapter<AppEntry> {
    Context context;
    int resource;
    String logText = "FavouritesAdapter";


    ArrayList<AppEntry> appList = new ArrayList<>();
    HashMap<String, String> favMap = new HashMap<String, String>();
    Set<String> favSet = new HashSet<String>();
    boolean favouritesAct = false;


    public FavouritesAdapter(Context context, int resource, ArrayList<AppEntry> objects) {
        super(context, resource, objects);
        this.appList = objects;
        this.context = context;
        this.resource = resource;
        setFavourites();

    }


    public void setFavourites() {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        favSet = preferences.getStringSet("FAV_LIST", new HashSet<String>());
        Log.d(logText, "fav set : " + favSet.toString());
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        AppEntry appEntry = null;
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(resource, parent, false);
            holder = new ViewHolder();
            holder.tv1 = (TextView) convertView.findViewById(R.id.appTitle);
            holder.tv2 = (TextView) convertView.findViewById(R.id.appPrice);
            holder.imageIcon = (ImageView) convertView.findViewById(R.id.imageView);
            holder.star = (ToggleButton) convertView.findViewById(R.id.star);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        try {
            appEntry = appList.get(position);
            if (appEntry.getFavourite()) {
                Log.d(logText, "AppEntry inside adapter : " + appEntry.toString());
                holder.tv1.setText(appEntry.getAppName());
                holder.tv2.setText("Price : USD " + appEntry.getPrice());
                boolean checked = holder.star.isChecked();
                final int i = position;


                final ViewHolder finalHolder = holder;
                final AppEntry finalAppEntry = appEntry;
                holder.star.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                        if (isChecked) {
                            DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    switch (which) {
                                        case DialogInterface.BUTTON_POSITIVE:
                                            //Yes button clicked
                                            finalHolder.star.setBackgroundResource(R.drawable.blackstar);
                                            Log.d("onCheckedChanged", i + "checked ");
                                            Log.d("alert Add", i + "Yes clicked ");
                                            SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
                                            SharedPreferences.Editor editor = preferences.edit();

                                            favSet.remove(String.valueOf(finalAppEntry.getId()));

                                            editor.putStringSet("FAV_LIST", favSet);
                                            Log.d(logText, "FAV_LIST after remove" + favSet.toString());
                                            editor.remove(String.valueOf(finalAppEntry.getId()));
                                            editor.apply();
                                            editor.commit();
                                            remove(finalAppEntry);
                                            notifyDataSetChanged();
                                            break;

                                        case DialogInterface.BUTTON_NEGATIVE:
                                            Log.d("alert Add", i + "No clicked ");
                                            break;
                                    }
                                }
                            };
                            AlertDialog.Builder builder = new AlertDialog.Builder(context);
                            builder.setTitle("Remove from Favourites");
                            builder.setMessage("Are you sure to remove this App from your favourites?").setPositiveButton("Yes", dialogClickListener)
                                    .setNegativeButton("No", dialogClickListener).show();

                        }

                    }
                });
                if (appEntry.getFavourite()) {
                    finalHolder.star.setBackgroundResource(R.drawable.blackstar);
                    notifyDataSetChanged();
                } else {
                    finalHolder.star.setBackgroundResource(R.drawable.whitestar);
                    notifyDataSetChanged();
                }

                Picasso.with(context).load(appEntry.getImageUrl()).into(holder.imageIcon);
            } else {
                Log.d(logText, "do nothing");
            }

        } catch (Exception e) {
            Log.d(logText, e.toString());
            Log.d(logText, e.getStackTrace() + "");
        }


        return convertView;
    }

    static class ViewHolder {
        TextView tv1;
        TextView tv2;
        ImageView imageIcon;
        ToggleButton star;
    }

}