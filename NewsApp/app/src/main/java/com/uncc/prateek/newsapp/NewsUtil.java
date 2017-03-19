package com.uncc.prateek.newsapp;
/*a. Assignment #. InClass 04
b. File Name. NewsUtil.java
c. Full name of all students in your group. Prateek Mahendrakar , Siva Ram Praneeth Vemulapalli*/
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by Prateek on 06-02-2017.
 */

public class NewsUtil {

    static public class NewsJSONParser {
        static ArrayList<News> parseNews(String jsonString) throws JSONException {
            ArrayList<News> newses = new ArrayList<News>();
            Log.d("NewsUtil", jsonString);
            JSONObject jsonObject = null;
            try {
                jsonObject = new JSONObject(jsonString);
                Log.d("Newsutil", jsonObject.toString());

                JSONArray articles = (JSONArray) jsonObject.get("articles");

                Log.d("NewUtil", articles.toString());
                for (int i = 0; i < articles.length(); i++) {
                    JSONObject newsJSONObject = articles.getJSONObject(i);
                    News news = new News(newsJSONObject);
                    Log.d("NewsUtil",news.toString());
                    newses.add(news);
                }

            } catch (Exception e) {
                Log.d("NewsUtilExc", e.getStackTrace().toString());
            }
            return newses;
        }
    }
}
