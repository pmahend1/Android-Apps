package com.example.databasenotesdemo;

import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

public class NotesTable {
	static final String TABLE_NAME = "notes";
	static final String NOTE_ID = "_id";
	static final String Note_SUBJECT = "subject";
	static final String Note_TEXT = "note";
	
	static public void onCreate(SQLiteDatabase db){		
		StringBuilder sb = new StringBuilder();		
		sb.append("CREATE TABLE " + NotesTable.TABLE_NAME + " (");
		sb.append(NotesTable.NOTE_ID + " integer primary key autoincrement, ");
		sb.append(NotesTable.Note_SUBJECT + " text not null, ");
		sb.append(NotesTable.Note_TEXT + " text not null");
		sb.append(");");		
		try{
			db.execSQL(sb.toString());
		} catch (SQLException e){				
			e.printStackTrace();
		}
	}
	
	static public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion){
		db.execSQL("DROP TABLE IF EXISTS " + NotesTable.TABLE_NAME);
		NotesTable.onCreate(db);
	}	
}
