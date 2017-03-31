package com.uncc.prateek.bbcnewsfeeds;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import java.util.List;



public class DatabaseDataManager {
    private Context mContext;
    private DatabaseOpenHelper dbOpenHelper;
    private SQLiteDatabase db;
    private BBCNewsDAO BBCNewsDAO;

    public DatabaseDataManager(Context mContext){
        this.mContext= mContext;
        dbOpenHelper = new DatabaseOpenHelper(this.mContext);
        db= dbOpenHelper.getWritableDatabase();

        BBCNewsDAO = new BBCNewsDAO(db);
    }
    public void close(){
        if (db!=null){
            db.close();
        }
    }
    public BBCNewsDAO getBBCNewsDAO(){
        return this.BBCNewsDAO;
    }
    public long saveNote(BBCNews note){
        return this.BBCNewsDAO.save(note);
    }
    public boolean updateNote(BBCNews note){
        return this.BBCNewsDAO.update(note);
    }
    public boolean deleteNote(BBCNews note){
        return this.BBCNewsDAO.delete(note);
    }
    public BBCNews getNote(long id){
        return this.BBCNewsDAO.get(id);
    }
    public List<BBCNews> getAllNotes(){
        return this.BBCNewsDAO.getAll();
    }



}
