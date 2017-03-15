package com.example.praneeth.itunestoppaidapps;
/*
        a. Assignment : Homework 6
        b. File Name. : FavouritesActivity.java
        c. Group Name : Group 06
        d. Students in  group : Prateek Mahendrakar , Siva Ram Praneeth Vemulapalli
 */

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class FavouritesActivity extends AppCompatActivity {
    private final String LOGTEXT = "favouriteActivity";
    RecyclerView recyclerView;
    ArrayList<AppEntry> favEntries = new ArrayList<>();
    ArrayList<AppEntry> appEntries;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_activity_favourites);
        Toolbar myToolbar = (Toolbar) findViewById(R.id.my_toolbar);
        myToolbar.setTitle("iTunes Apps : Favourites");
        setSupportActionBar(myToolbar);


        ArrayList<String> favourites = (ArrayList<String>) getIntent().getSerializableExtra("FAVOURITES");
        appEntries = (ArrayList<AppEntry>) getIntent().getSerializableExtra("ALL");


        if (appEntries != null) {
            if (favourites != null) {
                Log.d(LOGTEXT, "--------------favourites---------");

                for (int i = 0; i < favourites.size(); i++) {
                    int k = Integer.parseInt(favourites.get(i));
                    AppEntry ae = appEntries.get(k);
                    ae.setFavourite(true);
                    favEntries.add(ae);
                    Log.d(LOGTEXT, i + " " + ae.getAppName());
                }
                Log.d(LOGTEXT, "--------------favourites---------");
            }

            if (appEntries != null) {
                Log.d(LOGTEXT, "--------------ALL---------");

                for (int j = 0; j < appEntries.size(); j++) {
                    Log.d(LOGTEXT, j + " ==> " + appEntries.get(j).getAppName() + "Flag ==> " + appEntries.get(j).getFavourite());
                }
                Log.d(LOGTEXT, "--------------ALL---------");
            }


            FavouritesAdapter adapter = new FavouritesAdapter(this, R.layout.itemlayout, favEntries);
            adapter.setNotifyOnChange(true);
            ListView lv = (ListView) findViewById(R.id.listView_favourites);
            lv.setAdapter(adapter);
        } else {
            Toast.makeText(this, "Something wrong", Toast.LENGTH_SHORT).show();
        }

    }


/*    @Override
    public Context getContext() {
        return this;
    }*/


}
