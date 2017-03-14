package com.example.praneeth.triviaapp;

/*  a. Assignment #. Homework 4
    b. File Name. : Question.java
    c. Full name of all students in your group. : Prateek , Praneeth
    */
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.lang.reflect.Array;
import java.util.ArrayList;


public class Question implements Serializable{
    private String text,image,answer;
    private int id;
    private String[] choices=new String[10];
    static String logText = "Question";

/*    public Question(JSONObject questionJsonObject) {
    }*/

    public Question(JSONObject questionJsonObj) throws JSONException {
        this.id = questionJsonObj.getInt("id");
        Log.d(logText,""+this.id);
        this.text = questionJsonObj.getString("text");
        Log.d(logText,this.text);
        try{
            this.image = questionJsonObj.getString("image");
            Log.d(logText,this.image);
        }catch (Exception e){
            Log.d(logText,"Exc for image "+ e.getMessage() );
        }


        //this.answer = questionJsonObj.getString("answer");
        JSONObject choicesJsonObj = questionJsonObj.getJSONObject("choices");
        Log.d(logText,"choicesJsonObj"+choicesJsonObj.toString());
        JSONArray choicesJsonArray = (JSONArray) choicesJsonObj.get("choice");
        Log.d(logText,"choicesJsonArray "+choicesJsonArray.toString());

        this.answer=choicesJsonObj.getString("answer");
        Log.d(logText,this.answer);

        if(choicesJsonArray!=null){
            for (int i=0;i<choicesJsonArray.length();i++) {
                this.choices[i]=choicesJsonArray.get(i).toString();
                Log.d(logText,this.choices[i]);
            }
        }


    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public String[] getChoices() {
        return choices;
    }

    public void setChoices(String[] choices) {
        this.choices = choices;
    }

    public ArrayList<String> toArrayList(){
        ArrayList<String> arr = new ArrayList<String>();
        arr.add(""+this.id);
        arr.add(this.choices.toString());
        arr.add(this.image);
        arr.add(this.text);
        arr.add(this.answer);

        return arr;
    }
}
