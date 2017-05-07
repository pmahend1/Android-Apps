package com.example.praneeth.myapplication;

import android.widget.TextView;

/**
 * Created by Praneeth on 4/22/2017.
 */

public class CheckFieldValidator {
    public static boolean checkField(TextView field){
        String fieldData=field.getText().toString();
        if(fieldData.equalsIgnoreCase("") && fieldData!=null){field.setError("Please fill this field");return false;}
        return true;
    }
}