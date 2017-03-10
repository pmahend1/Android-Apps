package com.uncc.prateek.notekeeper;
/*
        a. Assignment : InClass 7
        b. File Name. : MainActivity.java
        c. Group Name : Group 06
        d. Students in  group : Prateek Mahendrakar , Siva Ram Praneeth Vemulapalli
 */
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Praneeth on 2/27/2017.
 */

public class DatabaseOpenHelper extends SQLiteOpenHelper {
    static final String DB_Name = "mynotes.db";
    static final int DB_Version = 1;


    public DatabaseOpenHelper(Context context) {
        super(context, DB_Name, null, DB_Version);
    }

    @Override
    public void onOpen(SQLiteDatabase db) {
        super.onOpen(db);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        NotesTable.onCreate(sqLiteDatabase);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        NotesTable.onUpgrade(sqLiteDatabase,i,i1);
    }
}
