package com.uncc.prateek.notekeeper;

import android.annotation.TargetApi;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Praneeth on 2/27/2017.
 */

public class NoteDAO {
    private SQLiteDatabase db;
    public NoteDAO(SQLiteDatabase db){
        this.db = db;
    }
    public long save(Note note){
        ContentValues values = new ContentValues();
        values.put(NotesTable.COLUMN_NOTE,note.getNote());
        values.put(NotesTable.COLUMN_PRIORITY,note.getPriority());
        values.put(NotesTable.COLUMN_TIME,note.getTime());
        values.put(NotesTable.COLUMN_STATUS, String.valueOf(note.getStatus()));
        return db.insert(NotesTable.TABLE_NAME,null,values);
    }
    public boolean update(Note note){
        Log.d("update NoteDao ",note.toString());
        ContentValues values = new ContentValues();
        values.put(NotesTable.COLUMN_NOTE,note.getNote());
        values.put(NotesTable.COLUMN_PRIORITY,note.getPriority());
        values.put(String.valueOf(NotesTable.COLUMN_TIME),note.getTime());
        values.put(NotesTable.COLUMN_STATUS, String.valueOf(note.getStatus()));
        return db.update(NotesTable.TABLE_NAME,values,NotesTable.COLUMN_ID + "=?",new String[]{note.get_id()+""})>0;
    }
    public boolean delete(Note note){
        return db.delete(NotesTable.TABLE_NAME,NotesTable.COLUMN_ID + "=?", new String[]{note.get_id()+""})>0;
    }
    @TargetApi(16)
    public Note get(long id){
        Note note= null;
        Cursor c=db.query(true,NotesTable.TABLE_NAME,new String[]{NotesTable.COLUMN_ID,NotesTable.COLUMN_NOTE,NotesTable.COLUMN_PRIORITY,NotesTable.COLUMN_TIME,NotesTable.COLUMN_STATUS},NotesTable.COLUMN_ID + "=?", new String[]{id+""},null,null,null,null,null);
        if (c!=null && c.moveToFirst()) {
            note = buildNoteFromCursor(c);
            if (!c.isClosed()) {
                c.close();
            }
        }
        return note;
    }
    public List<Note> getAll() {
        List<Note> notes = new ArrayList<Note>();
        try{
            Cursor c = db.query(NotesTable.TABLE_NAME, new String[]{NotesTable.COLUMN_ID,NotesTable.COLUMN_NOTE,NotesTable.COLUMN_PRIORITY,NotesTable.COLUMN_TIME,NotesTable.COLUMN_STATUS}, null, null, null, null, null);
            if (c != null && c.moveToFirst()) {
                do {
                    Note note= buildNoteFromCursor(c);
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
    private Note buildNoteFromCursor(Cursor c){
        Note note = null;
        if (c!=null){
            note = new Note();
            note.set_id(c.getLong(0));
            note.setNote(c.getString(1));
            note.setPriority(c.getString(2));
            note.setTime(c.getString(3));
            note.setStatus(c.getString(4));
        }
        return note;
    }
}
