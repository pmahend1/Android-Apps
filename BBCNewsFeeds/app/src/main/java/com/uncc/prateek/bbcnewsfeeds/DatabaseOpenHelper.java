package com.uncc.prateek.bbcnewsfeeds;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;



public class DatabaseOpenHelper extends SQLiteOpenHelper {
    static final String DB_Name = "bbc_news_apk.db";
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
        BBCNewsTable.onCreate(sqLiteDatabase);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        BBCNewsTable.onUpgrade(sqLiteDatabase,i,i1);
    }
}
