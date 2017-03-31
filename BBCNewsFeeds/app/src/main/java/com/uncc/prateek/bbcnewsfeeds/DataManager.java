package com.uncc.prateek.bbcnewsfeeds;

import java.util.List;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

public class DataManager {
	Context mContext;
	DatabaseHelper dbOpenHelper;
	SQLiteDatabase db;
	BBCNewsDAO BBCNewsDao;
	
	public DataManager(Context mContext){
		this.mContext = mContext;
		dbOpenHelper = new DatabaseHelper(mContext);
		db = dbOpenHelper.getWritableDatabase();
		dbOpenHelper.onCreate(db);
		BBCNewsDao = new BBCNewsDAO(db);
	}
	
	public void close(){
		db.close();
	}
	
	public long saveNote(BBCNews note){
		return BBCNewsDao.save(note);
	}
	
	public boolean updateNote(BBCNews note){
		return BBCNewsDao.update(note);
	}
	
	public boolean deleteNote(BBCNews note){
		return BBCNewsDao.delete(note);
	}
	
	public BBCNews getNote(long id){
		return BBCNewsDao.get(id);
	}
	
	public List<BBCNews> getAllNotes(){
		return BBCNewsDao.getAll();
	}
}
