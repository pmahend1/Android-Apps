package com.example.praneeth.itunestoppaidapps;
/*
        a. Assignment : Homework 6
        b. File Name. : AppDetailAdapter.java
        c. Group Name : Group 06
        d. Students in  group : Prateek Mahendrakar , Siva Ram Praneeth Vemulapalli
 */

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class AppDetailAdapter extends RecyclerView.Adapter {
    private final static String LOG_TEXT = "AppDetailAdapter";
    Activity activity;
    ArrayList<AppEntry> list = new ArrayList<>();
    MainActivity mainActivityactivity;
    Set<String> favSet = new HashSet<String>();
    IContext icontext;
    boolean favouritesAct = false;

    public AppDetailAdapter(ArrayList<AppEntry> list, Activity activity, IContext icontext) {
        this.list = list;
        this.activity = activity;
        this.icontext = icontext;
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(icontext.getContext());
        favSet = preferences.getStringSet("FAV_LIST", null);
        if (favSet != null) {
            Log.d(LOG_TEXT, "fav set : " + favSet.toString());

        } else {
            favSet = new HashSet<String>();
        }

    }

    public boolean isFavouritesAct() {
        return favouritesAct;
    }

    public void setFavouritesAct(boolean favouritesAct) {
        this.favouritesAct = favouritesAct;
    }

    public ArrayList<AppEntry> updateFavourites() {
        return list;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.itemlayout, parent, false);
        AppDetailHolder viewHolder = new AppDetailHolder(v);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        Log.d("onBindViewHolder", "At position" + position);
        final AppEntry entryAda = list.get(position);
        final AppDetailHolder holders = (AppDetailHolder) holder;
        holders.tv1.setText(entryAda.getAppName());
        holders.tv2.setText("Price : USD " + entryAda.getPrice());
        boolean checked = holders.star.isChecked();

        if (favouritesAct == false) {
            if (favSet != null) {
                for (String str : favSet) {
                    Log.d(LOG_TEXT, "str " + str + " and position " + String.valueOf(position));
                    if (entryAda.getFavourite()) {
                        Log.d(LOG_TEXT, entryAda.getAppName() + "is a favourite");
                        list.set(position, entryAda);
                        holders.star.setBackgroundResource(R.drawable.blackstar);
                        holders.star.setChecked(true);
                    }
                }
            }

        }
      /* else{
            holders.star.setBackgroundResource(R.drawable.blackstar);
            holders.star.setChecked(true);
        }
*/
        holders.star.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    //
                    DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            switch (which) {
                                case DialogInterface.BUTTON_POSITIVE:
                                    //Yes button clicked
                                    holders.star.setBackgroundResource(R.drawable.blackstar);

                                    Log.d(LOG_TEXT, "onCheckedChanged " + position + "checked ");
                                    Log.d(LOG_TEXT, "alert Add" + position + "Yes clicked ");
                                    SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(icontext.getContext());
                                    SharedPreferences.Editor editor = preferences.edit();
                                    entryAda.setFavourite(true);
                                    favSet.add(entryAda.getAppName());
                                    Log.d(LOG_TEXT, favSet.toString());
                                    editor.putStringSet("FAV_LIST", favSet);
                                    editor.putString(position + "", String.valueOf(position));
                                    editor.apply();
                                    editor.commit();
                                    break;

                                case DialogInterface.BUTTON_NEGATIVE:
                                    Log.d(LOG_TEXT, "alert Add" + position + "No clicked ");
                                    break;
                            }
                        }
                    };
                    AlertDialog.Builder builder = new AlertDialog.Builder(icontext.getContext());
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
                                    Log.d("alert Remove", position + "Yes clicked ");
                                    Log.d("onCheckedChanged", position + "unChecked ");
                                    holders.star.setBackgroundResource(R.drawable.whitestar);
                                    holders.star.setChecked(false);
                                    SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(icontext.getContext());
                                    SharedPreferences.Editor editor = preferences.edit();
                                    entryAda.setFavourite(false);
                                    editor.remove(entryAda.getAppName());

                                    favSet.remove(entryAda.getAppName());
                                    Log.d(LOG_TEXT, favSet.toString());
                                    editor.putStringSet("FAV_LIST", favSet);

                                    editor.commit();
                                    break;

                                case DialogInterface.BUTTON_NEGATIVE:
                                    Log.d("alert Remove", position + "No clicked ");
                                    break;
                            }
                        }
                    };

                    AlertDialog.Builder builder = new AlertDialog.Builder(icontext.getContext());
                    builder.setTitle("Remove from Favourites");
                    builder.setMessage("Are you sure to remove this App to your favourites?").setPositiveButton("Yes", dialogClickListener)
                            .setNegativeButton("No", dialogClickListener).show();


                }
            }
        });
        Picasso.with(activity).load(entryAda.getImageUrl()).into(holders.imageIcon);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public interface IContext {
        public Context getContext();
    }

    public interface OnEntryItemClickListener {
        void onItemClick(AppEntry item, int pos);
    }


}
