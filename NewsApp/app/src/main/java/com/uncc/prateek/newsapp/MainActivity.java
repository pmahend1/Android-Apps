package com.uncc.prateek.newsapp;
/*a. Assignment #. InClass 04
b. File Name. MainActivity.java
c. Full name of all students in your group. Prateek Mahendrakar , Siva Ram Praneeth Vemulapalli*/
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import java.net.HttpURLConnection;
import java.util.ArrayList;
import java.util.LinkedList;
import java.net.URL;

public class MainActivity extends AppCompatActivity implements View.OnClickListener, AsyncNewsGet.INewsData{

    String bbcNewsURL = "https://newsapi.org/v1/articles?source=bbc-news&sortBy=top&apiKey=29cf2c1ae98c4f31a850f51ce0fd88c4";
    String cnnNewsURL = "https://newsapi.org/v1/articles?source=cnn&sortBy=top&apiKey=29cf2c1ae98c4f31a850f51ce0fd88c4";
    String buzzFeedURL = "https://newsapi.org/v1/articles?source=buzzfeed&sortBy=top&apiKey=29cf2c1ae98c4f31a850f51ce0fd88c4";
    String espnURL = "https://newsapi.org/v1/articles?source=espn&sortBy=top&apiKey=29cf2c1ae98c4f31a850f51ce0fd88c4";
    String skyNewsURL = "https://newsapi.org/v1/articles?source=sky-news&sortBy=top&apiKey=29cf2c1ae98c4f31a850f51ce0fd88c4";
    String URL="";
    String logText="Main";
    ArrayList<News> newsList = new ArrayList<News>();
    int i=0;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final Spinner spinner_newsSources = (Spinner) findViewById(R.id.spinner_newsSources);
        final Context context = MainActivity.this;

        final String[] values = getResources().getStringArray(R.array.spinner_newsSources);
        ArrayAdapter<String> dropDownAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, values);
        dropDownAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spinner_newsSources.setAdapter(dropDownAdapter);

        findViewById(R.id.button_GetNews).setOnClickListener(this);
        findViewById(R.id.button_first).setOnClickListener(this);
        findViewById(R.id.button_previous).setOnClickListener(this);
        findViewById(R.id.button_last).setOnClickListener(this);
        findViewById(R.id.button_next).setOnClickListener(this);
        findViewById(R.id.button_finish).setOnClickListener(this);


    }

    @Override
    public void onClick(View v) {

        int viewId = v.getId();

        Log.d(logText,"Onclick of "+v.getId());
        switch (viewId) {
            case R.id.button_first: {
                i=0;
                ListView listView = (ListView) findViewById(R.id.listView_news);
                News news=null;
                if(newsList!=null){
                    News news1 = newsList.get(0);
                    ArrayList<String> arrayList = news1.toArrayList();
                    Log.d(logText,"News article is"+arrayList.toString());
                    ArrayAdapter<String> adapter = new ArrayAdapter<String>(MainActivity.this,android.R.layout.simple_list_item_1,android.R.id.text1,arrayList);
                    new GetImage().execute(news1.getUrlToImage());
                    listView.setAdapter(adapter);
                }


                break;
            }
            case R.id.button_previous: {
                if(i>0){
                    i=i-1;
                    ListView listView = (ListView) findViewById(R.id.listView_news);
                    News news=null;
                    if(newsList!=null){
                        News news1 = newsList.get(i);
                        ArrayList<String> arrayList = news1.toArrayList();
                        Log.d(logText,"News article is"+arrayList.toString());
                        ArrayAdapter<String> adapter = new ArrayAdapter<String>(MainActivity.this,android.R.layout.simple_list_item_1,android.R.id.text1,arrayList);
                        new GetImage().execute(news1.getUrlToImage());
                        listView.setAdapter(adapter);
                    }
                }else{
                    Toast.makeText(this, "Already at first", Toast.LENGTH_SHORT).show();
                }



                break;
            }
            case R.id.button_next: {
                if(i<newsList.size()-1){
                    i++;
                    ListView listView = (ListView) findViewById(R.id.listView_news);
                    News news=null;
                    if(newsList!=null){
                        News news1 = newsList.get(i);
                        ArrayList<String> arrayList = news1.toArrayList();
                        Log.d(logText,"News article is"+arrayList.toString());
                        ArrayAdapter<String> adapter = new ArrayAdapter<String>(MainActivity.this,android.R.layout.simple_list_item_1,android.R.id.text1,arrayList);
                        new GetImage().execute(news1.getUrlToImage());
                        listView.setAdapter(adapter);
                    }
                }else{
                    Toast.makeText(this, "Already at last element", Toast.LENGTH_SHORT).show();
                }


                break;
            }
            case R.id.button_last: {
                ListView listView = (ListView) findViewById(R.id.listView_news);
                News news=null;
                if(newsList!=null){
                    News news1 = newsList.get(newsList.size()-1);
                    ArrayList<String> arrayList = news1.toArrayList();
                    Log.d(logText,"News article is"+arrayList.toString());
                    ArrayAdapter<String> adapter = new ArrayAdapter<String>(MainActivity.this,android.R.layout.simple_list_item_1,android.R.id.text1,arrayList);
                    new GetImage().execute(news1.getUrlToImage());
                    listView.setAdapter(adapter);
                }

                break;
            }
            case R.id.button_finish: {
                    finish();

                break;
            }
            case R.id.button_GetNews: {
                Log.d(logText,"Inside R.id.button_GetNews");
                final Spinner spinner_newsSources = (Spinner) findViewById(R.id.spinner_newsSources);
                String selectedFeed = spinner_newsSources.getSelectedItem().toString();
                switch(selectedFeed){
                    case "BBC":
                        URL = bbcNewsURL;
                        break;
                    case "CNN":
                        URL = cnnNewsURL;
                        break;
                    case "Sky News":{
                        URL=skyNewsURL;
                        break;
                    }
                    case "ESPN":{
                        URL=espnURL;
                        break;
                    }
                    case "BuzzFeed":{
                        URL=buzzFeedURL;
                        break;
                    }
                }
                Log.d(logText,"URL:"+URL);
                new AsyncNewsGet(this).execute(URL);
                break;
            }
            default: {
                break;
            }

        }
    }


    @Override
    public void loadData(ArrayList<News> result) {
        if(result!=null){
            Log.d(logText,"loadData here");
            newsList = result;
            ListView listView = (ListView) findViewById(R.id.listView_news);
            News news = result.get(0);
            i=0;
            ArrayList<String> arrayList = news.toArrayList();
            Log.d(logText,"News article is"+arrayList.toString());
            ArrayAdapter<String> adapter = new ArrayAdapter<String>(MainActivity.this,android.R.layout.simple_list_item_1,android.R.id.text1,arrayList);
            new GetImage().execute(news.getUrlToImage());
            listView.setAdapter(adapter);

        }
        else{
            Log.d(logText,"loadData on NULL");
        }

    }

    @Override
    public Context getContext() {
        return this;
    }

    private class GetImage extends AsyncTask<String,Void,Bitmap> {

        @Override
        protected void onPostExecute(Bitmap s) {
            if(s != null ){
                Log.d("onPostExecute",s.toString());
                ImageView iw = (ImageView) findViewById(R.id.imageView_image);
                iw.setImageBitmap(s);
            }else{
                Log.d("onPostExecute","NULL");
            }
        }

        @Override
        protected Bitmap doInBackground(String... params) {

            try {
                URL url = new URL(params[0]);
                HttpURLConnection con =  (HttpURLConnection) url.openConnection();
                con.setRequestMethod("GET");
                Bitmap image = BitmapFactory.decodeStream(con.getInputStream());

                return image;
            }
            catch(Exception e){
                Toast.makeText(MainActivity.this, "", Toast.LENGTH_SHORT).show();
            }
            return null;
        }
    }

}
