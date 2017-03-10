package com.uncc.prateek.notekeeper;

import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.sql.Date;

/**
 * Created by Praneeth on 2/27/2017.
 */

public class NotesTable {
    static final String TABLE_NAME="NOTES";
    static final String COLUMN_ID="_id";
    static final String COLUMN_NOTE="note";
    static final String COLUMN_PRIORITY="priority";
    static final String COLUMN_TIME= "time";
    static final String COLUMN_STATUS="status";

    static public void onCreate(SQLiteDatabase db){
        StringBuilder sb = new StringBuilder();
        sb.append("CREATE TABLE "+ TABLE_NAME + " (");
        sb.append(COLUMN_ID + " integer primary key autoincrement, ");
        sb.append(COLUMN_NOTE + " text not null, ");
        sb.append(COLUMN_PRIORITY + " text not null, ");
        sb.append(COLUMN_TIME + " text not null, ");
        sb.append(COLUMN_STATUS + " text not null");
        sb.append(");");


        try {
            Log.d("Create",sb.toString());
            db.execSQL(sb.toString());
        } catch (Exception e) {
            //e.printStackTrace();
            Log.d("exc ",e.toString());
        }


    }
    static public void onUpgrade(SQLiteDatabase db,int oldVersion, int newVersion){
        //db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        //NotesTable.onCreate(db);


    }
}
