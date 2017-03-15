package com.example.praneeth.itunestoppaidapps;
/*
        a. Assignment : Homework 6
        b. File Name. : AppListAdapter.java
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
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.ToggleButton;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class AppListAdapter extends ArrayAdapter<AppEntry> {
    Context context;
    int resource;
    String logText = "AppListAdapter";


    ArrayList<AppEntry> appList = new ArrayList<>();
    Set<String> favSet = new HashSet<String>();
    boolean favouritesAct = false;


    public AppListAdapter(Context context, int resource, ArrayList<AppEntry> objects) {
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
            if (appEntry != null) {
                Log.d(logText, "AppEntry inside adapter : " + appEntry.toString());
                holder.tv1.setText(appEntry.getAppName());
                holder.tv2.setText("Price : USD " + appEntry.getPrice());
                boolean checked = holder.star.isChecked();
                final int i = position;


                final ViewHolder finalHolder = holder;
                appEntry = appList.get(position);
                if (appEntry.getFavourite()) {
                    holder.star.setBackgroundResource(R.drawable.blackstar);
                    holder.star.setChecked(true);
                    Log.d(logText, "setting image to black for " + appEntry.getFavourite());
                } else {
                    holder.star.setBackgroundResource(R.drawable.whitestar);
                    holder.star.setChecked(false);
                    Log.d(logText, "setting image to black " + appEntry.getFavourite());
                }
                final AppEntry finalAppEntry = appEntry;
                holder.star.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        boolean isChecked = finalHolder.star.isChecked();
                        if (isChecked) {
                            DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    switch (which) {
                                        case DialogInterface.BUTTON_POSITIVE:
                                            //Yes button clicked
                                            finalAppEntry.setFavourite(true);
                                            finalHolder.star.setBackgroundResource(R.drawable.blackstar);
                                            finalHolder.star.setChecked(true);
                                            Log.d("onCheckedChanged", i + "checked ");
                                            Log.d("alert Add", i + "Yes clicked ");
                                            appList.set(i, finalAppEntry);
                                            SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
                                            SharedPreferences.Editor editor = preferences.edit();

                                            favSet.add(String.valueOf(i));
                                            Log.d(logText, "Favourites after add " + favSet.toString());
                                            editor.putStringSet("FAV_LIST", favSet);
                                            editor.putString(i + "", String.valueOf(i));
                                            editor.apply();
                                            editor.commit();
                                            notifyDataSetChanged();
                                            break;

                                        case DialogInterface.BUTTON_NEGATIVE:
                                            Log.d("alert Add", i + "No clicked ");
                                            finalHolder.star.setChecked(false);
                                            break;
                                    }
                                }
                            };
                            AlertDialog.Builder builder = new AlertDialog.Builder(context);
                            builder.setTitle("Add to Favourites");
                            builder.setMessage("Are you sure to add this App to your favourites?").setPositiveButton("Yes", dialogClickListener)
                                    .setNegativeButton("No", dialogClickListener).show();


                        } else {
                            //
                            DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    switch (which) {
                                        case DialogInterface.BUTTON_POSITIVE:
                                            Log.d("alert Remove", i + "Yes clicked ");
                                            Log.d("onCheckedChanged", i + "unChecked ");
                                            finalHolder.star.setBackgroundResource(R.drawable.whitestar);
                                            finalHolder.star.setChecked(false);

                                            finalAppEntry.setFavourite(false);
                                            appList.set(i, finalAppEntry);
                                            SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
                                            SharedPreferences.Editor editor = preferences.edit();
                                            editor.remove(String.valueOf(i));

                                            favSet.remove(String.valueOf(i));
                                            Log.d(logText, "Favourites after removal " + favSet.toString());
                                            editor.putStringSet("FAV_LIST", favSet);

                                            editor.commit();
                                            notifyDataSetChanged();
                                            break;

                                        case DialogInterface.BUTTON_NEGATIVE:
                                            Log.d("alert Remove", i + "No clicked ");
                                            finalHolder.star.setChecked(true);
                                            break;
                                    }
                                }
                            };

                            AlertDialog.Builder builder = new AlertDialog.Builder(context);
                            builder.setTitle("Remove from Favourites");
                            builder.setMessage("Are you sure to remove this App to your favourites?").setPositiveButton("Yes", dialogClickListener)
                                    .setNegativeButton("No", dialogClickListener).show();


                        }
                    }
                });
                /*holder.star.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                        if (isChecked) {
                            DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    switch (which) {
                                        case DialogInterface.BUTTON_POSITIVE:
                                            //Yes button clicked
                                            finalAppEntry.setFavourite(true);
                                            finalHolder.star.setBackgroundResource(R.drawable.blackstar);
                                            //finalHolder.star.setChecked(true);
                                            Log.d("onCheckedChanged", i + "checked ");
                                            Log.d("alert Add", i + "Yes clicked ");
                                            appList.set(i, finalAppEntry);
                                            SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
                                            SharedPreferences.Editor editor = preferences.edit();

                                            favSet.add(String.valueOf(i));
                                            Log.d(logText, "Favourites after add " + favSet.toString());
                                            editor.putStringSet("FAV_LIST", favSet);
                                            editor.putString(i + "", String.valueOf(i));
                                            editor.apply();
                                            editor.commit();
                                            notifyDataSetChanged();
                                            break;

                                        case DialogInterface.BUTTON_NEGATIVE:
                                            Log.d("alert Add", i + "No clicked ");
                                            //finalHolder.star.setChecked(false);
                                            break;
                                    }
                                }
                            };
                            AlertDialog.Builder builder = new AlertDialog.Builder(context);
                            builder.setTitle("Add to Favourites");
                            builder.setMessage("Are you sure to add this App to favourites?").setPositiveButton("Yes", dialogClickListener)
                                    .setNegativeButton("No", dialogClickListener).show();


                        } else {
                            //
                            DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    switch (which) {
                                        case DialogInterface.BUTTON_POSITIVE:
                                            Log.d("alert Remove", i + "Yes clicked ");
                                            Log.d("onCheckedChanged", i + "unChecked ");
                                            finalHolder.star.setBackgroundResource(R.drawable.whitestar);
                                            //finalHolder.star.setChecked(false);

                                            finalAppEntry.setFavourite(false);
                                            appList.set(i, finalAppEntry);
                                            SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
                                            SharedPreferences.Editor editor = preferences.edit();
                                            editor.remove(String.valueOf(i));

                                            favSet.remove(String.valueOf(i));
                                            Log.d(logText, "Favourites after removal " + favSet.toString());
                                            editor.putStringSet("FAV_LIST", favSet);

                                            editor.commit();
                                            notifyDataSetChanged();
                                            break;

                                        case DialogInterface.BUTTON_NEGATIVE:
                                            Log.d("alert Remove", i + "No clicked ");
                                            //finalHolder.star.setChecked(true);
                                            break;
                                    }
                                }
                            };

                            AlertDialog.Builder builder = new AlertDialog.Builder(context);
                            builder.setTitle("Remove from Favourites");
                            builder.setMessage("Are you sure to remove this App from favourites?").setPositiveButton("Yes", dialogClickListener)
                                    .setNegativeButton("No", dialogClickListener).show();


                        }
                    }
                });*/

                Picasso.with(context).load(appEntry.getImageUrl()).into(holder.imageIcon);
            }

        } catch (Exception e) {
            Log.d(logText, e.toString());
            Log.d(logText, e.getStackTrace() + "");
        }


        return convertView;
    }

    public ArrayList<AppEntry> updateAppEntries() {
        return appList;
    }

    public Set<String> updateFavourites() {
        return favSet;
    }

    static class ViewHolder {
        TextView tv1;
        TextView tv2;
        ImageView imageIcon;
        ToggleButton star;
    }


}