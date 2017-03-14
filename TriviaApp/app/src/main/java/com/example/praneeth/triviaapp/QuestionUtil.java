package com.example.praneeth.triviaapp;

/*a. Assignment #. InClass 04
b. File Name. QuestionUtil.java
c. Full name of all students in your group. Prateek Mahendrakar , Siva Ram Praneeth Vemulapalli*/
        import android.util.Log;

        import org.json.JSONArray;
        import org.json.JSONException;
        import org.json.JSONObject;

        import java.util.ArrayList;


/**
 * Created by Prateek on 06-02-2017.
 */

public class QuestionUtil {

    static String logText = "QuestionUtil";

    static public class QuestionJSONParser {
        static ArrayList<Question> parseQuestions(String jsonString) throws JSONException {
            ArrayList<Question> questionsArray = new ArrayList<Question>();
            Log.d(logText, jsonString);
            JSONObject jsonObject = null;
            try {
                jsonObject = new JSONObject(jsonString);
                Log.d(logText, "jsonObject.toString()"+jsonObject.toString());

                JSONArray questions = (JSONArray) jsonObject.get("questions");

                //Log.d(logText,"questions JSON array is :" +questions.toString());
                if(questions!=null){
                    for (int i = 0; i < questions.length(); i++) {
                        JSONObject questionJSONObject = questions.getJSONObject(i);
                        Question question = new Question(questionJSONObject);
                        //Log.d(logText,"question.toString()"+question.toString());
                        questionsArray.add(question);
                    }

                }

            } catch (Exception e) {
                Log.d(logText, "Exception at QuestionJSONParser "+ e.getMessage());
            }
            return questionsArray;
        }
    }
}

