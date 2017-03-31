package com.uncc.prateek.bbcnewsfeeds;

import android.content.Context;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements AsyncBBCNewsGetter.Idata{

    private static String URL = "http://feeds.bbci.co.uk/news/video_and_audio/technology/rss.xml";
    private static String _LOG_TEXT = "Main";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar myToolbar = (Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(myToolbar);

        ActionBar actionBar = getSupportActionBar();

        LayoutInflater inflater = (LayoutInflater) this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.custom_action_bar, null);
        actionBar.setCustomView(view, new ActionBar.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        actionBar.setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);

        new AsyncBBCNewsGetter(this).execute(URL);
    }

    @Override
    public void loadData(ArrayList<BBCNews> result) {
        Log.d(_LOG_TEXT,"Data retrieved : "+result.toString());
        RecyclerView rv = (RecyclerView) findViewById(R.id.recyclerView);

        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(this);
        rv.setLayoutManager(mLayoutManager);

        RecyclerView.Adapter mAdapter = new RecyclerViewAdapter(this,result);
        mAdapter.notifyDataSetChanged();
        rv.setAdapter(mAdapter);


    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        //super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        //return super.onOptionsItemSelected(item);
        switch (item.getItemId())
        {
            case R.id.show_news:
                break;
            case R.id.show_blocked:
                break;
            case R.id.sort_atoz:
                break;
            case R.id.sort_ztoa:
            break;
            default:
                break;
        }
        return true;
    }

    @Override
    public Context getContext() {
        return this;
    }
}
