package com.uncc.prateek.bbcnewsfeeds;

import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.sql.Date;


public class BBCNewsTable {
    static final String TABLE_NAME="BBC_NEWS";
    static final String COLUMN_ID="_id";
    static final String COLUMN_TITLE="title";
    static final String COLUMN_DATE= "date";
    static final String COLUMN_IMAGE_LINK= "image_link";


    static public void onCreate(SQLiteDatabase db){
        StringBuilder sb = new StringBuilder();
        sb.append("CREATE TABLE "+ TABLE_NAME + " (");
        sb.append(COLUMN_ID + " integer primary key autoincrement, ");
        sb.append(COLUMN_TITLE + " text not null, ");
        sb.append(COLUMN_DATE + " text not null, ");
        sb.append(COLUMN_IMAGE_LINK + " text not null, ");
        sb.append(");");


        try {
            Log.d("Create",sb.toString());
            db.execSQL(sb.toString());
        } catch (Exception e) {
            Log.d("BBCNewsTable ",e.toString());
        }


    }
    static public void onUpgrade(SQLiteDatabase db,int oldVersion, int newVersion){
            Log.d("BBCNewsTable","onUpgrade");
    }
}
