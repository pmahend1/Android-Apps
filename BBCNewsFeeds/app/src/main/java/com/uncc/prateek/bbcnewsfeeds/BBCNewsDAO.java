package com.uncc.prateek.bbcnewsfeeds;

import android.annotation.TargetApi;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;


public class BBCNewsDAO {
    private SQLiteDatabase db;

    public BBCNewsDAO(SQLiteDatabase db){
        this.db = db;
    }
    public long save(BBCNews note){
        ContentValues values = new ContentValues();
        values.put(BBCNewsTable.COLUMN_TITLE,note.getTitle());
        values.put(BBCNewsTable.COLUMN_ID,note.get_id());
        values.put(BBCNewsTable.COLUMN_IMAGE_LINK,note.getImageLink());
        values.put(BBCNewsTable.COLUMN_DATE, String.valueOf(note.getNewsDateStr()));
        return db.insert(BBCNewsTable.TABLE_NAME,null,values);
    }
    public boolean update(BBCNews note){
        Log.d("update NoteDao ",note.toString());
        ContentValues values = new ContentValues();
        values.put(BBCNewsTable.COLUMN_TITLE,note.getTitle());
        values.put(BBCNewsTable.COLUMN_ID,note.get_id());
        values.put(String.valueOf(BBCNewsTable.COLUMN_IMAGE_LINK),note.getImageLink());
        values.put(BBCNewsTable.COLUMN_DATE, String.valueOf(note.getNewsDateStr()));
        return db.update(BBCNewsTable.TABLE_NAME,values, BBCNewsTable.COLUMN_ID + "=?",new String[]{note.get_id()+""})>0;
    }
    public boolean delete(BBCNews note){
        return db.delete(BBCNewsTable.TABLE_NAME, BBCNewsTable.COLUMN_ID + "=?", new String[]{note.get_id()+""})>0;
    }
    @TargetApi(16)
    public BBCNews get(long id){
        BBCNews note= null;
        Cursor c=db.query(true, BBCNewsTable.TABLE_NAME,new String[]{BBCNewsTable.COLUMN_ID, BBCNewsTable.COLUMN_TITLE,BBCNewsTable.COLUMN_DATE, BBCNewsTable.COLUMN_IMAGE_LINK}, BBCNewsTable.COLUMN_ID + "=?", new String[]{id+""},null,null,null,null,null);
        if (c!=null && c.moveToFirst()) {
            note = buildNoteFromCursor(c);
            if (!c.isClosed()) {
                c.close();
            }
        }
        return note;
    }
    public List<BBCNews> getAll() {
        List<BBCNews> notes = new ArrayList<BBCNews>();
        try{
            Cursor c = db.query(BBCNewsTable.TABLE_NAME, new String[]{BBCNewsTable.COLUMN_ID, BBCNewsTable.COLUMN_TITLE, BBCNewsTable.COLUMN_DATE, BBCNewsTable.COLUMN_IMAGE_LINK}, null, null, null, null, null);
            if (c != null && c.moveToFirst()) {
                do {
                    BBCNews note= buildNoteFromCursor(c);
                    if (note!=null){
                        notes.add(note);
                    }
                }while (c.moveToNext());
                if (!c.isClosed()) {
                    c.close();
                }
            }
        }catch (Exception e){
            Log.d("exc",e.toString());
        }

        return notes;
    }
    private BBCNews buildNoteFromCursor(Cursor c){
        BBCNews note = null;
        if (c!=null){
            note = new BBCNews();
            note.set_id(c.getLong(0));
            note.setTitle(c.getString(1));
            note.setNewsDateStr(c.getString(2));
            note.setImageLink(c.getString(3));
        }
        return note;
    }
}
