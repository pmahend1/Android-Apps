/*      a. Assignment #. Homework 3
        b. File Name.
        c. Full name of all students in your group  Prateek Mahendrakar
                                                    Siva Ram Praneeth Vemulapalli
*/
package com.uncc.prateek.wordcounter;

/**
 * Created by Prateek on 05-02-2017.
 */


import android.util.Log;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class WordCount {
    static TreeMap<String, Integer> frequencyData = new TreeMap<String, Integer>();
    public static String logLevel = "";
    public static String logText = "WordCount" + "/" + logLevel;
    public static boolean fileReadStatus = false;
    public static boolean matchCaseSet = false;

    public WordCount() {
    }

    public static void clearCounts(){
        frequencyData.clear();
        fileReadStatus = false;
    }
    public int getCount
            (String word,TreeMap<String, Integer> frequencyData) {
        if (frequencyData.containsKey(word)) {
            return frequencyData.get(word);
        } else {
            return 0;
        }
    }


    public  int search(String fileContents,String searchWord,boolean matchCase) {
        logLevel="search";
        Log.d(logText,"search");
        if((!fileReadStatus) || (matchCase!=matchCaseSet)){
            frequencyData.clear();
            readWordFile(fileContents,frequencyData,matchCase);
        }
        if(frequencyData!=null && frequencyData.size()!=0){
            int count=0;
            try{
                 count = frequencyData.get(searchWord);
            }catch (Exception e){
                count=0;
            }


            Log.d(logText,"has Count >0 ");
            return count;
        }
        return  0;

    }


    public  void readWordFile(String fileContents, TreeMap<String, Integer> frequencyData,boolean matchCase) {
        Scanner wordFile=null;
        String word;
        Integer count;

        try {
            Log.d(logText, "readWordFile ");
            wordFile = new Scanner(fileContents);
        }catch (Exception e){
            Log.d(logText,e.getMessage());
        }

        while (wordFile.hasNext()) {
            if(!matchCase){
                word = wordFile.next().toLowerCase();
                matchCaseSet =false;
            }else{
                word = wordFile.next();
                matchCaseSet=true;
            }

             word = word.replaceAll("[^a-zA-Z0-9]+","");

            count = getCount(word, frequencyData) + 1;
            frequencyData.put(word, count);
            fileReadStatus=true;
        }
    }

}