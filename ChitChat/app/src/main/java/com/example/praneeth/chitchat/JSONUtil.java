package com.example.praneeth.chitchat;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by Prateek on 06-02-2017.
 */

public class JSONUtil {

    static public class ObjectJSONParser {
        static ArrayList<Object> parseObject(String jsonString) throws JSONException {
            ArrayList<Object> Objectes = new ArrayList<Object>();
            Log.d("JSONUtil", jsonString);
            JSONObject jsonObject = null;
            try {
                jsonObject = new JSONObject(jsonString);
                Log.d("Objectutil", jsonObject.toString());

                JSONArray articles = (JSONArray) jsonObject.get("requests");

                Log.d("NewUtil", articles.toString());
                for (int i = 0; i < articles.length(); i++) {
                    JSONObject newJSONObject = articles.getJSONObject(i);
                    Object Object = new Object();
                    Log.d("JSONUtil",Object.toString());
                    Objectes.add(Object);
                }

            } catch (Exception e) {
                Log.d("JSONUtilExc", e.getStackTrace().toString());
            }
            return Objectes;
        }
    }
}
