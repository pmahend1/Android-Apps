package com.example.praneeth.itunestoppaidapps;
/*
        a. Assignment : Homework 6
        b. File Name. : MainActivity.java
        c. Group Name : Group 06
        d. Students in  group : Prateek Mahendrakar , Siva Ram Praneeth Vemulapalli
 */
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

public class MainActivity extends AppCompatActivity implements AsyncAppsFeed.LinkData , AppDetailAdapter.IContext {
    ProgressDialog progressDialog;
    ArrayList<AppEntry> appEntries;
    RecyclerView recyclerView;
    ListView listView_all;
    AppDetailAdapter adp;
    private final String  LOG_TEXT = "MainActivity";
    private final String URL = "https://itunes.apple.com/us/rss/toppaidapplications/limit=25/json";
    private ArrayList<AppEntry> favouriesList = new ArrayList<>();
    Set<String> favSet = new HashSet<>();

    @Override
    protected void onResume() {
        super.onResume();
        Log.d(LOG_TEXT,"On resume");
        new AsyncAppsFeed(MainActivity.this).execute(URL);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
     getMenuInflater().inflate(R.menu.menu, menu);
     return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        switch (item.getItemId())
        {
            case R.id.Refresh: {
                Log.d(LOG_TEXT, "Refresh clicked");
                new AsyncAppsFeed(this).execute(URL);
                return true;
            }
            case R.id.Favourites:{
                Log.d(LOG_TEXT,"Favourites clicked");
                SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(MainActivity.this);
                HashSet<String> favSetDefault =new HashSet<String>() ;
                favSet = preferences.getStringSet("FAV_LIST",favSetDefault);
                if(favSet.size()==0){
                    Toast.makeText(this, "No favourites exist! ", Toast.LENGTH_LONG).show();
                    return true;
                }else{
                    Intent favIntent = new Intent(MainActivity.this,FavouritesActivity.class);
                    favIntent.putExtra("FAVOURITES",new ArrayList(favSet));
                    favIntent.putExtra("ALL",appEntries);
                    startActivity(favIntent);
                    return true;
                }

            }

            case R.id.sort_incease: {
                Log.d(LOG_TEXT, "sort_incease clicked");
                ArrayList<AppEntry> ascending = appEntries;
                Collections.sort(ascending, new Comparator<AppEntry>() {
                    @Override
                    public int compare(AppEntry o1, AppEntry o2) {
                        int x = (int) (o1.getPrice() - o2.getPrice());
                        if(x==0){
                            return o1.getAppName().compareTo(o2.getAppName());
                        }else{
                        return x;
                        }
                    }
                });

                AppListAdapter adapter = new AppListAdapter(this,R.layout.itemlayout,ascending);
                adapter.setNotifyOnChange(true);
                ListView lv = (ListView) findViewById(R.id.listView_all);
                lv.setAdapter(adapter);

                return true;
            }
            case R.id.sort_decrease:
                Log.d(LOG_TEXT,"sort_decrease clicked");
                ArrayList<AppEntry> descending = appEntries;
                Collections.sort(descending, new Comparator<AppEntry>() {
                    @Override
                    public int compare(AppEntry o1, AppEntry o2) {
                        int x = (int) (o2.getPrice() - o1.getPrice());
                        if(x==0){
                            return o1.getAppName().compareTo(o2.getAppName());
                        }else{
                            return x;
                        }

                    }
                });

                AppListAdapter adapter = new AppListAdapter(this,R.layout.itemlayout,descending);
                adapter.setNotifyOnChange(true);
                ListView lv = (ListView) findViewById(R.id.listView_all);
                lv.setAdapter(adapter);
                return true;
            default:
                Log.d(LOG_TEXT,"default clicked");
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar myToolbar = (Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(myToolbar);
        //recyclerView= (RecyclerView) findViewById(R.id.recycleview);
        listView_all= (ListView) findViewById(R.id.listView_all);
        progressDialog=new ProgressDialog(this);
        progressDialog.setCancelable(false);
        progressDialog.setTitle("Loading...");
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.show();
        new AsyncAppsFeed(this).execute(URL);

    }

    @Override
    public void printListItems(ArrayList<AppEntry> appFeeds) {
        appEntries= appFeeds;
        if(appEntries!=null){
            Log.d(LOG_TEXT,"Size retrived"+appEntries.size());

            progressDialog.dismiss();
            SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(MainActivity.this);
            HashSet<String> favSetDefault =new HashSet<String>() ;
            favSet = preferences.getStringSet("FAV_LIST",favSetDefault);
            if(favSet!=null){
                Log.d(LOG_TEXT,"--------------favourites in Main ---------");

                for (Iterator<String> it = favSet.iterator(); it.hasNext(); ) {
                    String f = it.next();
                    int k = Integer.parseInt(f);
                    AppEntry ae = appEntries.get(k);
                    ae.setFavourite(true);
                    appEntries.set(k,ae);
                    Log.d(LOG_TEXT,k + "Favourite "+ ae.getAppName() + " flag " + ae.getFavourite());
                }
                Log.d(LOG_TEXT,"--------------favourites in Main ---------");
            }
            Log.d(LOG_TEXT,"------All entries------------");

            for(AppEntry aex : appEntries){
                Log.d(LOG_TEXT,aex.toString());
            }
            Log.d(LOG_TEXT,"------All entries------------");

            AppListAdapter adapter = new AppListAdapter(this,R.layout.itemlayout,appEntries);
            adapter.setNotifyOnChange(true);
            ListView lv = (ListView) findViewById(R.id.listView_all);
            lv.setAdapter(adapter);

        }else{
            Toast.makeText(this, "No values returned from API", Toast.LENGTH_SHORT).show();
        }

    }

    @Override
    public Context getContext() {
        return this;
    }
}