package com.uncc.prateek.cnnnewsapp;
/*
    a) Assignment #. : InClass05
    b) Full name of the student. Prateek Mahendrakar
*/
import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements View.OnClickListener, AsyncGetNews.INewsData {
    final static String url = "http://rss.cnn.com/rss/cnn_tech.rss";
    ArrayList<News> newsList = new ArrayList<News>();
    private int i = 0;

    String logText = "Main";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViewById(R.id.button_getNews).setOnClickListener(this);
        findViewById(R.id.button_first).setOnClickListener(this);
        findViewById(R.id.button_previous).setOnClickListener(this);
        findViewById(R.id.button_next).setOnClickListener(this);
        findViewById(R.id.button_last).setOnClickListener(this);
        findViewById(R.id.button_finish).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
/*        if (v.getId() == R.id.button_getNews) {
            new AsyncGetNews(MainActivity.this).execute(url);
        }*/
        switch (v.getId()) {
            case R.id.button_getNews: {
                new AsyncGetNews(MainActivity.this).execute(url);
                break;
            }
            case R.id.button_first: {
                i = 0;
                ListView listView = (ListView) findViewById(R.id.listView_data);
                News news = null;
                if (newsList != null) {
                    News news1 = newsList.get(0);
                    ArrayList<String> arrayList = news1.toArrayList();
                    Log.d(logText, "News article is" + arrayList.toString());
                    ArrayAdapter<String> adapter = new ArrayAdapter<String>(MainActivity.this, android.R.layout.simple_list_item_1, android.R.id.text1, arrayList);
                    if(news1.getImage()!=null){
                        new AsyncGetImage(MainActivity.this).execute(news1.getImage());
                    }
                    listView.setAdapter(adapter);
                }
                break;
            }
            case R.id.button_previous: {
                if (i > 0) {
                    i = i - 1;
                    ListView listView = (ListView) findViewById(R.id.listView_data);
                    News news = null;
                    if (newsList != null) {
                        News news1 = newsList.get(i);
                        ArrayList<String> arrayList = news1.toArrayList();
                        Log.d(logText, "News article is" + arrayList.toString());
                        ArrayAdapter<String> adapter = new ArrayAdapter<String>(MainActivity.this, android.R.layout.simple_list_item_1, android.R.id.text1, arrayList);
                        if(news1.getImage()!=null){
                            new AsyncGetImage(MainActivity.this).execute(news1.getImage());
                        }
                        listView.setAdapter(adapter);
                    }
                } else {
                    Toast.makeText(this, "Already at first", Toast.LENGTH_SHORT).show();
                }


                break;
            }

            case R.id.button_next: {
                if(i<newsList.size()-1){
                    i++;
                    ListView listView = (ListView) findViewById(R.id.listView_data);
                    News news=null;
                    if(newsList!=null){
                        News news1 = newsList.get(i);
                        ArrayList<String> arrayList = news1.toArrayList();
                        Log.d(logText,"News article is"+arrayList.toString());
                        ArrayAdapter<String> adapter = new ArrayAdapter<String>(MainActivity.this,android.R.layout.simple_list_item_1,android.R.id.text1,arrayList);
                        if(news1.getImage()!=null){
                            new AsyncGetImage(MainActivity.this).execute(news1.getImage());
                        }

                        listView.setAdapter(adapter);
                    }
                }else{
                    Toast.makeText(this, "Already at last element", Toast.LENGTH_SHORT).show();
                }


                break;
            }
            case R.id.button_last: {
                ListView listView = (ListView) findViewById(R.id.listView_data);
                News news=null;
                if(newsList!=null){
                    News news1 = newsList.get(newsList.size()-1);
                    ArrayList<String> arrayList = news1.toArrayList();
                    Log.d(logText,"News article is"+arrayList.toString());
                    ArrayAdapter<String> adapter = new ArrayAdapter<String>(MainActivity.this,android.R.layout.simple_list_item_1,android.R.id.text1,arrayList);
                    //adapter.getItem();
                    if(news1.getImage()!=null){
                        new AsyncGetImage(MainActivity.this).execute(news1.getImage());
                    }
                    listView.setAdapter(adapter);
                }

                break;
            }
            case R.id.button_finish: {
                finish();
                break;
            }
        }
    }

    @Override
    public void loadData(ArrayList<News> result) {
        newsList = result;
        ListView listView = (ListView) findViewById(R.id.listView_data);
        News news = result.get(0);
        i = 0;
        ArrayList<String> arrayList = news.toArrayList();
        Log.d("Main", "News article is" + arrayList.toString());
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(MainActivity.this, android.R.layout.simple_list_item_1, android.R.id.text1, arrayList);
        if(news.getImage()!=null){
            new AsyncGetImage(MainActivity.this).execute(news.getImage());
        }
        listView.setAdapter(adapter);
    }

    @Override
    public Context getContext() {
        return this;
    }

}
