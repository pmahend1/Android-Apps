package com.example.praneeth.itunestoppaidapps;
/*
        a. Assignment : Homework 6
        b. File Name. : JsonParser.java
        c. Group Name : Group 06
        d. Students in  group : Prateek Mahendrakar , Siva Ram Praneeth Vemulapalli
 */

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;


public class JsonParser {
    public static ArrayList<AppEntry> parseData(String jsonString) {
        ArrayList<AppEntry> AppEntryList = new ArrayList<>();
        JSONObject root = null;
        try {
            root = new JSONObject(jsonString);
            JSONObject entryObj = root.getJSONObject("feed");
            JSONArray entryArray = entryObj.getJSONArray("entry");
            for (int i = 0; i < entryArray.length(); i++) {
                JSONObject entry = entryArray.getJSONObject(i);
                String name = entry.getJSONObject("im:name").getString("label");
                String imageUrl = entry.getJSONArray("im:image").getJSONObject(0).getString("label");
                Double price = Double.parseDouble(entry.getJSONObject("im:price").getJSONObject("attributes").getString("amount"));
                int id = i;
                AppEntry appEntry = new AppEntry(name, price, imageUrl, false, id);
                AppEntryList.add(appEntry);
            }
            return AppEntryList;
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }
}
