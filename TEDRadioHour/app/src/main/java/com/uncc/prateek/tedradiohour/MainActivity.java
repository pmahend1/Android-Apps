/*
a. Assignment #. Homework 7 
b. File Name. MainActivity
c. Full name of all students in your group. : Prateek Mahendrakar , Praneeth

*/
package com.uncc.prateek.tedradiohour;

import android.content.Context;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.support.v7.widget.CardView;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class MainActivity extends AppCompatActivity implements AsyncRadioHourGetter.Idata , View.OnClickListener {

    private static String URL = "https://www.npr.org/rss/podcast.php?id=510298";
    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private ArrayList<Radio> radioArrayList;
    private boolean bGridview=false;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_activity_main);

        Toolbar myToolbar = (Toolbar) findViewById(R.id.my_toolbar);
       setSupportActionBar(myToolbar);

        ActionBar actionBar = getSupportActionBar();

        LayoutInflater inflater = (LayoutInflater) this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.custom_action_bar, null);
        actionBar.setCustomView(view, new ActionBar.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        actionBar.setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);

        ImageButton switchView = (ImageButton) findViewById(R.id.button_switch_view);

        switchView.setOnClickListener(this);

        new AsyncRadioHourGetter(this).execute(URL);

    }


    @Override
    public void loadData(ArrayList<Radio> result) {
            radioArrayList=result;

            //sort based on date
            Collections.sort(radioArrayList, new Comparator<Radio>() {
                @Override
                public int compare(Radio o1, Radio o2) {
                    int diff=0;
                    try{
                        diff = o2.getPubDateDate().compareTo(o1.getPubDateDate());
                    }catch (Exception e){
                        Log.d("Exc","Exce at "+e.getMessage());
                    }
                    return diff;
                }
            });

            if(radioArrayList!=null){
                RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recyclerViewRadioList);

                RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(this);
                recyclerView.setLayoutManager(mLayoutManager);

                mAdapter = new ViewAsListAdapter(this,radioArrayList);
                recyclerView.setAdapter(mAdapter);
            }
    }

    @Override
    public Context getContext() {
        return this;
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        bGridview=!bGridview;
        Log.d("main","Inside OnClick"+v.getId());

        if(id == R.id.button_switch_view){
            Log.d("main","Change to grid or list");
            if(radioArrayList!=null && bGridview){

                 GridLayoutManager lLayout;
                lLayout = new GridLayoutManager(MainActivity.this, 2);

                RecyclerView rView = (RecyclerView)findViewById(R.id.recyclerViewRadioList);
                rView.setHasFixedSize(true);
                rView.setLayoutManager(lLayout);

                ViewAsGridAdapter rcAdapter = new ViewAsGridAdapter(MainActivity.this, radioArrayList);
                rView.setAdapter(rcAdapter);

            }else if(radioArrayList!=null && !bGridview){
                RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recyclerViewRadioList);

                RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(this);
                recyclerView.setLayoutManager(mLayoutManager);

                mAdapter = new ViewAsListAdapter(this,radioArrayList);
                recyclerView.setAdapter(mAdapter);
            }
        }
    }
}
