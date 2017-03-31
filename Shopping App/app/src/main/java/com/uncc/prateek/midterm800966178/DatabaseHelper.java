package com.uncc.prateek.midterm800966178;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DatabaseHelper extends SQLiteOpenHelper{
	static final String DATABASE_NAME = "products.db";
	static final int DATABASE_VERSION = 2;
	static final String TAG = "demo1";
	
	DatabaseHelper(Context mContext){
		super(mContext, DATABASE_NAME, null, DATABASE_VERSION);
	}
	
	@Override
	public void onOpen(SQLiteDatabase db) {
		super.onOpen(db);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		CartTable.onCreate(db);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		Log.w(TAG, "Upgrading db from version " + oldVersion + " to " + newVersion + ", this will destroy all old data");
		CartTable.onUpgrade(db, oldVersion, newVersion);
	}
}
