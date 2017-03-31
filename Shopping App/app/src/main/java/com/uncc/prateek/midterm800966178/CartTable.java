package com.uncc.prateek.midterm800966178;


import android.database.sqlite.SQLiteDatabase;
import android.util.Log;



public class CartTable {
    static final String TABLE_NAME="cart";
    static final String COLUMN_ID="_id";
    static final String COLUMN_1="product";
    static final String COLUMN_2="price";

    static public void onCreate(SQLiteDatabase db){
        StringBuilder sb = new StringBuilder();
        sb.append("CREATE TABLE "+ TABLE_NAME + " (");
        sb.append(COLUMN_ID + " integer primary key autoincrement, ");
        sb.append(COLUMN_1 + " text not null, ");
        sb.append(COLUMN_2 + " text not null, ");
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
