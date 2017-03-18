package com.example.databasenotesdemo;
import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class NoteDAO { //Data Access Object (DAO)
	private SQLiteDatabase db;
	
	public NoteDAO(SQLiteDatabase db){
		this.db = db;
	}
	
	public long save(Note note){
		ContentValues values = new ContentValues();
		values.put(NotesTable.Note_SUBJECT, note.getSubject());
		values.put(NotesTable.Note_TEXT, note.getText());
		return db.insert(NotesTable.TABLE_NAME, null, values);
	}
	
	public boolean update(Note note){
		ContentValues values = new ContentValues();
		values.put(NotesTable.Note_SUBJECT, note.getSubject());
		values.put(NotesTable.Note_TEXT, note.getText());
		return db.update(NotesTable.TABLE_NAME, values, NotesTable.NOTE_ID+"="+ note.get_id(), null) > 0;		
	}	
	
	
	public boolean delete(Note note){
		return db.delete(NotesTable.TABLE_NAME, NotesTable.NOTE_ID+"="+note.get_id(), null)>0;
	}
	
	public Note get(long id){
		Note note = null;
		Cursor c = db.query(true, NotesTable.TABLE_NAME, 
				new String[]{NotesTable.NOTE_ID, NotesTable.Note_SUBJECT, NotesTable.Note_TEXT}, 
				NotesTable.NOTE_ID+"="+ id, null, null, null, null, null);
		if(c != null && c.moveToFirst()){
			note = this.buildNoteFromCursor(c);			
		}	
		
		if(!c.isClosed()){
			c.close();
		}		
		return note;
	}
	
	public List<Note> getAll(){
		List<Note> list = new ArrayList<Note>();
		Cursor c = db.query(NotesTable.TABLE_NAME, 
				new String[]{NotesTable.NOTE_ID, NotesTable.Note_SUBJECT, NotesTable.Note_TEXT}, 
				null, null, null, null, null);
		if(c != null && c.moveToFirst()){				
			do{
				Note note = this.buildNoteFromCursor(c);
				if(note != null){
					list.add(note);
				}				
			} while(c.moveToNext());
			
			if(!c.isClosed()){
				c.close();
			}
		}
		return list;
	}
	
	private Note buildNoteFromCursor(Cursor c){
		Note note = null;		
		if(c != null){
			note = new Note();
			note.set_id(c.getLong(0));
			note.setSubject(c.getString(1));
			note.setText(c.getString(2));			
		}
		return note;
	}
}